package zgl.com.cn.model_flight.aac.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import zgl.com.cn.model_flight.aac.entity.Product;
import zgl.com.cn.model_flight.aac.repository.DataRepository;


/**
 * 描述：ViewModel层，相当于P层，持有repository的引用，可获取数据
 * @author : jsj_android
 * @date : 2018/12/6
 */

public class ProductViewModel extends AndroidViewModel {

    private DataRepository mRepository;
    private LiveData<Product> mLiveProduct;
    private LiveData<String> mStrProducr;

    /**
     * ViewModel中如果需要Application，就 extends AndroidViewModel
     * @param application
     */
    public ProductViewModel(@NonNull Application application) {
        super(application);
        mRepository = DataRepository.getInstance();
    }

    /**
     * 自定义的构造函数，需要借助ViewModelFactory
     *  view层调用时需要 =》  ViewModelProviders.of(this,XXXFactory )
     * @param application
     * @param repository
     */
    public ProductViewModel(@NonNull Application application, DataRepository repository){
        super(application);
        mRepository = repository;
    }


    public LiveData<String> getProduct(int id) {
        mLiveProduct = mRepository.loadProduct(id);

        /**可使用Transformations.map/switchmap来转换得到的数据**/
        mStrProducr = Transformations.map(mLiveProduct, new Function<Product, String>() {
            @Override
            public String apply(Product data) {
                String str = "ID:"+data.getId()+"   NAME:"+data.getName()+"   描述："+data.getDescription();
                return str;
            }
        });
        return mStrProducr;
    }

    public void setProduct(Product product) {
        mRepository.setProduct(product);
    }
}
