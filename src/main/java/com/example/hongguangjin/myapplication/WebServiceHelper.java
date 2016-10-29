package com.example.hongguangjin.myapplication;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongguang.jin on 2016/10/28.
 */
public class WebServiceHelper {

    //WSDL文档中的命名空间
    private static final String targetNameSpace = "http://WebXml.com.cn/";
    //WSDL文档中的URL
    private static final String WSDL = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx";

    //需要调用的方法名(获得本天气预报Web Services支持的洲、国内外省份和城市信息)
    private static final String getSupportProvince = "getRegionProvince";
    //需要调用的方法名(获得本天气预报Web Services支持的城市信息,根据省份查询城市集合：带参数)
    private static final String getSupportCity = "getSupportCityString";
    //根据城市或地区名称查询获得未来三天内天气情况、现在的天气实况、天气和生活指数
    private static final String getWeatherbyCityByCode = "getWeather";


    /********
     * 获得州，国内外省份和城市信息
     *
     * @return
     */
    public List<String> getProvince() {
        List<String> provinces = new ArrayList<String>();
        String str = "";
        SoapObject soapObject = new SoapObject(targetNameSpace, getSupportProvince);
//        soapObject.addProperty("parameter name", "value");//调用的方法参数与参数值（根据具体需要可选可不选）

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);//envelope.bodyOut=request;


//        AndroidHttpTransport httpTranstation=new AndroidHttpTransport(WSDL);
        HttpTransportSE httpTranstation = new HttpTransportSE(WSDL);
        try {

            httpTranstation.call(targetNameSpace + getSupportProvince, envelope);
            SoapObject result = (SoapObject) envelope.getResponse();
            //下面对结果进行解析，结构类似json对象
            //str=(String) result.getProperty(6).toString();

            int count = result.getPropertyCount();
            for (int index = 0; index < count; index++) {
                provinces.add(result.getProperty(index).toString());
            }
            Log.i("ScrollingActivity", provinces.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return provinces;
    }

    /**********
     * 根据省份或者直辖市获取天气预报所支持的城市集合
     *
     * @param province
     * @return
     */
    public List<String> getCitys(String province) {
        List<String> citys = new ArrayList<String>();
        SoapObject soapObject = new SoapObject(targetNameSpace, getSupportCity);
        soapObject.addProperty("theRegionCode", province);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);
        HttpTransportSE httpTransport = new HttpTransportSE(WSDL);
        try {
            httpTransport.call(targetNameSpace + getSupportCity, envelope);
            SoapObject result = (SoapObject) envelope.getResponse();
//            same solution ...
//            SoapObject result = (SoapObject) envelope.bodyIn;
//            SoapObject results = (SoapObject) result.getProperty("getSupportCityStringResult");
//            Log.i("ScrollingActivity", "getSupportCityStringResult="+results.getProperty(0));

            int count = result.getPropertyCount();
            for (int index = 0; index < count; index++) {
                citys.add(result.getProperty(index).toString());
            }
            Log.i("ScrollingActivity", citys.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return citys;
    }

    /***************************
     * 根据城市信息获取天气预报信息
     *
     * @param code
     * @return
     ***************************/
    public WeatherBean getWeatherBytheCityCode(String code) {

//        WeatherBean bean=new WeatherBean();

        HttpTransportSE httpTransport = new HttpTransportSE(WSDL);
        httpTransport.debug = true;

        SoapObject soapObject = new SoapObject(targetNameSpace, getWeatherbyCityByCode);
        PropertyInfo pi = new PropertyInfo();
        pi.setName("theCityCode");
        pi.setValue(code);
        pi.setType(code.getClass());
        soapObject.addProperty(pi);
//        soapObject.addProperty("theCityCode", code);//调用的方法参数与参数值（根据具体需要可选可不选）
//        soapObject.addProperty("theUserID", "");//调用的方法参数与参数值（根据具体需要可选可不选）
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        try {
            httpTransport.call(targetNameSpace + getWeatherbyCityByCode, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            SoapObject results = (SoapObject) result.getProperty("getWeatherResult");
            Log.i("ScrollingActivity", "getWeatherResult=" + results.getProperty(0));

            //下面对结果进行解析，结构类似json对象
//            bean=parserWeather(result);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    // 解析服务器响应的SOAP消息。
    private static Map<String, String> parseProvinceOrCity(SoapObject detail) {
        Map<String, String> result = new HashMap<String, String>();
        for (int i = 0; i < detail.getPropertyCount(); i++) {
            // 解析出每个省份
            result.put(detail.getProperty(i).toString().split(",")[1], detail.getProperty(i).toString().split(",")[0]);
        }
        return result;
    }

    /**
     * 解析返回的结果
     *
     * @param soapObject
     */
    protected WeatherBean parserWeather(SoapObject soapObject) {
        WeatherBean bean = new WeatherBean();
        //城市名
        bean.setCityName(soapObject.getProperty(1).toString());
        //更新时间
        bean.setUpateWeatherTime(soapObject.getProperty(3).toString());
        //今日天气
        bean.setTodayWeather(soapObject.getProperty(4).toString());
        //空气质量
        bean.setTodayEnvironment(soapObject.getProperty(5).toString());
        //城市简介
        bean.setCityDescription(soapObject.getProperty(6).toString());

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int length = soapObject.getPropertyCount();
        Map<String, Object> map;
        List<Integer> icons;
        for (int i = 0; i < length; i = i + 5) {
            map = new HashMap<String, Object>();
            String date = soapObject.getProperty(7 + i).toString();
            String weatherDay = "今天：" + date.split(" ")[0]; //日期
            weatherDay += "\n天气：" + date.split(" ")[1];
            weatherDay += "\n气温：" + soapObject.getProperty(8 + i).toString();
            weatherDay += "\n风力：" + soapObject.getProperty(9 + i).toString();
            weatherDay += "\n";

            icons = new ArrayList<Integer>();
            icons.add(parseIcon(soapObject.getProperty(10 + i).toString()));
            icons.add(parseIcon(soapObject.getProperty(11 + i).toString()));

            map.put("weatherDay", weatherDay);
            map.put("icons", icons);
            list.add(map);
        }
        bean.setList(list);
        return bean;
    }

    //解析图标字符串
    private int parseIcon(String data) {
        // 0.gif，返回名称0,
        int resID = 32;
        String result = data.substring(0, data.length() - 4).trim();
        // String []icon=data.split(".");
        // String result=icon[0].trim();
        //   Log.e("this is the icon", result.trim());

        if (!result.equals("nothing")) {
            resID = Integer.parseInt(result.trim());
        }
        return resID;
        //return ("a_"+data).split(".")[0];
    }
}
