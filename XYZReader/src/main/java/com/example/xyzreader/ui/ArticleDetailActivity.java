package com.example.xyzreader.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.xyzreader.R;

/**
 * An activity representing a single Article detail screen, letting you swipe between articles.
 */
public class ArticleDetailActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private long mSelectedItemId;
    private ImageButton mUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        mUpButton = (ImageButton) findViewById(R.id.action_up);
        mUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportNavigateUp();
            }
        });

        Bundle bundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        mSelectedItemId = bundle.getLong(ArticleDetailFragment.ARG_ITEM_ID);

        fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(ArticleDetailFragment.TAG) == null) {
            showFragment();
        }
    }

    private void showFragment() {
        Fragment fragment = ArticleDetailFragment.newInstance(mSelectedItemId);
        fragmentManager.beginTransaction().replace(R.id.article_details_container, fragment, ArticleDetailFragment.TAG).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ArticleDetailFragment.ARG_ITEM_ID, mSelectedItemId);
    }
}