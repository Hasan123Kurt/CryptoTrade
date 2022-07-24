package com.example.hsncrypto;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerAdepter extends RecyclerView.Adapter<recyclerAdepter.MyviewHolder> {

    private ArrayList<ListItem> BtcCoin;
    private OnClickListener onClickListener;
    public recyclerAdepter(ArrayList<ListItem> BtcCoin, OnClickListener onClickListener){
        this.BtcCoin = BtcCoin;
        this.onClickListener = onClickListener;

    }


    public class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView btcName, btcPrice,btcIcon;
        private ImageView icon;
        OnClickListener onClickListener;
        public MyviewHolder(final View view, OnClickListener onClickListener){
            super(view);
            btcName = view.findViewById(R.id.bigTitle);
            btcPrice = view.findViewById(R.id.priceText);
            btcIcon = view.findViewById(R.id.miniTitle);
            icon = view.findViewById(R.id.icon_btc);

            this.onClickListener = onClickListener;
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            onClickListener.onClick(getAdapterPosition());

        }
    }

    @NonNull
    @Override
    public recyclerAdepter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
        return new MyviewHolder(itemView,onClickListener);
    }



    @Override
    public void onBindViewHolder(@NonNull recyclerAdepter.MyviewHolder holder, int position) {
        String btcName2 = BtcCoin.get(position).getBtcName();
        String btcPrice2 =BtcCoin.get(position).getBtcPrice();
        String btcIcon2 = BtcCoin.get(position).getBtcIcon();
        holder.btcName.setText(btcName2);
        holder.btcPrice.setText(btcPrice2);
        holder.btcIcon.setText(btcIcon2);

        if(btcIcon2.equals("BTC"))
        {
            holder.icon.setImageResource(R.drawable.bitcoin);
        }
        if(btcIcon2.equals("ETH"))
        {
            holder.icon.setImageResource(R.drawable.ethereum);
        }
        else if(btcIcon2.equals("Doge"))
        {
            Log.d("icon","icon Type:" + btcIcon2);
            holder.icon.setImageResource(R.drawable.dogecoin);
        }




    }
    public interface OnClickListener{
        void onClick(int position);
    }

    @Override
    public int getItemCount() {
        return BtcCoin.size();

    }
}
