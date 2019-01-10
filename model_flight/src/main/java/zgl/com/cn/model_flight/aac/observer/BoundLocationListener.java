package zgl.com.cn.model_flight.aac.observer;

import android.Manifest;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 *  描述：自定义的可监听声明周期的listener

 * @author : jsj_android
 * @date : 2018/12/7
 */

public class BoundLocationListener implements LifecycleObserver {

    private LocationListener mListener;
    private Context mContext;
    private LocationManager mManger;

    public BoundLocationListener(LocationListener listener, Context context) {
        this.mListener = listener;
        this.mContext = context;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void addLocationListener() {
        mManger = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("权限","未授权");
            return;
        }
        mManger.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mListener);
        Location location = mManger.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (location!=null) {
            mListener.onLocationChanged(location);
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void removeLocationListener(){
        //根据activity的生命状态，做不同的操作
        if (mManger!=null) {
            mManger.removeUpdates(mListener);
            mManger  = null;
        }

    }

}
