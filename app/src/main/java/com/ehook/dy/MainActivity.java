package com.ehook.dy;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ehook.dy.dialog.AutoClickDialog;
import com.ehook.dy.dialog.AutoSendGiftDialog;
import com.ehook.dy.dialog.AutoShoppingDialog;
import com.ehook.dy.dialog.DialogUtils;
import com.ehook.dy.dialog.LoginDialog;
import com.ehook.dy.dialog.ReplyGiftDialog;
import com.ehook.dy.dialog.ReplyKeywordDialog;
import com.ehook.dy.dialog.ReplySubscribeDialog;
import com.ehook.dy.dialog.SendBulletinDialog;
import com.ehook.dy.dialog.SendFanDialog;
import com.ehook.dy.dialog.WelcomeDialog;
import com.ehook.dy.model.LoginUser;
import com.ehook.dy.utils.UserManager;
import com.evan.dy.api.model.DyConfig;
import com.evan.dy.api.model.DyConfig.DyBaseConfig;
import com.evan.dy.floatingview.FloatingView;
import com.evan.dy.utils.ConfigUtil;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.yy.permission.EsayPermissions;
import com.yy.permission.OnPermission;
import com.yy.permission.Permission;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private TextView textTitle;
    private TextView tvWarm;
    private TextView text_about;
    private MyRecycleViewAdapter mAdapter;
    private ArrayList<DyBaseConfig> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.fragment_main);
        recyclerView = findViewById(R.id.recyclerView);
        textTitle = findViewById(R.id.text_title);
        tvWarm = findViewById(R.id.tv_warm);
        text_about = findViewById(R.id.text_about);
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                moveTaskToBack(true);
                finish();
            }
        });
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new MyRecycleViewAdapter();
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int pos, DyBaseConfig dConfig) {
                if (c()) return;
                switch (dConfig.name) {
                    case "欢迎语":
                        new WelcomeDialog(MainActivity.this)
                            .setData((DyConfig.DyReplyWelcome) dConfig, new DialogUtils.DialogCallback() {
                                @Override
                                public void callback(Dialog dialog, Object o) {
                                    ConfigUtil.getInstance().updateAndNotifyFromModule(MainActivity.this);
                                }
                            }).show();
                        break;
                    case "定时公告":
                        new SendBulletinDialog(MainActivity.this)
                            .setData((DyConfig.DySendBulletin) dConfig, new DialogUtils.DialogCallback() {
                                @Override
                                public void callback(Dialog dialog, Object o) {
                                    ConfigUtil.getInstance().getInstance().updateAndNotifyFromModule(MainActivity.this);
                                }
                            }).show();
                        break;
                    case "直播点赞":
                        new AutoClickDialog(MainActivity.this)
                            .setData((DyConfig.DyAutoClick) dConfig, new DialogUtils.DialogCallback() {
                                @Override
                                public void callback(Dialog dialog, Object o) {
                                    ConfigUtil.getInstance().getInstance().updateAndNotifyFromModule(MainActivity.this);
                                }
                            }).show();
                        break;
                    case "自动刷礼物":
                        new AutoSendGiftDialog(MainActivity.this)
                            .setData((DyConfig.DyAutoSendGift) dConfig, new DialogUtils.DialogCallback() {
                                @Override
                                public void callback(Dialog dialog, Object o) {
                                    ConfigUtil.getInstance().updateAndNotifyFromModule(MainActivity.this);
                                }
                            }).show();
                        break;
                    case "自动刷单":
                        new AutoShoppingDialog(MainActivity.this)
                            .setData((DyConfig.DyAutoShopping) dConfig, new DialogUtils.DialogCallback() {
                                @Override
                                public void callback(Dialog dialog, Object o) {
                                    ConfigUtil.getInstance().updateAndNotifyFromModule(MainActivity.this);
                                }
                            }).show();
                        break;
                    case "礼物回复":
                        new ReplyGiftDialog(MainActivity.this)
                            .setData((DyConfig.DyReplyGift) dConfig, new DialogUtils.DialogCallback() {
                                @Override
                                public void callback(Dialog dialog, Object o) {
                                    ConfigUtil.getInstance().updateAndNotifyFromModule(MainActivity.this);
                                }
                            }).show();
                        break;
                    case "关键词回复":
                        new ReplyKeywordDialog(MainActivity.this)
                            .setData((DyConfig.DyReplyKeyword) dConfig, new DialogUtils.DialogCallback() {
                                @Override
                                public void callback(Dialog dialog, Object o) {
                                    ConfigUtil.getInstance().updateAndNotifyFromModule(MainActivity.this);
                                }
                            }).show();
                        break;
                    case "关注回复":
                        new ReplySubscribeDialog(MainActivity.this)
                            .setData((DyConfig.DyReplySubscribe) dConfig, new DialogUtils.DialogCallback() {
                                @Override
                                public void callback(Dialog dialog, Object o) {
                                    ConfigUtil.getInstance().updateAndNotifyFromModule(MainActivity.this);
                                }
                            }).show();
                        break;
                    case "粉丝群发消息":
                        new SendFanDialog(MainActivity.this)
                            .setData((DyConfig.DySendFan) dConfig, new DialogUtils.DialogCallback() {
                                @Override
                                public void callback(Dialog dialog, Object o) {
                                    ConfigUtil.getInstance().updateAndNotifyFromModule(MainActivity.this);
                                }
                            }).show();
                        break;

                }
            }
        });
        intiPermission();
        tvWarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (d()) {

                }
            }
        });
        text_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "完善中...", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean d() {
        if (!UserManager.getInstance().isValidLogin(MainActivity.this) || !UserManager.getInstance().isValidTime(MainActivity.this)) {
            if (list != null) {
                for (DyBaseConfig dyBaseConfig : list) {
                    dyBaseConfig.status = 0;
                }
                ConfigUtil.getInstance().updateAndNotifyFromModule(MainActivity.this);
            }
            new LoginDialog(MainActivity.this)
                .setData(new DialogUtils.DialogCallback() {
                    @Override
                    public void callback(Dialog dialog, Object o) {
                        if (o instanceof String) {
                            LoginUser user = UserManager.getInstance().getUser(MainActivity.this);
                            user.token = (String) o;
                            UserManager.getInstance().setLoginUser(MainActivity.this, user);
                        }
                    }
                }).show();
            return true;
        }
        return false;
    }
    private boolean c() {
        if (!UserManager.getInstance().isValidLogin(MainActivity.this) && !UserManager.getInstance().isValidTime(MainActivity.this)) {
            if (list != null) {
                for (DyBaseConfig dyBaseConfig : list) {
                    dyBaseConfig.status = 0;
                }
                ConfigUtil.getInstance().updateAndNotifyFromModule(MainActivity.this);
            }
            new LoginDialog(MainActivity.this)
                .setData(new DialogUtils.DialogCallback() {
                    @Override
                    public void callback(Dialog dialog, Object o) {
                        if (o instanceof String) {
                            LoginUser user = UserManager.getInstance().getUser(MainActivity.this);
                            user.token = (String) o;
                            UserManager.getInstance().setLoginUser(MainActivity.this, user);
                            boolean validLogin = UserManager.getInstance().isValidLogin(MainActivity.this);
                            tvWarm.setText(validLogin ? "欢迎使用慧播助手" : "可试用2个小时 点击注册");
                        }
                    }
                }).show();
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean validLogin = UserManager.getInstance().isValidLogin(MainActivity.this);
        tvWarm.setText(validLogin ? "欢迎使用慧播助手" : "可试用2个小时 点击注册");
        if (checkHook()) {
            textTitle.setText("场控设置(正常)");
        }
    }



    private void setData() {
        DyConfig dyConfig = ConfigUtil.getInstance().getDyConfig();
        list = new ArrayList<>();
        list.add(dyConfig.replyWelcome);
        list.add(dyConfig.sendBulletin);
        list.add(dyConfig.replySubscribe);
        list.add(dyConfig.replyGift);
        list.add(dyConfig.replyKeyword);
        list.add(dyConfig.autoClick);
        list.add(dyConfig.autoShopping);
        list.add(dyConfig.autoSendGift);
//        list.add(dyConfig.sendFan);
        mAdapter.setList(list);
    }

    interface OnItemClickListener {

        void onItemClick(int pos, DyBaseConfig o);
    }

    public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyHolder> {

        private List<DyBaseConfig> mList;//数据源
        private OnItemClickListener onItemClickListener;

        public void setList(List<DyBaseConfig> mList) {
            this.mList = mList;
            notifyDataSetChanged();
        }

        @Override
        public MyRecycleViewAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_fragment, parent, false);
            MyRecycleViewAdapter.MyHolder holder = new MyRecycleViewAdapter.MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyRecycleViewAdapter.MyHolder holder, final int position) {
            final DyBaseConfig mainItemBean = mList.get(position);
            if (mainItemBean == null) {
                return;
            }
            holder.textView.setText(mainItemBean.name);
            holder.switchView.setChecked(mainItemBean.status == 1);
            holder.switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if (c()) {
                        holder.switchView.setChecked(false);
                        return;
                    }
                    mainItemBean.status = b ? 1 : 0;
                    ConfigUtil.getInstance().updateAndNotifyFromModule(MainActivity.this);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, mainItemBean);
                    }
                }
            });
        }

        //获取数据源总的条数
        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        /**
         * 自定义的ViewHolder
         */
        class MyHolder extends RecyclerView.ViewHolder {

            TextView textView;
            SwitchCompat switchView;

            public MyHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.text);
                switchView = itemView.findViewById(R.id.switch_view);
            }
        }
    }

    private void intiPermission() {
        EsayPermissions.with(this)
            .constantRequest()
            .permission(Permission.READ_PHONE_STATE, Permission.WRITE_EXTERNAL_STORAGE)
            .request(new OnPermission() {
                @Override
                public void hasPermission(List<String> granted, boolean isAll) {
                    if (isAll) {
                        setData();
                    }
                }

                @Override
                public void noPermission(List<String> denied, boolean quick) {
                }
            });
    }

    public boolean checkHook() {
        return false;
    }
    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(true);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    public boolean moveTaskToBack(boolean nonRoot) {
        if (!nonRoot) {
            if (!isTaskRoot()) {
                return false;
            }
        }
        return super.moveTaskToBack(nonRoot);
    }
}
