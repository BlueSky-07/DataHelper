package me.ihint.datahelper.test.java.impl.core.datatype.mysql;

import me.ihint.datahelper.exception.verify.ValueIsNullException;
import me.ihint.datahelper.core.Data;
import me.ihint.datahelper.core.Field;
import me.ihint.datahelper.impl.core.config.SimpleConfig;
import me.ihint.datahelper.impl.core.datatype.mysql.BOOLEAN;

public class BooleanTest {
    public static void test() {
        Field field = new Field("fieldname", BOOLEAN.INSTANCE, new SimpleConfig());

        Data data = field.newData();
        assert (data.verify(false) == false);
        assert (data.verify(true) == true);
        try {
            BOOLEAN.INSTANCE.toEntry(data);
            throw new Exception();
        } catch (Exception e) {
            assert (e instanceof ValueIsNullException);
        }

        data.setValue("abc");
        assert (data.verify(false) == false);
        assert (data.verify(true) == false);

        data.setValue("1");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);
        assert (BOOLEAN.INSTANCE.toEntry(data).getValue().equals("TRUE"));

        data.setValue("True");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);
        assert (BOOLEAN.INSTANCE.toEntry(data).getValue().equals("TRUE"));

        data.setValue("0");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);
        assert (BOOLEAN.INSTANCE.toEntry(data).getValue().equals("FALSE"));

        data.setValue("False");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);
        assert (BOOLEAN.INSTANCE.toEntry(data).getValue().equals("FALSE"));

        System.out.println("BOOLEAN tests passed");
    }
}
