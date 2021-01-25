package com.ehook.dy.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ehook.dy.R;
import com.ehook.dy.utils.BeanCloneUtil;
import com.evan.dy.api.model.DyConfig;
import com.evan.dy.api.model.DyConfig.DyReplyKeyValue;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeywordDialog extends DialogUtils {
    private final View tvAdd;
    private final LinearLayout llContent;
    private DyConfig.DyReplyKeyword data;
    private DialogCallback callback;
    private List<DyReplyKeyValue> keyValues;

    public ReplyKeywordDialog(@NonNull Context context) {
        super(context);
        tvAdd = contentView.findViewById(R.id.tv_add);
        llContent = contentView.findViewById(R.id.ll_content);
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (keyValues == null) {
                    keyValues = new ArrayList<>();
                }
                DyReplyKeyValue value = new DyReplyKeyValue();
                value.key = "代金券";
                value.content = "关注主播,领取代金券";
                keyValues.add(value);
                setKeyValue(keyValues);
            }
        });
    }

    public DialogUtils setData(final DyConfig.DyReplyKeyword data, DialogCallback callback) {
        this.data = data;
        this.callback = callback;
        this.setTitle(data.name);
        keyValues = BeanCloneUtil.cloneTo(data.data);
        setKeyValue(keyValues);
        return this;
    }

    private void setKeyValue(final List<DyReplyKeyValue> dyReplyKeyValues) {
        llContent.removeAllViews();
        if (dyReplyKeyValues != null) {
            for (final DyReplyKeyValue dyReplyKeyValue : dyReplyKeyValues) {
                View view = getLayoutInflater().inflate(R.layout.dialog_item_reply_keyword_view, null);
                view.findViewById(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dyReplyKeyValues.remove(dyReplyKeyValue);
                        keyValues = dyReplyKeyValues;
                        setKeyValue(dyReplyKeyValues);
                    }
                });
                EditText editTextKey = view.findViewById(R.id.edit_text_key);
                editTextKey.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        dyReplyKeyValue.key = charSequence.toString().trim();
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                EditText editTextValue = view.findViewById(R.id.edit_text_value);
                editTextValue.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        dyReplyKeyValue.content = charSequence.toString().trim();
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                editTextKey.setText(dyReplyKeyValue.key);
                editTextValue.setText(dyReplyKeyValue.content);
                llContent.addView(view);
            }
        }
    }

    @Override
    protected void onSaveClick() {
        super.onSaveClick();
        if (keyValues != null) {
            for (DyReplyKeyValue keyValue : keyValues) {
                if (keyValue != null) {
                    if (TextUtils.isEmpty(keyValue.key) && TextUtils.isEmpty(keyValue.content)) {
                        Toast.makeText(getContext(), "不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!TextUtils.isEmpty(keyValue.key) && TextUtils.isEmpty(keyValue.content)) {
                        Toast.makeText(getContext(), keyValue.key + "的回复不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(keyValue.key) && !TextUtils.isEmpty(keyValue.content)) {
                        Toast.makeText(getContext(), keyValue.content + "的关键词不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        }
        data.data = keyValues;
        if (callback != null) {
            callback.callback(this, data);
        }
        dismiss();
    }

    @Override
    int getLayoutId() {
        return R.layout.dialog_reply_keyword_view;
    }
}
