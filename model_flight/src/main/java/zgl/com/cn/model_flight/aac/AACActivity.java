package zgl.com.cn.model_flight.aac;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zgl.com.cn.model_flight.R;
import zgl.com.cn.model_flight.aac.entity.Product;
import zgl.com.cn.model_flight.aac.observer.BoundLocationListener;
import zgl.com.cn.model_flight.aac.viewmodel.ProductViewModel;
import zgl.com.cn.model_flight.aac.viewmodel.VMFactory;


public class AACActivity extends AppCompatActivity {

    private TextView tv_text;
    private Button btn_set;

    private ProductViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aac);




        LinearLayout llMain = findViewById(R.id.ll_main);
        tv_text = (TextView) findViewById(R.id.tv_text);
        btn_set = (Button) findViewById(R.id.btn_set);



        /**
         * 方式一：通过Fragment使用AAC
         */
        /*if (savedInstanceState==null) {
            ProductFragment fragment = new ProductFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ll_main,fragment)
                    .commit();
        }*/


        /**
         * 方式二：在Activity直接使用
         */

        //初始化ViewModel
        mModel = ViewModelProviders.of(this,new VMFactory()).get(ProductViewModel.class);
        //初始化获取产品 id = 5
        getProduct(5);


        //点击添加按钮
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加产品后，获取产品id=7
                mModel.setProduct(new Product(6, "F", "fff", 600));
                mModel.setProduct(new Product(7, "G", "ggg", 700));
                getProduct(7);
            }
        });


        //【其他功能】创建一个能感知生命周期的监听，例如 onDestroy()时自动销毁。。。
        //获得activity的注册对象，然后添加监听
        getLifecycle().addObserver(new BoundLocationListener(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                btn_set.setText(location.getLatitude() + "--" + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        }, this));


    }

    private void getProduct(int id) {
        //调用具体的viewModel的getProduct(),通过观察者监听到变化自动设值
        mModel.getProduct(id).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String product) {

                showProduct(product);

            }
        });
    }


    public void showProduct(String str) {
        tv_text.setText(str);
    }
}
