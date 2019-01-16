package com.example.lab1;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.text.TextUtils;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    // variables
    private RadioGroup radioGroup;
    private EditText searchContent;
    private Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup)findViewById(R.id.radioBtnGroup);
        searchButton = (Button)findViewById(R.id.searchButton);
        searchContent = (EditText)findViewById(R.id.input);

        radioGroup.check(R.id.one);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchContent.getText().toString().equals("Health")) {
                    int checkedID = radioGroup.getCheckedRadioButtonId();
                    RadioButton checkBtn = (RadioButton)findViewById(checkedID);
                    // 弹出对话框
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    dialogBuilder.setTitle("Tips")
                            .setMessage(checkBtn.getText() + "搜索成功")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainActivity.this, "对话框\"确定\"按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainActivity.this, "对话框\"取消\"按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();

                }
                else if (searchContent.getText().toString().equals("Shop")) {
                    //显示方式声明Intent，直接启动SecondActivity
                    Intent intent = new Intent(MainActivity.this, ShopList.class);
                    startActivity(intent);
                }
                else if (TextUtils.isEmpty(searchContent.getText().toString())) {
                    Toast.makeText(MainActivity.this, "搜索内容不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    dialogBuilder.setTitle("Tips")
                            .setMessage("搜索失败")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainActivity.this, "对话框\"确定\"按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainActivity.this, "对话框\"取消\"按钮被点击", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checked = (RadioButton)findViewById(i);
                Toast.makeText(MainActivity.this, checked.getText() + "被选中", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
