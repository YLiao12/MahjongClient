package hk.edu.cuhk.ie.group23;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.versionedparcelable.ParcelField;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GameActivity extends AppCompatActivity {

    private Socket socket;
    Map<Integer, Mahjong> mahjongMap = new ConcurrentHashMap<>();
    // Handler handler;

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            TextView winText = (TextView) findViewById(R.id.win_text);
            TextView mahjongNumText = (TextView) findViewById(R.id.mahjong_num);
            Button startButton = (Button) findViewById(R.id.game_start);
            Button readyButton = (Button) findViewById(R.id.ready);
            ListView mahjongListView = (ListView) findViewById(R.id.mahjongList);
            switch (msg.what) {
                case 0:
                    MahjongAdapter mahjongAdapter = new MahjongAdapter(GameActivity.this, R.layout.mahjong_item, (List) msg.obj);
                    mahjongListView.setAdapter(mahjongAdapter);
                    TextView playing_order_text = (TextView) findViewById(R.id.playing_order);
                    StringBuffer playerSb = new StringBuffer();
                    playerSb.append("Now it's player");
                    playerSb.append(msg.arg1);
                    playerSb.append("'s turn");
                    playing_order_text.setText(playerSb.toString());

                    StringBuffer mjSb = new StringBuffer();
                    mjSb.append(msg.arg2);
                    mjSb.append(" mahjongs left");
                    mahjongNumText.setText(mjSb.toString());
                    mahjongNumText.setVisibility(View.VISIBLE);

                    startButton.setVisibility(View.GONE);
                    winText.setVisibility(View.GONE);
                    break;
                case 1:
                    TextView player_order = (TextView) findViewById(R.id.player_order);
                    StringBuffer orderSb = new StringBuffer();
                    orderSb.append("You're player");
                    orderSb.append(msg.arg2);
                    player_order.setText(orderSb.toString());
                    readyButton.setVisibility(View.GONE);
                    startButton.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    startButton.setVisibility(View.VISIBLE);
                    playerMahjongList.clear();
                    MahjongAdapter mahjongWinAdapter = new MahjongAdapter(GameActivity.this, R.layout.mahjong_item, playerMahjongList);
                    mahjongListView.setAdapter(mahjongWinAdapter);
                    ImageView mj_played = (ImageView) findViewById(R.id.mj_played);
                    mj_played.setVisibility(View.GONE);
                    StringBuffer sb = new StringBuffer();
                    sb.append("player ");
                    sb.append(msg.arg1);
                    sb.append(" has won!");
                    winText.setText(sb.toString());
                    winText.setVisibility(View.VISIBLE);
                    mahjongNumText.setText("Mahjong left");
                    break;
                case 3:
                    break;
            }
        }
    };

    private class PlayedMjHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            ImageView mj_played = (ImageView) findViewById(R.id.mj_played);
            // mj_played.setVisibility(View.VISIBLE);
            int mjId = (Integer) msg.arg1;
            Mahjong mahjong = mahjongMap.get(mjId);
            //mj = mahjongMap.get(mjId);
            if(mahjong.getName().equals("1w"))
                mj_played.setImageResource(R.drawable.hai_00_wan_1_bottom);
            if(mahjong.getName().equals("2w"))
                mj_played.setImageResource(R.drawable.hai_01_wan_2_bottom);
            if(mahjong.getName().equals("3w"))
                mj_played.setImageResource(R.drawable.hai_02_wan_3_bottom);
            if(mahjong.getName().equals("4w"))
                mj_played.setImageResource(R.drawable.hai_03_wan_4_bottom);
            if(mahjong.getName().equals("5w"))
                mj_played.setImageResource(R.drawable.hai_04_wan_5_bottom);
            if(mahjong.getName().equals("6w"))
                mj_played.setImageResource(R.drawable.hai_05_wan_6_bottom);
            if(mahjong.getName().equals("7w"))
                mj_played.setImageResource(R.drawable.hai_06_wan_7_bottom);
            if(mahjong.getName().equals("8w"))
                mj_played.setImageResource(R.drawable.hai_07_wan_8_bottom);
            if(mahjong.getName().equals("9w"))
                mj_played.setImageResource(R.drawable.hai_08_wan_9_bottom);
            //mj_played.setImageResource(R.drawable.hai_08_wan_9_bottom);
            if(mahjong.getName().equals("1l"))
                mj_played.setImageResource(R.drawable.hai_18_sou_1_bottom);
            if(mahjong.getName().equals("2l"))
                mj_played.setImageResource(R.drawable.hai_19_sou_2_bottom);
            if(mahjong.getName().equals("3l"))
                mj_played.setImageResource(R.drawable.hai_20_sou_3_bottom);
            if(mahjong.getName().equals("4l"))
                mj_played.setImageResource(R.drawable.hai_21_sou_4_bottom);
            if(mahjong.getName().equals("5l"))
                mj_played.setImageResource(R.drawable.hai_22_sou_5_bottom);
            if(mahjong.getName().equals("6l"))
                mj_played.setImageResource(R.drawable.hai_23_sou_6_bottom);
            if(mahjong.getName().equals("7l"))
                mj_played.setImageResource(R.drawable.hai_24_sou_7_bottom);
            if(mahjong.getName().equals("8l"))
                mj_played.setImageResource(R.drawable.hai_25_sou_8_bottom);
            if(mahjong.getName().equals("9l"))
                mj_played.setImageResource(R.drawable.hai_26_sou_9_bottom);
            if(mahjong.getName().equals("1t"))
                mj_played.setImageResource(R.drawable.hai_09_pin_1_bottom);
            if(mahjong.getName().equals("2t"))
                mj_played.setImageResource(R.drawable.hai_10_pin_2_bottom);
            if(mahjong.getName().equals("3t"))
                mj_played.setImageResource(R.drawable.hai_11_pin_3_bottom);
            if(mahjong.getName().equals("4t"))
                mj_played.setImageResource(R.drawable.hai_12_pin_4_bottom);
            if(mahjong.getName().equals("5t"))
                mj_played.setImageResource(R.drawable.hai_13_pin_5_bottom);
            if(mahjong.getName().equals("6t"))
                mj_played.setImageResource(R.drawable.hai_14_pin_6_bottom);
            if(mahjong.getName().equals("7t"))
                mj_played.setImageResource(R.drawable.hai_15_pin_7_bottom);
            if(mahjong.getName().equals("8t"))
                mj_played.setImageResource(R.drawable.hai_16_pin_8_bottom);
            if(mahjong.getName().equals("9t"))
                mj_played.setImageResource(R.drawable.hai_17_pin_9_bottom);
            mj_played.setVisibility(View.VISIBLE);
        }
    }

    MyHandler handler;
    PlayedMjHandler mjHandler;
//    ButtonHandler btnHandler;

    int player_order;
    int playOrder;
    List<Mahjong> playerMahjongList;
    int mahjongNum;
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

        handler = new MyHandler();
        mjHandler = new PlayedMjHandler();
//        btnHandler = new ButtonHandler();

        TextView mjNumText = (TextView) findViewById(R.id.mahjong_num);
        // mjNumText.setVisibility(View.GONE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ListView mahjongList = (ListView) findViewById(R.id.mahjongList);
        ImageView mj_played = (ImageView) findViewById(R.id.mj_played);
        mj_played.setVisibility(View.GONE);

        TextView winText = (TextView) findViewById(R.id.win_text);
        winText.setVisibility(View.GONE);

        Button startGameButton = (Button) findViewById(R.id.game_start);
        startGameButton.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();

        // String playerId = extras.getString("playerId");
        int tableId = extras.getInt("tableId");
        int playerNum = extras.getInt("playerNum");

        try {
            socket = IO.socket("http://34.92.209.154:8085/");
            socket.on(Socket.EVENT_CONNECT, onConnectSuccess);
            socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            socket.on("start_game", startGame);
            socket.on("mj", mjShow);
            socket.on("next", nextMjDisplay);
            socket.on("win", winBroadcast);
            JSONObject data = new JSONObject();
            data.put("room", tableId);
            data.put("playerNum", playerNum);
            socket.emit("join", data);
            socket.connect();
        } catch (URISyntaxException | JSONException e) {
            e.printStackTrace();
        }

        mahjongList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle extras = getIntent().getExtras();

                if (playOrder == player_order) {
                    int tableId = extras.getInt("tableId");
                    Mahjong mahjong = (Mahjong) mahjongList.getAdapter().getItem(i);
                    playerMahjongList.remove(mahjong);
                    mahjongNum --;

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
                                int mj_id = json.getInt("mj");

                                JSONObject nextData = new JSONObject();
                                nextData.put("mj", mj_id);
                                nextData.put("order", playOrder);
                                nextData.put("room", tableId);
                                nextData.put("mj_num", mahjongNum);

                                socket.emit("next", nextData);
                                // socket.on("next", nextMjDisplay);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    try {

                        // socket.on("mj", mjShow);

                        JSONObject data = new JSONObject();
                        data.put("mj", mahjong.getId());
                        data.put("room", tableId);
                        socket.emit("mj", data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = playerMahjongList;
                    message.arg1 = playOrder;
                    message.arg2 = player_order;
                    handler.sendMessage(message);
                }
            }
        });
    }

    private Emitter.Listener onConnectSuccess = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            System.out.println("Connect Success!");
        }
    };

    private Emitter.Listener mjShow = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            Message message = Message.obtain();
            message.what = 0;
            message.arg1 = (Integer) args[0];
            mjHandler.sendMessage(message);
            System.out.println(message.arg1);
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            System.out.println("Connect Error");
        }
    };

    private Emitter.Listener winBroadcast = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Message message = Message.obtain();
            message.what = 2;
            JSONObject json = (JSONObject) args[0];
            try {
                message.arg1 = json.getInt("player_order");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            handler.sendMessage(message);
        }
    };

    private Emitter.Listener nextMjDisplay = new Emitter.Listener() {

        @Override
        public void call(Object... args) {

            // get mj and order from args
            // update mjListView
            JSONObject json = (JSONObject) args[0];
            try {
                playOrder = json.getInt("order");
                int mj_id = json.getInt("mj");
                mahjongNum = json.getInt("mj_num");
                Mahjong newMj = mahjongMap.get(mj_id);
                if (player_order == playOrder) {
                    playerMahjongList.add(newMj);
                    //sort
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        playerMahjongList.sort(new Comparator<Mahjong>() {
                            @Override
                            public int compare(Mahjong o1, Mahjong o2) {
                                return o1.getId() < o2.getId()? -1 : o1.getId() == o1.getId() ? 0: 1;
                            }
                        });
                    }
                }
                Message message = Message.obtain();
                message.what = 0;
                message.obj = playerMahjongList;
                message.arg1 = playOrder;
                message.arg2 = mahjongNum;

                handler.sendMessage(message);
                // playerMahjongList.add(newMj);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };

    private Emitter.Listener startGame = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            StringBuffer orderKeyBuffer = new StringBuffer();
            orderKeyBuffer.append("player");
            orderKeyBuffer.append(player_order);
            String orderKey = orderKeyBuffer.toString();

            JSONObject json = (JSONObject) args[0];
            JSONArray playerArray = null;
            try {
                playerArray = json.getJSONArray(orderKey);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                playOrder = json.getInt("order");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            List<Integer> playerList = new ArrayList<>();
            playerMahjongList = new ArrayList<>();
            if (playerArray !=null) {
                for (int i = 0; i < playerArray.length(); i++) {
                    try {
                        playerList.add(playerArray.getInt(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //playerMahjongList.add(mahjongMap.get(playerArray.getInt(i)));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    playerList.sort(Comparator.naturalOrder());
                }
                for (int i = 0; i < playerList.size(); i++) {
                    playerMahjongList.add(mahjongMap.get(playerList.get(i)));
                }

                mahjongNum = 55;

                Message message = Message.obtain();
                message.what = 0;
                message.obj = playerMahjongList;
                message.arg1 = playOrder;
                message.arg2 = 55;
                handler.sendMessage(message);
            }
        }
    };

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
        int tableId = extras.getInt("tableId");
        mahjongNum = 55;
        //okhttp 异步post
        FormBody.Builder Builder = new FormBody.Builder()
                .add("table_id", String.valueOf(tableId));
        RequestBody FormBody=Builder.build();
        OkHttpClient Client = new OkHttpClient();
        Request Request = new Request.Builder()
                .url("http://34.92.209.154/game/start_game")
                .post(FormBody)
                .build();

        Client.newCall(Request).enqueue(new Callback() {
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
                    json.put("room", tableId);
                    socket.emit("start_game", json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                body.close();
            }
        });
    }

    public void ready(View view) {
        //generate player order
        Bundle extras = getIntent().getExtras();

        String playerId = extras.getString("playerId");
        int tableId = extras.getInt("tableId");

        //okhttp 获取order
        FormBody.Builder builder = new FormBody.Builder()
                .add("table_id", String.valueOf(tableId))
                .add("player_id", playerId);
        RequestBody formBody = builder.build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://34.92.209.154/game/get_order")
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
                if(json !=null) {
                    try {
                        player_order = json.getInt("player_order");
                        // System.out.println(playerArray.length());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Message message = Message.obtain();
                    message.what = 1;
                    message.arg2 = player_order;
                    handler.sendMessage(message);

                    body.close();
                }
            }
        });
    }

    public void win(View view) {
        Bundle extras = getIntent().getExtras();
        // String playerId = extras.getString("playerId");
        int tableId = extras.getInt("tableId");

        try {
            JSONObject data = new JSONObject();
            data.put("room", tableId);
            data.put("player_order", player_order);
            socket.emit("win", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        FormBody.Builder builder = new FormBody.Builder()
                .add("table_id", String.valueOf(tableId));
        RequestBody formBody=builder.build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://34.92.209.154/game/win")
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
    }
}