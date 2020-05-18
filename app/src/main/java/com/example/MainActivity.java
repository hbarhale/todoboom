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
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private static final String TAG = "MainActivity";


    private ArrayList<One_Task> tasks_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final Button create_button = (Button) findViewById(R.id.create_button);
        final EditText editText = (EditText) findViewById(R.id.editTextId);
        Log.d(TAG, "saveinstance: "+ savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.getString("tasks_list") != null)
        {
            String s = savedInstanceState.getString("tasks_list");
            String[] list_s = s.split(";");

            for(int i = 0; i < list_s.length - 1; i = i + 2)
            {
                tasks_list.add(new One_Task(list_s[i], Integer.parseInt(list_s[i + 1])));
            }
            initRecyclerView();
        }
        else {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            String t = sp.getString("tasks_list", null);
            if (t != null && !t.equals("")) {
                String[] list_t = t.split(";");
                for (int i = 0; i < list_t.length - 1; i = i + 2) {
                    tasks_list.add(new One_Task(list_t[i], Integer.parseInt(list_t[i + 1])));
                }
                initRecyclerView();
            }
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
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sp.edit();
                    One_Task one_task = new One_Task(text, R.mipmap.tick_box_unchecked);
                    tasks_list.add(one_task);
                    editor.putString("tasks_list", tasks_list_to_string());
                    editor.apply();
                    initRecyclerView();
                }
            }
            });
    }

    private void initRecyclerView(){
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
                Toast.makeText(context, "TODO " + task.return_task_text() + " is done!", Toast.LENGTH_SHORT).show();
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("tasks_list", tasks_list_to_string());
                editor.apply();
                Toast.makeText(context, "TODO " + task.return_task_text() + " is now DONE. BOOM!", Toast.LENGTH_SHORT).show();
            }
            public boolean onLongClick(int position)
            {
                Context context = getApplicationContext();
                One_Task task = tasks_list.get(position);
                //adapter.notifyItemChanged(position);
                Toast.makeText(context, "Long press on " + task.return_task_text(), Toast.LENGTH_SHORT).show();
                OpenPopUp(position);
                return true;
            }

        });
    }

    public void OpenPopUp(final int position){
        DeleteAction deleteAction = new DeleteAction() ;
        deleteAction.setOnDeleteClickListener(new DeleteAction.OnDeleteClickListener() {
            @Override
            public void delete_pressed() {
                Context context = getApplicationContext();
                tasks_list.remove(position);
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("tasks_list", tasks_list_to_string());
                initRecyclerView();
                editor.apply();
            }

            @Override
            public void not_delete_pressed() {

            }
        });
        deleteAction.show(getSupportFragmentManager(), "deleteAction");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState)
    {

        super.onSaveInstanceState(outState);
        //String s = "";
        if (tasks_list.size() > 0 ) {
            outState.putString("tasks_list", tasks_list_to_string());
        }
        else {
         outState = null;
        }

    }
    private String tasks_list_to_string()
    {
        String s = "";
        for (One_Task a : tasks_list)
        {
            s = s + a.return_task_text() + ";" + String.valueOf(a.return_im_number()) + ";";
        }
        return s;
    }
}

