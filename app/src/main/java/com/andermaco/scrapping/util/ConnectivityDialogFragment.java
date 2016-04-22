package com.andermaco.scrapping.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.andermaco.scrapping.R;


/**
 * @author andermaco@gmail.com
 */
public class ConnectivityDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState)
    {
        return new AlertDialog.Builder(getActivity())
            .setTitle(getString(R.string.app_name))
            .setMessage("Check your network settings")
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            })
            .setIcon(R.drawable.ic_warning_black_24dp)
            .create();
    }
}
