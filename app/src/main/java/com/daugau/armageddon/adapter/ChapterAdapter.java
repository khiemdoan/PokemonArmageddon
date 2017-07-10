package com.daugau.armageddon.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.daugau.armageddon.R;
import com.daugau.armageddon.model.Chapter;

import java.util.List;

/**
 * Created by Khiem on 3/27/2017.
 */

public class ChapterAdapter extends ArrayAdapter<Chapter> {

    Context context;
    int resource;

    public ChapterAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Chapter> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View row = inflater.inflate(resource, null);

        Chapter chapter = getItem(position);
        TextView txtTitleItem = (TextView) row.findViewById(R.id.txtTitleItem);

        if (chapter != null && txtTitleItem != null) {
            String title = chapter.getTitle();
            txtTitleItem.setText(title);
        }

        return row;
    }
}
