package zgl.com.cn.libandroid.net.network.bean;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *  描述：响应数据的数据类型（用于响应数据的转换）
 *
 * @author : jsj_android
 * @date : 2018/11/8
 */

public class DataType {

    public static final int STRING = 1;
    public static final int JSON_OBJECT = 2;
    public static final int JSON_ARRAY = 3;
    public static final int PROTO_BUFFER = 4;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STRING,JSON_OBJECT,JSON_ARRAY,PROTO_BUFFER})
    public @interface Type{}

}
