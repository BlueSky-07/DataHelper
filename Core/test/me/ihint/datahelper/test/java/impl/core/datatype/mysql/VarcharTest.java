package me.ihint.datahelper.test.java.impl.core.datatype.mysql;

import me.ihint.datahelper.exception.verify.ValueIsNullException;
import kotlin.TypeCastException;
import me.ihint.datahelper.core.Data;
import me.ihint.datahelper.core.Field;
import me.ihint.datahelper.impl.core.config.SimpleConfig;
import me.ihint.datahelper.impl.core.datatype.mysql.VARCHAR;

import java.util.regex.Pattern;

public class VarcharTest {
    public static void test() {
        SimpleConfig config = new SimpleConfig();
        config.set("length", 5);
        Field field = new Field("fieldname", VARCHAR.INSTANCE, config);

        Data data = field.newData();
        assert (data.verify(false) == false);
        assert (data.verify(true) == true);
        try {
            VARCHAR.INSTANCE.toEntry(data);
            throw new Exception();
        } catch (Exception e) {
            assert (e instanceof ValueIsNullException);
        }

        data.setValue("123");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);
        assert (VARCHAR.INSTANCE.toEntry(data).getValue().equals("`123`"));

        data.setValue("123456");
        assert (data.verify(false) == false);
        assert (data.verify(true) == false);

        config.set("pattern", Pattern.compile("[\\s]+"));
        data = field.newData("123");
        assert (data.verify(false) == false);
        assert (data.verify(true) == false);

        config = new SimpleConfig();
        config.set("length", 5);
        config.set("pattern", Pattern.compile("[\\d]+"));
        field = new Field("fieldname", VARCHAR.INSTANCE, config);
        data = field.newData("123");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);
        assert (VARCHAR.INSTANCE.toEntry(data).getValue().equals("`123`"));

        data = new Field("fieldname", VARCHAR.INSTANCE, new SimpleConfig()).newData();
        data.setValue("123");
        try {
            data.verify(true);
            throw new Exception();
        } catch (Exception e) {
            assert (e instanceof TypeCastException);
        }

        System.out.println("VARCHAR tests passed");
    }
}
