package com.ehook.dy.dialog;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ehook.dy.R;
import com.ehook.dy.utils.KeyBordUtil;
import com.ehook.dy.utils.UserManager;
import com.evan.dy.api.model.DyConfig;

import a.c.e.f.DeviceUtils;
import a.c.e.h.f;

public class LoginDialog extends DialogUtils {
    EditText editText;
    TextView tvIeme;
    TextView tvSend;
    TextView tvCopy;
    private DialogCallback callback;

    public LoginDialog(@NonNull Context context) {
        super(context);
        editText = contentView.findViewById(R.id.edit_text);
        tvSend = contentView.findViewById(R.id.tv_send);
        tvIeme = contentView.findViewById(R.id.tv_ieme);
        tvCopy = contentView.findViewById(R.id.tv_copy);
        tvSave.setText("取消");
    }

    public DialogUtils setData(final DialogCallback callback) {
        setTitle("注册");
        String imei = DeviceUtils.getIMEI(getContext());
        tvIeme.setText(imei);
        this.callback = callback;
        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imee = tvIeme.getText().toString().trim();
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", imee);
// 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(getContext(), "复制成功", Toast.LENGTH_SHORT).show();
            }
        });
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trim = editText.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                    Toast.makeText(getContext(), "注册码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!f.INSTANCE.c(getContext(), trim)) {
                    Toast.makeText(getContext(), "注册码不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                dismiss();
                if (callback != null) {
                    KeyBordUtil.hideSoftKeyboard(view);
                    callback.callback(LoginDialog.this, trim);
                }
            }
        });
        return this;
    }

    @Override
    protected void onSaveClick() {
        super.onSaveClick();
        dismiss();
    }

    @Override
    int getLayoutId() {
        return R.layout.dialog_login_view;
    }
}
