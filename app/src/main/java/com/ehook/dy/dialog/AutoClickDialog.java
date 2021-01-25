package com.ehook.dy.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ehook.dy.R;
import com.evan.dy.api.model.DyConfig;

public class AutoClickDialog extends DialogUtils {
    EditText editText;
    private DyConfig.DyAutoClick data;
    private DialogCallback callback;

    public AutoClickDialog(@NonNull Context context) {
        super(context);
        editText = contentView.findViewById(R.id.edit_text);
    }

    public DialogUtils setData(final DyConfig.DyAutoClick data, DialogCallback callback) {

        this.data = data;
        this.callback = callback;
        this.setTitle(data.name);
        editText.setText(data.threshold + "");
        return this;
    }

    @Override
    protected void onSaveClick() {
        super.onSaveClick();
        String s = editText.getText().toString().trim();
        if (TextUtils.isEmpty(s)) {
            Toast.makeText(getContext(), "不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        int i = Integer.parseInt(s);
        if (i == 0) {
            Toast.makeText(getContext(), "不能为0", Toast.LENGTH_SHORT).show();
            return;
        }

        data.threshold = i;
        if (callback != null) {
            callback.callback(this, data);
        }
        dismiss();
    }

    @Override
    int getLayoutId() {
        return R.layout.dialog_auto_click_view;
    }
}
