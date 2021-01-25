package com.ehook.dy.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ehook.dy.R;
import com.ehook.dy.utils.BeanCloneUtil;
import com.evan.dy.api.model.DyConfig;

import java.util.ArrayList;
import java.util.List;

public class AutoSendGiftDialog extends DialogUtils {
    EditText editTextTime;
    EditText editTextNum;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    TextView text6;
    private DyConfig.DyAutoSendGift data;
    private DialogCallback callback;
    private List<String> cloneGift;

    public AutoSendGiftDialog(@NonNull Context context) {
        super(context);
        editTextTime = contentView.findViewById(R.id.edit_text_time);
        editTextNum = contentView.findViewById(R.id.edit_text_num);
        text1 = contentView.findViewById(R.id.text1);
        text2 = contentView.findViewById(R.id.text2);
        text3 = contentView.findViewById(R.id.text3);
        text4 = contentView.findViewById(R.id.text4);
        text5 = contentView.findViewById(R.id.text5);
        text6 = contentView.findViewById(R.id.text6);
    }

    public DialogUtils setData(final DyConfig.DyAutoSendGift data, DialogCallback callback) {
        this.data = data;
        this.callback = callback;
        this.setTitle(data.name);
        editTextNum.setText(data.interval + "");
        editTextTime.setText(data.threshold + "");
        try {
            cloneGift = BeanCloneUtil.cloneTo(data.gift_json);
        } catch (Exception e) {
        }
        setGiftData(cloneGift);
        setTextSelect(text1);
        setTextSelect(text2);
        setTextSelect(text3);
        setTextSelect(text4);
        setTextSelect(text5);
        setTextSelect(text6);
        return this;
    }

    private void setTextSelect(final TextView text) {
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) text.getTag();
                if (tag == 1) {
                    try {
                        cloneGift.remove(text.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    text.setTag(0);
                    text.setSelected(false);
                } else {
                    text.setTag(1);
                    cloneGift.add(text.getText().toString());
                    text.setSelected(true);
                }
            }
        });
    }

    private void setGiftData(List<String> giftData) {
        if (giftData != null) {
            text1.setSelected(false);
            text2.setSelected(false);
            text3.setSelected(false);
            text4.setSelected(false);
            text5.setSelected(false);
            text6.setSelected(false);
            text1.setTag(0);
            text2.setTag(0);
            text3.setTag(0);
            text4.setTag(0);
            text5.setTag(0);
            text6.setTag(0);
            for (String giftDatum : giftData) {
                switch (giftDatum) {
                    case "小心心":
                        text1.setSelected(true);
                        text1.setTag(1);
                        break;
                    case "你最好看":
                        text2.setSelected(true);
                        text2.setTag(1);
                        break;
                    case "抖音":
                        text3.setTag(1);
                        text3.setSelected(true);
                        break;
                    case "樱花":
                        text4.setSelected(true);
                        text4.setTag(1);
                        break;
                    case "甜甜圈":
                        text5.setTag(1);
                        text5.setSelected(true);
                        break;
                    case "好想吃":
                        text6.setTag(1);
                        text6.setSelected(true);
                        break;
                }
            }
        }
    }

    @Override
    protected void onSaveClick() {
        super.onSaveClick();
        String s = editTextTime.getText().toString().trim();
        if (TextUtils.equals("0", s)) {
            Toast.makeText(getContext(), "触发时间不能为0", Toast.LENGTH_SHORT).show();
            return;
        }
        String num = editTextNum.getText().toString().trim();
        if (TextUtils.equals("0", num)) {
            Toast.makeText(getContext(), "抖币数不能为0", Toast.LENGTH_SHORT).show();
            return;
        }
        data.interval = Integer.parseInt(s);
        data.threshold = Integer.parseInt(num);
        if (cloneGift == null) {
            Toast.makeText(getContext(), "选择礼物", Toast.LENGTH_SHORT).show();
            return;
        }
        data.gift_json = cloneGift;
        if (callback != null) {
            callback.callback(this, data);
        }
        dismiss();
    }

    @Override
    int getLayoutId() {
        return R.layout.dialog_auto_send_gift_view;
    }
}
