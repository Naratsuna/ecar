package com.example.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ecar.R;
import com.github.chrisbanes.photoview.PhotoView;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//参考https://www.jianshu.com/p/9c1741738d8f
public class PicPageAdapter extends RecyclerView.Adapter<PicPageAdapter.ViewHolder> {
    private List<String> photoList;
    private static final String TAG="PicPageAdapter";

    public PicPageAdapter(List<String> photoList) {
        this.photoList = photoList;
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<String> photoList) {
        this.photoList = photoList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        PhotoView imageView;

        public ViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.pic_page);
        }
    }

    public void removeItem(int position){
        photoList.remove(position);
        notifyItemRemoved(position);
        if (position != photoList.size()) {
            notifyItemRangeChanged(position, photoList.size() - position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String photo = photoList.get(position);
        holder.imageView.setImageURI(Uri.parse(photo));
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
