package com.charming.ledtextview;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends Activity {

    private int mCount = 0;
    private long mDuration;
    private boolean isStart = false;
    private List<String> mTimeData = new ArrayList();
    private TimeListAdapter mAdapter;
    private TextView mLedText;
    private ListView mTimeList;
    private Animator mBtnAppearing;
    private Animator mLedDisappearing;
    private Animation mAnimation;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (isStart) {
                    mDuration += 100;
                    if (mCount < 10) {
                        mCount++;
                    } else {
                        mCount = 0;
                    }
                    mHandler.sendEmptyMessageDelayed(1, 100);
                }
                SimpleDateFormat format = new SimpleDateFormat(getString(R.string.time_format) + mCount);
                String time = format.format(new Date(mDuration));
                mLedText.setText(time);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mBtnAppearing = AnimatorInflater.loadAnimator(this, R.animator.btn_appear);
        mLedDisappearing = AnimatorInflater.loadAnimator(this, R.animator.led_item_disappear);
        mLedDisappearing.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mTimeData.remove(0);
                mTimeData.add(mLedText.getText().toString());
                mAdapter.notifyDataSetChanged();
            }
        });
        mLedText = (TextView) findViewById(R.id.time);
        mLedText.setText(getString(R.string.time_init));
        mTimeList = (ListView) findViewById(R.id.time_list);
        mAdapter = new TimeListAdapter(this, mTimeData);
        mTimeList.setAdapter(mAdapter);
        final View grp1 = findViewById(R.id.grp1);
        final View grp2 = findViewById(R.id.grp2);
        final Button start = (Button) findViewById(R.id.btn_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setVisibility(View.GONE);
                grp1.setVisibility(View.VISIBLE);
                mBtnAppearing.setTarget(grp1);
                mBtnAppearing.start();
                isStart = true;
                mHandler.sendEmptyMessage(1);
            }
        });

        Button count = (Button) findViewById(R.id.btn_count);
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimeData.size() > 7) {
                    mLedDisappearing.setTarget(mTimeList.getChildAt(0));
                    mLedDisappearing.start();
                    return;
                }
                mTimeData.add(mLedText.getText().toString());
                mAdapter.notifyDataSetChanged();
            }
        });

        Button pause = (Button) findViewById(R.id.btn_pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grp1.setVisibility(View.GONE);
                grp2.setVisibility(View.VISIBLE);
                mBtnAppearing.setTarget(grp2);
                mBtnAppearing.start();
                isStart = false;
                mHandler.sendEmptyMessage(1);
            }
        });

        Button reset = (Button) findViewById(R.id.btn_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grp2.setVisibility(View.GONE);
                start.setVisibility(View.VISIBLE);
                mBtnAppearing.setTarget(start);
                mBtnAppearing.start();
                isStart = false;
                mDuration = 0;
                mTimeData.clear();
                mAdapter.notifyDataSetChanged();
                mLedText.setText(getString(R.string.time_init));
            }
        });

        Button resume = (Button) findViewById(R.id.btn_resume);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grp2.setVisibility(View.GONE);
                grp1.setVisibility(View.VISIBLE);
                mBtnAppearing.setTarget(grp1);
                mBtnAppearing.start();
                isStart = true;
                mHandler.sendEmptyMessage(1);
            }
        });
    }

}
