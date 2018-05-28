package com.example.xyzreader.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.xyzreader.R;
import com.example.xyzreader.data.Article;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * An activity representing a single Article detail screen, letting you swipe between articles.
 */
public class ArticleDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_ITEM = "item";
    public static final String KEY_ITEM_ID = "item_id";

    private Article mArticle;

    private CollapsingToolbarLayout mToolbarLayout;
    private TextView mTitleView;
    private TextView mDateView;
    private TextView mAuthorView;
    private TextView mTextView;
    private ImageView mImageView;
    private ImageButton mShareFab;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
    private SimpleDateFormat outputFormat = new SimpleDateFormat();
    private GregorianCalendar START_OF_EPOCH = new GregorianCalendar(2, 1, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        mArticle = bundle.getParcelable(KEY_ITEM);

        mToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mTitleView = (TextView) findViewById(R.id.details_title_tv);
        mDateView = (TextView) findViewById(R.id.details_date_tv);
        mAuthorView = (TextView) findViewById(R.id.details_author_tv);
        mTextView = (TextView) findViewById(R.id.details_text_tv);
        mImageView = (ImageView) findViewById(R.id.details_image_iv);
        mShareFab = (ImageButton) findViewById(R.id.share_fab);

        populateViews();
    }

    private void populateViews() {
        mShareFab.setOnClickListener(this);
        mTitleView.setText(mArticle.getTitle());
        mAuthorView.setText(mArticle.getAuthor());
        mDateView.setText(DateUtils.getRelativeTimeSpanString(
                mArticle.getPublishedDate().getTime(),
                System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS));
        mTextView.setText(Html.fromHtml(mArticle.getBody()));
        fetchAndLoadArticleImage();

        mToolbarLayout.setTitle(mTitleView.getText());
        mToolbarLayout.setExpandedTitleTextColor(ColorStateList.valueOf(getResources().getColor(android.R.color.transparent)));

        Typeface tf = Typeface.createFromAsset(getAssets(), getString(R.string.font_roboto_slab));
        mTitleView.setTypeface(tf);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_ITEM, mArticle);
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

    private void fetchAndLoadArticleImage() {
        ImageLoaderHelper.getInstance(this).getImageLoader()
                .get(mArticle.getPhotoUrl(), new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                        Bitmap bitmap = imageContainer.getBitmap();
                        if (bitmap != null) {
                            mImageView.setImageBitmap(imageContainer.getBitmap());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(getClass().getSimpleName(), volleyError.getMessage());
                    }
                });
    }
}