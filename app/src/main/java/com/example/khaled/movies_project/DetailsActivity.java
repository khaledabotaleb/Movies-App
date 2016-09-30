package com.example.khaled.movies_project;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by Khaled on 21/09/2016.
 */
public class DetailsActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);



       // Toast.makeText(getBaseContext(),"activity",Toast.LENGTH_LONG).show();
        ArrayList<String> Review =new ArrayList<>();
        ArrayList<String> Tailler =new ArrayList<>();
        Intent data =this.getIntent();
        String Title=data.getExtras().getString("title");
        String Poster_url=data.getExtras().getString("Poster");
        String Release_date=data.getExtras().getString("Release");
        String overview=data.getExtras().getString("overview");
         Review =data.getExtras().getStringArrayList("review");
        Tailler =data.getExtras().getStringArrayList("tailler");




        //  Toast.makeText(getBaseContext(),MainFragment.review.get(0), Toast.LENGTH_LONG).show();
//
        Bundle bundle=new Bundle();
        bundle.putString("title", Title);
       bundle.putString("Poster", Poster_url);
       bundle.putString("Release", Release_date);
        bundle.putString("overview", overview);
        bundle.putStringArrayList("review", MainFragment.review);
        bundle.putStringArrayList("tailler", MainFragment.tailler);




        //  set Fragmentclass Arguments
       DetailsFragment fragobj=new DetailsFragment();
        fragobj.setArguments(bundle);
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        FragmentTransaction add = fragmentTransaction.add(R.id.detailcontainer, fragobj);

        add.commit();
//
//        if(bundle!=null)
//            Log.d("amr","ahmed");
/*


        TextView texttitle=(TextView)findViewById(R.id.titleView);
        texttitle.setText(Title);

        ImageView Poster=(ImageView) findViewById(R.id.posterView);
        Picasso.with(this)
                .load(Poster_url)
                .into(Poster);

        TextView Release_Text=(TextView)findViewById(R.id.releaseView);
        Release_Text.setText(Release_date);

        TextView overview_Text=(TextView)findViewById(R.id.overView);
        overview_Text.setText(overview);
*/


     //   Toast.makeText(getBaseContext(),Title,Toast.LENGTH_LONG).show();


    }

}
