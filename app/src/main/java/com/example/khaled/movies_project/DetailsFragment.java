package com.example.khaled.movies_project;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by khaled on 22/09/2016.
 */
public class DetailsFragment extends Fragment {
    static ArrayList<String> Tailler =new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.details_fragment, container, false);
        Bundle bundle = getArguments();

        String Title = bundle.getString("title");

        String Poster_url = bundle.getString("Poster");
        String Release_date = bundle.getString("Release");
        String overview = bundle.getString("overview");
        ArrayList<String> Review =new ArrayList<>();
        Review =bundle.getStringArrayList("review");
        Tailler =bundle.getStringArrayList("tailler");
       //  Toast.makeText(getActivity(), Tailler.get(0), Toast.LENGTH_LONG).show();



        final ListView listview = (ListView) view.findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, Review);
        listview.setAdapter(adapter);

        final ListView listview2 = (ListView) view.findViewById(R.id.listView2);
         ArrayAdapter adapter2 = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, Tailler);
        listview2.setAdapter(adapter2);
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(DetailsFragment.Tailler.get(position)));
                startActivity(i);

            }
        });




        TextView texttitle = (TextView) view.findViewById(R.id.titleView);
        texttitle.setText(Title);

        ImageView Poster = (ImageView) view.findViewById(R.id.posterView);
        Picasso.with(getActivity())
                .load(Poster_url)
                .into(Poster);

        TextView Release_Text = (TextView) view.findViewById(R.id.releaseView);
        Release_Text.setText(Release_date);

        TextView overview_Text = (TextView) view.findViewById(R.id.overView);

        overview_Text.setText(overview);



        return view;
    }
}
