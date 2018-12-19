/**
 * Copyright 2018 bejson.com
 */
package zgl.com.cn.libandroid.net.network.bean;

/**
 * Auto-generated: 2018-04-08 17:32:45
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Lives {

    private String province;
    private String city;
    private String adcode;
    private String weather;
    private String temperature;
    private String winddirection;
    private String windpower;
    private String humidity;
    private String reporttime;

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeather() {
        return weather;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setWinddirection(String winddirection) {
        this.winddirection = winddirection;
    }

    public String getWinddirection() {
        return winddirection;
    }

    public void setWindpower(String windpower) {
        this.windpower = windpower;
    }

    public String getWindpower() {
        return windpower;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public String getReporttime() {
        return reporttime;
    }

    @Override
    public String toString() {
        return "Lives{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", adcode='" + adcode + '\'' +
                ", weather='" + weather + '\'' +
                ", temperature='" + temperature + '\'' +
                ", winddirection='" + winddirection + '\'' +
                ", windpower='" + windpower + '\'' +
                ", humidity='" + humidity + '\'' +
                ", reporttime=" + reporttime +
                '}';
    }
}