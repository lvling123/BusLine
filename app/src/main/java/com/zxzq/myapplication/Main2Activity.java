package com.zxzq.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;


public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog dialog;
    String url;
    private TextView mTv_place;
    private TextView mTv_time;
    private ImageView mIv_recyle;
    private ImageView mIv_back;
    private RecyclerView mRv_show;
    private TextView mTv_title;
    private ShowBean.ResultBean mResultBean;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String city = getIntent().getStringExtra("city");
        String line = getIntent().getStringExtra("line");
        url = "http://op.juhe.cn/189/bus/busline?key=6f0e2a5d983cd6045f11eb0086eb5b3c&dtype=json&city=" + city + "&bus=" + line;
        initView();
        initData();
    }

    private void initView() {
        mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_place = (TextView) findViewById(R.id.tv_place);
        mTv_time = (TextView) findViewById(R.id.tv_time);
        mIv_recyle = (ImageView) findViewById(R.id.iv_recyle);
        mIv_recyle.setOnClickListener(this);
        mIv_back = (ImageView) findViewById(R.id.iv_back);
        mIv_back.setOnClickListener(this);
        mRv_show = (RecyclerView) findViewById(R.id.rv_show);
        mTv_time.setVisibility(View.INVISIBLE);
        mTv_place.setVisibility(View.INVISIBLE);
        mTv_title.setVisibility(View.INVISIBLE);

    }


    private void initData() {
        dialog = ProgressDialog.show(this, null, " 加载中，请稍候。。。 ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = Manager.loadJson(url);
                Gson gson = new Gson();
                ShowBean showBean = gson.fromJson(json, ShowBean.class);
                final List<ShowBean.ResultBean> result = showBean.getResult();
                if (result != null) {
                    mResultBean = result.get(0);
                    final StringBuffer startTime = new StringBuffer(mResultBean.getStart_time());
                    startTime.insert(2, ':');
                    final StringBuffer endTime = new StringBuffer(mResultBean.getEnd_time());
                    endTime.insert(2, ':');
                    final List<ShowBean.ResultBean.StationdesBean> stationdes = mResultBean.getStationdes();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTv_time.setVisibility(View.VISIBLE);
                            mTv_place.setVisibility(View.VISIBLE);
                            mTv_title.setVisibility(View.VISIBLE);
                            mTv_title.setText(mResultBean.getKey_name() + "详情");
                            mTv_place.setText(mResultBean.getFront_name() + "-" + mResultBean.getTerminal_name());
                            mTv_time.setText("首 " + new StringBuffer(startTime.toString() + " 末 " + endTime.toString() + " 全程 " + mResultBean.getLength().split("\\.")[0] + " 公里"));
                            MyAdapter myAdapter = new MyAdapter(stationdes);
                            myAdapter.setOnItemClickListener(new MyAdapter.MyItemClickListener() {
                                @Override
                                public void onItemClick(View view, int postion) {
                                    Toast.makeText(Main2Activity.this, stationdes.get(postion).getName(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            mRv_show.setAdapter(myAdapter);
                            mRv_show.setLayoutManager(new LinearLayoutManager(Main2Activity.this, LinearLayoutManager.VERTICAL, false));
                            dialog.dismiss();
                            mRv_show.setVisibility(View.VISIBLE);
                        }
                    });


                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Intent intent = new Intent(Main2Activity.this, Main3Activity.class);
                            startActivity(intent);
                        }
                    });

                }
            }
        }).start();


    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_back:
                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_recyle:
                if (i % 2 == 0) {
                    mTv_place.setText(mResultBean.getTerminal_name() + "-" + mResultBean.getFront_name());
                    mRv_show.setLayoutManager(new LinearLayoutManager(Main2Activity.this, LinearLayoutManager.VERTICAL, true));
                    i++;
                } else {
                    mTv_place.setText(mResultBean.getFront_name() + "-" + mResultBean.getTerminal_name());
                    mRv_show.setLayoutManager(new LinearLayoutManager(Main2Activity.this, LinearLayoutManager.VERTICAL, false));
                    i++;
                }
                break;

        }
    }

}
