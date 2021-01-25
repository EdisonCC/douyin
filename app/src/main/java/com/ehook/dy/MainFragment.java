package com.ehook.dy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ehook.dy.model.MainItemBean;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private MyRecycleViewAdapter mAdapter;
    TextView textTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        textTitle = view.findViewById(R.id.text_title);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new MyRecycleViewAdapter();
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mAdapter);

        setData();
    }

    @Override
    public void onResume() {
        super.onResume();
        textTitle.setText(checkHook() ? "欢迎使用" : "环境异常");
    }

    public boolean checkHook() {
        return false;
    }

    private void setData() {
        List<MainItemBean> list = new ArrayList<>();
        list.add(new MainItemBean("定时公共", true));
        list.add(new MainItemBean("欢迎语", true));
        list.add(new MainItemBean("关注回复", true));
        list.add(new MainItemBean("礼物回复", true));
        list.add(new MainItemBean("关注词回复", true));
        list.add(new MainItemBean("直播间点赞", true));
        list.add(new MainItemBean("自动刷单", true));
        list.add(new MainItemBean("自动刷礼物", true));
        list.add(new MainItemBean("群发消息", true));
        mAdapter.setList(list);
    }


    public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyHolder> {

        private List<MainItemBean> mList;//数据源

        public void setList(List<MainItemBean> mList) {
            this.mList = mList;
            notifyDataSetChanged();
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_fragment, parent, false);
            MyHolder holder = new MyHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            MainItemBean mainItemBean = mList.get(position);
            holder.textView.setText(mainItemBean.title);
            holder.switchView.setChecked(mainItemBean.isOpen);
        }

        //获取数据源总的条数
        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        /**
         * 自定义的ViewHolder
         */
        class MyHolder extends RecyclerView.ViewHolder {

            TextView textView;
            Switch switchView;

            public MyHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.text);
                switchView = itemView.findViewById(R.id.switch_view);
            }
        }
    }

}
