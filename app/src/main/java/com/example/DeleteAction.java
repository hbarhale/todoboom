package com.example;

import androidx.annotation.NonNull;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DeleteAction extends AppCompatDialogFragment{
    private OnDeleteClickListener mListener;

    public interface OnDeleteClickListener
    {
        void delete_pressed();
        void not_delete_pressed();
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener)
    {
        mListener = listener;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.delete_pressed();
                    }
                })
                .setNegativeButton( "NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.not_delete_pressed();
                    }
                });
        return builder.create();
    }

}
