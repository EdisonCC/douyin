package com.ehook.dy.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ehook.dy.R;
import com.evan.dy.api.model.DyConfig;

public class AutoShoppingDialog extends DialogUtils {
    EditText editText;
    private DyConfig.DyAutoShopping data;
    private DialogCallback callback;

    public AutoShoppingDialog(@NonNull Context context) {
        super(context);
        editText = contentView.findViewById(R.id.edit_text);
    }

    public DialogUtils setData(final DyConfig.DyAutoShopping data, DialogCallback callback) {

        this.data = data;
        this.callback = callback;
        this.setTitle(data.name);
        editText.setText(data.interval + "");
        return this;
    }

    @Override
    protected void onSaveClick() {
        super.onSaveClick();
        String s = editText.getText().toString().trim();
        if (TextUtils.equals("0",s)) {
            Toast.makeText(getContext(), "时间不能为0", Toast.LENGTH_SHORT).show();
            return;
        }
        data.interval = Integer.parseInt(s);
        if (callback != null) {
            callback.callback(this, data);
        }
        dismiss();
    }

    @Override
    int getLayoutId() {
        return R.layout.dialog_auto_shopping_view;
    }
}
