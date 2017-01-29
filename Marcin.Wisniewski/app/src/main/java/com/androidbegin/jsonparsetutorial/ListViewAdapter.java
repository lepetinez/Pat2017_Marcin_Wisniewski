package com.androidbegin.jsonparsetutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

class ListViewAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<HashMap<String, String>> data;
    private final ImageLoader imageLoader;

    ListViewAdapter(Context context,
                    ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView title;
        TextView desc;
        ImageView url;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_item, parent, false);
        HashMap<String, String> resultp = data.get(position);
        title = (TextView) itemView.findViewById(R.id.title);
        desc = (TextView) itemView.findViewById(R.id.desc);
        url = (ImageView) itemView.findViewById(R.id.url);
        title.setText(resultp.get(LoggedActivity.TITLE));
        desc.setText(resultp.get(LoggedActivity.DESC));
        imageLoader.DisplayImage(resultp.get(LoggedActivity.URL), url);
        return itemView;
    }
}
