package zgl.com.cn.componentframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

public class MainActivity extends AppCompatActivity {

    private Button mStart;
    private Button mEnd;
    private ProgressBar mBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();

    }

    private void initView() {

        TextView tv_hotel = findViewById(R.id.tv_hotel);
        TextView tv_flight = findViewById(R.id.tv_flight);


        tv_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/hotel/MainHotelActivity")
                        .withString("hotelName", "征程大酒店")
                        .withDouble("hotelPrice", 108.88)
                        .navigation();
            }
        });

        tv_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build("/flight/MainFlightActivity")
                        .withString("flightName", "征程NB航班B")
                        .withInt("flightNO", 1001)
                        .navigation();
            }
        });


    }
}



