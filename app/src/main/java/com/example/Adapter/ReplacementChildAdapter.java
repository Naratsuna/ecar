package com.example.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.Entity.ReplacementItem;
import com.example.ecar.R;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReplacementChildAdapter extends RecyclerView.Adapter<ReplacementChildAdapter.ViewHolder>{

    private static final String TAG = "ReplacementGroupAdapter";
    private List<ReplacementItem> childItem;
    private int pos;
    private OnIconClickListener mClickListener;
    private View childView;

    public ReplacementChildAdapter(List<ReplacementItem> childItem) {
        this.childItem = childItem;
    }

    /**
     * 创建ViewHolder实例
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_extra_expand_child, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = holder.getAdapterPosition();
                childItem.get(pos).setNum(childItem.get(pos).getNum()+1);
                Toast.makeText(parent.getContext(),"添加成功",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: " + childItem.get(pos).getNum());
                mClickListener.clickListener(view,childItem.get(pos).getNum());
            }
        });
        holder.btn_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = holder.getAdapterPosition();
                if(childItem.get(pos).getNum() > 0){
                    childItem.get(pos).setNum(childItem.get(pos).getNum()-1);
                }else{
                    Toast.makeText(parent.getContext(),"数量为0，不可减少",Toast.LENGTH_SHORT).show();
                }
                mClickListener.clickListener(view,childItem.get(pos).getNum());
                Log.d(TAG, "onClick: " + childItem.get(pos).getNum());
            }
        });
        return holder;
    }

    /**
     * 每项加载时事件
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        ReplacementItem item = childItem.get(position);
        holder.tvTitle.setText(item.getName());
        //pos = position;
    }

    @Override
    public int getItemCount() {
        return childItem.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView count;
        View btn_add;
        View btn_reduce;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.itemChildName);
            count = itemView.findViewById(R.id.textView_count);
            btn_add = itemView.findViewById(R.id.btn_add);
            btn_reduce = itemView.findViewById(R.id.btn_reduce);
        }
    }

    public interface OnIconClickListener{
        void clickListener(View view,int num);
    }

    public void setIconClickListener(OnIconClickListener mClickListener){
        this.mClickListener = mClickListener;
    }

    public ReplacementItem getItem(int position){
        return childItem.get(position);
    }
}
