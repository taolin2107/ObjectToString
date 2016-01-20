
import com.google.gson.Gson;

import java.util.List;

/**
 * @author Terry Tao
 * @version V1.0
 * @description
 * @date Jan 20, 2016
 */
public class Test {
    public static void main(String[] args) {
        String s = "{\"city\":{\"id\":2643743,\"name\":\"London\",\"coord\":{\"lon\":-0.12574,\"lat\":51.50853},\"country\":\"GB\",\"population\":0},\"cod\":\"200\",\"message\":0.0132,\"cnt\":2,\"list\":[{\"dt\":1453204800,\"temp\":{\"day\":273.82,\"min\":268.24,\"max\":273.82,\"night\":268.24,\"eve\":270.9,\"morn\":270.1},\"pressure\":1022.39,\"humidity\":0,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01d\"}],\"speed\":1.53,\"deg\":303,\"clouds\":50,\"snow\":0.03},{\"dt\":1453291200,\"temp\":{\"day\":275.39,\"min\":271.37,\"max\":276.02,\"night\":271.37,\"eve\":274.16,\"morn\":275.48},\"pressure\":1028.11,\"humidity\":99,\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"speed\":1.91,\"deg\":311,\"clouds\":20}]}";
        Gson gson = new Gson();
        WeatherModel m = gson.fromJson(s, WeatherModel.class);
        System.out.print(StringUtils.objectToString(m));
    }

    public class WeatherModel {
        public City city;
        public String cod;
        public String message;
        public String cnt;
        public List<WeatherData> list;

        public class City {
            public String id;
            public String name;
            public Coord coord;
            public String country;
        }

        public class Coord {
            public String lon;
            public String lat;
        }

        public class WeatherData {
            public String dt;
            public Temp temp;
            public String pressure;
            public String humidity;
            public List<Weather> weather;
            public String speed;
            public String deg;
        }

        public class Temp {
            public String day;
            public String min;
            public String max;
        }

        public class Weather {
            public String id;
            public String main;
            public String description;
            public String icon;
        }
    }
}
