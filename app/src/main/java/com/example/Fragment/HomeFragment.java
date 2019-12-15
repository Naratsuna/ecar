package com.example.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.example.ecar.R;
import java.util.ArrayList;
import java.util.List;
import com.example.Adapter.OrderAdapter;
import com.example.Entity.Order;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private List<Order> mOrderList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //  处理订单统计
        Button total_btn =  getActivity().findViewById(R.id.total_btn);
        Button confirm_btn = getActivity().findViewById(R.id.confirm_btn);
        Button finished_btn = getActivity().findViewById(R.id.finished_btn);
        total_btn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
        finished_btn.setOnClickListener(this);

        //处理recycleView
        initOrder();
        RecyclerView recyclerView = getActivity().findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        OrderAdapter adapter = new OrderAdapter(mOrderList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.total_btn:
                Toast.makeText(getActivity(),"You click the total_btn",Toast.LENGTH_SHORT).show();
                break;
            case R.id.confirm_btn:
                Toast.makeText(getActivity(),"You click the confirm_btn",Toast.LENGTH_SHORT).show();
                break;
            case R.id.finished_btn:
                Toast.makeText(getActivity(),"You click the finished_btn",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void initOrder(){
        int Num = 8;
        Order[] orders = new Order[Num];
        for(int i=0;i<Num;i++){
            orders[i] = new Order("拖车","等待技师接单",R.drawable.avatar,"5:00");
            mOrderList.add(orders[i]);
        }
    }
}
