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

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("天气 Weather");
        }

        ImageView imageView = (ImageView) findViewById(R.id.header_pic);
        imageView.setImageResource(R.drawable.pic_list_1);

        setupCardList();
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

    private void setupCardList() {
        ArrayList<Card> cards = new ArrayList<Card>();

        cards.add(getRecipeCard(R.drawable.list_w_4));
        cards.add(getRecipeCard(R.drawable.list_w_3));
        cards.add(getRecipeCard(R.drawable.list_w_2));
        cards.add(getRecipeCard(R.drawable.list_w_1));

        CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(this, cards);

        //Staggered grid view
        CardRecyclerView mRecyclerView = (CardRecyclerView) findViewById(R.id.recipe_list);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCardArrayAdapter);
    }

    private Card getRecipeCard(int resourceId) {
        Card card = new Card(this);
        card.setBackgroundColorResourceId(R.color.background);
        CardThumbnail thumbnail = new CardThumbnail(this);
        thumbnail.setDrawableResource(resourceId);
        card.addCardThumbnail(thumbnail);
        card.addPartialOnClickListener(Card.CLICK_LISTENER_ALL_VIEW, new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Intent intent = new Intent(view.getContext(), WeatherRecipeActivity.class);
                startActivity(intent);
            }
        });

        return card;
    }
}
