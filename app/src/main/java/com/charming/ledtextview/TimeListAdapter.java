package com.charming.ledtextview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by 56223 on 2016/11/15.
 */

public class TimeListAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mTimeData;

    public TimeListAdapter(Context context, List<String> timeData) {
        mContext = context;
        mTimeData = timeData;
    }

    @Override
        public int getCount() {
        return mTimeData.size();
    }

    @Override
    public Object getItem(int i) {
        return mTimeData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_time_list, null, false);
        LedTextView recordedText = (LedTextView) view.findViewById(R.id.recorded_time);
        recordedText.setText(getItem(i).toString());
        return view;
    }

}
