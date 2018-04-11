package com.bignerdranch.android.netproject111;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.bignerdranch.android.netproject111.adapters.TaskAdapter;
import com.bignerdranch.android.netproject111.beans.Task;
import com.bignerdranch.android.netproject111.beans.TaskOne;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private final String url1 = "http://172.16.2.139:5000/api/";
    public List<TaskOne> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendRequest();
        Log.d("list", list.toString());
       // Log.d("list1", list1);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        TaskAdapter taskAdapter = new TaskAdapter(list);
        recyclerView.setAdapter(taskAdapter);

        btn1 = findViewById(R.id.click_get);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }
    private void sendRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(url1).build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    jsonJX(responseData);
                    showResult(list);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void jsonJX(String data){
        Gson gson = new Gson();
        Task tasks = gson.fromJson(data, Task.class);
        list = tasks.getTasks();
        for(TaskOne one : list){
            Log.d("hhh1",one.getId());
            Log.d("hhh2", one.getTitle());
        }
    }

    private void showResult(final List<TaskOne> data){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView recyclerView = findViewById(R.id.recycler_view);
                LinearLayoutManager lm = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(lm);
                TaskAdapter taskAdapter = new TaskAdapter(data);
                recyclerView.setAdapter(taskAdapter);
            }
        });
    }
}
