package com.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //vars
    private ArrayList<One_Task> tasks_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final Button create_button = (Button) findViewById(R.id.create_button);
        final EditText editText = (EditText) findViewById(R.id.editTextId);
        if (savedInstanceState != null)
        {
            String s = savedInstanceState.getString("todo_string");
            String[] list_s = s.split(";");

            for(int i = 0; i < list_s.length - 1; i = i + 2)
            {
                tasks_list.add(new One_Task(list_s[i], Integer.parseInt(list_s[i + 1])));
            }
            initRecyclerView();
        }

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                String err_msg = "you can't create an empty TODO item, oh silly!";
                String empty_str = "";
                editText.setText(empty_str);
                Context context = getApplicationContext();
                if (text.equals(empty_str))
                    Toast.makeText(context, err_msg, Toast.LENGTH_SHORT).show();
                else {
                    One_Task one_task = new One_Task(text, R.mipmap.tick_box_unchecked);
                    tasks_list.add(one_task);
                    initRecyclerView();
                }
            }
            });
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: initrecyclerview.");
        RecyclerView recyclerView  = findViewById(R.id.recyclerView);
        final recyclerViewAdapter adapter = new recyclerViewAdapter(this, tasks_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.Set_Click_Listener(new recyclerViewAdapter.Item_Click_Listener() {

            @Override
            public void Item_Click(int position) {
                Context context = getApplicationContext();
                One_Task task = tasks_list.get(position);
                task.edit_image(R.mipmap.tick_box_checked);
                adapter.notifyItemChanged(position);
                Toast.makeText(context, "TODO" + task.return_task_text() + "done", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

        super.onSaveInstanceState(outState);
        String s = "";
        for (One_Task a : tasks_list)
        {
            s = s + a.return_task_text() + ";" + String.valueOf(a.return_im_number()) + ";";
        }
        super.onSaveInstanceState(outState);
        outState.putString("todo_String", s);
    }
}

