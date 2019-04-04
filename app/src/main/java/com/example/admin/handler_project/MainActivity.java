package com.example.admin.handler_project;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


/**
 *
 * handler的被动发送消息
 */

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {  //这个是发送过来的消息
            //处理从子线程发送过来的消息
            int arg1 = msg.arg1;
            int arg2 = msg.arg2;
            int what = msg.what;
            Object result = msg.obj;
            Log.d(TAG,"arg1=" + arg1);
            Log.d(TAG,"arg2=" + arg2);
            Log.d(TAG,"what=" + what);
            Log.d(TAG,"result=" + result);
            Bundle bundle = msg.getData(); //用来获取消息里面的bundle数据
            Log.d(TAG, "getData = " + bundle.getStringArray("str").length);
        }
    };

    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = (Button)findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = Message.obtain(handler,33,2,3,"hello");
                        Bundle data = new Bundle(); //message 也可以携带复杂一点的数据，比如 bundle对象
                        data.putStringArray("str", new String[]{"c","c++","java"});
                        message.setData(data);
                        message.sendToTarget();//发送消息的方式，有一点将自己绑定好了被发射的感觉,被动
                    }
                }).start();
            }
        });



    }
}
