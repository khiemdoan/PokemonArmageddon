package com.daugau.armageddon;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(CHAPTER_ID, id);
            editor.apply();
        }

        addControls();
        addEvent();
    }

    private int id = 1;
    private final String CORE_TEMPLATE = "<html><body style=\"text-align:justify;\">%s</body></html>";

    private void addControls() {
        WebView webView = (WebView) findViewById(R.id.wvContent);

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        DatabaseHelper data = new DatabaseHelper(this);
        Chapter chapter = data.getChapter(id);

        String html = chapter.getContent();
        webView.loadData(String.format(CORE_TEMPLATE, html), "text/html; charset=utf-8", "UTF-8");
    }

    private void addEvent() {

    }
}
