package me.ihint.datahelper.test.java.impl.core.datatype.mysql;

import me.ihint.datahelper.core.Data;
import me.ihint.datahelper.core.Field;
import me.ihint.datahelper.exception.verify.ValueIsNullException;
import me.ihint.datahelper.impl.core.config.SimpleConfig;
import me.ihint.datahelper.impl.core.datatype.mysql.TEXT;

public class TextTest {
    public static void test() {
        SimpleConfig config = new SimpleConfig();
        Field field = new Field("fieldname", TEXT.INSTANCE, config);

        Data data = field.newData();
        assert (data.verify(false) == false);
        assert (data.verify(true) == true);
        try {
            TEXT.INSTANCE.toEntry(data);
            throw new Exception();
        } catch (Exception e) {
            assert (e instanceof ValueIsNullException);
        }

        data.setValue("123");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);
        assert (TEXT.INSTANCE.toEntry(data).getValue().equals("`123`"));

        config.set("max", 5);
        data = field.newData("12345");
        assert (data.verify(false) == false);
        assert (data.verify(true) == false);
        data.setValue("1234");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);

        config.set("min", 3);
        data = field.newData("12");
        assert (data.verify(false) == false);
        assert (data.verify(true) == false);
        data.setValue("123");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);

        System.out.println("TEXT tests passed");
    }
}
