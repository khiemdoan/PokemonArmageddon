package com.daugau.armageddon;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.daugau.armageddon.model.Chapter;

public class ChapterActivity extends AppCompatActivity {

    private static final String CHAPTER_INFO = "chapter";
    public static final String CHAPTER_ID = "chapter_id";
    public static final String CONTINUE_READ = "continue_read";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        boolean contRead = getIntent().getExtras().getBoolean(CONTINUE_READ, true);

        SharedPreferences preferences = getSharedPreferences(CHAPTER_INFO, MODE_PRIVATE);
        if (contRead) {
            id = preferences.getInt(CHAPTER_ID, 1);
        } else {
            id = getIntent().getExtras().getInt(CHAPTER_ID, 1);
        }

        addControls();
        addEvent();
    }

    private int id = 1;
    private final String CORE_TEMPLATE = "<html><body style=\"text-align:justify;\">%s</body></html>";
    private CustomWebView webView;

    private void addControls() {
        webView = findViewById(R.id.wvContent);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        loadContent();
    }

    private void addEvent() {
        webView.setGestureDetector(new GestureDetector(new CustomGestureDetector()));
    }

    private void loadContent() {
        DatabaseHelper data = new DatabaseHelper(this);
        Chapter chapter = data.getChapter(id);

        setTitle(chapter.getTitle());
        String html = chapter.getContent();
        webView.loadData(
                String.format(CORE_TEMPLATE, html),
                "text/html; charset=utf-8",
                "UTF-8");

        SharedPreferences preferences = getSharedPreferences(CHAPTER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(CHAPTER_ID, id);
        editor.apply();
    }

    private class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1 == null || e2 == null)
                return false;

            if(e1.getPointerCount() > 1 || e2.getPointerCount() > 1)
                return false;

            if (Math.abs(velocityX) > 800) {

                // right to left swipe .. go to next page
                if (e1.getX() - e2.getX() > 100) {
                    DatabaseHelper database = new DatabaseHelper(ChapterActivity.this);
                    int maxId = database.getMaxId();
                    if (id < maxId) {
                        id++;
                        loadContent();
                    }
                    return true;
                }

                // left to right swipe .. go to prev page
                if (e2.getX() - e1.getX() > 100) {
                    if (id > 1) {
                        id--;
                        loadContent();
                    }
                    return true;
                }
            }

            return false;
        }
    }
}

class CustomWebView extends WebView {

    private GestureDetector gestureDetector;

    public CustomWebView(Context context) {
        super(context);
    }


    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gestureDetector.onTouchEvent(ev) || super.onTouchEvent(ev);
    }

    public void setGestureDetector(GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
    }
}