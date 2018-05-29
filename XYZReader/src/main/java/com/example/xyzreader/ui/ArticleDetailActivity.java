package com.example.xyzreader.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xyzreader.R;
import com.example.xyzreader.data.Article;
import com.squareup.picasso.Picasso;

/**
 * An activity representing a single Article detail screen, letting you swipe between articles.
 */
public class ArticleDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_ITEM = "item";
    public static final String KEY_ITEM_ID = "item_id";
    public static final String KEY_SCROLL_POSITION = "scroll_y";

    private Article mArticle;

    private CollapsingToolbarLayout mToolbarLayout;
    private AppCompatTextView mTitleView;
    private TextView mDateView;
    private TextView mAuthorView;
    private TextView mTextView = (TextView) findViewById(R.id.details_text_tv);
    private ImageView mImageView;
    private ImageButton mShareFab;
    private NestedScrollView mScrollContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        final Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Bundle bundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        mArticle = bundle.getParcelable(KEY_ITEM);

        mToolbarLayout = findViewById(R.id.collapsing_toolbar);
        mTitleView = findViewById(R.id.details_title_tv);
        mDateView = findViewById(R.id.details_date_tv);
        mAuthorView = findViewById(R.id.details_author_tv);
        mImageView = findViewById(R.id.details_image_iv);
        mShareFab = findViewById(R.id.share_fab);
        mScrollContainer = findViewById(R.id.detail_scroll_container);

        populateViews();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollToYPercentage(mScrollContainer, bundle.getFloat(KEY_SCROLL_POSITION, 0));
                mScrollContainer.setVisibility(View.GONE);
                mScrollContainer.setVisibility(View.VISIBLE);
            }
        }, 10);
    }

    private void populateViews() {
        mShareFab.setOnClickListener(this);
        mTitleView.setText(mArticle.getTitle());
        mAuthorView.setText(mArticle.getAuthor());
        mDateView.setText(DateUtils.getRelativeTimeSpanString(
                mArticle.getPublishedDate().getTime(),
                System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS));
        mTextView.setText(Html.fromHtml(mArticle.getBody()));

        Picasso.get().load(mArticle.getPhotoUrl()).into(mImageView);

        mToolbarLayout.setTitle(mTitleView.getText());
        mToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(getResources().getColor(android.R.color.transparent)));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_ITEM, mArticle);
        outState.putFloat(KEY_SCROLL_POSITION, getYPercentage(mScrollContainer));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_fab:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TITLE, mArticle.getTitle());
                shareIntent.putExtra(Intent.EXTRA_TEXT, mArticle.getBody());

                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_using)));
                break;
        }
    }

    private static void scrollToYPercentage(NestedScrollView scrollView, float relativeY) {
        int height = scrollView.getChildAt(0).getHeight();
        scrollView.scrollTo(0, (int) (relativeY * height));
    }

    private static float getYPercentage(NestedScrollView scrollView) {
        return (float) scrollView.getScrollY() / scrollView.getChildAt(0).getHeight();
    }
}