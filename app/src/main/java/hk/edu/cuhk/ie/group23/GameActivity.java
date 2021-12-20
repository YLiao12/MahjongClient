package hk.edu.cuhk.ie.group23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GameActivity extends AppCompatActivity {

    Map<Integer, Mahjong> mahjongMap = new HashMap<>();
    // Handler handler;

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            ListView mahjongListView = (ListView) findViewById(R.id.mahjongList);
            MahjongAdapter mahjongAdapter = new MahjongAdapter(GameActivity.this, R.layout.mahjong_item, (List) msg.obj);
            mahjongListView.setAdapter(mahjongAdapter);
        }
    };

    MyHandler handler;

    int player_order;
    int playOrder;
    List<Mahjong> playerMahjongList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //w: 万 l: 条 t: 筒
        mahjongMap.put(1, new Mahjong(1, "1w"));
        mahjongMap.put(2, new Mahjong(2, "1w"));
        mahjongMap.put(3, new Mahjong(3, "1w"));
        mahjongMap.put(4, new Mahjong(4, "1w"));
        mahjongMap.put(5, new Mahjong(5, "2w"));
        mahjongMap.put(6, new Mahjong(6, "2w"));
        mahjongMap.put(7, new Mahjong(7, "2w"));
        mahjongMap.put(8, new Mahjong(8, "2w"));
        mahjongMap.put(9, new Mahjong(9, "3w"));
        mahjongMap.put(10, new Mahjong(10, "3w"));
        mahjongMap.put(11, new Mahjong(11, "3w"));
        mahjongMap.put(12, new Mahjong(12, "3w"));
        mahjongMap.put(13, new Mahjong(13, "4w"));
        mahjongMap.put(14, new Mahjong(14, "4w"));
        mahjongMap.put(15, new Mahjong(15, "4w"));
        mahjongMap.put(16, new Mahjong(16, "4w"));
        mahjongMap.put(17, new Mahjong(17, "5w"));
        mahjongMap.put(18, new Mahjong(18, "5w"));
        mahjongMap.put(19, new Mahjong(19, "5w"));
        mahjongMap.put(20, new Mahjong(20, "5w"));
        mahjongMap.put(21, new Mahjong(21, "6w"));
        mahjongMap.put(22, new Mahjong(22, "6w"));
        mahjongMap.put(23, new Mahjong(23, "6w"));
        mahjongMap.put(24, new Mahjong(24, "6w"));
        mahjongMap.put(25, new Mahjong(25, "7w"));
        mahjongMap.put(26, new Mahjong(26, "7w"));
        mahjongMap.put(27, new Mahjong(27, "7w"));
        mahjongMap.put(28, new Mahjong(28, "7w"));
        mahjongMap.put(29, new Mahjong(29, "8w"));
        mahjongMap.put(30, new Mahjong(30, "8w"));
        mahjongMap.put(31, new Mahjong(31, "8w"));
        mahjongMap.put(32, new Mahjong(32, "8w"));
        mahjongMap.put(33, new Mahjong(33, "9w"));
        mahjongMap.put(34, new Mahjong(34, "9w"));
        mahjongMap.put(35, new Mahjong(35, "9w"));
        mahjongMap.put(36, new Mahjong(36, "9w"));
        mahjongMap.put(37, new Mahjong(37, "1l"));
        mahjongMap.put(38, new Mahjong(38, "1l"));
        mahjongMap.put(39, new Mahjong(39, "1l"));
        mahjongMap.put(40, new Mahjong(40, "1l"));
        mahjongMap.put(41, new Mahjong(41, "2l"));
        mahjongMap.put(42, new Mahjong(42, "2l"));
        mahjongMap.put(43, new Mahjong(43, "2l"));
        mahjongMap.put(44, new Mahjong(44, "2l"));
        mahjongMap.put(45, new Mahjong(45, "3l"));
        mahjongMap.put(46, new Mahjong(46, "3l"));
        mahjongMap.put(47, new Mahjong(47, "3l"));
        mahjongMap.put(48, new Mahjong(48, "3l"));
        mahjongMap.put(49, new Mahjong(49, "4l"));
        mahjongMap.put(50, new Mahjong(50, "4l"));
        mahjongMap.put(51, new Mahjong(51, "4l"));
        mahjongMap.put(52, new Mahjong(52, "4l"));
        mahjongMap.put(53, new Mahjong(53, "5l"));
        mahjongMap.put(54, new Mahjong(54, "5l"));
        mahjongMap.put(55, new Mahjong(55, "5l"));
        mahjongMap.put(56, new Mahjong(56, "5l"));
        mahjongMap.put(57, new Mahjong(57, "6l"));
        mahjongMap.put(58, new Mahjong(58, "6l"));
        mahjongMap.put(59, new Mahjong(59, "6l"));
        mahjongMap.put(60, new Mahjong(60, "6l"));
        mahjongMap.put(61, new Mahjong(61, "7l"));
        mahjongMap.put(62, new Mahjong(62, "7l"));
        mahjongMap.put(63, new Mahjong(63, "7l"));
        mahjongMap.put(64, new Mahjong(64, "7l"));
        mahjongMap.put(65, new Mahjong(65, "8l"));
        mahjongMap.put(66, new Mahjong(66, "8l"));
        mahjongMap.put(67, new Mahjong(67, "8l"));
        mahjongMap.put(68, new Mahjong(68, "8l"));
        mahjongMap.put(69, new Mahjong(69, "9l"));
        mahjongMap.put(70, new Mahjong(70, "9l"));
        mahjongMap.put(71, new Mahjong(71, "9l"));
        mahjongMap.put(72, new Mahjong(72, "9l"));
        mahjongMap.put(73, new Mahjong(73, "1t"));
        mahjongMap.put(74, new Mahjong(74, "1t"));
        mahjongMap.put(75, new Mahjong(75, "1t"));
        mahjongMap.put(76, new Mahjong(76, "1t"));
        mahjongMap.put(77, new Mahjong(77, "2t"));
        mahjongMap.put(78, new Mahjong(78, "2t"));
        mahjongMap.put(79, new Mahjong(79, "2t"));
        mahjongMap.put(80, new Mahjong(80, "2t"));
        mahjongMap.put(81, new Mahjong(81, "3t"));
        mahjongMap.put(82, new Mahjong(82, "3t"));
        mahjongMap.put(83, new Mahjong(83, "3t"));
        mahjongMap.put(84, new Mahjong(84, "3t"));
        mahjongMap.put(85, new Mahjong(85, "4t"));
        mahjongMap.put(86, new Mahjong(86, "4t"));
        mahjongMap.put(87, new Mahjong(87, "4t"));
        mahjongMap.put(88, new Mahjong(88, "4t"));
        mahjongMap.put(89, new Mahjong(89, "5t"));
        mahjongMap.put(90, new Mahjong(90, "5t"));
        mahjongMap.put(91, new Mahjong(91, "5t"));
        mahjongMap.put(92, new Mahjong(92, "5t"));
        mahjongMap.put(93, new Mahjong(93, "6t"));
        mahjongMap.put(94, new Mahjong(94, "6t"));
        mahjongMap.put(95, new Mahjong(95, "6t"));
        mahjongMap.put(96, new Mahjong(96, "6t"));
        mahjongMap.put(97, new Mahjong(97, "7t"));
        mahjongMap.put(98, new Mahjong(98, "7t"));
        mahjongMap.put(99, new Mahjong(99, "7t"));
        mahjongMap.put(100, new Mahjong(100, "7t"));
        mahjongMap.put(101, new Mahjong(101, "8t"));
        mahjongMap.put(102, new Mahjong(102, "8t"));
        mahjongMap.put(103, new Mahjong(103, "8t"));
        mahjongMap.put(104, new Mahjong(104, "8t"));
        mahjongMap.put(105, new Mahjong(105, "9t"));
        mahjongMap.put(106, new Mahjong(106, "9t"));
        mahjongMap.put(107, new Mahjong(107, "9t"));
        mahjongMap.put(108, new Mahjong(108, "9t"));

//        handler = new Handler() {
//
//            @Override
//            public void handleMessage(Message msg) {
//                ListView mahjongListView = (ListView) findViewById(R.id.mahjongList);
//                MahjongAdapter mahjongAdapter = new MahjongAdapter(GameActivity.this, R.layout.mahjong_item, (List) msg.obj);
//                mahjongListView.setAdapter(mahjongAdapter);
//                // mahjongListView.setRotation(-90);
//            }
//        };
        handler = new MyHandler();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ListView mahjongList = (ListView) findViewById(R.id.mahjongList);

        mahjongList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 通过getAdapter()方法取得MyAdapter对象，再调用getItem(int)返回一个Data对象
                // Intent intent = new Intent(TableActivity.this, GameActivity.class);

                Bundle extras = getIntent().getExtras();

                if (playOrder == player_order) {
                    int tableId = extras.getInt("tableId");
                    Mahjong mahjong = (Mahjong) mahjongList.getAdapter().getItem(i);
                    playerMahjongList.remove(mahjong);

                    RequestBody formBody = new FormBody.Builder()
                            .add("mj", String.valueOf(mahjong.getId()))
                            .add("player_order", String.valueOf(player_order))
                            .add("table_id", String.valueOf(tableId))
                            .build();

                    Request request = new Request.Builder()
                            .url("http://34.92.209.154/game/next")
                            .post(formBody)
                            .build();
                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            ResponseBody body = response.body();
                            String data = body.string();
                            JSONObject json = null;
                            try {
                                json = new JSONObject(data);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                playOrder = json.getInt("order");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = playerMahjongList;
                    handler.sendMessage(message);
                }
            }
        });
    }

    public void to_tables(View view) {

        Bundle extras = getIntent().getExtras();

        String playerId = extras.getString("playerId");
        int tableId = extras.getInt("tableId");
        String playerName = extras.getString("playerName");

        //okhttp 异步post
        FormBody.Builder builder = new FormBody.Builder()
                .add("player_id", playerId)
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
                String data = body.string();
                body.close();
            }
        });

        //跳转至选择麻将桌页面
        Intent intent = new Intent(GameActivity.this, TableActivity.class);
        intent.putExtra("playerName", playerName);
        intent.putExtra("playerId", playerId);
        startActivity(intent);
    }

    public void game_start(View view) {

        Bundle extras = getIntent().getExtras();

        // String playerId = extras.getString("playerId");
        int tableId = extras.getInt("tableId");
        int order = extras.getInt("playerNum");
        player_order = order + 1;
        StringBuffer orderKeyBuffer = new StringBuffer();
        orderKeyBuffer.append("player");
        orderKeyBuffer.append(player_order);
        String orderKey = orderKeyBuffer.toString();


        //okhttp 异步post
        FormBody.Builder builder = new FormBody.Builder()
                .add("table_id", String.valueOf(tableId));
        RequestBody formBody=builder.build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://34.92.209.154/game/start_game")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                String data = body.string();
                JSONObject json = null;
                try {
                    json = new JSONObject(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray playerArray = json.getJSONArray(orderKey);
                    playOrder = json.getInt("order");
                    List<Integer> playerList = new ArrayList<>();
                    playerMahjongList = new ArrayList<>();
                    for (int i = 0; i < playerArray.length(); i++) {
                        playerList.add(playerArray.getInt(i));
                        //playerMahjongList.add(mahjongMap.get(playerArray.getInt(i)));
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        playerList.sort(Comparator.naturalOrder());
                    }
                    for (int i = 0; i < playerList.size(); i++) {
                        playerMahjongList.add(mahjongMap.get(playerList.get(i)));
                    }

                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = playerMahjongList;
                    handler.sendMessage(message);

                    // System.out.println(playerArray.length());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                body.close();
            }
        });

        Button startButton = (Button) findViewById(R.id.game_start);
        startButton.setVisibility(View.GONE);
    }
}