 package com.example.weather;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

//https://api.openweathermap.org/data/2.5/weather?q=Brzeg&appid=9e78fe82c3c763872a651f2affb0f808

@Entity
@Table(name = "weather")
public class WeatherClass{

        @javax.persistence.Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long Id;
        private String Name;
        private int timeZone;
        private float Temperature;
        private float windSpeed;
        private Integer Pressure;
        private String Date;
        private String weatherType;

        public WeatherClass(){

        }

        public String gregorianCalendar (Long dt){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS", Locale.UK);
                GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(dt*1000);
                return sdf.format(calendar.getTime());
        }
        public WeatherClass(String name, int timezone, float temp, float speed, int pressure, Long dt, String weather) {
                this.Name = name;
                this.timeZone = timezone/3600;
                this.Temperature = temp - 273.16f;
                this.windSpeed = speed;
                this.Pressure = pressure;
                this.Date = gregorianCalendar(dt);
                this.weatherType = weather;
        }

        public Long getID(){
                return this.Id;
        }
        public String getName() {
                return Name;
        }

        public void setName(String name) {
                this.Name = name;
        }

        public int getTimeZone() {
                return timeZone;
        }

        public void setTimeZone(int timeZone) {
                this.timeZone = timeZone;
        }

        public float getTemperature() {
                return Temperature;
        }

        public void setTemperature(float temperature) {
                Temperature = temperature;
        }

        public float getWindSpeed() {
                return windSpeed;
        }

        public void setWindSpeed(Integer windSpeed) {
                this.windSpeed = windSpeed;
        }

        public Integer getPressure() {
                return Pressure;
        }

        public void setPressure(Integer pressure) {
                Pressure = pressure;
        }

        public String getDate() {
                return Date;
        }

        public void setDate(Long dt) {
                Date = gregorianCalendar(dt);
        }

        public String getWeatherType() {
                return weatherType;
        }

        public void setWeatherType(String weatherType) {
                this.weatherType = weatherType;
        }
        //
//        public int ID{get;set;}
//        public String Name {get;set;}
//        public String TimeZone{get;set;}
//        public int Temperature{get;set;}
//        public int WindSpeed{get;set;}
//        public int Pressure{get;set;}
//        public String Date{get;set;}
//        public String WeatherType{get;set;}
}


