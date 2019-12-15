package com.example.Fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.example.ecar.ExecuteActivity;
import com.example.ecar.R;
import com.baidu.mapapi.map.MapStatus;
import com.example.listener.MyOrientationListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MapFragment extends BaseFragment implements View.OnClickListener, MyOrientationListener.OnOrientationListener{

    private static final String TAG="MapFragment";
    //百度地图
    private boolean isFirstLocate = true;//首次定位
    private TextureMapView mMapView;//解决闪屏问题https://www.jianshu.com/p/b8a3b967b8ce
    private LocationClient mLocationClient;
    private LocationClientOption option = null;
    private BaiduMapOptions baiduMapOptions;
    private MyOrientationListener myOrientationListener;
    private BaiduMap baiduMap;
    private float mLastX = -1;//方向信息
    private LatLng ll;//定位位置
    //布局
    private RelativeLayout parent_layout;
    private View layout_call;
    private View layout_more;
    private View left_menu;
    private View right_menu;
    private Button btn_navigate;
    private ImageButton btn_locate;
    private FragmentConfig config = new FragmentConfig(false,"前往救援地",false,new TollsFragment());//初始化top

    @Override
    public FragmentConfig getConfig() {
        return config;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        Log.d(TAG, "onCreateView: ");
        initView(view);
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getActivity(), "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                            return;
                        }
                    }
                    //requestLocation();
                } else {
                    Toast.makeText(getActivity(), "发生未知错误", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                break;
            default:
        }
    }

    private void initView(View view){
        layout_call = view.findViewById(R.id.layout_call);
        layout_more = view.findViewById(R.id.layout_more);
        btn_navigate = view.findViewById(R.id.btn_navigate);
        parent_layout = view.findViewById(R.id.parent_layout);
        left_menu = view.findViewById(R.id.left_menu);
        right_menu = view.findViewById(R.id.right_menu);
        btn_locate = view.findViewById(R.id.btn_locate);
        layout_call.setOnClickListener(this);
        layout_more.setOnClickListener(this);
        btn_navigate.setOnClickListener(this);
        btn_locate.setOnClickListener(this);

        configMap();
        initLocationSdk();
        mMapView = new TextureMapView(view.getContext(),baiduMapOptions);
        //地图初始化
        baiduMap = mMapView.getMap();
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        //通过动态添加百度地图隐藏控件
        RelativeLayout.LayoutParams params_map = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        parent_layout.addView(mMapView,params_map);
    }

    //每次方向改变时刷新图标
    @Override
    public void onOrientationChanged(float x) {
        mLastX = x;
        if (null != baiduMap && null != baiduMap.getLocationData()) {
            MyLocationData locData = new MyLocationData.Builder()
                    .direction(mLastX)
                    .latitude(baiduMap.getLocationData().latitude)
                    .longitude(baiduMap.getLocationData().longitude)
                    .accuracy(baiduMap.getLocationData().accuracy)
                    .build();
            baiduMap.setMyLocationData(locData);
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mLastX).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);

            if (isFirstLocate) {
                ll = new LatLng(location.getLatitude(), location.getLongitude());
                requestLocation(ll);
                if (baiduMap.getLocationData()!=null) {
                    if (baiduMap.getLocationData().latitude == location.getLatitude()
                            && baiduMap.getLocationData().longitude == location.getLongitude()) {
                        isFirstLocate = false;
                    }
                }
            }
            //设置自定义图标
            MyLocationConfiguration config = new MyLocationConfiguration(
                    MyLocationConfiguration.LocationMode.NORMAL, true, null);
            baiduMap.setMyLocationConfigeration(config);
        }
    }

    @Override
    public void onClick(View v) {
        /*时间会直接进入判断，而且只有初始化的时候会进行下面这一句的执行*/
        //System.out.println("onClick");
        switch(v.getId()){
            case R.id.btn_navigate:
                if(getResources().getString(R.string.btn_navigate_start).equals(btn_navigate.getText().toString())){
                    btn_navigate.setText(R.string.btn_navigate_stop);
                }else{
                    nextFragment();
                }
                break;

            case R.id.layout_call:
                if(left_menu.getVisibility() == View.VISIBLE){
                    left_menu.setVisibility(View.INVISIBLE);
                }else{
                    left_menu.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.layout_more:
                if(right_menu.getVisibility() == View.VISIBLE){
                    right_menu.setVisibility(View.INVISIBLE);
                }else{
                    right_menu.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.btn_locate:
                //把定位点再次显现出来
                requestLocation(ll);
                break;

            default:
        }
    }

    public StringBuilder description(BDLocation location){
        StringBuilder currentPosition = new StringBuilder();
        currentPosition.append("纬度：").append(location.getLatitude()).append('\n');
        currentPosition.append("经度：").append(location.getLongitude()).append('\n');
        currentPosition.append("国家：").append(location.getCountry()).append('\n');
        currentPosition.append("省份：").append(location.getProvince()).append('\n');
        currentPosition.append("市：").append(location.getCity()).append('\n');
        currentPosition.append("区：").append((location.getDistrict())).append('\n');
        currentPosition.append("街道：").append(location.getStreet()).append('\n');
        //currentPosition.append("定位方式：");
        if(location.getLocType() == BDLocation.TypeGpsLocation||location.getLocType() == BDLocation.TypeNetWorkLocation){
            //navigateTo(location);
        }
        return currentPosition;
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        if (null != mLocationClient && !mLocationClient.isStarted()) {
            mLocationClient.start();
        }
        myOrientationListener.start();
        requestLocation(ll);
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        if (null != mLocationClient && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
        myOrientationListener.stop();
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onDestroy() {
        if(mMapView != null){
            mMapView.onDestroy();
        }
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    private void configMap(){
        baiduMapOptions = new BaiduMapOptions();
        baiduMapOptions.zoomControlsEnabled(false);
        baiduMapOptions.scaleControlEnabled(false);
        //初始化传感器
        myOrientationListener = new MyOrientationListener(getActivity());
        myOrientationListener.setOnOrientationListener(this);
    }

    //定位
    private void requestLocation(LatLng ll){
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus(new MapStatus.Builder().target(ll)
                        .overlook(-5).rotate(0).zoom(18f).build());
        //地图中心target、俯仰角overlook、平面旋转角度rotate、放大级别zoom
        baiduMap.animateMapStatus(mapStatusUpdate);
    }

    private void initLocationSdk(){
        option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式：高精度，低功耗，仅设备
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型,gcj02会导致位置偏移
        //option.setScanSpan(1000);//设置每秒更新一次位置信息
        option.setIsNeedLocationDescribe(true);//设置需要位置描述信息
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setIgnoreKillProcess(true);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
        option.setOpenAutoNotifyMode(); //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        mLocationClient = new LocationClient(getActivity());
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new MyLocationListener());
        mLocationClient.start();

        try {
            LocationManager locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(getActivity(),R.string.gps_enabled_false,Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
