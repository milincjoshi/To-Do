package xyz.milinjoshi.to_do;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by milinjoshi on 3/27/16.
 */
public class Custom_Adapter extends ArrayAdapter {

    ArrayList<Item_One> modelItems = null;
    Context context;
    public Custom_Adapter(Context context, ArrayList<Item_One> resource) {
        super(context,R.layout.single_list_item,resource);

        this.context = context;
        this.modelItems = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.single_list_item, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.textView1);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
        name.setText(modelItems.get(position).getName());
        int checked = modelItems.get(position).getValue();
        if(checked == 1){
            cb.setChecked(true);
        }
       // if(modelItems.get(position).getValue() == 1)
       //     cb.setChecked(true);
       // else
       //     cb.setChecked(false);
        return convertView;
    }
}
