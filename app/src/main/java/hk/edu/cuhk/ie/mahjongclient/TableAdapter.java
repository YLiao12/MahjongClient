package hk.edu.cuhk.ie.mahjongclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class TableAdapter extends ArrayAdapter<Table> {
    private int resourceId;
    public TableAdapter(Context context, int resource, List<Table> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Table table = getItem(position);
        View view;
        TableAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new TableAdapter.ViewHolder();
            viewHolder.tableName = view.findViewById(R.id.tableName);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (TableAdapter.ViewHolder) view.getTag();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(table.getTableName());
        sb.append("  (");
        sb.append(table.getPlayersNum());
        sb.append(")");
        viewHolder.tableName.setText(sb.toString());
        return view;
    }

    class ViewHolder {
        TextView tableName;
    }

}
