package me.ihint.datahelper.test.java.impl.core.group.mysql;

import me.ihint.datahelper.core.Data;
import me.ihint.datahelper.core.Field;
import me.ihint.datahelper.exception.bundle.FieldNotFoundException;
import me.ihint.datahelper.impl.core.bundle.SimpleBundle;
import me.ihint.datahelper.impl.core.config.SimpleConfig;
import me.ihint.datahelper.impl.core.datatype.mysql.INTEGER;
import me.ihint.datahelper.impl.core.datatype.mysql.VARCHAR;
import me.ihint.datahelper.impl.core.group.mysql.Record;
import me.ihint.datahelper.impl.core.group.mysql.Struct;

public class GroupTest {
    public static void test() {
        SimpleBundle<Field> fields = new SimpleBundle<>();
        fields.set("id", new Field("id", INTEGER.INSTANCE, new SimpleConfig()));

        SimpleConfig configOfUsername = new SimpleConfig();
        configOfUsername.set("length", 15);
        fields.set("username", new Field("username", VARCHAR.INSTANCE, configOfUsername));

        SimpleConfig configOfAge = new SimpleConfig();
        configOfAge.set("min", 0);
        fields.set("age", new Field("age", INTEGER.INSTANCE, configOfAge));

        SimpleConfig config = new SimpleConfig();
        config.set("writable", true);

        Struct struct = new Struct(fields, config);
        struct.setTableName("test_table_name");

        Record record = struct.newRecord();

        assert (record.getTableName().equals("test_table_name"));
        assert ((boolean) record.getConfig().get("writable") == true);

        Data id = record.getBundle().get("id");
        id.setValue("0");
        assert (record.get("id").equals("0"));

        record.set("username", "BlueSky");
        assert (record.get("username").equals("BlueSky"));

        record.getBundle().get("age").setValue("20");
        assert (record.get("age").equals("20"));

        try {
            record.get("null");
            throw new Exception();
        } catch (Exception e) {
            assert (e instanceof FieldNotFoundException);
        }

        try {
            record.set("null", "null");
            throw new Exception();
        } catch (Exception e) {
            assert (e instanceof FieldNotFoundException);
        }

        assert (record.count(false) == 3);
        assert (record.count(true) == 3);

        try {
            record.clear("null");
        } catch (Exception e) {
            assert (e instanceof FieldNotFoundException);
        }

        record.clear("username");
        assert (record.count(false) == 2);
        assert (record.count(true) == 3);

        record.clear();
        assert (record.count(false) == 0);
        assert (record.count(true) == 3);

        System.out.println("Group tests passed");
    }
}
