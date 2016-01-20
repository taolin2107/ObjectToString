ObjectToString
===========

##简介
项目中经常需要打印一个java bean对象中全部的属性，但是需要自己去实现toString方法，如果属性很多，写起来比较费事。
通过ObjectToString，可以直接将java bean对象转化成json格式的String。

##使用

```java
WeatherModel m;  //java bean对象
...
初始化m
...
System.out.print(objectToString(m))

```

打印结果如下：
![Test result](https://github.com/taolin2107/ObjectToString/raw/master/img/test_result.png)
