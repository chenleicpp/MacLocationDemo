package com.amap.location.demo;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;

/**
 * Created by cl on 2018/1/31.
 */

public class MyActivity extends Activity implements AMap.OnMyLocationChangeListener{

    private MapView mapView;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private Double mLocationLatitude,mLocationLongitude;
    private boolean isFirst = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (aMap == null){
            aMap = mapView.getMap();
        }
        initMap();

    }

    private void initMap(){
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        myLocationStyle.interval(1000);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        aMap.setOnMyLocationChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (location != null) {
            Bundle bundle = location.getExtras();
            if (bundle != null) {
                mLocationLatitude = location.getLatitude();
                mLocationLongitude = location.getLongitude();
                if (isFirst) {
                    if (mLocationLatitude > 0 && mLocationLongitude > 0) {
                        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(mLocationLatitude, mLocationLongitude), 17);
                        aMap.moveCamera(cu);
                    } else {
                        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(Constant.DEFAULT_LATITUDE, Constant.DEFAULT_LONGITUDE), 17);
                        aMap.moveCamera(cu);
                    }
                    isFirst = false;
                }
            } else {
                Toast.makeText(this,"failed!!!",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
