package zgl.com.cn.libandroid.net.network.retrofit;

import android.text.TextUtils;

import com.google.protobuf.Message;
import com.google.protobuf.nano.MessageNano;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zgl.com.cn.libandroid.net.network.bean.DataType;
import zgl.com.cn.libandroid.net.network.bean.NetParams;
import zgl.com.cn.libandroid.net.network.ICallBack;
import zgl.com.cn.libandroid.net.network.IHttpRequest;
import zgl.com.cn.libandroid.net.network.bean.ZRequest;
import zgl.com.cn.libandroid.net.network.bean.ZResponse;
import zgl.com.cn.libandroid.net.network.utils.DataParseUtil;
import zgl.com.cn.libandroid.net.network.utils.ProtoConverterFactory;

/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2018/11/7
 */

public class RetrofitHttpRequest implements IHttpRequest {

    private Retrofit mRetrofit;
    private String mBaseUrl = "";
    private Call<ResponseBody> mCall;
    private Call<ZResponse> mCallZ;
    //缓存发出的请求，用于后期删除
    private static final Map<String, Call> REQ_CALL_MAP = new HashMap<>();
    private final Map mJsonMap, mProtoMap;
    public  InputStream mIs;
    public  Message.Builder builder;

    public RetrofitHttpRequest() {

        mJsonMap = new HashMap(2);
        mJsonMap.put("Content-Type", "application/json");
        mJsonMap.put("Accept", "application/json");
        mJsonMap.put("Cache-Control", "no-cache");

        mProtoMap = new HashMap(3);
        mProtoMap.put("Content-Type", "application/x-protobuf");
        mProtoMap.put("Accept", "application/x-protobuf");
        mProtoMap.put("Cache-Control", "no-cache");

    }

    private void initRetrofit(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new RuntimeException("<<<<<ERROR: BaseUrl is empty!!!>>>>>");
        }
        if ( !mBaseUrl.equals(baseUrl) || mRetrofit == null ) {
            mBaseUrl = baseUrl;

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    // 添加Rx适配器
                    // .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    //设置数据解析器  //一定要在gsonconvert的前面  【！！！必须引用本地的ProtoConverterFactory，否则构建失败！！！】
                    .addConverterFactory(ProtoConverterFactory.create())
                    //设置Json数据的转换器为Gson
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

    @Override
    public void get(final NetParams params, final ICallBack callBack) {

        // 1.把参数分解
        String url = appendGetUrl(params.getUrl(), params.getParams());
        NetParams netParams = new NetParams.Builder(params).url(url).build();
        // 2.发送请求
        initRetrofit(netParams.getBaseUrl());

        if (netParams.getResDataType()== DataType.PROTO_BUFFER) {
            //Get方式暂不支持PROTO_BUFFER方式
            throw new RuntimeException("<<<<< ERROR : GET nonsupport PROTO_BUFFER dataType !!! >>>>>");
        }else{
            mCall = create(ApiService.class).executeGet(mJsonMap, url);
        }

        putCall(netParams, mCall);//保存请求到本地，用于取消
        toRequest(netParams, callBack);

    }



    @Override
    public void post(NetParams params, ICallBack callBack) {
        initRetrofit(params.getBaseUrl());

        if (params.getResDataType()== DataType.PROTO_BUFFER) {
            ZRequest request = new ZRequest();
            //设置请求方法
            request.methodName = params.getMethodName();
            //将数据压缩到ZReq中
            MessageNano nano = params.getReqData();
            request.zPack = MessageNano.toByteArray(nano);

            mProtoMap.put("MethodName",params.getMethodName());

            mCallZ = create(ApiService.class).executeProtoPost(params.getUrl(), mProtoMap, request );

            putCall(params, mCallZ);//保存请求到本地，用于取消
            toRequestZ(params, callBack);
        }else{

            mJsonMap.put("MethodName",params.getMethodName());
            mCall = create(ApiService.class).executePost(params.getUrl(), mJsonMap, params.getParams());

            putCall(params, mCall);//保存请求到本地，用于取消
            toRequest(params, callBack);
        }



    }

    @Override
    public void cancel(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        List<String> list = new ArrayList<>();
        synchronized (REQ_CALL_MAP) {
            for (String key : REQ_CALL_MAP.keySet()) {
                if (key.startsWith(tag)) {
                    REQ_CALL_MAP.get(key).cancel();
                    list.add(key);
                }
            }
        }
        for (String s : list) {
            removeCall(s);
        }
    }


    /**
     * 公共的请求方法
     *
     * @param netParams
     * @param callBack
     */
    private void toRequest(final NetParams netParams, final ICallBack callBack) {
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (200 == response.code()) {
                    //得到请求回来的数据，并转换成对应的实体
                    parseData(response, netParams.getClazz(), netParams.getResDataType(), callBack);
                }

                if (!response.isSuccessful() || 200 != response.code()) {
                    callBack.onError(response.code(), response.message());
                }
                if (netParams.getTag() != null) {
                    removeCall(netParams.getUrl());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.onFailure(t.getMessage());
                if (netParams.getTag() != null) {
                    removeCall(netParams.getUrl());
                }
            }
        });
    }
    private void toRequestZ(final NetParams netParams, final ICallBack callBack) {
        mCallZ.enqueue(new Callback<ZResponse>() {
            @Override
            public void onResponse(Call<ZResponse> call, Response<ZResponse> response) {
                if (200 == response.code()) {
                    //得到请求回来的数据，并转换成对应的实体
                    if (response!=null) {
                        callBack.onSuccess(response.body());
                    }
                }

                if (!response.isSuccessful() || 200 != response.code()) {
                    callBack.onError(response.code(), response.message());
                }
                if (netParams.getTag() != null) {
                    removeCall(netParams.getUrl());
                }
            }

            @Override
            public void onFailure(Call<ZResponse> call, Throwable t) {
                callBack.onFailure(t.getMessage());
                if (netParams.getTag() != null) {
                    removeCall(netParams.getUrl());
                }
            }
        });
    }

/**************************************************************************/
/*************----------------------工具方法----------------------*********/
/**************************************************************************/


    /**
     * 响应的数据进行解析并返回给回调
     *  @param response
     * @param clazz
     * @param resDataType
     * @param callBack
     */
    public static void parseData(Response<ResponseBody> response, Class clazz, int resDataType, ICallBack callBack) {

        String result = null;
        try {
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (resDataType) {
            case DataType.STRING:
                callBack.onSuccess(result);
                break;
            case DataType.JSON_OBJECT:
                callBack.onSuccess(DataParseUtil.parseObject(result, clazz));
                break;
            case DataType.JSON_ARRAY:
                callBack.onSuccess(DataParseUtil.parseToArrayList(result, clazz));
                break;
            default:
                throw new RuntimeException("<<<<< ERROR : NetFrameWork response dataType error!!! >>>>>");
        }
    }


    /**
     * 具体删除请求的方法
     *
     * @param url
     */
    private void removeCall(String url) {

        synchronized (REQ_CALL_MAP) {
            for (String key : REQ_CALL_MAP.keySet()) {
                if (key.contains(url)) {
                    url = key;
                    break;
                }
            }
            REQ_CALL_MAP.remove(url);
        }

    }

    /**
     * 把请求保存到内存,用于取消
     *
     * @param params
     * @param call
     */
    private void putCall(NetParams params, Call call) {
        if (TextUtils.isEmpty(params.getTag())) {
            return;
        }
        synchronized (REQ_CALL_MAP) {
            REQ_CALL_MAP.put(params.getTag() + params.getUrl(), call);
        }
    }


    /**
     * 把参数拼接到请求URL中
     *
     * @param url
     * @param params
     * @return
     */
    private String appendGetUrl(String url, Map<String, String> params) {
        StringBuffer sb = new StringBuffer("?");
        //拼接get请求url
        if (null != params) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        url += sb.toString();
        return url;
    }

}
