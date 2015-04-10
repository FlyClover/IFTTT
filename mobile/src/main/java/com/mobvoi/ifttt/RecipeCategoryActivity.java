package com.mobvoi.ifttt;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.software.shell.fab.ActionButton;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;


public class RecipeCategoryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_category);

        setupCardList();

        ActionButton actionButton = (ActionButton) findViewById(R.id.action_button);
        if (actionButton != null) {
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), IfThenActivity.class);
                    startActivity(intent);
                }
            });
        }

        RecipeManager.init(this);
        RecipeManager.getInstance().startCheck();
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

    private void setupCardList() {
        ArrayList<Card> cards = new ArrayList<Card>();

        cards.add(getCard(R.drawable.pic_3));
        cards.add(getCard(R.drawable.pic_2));
        cards.add(getCard(R.drawable.pic_1));

        CardArrayRecyclerViewAdapter mCardArrayAdapter = new CardArrayRecyclerViewAdapter(this, cards);

        //Staggered grid view
        CardRecyclerView mRecyclerView = (CardRecyclerView) findViewById(R.id.recipe_category_list);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCardArrayAdapter);
    }

    private Card getCard(int resourceId) {
        MaterialLargeImageCard card =
                MaterialLargeImageCard.with(this)
                        .useDrawableId(resourceId)
                        .build();
        card.setOnClickListener(new Card.OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Intent intent = new Intent(view.getContext(), RecipeListActivity.class);
                startActivity(intent);
            }
        });

        return card;
    }
}
