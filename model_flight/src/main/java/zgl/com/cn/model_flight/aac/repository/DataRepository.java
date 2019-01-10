package zgl.com.cn.model_flight.aac.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import zgl.com.cn.model_flight.aac.entity.Product;


/**
 *  描述：数据仓库，用于获取所有数据（网络）（本地）（数据库）
 *
 * @author : jsj_android
 * @date : 2018/12/6
 */

public class DataRepository {

    /**
     * 可变的LiveDate类型
     */
    private MutableLiveData<List<Product>> mProductList;

    private List<Product> products = new ArrayList<>();

    private DataRepository(){

        mProductList = new MutableLiveData<List<Product>>();

    }


    private static class Holder{
        //单例方式

        public static DataRepository Instance =  new DataRepository();
    }

    public static DataRepository getInstance(){
        return Holder.Instance;
    }



    public LiveData<List<Product>> getProductList(){

        //模拟请求回来的数据
        products.add(new Product(1,"A","aaa",100));
        products.add(new Product(2,"B","bbb",200));
        products.add(new Product(3,"C","ccc",300));
        products.add(new Product(4,"D","ddd",400));
        products.add(new Product(5,"E","eee",500));

        mProductList.setValue(products);
        return mProductList;
    }

    public LiveData<Product> loadProduct(int id){

        getProductList();

        MutableLiveData<Product> pro = new MutableLiveData<Product>();
        //此处可添加网络请求，请求回来后setValue()即可，如果是子线程需要调用postValue()
        pro.setValue(products.get(id-1));
        return pro;
    }

    public void setProduct(Product product) {
        products.add(product);
        mProductList.setValue(products);
    }
}
