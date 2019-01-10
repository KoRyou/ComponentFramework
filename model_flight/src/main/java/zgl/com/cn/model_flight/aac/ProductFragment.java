package zgl.com.cn.model_flight.aac;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import zgl.com.cn.model_flight.R;
import zgl.com.cn.model_flight.aac.entity.Product;
import zgl.com.cn.model_flight.aac.viewmodel.ProductViewModel;


/**
 * todo 描述：同Activity处理一样的
 *
 * @author : jsj_android
 * @date : 2018/12/6
 */

public class ProductFragment extends Fragment {

    private ProductViewModel mModel;
    private TextView tv_text;
    private Button btn_set;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mModel = ViewModelProviders.of(this).get(ProductViewModel.class);


        getProduct(2);

    }

    private void getProduct(int id) {
      // mModel.getProduct(this,id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(),R.layout.fragment_demo,null);
        tv_text = (TextView) view.findViewById(R.id.tv_text);
        btn_set = (Button) view.findViewById(R.id.btn_set);


        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mModel.setProduct(new Product(6,"F","fff",600));
                getProduct(6);
            }
        });

        return view;
    }


}
