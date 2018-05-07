package com.example.mnt.helloworld;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private final String uri = "http://api.atnd.org/events/?keyword=android&format=json";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // 先ほどのレイアウトをここでViewとして作成します
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        HttpResponsAsync httpResponsAsync = new HttpResponsAsync(new HttpResponsAsync.AsyncCallback() {
            // 実行前
            public void preExecute() {
            }
            // 実行後
            // resultにはdoInBackgroundの返り値が入る
            public void postExecute(String result) {
                // ここからAsyncTask処理後の処理を記述する
                if (result == null) {
                    showLoadError(); // エラーメッセージを表示
                    return;
                }
                // JSONへのパース部分
//                try {
//                    // 各 ATND イベントのタイトルを配列へ格納
//                    ArrayList<String> list = new ArrayList<>();
//                    JSONArray eventArray = result.getJSONArray("events");
//                    for (int i = 0; i < eventArray.length(); i++) {
//                        JSONObject eventObj = eventArray.getJSONObject(i);
//                        JSONObject event = eventObj.getJSONObject("event");
//                        //Log.d("title", event.getString("title"));
//                        list.add(event.getString("title"));
//                    }
//                    // ListView 用のアダプタを作成
//                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
//                            getActivity(), android.R.layout.simple_list_item_1, list
//                    );
//                    // ListView にアダプタをセット
//                    ListView listView = (ListView)getActivity().findViewById(R.id.listView);
//                    listView.setAdapter(arrayAdapter);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    showLoadError(); // エラーメッセージを表示
//                }
            }
            // 実行中
            public void progressUpdate(int progress) {
            }
            // キャンセル
            public void cancel() {
            }
        });
        // 処理を実行
        httpResponsAsync.execute(uri);
    }

    // エラーメッセージ表示
    private void showLoadError() {
        Toast toast = Toast.makeText(getActivity(), "データを取得できませんでした。", Toast.LENGTH_SHORT);
        toast.show();
    }
}
