package com.ehook.dy.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ehook.dy.R;
import com.evan.dy.api.model.DyConfig;

public class WelcomeDialog extends DialogUtils {
    EditText editText;
    private DyConfig.DyReplyWelcome data;
    private DialogCallback callback;

    public WelcomeDialog(@NonNull Context context) {
        super(context);
        editText = contentView.findViewById(R.id.edit_text);
        editText.setText("welcome.content");
    }

    public DialogUtils setData(final DyConfig.DyReplyWelcome welcome, DialogCallback callback) {

        this.data = welcome;
        this.callback = callback;
        this.setTitle(data.name);
        editText.setText(welcome.content);
        return this;
    }

    @Override
    protected void onSaveClick() {
        super.onSaveClick();
        String s = editText.getText().toString();
        if (TextUtils.isEmpty(s)) {
            Toast.makeText(getContext(), "不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        data.content = s;
        if (callback != null) {
            callback.callback(this, data);
        }
        dismiss();
    }

    @Override
    int getLayoutId() {
        return R.layout.dialog_welcome_view;
    }
}
