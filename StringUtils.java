
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Terry Tao
 * @version V1.0
 * @description
 * @date Jan 20, 2016
 */
public class StringUtils {

    /**
     * 将java bean 对象转化为json格式的String
     * @param o java bean对象
     * @return
     */
    public static String objectToString(Object o) {
        return objectToString(o, "", 4);
    }

    /**
     * 将java bean 对象转化为json格式的String
     * @param o java bean对象
     * @param tabLength 缩进空格数, 取值0到10，0表示不换行
     * @return json格式的string
     */
    public static String objectToString(Object o, int tabLength) {
        if (tabLength < 0 || tabLength > 10) {
            throw new IllegalArgumentException("tabLength must be a number between 0 to 10.");
        }
        return objectToString(o, "", tabLength);
    }

    private static String objectToString(Object o, String parent, int tabLength) {
        String tab = "";
        String wrapper = " ";
        if (tabLength > 0) {
            tab = String.format("%" + tabLength + "c", ' ');
            wrapper = "\n";
        }
        String indent = parent + tab;
        StringBuilder ret = new StringBuilder("");
        ret.append("{" + wrapper);
        Field[] fields = o.getClass().getFields();
        try {
            for (Field f: fields) {
                f.setAccessible(true);
                Object value = f.get(o);
                if (isPrimitive(f)) {
                    ret.append(indent + f.getName() + ": " + value + "," + wrapper);
                } else if (value instanceof List) {
                    ret.append(indent + f.getName() + ": [" + wrapper);
                    for (Object ob: (List) value) {
                        ret.append(indent + tab);
                        ret.append(objectToString(ob, indent + tab, tabLength));
                    }
                    ret.deleteCharAt(ret.length() - 2);
                    ret.append(indent + "]," + wrapper);
                } else {
                    ret.append(indent + f.getName() + ": ");
                    ret.append(objectToString(value, indent, tabLength));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (fields.length > 0) {
            ret.deleteCharAt(ret.length() - 2);
        }
        if (tabLength == 0) {
            ret.append(parent + "}," + wrapper);
        } else {
            ret.append(parent.length() > 0 ? parent + "}," + wrapper: "}" + wrapper);
        }
        return ret.toString();
    }

    private static boolean isPrimitive(Field f) {
        Class[] classes = {
                byte.class, Byte.class, short.class, Short.class,
                char.class, Character.class, int.class, Integer.class,
                long.class, Long.class, float.class, Float.class,
                double.class, Double.class, boolean.class, Boolean.class,
                String.class, void.class
        };
        for (Class c: classes) {
            if (f.getType() == c) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String s = "{\"city\":{\"id\":2643743,\"name\":\"London\",\"coord\":{\"lon\":-0.12574,\"lat\":51.50853},\"country\":\"GB\",\"population\":0},\"cod\":\"200\",\"message\":0.0132,\"cnt\":2,\"list\":[{\"dt\":1453204800,\"temp\":{\"day\":273.82,\"min\":268.24,\"max\":273.82,\"night\":268.24,\"eve\":270.9,\"morn\":270.1},\"pressure\":1022.39,\"humidity\":0,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01d\"}],\"speed\":1.53,\"deg\":303,\"clouds\":50,\"snow\":0.03},{\"dt\":1453291200,\"temp\":{\"day\":275.39,\"min\":271.37,\"max\":276.02,\"night\":271.37,\"eve\":274.16,\"morn\":275.48},\"pressure\":1028.11,\"humidity\":99,\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"speed\":1.91,\"deg\":311,\"clouds\":20}]}";
        Gson gson = new Gson();
        WeatherModel m = gson.fromJson(s, WeatherModel.class);
        System.out.print(objectToString(m));
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
