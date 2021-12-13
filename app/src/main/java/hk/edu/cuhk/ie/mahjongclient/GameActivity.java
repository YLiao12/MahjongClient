package hk.edu.cuhk.ie.mahjongclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


    }

    public void to_tables(View view) {

        Bundle extras = getIntent().getExtras();
        int playerId = extras.getInt("playerId");
        int tableId = extras.getInt("tableId");
        String playerName = extras.getString("playerName");

        //okhttp 异步post玩家id，存入数据库
        FormBody.Builder builder = new FormBody.Builder()
                .add("player_id", String.valueOf(playerId))
                .add("table_id", String.valueOf(tableId));
        RequestBody formBody=builder.build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://34.92.209.154/mj/leaving_tables")
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
        Intent intent = new Intent(GameActivity.this, TableActivity.class);
        intent.putExtra("playerId", playerName);
        startActivity(intent);
    }
}