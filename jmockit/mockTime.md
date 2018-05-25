# MockTime
---
用jmockit实现时间的模拟
```java
public class MockSystemTimeUtil {

    public static void fixSystemTime(int year, int month, int date, int hourOfDay,
                                    int minute, int second, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, date);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        setSystemTime(calendar.getTimeInMillis());
    }

    public static void fixSystemTime(long milliTime) {
        setSystemTime(milliTime);
    }

    private static void setSystemTime(long milliTime) {
        // 此处就是模拟某类的某个方法
        new MockUp<Calendar>() {
            @Mock
            public Calendar getInstance(Invocation inv) {
                // Invocation是用来执行该类的构造方法的， proceed()是执行真正的构造方法（比如这个类的构造实现是由某个实现类实现的）
                Calendar calendar = inv.proceed();
                calendar.setTimeInMillis(milliTime);
                return calendar;
            }
        };
        new MockUp<System>() {
            @Mock
            public long currentTimeMillis() {
                return milliTime;
            }
        };
    }

}
```
