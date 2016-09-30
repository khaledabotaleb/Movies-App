package com.example.khaled.movies_project;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by khaled on 20/09/2016.
 */
public class MainFragment extends Fragment {

    private GridView mGridView;
    private GridViewAdapter mGridAdapter;
    private ArrayList<Model> mGridData;
    static ArrayList<String> review = new ArrayList<>();
    static ArrayList<String> tailler = new ArrayList<>();


    Model Movie_model;
    private SharedPreferences pref;
    String Posters = "";
    String Overveiws = "";
    String Titels = "";
    String Dates = "";
    String Vote = "";
    String Trailer = "";
    static int Postion;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contaner, Bundle saveinstancesState) {

        View view = inflater.inflate(R.layout.fragment_main, contaner, false);

        mGridView = (GridView) view.findViewById(R.id.gridView);

        mGridData = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item, mGridData);
        mGridView.setAdapter(mGridAdapter);
        new JsonTask().execute("http://api.themoviedb.org/3/movie/popular?api_key=473af073aa2961cdedc82165fffc3985");
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Model item = (Model) parent.getItemAtPosition(position);
                ImageView imageView = (ImageView) view.findViewById(R.id.grid_item);
                String Title = mGridData.get(position).getOrignal_title();
                String Poster_url = mGridData.get(position).getPoster();
                String release_date = mGridData.get(position).getRelase_date();
                String Overview = mGridData.get(position).getOverview();
                String Id = mGridData.get(position).getId();

                new JsonTask1().execute("https://api.themoviedb.org/3/movie/" + Id + "/reviews?api_key=09ef905fb0a8f078de853a6aa6ac8983");
                new JsonTask2().execute("https://api.themoviedb.org/3/movie/" + Id + "/videos?api_key=09ef905fb0a8f078de853a6aa6ac8983");

                Postion = position;
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("title", Title);
                intent.putExtra("Poster", Poster_url);
                intent.putExtra("Release", release_date);
                intent.putExtra("overview", Overview);
                intent.putExtra("id", Overview);
                intent.putExtra("review", review);
                intent.putExtra("tailler", tailler);




                startActivity(intent);
            }
        });


        return view;

    }


    public class JsonTask extends AsyncTask<String, String, Integer> {


        @Override
        protected Integer doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("results");

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    Movie_model = new Model();
                    Movie_model.setPoster("http://image.tmdb.org/t/p/w185" + finalObject.getString("poster_path"));
                    Movie_model.setOverview(finalObject.getString("overview"));
                    Movie_model.setOrignal_title(finalObject.getString("original_title"));
                    Movie_model.setRelase_date(finalObject.getString("release_date"));
                    Movie_model.setVote_average(finalObject.getDouble("vote_average"));
                    Movie_model.setId(finalObject.getString("id"));
                    mGridData.add(Movie_model);
                    Posters = mGridData.get(i).getPoster() + "amr" + Posters;
                    Vote = mGridData.get(i).getVote_average() + "amr" + Vote;
                    Overveiws = mGridData.get(i).getOverview() + "amr" + Overveiws;
                    Titels = mGridData.get(i).getOrignal_title() + "amr" + Titels;
                    Dates = mGridData.get(i).getRelase_date() + "amr" + Dates;


                }

                return 1;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 1) {

                mGridAdapter.setGridData(mGridData);
                pref = getActivity().getSharedPreferences("test", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("poster", Posters);
                editor.putString("titel", Titels);
                editor.putString("over", Overveiws);
                editor.putString("date", Dates);
                editor.putString("vot", Vote);

                editor.commit();


            } else {
                Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
                pref = getActivity().getSharedPreferences("test", getActivity().MODE_PRIVATE);

                String dates[] = pref.getString("date", "").split("amr");
                String vot[] = pref.getString("vot", "").split("amr");
                String posers[] = pref.getString("poster", "").split("amr");
                String titels[] = pref.getString("titel", "").split("amr");
                String overs[] = pref.getString("over", "").split("amr");


                for (int i = 0; i < overs.length; i++) {

                    Movie_model = new Model();
                    Movie_model.setPoster(posers[i]);
                    Movie_model.setOverview(overs[i]);
                    Movie_model.setOrignal_title(titels[i]);
                    Movie_model.setRelase_date(dates[i]);
                    Movie_model.setVote_average(Double.parseDouble(vot[i]));
                    mGridData.add(Movie_model);
                }


                mGridAdapter.setGridData(mGridData);


            }


        }
    }

    public class JsonTask1 extends AsyncTask<String, String, Integer> {


        @Override
        protected Integer doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("results");


                // review =new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    String auther = finalObject.getString("author");
                    String content = finalObject.getString("content");

                    review.add(auther + content);
                }


                Movie_model = new Model();
                Movie_model = mGridData.get(Postion);

                Movie_model.setReview(review);
                mGridData.set(Postion, Movie_model);
                return 1;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 1) {


            } else {

                Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_SHORT).show();


            }


        }


    }

    public class JsonTask2 extends AsyncTask<String, String, Integer> {


        @Override
        protected Integer doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("results");


                // review =new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    String key = finalObject.getString("key");
                    String base = "https://www.youtube.com/watch?v=";

                    tailler.add(base+key);
                }


//                Movie_model = new Model();
//                Movie_model=mGridData.get(Postion);
//
//                Movie_model.setReview(review);
//                mGridData.set(Postion, Movie_model);
                return 1;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {

                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 1) {



            } else {

                Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_SHORT).show();


            }


        }


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            mGridData.clear();
            new JsonTask().execute("http://api.themoviedb.org/3/movie/popular?api_key=473af073aa2961cdedc82165fffc3985");
            return true;
        } else if (id == R.id.top_rated) {
            mGridData.clear();
            new JsonTask().execute("http://api.themoviedb.org/3/movie/top_rated?api_key=473af073aa2961cdedc82165fffc3985");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
