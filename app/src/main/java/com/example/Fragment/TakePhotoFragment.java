package com.example.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.Adapter.PhotoAdapter;
import com.example.ecar.PicPageActivity;
import com.example.ecar.R;
import com.example.ecar.SpacesItemDecoration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import top.zibin.luban.OnRenameListener;

public class TakePhotoFragment extends BaseFragment implements View.OnClickListener{

    public static final String TAG="TakePhotoFragment";
    public static final int TAKE_PHOTO = 1;
    public static final int SHOW_PIC = 2;
    public static final int OPEN_ALBUM = 4;
    public static final int SPAN_COUNT  = 3;//grid的列数
    public static final int MAXSIZE = 9;//最多可以添加的照片数
    private boolean isTakePhoto = false;//判断该照片是修改还是新增

    private Uri imageUri;//为生成图片而生成的
    private File outputImage;//输出文件
    private PhotoAdapter adapter;
    private RecyclerView rv;
    private EditText text_desc;
    private Button btn_submit;
    private GridLayoutManager layoutManager;
    private List<String> originPhotoList = new ArrayList<>();//压缩前的照片

    void initView(View view){
        btn_submit = view.findViewById(R.id.btn_submit);
        text_desc = view.findViewById(R.id.text_desc);
        rv = view.findViewById(R.id.photo_item_rv);
        btn_submit.setOnClickListener(this);
    }

    void initRV(List<String> photoList){
        //recycleView的初始化
        adapter = new PhotoAdapter(photoList);
        layoutManager = new GridLayoutManager(getContext(),SPAN_COUNT );
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        if(rv.getItemDecorationCount() == 0){//防止重复添加
            rv.addItemDecoration(new SpacesItemDecoration(SPAN_COUNT,getSpace(),false));
        }
        adapter.setOnImageClickListener(onImageClickListener);
        adapter.setOnAddClickListener(onAddClickListener);
    }

    //命名要根据服务器时间
    private String namePhoto(){
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        String fileTime = time.format(date);
        return  fileTime + ".jpg";
    }

    //根据Item的大小计算RecycleView间距的大小
    private int getSpace(){
        Context context = Objects.requireNonNull(getContext());
        final float widthdpi = context.getResources().getDisplayMetrics().widthPixels;
        final float size = context.getResources().getDimension(R.dimen.size_pic_small);
        final float hspace = context.getResources().getDimension(R.dimen.margin_of_tolls);
        int space = (int)((widthdpi - (hspace * 2)) - (size * SPAN_COUNT)) / (SPAN_COUNT - 1);
        return space;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            default:
        }
    }

    private void takePhoto(){
        String fileName = namePhoto();
        outputImage = new File(Objects.requireNonNull(getActivity()).getExternalCacheDir(),fileName);
        try{
            if(outputImage.createNewFile()){
                outputImage.delete();//文件存在时删除
                //Log.d(TAG, "takePhoto: 文件出现重名！请检查！");
            }
            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(Objects.requireNonNull(getContext()),"com.example.ecar.fileprovider",outputImage);
        }else{
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    void picSave(){
        if(outputImage != null){
            Log.d(TAG, "picSave "+ outputImage.getPath()+ " " + text_desc.getText().toString() + isTakePhoto);
            String photo =outputImage.getPath();
            originPhotoList.add(photo);//用于传输给viewPager，碎片则显示缩略图
            lubanCompress(photo);//压缩后放入
        }
    }

    private PhotoAdapter.OnImageClickListener onImageClickListener = new PhotoAdapter.OnImageClickListener(){
        @Override
        public void clickListener(int position) {//响应内部图片点击事件
            Log.d(TAG, "clickListener: position" + position);
            Intent intent = PicPageActivity.getIntent(getContext(),adapter.getmPhotoList(),adapter.getItemCountReal(),position);
            //Intent intent = PicPageActivity.getIntent(getContext(),originPhotoList,adapter.getItemCountReal(),position);
            startActivityForResult(intent,SHOW_PIC);
        }
    };

    private PhotoAdapter.OnAddClickListener onAddClickListener = new PhotoAdapter.OnAddClickListener() {
        @Override
        public void addClick() {
            //takePhoto();//这里只负责启动相机，相机之后的行为由各自的碎片完成
            takePhoto();
        }
    };

    void setTextHint(String hint) {
        text_desc.setHint(hint);
    }


    @SuppressWarnings("unchecked")
    private void lubanCompress(final List<String> photoList){
        final List<String> photoListCopy = (List<String>) ((ArrayList) photoList).clone();
        final List<String> newPhotoList = (List<String>) new ArrayList<>().clone();
        Log.d("lubanLog","SIZE"+photoList.size()+"");
        for (int i = 0; i<photoListCopy.size(); i++){//压缩所有图片
            //在这里做图片压缩
            //得到newPhotoList
            final int index = i;
            File oldFile = new File(photoListCopy.get(i));
            Log.d("lubanLog","old/"+"第"+i+"个图片的大小为："+oldFile.length()/1024+"KB");
            Log.d("lubanLog","old/"+"第"+i+"个图片的路径为："+photoListCopy.get(i));
            Luban.with(getContext()) // 初始化
                    .load(oldFile) // 要压缩的图片
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(File newFile) {
                            // 压缩成功后调用，返回压缩后的图片文件
                            // 获取返回的图片地址 newfile
                            String newPath = newFile.getAbsolutePath();
                            String photo = newPath;
                            newPhotoList.add(index,photo);
                            // 输出图片的大小
                            Log.d("lubanLog","new/"+"第"+index+"个图片的大小为："+newFile.length()/1024+"KB");
                            Log.d("lubanLog","new/"+"第"+index+"个图片的路径为："+newPhotoList.get(index));
                            Log.d("lubanLog",newPhotoList.size()+"///");
                            if (newPhotoList.size() == photoListCopy.size()){
                                compressFinish(newPhotoList);
                            }
                        }
                        @Override
                        public void onError(Throwable e) {
                        }
                    }).launch(); // 启动压缩
        }
    }

    private void compressFinish(List<String> photoList){}

    private void compressFinish(String photo){
        adapter.addItem(photo);
    }

    private void lubanCompress(final String photo){
        final File oldFile = new File(photo);
        Log.d(TAG,"old/"+"图片的大小为："+oldFile.length()/1024+"KB");
        Log.d(TAG,"old/"+"图片的路径为："+photo);
        Luban.with(getContext()) // 初始化
                .load(oldFile) // 要压缩的图片
                .putGear(3)//压缩等级1,3,4，默认3
                .setRenameListener(new OnRenameListener() {
                    @Override
                    public String rename(String filePath) {
                        String originName = photo.substring(photo.lastIndexOf("/")+1);
                        String name = "c" + originName;
                        Log.d(TAG, "rename: "+name);
                        return name;
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File newFile) {
                        // 压缩成功后调用，返回压缩后的图片文件
                        // 获取返回的图片地址 newfile
                        String newPath = newFile.getAbsolutePath();
                        String photoc = newPath;
                        // 输出图片的大小
                        Log.d(TAG,"new/"+"图片的大小为："+newFile.length()/1024+"KB");
                        Log.d(TAG,"new/"+"图片的路径为："+photoc);
                        oldFile.delete();//压缩后删除原图
                        compressFinish(photoc);
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                }).launch(); // 启动压缩
    }
}
