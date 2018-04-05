package com.example.shams.moviestageone;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    /*

    */


    private final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        //

        Log.d(TAG, "onCreate: ");

        TabLayout tabLayout = findViewById(R.id.tl_movie_details_tab_id);

        ViewPager viewPager = findViewById(R.id.vp_movie_details_tab_id);

        MovieDetailsFragmentAdapter movieDetailsFragmentAdapter
                = new MovieDetailsFragmentAdapter(getSupportFragmentManager());
        tabLayout.setupWithViewPager(viewPager,true);

        viewPager.setAdapter(movieDetailsFragmentAdapter);

       // tabLayout.setupWithViewPager(viewPager);


/*

    }



}
*/
    }
}
