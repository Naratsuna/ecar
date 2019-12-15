package com.example.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapter.ReplacementChildAdapter;
import com.example.Adapter.ReplacementGroupAdapter;
import com.example.Entity.ReplacementItem;
import com.example.ecar.ExecuteActivity;
import com.example.ecar.R;
import com.example.ecar.SpacesItemDecorationLinearLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.Util.PicUtil.dp2px;

public class ExtraCostFragment extends BaseFragment{
    private static final String TAG="ExtraCostFragment";
    private RecyclerView recyclerView;
    private ReplacementGroupAdapter adapter;
    private Button btn_submit;
    private List<String> groupItem = new ArrayList<>();
    private List<List<ReplacementItem>> childItem = new ArrayList<List<ReplacementItem>>();
    private int pos;//记录二次列表在适配器中的位置，获取实例
    private View view;
    private FragmentConfig config = new FragmentConfig(false,"额外费用",true,new PaymentFragment());//初始化top

    @Override
    public FragmentConfig getConfig() {
        return config;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_extra_cost,container,false);
            initData();
            initView(view);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if(parent != null){
            parent.removeView(view);
        }
        Log.d(TAG, "onCreateView: ");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.replacement_group_layout);
        btn_submit = view.findViewById(R.id.btn_submit_extra);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextFragment();
            }
        });

        ReplacementGroupAdapter adapter = new ReplacementGroupAdapter(groupItem,childItem);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecorationLinearLayout(0,0,0,(int)dp2px(getContext(),10)));
    }

    private void initData(){
        String[] group = {"轮胎配件","车窗配件","车门配件","车盖配件"};
        String[][] child = {
                {"配件1","配件2","配件3"},
                {"配件4","配件5","配件6"},
                {"配件7","配件8","配件9"},
                {"配件10","配件11","配件12"},
        };
        for(int i=0;i<child.length;i++){
            List<ReplacementItem> items = new ArrayList<>();
            for(int j=0;j < child[i].length;j++){
                ReplacementItem item = new ReplacementItem(child[i][j],0);
                items.add(item);
            }
            childItem.add(items);
        }
        for(int i=0;i != group.length;i++){
            groupItem.add(group[i]);
        }
    }
}
