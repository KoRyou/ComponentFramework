package zgl.com.cn.libandroid.net.netcore.bean;

import java.io.File;
import java.util.Map;

import okhttp3.RequestBody;
import zgl.com.cn.libandroid.net.netcore.callback.IError;
import zgl.com.cn.libandroid.net.netcore.callback.IFailure;
import zgl.com.cn.libandroid.net.netcore.callback.IListener;
import zgl.com.cn.libandroid.net.netcore.callback.IProgress;
import zgl.com.cn.libandroid.net.netcore.callback.ISuccess;
import zgl.com.cn.libandroid.net.network.bean.ZRequest;

/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2018/11/29
 */

public class NetClient {

    protected  String BASEURL;
    protected  String URL;
    protected  String TAG;
    protected  HttpMethod HTTPMETHOD;
    protected  Map<String, String> HEADERS;
    protected  Map<String, Object> PARAMS;
    protected  RequestBody REQBODY;
    protected  ZRequest ZREQUEST;

    protected  IListener LISTENER;
    protected  ISuccess SUCCESS;
    protected  IFailure FAILURE;
    protected  IError ERROR;

    //上传  &  下载

    protected  File FILE;

    protected  String DOWNLOAD_DIR;
    protected  String EXTENSION;
    protected  String FILENAME;
    protected IProgress PROGRESS;



    protected NetClient(){}
    public NetClient(String baseurl, String url, String tag, HttpMethod httpmethod, Map<String, String> headers, Map<String, Object> params, RequestBody reqBody, ZRequest zrequest, IListener listener, ISuccess success, IFailure failure, IError error, File file, String download_dir, String extension, String filename,IProgress progress){
        BASEURL = baseurl;
        URL = url;
        TAG = tag;
        HTTPMETHOD = httpmethod;
        HEADERS = headers;
        PARAMS = params;
        REQBODY = reqBody;
        ZREQUEST = zrequest;
        LISTENER = listener;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
        FILE = file;
        DOWNLOAD_DIR = download_dir;
        EXTENSION = extension;
        FILENAME = filename;
        PROGRESS = progress;
    }

    public String getBASEURL() {
        return BASEURL;
    }

    public String getURL() {
        return URL;
    }

    public String getTAG() {
        return TAG;
    }

    public HttpMethod getHTTPMETHOD() {
        return HTTPMETHOD;
    }

    public Map<String, String> getHEADERS() {
        return HEADERS;
    }

    public Map<String, Object> getPARAMS() {
        return PARAMS;
    }

    public RequestBody getREQBODY() {
        return REQBODY;
    }

    public ZRequest getZREQUEST() {
        return ZREQUEST;
    }

    public IListener getLISTENER() {
        return LISTENER;
    }

    public ISuccess getSUCCESS() {
        return SUCCESS;
    }

    public IFailure getFAILURE() {
        return FAILURE;
    }

    public IError getERROR() {
        return ERROR;
    }

    public File getFILE() {
        return FILE;
    }

    public String getDOWNLOAD_DIR() {
        return DOWNLOAD_DIR;
    }

    public String getEXTENSION() {
        return EXTENSION;
    }

    public String getFILENAME() {
        return FILENAME;
    }

    public IProgress getPROGRESS() {
        return PROGRESS;
    }
}
