package com.example.caroleenanwar.movie.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.caroleenanwar.movie.R;


/**
 * Created by caroleen on 3/24/2018.
 */

public class WebserviceUtil {

    public static boolean isNetworkOnline(Context mContext) {
        if (mContext == null) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }


    public static Dialog showNewDialog(Context mContext){
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_network);
        TextView message=(TextView)dialog.findViewById(R.id.message);
        message.setText("Please open you Wifi to get data");
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Button yes =(Button) dialog.findViewById(R.id.yes);
        Button no = (Button) dialog.findViewById(R.id.no);
        yes.setText(mContext.getResources().getText(R.string.retry));

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }





}
