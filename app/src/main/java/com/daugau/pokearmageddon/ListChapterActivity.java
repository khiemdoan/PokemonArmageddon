package com.daugau.pokearmageddon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.daugau.pokearmageddon.adapter.ChapterAdapter;
import com.daugau.pokearmageddon.model.Chapter;
import java.util.ArrayList;

public class ListChapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chapter);

        addControls();
        addEvents();
    }

    ListView lvChapters;

    private void addControls() {
        setTitle("Mục lục");

        lvChapters = findViewById(R.id.lstChapters);

        DatabaseHelper data = new DatabaseHelper(this);
        ArrayList<Chapter> chapters = data.getListTitle();

        ChapterAdapter adapter = new ChapterAdapter(this, R.layout.item_list_chapter, chapters);
        lvChapters.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private void addEvents() {
        lvChapters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chapter chapter = (Chapter) parent.getAdapter().getItem(position);
                Intent intent = new Intent(ListChapterActivity.this, ChapterActivity.class);
                intent.putExtra(ChapterActivity.CHAPTER_ID, chapter.getId());
                intent.putExtra(ChapterActivity.CONTINUE_READ, false);
                startActivity(intent);
            }
        });
    }
}
