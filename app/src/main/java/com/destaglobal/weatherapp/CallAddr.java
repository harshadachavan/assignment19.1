package com.destaglobal.weatherapp;

/**
 * Created by Harshada Chavan on 12/24/2017.
 */
import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okio.Buffer;
public class CallAddr extends AsyncTask<String,Void,String>
{

    Context context;
    String result="";
    FormEncodingBuilder formBody;
    String url;
    OnWebServiceResult resultListener;
    CommonUtilities.SERVICE_TYPE Servicetype;
    Request request;

    public Request getRequest()
    {
        return request;
    }

    public CallAddr(Context context, String url, FormEncodingBuilder formBody, CommonUtilities.SERVICE_TYPE Servicetype, OnWebServiceResult resultListener)
    {
        this.context = context;
        this.formBody = formBody;
        this.url = url;
        this.resultListener = resultListener;
        this.Servicetype = Servicetype;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params)
    {
        OkHttpClient client=new OkHttpClient();
        client.setConnectTimeout(120, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(120, TimeUnit.SECONDS); // socket timeout

        RequestBody body=formBody.build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try
        {
            Response response = client.newCall(request).execute();
            if(!response.isSuccessful())
            {
                result=response.toString();
            }
            result=response.body().string();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        resultListener.getWebResponse(s,Servicetype);
    }

    private  static String bodyToString(final Request request)
    {
        try
        {
            final Request copy=request.newBuilder().build();
            final Buffer buffer=new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        }
        catch (IOException e)
        {
            return "did not worked";
        }
    }
}
