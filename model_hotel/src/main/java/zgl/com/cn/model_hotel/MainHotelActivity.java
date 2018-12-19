package zgl.com.cn.model_hotel;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import zgl.com.cn.libandroid.net.netcore.NetHelper;
import zgl.com.cn.libandroid.net.netcore.bean.HttpMethod;
import zgl.com.cn.libandroid.net.netcore.callback.IError;
import zgl.com.cn.libandroid.net.netcore.callback.IFailure;
import zgl.com.cn.libandroid.net.netcore.callback.IListener;
import zgl.com.cn.libandroid.net.netcore.callback.IProgress;
import zgl.com.cn.libandroid.net.netcore.callback.ISuccess;

@Route(path = "/hotel/MainHotelActivity")
public class MainHotelActivity extends AppCompatActivity {

    private String hotelName;
    private double hotelPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hotel);

        hotelName = getIntent().getStringExtra("hotelName");
        hotelPrice = getIntent().getDoubleExtra("hotelPrice", 0);

        initView();

    }

    private void initView() {
        Button btn_net = findViewById(R.id.btn_net);
        Button btn_go_flight = findViewById(R.id.btn_go_flight);
        Button btn_down = findViewById(R.id.btn_down);
        Button btn_up = findViewById(R.id.btn_up);
        final TextView tv_init = findViewById(R.id.tv_init);
        final TextView tv_net = findViewById(R.id.tv_net);

        tv_init.setText(hotelName + "---" + hotelPrice);


        btn_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //网络请求

                Map<String, Object> params = new HashMap<>();
                params.put("city", "北京");
                params.put("key", "13cb58f5884f9749287abbead9c658f2");

                NetHelper.getInstance().request(NetHelper.createBuilder("http://restapi.amap.com", "/v3/weather/weatherInfo", "MainHotelActivityWeather")
                        .httpMethod(HttpMethod.POST)
                        .params(params)
                        .listener(new IListener() {
                            @Override
                            public void onRequestStart() {
                                Log.e("升级版请求2", "开始**************************************");
                            }

                            @Override
                            public void onRequestEnd() {
                                Log.e("升级版请求2", "**************************************结束");
                            }
                        })
                        .success(new ISuccess() {
                            @Override
                            public void onSuccess(Object result) {
                                ResponseBody rb = (ResponseBody) result;
                                try {
                                    Log.e("升级版请求2", rb.string() + "");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        })
                        .error(new IError() {
                            @Override
                            public void onError(int code, String errorMsg) {
                                Log.e("升级版请求2", "ERROR " + code + errorMsg);
                            }
                        })
                        .failure(new IFailure() {
                            @Override
                            public void onFailure() {
                                Log.e("升级版请求2", "Failure  请求失败！！");
                            }
                        })
                        .build()
                );


            }
        });


        btn_go_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/flight/MainFlightActivity")
                        .withString("flightName", "征程NB航班")
                        .withInt("flightNO", 8899)
                        .navigation();
            }
        });


        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //下载
                File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/AABB");
                if (!f.exists()) {
                    f.mkdir();
                }


                HashMap<String, String> headers = new HashMap<>();
                headers.put("range", "bytes=" + 0 + "-");

                //http://img.jsjinfo.cn/f0bb8cc2181cb28fa10cbcfcf01d4537

                NetHelper.getInstance().request(
                        NetHelper.createBuilder("http://img.jsjinfo.cn/", "f0bb8cc2181cb28fa10cbcfcf01d4537", "download_GCTravelTools1111")
                                .httpMethod(HttpMethod.DOWNLOAD)
                                .headers(headers)
                                .params(null)
                                .extension("jpg")
                                .filename("f0bb8cc2181cb28fa10cbcfcf01d4537")
                                .downloaddir("AABB")
                                .listener(new IListener() {
                                    @Override
                                    public void onRequestStart() {
                                        Log.e("下载测试AAA", "-----------------------开始 ------------------------");
                                    }

                                    @Override
                                    public void onRequestEnd() {
                                        Log.e("下载测试AAA", "-----------------------！！！结束！！！------------------------");
                                    }
                                }).progress(new IProgress() {
                            @Override
                            public void onProgress(int progress, long loaded) {
                                Log.e("下载测试AAA", "下载进度==" + progress + "    loaded=" + loaded);
                            }
                        })
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(Object result) {
                                        Log.e("下载测试AAA", "路径" + (String) result);
                                    }
                                })
                                .error(new IError() {
                                    @Override
                                    public void onError(int code, String errorMsg) {
                                        Log.e("下载测试AAA", "ERROR" + errorMsg);
                                    }
                                })
                                .failure(new IFailure() {
                                    @Override
                                    public void onFailure() {
                                        Log.e("下载测试AAA", "下载失败");
                                    }
                                })
                                .build());


            }
        });

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //上传

            }
        });


    }
}
