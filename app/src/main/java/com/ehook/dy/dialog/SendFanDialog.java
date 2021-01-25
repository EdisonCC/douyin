package com.ehook.dy.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ehook.dy.R;
import com.evan.dy.api.model.DyConfig;

import java.util.ArrayList;
import java.util.List;

public class SendFanDialog extends DialogUtils {
    EditText editTextNum;
    EditText editTextTime;
    EditText editTextContent;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    private DyConfig.DySendFan data;
    private DialogCallback callback;
    private ArrayList<String> filterList;

    public SendFanDialog(@NonNull Context context) {
        super(context);
        editTextNum = contentView.findViewById(R.id.edit_text_num);
        editTextTime = contentView.findViewById(R.id.edit_text_time);
        editTextContent = contentView.findViewById(R.id.edit_text);
        text1 = contentView.findViewById(R.id.text1);
        text2 = contentView.findViewById(R.id.text2);
        text3 = contentView.findViewById(R.id.text3);
        text4 = contentView.findViewById(R.id.text4);
    }


    /**
     * * gender -1 全部 1 男 2女
     *      * followStatus -1全部 0 粉丝 2相互关注
     * @param data
     * @param callback
     * @return
     */
    public DialogUtils setData(final DyConfig.DySendFan data, DialogCallback callback) {

        this.data = data;
        this.callback = callback;
        this.setTitle(data.name);
        editTextNum.setText(data.max_send_num + "");
        editTextTime.setText(data.interval + "");
        filterList = new ArrayList<>();
        if (data.subscribe_type == -1) {
            filterList.add("1");
            filterList.add("2");
        } else if (data.subscribe_type == 0) {
            filterList.add("2");
        } else {
            filterList.add("1");
        }
        if (data.sex_type == -1) {
            filterList.add("3");
            filterList.add("4");
        } else if (data.sex_type == 2) {
            filterList.add("4");
        } else {
            filterList.add("3");
        }
        setFilterData(filterList);
        setTextSelect(text1);
        setTextSelect(text2);
        setTextSelect(text3);
        setTextSelect(text4);
        return this;
    }

    private void setTextSelect(final TextView text) {
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) text.getTag();
                if (tag == 1) {
                    try {
                        filterList.remove(text.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    text.setTag(0);
                    text.setSelected(false);
                } else {
                    text.setTag(1);
                    filterList.add(text.getText().toString());
                    text.setSelected(true);
                }
            }
        });
    }

    private void setFilterData(List<String> filterList) {
        if (filterList != null) {
            text1.setSelected(false);
            text2.setSelected(false);
            text3.setSelected(false);
            text4.setSelected(false);
            text1.setTag(0);
            text2.setTag(0);
            text3.setTag(0);
            text4.setTag(0);
            for (String giftDatum : filterList) {
                switch (giftDatum) {
                    case "1":
                        text1.setSelected(true);
                        text1.setTag(1);
                        break;
                    case "2":
                        text2.setSelected(true);
                        text2.setTag(1);
                        break;
                    case "3":
                        text3.setTag(1);
                        text3.setSelected(true);
                        break;
                    case "4":
                        text4.setSelected(true);
                        text4.setTag(1);
                        break;
                }
            }
        }
    }

    @Override
    protected void onSaveClick() {
        super.onSaveClick();
        if (filterList != null) {
//            * gender -1 全部 1 男 2女
//            *  followStatus -1全部 0 粉丝 2相互关注
            if (filterList.contains("1")) {
                data.subscribe_type = 2;
            }
            if (filterList.contains("2")) {
                data.subscribe_type = 0;
            }
            if (filterList.contains("2") && filterList.contains("1")) {
                data.subscribe_type = -1;
            }
            if (filterList.contains("3")) {
                data.sex_type = 1;
            }
            if (filterList.contains("4")) {
                data.sex_type = 2;
            }
            if (filterList.contains("3") && filterList.contains("4")) {
                data.sex_type = -1;
            }
        }
        String s = editTextContent.getText().toString().trim();
        if (TextUtils.isEmpty(s)) {
            Toast.makeText(getContext(), "发送内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String time = editTextTime.getText().toString().trim();
        if (TextUtils.equals(time, "0")) {
            Toast.makeText(getContext(), "时间间隔不能为0", Toast.LENGTH_SHORT).show();
            return;
        }
        data.content = s;
        data.interval = Integer.parseInt(time);
        data.max_send_num = Integer.parseInt(editTextTime.getText().toString().trim());
        if (callback != null) {
            callback.callback(this, data);
        }
        dismiss();
    }

    @Override
    int getLayoutId() {
        return R.layout.dialog_send_fan_view;
    }
}
