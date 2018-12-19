package zgl.com.cn.go_model_hotel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import zgl.com.cn.model_hotel.MainHotelActivity;

public class GoModelHotelMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_model_hotel_main);
        TextView tv_go_hotel = (TextView)findViewById(R.id.tv_go_hotel);
        tv_go_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GoModelHotelMainActivity.this,MainHotelActivity.class));
            }
        });
    }
}
