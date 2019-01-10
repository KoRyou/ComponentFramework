package zgl.com.cn.model_flight.aac.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import zgl.com.cn.model_flight.aac.repository.DataRepository;


/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2018/12/11
 */

public class VMFactory implements ViewModelProvider.Factory {

    private DataRepository repository = DataRepository.getInstance();

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) new ProductViewModel(null,repository);
    }
}
