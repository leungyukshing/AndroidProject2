package com.example.lab1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class myListViewAdapter extends RecyclerView.Adapter<myListViewAdapter.ViewHolder> {
    private ArrayList<Product> list;

    public myListViewAdapter(ArrayList<Product> list) {
        this.list = list;
    }

    public Product getItem(int pos) {
        return list.get(pos);
    }

    public ArrayList<Product> getList() {
        return list;
    }

    // 根据名字修改对应商品的星星状态
    public void setItem(String name, boolean star) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                list.get(i).setIsStar(star);
            }
        }
    }
    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final Product product = list.get(i);
        viewHolder.productName.setText(product.getName());
        viewHolder.productLabel.setText(product.getLabel());
        // 点击逻辑
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), productDetail.class);
                Bundle data = new Bundle();
                data.putString("name", product.getName());
                data.putString("label", product.getLabel());
                data.putString("type", product.getType());
                data.putString("nutrition", product.getNutrition());
                data.putString("color", product.getColor());
                data.putInt("index", i);
                data.putBoolean("star", product.getIsStar());

                intent.putExtras(data);
                ((Activity)v.getContext()).startActivityForResult(intent, 0);
            }
        });
        // 长按逻辑
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String name = list.get(i).getName();
                list.remove(i);
                //myListViewAdapter.this.notifyDataSetChanged();

                myListViewAdapter.this.notifyItemRemoved(i);
                myListViewAdapter.this.notifyItemRangeChanged(i, list.size());

                Toast.makeText(v.getContext(), "删除" + name, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        private TextView productName;
        private TextView productLabel;

       public ViewHolder(View itemView) {
           super(itemView);
           productName = itemView.findViewById(R.id.name);
           productLabel = itemView.findViewById(R.id.label);
       }
    }


}
