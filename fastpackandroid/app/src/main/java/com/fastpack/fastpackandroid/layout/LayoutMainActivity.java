package com.fastpack.fastpackandroid.layout;

import android.view.View;
import android.widget.TextView;

import com.fastpack.fastpackandroid.R;

/**
 * Created by root on 28/03/18.
 */

public class LayoutMainActivity extends LayoutBasic {

    private TextView textView;

    @Override
    public void recuperarReferencias(View view) {
        textView = view.findViewById(R.id.text);
    }

    @Override
    public void iniciarDadosNoLayout() {
        textView.setText( "  texto novo  " );
    }

}
