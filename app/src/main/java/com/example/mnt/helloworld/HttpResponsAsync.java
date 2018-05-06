package com.example.mnt.helloworld;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpResponsAsync extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // doInBackground前処理
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // doInBackground後処理
    }

    @Override
    protected String doInBackground(String... _uri) {
        HttpURLConnection con = null;
        URL url = null;
        String urlSt = "http://sample.jp";
        String resultSt = null;

        try {
            // URLの作成
            url = new URL(_uri[0]);
            // 接続用HttpURLConnectionオブジェクト作成
            con = (HttpURLConnection)url.openConnection();
            // リクエストメソッドの設定
            con.setRequestMethod("POST");
            // リダイレクトを自動で許可しない設定
            con.setInstanceFollowRedirects(false);
            // URL接続からデータを読み取る場合はtrue
            con.setDoInput(true);
            // URL接続にデータを書き込む場合はtrue
            con.setDoOutput(true);

            // 接続
            con.connect();
            // 本文の取得
            InputStream in = con.getInputStream();
            // "bodyByte"が本文のbody部分です
            // このbodyをWebブラウザで表示させることができます。
//            byte bodyByte[] = new byte[1024];
//            in.read(bodyByte);
            resultSt = readInputStream(in);
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultSt;
    }

    // JSONを取得する
    private String readInputStream(InputStream in) throws IOException, UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        String st = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        while((st = br.readLine()) != null) sb.append(st);

        try {
            in.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
