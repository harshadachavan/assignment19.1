package com.destaglobal.weatherapp;

/**
 * Created by Harshada Chavan on 12/24/2017.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Report extends AppCompatActivity implements OnWebServiceResult{

    String url = "http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa7972254124 29c1c50c122a1";

    TextView lon,lat,id, main,description,icon,base,temp,pressure,humidity,temp_min,temp_max,visibility,speed,deg,all,dt;
    TextView id_sys,message,country,sunrise,sunset,id_main,name,cod;
    TextView typeTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_info);

        lon=(TextView)findViewById(R.id.lon_tv);
        lat=(TextView)findViewById(R.id.lat_tv);
        id=(TextView)findViewById(R.id.id_tv);
        main=(TextView)findViewById(R.id.main_tv);
        description=(TextView)findViewById(R.id.description_tv);
        icon=(TextView)findViewById(R.id.icon_tv);
        base=(TextView)findViewById(R.id.base_tv);
        temp=(TextView)findViewById(R.id.temp_tv);
        pressure=(TextView)findViewById(R.id.pressure_tv);
        humidity=(TextView)findViewById(R.id.humidity_tv);
        temp_min=(TextView)findViewById(R.id.temp_min_tv);
        temp_max=(TextView)findViewById(R.id.temp_max_tv);
        visibility=(TextView)findViewById(R.id.visibility_tv);
        speed=(TextView)findViewById(R.id.speed_tv);
        deg=(TextView)findViewById(R.id.deg_tv);
        all=(TextView)findViewById(R.id.all_tv);
        dt=(TextView)findViewById(R.id.dt_tv);
        id_sys=(TextView)findViewById(R.id.id_sys_tv);
        message=(TextView)findViewById(R.id.message_tv);
        country=(TextView)findViewById(R.id.country_tv);
        sunrise=(TextView)findViewById(R.id.sunrise_tv);
        sunset=(TextView)findViewById(R.id.sunset_tv);
        id_main=(TextView)findViewById(R.id.id_main_tv);
        name=(TextView)findViewById(R.id.name_tv);
        cod=(TextView)findViewById(R.id.cod_tv);
        typeTV=(TextView)findViewById(R.id.type_tv);

        hitRequest();

    }

    private void hitRequest()
    {
        FormEncodingBuilder parameters=new FormEncodingBuilder();
        parameters.add("page","1");

        if(NetworkStatus.getInstance(this).isConnectedToInternet())
        {
            CallAddr call = new CallAddr(this,url,parameters, CommonUtilities.SERVICE_TYPE.GET_DATA,this);
            call.execute();
        }
        else
        {
            Toast.makeText(this,"No Network ! You are offline.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getWebResponse(String result, CommonUtilities.SERVICE_TYPE type)
    {
        try
        {
            JSONObject object=new JSONObject(result);
            DataHandler myData = new DataHandler();
            JSONObject coordObj=object.getJSONObject("coord");

            myData.setLon("lon : "+coordObj.getString("lon"));
            myData.setLon("lat : "+coordObj.getString("lat"));

            JSONArray weatherArray = object.getJSONArray("weather");
            for(int i=0;i<weatherArray.length();i++)
            {
                myData.setId("id : "+weatherArray.getJSONObject(i).getInt("id"));
                myData.setMain("main : "+weatherArray.getJSONObject(i).getString("main"));
                myData.setDescription("description : "+weatherArray.getJSONObject(i).getString("description"));
                myData.setIcon("icon : "+weatherArray.getJSONObject(i).getString("icon"));
            }

            myData.setBase("base : "+object.getString("base"));

            JSONObject mainObject=object.getJSONObject("main");
            myData.setTemp("temp : "+mainObject.getDouble("temp"));
            myData.setPressure("pressure : "+mainObject.getDouble("pressure"));
            myData.setHumidity("humidity : "+mainObject.getInt("humidity"));
            myData.setTemp_min("temp_min : "+mainObject.getDouble("temp_min"));
            myData.setTemp_max("temp_max : "+mainObject.getDouble("temp_max"));

            myData.setVisibility("visibility : "+object.getInt("visibility"));

            JSONObject windObj = object.getJSONObject("wind");
            myData.setSpeed("speed : "+windObj.getDouble("speed"));
            myData.setDeg("deg : "+windObj.getInt("deg"));

            JSONObject cloudObj=object.getJSONObject("clouds");
            myData.setAll("all : "+cloudObj.getInt("all"));

            myData.setDt("dt : "+object.getString("dt"));

            JSONObject sysObj = object.getJSONObject("sys");
            myData.setType("type : "+sysObj.getInt("type"));
            myData.setId_sys("id_sys : "+sysObj.getInt("id"));
            myData.setMessage("message : "+sysObj.getDouble("message"));
            myData.setCountry("country : "+sysObj.getString("country"));
            myData.setSunrise("sunrise : "+sysObj.getString("sunrise"));
            myData.setSunset("sunset : "+sysObj.getString("sunset"));

            myData.setId_main("id_main : "+object.getInt("id"));
            myData.setName("name : "+object.getString("name"));
            myData.setCod("cod : "+object.getInt("cod"));

            lon.setText(myData.getLon());
            lat.setText(myData.getLat());
            id.setText(myData.getId());
            main.setText(myData.getMain());
            description.setText(myData.getDescription());
            icon.setText(myData.getIcon());
            base.setText(myData.getBase());
            temp.setText(myData.getTemp());
            pressure.setText(myData.getPressure());
            humidity.setText(myData.getHumidity());
            temp_min.setText(myData.getTemp_min());
            temp_max.setText(myData.getTemp_max());
            visibility.setText(myData.getVisibility());
            speed.setText(myData.getSpeed());
            deg.setText(myData.getDeg());
            all.setText(myData.getAll());
            dt.setText(myData.getDt());
            typeTV.setText(myData.getType());
            id_sys.setText(myData.getId_sys());
            message.setText(myData.getMessage());
            country.setText(myData.getCountry());
            sunrise.setText(myData.getSunrise());
            sunset.setText(myData.getSunset());
            id_main.setText(myData.getId_main());
            name.setText(myData.getName());
            cod.setText(myData.getCod());

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
