package mendroid.easygo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;


/**
 * Used to validate the input fields
 * @author Umayaesawaran
 */

public class ErrorReport {
    private Context context;
    private ArrayList<String> reports;

    public ErrorReport(Context _context) {
        context = _context;
        reports = new ArrayList<>();
    }

    public void addError(String _errorText) {
        reports.add(_errorText);
    }


    public String checkText(EditText _text, final TextInputLayout til, boolean _required, String err) {
        boolean ret = true;
        String text = _text.getText().toString();
        if (_required) {
            if (text.length() == 0) {
                reports.add(err);
                if (til != null) {
                    til.setError(err);
                    _text.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            til.setError(null);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else {
                    _text.setError(err);
                }
                ret = false;
            }
        }
        if (ret) {
            return text;
        } else {
            return null;
        }
    }


    public int checkInt(EditText _text, final TextInputLayout til, boolean _required, String err) {
        int ret = 0;
        try {
            String text = _text.getText().toString();
            if (text.length() == 0 && _required) {
                reports.add(err);
                if (til != null) {
                    til.setError(err);
                    _text.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            til.setError(null);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                } else {
                    _text.setError(err);
                }
            } else {
                ret = Integer.parseInt(text);
            }
        } catch (NumberFormatException e) {
            if (_required) {
                reports.add(err);
            }
        }
        return ret;
    }


    public void clear() {
        reports.clear();
    }


    public boolean checkError() {
        return checkError(true);
    }

    public boolean checkError(boolean _show) {
        boolean success = reports.size() == 0;
        if (!success && _show) {
            show();
        }
        return success;
    }

    public void show() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        String[] areports = new String[reports.size()];
        for (int i = 0; i < areports.length; i++) {
            areports[i] = reports.get(i);
        }
        adb.setItems(areports, null);
        adb.setNeutralButton(context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        adb.show();
    }
}
