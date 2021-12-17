package hk.edu.cuhk.ie.mahjongclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        setContentView(R.layout.activity_main);
    }

    public void to_tables(View view) {

        // 获取玩家Name
        EditText text = (EditText) findViewById(R.id.playerIdEditText);
        String playerName = text.getText().toString();
        String playerId = UUID.randomUUID().toString().replaceAll("-", "");;

        //okhttp 异步post玩家id，存入数据库
        FormBody.Builder builder = new FormBody.Builder()
                .add("player_name", playerName)
                .add("player_id", playerId);
        RequestBody formBody=builder.build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://34.92.209.154/mj/create_player")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
            }
        });

        //跳转至选择麻将桌页面
        Intent intent = new Intent(MainActivity.this, TableActivity.class);
        intent.putExtra("playerName", playerName);
        intent.putExtra("playerId", playerId);
        startActivity(intent);
    }
}