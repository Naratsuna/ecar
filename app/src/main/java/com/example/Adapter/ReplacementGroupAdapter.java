package com.example.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.Entity.ReplacementItem;
import com.example.ecar.R;
import com.example.ecar.SpacesItemDecorationLinearLayout;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.Util.PicUtil.dp2px;

public class ReplacementGroupAdapter extends RecyclerView.Adapter<ReplacementGroupAdapter.ViewHolder> {

    private static final String TAG="ReplacementGroupAdapter";
    private int pos;//当前组的位置，确认子列表的位置
    private List<String> groupItem;
    private List<List<ReplacementItem>> childItem;

    public ReplacementGroupAdapter(List<String> groupItem, List<List<ReplacementItem>> childItem) {
        this.groupItem = groupItem;
        this.childItem = childItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_extra_expand_father, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.group_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkBox.toggle();
                if(holder.rv.getVisibility() == View.GONE){
                    holder.rv.setVisibility(View.VISIBLE);
                }else{
                    holder.rv.setVisibility(View.GONE);//不占据空间
                }
            }
        });
        ReplacementChildAdapter adapter = new ReplacementChildAdapter(childItem.get(pos));
        LinearLayoutManager layoutManager = new LinearLayoutManager(parent.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter.setIconClickListener(onIconClickListener);
        holder.rv.setLayoutManager(layoutManager);
        holder.rv.setAdapter(adapter);
        holder.rv.addItemDecoration(new SpacesItemDecorationLinearLayout(0,0,0,(int)dp2px(parent.getContext(),10)));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(groupItem.get(position));
        holder.checkBox.setChecked(false);
        holder.rv.setVisibility(View.GONE);
        pos = position;
    }

    @Override
    public int getItemCount() {
        return groupItem.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        CheckBox checkBox;
        RelativeLayout group_layout;
        RecyclerView rv;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.groupName);
            checkBox = view.findViewById(R.id.checkBox_expand);
            group_layout = view.findViewById(R.id.group_layout);
            rv = view.findViewById(R.id.rv_child_layout);
        }
    }

    private ReplacementChildAdapter.OnIconClickListener onIconClickListener = new ReplacementChildAdapter.OnIconClickListener() {
        @Override
        public void clickListener(View view, int num) {
            TextView tv = view.findViewById(R.id.textView_count);
            tv.setText(String.valueOf(num));
        }
    };
}
