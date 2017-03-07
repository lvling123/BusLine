package com.zxzq.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mBt_search;
    private EditText mEt_city;
    private EditText mEt_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEt_city = (EditText) findViewById(R.id.et_city);
        mEt_line = (EditText) findViewById(R.id.et_line);
        mBt_search = (Button) findViewById(R.id.bt_search);
        mBt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEt_city.getText().toString().equals("")||mEt_line.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "city或者line不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("city",mEt_city.getText().toString());
                    bundle.putString("line",mEt_line.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

}
