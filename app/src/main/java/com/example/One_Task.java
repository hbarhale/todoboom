package com.example;

public class One_Task {
    private String  Task_text;
    private int Im_number;

    public  One_Task(String s, int i){
        this.Task_text = s;
        this.Im_number = i;
    }

    public void edit_text(String s){
        this.Task_text = s;
    }

    public void edit_image(int i){
        this.Im_number = i;
    }

    public String return_task_text(){
        return Task_text;
    }

    public int return_im_number(){
        return Im_number;
    }
}
