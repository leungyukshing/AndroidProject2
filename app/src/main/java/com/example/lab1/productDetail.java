package com.example.lab1;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.List;

public class productDetail extends AppCompatActivity {
    TextView name;
    TextView detail;
    TextView type;
    ImageView back;
    RelativeLayout topLayout;
    ListView operationList;
    ImageView star;
    ImageView collect;
    int index;
    boolean isStar;
    boolean buy;
    Bundle product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        name = (TextView)findViewById(R.id.productName);
        detail = (TextView)findViewById(R.id.subtitle);
        type = (TextView)findViewById(R.id.type);
        back = (ImageView)findViewById(R.id.back);
        topLayout = (RelativeLayout)findViewById(R.id.top);
        operationList = (ListView)findViewById(R.id.operationList);
        star = (ImageView)findViewById(R.id.star);
        collect = (ImageView)findViewById(R.id.collect);

        // 根据Intent内容初始化页面元素
        product = getIntent().getExtras();
        name.setText(product.getString("name"));
        detail.setText("富含 " + product.getString("nutrition"));
        type.setText(product.getString("type"));
        topLayout.setBackgroundColor(Color.parseColor(product.getString("color")));
        index = product.getInt("index");
        isStar = product.getBoolean("star");
        buy = false;

        // 星星初始化
        if (isStar) {
            star.setImageDrawable(getResources().getDrawable(R.drawable.full_star));
        }
        // 返回图标
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Buy: " + buy);
                // Intent intent = new Intent(productDetail.this, ShopList.class);
                // startActivity(intent);
                productDetail.this.finish();
            }
        });

        // 星星图标
        star.setTag(isStar);
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean tag = (boolean) v.getTag();
                isStar = !isStar;
                if (!tag) {
                    star.setImageDrawable(getResources().getDrawable(R.drawable.full_star));
                    v.setTag(true);
                }
                else {
                    star.setImageDrawable(getResources().getDrawable(R.drawable.empty_star));
                    v.setTag(false);
                }

                EventBus.getDefault().post(new StarEvent(name.getText().toString(), isStar));
            }
        });

        // 操作列表
        String[] operations = {"分享信息", "不感兴趣", "查看更多信息", "出错反馈"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.operation, operations);
        operationList.setAdapter(arrayAdapter);

        // 购物车按钮
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(productDetail.this, "已收藏", Toast.LENGTH_SHORT).show();
                buy = true;
                System.out.println("collect!");
                product.putBoolean("star", isStar);
                EventBus.getDefault().post(new MessageEvent(product));
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent = new Intent(productDetail.this, ShopList.class);
        startActivity(intent);
        return super.onKeyDown(keyCode, event);
    }
}
