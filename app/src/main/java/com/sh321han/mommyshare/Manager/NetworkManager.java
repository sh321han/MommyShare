package com.sh321han.mommyshare.Manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;
import com.sh321han.mommyshare.GoogleMap.AddressInfo;
import com.sh321han.mommyshare.GoogleMap.AddressInfoResult;
import com.sh321han.mommyshare.GoogleMap.PoiSearchResult;
import com.sh321han.mommyshare.GoogleMap.SearchPOIInfo;
import com.sh321han.mommyshare.MyApplication;
import com.sh321han.mommyshare.data.MainProduct;
import com.sh321han.mommyshare.data.MainProductResult;
import com.sh321han.mommyshare.data.ProductDetailData;
import com.sh321han.mommyshare.data.ProductDetailDataResult;
import com.sh321han.mommyshare.data.WriteData;
import com.sh321han.mommyshare.data.WriteDataResult;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URLEncoder;
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

    public Request ProductWrite(String name, int rent_fee, int rent_deposit, int rent_min_period, int rent_max_period,
                                OnResultListener<WriteData> listener) {

        RequestBody body = new FormBody.Builder()
                .add("name", name)
                .add("rent_fee", rent_fee+"")
                .add("rent_deposit", rent_deposit+"")
                .add("rent_min_period", rent_min_period+"")
                .add("rent_max_period", rent_max_period+"")
//                .add("content", content)
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


    private static final String TMAP_SERVER = "https://apis.skplanetx.com";
    private static final String TMAP_REVERSE_GEOCODING = TMAP_SERVER + "/tmap/geo/reversegeocoding?coordType=WGS84GEO&addressType=A02&lat=%s&lon=%s&version=1";
    public Request getTMapReverseGeocoding(Object tag, double lat, double lng, OnResultListener<AddressInfo> listener) {
        String url = String.format(TMAP_REVERSE_GEOCODING, "" + lat, ""+lng);
        Request request = new Request.Builder()
                .url(url)
                .header("Accept","application/json")
                .header("appKey","458a10f5-c07e-34b5-b2bd-4a891e024c2a")
                .build();
        final NetworkResult<AddressInfo> result = new NetworkResult<>();
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
                    AddressInfoResult data = gson.fromJson(response.body().charStream(), AddressInfoResult.class);
                    result.result = data.addressInfo;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }

    private static final String TMAP_SEARCH_POI = TMAP_SERVER + "/tmap/pois?searchKeyword=%s&resCoordType=WGS84GEO&version=1";
    public Request getTMapSearchPOI(Object tag, String keyword, OnResultListener<SearchPOIInfo> listener) throws UnsupportedEncodingException {
        String url = String.format(TMAP_SEARCH_POI, URLEncoder.encode(keyword,"utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .header("Accept","application/json")
                .header("appKey","458a10f5-c07e-34b5-b2bd-4a891e024c2a")
                .build();
        final NetworkResult<SearchPOIInfo> result = new NetworkResult<>();
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
                    PoiSearchResult data = gson.fromJson(response.body().charStream(), PoiSearchResult.class);
                    result.result = data.searchPoiInfo;
                    mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SUCCESS, result));
                } else {
                    throw new IOException(response.message());
                }
            }
        });
        return request;
    }


}
