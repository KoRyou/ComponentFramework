package zgl.com.cn.libandroid.net.network.okhttp;

import android.os.Handler;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zgl.com.cn.libandroid.net.network.bean.DataType;
import zgl.com.cn.libandroid.net.network.bean.NetParams;
import zgl.com.cn.libandroid.net.network.ICallBack;
import zgl.com.cn.libandroid.net.network.IHttpRequest;
import zgl.com.cn.libandroid.net.network.utils.DataParseUtil;

/**
 * 描述：网络框架--okhttp的网络请求
 *
 * @author : jsj_android
 * @date : 2018/11/7
 */

public class OkHttpRequest implements IHttpRequest {

    private final OkHttpClient mHttpClient;
    private final Handler mHandler;

    public OkHttpRequest() {
        mHttpClient = new OkHttpClient();
        mHandler = new Handler();
    }

    @Override
    public void get(final NetParams params, final ICallBack callBack) {
        String url = appendGetUrl(params.getBaseUrl()+params.getUrl(),params.getParams());
        final Request req = new Request.Builder().get()
                .url(url)
                .build();
        mHttpClient.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure("发送请求失败"+e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                parseData(params,response,callBack);

            }
        });

    }

    private void parseData(final NetParams params, final Response response, final ICallBack callBack) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if ( response.code()==200) {
                    try {
                        String resStr = response.body().string();
                        switch (params.getResDataType()) {
                            case DataType.STRING:
                                callBack.onSuccess(resStr);
                                break;
                            case DataType.JSON_OBJECT:
                                callBack.onSuccess(DataParseUtil.parseObject(resStr, params.getClazz()));
                                break;
                            case DataType.JSON_ARRAY:
                                callBack.onSuccess(DataParseUtil.parseToArrayList(resStr, params.getClazz()));
                                break;
                            default:
                                throw new RuntimeException("<<<<< ERROR : NetFrameWork response dataType error!!! >>>>>");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if(!response.isSuccessful() || response.code()!=200){
                    callBack.onError(response.code(),response.message());
                }
            }
        });

    }

    @Override
    public void post(final NetParams params, final ICallBack callBack) {
        RequestBody body = appendBody(params.getParams());
        Request req = new Request.Builder().get()
                .url(params.getBaseUrl()+params.getUrl())
                .post(body)
                .build();

        mHttpClient.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure("发送请求失败"+e.getMessage());
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                parseData(params,response,callBack);

            }
        });

    }

    @Override
    public void cancel(String tag) {

    }


    /**
     * 把参数拼接到请求体中
     * @param params
     * @return
     */
    private RequestBody appendBody(Map<String, String> params) {
        FormBody.Builder body = new FormBody.Builder();
        if (params==null ||  params.isEmpty()) {
            return body.build();
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            body.add(entry.getKey(),entry.getValue());
        }
        return body.build();
    }


    /**
     * 把参数拼接到请求URL中
     * @param url
     * @param params
     * @return
     */
    private String appendGetUrl(String url, Map<String, String> params) {
        StringBuffer sb = new StringBuffer("?");
        //拼接get请求url
        if (null!=params) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("&");
            }
        }
        sb.deleteCharAt(sb.length()-1);
        url+=sb.toString();
        return url;
    }


}
