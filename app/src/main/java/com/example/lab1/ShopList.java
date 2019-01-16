package com.example.lab1;

import android.app.Activity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ShopList extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton floatBtn;
    Boolean isShopList = false;
    ListView cart;
    LinearLayout linearLayout;
    List<Map<String, Object>> cartList = new ArrayList<>();
    SimpleAdapter simpleAdapter;
    private DynamicReceiver dynamicReceiver = null;
    private  NewAppWidget appWidget = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_list);

        floatBtn = (FloatingActionButton)findViewById(R.id.btn);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        cart = (ListView)findViewById(R.id.cart);
        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new myListViewAdapter(getData()));

        // Add Remove Animation
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setRemoveDuration(10);
        recyclerView.setItemAnimator(defaultItemAnimator);

        // 悬浮按钮
        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShopList = !isShopList;
                if (isShopList) {
                    recyclerView.setVisibility(View.INVISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    floatBtn.setImageDrawable(getResources().getDrawable(R.drawable.mainpage));
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.INVISIBLE);
                    floatBtn.setImageDrawable(getResources().getDrawable(R.drawable.collect));
                }
            }
        });

        // Cart
        simpleAdapter = new SimpleAdapter(this, cartList, R.layout.shop_list_item, new String[] {"label", "name"}, new int[] {R.id.label, R.id.name});
        cart.setAdapter(simpleAdapter);
        // 点击逻辑
        cart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), productDetail.class);
                Bundle data = new Bundle();
                HashMap<String, Object> m = (HashMap<String, Object>) cartList.get(position);
                data.putString("name", (String)m.get("name"));
                data.putString("label", (String)m.get("label"));
                data.putString("type", (String)m.get("type"));
                data.putString("nutrition", (String)m.get("nutrition"));
                data.putString("color", (String)m.get("color"));
                data.putInt("index", position);
                data.putBoolean("star", (boolean)m.get("star"));

                intent.putExtras(data);
                ((Activity)view.getContext()).startActivityForResult(intent, 0);
            }
        });
        // 长按逻辑
        cart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ShopList.this);
                dialogBuilder.setTitle("删除")
                        .setMessage("确定删除" + cartList.get(position).get("name") + "?").setCancelable(true)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartList.remove(position);
                        simpleAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                return true;
            }
        });


        // Static Broadcast
        ArrayList<Product> products = ((myListViewAdapter)recyclerView.getAdapter()).getList();
        Random random = new Random();
        int index = random.nextInt(products.size());

        Intent intentBroadcast = new Intent();
        intentBroadcast.setAction(BroadcastType.STATICACTION);

        Product product = products.get(index);
        Bundle bundle = new Bundle();
        bundle.putInt("image", R.mipmap.empty_star);
        bundle.putString("name", product.getName());
        bundle.putString("label", product.getLabel());
        bundle.putString("type", product.getType());
        bundle.putString("nutrition", product.getNutrition());
        bundle.putString("color", product.getColor());
        bundle.putInt("index", index);
        bundle.putBoolean("star", (boolean)product.getIsStar());

        intentBroadcast.putExtras(bundle);
        sendBroadcast(intentBroadcast);


        // register dynamic receiver
        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction(BroadcastType.DYNAMICACTION);
        dynamicReceiver = new DynamicReceiver();
        appWidget = new NewAppWidget();
        registerReceiver(dynamicReceiver, dynamic_filter);
        registerReceiver(appWidget, dynamic_filter);

        // Register Event Bus
        EventBus.getDefault().register(this);
    }

    private ArrayList<Product> getData() {
        ArrayList<Product> list = new ArrayList<>();
        list.add(new Product("大豆", "粮", "粮食", "蛋白质", "#BB4C3B"));
        list.add(new Product("十字花科蔬菜", "蔬", "蔬菜", "维生素", "#C48D30"));
        list.add(new Product("牛奶", "饮", "饮品", "钙", "#4469B0"));
        list.add(new Product("海鱼", "肉", "肉食", "蛋白质", "#20A17B"));
        list.add(new Product("菌菇类", "蔬", "蔬菜", "微量元素", "#BB4C3B"));
        list.add(new Product("番茄", "蔬", "蔬菜", "番茄红素", "#4469B0"));
        list.add(new Product("胡萝卜", "蔬", "蔬菜", "胡萝卜素", "#20A17B"));
        list.add(new Product("荞麦", "粮","粮食", "膳食纤维", "#BB4C3B"));
        list.add(new Product("鸡蛋", "杂", "杂", "几乎所有营养物质", "#C48D30"));
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult");
        if (data == null) {
            return;
        }
        System.out.println("not null");
        if (requestCode == 0) {
            if (resultCode == 0) {
                System.out.println("In Result");
                /*
                boolean isStared = data.getBooleanExtra("star", false);
                int index = data.getIntExtra("index", 0);
                String name = data.getStringExtra("productName");
                ((myListViewAdapter)recyclerView.getAdapter()).setItem(name, isStared);
                for (int i = 0; i < cartList.size(); i++) {
                    if (cartList.get(i).get("name").equals(name)) {
                        cartList.get(i).put("star", isStared);
                        System.out.println("Modify: " + cartList.get(i).get("star"));
                    }
                }
                */
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        System.out.println("Get Message Event");

        // Send Broadcast
        Intent intent = new Intent();
        // 生成随机数以产生通知
        Random random = new Random();
        int l = random.nextInt(2147483647);

        intent.setAction(BroadcastType.DYNAMICACTION);
        intent.putExtras(event.bundle);
        intent.putExtra("i", l);
        intent.putExtra("showCart", false);
        sendBroadcast(intent);

        // add to cartlist
        Map<String, Object> temp = new LinkedHashMap<>();
        Product product = ((myListViewAdapter)recyclerView.getAdapter()).getItem((int)event.bundle.get("index"));
        temp.put("label", event.bundle.get("label"));
        temp.put("name", event.bundle.get("name"));
        temp.put("type", event.bundle.get("type"));
        temp.put("nutrition", event.bundle.get("nutrition"));
        temp.put("color", event.bundle.get("color"));
        temp.put("star", event.bundle.get("star"));

        boolean existed = false;

        // 收藏夹添加查重
        for (int i = 0; i < cartList.size(); i++) {
            // System.out.println("hello: " + cartList.get(i).get("name") + ", ss: "+ event.bundle.get("name"));
            if (cartList.get(i).get("name").equals(event.bundle.get("name"))) {
                existed = true;
                break;
            }
        }
        if (!existed) {
            cartList.add(temp);
            simpleAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStarEvent(StarEvent event) {
        System.out.println("Get Star Event");
        boolean isStared = event.star;
        String name = event.name;
        // 更新商品列表的状态
        ((myListViewAdapter)recyclerView.getAdapter()).setItem(name, isStared);
        // 更新收藏夹的状态
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).get("name").equals(name)) {
                cartList.get(i).put("star", isStared);
                // System.out.println("Modify: " + cartList.get(i).get("star"));
            }
        }
        System.out.println("Finish Update star: " + name + ", " + isStared);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(dynamicReceiver);
        unregisterReceiver(appWidget);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("ReStart");

        // 根据Intent的数据判断显示商品列表还是收藏夹
        boolean temp = getIntent().getBooleanExtra("showCart", false);
        System.out.println(temp);
        isShopList = temp;
        if (isShopList) {
            recyclerView.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            floatBtn.setImageDrawable(getResources().getDrawable(R.drawable.mainpage));
        }
        else {
            recyclerView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
            floatBtn.setImageDrawable(getResources().getDrawable(R.drawable.collect));
        }

    }
}