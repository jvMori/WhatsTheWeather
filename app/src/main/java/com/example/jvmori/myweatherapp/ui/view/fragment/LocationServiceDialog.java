package com.example.jvmori.myweatherapp.ui.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.jvmori.myweatherapp.R;

public class LocationServiceDialog extends DialogFragment {

    private ILocationServiceDialog iClickable;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_location)
                .setPositiveButton(R.string.settings, (dialog, id) -> {
                    if (iClickable != null) iClickable.onPositiveBtn();
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    if (iClickable != null) iClickable.onCancel();
                });
        return builder.create();
    }

    public void setILocationServiceDialog(ILocationServiceDialog iClickable) {
        this.iClickable = iClickable;
    }

    public interface ILocationServiceDialog {
        void onPositiveBtn();

        void onCancel();
    }
}
