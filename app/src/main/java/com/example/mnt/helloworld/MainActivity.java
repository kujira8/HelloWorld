package com.example.mnt.helloworld;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_PERMISSION = 1000;

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

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickWriteSDBtnEvent(v);
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
        getSDData();
    }

    // SDカード書き込み処理
    private void clickWriteSDBtnEvent(View v){
        TextView varTextView = findViewById(R.id.textView);
        varTextView.setText("ボタン2が押されたよ");

        // Android 6, API 23以上でパーミッシンの確認
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermission();
        } else {
            writeSDData();
        }
    }

    private void writeSDData(){

        TextView varTextView = findViewById(R.id.textView);
        String str = "test";
        String fileName = "testFile.txt";
        String text;

        // 現在ストレージが書き込みできるかチェック
        if(!isExternalStorageWritable()) new AlertDialog.Builder(this).setMessage("SDカードが必要です").setPositiveButton("OK", null).show();

        String filePath = Environment.getExternalStorageDirectory().getPath() + "/"+fileName;
        File file = new File(filePath);
        file.getParentFile().mkdir();

        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            BufferedWriter bw = new BufferedWriter(outputStreamWriter);

            bw.write(str);
            bw.flush();
            bw.close();
            outputStreamWriter.close();
            fileOutputStream.close();
            text = "saved";
        } catch (Exception e) {
            text = "error: FileOutputStream";
            e.printStackTrace();
        }
        varTextView.setText(text);
    }

    private void getSDData(){

        String TAG = "READ_FILES";
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        String fileName = "testFile.txt";

        // 現在ストレージが読み込みできるかチェック
        if(!isExternalStorageReadable()) new AlertDialog.Builder(this).setMessage("SDカードが必要です").setPositiveButton("OK", null).show();

        String filePath = Environment.getExternalStorageDirectory().getPath() + "/"+fileName;

        try {
            inputStream = new FileInputStream(filePath);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                if(line != "") Log.d(TAG, line);
                TextView varTextView = findViewById(R.id.textView);
                varTextView.setText(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(this, "ファイルの読込みに失敗しました。", Toast.LENGTH_SHORT);
            toast.show();
        }  catch (Exception e) {
            e.printStackTrace();
          Toast toast = Toast.makeText(this, "ファイルの読込みに失敗しました。", Toast.LENGTH_SHORT);
            toast.show();
        } finally {
            try {
                reader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
   }

    // Checks if external storage is available for read and write
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    // Checks if external storage is available to at least read
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    // permissionの確認
    public void checkPermission() {
        // 既に許可している
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED){
            writeSDData();
        }
        // 拒否していた場合
        else{
            requestLocationPermission();
        }
    }

    // 許可を求める
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

        } else {
            Toast toast =
                    Toast.makeText(this, "アプリ実行に許可が必要です", Toast.LENGTH_SHORT);
            toast.show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, REQUEST_PERMISSION);

        }
    }

    // 結果の受け取り
    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                writeSDData();
            } else {
                // それでも拒否された時の対応
                Toast toast = Toast.makeText(this, "何もできません", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
