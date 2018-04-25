package com.example.mnt.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickReadSDBtnEvent(v);
            }
        });
    }

    // アクションバーを表示するメソッド
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //main.xmlの内容を読み込む
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    // オプションメニューのアイテムが選択されたときに呼び出されるメソッド
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        TextView varTextView = (TextView) findViewById(R.id.textView);
        TextView varTextView = findViewById(R.id.textView);
        switch (item.getItemId()) {
            case R.id.item1:
                varTextView.setText(R.string.menu_item1);
                return true;
            case R.id.item2:
                varTextView.setText(R.string.menu_item2);
                return true;
            case R.id.item3:
                varTextView.setText(R.string.menu_item3);
                return true;
            case R.id.item4:
                varTextView.setText(R.string.menu_item4);
                return true;
            case R.id.item5:
                varTextView.setText(R.string.menu_item5);
                return true;
            case R.id.item6:
                varTextView.setText(R.string.menu_item6);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // SDカード読み込み処理
    private void clickReadSDBtnEvent(View v){
        TextView varTextView = findViewById(R.id.textView);
        varTextView.setText("ボタンが押されたよ");
    }
}
