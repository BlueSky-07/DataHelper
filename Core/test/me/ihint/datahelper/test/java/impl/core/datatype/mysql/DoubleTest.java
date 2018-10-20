package me.ihint.datahelper.test.java.impl.core.datatype.mysql;

import me.ihint.datahelper.exception.ValueIsNullException;
import me.ihint.datahelper.core.Data;
import me.ihint.datahelper.core.Field;
import me.ihint.datahelper.impl.core.config.SimpleConfig;
import me.ihint.datahelper.impl.core.datatype.mysql.DOUBLE;

public class DoubleTest {
    public static void test() {
        SimpleConfig config = new SimpleConfig();
        Field field = new Field("fieldname", DOUBLE.INSTANCE, config);
        
        Data data = field.newData();
        assert (data.verify(false) == false);
        assert (data.verify(true) == true);
        try {
            DOUBLE.INSTANCE.toEntry(data);
            throw new Exception();
        } catch (Exception e) {
            assert (e instanceof ValueIsNullException);
        }
        
        data.setValue("abc");
        assert (data.verify(false) == false);
        assert (data.verify(true) == false);
        
        config.set("max", 1.23d);
        data = field.newData("1.23");
        assert (data.verify(false) == false);
        assert (data.verify(true) == false);
        data.setValue("1.22");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);
        assert (DOUBLE.INSTANCE.toEntry(data).getValue().equals("1.22"));
        
        config = new SimpleConfig();
        config.set("min", 1.23d);
        field = new Field("fieldname", DOUBLE.INSTANCE, config);
        data = field.newData("1.22");
        assert (data.verify(false) == false);
        assert (data.verify(true) == false);
        data.setValue("1.23");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);
        assert (DOUBLE.INSTANCE.toEntry(data).getValue().equals("1.23"));
        
        config = new SimpleConfig();
        config.set("max", 1.24d);
        config.set("min", 1.23d);
        field = new Field("fieldname", DOUBLE.INSTANCE, config);
        data = field.newData("1.23");
        assert (data.verify(false) == true);
        assert (data.verify(true) == true);
        assert (DOUBLE.INSTANCE.toEntry(data).getValue().equals("1.23"));
        data.setValue("1.24");
        assert (data.verify(false) == false);
        assert (data.verify(true) == false);
        
        config.set("fix", 2);
        data = field.newData("1.234");
        assert (DOUBLE.INSTANCE.toEntry(data).getValue().equals("1.23"));
        data.setValue("1.235");
        assert (DOUBLE.INSTANCE.toEntry(data).getValue().equals("1.23"));
        
        System.out.println("DOUBLE tests passed");
    }
}
