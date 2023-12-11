package com.example.alt_13_android_wallet.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alt_13_android_wallet.R;
import com.example.alt_13_android_wallet.models.DisplayTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryCustomAdapter extends BaseAdapter {

    //fields//attributes
    private Context myContext;
    //private String[] data;
    //private JSONArray jsonArray;
    private ArrayList<DisplayTransaction> transactions;
    private LayoutInflater inflater;
    public HistoryCustomAdapter(Context myContext, ArrayList<DisplayTransaction> transactions) {
        this.myContext = myContext;
        //this.data = data;
        //this.jsonArray = jsonArray;
        this.transactions = transactions;
        inflater = LayoutInflater.from(myContext);
    }
    @Override
    public int getCount() {
        return transactions.size();
    }
    @Override
    public Object getItem(int position) {
            return  transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.history_list_view_item, parent, false);
        }

        TextView textViewFrom = convertView.findViewById(R.id.textViewHistoryFrom);
        TextView textViewTo = convertView.findViewById(R.id.textViewHistoryTo);
        TextView textViewAmount = convertView.findViewById(R.id.textViewHistoryAmount);
        ImageView imageView = convertView.findViewById(R.id.imageView2);


        textViewFrom.setText(transactions.get(position).getAccountId());
        textViewTo.setText(transactions.get(position).getRecipientId());
        imageView.setImageResource(R.drawable.baseline_arrow_right_alt_2424);
        textViewAmount.setText(Double.toString(transactions.get(position).getAmount()));

        return convertView;
    }
}
