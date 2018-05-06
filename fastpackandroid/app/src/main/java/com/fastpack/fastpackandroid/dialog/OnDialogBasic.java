package com.fastpack.fastpackandroid.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fastpack.fastpackandroid.animation.UtilsAnimation;
import com.fastpack.fastpackandroid.interfaces.Interfaces;
import com.fastpack.fastpackandroid.utils.UtilsColor;

public class OnDialogBasic implements Interfaces.OnMethodsDialog, DialogInterface.OnDismissListener {
    private Activity activity;
    private MyAlertDialog alertDialog;
    private View view;

    //se false is up and down
    //se true is right and left
    boolean tipoEffect = false;

    private AlertDialog.OnDismissListener dismissListener;

    public OnDialogBasic(Activity ac) {
        this.activity = ac;
    }

    @Override
    public Activity getActivity() {
        return activity;
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return activity.getLayoutInflater();
    }

    @Override
    public AlertDialog onCreateDialog(AlertDialog alertDialogBuilder) {
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK &&
                        event.getAction() == KeyEvent.ACTION_UP &&
                        !event.isCanceled()) {
                    if (!onBackPressed()) {
                        dismiss();
                        return true;
                    }
                    return true;
                }
                return false;
            }
        });

        alertDialogBuilder.setOnDismissListener(this);

        return alertDialogBuilder;
    }

    @Override
    public void setBackgroundTransparent() {
        getDialog().getWindow().setBackgroundDrawable( new ColorDrawable(UtilsColor.getColor( getResources() , android.R.color.transparent )));
    }

    @Override
    public void dismiss() {
        if (view != null) {
            UtilsAnimation.ListenerEffect listener = new UtilsAnimation.ListenerEffect() {
                @Override
                public void onEffectTermined() {
                    getDialog().dismiss();
                }
            };

            if (!tipoEffect)
                UtilsAnimation.slideDown(view, listener);
            else
                UtilsAnimation.slideLeft(view, listener);

        } else getDialog().dismiss();
    }

    @Override
    public boolean onTouchOutsideDialog() {
        dismiss();
        return true;
    }

    public final View createView(int idView) {
        return getLayoutInflater().inflate(idView, null);
    }

    public final View createView(int idView, ViewGroup container) {
        return getLayoutInflater().inflate(idView, container, false);
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public String getString(int idRes) {
        return getResources().getString(idRes);
    }

    @Override
    public Resources getResources() {
        return getActivity().getResources();
    }

    public void execute() {
        alertDialog = new MyAlertDialog(getActivity(), this);

        onCreateDialog(alertDialog);


        view = onCreateView();
        if (view != null) {
            getDialog().setView(view);
            recuperarReferencias(view);
            setOnClickListeners();
            setDadosInLayout();
        }

        onFinish();

        onPostViewCreated(view);


    }

    private void onFinish() {
        alertDialog.show();
    }


    @Override
    public void onPostViewCreated(final View v) {
        if (v != null)
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!tipoEffect)
                                UtilsAnimation.slideUp(v);
                            else
                                UtilsAnimation.slideLeft(v);
                        }
                    });
                }
            }).start();
    }

    @Override
    public View onCreateView() {
        return null;
    }

    @Override
    public MyAlertDialog getDialog() {
        return alertDialog;
    }

    @Override
    public void recuperarReferencias(View view) {

    }

    @Override
    public void setOnClickListeners() {

    }

    @Override
    public void setDadosInLayout() {

    }

    public void setOnDismissListener(AlertDialog.OnDismissListener dismiss) {
        this.dismissListener = dismiss;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (dismissListener != null) {
            dismissListener.onDismiss(dialog);
        }
    }

    public void makeText(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    public static class MyAlertDialog extends AlertDialog {
        private Interfaces.OnMethodsDialog methods;

        protected MyAlertDialog(@NonNull Context context, @Nullable Interfaces.OnMethodsDialog methods) {
            super(context);
            this.methods = methods;
        }


        //chamado quando tocado FORA do dialog
        @Override
        public synchronized boolean onTouchEvent(MotionEvent event) {
            //2 quando ele arrasta
            //1 quando ele solta (aparentemente...)

            if (methods != null && event.getAction() == 1) {
                return methods.onTouchOutsideDialog();
            }

            return true;
        }
    }

}
