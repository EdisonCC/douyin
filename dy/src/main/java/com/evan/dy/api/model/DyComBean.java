package com.evan.dy.api.model;

import android.text.TextUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DyComBean implements Serializable {
    /**
     * 单日刷币量
     */
    public DayCoinNum dayCoinNumBean;

    public int getDayCoinNum() {
        if (dayCoinNumBean == null) {
            return 0;
        }
        String curDay = getCurDay();
        if (TextUtils.equals(dayCoinNumBean.key, curDay)) {
            return dayCoinNumBean.dayCoinNum;
        } else {
            return 0;
        }
    }

    public void setDayCoinNum(int dayCoinNum) {
        if (dayCoinNumBean == null) {
            dayCoinNumBean = new DayCoinNum();
        }
        String curDay = getCurDay();
        dayCoinNumBean.key = curDay;
        dayCoinNumBean.dayCoinNum = dayCoinNum;
    }

    public class DayCoinNum implements Serializable {
        /**
         * 单日刷币量
         */
        public int dayCoinNum;
        public String key;
    }

    public static String getCurDay() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        return sf.format(c.getTime());
    }
}
