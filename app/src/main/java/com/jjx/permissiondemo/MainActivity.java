package com.jjx.permissiondemo;

import android.app.AppOpsManager;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static LocationManager locationManager;
    AppOpsManager appOpsManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        initData();
    }

    private void initData() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isWifiEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int checkResult = appOpsManager.checkOpNoThrow(
                    AppOpsManager.OPSTR_FINE_LOCATION, Binder.getCallingUid(), context.getPackageName());
            if(checkResult == AppOpsManager.MODE_ALLOWED){
                Toast.makeText(context,"有权限",Toast.LENGTH_LONG).show();
                Log.e("jijiaxin","有权限");
            }else if(checkResult == AppOpsManager.MODE_IGNORED){
                // TODO: 只需要依此方法判断退出就可以了，这时是没有权限的。
                Toast.makeText(context,"被禁止了",Toast.LENGTH_LONG).show();
                Log.e("jijiaxin","被禁止了");
            }else if(checkResult == AppOpsManager.MODE_ERRORED){
                Toast.makeText(context,"出错了",Toast.LENGTH_LONG).show();
                Log.e("jijiaxin","出错了");
            }else if(checkResult == 4){
                Toast.makeText(context,"权限需要询问",Toast.LENGTH_LONG).show();
                Log.e("jijiaxin","权限需要询问");
            }
        }

        if (!isGPSEnabled && !isWifiEnabled) {
            Toast.makeText(context,"GPS wifi 都关闭了",Toast.LENGTH_LONG).show();
            Log.e("jijiaxin","GPS wifi 都关闭了");
        }else if(!isGPSEnabled && isWifiEnabled){
            Toast.makeText(context,"GPS 关闭 wifi 可用",Toast.LENGTH_LONG).show();
            Log.e("jijiaxin","GPS 关闭 wifi 可用");
        }else if(isGPSEnabled && !isWifiEnabled){
            Toast.makeText(context,"GPS 可用 wifi 关闭",Toast.LENGTH_LONG).show();
            Log.e("jijiaxin","GPS 可用 wifi 关闭");
        }else if(isGPSEnabled && isWifiEnabled){
            Toast.makeText(context,"GPS wifi 都可用",Toast.LENGTH_LONG).show();
            Log.e("jijiaxin","GPS wifi 都可用");
        }
        if(isGPSEnabled || isWifiEnabled){
            if (isWifiEnabled){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.e("jijiaxin",location.getLongitude()+"wifi");
                        Log.e("jijiaxin",location.getAltitude()+"wifi");
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
            }else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.e("jijiaxin",location.getLongitude()+"gps");
                        Log.e("jijiaxin",location.getAltitude()+"gps");
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
            }

        }
    }

    private void initView() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
