package com.mobvoi.ifttt;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnkaZhao<kei@mobvoi.com>
 * @date 2015年04月11日
 */
public class RecipeManager {
    private static final String TAG = "RecipeManager";
    public static RecipeManager sInstance = null;
    private static Context mContext = null;
    private static DBHelper mDbHelper = null;

    public static void init(Context context) {
        mContext = context;
        MobvoiSender.init(context);
    }

    public synchronized static RecipeManager getInstance() {
        if (null == sInstance) {
            sInstance = new RecipeManager();
        }
        return sInstance;
    }

    public RecipeManager() {
        if (null == mContext) {
            Log.e(TAG, "RecipeManager is not initialized");
        }
        mDbHelper = new DBHelper(mContext);
        mDbHelper.deleteAllRecipe();
    }

    public void deleteAll() {
        mDbHelper.deleteAllRecipe();
    }

    public void startCheck() {
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                    }
                    List<Recipe> recipes = getRecipes();
                    Log.i(TAG, "start check recipe, size=" + recipes.size());
                    for (Recipe recipe : recipes) {
                        recipe.check();
                    }
                }
            }
        }.start();
    }

    public void addRecipe(Recipe.Model recipe) {
        if (recipe.trigger.id == 0) {
            recipe.trigger.id = mDbHelper.addTrigger(recipe.trigger);
            Log.i(TAG, "inserted trigger id=" + recipe.trigger.id);
        }
        if (recipe.action.id == 0) {
            recipe.action.id = mDbHelper.addAction(recipe.action);
            Log.i(TAG, "inserted action id=" + recipe.trigger.id);
        }
        if (recipe.trigger.id != 0 && recipe.action.id != 0) {
            mDbHelper.addRecipe(recipe.trigger.id, recipe.action.id);
        }
    }

    public List<Recipe> getRecipes() {
        List<Recipe.Model> recipeModels = mDbHelper.getRecipes();
//        Log.i(TAG, "recipe size=" + recipeModels.size());
//        for (Recipe.Model model : recipeModels) {
//            Log.i(TAG, "recipe=" + model.trigger.type + "," + model.trigger.desc + "," + model.action.type
//            + "," + model.action.desc);
//        }
        List<Recipe> recipes = new ArrayList<>();
        for (Recipe.Model model : recipeModels) {
            try {
                Recipe recipe = new Recipe();
                recipe.loadRecipe(model.trigger, model.action);
                recipes.add(recipe);
            } catch (Exception e) {
                Log.w(TAG, e);
            }
        }
        return recipes;
    }

}
