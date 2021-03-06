package zgl.com.cn.model_flight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import zgl.com.cn.model_flight.aac.AACActivity;
import zgl.com.cn.model_flight.drawlayout.ZSlideLayoutActivity;

@Route(path = "/flight/MainFlightActivity")
public class MainFlightActivity extends AppCompatActivity {

    private String flightName;
    private int flightNO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_main);

        flightName = getIntent().getStringExtra("flightName");
        flightNO = getIntent().getIntExtra("flightNO",0);

        initView();
    }

    private void initView() {
        Button btn_go_hotel =  findViewById(R.id.btn_go_hotels);
        TextView tv_msg = findViewById(R.id.tv_msg);
        Button btn_drawer_layout = findViewById(R.id.btn_drawer_layout);
        Button btn_aac = findViewById(R.id.btn_aac);

        tv_msg.setText(flightName+"---"+flightNO);

        btn_go_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/hotel/MainHotelActivity")
                        .withString("hotelName","征程er酒店")
                        .withDouble("hotelPrice",109.99)
                        .navigation();
            }
        });
        btn_drawer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainFlightActivity.this, ZSlideLayoutActivity.class));
            }
        });

        btn_aac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainFlightActivity.this, AACActivity.class));
            }
        });
    }




}
