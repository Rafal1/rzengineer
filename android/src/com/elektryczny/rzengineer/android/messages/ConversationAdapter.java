package com.elektryczny.rzengineer.android.messages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.elektryczny.rzengineer.android.R;

@SuppressLint("SimpleDateFormat")
public class ConversationAdapter extends BaseAdapter {

    private ArrayList<Message> _data;
    Context _c;

    ConversationAdapter(ArrayList<Message> data, Context c) {
        _data = data;
        _c = c;
    }

    public int getCount() {
        return _data.size();
    }

    public Object getItem(int position) {
        return _data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.convarsation_message_layout, null);
        }

        ImageView image = (ImageView) v.findViewById(R.id.icon);
        TextView fromView = (TextView) v.findViewById(R.id.From);
        TextView descView = (TextView) v.findViewById(R.id.description);
        TextView timeView = (TextView) v.findViewById(R.id.time);

        Message msg = _data.get(position);
        image.setImageResource(R.drawable.ic_launcher);
        fromView.setText(msg.getFrom());
        descView.setText(msg.getContent());
        timeView.setText(dateToString(msg.getDateSent()));
        return v;
    }

    private static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        String s = sdf.format(date);
        return s;
    }
}