package me.ihint.datahelper.test.java.impl.core.datatype.mysql;

import me.ihint.datahelper.exception.verify.ValueIsNullException;
import me.ihint.datahelper.core.Data;
import me.ihint.datahelper.core.Field;
import me.ihint.datahelper.impl.core.config.SimpleConfig;
import me.ihint.datahelper.impl.core.datatype.mysql.INTEGER;

public class IntegerTest {
    public static void test() {
        SimpleConfig config = new SimpleConfig();
        Field field = new Field("fieldname", INTEGER.INSTANCE, config);

        Data data = field.newData();
        assert (data.verify(false) == false);
        assert (data.verify(true) == true);
        try {
            INTEGER.INSTANCE.toEntry(data);
            throw new Exception();
        } catch (Exception e) {
            assert (e instanceof ValueIsNullException);
        }

        data.setValue("abc");
        assert (data.verify(false) == false);
        assert (data.verify(true) == false);

        config.set("max", 123);
        data = field.newData("123");
        assert (data.verify(false) == false);
        assert (data.verify(true) == false);
        data.setValue("122");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);
        assert (INTEGER.INSTANCE.toEntry(data).getValue().equals("122"));

        config = new SimpleConfig();
        field = new Field("fieldname", INTEGER.INSTANCE, config);
        config.set("min", 123);
        data = field.newData("122");
        assert (data.verify(false) == false);
        assert (data.verify(true) == false);
        data.setValue("123");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);
        assert (INTEGER.INSTANCE.toEntry(data).getValue().equals("123"));

        config = new SimpleConfig();
        field = new Field("fieldname", INTEGER.INSTANCE, config);
        config.set("min", 123);
        config.set("max", 124);
        data = field.newData("123");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);
        assert (INTEGER.INSTANCE.toEntry(data).getValue().equals("123"));
        data.setValue("124");
        assert (data.verify(false) == false);
        assert (data.verify(true) == false);

        System.out.println("INTEGER tests passed");
    }
}
