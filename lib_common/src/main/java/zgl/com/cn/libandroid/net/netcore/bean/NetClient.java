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
 * 描述：网络请求参数实体
 *
 * @author : jsj_android
 * @date : 2018/11/29
 */

public class NetClient {

    /**
     * 基础URL
     */
    protected String BASEURL;
    /**
     * 除base url 之外的url
     */
    protected String URL;
    /**
     * 每个请求的唯一标示（用于取消请求）
     */
    protected String TAG;
    /**
     * 请求方式
     */
    protected HttpMethod HTTPMETHOD;
    /**
     * 请求头（特殊请求头需要此参数）
     */
    protected Map<String, String> HEADERS;
    /**
     * 请求参数通过Map的形式
     */
    protected Map<String, Object> PARAMS;
    /**
     * 请求参数通过RequestBody的形式
     */
    protected RequestBody REQBODY;
    /**
     * 请求参数通过ZRequest的形式（仅proto时）
     */
    protected ZRequest ZREQUEST;
    /**
     * 成功-失败-错误，请求开始结束 的监听
     */
    protected IListener LISTENER;
    protected ISuccess SUCCESS;
    protected IFailure FAILURE;
    protected IError ERROR;

    //---------上传  &  下载----------

    /**
     * 上传的文件
     */
    protected File FILE;

    /**
     * 下载的 文件路径（系统根目录之后的部分）
     * 下载文件的扩展名
     * 下载文件的名字
     * 下载的进度监听
     */
    protected String DOWNLOAD_DIR;
    protected String EXTENSION;
    protected String FILENAME;
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
