package com.sh321han.mommyshare.Manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;
import com.sh321han.mommyshare.MyApplication;
import com.sh321han.mommyshare.data.MainProduct;
import com.sh321han.mommyshare.data.MainProductResult;
import com.sh321han.mommyshare.data.ProductDetailData;
import com.sh321han.mommyshare.data.ProductDetailDataResult;
import com.sh321han.mommyshare.data.WriteData;
import com.sh321han.mommyshare.data.WriteDataResult;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkManager {
    private static NetworkManager instance;

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    private static final int DEFAULT_CACHE_SIZE = 50 * 1024 * 1024;
    private static final String DEFAULT_CACHE_DIR = "miniapp";
    OkHttpClient mClient;

    private NetworkManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Context context = MyApplication.getContext();
        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));

        File dir = new File(context.getExternalCacheDir(), DEFAULT_CACHE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        builder.cache(new Cache(dir, DEFAULT_CACHE_SIZE));

        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);

        mClient = builder.build();
    }

    public interface OnResultListener<T> {
        public void onSuccess(Request request, T result);

        public void onFail(Request request, IOException exception);
    }

    private static final int MESSAGE_SUCCESS = 1;
    private static final int MESSAGE_FAIL = 2;

    class NetworkHandler extends Handler {
        public NetworkHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            NetworkResult result = (NetworkResult) msg.obj;
            switch (msg.what) {
                case MESSAGE_SUCCESS:
                    result.listener.onSuccess(result.request, result.result);
                    break;
                case MESSAGE_FAIL:
                    result.listener.onFail(result.request, result.excpetion);
                    break;
            }
        }
    }

    NetworkHandler mHandler = new NetworkHandler(Looper.getMainLooper());

    static class NetworkResult<T> {
        Request request;
        OnResultListener<T> listener;
        IOException excpetion;
        T result;
    }

    Gson gson = new Gson();

    private static final String MOMMYSHARE_SERVER = "http://52.79.57.157:3000";
    private static final String MAIN_PRODUCT_URL = MOMMYSHARE_SERVER + "/product/list";

    public Request MainProductList( OnResultListener<List<MainProduct>> listener) {

        Request request = new Request.Builder()
                .url(MAIN_PRODUCT_URL)
                .header("Accept", "application/json")
                .build();


        final NetworkResult<List<MainProduct>> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    MainProductResult data = gson.fromJson(response.body().charStream(), MainProductResult.class);
                    result.result = data.result;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }





    private static final String PRODUCT_DETAIL_URL = MOMMYSHARE_SERVER + "/product/%s";

    public Request ProductDetail(String id, OnResultListener<ProductDetailData> listener) {

        String url = String.format(PRODUCT_DETAIL_URL, id);
        Request request = new Request.Builder()
                .url(url)
                .build();


        final NetworkResult<ProductDetailData> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ProductDetailDataResult data = gson.fromJson(response.body().charStream(), ProductDetailDataResult.class);

                    result.result = data.result;

                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }






    private static final String PRODUCT_WRITE_URL = MOMMYSHARE_SERVER + "/product/write";

    public Request ProductWrite(String name, OnResultListener<WriteData> listener) {

        RequestBody body = new FormBody.Builder()
                .add("name", name)
//                .add("link", link)
//                .add("caption", caption)
//                .add("picture", picture)
//                .add("name", name)
//                .add("description", description)
                .build();


        Request request = new Request.Builder()
                .url(PRODUCT_WRITE_URL)
                .post(body)
                .build();


        final NetworkResult<WriteData> result = new NetworkResult<>();
        result.request = request;
        result.listener = listener;

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.excpetion = e;
                mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    WriteDataResult data = gson.fromJson(response.body().charStream(), WriteDataResult.class);

                    result.result = data.result;

                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    result.excpetion = new IOException(response.message());
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_FAIL, result));
                }
            }
        });
        return request;
    }

}
