package zgl.com.cn.libandroid.net.netcore.download;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import zgl.com.cn.libandroid.net.netcore.callback.IProgress;
import zgl.com.cn.libandroid.net.netcore.callback.ISuccess;

/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2018/12/4
 */

public class ProgressResponseBody extends ResponseBody {
    public static final int UPDATE = 0x01;
    public static final int COMPLETE = 0x02;

    public static final String TAG = ProgressResponseBody.class.getName();
    private ResponseBody responseBody;
    private IProgress mListener;
    private BufferedSource bufferedSource;
    private Handler myHandler;

    public ProgressResponseBody(ResponseBody body, IProgress listener) {
        responseBody = body;
        mListener = listener;
        if (myHandler == null) {
            myHandler = new MyHandler();
        }
    }

    /**
     * 将进度放到主线程中显示
     */
    class MyHandler extends Handler {

        public MyHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    int progress = (int) msg.arg1;
                    int loaded = (int) msg.arg2;
                    //接口返回
                    if (mListener != null) {
                        mListener.onProgress(progress,loaded);
                    }
                    break;
                default:
                    break;

            }
        }
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {

        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(mySource(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source mySource(Source source) {

        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //发送消息到主线程
                Message msg = Message.obtain();

                //下载中，更新进度
                msg.what = UPDATE;
                int progress = 0;
                if (contentLength()>0 && totalBytesRead>0) {
                    progress = (int) (totalBytesRead/contentLength()*10000);
                }
                msg.arg1 = progress;
                msg.arg2 = (int)totalBytesRead;

                myHandler.sendMessage(msg);

                return bytesRead;
            }
        };
    }
}