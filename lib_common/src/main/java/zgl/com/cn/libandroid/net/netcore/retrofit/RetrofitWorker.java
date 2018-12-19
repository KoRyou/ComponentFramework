package zgl.com.cn.libandroid.net.netcore.retrofit;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import zgl.com.cn.libandroid.net.netcore.bean.HttpMethod;
import zgl.com.cn.libandroid.net.netcore.bean.NetClient;
import zgl.com.cn.libandroid.net.netcore.callback.IProgress;
import zgl.com.cn.libandroid.net.netcore.callback.IRequest;
import zgl.com.cn.libandroid.net.netcore.callback.RetrofitCallback_ResponseBody;
import zgl.com.cn.libandroid.net.netcore.callback.RetrofitCallback_ZResponse;
import zgl.com.cn.libandroid.net.netcore.download.DownloadHandler;
import zgl.com.cn.libandroid.net.network.bean.ZResponse;

/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2018/11/30
 */

public class RetrofitWorker extends NetClient implements IRequest {

    //缓存发出的请求，用于后期删除
    private static final Map<String, Call> REQ_CALL_MAP = new HashMap<>();

    private final Map mJsonHeader, mProtoHeader;

    public RetrofitWorker() {
        mJsonHeader = new HashMap(2);
        mJsonHeader.put("Content-Type", "application/json");
        mJsonHeader.put("Accept", "application/json");
        mJsonHeader.put("Cache-Control", "no-cache");

        mProtoHeader = new HashMap(3);
        mProtoHeader.put("Content-Type", "application/x-protobuf");
        mProtoHeader.put("Accept", "application/x-protobuf");
        mProtoHeader.put("Cache-Control", "no-cache");

    }

    /**
     * 把请求保存到内存,用于取消
     *
     * @param params
     * @param call
     */
    private void putCall(NetClient params, Call call) {
        if (TextUtils.isEmpty(params.getTAG())) {
            return;
        }
        synchronized (REQ_CALL_MAP) {
            REQ_CALL_MAP.put(params.getTAG() + params.getURL(), call);
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

    @Override
    public void request(NetClient client) {

        if (LISTENER != null && !HTTPMETHOD.equals(HttpMethod.DOWNLOAD)) {
            LISTENER.onRequestStart();
        }

        //1.把所有请求数据填充进进来
        configData(client);
        //2.根据参数创建对应的retrofit对象及SERVICE
        ApiService service = RetrofitCreator.getService(BASEURL);
        Call<ResponseBody> callRB = null;
        Call<ZResponse> callZ = null;
        //3.判断Header参数的设置情况
        if (HEADERS == null) {
            if (HTTPMETHOD.equals(HttpMethod.POST_PROTO)) {
                HEADERS = mProtoHeader;
            } else {
                HEADERS = mJsonHeader;
            }
        }

        //4.根据不同的请求类型执行不同的service方法
        switch (HTTPMETHOD) {
            case GET:
                callRB = service.get(URL, HEADERS, PARAMS);
                break;
            case PUT:
                callRB = service.put(URL, HEADERS, PARAMS);
                break;
            case DELETE:
                callRB = service.delete(URL, PARAMS);
                break;
            case POST:
                callRB = service.post(URL, HEADERS, PARAMS);
                break;
            case POST_RAW:
                callRB = service.postRaw(URL, HEADERS, REQBODY);
                break;
            case POST_PROTO:
                callZ = service.postProto(URL, HEADERS, ZREQUEST);
                break;
            case UPLOAD:
                //上传
                final RequestBody requestBody= RequestBody.create(
                        MultipartBody.FORM,FILE);
                final MultipartBody.Part body=MultipartBody.Part.createFormData(
                        "file",FILE.getName(),requestBody);
                callRB = service.upload(URL,body);
                break;
            case DOWNLOAD:

                //调用自己的方法下载
                DownloadHandler.getInstance(
                        (HashMap<String, Object>) PARAMS,(HashMap<String, String>)HEADERS,URL,
                        LISTENER, SUCCESS,FAILURE,ERROR,
                        DOWNLOAD_DIR, EXTENSION,FILENAME,PROGRESS
                ).handleDownload( RetrofitCreator.getService(BASEURL, PROGRESS) );

                break;
            default:
                try {
                    throw new Exception("【ERROR】 HttpMethod type is unreal !!!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }


        //真正的发送请求
        if (callRB != null) {
            callRB.enqueue(getReqeustCallback("RB", client));
            //把所有的call存入Map中
            putCall(client, callRB);
        }

        if (callZ != null) {
            callZ.enqueue(getReqeustCallback("Z", client));
            //把所有的call存入Map中
            putCall(client, callZ);
        }


    }

    private Callback getReqeustCallback(String state, final NetClient client) {

        Callback callback = null;

        switch (state) {
            case "RB":
                callback = new RetrofitCallback_ResponseBody(LISTENER, SUCCESS, FAILURE, ERROR, new RetrofitCallback_ResponseBody.IResBack() {
                    @Override
                    public void onResBack() {
                        //用于删除Call
                        if (client.getTAG() != null) {
                            removeCall(client.getURL());
                        }
                    }
                });
                break;
            case "Z":
                callback = new RetrofitCallback_ZResponse(LISTENER, SUCCESS, FAILURE, ERROR, new RetrofitCallback_ZResponse.IResBack() {
                    @Override
                    public void onResBack() {
                        //用于删除Call
                        if (client.getTAG() != null) {
                            removeCall(client.getURL());
                        }
                    }
                });
                break;
            default:
                break;
        }

        return callback;

    }

    private void configData(NetClient client) {
        BASEURL = client.getBASEURL();
        URL = client.getURL();
        TAG = client.getTAG();
        HTTPMETHOD = client.getHTTPMETHOD();
        PARAMS = client.getPARAMS();
        REQBODY = client.getREQBODY();
        ZREQUEST = client.getZREQUEST();
        LISTENER = client.getLISTENER();
        SUCCESS = client.getSUCCESS();
        FAILURE = client.getFAILURE();
        ERROR = client.getERROR();
        HEADERS = client.getHEADERS();
        DOWNLOAD_DIR = client.getDOWNLOAD_DIR();
        EXTENSION = client.getEXTENSION();
        FILENAME =client.getFILENAME();
        PROGRESS = client.getPROGRESS();
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
}
