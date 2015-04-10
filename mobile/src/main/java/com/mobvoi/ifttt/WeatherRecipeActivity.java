package com.mobvoi.ifttt;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mobvoi.ifttt.action.Action;
import com.mobvoi.ifttt.trigger.Trigger;


public class WeatherRecipeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_recipe);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("雾霾指数通知");
        }

        ImageButton button = (ImageButton) findViewById(R.id.add_weather_recipe_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe.Model recipeModel = new Recipe.Model();
                recipeModel.action = new Action.Model();
                recipeModel.action.desc = "{}";
                recipeModel.action.type = "notification";
                recipeModel.trigger = new Trigger.Model();
                recipeModel.trigger.desc = "{}";
                recipeModel.trigger.type = "weather";
                RecipeManager.getInstance().addRecipe(recipeModel);

                Toast.makeText(WeatherRecipeActivity.this, "Recipe added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
