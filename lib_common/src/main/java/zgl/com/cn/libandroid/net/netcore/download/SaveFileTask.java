package zgl.com.cn.libandroid.net.netcore.download;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;
import zgl.com.cn.libandroid.net.netcore.callback.IListener;
import zgl.com.cn.libandroid.net.netcore.callback.IProgress;
import zgl.com.cn.libandroid.net.netcore.callback.ISuccess;

/**
 * Created by ZGL on 2018/6/6.
 */

public class SaveFileTask extends AsyncTask<Object, Integer, File> {

    private TaskListener mListener;

    public SaveFileTask(TaskListener  listener) {
        this.mListener = listener;
    }


    /**
     * 后台执行的具体任务
     * @param params
     * @return
     */
    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        ResponseBody body = (ResponseBody) params[2];
        String name = (String) params[3];

        InputStream is = body.byteStream();
        final long total = body.contentLength();
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "AAAA";
        }
        if (extension == null) {
            extension = "";
        }

        if (name == null) {
            return FileUtil.writeToDisk(is, downloadDir, extension.toUpperCase(), extension, new IProgress() {
                @Override
                public void onProgress(int progress, long loaded) {
                    publishProgress((int)(progress*100f/total),(int)loaded);
                }

            });
        } else {
            return FileUtil.writeToDisk(is, downloadDir, name,new IProgress() {
                @Override
                public void onProgress(int progress, long loaded) {
                    publishProgress((int)(progress*100f/total),(int)loaded);
                }
            });
        }

    }

    /**
     * 进度监听
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (mListener != null) {
            mListener.onProgress(values[0],values[1]);
        }
    }

    /**
     * 如果文件已经下完了
     */
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (mListener != null) {
            mListener.postExecute(file.getPath());
        }

    }


    /**
     * 执行任务完毕的回调监听
     */
    public interface TaskListener{
        void postExecute(String filePath);
        void onProgress(int progress,long loaded);
    }

}
