package com.example.weather;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Controller
public class WeatherController {

    @Autowired
    private final WeatherRepository weatherRepository;

    private final String weather_json = getApi("Wroclaw");
//    private final String weather_json = "{\"coord\":{\"lon\":17.4674,\"lat\":50.8608},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"base\":\"stations\",\"main\":{\"temp\":296.15,\"feels_like\":296.36,\"temp_min\":291.78,\"temp_max\":296.99,\"pressure\":1018,\"humidity\":71,\"sea_level\":1018,\"grnd_level\":1001},\"visibility\":10000,\"wind\":{\"speed\":4.48,\"deg\":125,\"gust\":11.1},\"clouds\":{\"all\":1},\"dt\":1623959571,\"sys\":{\"type\":2,\"id\":2000558,\"country\":\"PL\",\"sunrise\":1623897352,\"sunset\":1623956778},\"timezone\":7200,\"id\":3102459,\"name\":\"Brzeg\",\"cod\":200}";
    //Dependency Injection
    public WeatherController(WeatherRepository weatherRepository) throws IOException {
        this.weatherRepository = weatherRepository;
    }

    public String getApi(String miasto) throws IOException {

        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+miasto+"&appid=");
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Api-Key", "9e78fe82c3c763872a651f2affb0f808");
        con.setRequestProperty("Accept", "application/json");
        con.connect();
        Scanner scanner = new Scanner(con.getInputStream());
        return scanner.nextLine();
        }



    // localhost:8080/weathers
    @GetMapping("/weathers")
    public String getAll(Model model) {
        String newCity = "";
        model.addAttribute("weathers", this.weatherRepository.findAll());
        model.addAttribute("city", newCity);
        return "weathers";
    }

    @PostMapping("/addWeather")
public String addWeather(Model model, @RequestParam String newCity) throws IOException{
        String jsonResponse = getApi(newCity);
        Gson gson = new Gson();
        WeatherJson w = gson.fromJson(jsonResponse, WeatherJson.class);
        WeatherClass weather = new WeatherClass(w.getName(), w.getTimezone(), w.main.getTemp(), w.wind.getSpeed(), w.main.getPressure(), w.getDt(), w.getWeather(0).getDescription());
        System.out.println(weather.getName());
        weatherRepository.save(weather);
        System.out.println(newCity);
        return "redirect:/weathers";
    }
}
