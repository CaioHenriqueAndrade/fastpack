package com.fastpack.fastpackandroid.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Caio on 09/02/2018.
 */

//responsavel por retirar caracteres especiais do text,
//estes caracteres podem bugar a base de dados
// como '

public class MyEditText extends android.support.v7.widget.AppCompatEditText implements TextView.OnEditorActionListener {

    public interface KeyEnterListener {
        boolean onKeyPressed();
    }

    private KeyEnterListener keyEnterListener;

    public MyEditText(Context context) {
        super(context);
        getListenerOfEditText(this);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        getListenerOfEditText(this);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getListenerOfEditText(this);
    }


    public void setKeyEnterListener(KeyEnterListener keyEnterListener) {
        setOnEditorActionListener(this);
        this.keyEnterListener = keyEnterListener;

    }


    public static final String[] chars = {"'"};

    public static void getListenerOfEditText(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            boolean textFoiChanged = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!textFoiChanged) {

                    if (s.length() > 0) {
                        for (String letra : chars) {
                            if (s.toString().contains(letra)) {
                                textFoiChanged = true;
                                editText.setText(s.toString().replaceAll("'", ""));
                                editText.setSelection(editText.getText().toString().length()); //para mandar o focus para a ultima letra da string...
                            }
                        }
                    }

                } else {
                    textFoiChanged = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public static void setEspacoNotPermited(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            boolean textFoiChanged = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!textFoiChanged) {

                    if (s.length() > 0) {
                            if (s.toString().contains(" ")) {
                                textFoiChanged = true;
                                editText.setText(s.toString().replaceAll(" ", ""));
                                editText.setSelection(editText.getText().toString().length()); //para mandar o focus para a ultima letra da string...
                            }
                    }

                } else {
                    textFoiChanged = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (EditorInfo.IME_ACTION_SEARCH == actionId) {

            boolean processado = keyEnterListener.onKeyPressed();

            if (!processado) {
                setText(null);
            }

        } else if (actionId == EditorInfo.IME_NULL
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            boolean processado = keyEnterListener.onKeyPressed();

            if (!processado) {
                setText(null);
            }
        }
        return true;
    }
}