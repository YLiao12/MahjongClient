package hk.edu.cuhk.ie.mahjongclient;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TableActivity extends AppCompatActivity {

    private String playerName;
    private String playerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Bundle extras = getIntent().getExtras();
        playerName = extras.getString("playerName");
        playerId = extras.getString("playerId");

        // 将playerId显示到这个页面
        TextView helloToPlayer = (TextView) findViewById(R.id.hello_To_Player);
        StringBuffer sb = new StringBuffer();
        sb.append("Hello, ");
        sb.append(playerName);
        sb.append("! Please choose your table or create a new one: ");
        helloToPlayer.setText(sb.toString());

        // 调用 /get_tables API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()       //创建一个请求
                .url("http://34.92.209.154/mj/get_tables")
                .get()  //表明为get请求
                .build();
        Call call=client.newCall(request);      //创建一个Call
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<Table> tableList = new ArrayList<Table>();
                String data = response.body().string();
                JSONObject json = null;
                try {
                    json = new JSONObject(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String arrayString = json.getString("data");
                    JSONArray array = JSONArray.parseArray(arrayString);
                    for (int i = 0; i < array.size(); i++) {
                        String tableName = array.getJSONObject(i).getObject("Table_name", String.class);
                        int tableId = array.getJSONObject(i).getObject("Table_id", Integer.class);
                        int playersNum = array.getJSONObject(i).getObject("players_num", Integer.class);
                        Table newTable = new Table(tableId, tableName, playersNum);
                        tableList.add(newTable);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ListView tableListView = (ListView) findViewById(R.id.tableList);
                TableAdapter chatroomAdapter = new TableAdapter(TableActivity.this, R.layout.table_item, tableList);
                tableListView.setAdapter(chatroomAdapter);
            }
        });

        ListView tableListView = (ListView) findViewById(R.id.tableList);
        tableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 通过getAdapter()方法取得MyAdapter对象，再调用getItem(int)返回一个Data对象
                Intent intent = new Intent(TableActivity.this, GameActivity.class);
                Table table = (Table) tableListView.getAdapter().getItem(i);
                intent.putExtra("tableName", table.getTableName());
                intent.putExtra("tableId", table.getTableId());
                intent.putExtra("playerId", playerId);
                intent.putExtra("playerName", playerName);
                RequestBody formBody = new FormBody.Builder()
                        .add("table_id", String.valueOf(table.getTableId()))
                        .add("player_id", playerId)
                        .build();

                Request request = new Request.Builder()
                        .url("http://34.92.209.154/mj/coming_into_tables")
                        .post(formBody)
                        .build();

                Call call = client.newCall(request);
                call.enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                    }
                });

                startActivity(intent);
            }
        });

    }
}