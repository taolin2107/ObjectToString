
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
}
