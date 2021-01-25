package com.ehook.dy;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ehook.dy.view.TabStripView;
import com.yy.permission.EsayPermissions;
import com.yy.permission.OnPermission;
import com.yy.permission.Permission;

import java.util.List;

public class MainActivityBack extends AppCompatActivity {
    TabStripView navigateTabBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigateTabBar = findViewById(R.id.navigateTabBar);
        navigateTabBar.onRestoreInstanceState(savedInstanceState);
        navigateTabBar.addTab(MainFragment.class, new TabStripView.TabParam(R.drawable.tab_home_off, R.drawable.tab_home_on, "首页", "/main/home"), 1);
        navigateTabBar.addTab(MyFragment.class, new TabStripView.TabParam(R.drawable.tab_my_off, R.drawable.tab_my_on, "我的", "/main/mine"), 1);
        navigateTabBar.setTabSelectListener(new TabStripView.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabStripView.ViewHolder holder) {
                switch (holder.tabIndex) {
                    case 0:
                        break;
                    case 1:
                        break;
                }
            }
        });
        intiPermission();
    }

    private void intiPermission() {
        EsayPermissions.with(this)
            .constantRequest()
            .permission(Permission.READ_PHONE_STATE, Permission.WRITE_EXTERNAL_STORAGE)
            .request(new OnPermission() {
                @Override
                public void hasPermission(List<String> granted, boolean isAll) {
                }

                @Override
                public void noPermission(List<String> denied, boolean quick) {
                }
            });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkHook()) {

        }
    }

    public boolean checkHook() {
        return false;
    }
}
