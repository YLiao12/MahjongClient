package hk.edu.cuhk.ie.group23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class MahjongAdapter extends ArrayAdapter<Mahjong> {
    private int resourceId;

    public MahjongAdapter(Context context, int resource, List<Mahjong> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Mahjong mahjong = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.image = view.findViewById(R.id.mahjongImg);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        //if id =
        if(mahjong.getName().equals("1w"))
            viewHolder.image.setImageResource(R.drawable.hai_00_wan_1_bottom);
        if(mahjong.getName().equals("2w"))
            viewHolder.image.setImageResource(R.drawable.hai_01_wan_2_bottom);
        if(mahjong.getName().equals("3w"))
            viewHolder.image.setImageResource(R.drawable.hai_02_wan_3_bottom);
        if(mahjong.getName().equals("4w"))
            viewHolder.image.setImageResource(R.drawable.hai_03_wan_4_bottom);
        if(mahjong.getName().equals("5w"))
            viewHolder.image.setImageResource(R.drawable.hai_04_wan_5_bottom);
        if(mahjong.getName().equals("6w"))
            viewHolder.image.setImageResource(R.drawable.hai_05_wan_6_bottom);
        if(mahjong.getName().equals("7w"))
            viewHolder.image.setImageResource(R.drawable.hai_06_wan_7_bottom);
        if(mahjong.getName().equals("8w"))
            viewHolder.image.setImageResource(R.drawable.hai_07_wan_8_bottom);
        if(mahjong.getName().equals("9w"))
            viewHolder.image.setImageResource(R.drawable.hai_08_wan_9_bottom);
        //viewHolder.image.setImageResource(R.drawable.hai_08_wan_9_bottom);
        if(mahjong.getName().equals("1l"))
            viewHolder.image.setImageResource(R.drawable.hai_18_sou_1_bottom);
        if(mahjong.getName().equals("2l"))
            viewHolder.image.setImageResource(R.drawable.hai_19_sou_2_bottom);
        if(mahjong.getName().equals("3l"))
            viewHolder.image.setImageResource(R.drawable.hai_20_sou_3_bottom);
        if(mahjong.getName().equals("4l"))
            viewHolder.image.setImageResource(R.drawable.hai_21_sou_4_bottom);
        if(mahjong.getName().equals("5l"))
            viewHolder.image.setImageResource(R.drawable.hai_22_sou_5_bottom);
        if(mahjong.getName().equals("6l"))
            viewHolder.image.setImageResource(R.drawable.hai_23_sou_6_bottom);
        if(mahjong.getName().equals("7l"))
            viewHolder.image.setImageResource(R.drawable.hai_24_sou_7_bottom);
        if(mahjong.getName().equals("8l"))
            viewHolder.image.setImageResource(R.drawable.hai_25_sou_8_bottom);
        if(mahjong.getName().equals("9l"))
            viewHolder.image.setImageResource(R.drawable.hai_26_sou_9_bottom);
        if(mahjong.getName().equals("1t"))
            viewHolder.image.setImageResource(R.drawable.hai_09_pin_1_bottom);
        if(mahjong.getName().equals("2t"))
            viewHolder.image.setImageResource(R.drawable.hai_10_pin_2_bottom);
        if(mahjong.getName().equals("3t"))
            viewHolder.image.setImageResource(R.drawable.hai_11_pin_3_bottom);
        if(mahjong.getName().equals("4t"))
            viewHolder.image.setImageResource(R.drawable.hai_12_pin_4_bottom);
        if(mahjong.getName().equals("5t"))
            viewHolder.image.setImageResource(R.drawable.hai_13_pin_5_bottom);
        if(mahjong.getName().equals("6t"))
            viewHolder.image.setImageResource(R.drawable.hai_14_pin_6_bottom);
        if(mahjong.getName().equals("7t"))
            viewHolder.image.setImageResource(R.drawable.hai_15_pin_7_bottom);
        if(mahjong.getName().equals("8t"))
            viewHolder.image.setImageResource(R.drawable.hai_16_pin_8_bottom);
        if(mahjong.getName().equals("9t"))
            viewHolder.image.setImageResource(R.drawable.hai_17_pin_9_bottom);
        return view;
    }


    class ViewHolder {
        ImageView image;
        //TextView tableName;
    }
}
