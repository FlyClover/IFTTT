package com.mobvoi.ifttt;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;


public class RecipeListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Intent intent = getIntent();

        ImageView imageView = (ImageView) findViewById(R.id.header_pic);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            switch (intent.getStringExtra(RecipeCategoryActivity.EXTRA_RECIPE_CATEGORY)) {
                case RecipeCategoryActivity.CATEGORY_MOVIE:
                    ab.setTitle("电影 Movie");
                    imageView.setImageResource(R.drawable.pic_list_2);
                    setupMovieCardList();
                    break;
                case RecipeCategoryActivity.CATEGORY_TRAFFIC:
                    ab.setTitle("路况 Traffic");
                    imageView.setImageResource(R.drawable.traffic);
                    setupTrafficCardList();
                    break;
                default:
                    ab.setTitle("天气 Weather");
                    imageView.setImageResource(R.drawable.pic_list_1);
                    setupWeatherCardList();
                    break;
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    private void setupMovieCardList() {
        Card.OnCardClickListener listener = new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Intent intent = new Intent(view.getContext(), WeatherRecipeActivity.class);
                startActivity(intent);
            }
        };

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(getRecipeCard(R.drawable.list_m_1, listener));
        cards.add(getRecipeCard(R.drawable.list_m_2, listener));
        cards.add(getRecipeCard(R.drawable.list_m_3, listener));

        CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(this, cards);

        //Staggered grid view
        CardRecyclerView mRecyclerView = (CardRecyclerView) findViewById(R.id.recipe_list);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCardArrayAdapter);
    }

    private void setupWeatherCardList() {
        Card.OnCardClickListener listener = new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Intent intent = new Intent(view.getContext(), WeatherRecipeActivity.class);
                startActivity(intent);
            }
        };

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(getRecipeCard(R.drawable.list_w_3, listener));
        cards.add(getRecipeCard(R.drawable.list_w_4, listener));
        cards.add(getRecipeCard(R.drawable.list_w_2, listener));
        cards.add(getRecipeCard(R.drawable.list_w_1, listener));

        CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(this, cards);

        //Staggered grid view
        CardRecyclerView mRecyclerView = (CardRecyclerView) findViewById(R.id.recipe_list);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCardArrayAdapter);
    }

    private void setupTrafficCardList() {
        Card.OnCardClickListener listener = new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Intent intent = new Intent(view.getContext(), TrafficRecipeActivity.class);
                startActivity(intent);
            }
        };

        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(getRecipeCard(R.drawable.list_t_1, listener));
        cards.add(getRecipeCard(R.drawable.list_t_2, listener));

        CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(this, cards);

        //Staggered grid view
        CardRecyclerView mRecyclerView = (CardRecyclerView) findViewById(R.id.recipe_list);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCardArrayAdapter);
    }

    private Card getRecipeCard(int resourceId, Card.OnCardClickListener listener) {
        Card card = new Card(this);
        card.setBackgroundColorResourceId(R.color.background);
        CardThumbnail thumbnail = new CardThumbnail(this);
        thumbnail.setDrawableResource(resourceId);
        card.addCardThumbnail(thumbnail);
        card.addPartialOnClickListener(Card.CLICK_LISTENER_ALL_VIEW, listener);

        return card;
    }
}
