package zgl.com.cn.libandroid.net.netcore.download;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zgl.com.cn.libandroid.net.netcore.callback.IError;
import zgl.com.cn.libandroid.net.netcore.callback.IFailure;
import zgl.com.cn.libandroid.net.netcore.callback.IListener;
import zgl.com.cn.libandroid.net.netcore.callback.IProgress;
import zgl.com.cn.libandroid.net.netcore.callback.ISuccess;
import zgl.com.cn.libandroid.net.netcore.retrofit.ApiService;

/**
 * Created by ZGL on 2018/6/6.
 */

public class DownloadHandler {
    public static final int STATE = 0x01;

    public final HashMap<String, Object> PARAMS;
    public final HashMap<String, String> HEADERS;
    public final String URL;
    public final IListener REQUEST;
    public final ISuccess SUCCESS;
    public final IFailure FAILURE;
    public final IError ERROR;
    public final String DOWNLOAD_DIR;
    public final String EXTENSION;
    public final String FILENAME;
    public final IProgress PROGRESS;
    private static DownloadHandler mINSTANCE;
    public static HashMap<String,Object> mSubMap= new HashMap<>();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if (msg.what==STATE) {
                ((Call<ResponseBody>) mSubMap.get(URL)).cancel();
                mSubMap.remove(URL);
            }

        }
    };

    private static class Holder {
        private static final DownloadHandler getInstance(HashMap<String, Object> params, HashMap<String, String> headers, String url,
                                                         IListener request, ISuccess success,
                                                         IFailure failure, IError error,
                                                         String downloadDir, String extension, String filename, IProgress progress){
            mINSTANCE = new DownloadHandler(params,headers,url,request,success,failure,error,downloadDir,extension,filename,progress);
            mSubMap.clear();
            return mINSTANCE;
        }
    }

    private DownloadHandler(HashMap<String, Object> params, HashMap<String, String> headers, String url,
                           IListener request, ISuccess success,
                           IFailure failure, IError error,
                           String downloadDir, String extension, String filename, IProgress progress) {
        this.PARAMS = params;
        this.HEADERS = headers;
        this.URL = url;
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.FILENAME = filename;
        this.PROGRESS = progress;
    }

    public static DownloadHandler getInstance(HashMap<String, Object> params, HashMap<String, String> headers, String url,
                            IListener request, ISuccess success,
                            IFailure failure, IError error,
                            String downloadDir, String extension, String filename, IProgress progress){
        return Holder.getInstance(params, headers, url, request, success, failure, error, downloadDir, extension, filename, progress);
    }


    public final void handleDownload(ApiService service) {
        
        //1.判断当前资源如果在下载中，不启动下载
        if (mSubMap!=null && mSubMap.size()>0) {
            Log.e("下载缓存集合",""+mSubMap.size());
            for (String key:mSubMap.keySet()) {
                if(URL.equals(key)){
                    return;
                }
            }
        }
        // 2.下载完毕才进行下载
        if (REQUEST!=null) {
            REQUEST.onRequestStart();
        }

        Call<ResponseBody> call = service.download(URL,HEADERS);

        mSubMap.put(URL,call);
        
        call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            //开始保存文件,开一个异步任务来做
                            SaveFileTask task = new SaveFileTask(new SaveFileTask.TaskListener() {
                                @Override
                                public void postExecute(String filePath) {
                                    if (SUCCESS!=null) {
                                        SUCCESS.onSuccess(filePath);
                                    }
                                    if (REQUEST!=null) {
                                        REQUEST.onRequestEnd();
                                    }
                                    mSubMap.remove(URL);
                                }

                                @Override
                                public void onProgress(int progress,long loaded) {
                                    if (PROGRESS!=null) {
                                       PROGRESS.onProgress(progress,loaded);
                                    }
                                }
                            });

                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DOWNLOAD_DIR, EXTENSION, response.body(), FILENAME);

                        } else {
                            if (ERROR != null) {
                                ERROR.onError(response.code(), response.message());
                            }
                            mSubMap.remove(URL);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure();
                        }
                        if (REQUEST != null) {
                            REQUEST.onRequestEnd();
                        }
                        ((Call<ResponseBody>) mSubMap.get(URL)).cancel();
                        mSubMap.remove(URL);
                    }
                });
    }


}
