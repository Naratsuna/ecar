package com.example.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecar.ExecuteActivity;
import com.example.ecar.OrderDetailActivity;
import com.example.ecar.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.Entity.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> mOrderList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView orderImg;
        Button orderBtn;
        TextView type;
        TextView orderState;
        TextView countTime;
        View orderView;

        public ViewHolder(View view){
            super(view);
            orderImg = view.findViewById(R.id.smallAvatar);
            type = view.findViewById(R.id.car_type_tv);
            orderState = view.findViewById(R.id.state_hint);
            countTime = view.findViewById(R.id.countdown_time);
            orderBtn = view.findViewById(R.id.order_btn);
            orderView = view;
            //System.out.println("Order的ViewHolder");
        }
    }

    public OrderAdapter(List<Order> orderList){
        mOrderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        //点击响应不在此处
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //System.out.println("Order的onBindViewHolder");
        Order order = mOrderList.get(position);
        holder.orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"you click the order",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), OrderDetailActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        holder.orderImg.setImageResource(order.getAvatarId());
        holder.type.setText(order.getType());
        //设置调度状态
        holder.orderState.setText(order.getOrderState());
        //设置倒计时时间
        holder.countTime.setText(order.getCountTime());
    }

    @Override
    public int getItemCount() {
        //System.out.println("Order的getItemCount");
        return mOrderList.size();
    }
}
