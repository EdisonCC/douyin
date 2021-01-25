package com.ehook.dy.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ehook.dy.R;
import com.evan.dy.api.model.DyConfig;

public class SendBulletinDialog extends DialogUtils {
    private final EditText editTextTime;
    EditText editText;
    private DyConfig.DySendBulletin data;
    private DialogCallback callback;

    public SendBulletinDialog(@NonNull Context context) {
        super(context);
        editText = contentView.findViewById(R.id.edit_text);
        editTextTime = contentView.findViewById(R.id.edit_text_time);
    }

    public DialogUtils setData(final DyConfig.DySendBulletin data, DialogCallback callback) {
        this.data = data;
        this.callback = callback;
        this.setTitle(data.name);
        editText.setText(data.content);
        editTextTime.setText(data.interval + "");
        return this;
    }

    @Override
    protected void onSaveClick() {
        super.onSaveClick();
        String s = editText.getText().toString().trim();
        if (TextUtils.isEmpty(s)) {
            Toast.makeText(getContext(), "公告内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        data.content = s;
        String time = editTextTime.getText().toString().trim();
        if (TextUtils.equals("0", time) || TextUtils.isEmpty("")) {
            Toast.makeText(getContext(), "公告时间不能为0", Toast.LENGTH_SHORT).show();
            return;
        }
        data.interval = Integer.parseInt(time);
        if (callback != null) {
            callback.callback(this, data);
        }
        dismiss();
    }

    @Override
    int getLayoutId() {
        return R.layout.dialog_send_bulletin_view;
    }
}
