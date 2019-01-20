package com.daugau.pokearmageddon;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DatabaseHelper(this).copyDatabase();
    }

    public void readContinue(View v) {
        Intent intent = new Intent(this, ChapterActivity.class);
        intent.putExtra(ChapterActivity.CHAPTER_ID, 1);
        intent.putExtra(ChapterActivity.CONTINUE_READ, true);
        startActivity(intent);
    }

    public void viewChapterList(View v) {
        Intent intent = new Intent(this, ListChapterActivity.class);
        intent.putExtra(ChapterActivity.CHAPTER_ID, 1);
        intent.putExtra(ChapterActivity.CONTINUE_READ, false);
        startActivity(intent);
    }

    public void visitFanPage(View v) {
        String FACEBOOK_PAGE_ID = "PokemonTheoristsVN";
        String FACEBOOK_URL = "https://www.facebook.com/" + FACEBOOK_PAGE_ID;

        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl;
        try {
            PackageManager packageManager = getPackageManager();
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                facebookUrl = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                facebookUrl = "fb://page/" + FACEBOOK_PAGE_ID;
            }
            facebookIntent.setData(Uri.parse(facebookUrl));
            startActivity(facebookIntent);
        } catch (Exception ex) {
            facebookUrl = FACEBOOK_URL; //normal web url
        }

        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);
    }

    public void exit(View v) {
        finish();
    }

}
