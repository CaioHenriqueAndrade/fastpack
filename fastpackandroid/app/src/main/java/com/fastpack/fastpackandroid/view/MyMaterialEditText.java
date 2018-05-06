package com.fastpack.fastpackandroid.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.fastpack.fastpackandroid.R;

/**
 * Created by Caio on 11/02/2018.
 */

public class MyMaterialEditText extends com.rengwuxian.materialedittext.MaterialEditText {

    private boolean selectionActived = false;

    public MyMaterialEditText(Context context) {
        super(context);
        MyEditText.getListenerOfEditText(this);
    }

    public MyMaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        MyEditText.getListenerOfEditText(this);
    }

    public MyMaterialEditText(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        MyEditText.getListenerOfEditText(this);
    }

    //responsavel por ouvir a mudanca do cursor
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged( selStart , selEnd );

        //se ele atvou e o curso nao se encontra na ultima, obrigamos a ir para a ultima position
        if( selectionActived && selEnd != length() ) {
            setSelection( length() );
        }

    }

    public interface OnClickOkListener {
        boolean whenClicked();
    }
    public void setOnClickOkListener(final OnClickOkListener onClick ) {
        setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    return onClick.whenClicked();
                }
                return false;
            }
        });
    }
    public void notifyCampoText() {
        setHideUnderline(true);
        setBackgroundResource(R.drawable.border_edt);
    }

    public void setListenerOfMoney() {
        selectionActived = true;
        addTextChangedListener(new TextWatcher() {
            boolean foiModified = false;

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            private void setText(String text) {
                foiModified = true;
                MyMaterialEditText.this.setText(text);
                setSelection(text.length());
            }

            public void afterTextChanged(Editable editable) {
                if (foiModified) {
                    foiModified = false;
                    return;
                }

                String sequence = editable.toString();
                int sizeAtual = sequence.length();
                if (sizeAtual > 3) {
                    sequence = sequence.replaceAll(" ", "");

                    if (sequence.substring(0, 2).equals("0,"))
                        sequence = sequence.replaceAll("0,", "");

                    sequence = sequence.replaceAll(",", "");

                    sizeAtual = sequence.length();
                    if (sequence.length() > 1) {
                        String v = sequence.substring(0, sizeAtual - 2);
                        if(v.equals("")) {
                            v = "0";
                        }
                        setText(  v + "," + sequence.substring(sizeAtual - 2, sizeAtual));
                    }
                } else {
                    if (sequence.length() >= 1) {
                        sequence = sequence.replaceAll(",", "");
                        setText("0," + sequence);
                    } else if (sequence.length() == 0) {
                        if (!sequence.contains(",")) {
                            setText("0," + sequence);
                        } else {
                            setText("0,");
                        }
                    }
                }
            }
        });
    }



    public void setListenerOfPercent() {
        addTextChangedListener(new TextWatcher() {
            boolean foiModified = false;

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            private void setText(String text) {
                foiModified = true;
                MyMaterialEditText.this.setText( text + "%" );
                setSelection(text.length());
            }
            public String getSequence(Editable editable) {
                String text = editable.toString().replace("%","");
                while( text.startsWith("0") && ! text.startsWith("0,") && text.length() > 1) {
                    text = text.substring(1, text.length() );
                }
                if( text.startsWith("0,00") )
                    text = text.replace("0,00","0,0");

                return text;
            }

            public void afterTextChanged(Editable editable) {
                if (foiModified) {
                    foiModified = false;
                    return;
                }

                //String sequence = editable.toString().replace("%","");
                String sequence = getSequence(editable);

                int sizeAtual = sequence.length();

                if(sizeAtual == 5) return;

                if (sizeAtual > 3) {
                    sequence = sequence.replaceAll(" ", "");

                    if (sequence.substring(0, 2).equals("0,"))
                        sequence = sequence.replaceAll("0,", "");

                    sequence = sequence.replaceAll(",", "");

                    sizeAtual = sequence.length();
                    if (sequence.length() > 1) {
                        String v = sequence.substring(0, sizeAtual - 2);
                        if(v.equals("")) {
                            v = "0";
                        }
                        setText(  v + "," + sequence.substring(sizeAtual - 2, sizeAtual));
                    }
                } else {
                    if (sequence.length() >= 1) {
                        sequence = sequence.replaceAll(",", "");
                        setText("0," + sequence);
                    } else if (sequence.length() == 0) {
                        if (!sequence.contains(",")) {
                            setText("0," + sequence);
                        } else {
                            setText("0,");
                        }
                    }
                }
            }
        });

    }
}
