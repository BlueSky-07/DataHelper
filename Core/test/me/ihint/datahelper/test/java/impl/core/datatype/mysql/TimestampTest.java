package me.ihint.datahelper.test.java.impl.core.datatype.mysql;

import kotlin.TypeCastException;
import me.ihint.datahelper.core.Config;
import me.ihint.datahelper.exception.verify.ValueIsNullException;
import me.ihint.datahelper.core.Data;
import me.ihint.datahelper.core.Field;
import me.ihint.datahelper.impl.core.config.SimpleConfig;
import me.ihint.datahelper.impl.core.datatype.mysql.TIMESTAMP;

import java.time.format.DateTimeFormatter;

public class TimestampTest {
    final private static DateTimeFormatter FORMATTER_ISO = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    final private static DateTimeFormatter FORMATTER_TIMESTAMP = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static void test() {
        Config config = new SimpleConfig();
        Field field = new Field("fieldname", TIMESTAMP.INSTANCE, config);

        Data data = field.newData();
        assert (data.verify(false) == false);
        assert (data.verify(true) == true);
        try {
            TIMESTAMP.INSTANCE.toEntry(data);
            throw new Exception();
        } catch (Exception e) {
            assert (e instanceof ValueIsNullException);
        }

        config.set("read", FORMATTER_ISO);
        config.set("write", FORMATTER_TIMESTAMP);
        data = field.newData("2011-12-03T10:15:30.000Z");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);
        assert (TIMESTAMP.INSTANCE.toEntry(data).getValue().equals("'2011/12/03 10:15:30'"));

        data.setValue("2011/12/03 10:15:30");
        assert (data.verify(false) == false);
        assert (data.verify(true) == false);

        config = new SimpleConfig();
        field = new Field("fieldname", TIMESTAMP.INSTANCE, config);
        data = field.newData("2011-12-03T10:15:30.000Z");
        try {
            data.verify(false);
            throw new Exception();
        } catch (Exception e) {
            assert (e instanceof TypeCastException);
        }

        System.out.println("TIMESTAMP tests passed");
    }
}
