package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;


    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ArrayList<String> alsoKnownAsList;
    TextView alsoKnownAsTV, originTV, descriptionTV, ingredientsTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout =(TabLayout) findViewById(R.id.tab);
        appBarLayout= (AppBarLayout) findViewById(R.id.appbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager());
        //adding fragments
        adapter.AddFragment(new about(),"ABOUT");
        adapter.AddFragment(new ingredients(),"INGREDIENTS");
        adapter.AddFragment(new description(),"DESCRIPTION");
        //adapter setup
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //get the references
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        originTV= findViewById(R.id.origin_tv);
        alsoKnownAsTV= findViewById(R.id.also_known_tv);
        ingredientsTV= findViewById(R.id.ingredients_tv);
        descriptionTV= findViewById(R.id.description_tv);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.ic_error_loading_image)
                .into(ingredientsIv);


        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        try {
            originTV.setText(sandwich.getPlaceOfOrigin());

            // Convert the ArrayList into a String and set the text
            String res = TextUtils.join(",", sandwich.getAlsoKnownAs());
            alsoKnownAsTV.setText(res);
            String ingredientsString = TextUtils.join(",", sandwich.getIngredients());
            ingredientsTV.setText(ingredientsString);
            descriptionTV.setText(sandwich.getDescription());

        }

        catch (NullPointerException e){
            System.out.println("Not Available");
        }
    }

    public boolean OnOptionsItemSelected(MenuItem item){
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(intent,0);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
