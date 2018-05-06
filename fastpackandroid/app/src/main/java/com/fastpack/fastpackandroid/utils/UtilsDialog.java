package com.fastpack.fastpackandroid.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fastpack.fastpackandroid.R;

/**
 * Created by Caio on 20/01/2018.
 */

public class UtilsDialog {

    AlertDialog.Builder algBuilder;
    AlertDialog alertDialog;
    private Context context;
    public UtilsDialog(Context context) {
        this.context = context;
    }

    public void dialogBasic(String message , String msgBtPositive, String msgBtNegative , AlertDialog.OnClickListener onClick ) {
        newInstance();
        getAlertDialogBuilder().setMessage( message );
        getAlertDialogBuilder().setPositiveButton(msgBtPositive , onClick);
        getAlertDialogBuilder().setNegativeButton( msgBtNegative , null);
        show();
    }
    public void dialogBasic(String message , String msgBtPositive, String msgBtNegative , AlertDialog.OnClickListener onClick , AlertDialog.OnClickListener onClickNegative ) {
        newInstance();
        getAlertDialogBuilder().setMessage( message );
        getAlertDialogBuilder().setPositiveButton(msgBtPositive , onClick);
        getAlertDialogBuilder().setNegativeButton( msgBtNegative , onClickNegative);
        show();
    }


    private void show() {
        alertDialog = getAlertDialogBuilder().create();
        alertDialog.show();
    }

    public AlertDialog.Builder getAlertDialogBuilder() {
        return algBuilder;
    }


    public void dialogListBasic(String titleName, ArrayAdapter<String> adapter , final OnClickDialog onClickList) {
        newInstance();

// ...Irrelevant code for customizing the buttons and title
        final View dialogView = LayoutInflater.from(  getAlertDialogBuilder().getContext()  ).inflate(R.layout.layout_with_list_view, null );

        getAlertDialogBuilder().setView(dialogView);

        ListView butView = (ListView) dialogView.findViewById(R.id.listview);
        ((TextView) dialogView.findViewById(R.id.txt_titulo) ).setText( titleName );

        //   final ArrayAdapter<String> Adapter = new ArrayAdapter<String>( getContext() , android.R.layout.simple_list_item_1);
        butView.setAdapter(adapter);
        alertDialog = algBuilder.create();
        alertDialog.show();

        /*
        butView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        }); */

        butView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClickList.onClick(parent,view,position,id, alertDialog);
            }
        });

    }

    public void newInstance() {
        algBuilder = new AlertDialog.Builder( context );
    }

    private Context getContext() {
        return context;
    }

    public void dismiss() {
        if (getAlertDialog() != null)
            getAlertDialog().dismiss();
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public boolean isShow() {
        return getAlertDialog() != null && getAlertDialog().isShowing();
    }


    public interface OnClickDialog {
        void onClick(AdapterView<?> parent, View view, int position, long id , AlertDialog dialog);
    }
}
