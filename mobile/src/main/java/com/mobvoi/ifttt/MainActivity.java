package com.mobvoi.ifttt;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mobvoi.ifttt.action.Action;
import com.mobvoi.ifttt.trigger.Trigger;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecipeManager.init(this);
        Recipe.Model recipeModel = new Recipe.Model();
        recipeModel.action = new Action.Model();
        recipeModel.action.desc = "{}";
        recipeModel.action.type = "notification";
        recipeModel.trigger = new Trigger.Model();
        recipeModel.trigger.desc = "{}";
        recipeModel.trigger.type = "weather";
        RecipeManager.getInstance().addRecipe(recipeModel);
        RecipeManager.getInstance().startCheck();
//
//        RecipeManager.getInstance().getRecipes();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
