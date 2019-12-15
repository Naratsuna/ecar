package com.example.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.ecar.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Entity.StepButton;

public class StepButtonAdapter extends RecyclerView.Adapter<StepButtonAdapter.ViewHolder>{
    private static final String TAG="StepButtonAdapter";

    private List<StepButton> mStep;
    private onButtonClickListener onButtonClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder{

        RadioButton rb;

        public ViewHolder(View view){
            super(view);
            rb = view.findViewById(R.id.radioButton);
            Log.d(TAG, "ViewHolder: ");
        }
    }

    public StepButtonAdapter(List<StepButton> mStep){this.mStep = mStep;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        // LayoutInflater.from()根据布局实例化一个对象，然后调用inflate方法把子布局加载到当前布局parent中
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_radiobutton,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        StepButton stepButton = mStep.get(position);
        holder.rb.setText(stepButton.getNum());
        holder.rb.setChecked(stepButton.isCheck());
        holder.rb.setEnabled(stepButton.isEnable());
        if(stepButton.isCheck()){
            holder.rb.setAlpha(1);
        }else{
            holder.rb.setAlpha((float)0.3);
        }
        holder.rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.clickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStep.size();
    }

    public interface onButtonClickListener{
        void clickListener(int position);
    }

    public void setOnButtonClickListener(onButtonClickListener onButtonClickListener){
        this.onButtonClickListener = onButtonClickListener;
    }

    public StepButton getItem(int position){
        return mStep.get(position-1);
    }
}
