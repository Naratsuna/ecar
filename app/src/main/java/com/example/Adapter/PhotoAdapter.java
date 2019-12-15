package com.example.Adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.ecar.R;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.Fragment.TakePhotoFragment.MAXSIZE;

//参考https://www.jianshu.com/p/9c1741738d8f
public class PhotoAdapter extends RecyclerView.Adapter {
    private static final String TAG="PhotoAdapter";
    private List<String> mPhotoList;
    private OnImageClickListener onImageClickListener;
    private OnAddClickListener onAddClickListener;
    //定义两种类型
    private static final int VIEW = 1;
    private static final int ADD_VIEW = 2;

    public PhotoAdapter(List<String> mPhotoList) {
        this.mPhotoList = mPhotoList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton photoItem;

        public ViewHolder(@NonNull View view) {
            super(view);
            photoItem = view.findViewById(R.id.photo_item);
            Log.d(TAG, "ViewHolder: ");
        }
    }

    static class ViewHolderTwo extends RecyclerView.ViewHolder{
        FrameLayout fview;

        public ViewHolderTwo(@NonNull View view) {
            super(view);
            fview = view.findViewById(R.id.btn_add_pic);
            Log.d(TAG, "ViewHolderTwo: ");
        }
    }

    public void addItem(String photo,int position){
        mPhotoList.add(position,photo);
        notifyItemInserted(position);
        if (position != mPhotoList.size()) {
            notifyItemRangeChanged(position, mPhotoList.size() - position);
        }
    }

    //加在最后面
    public void addItem(String photo){
        Log.d(TAG, "addItem: " + mPhotoList.size());
        mPhotoList.add(photo);
        notifyItemInserted(mPhotoList.size());
    }

    public void removeItem(int position){
        mPhotoList.remove(position);
        notifyItemRemoved(position);
        if (position != mPhotoList.size()) {
            notifyItemRangeChanged(position, mPhotoList.size() - position);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW) {
            view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
            return new ViewHolder(view);
        } else {
            view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_footer, parent, false);
            return new ViewHolderTwo(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder,final int position) {
        if (holder instanceof ViewHolder) {
            String photo = mPhotoList.get(position);
            ((ViewHolder) holder).photoItem.setImageURI(Uri.parse(photo));
            ((ViewHolder) holder).photoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //holder.getAdapterPosition()可以获取当前点击的数据在adapter中的位置
                    onImageClickListener.clickListener(position);
                }
            });
        } else if (holder instanceof ViewHolderTwo) {
            ((ViewHolderTwo) holder).fview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAddClickListener.addClick();
                }
            });
        }
    }
    /**
     * 该函数是为了createViewHolder而写的，指应该生成的view的数量
     */
    @Override
    public int getItemCount() {
        //如果小于最大的图片就+1，因为要显示添加的图片
        if(mPhotoList.size() < MAXSIZE){
            return mPhotoList.size() + 1;
        }else{
            return mPhotoList.size();
        }
    }

    public int getItemCountReal(){
        return mPhotoList.size();
    }

    /**
     * 决定元素的布局使用哪种类型
     *
     * @param position 数据源的下标
     * @return 一个int型标志，传递给onCreateViewHolder的第二个参数 */
    @Override
    public int getItemViewType(int position) {
        if (isShowAdd(position)) {
            return ADD_VIEW ;
        } else {
            return VIEW;
        }
    }

    //根据 position 判断显示什么布局
    private boolean isShowAdd(int position) {
        //position=0 的时候  mPhotoList.size() 也等于0，返回 true，那么就是显示 添加 的图片
        //position =1，mPhotoList.size()=2 (position从0开始，返回 false)
        //position =8时，mPhotoList.size()=9，getItemCount()=9
        return position == mPhotoList.size();
    }

    public interface OnImageClickListener{
        void clickListener(int position);
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener){
        this.onImageClickListener = onImageClickListener;
    }

    public interface OnAddClickListener{
        void addClick();//添加照片
    }

    public void setOnAddClickListener(OnAddClickListener onAddClickListener){
        this.onAddClickListener = onAddClickListener;
    }

    public List<String> getmPhotoList() {
        return mPhotoList;
    }
}
