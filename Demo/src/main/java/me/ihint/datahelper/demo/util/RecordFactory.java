package me.ihint.datahelper.demo.util;

import me.ihint.datahelper.demo.pojo.RecordInput;
import me.ihint.datahelper.exception.DataHelperException;
import me.ihint.datahelper.impl.core.group.mysql.Record;
import me.ihint.datahelper.impl.core.group.mysql.Struct;
import me.ihint.datahelper.impl.helper.SQLHelper;

import java.util.Map;

public class RecordFactory {
    public static Record create(SQLHelper helper, RecordInput input) throws DataHelperException {
        String tableName = input.getTable();
        Struct struct = helper.get(tableName);
        if (struct == null) {
            throw new DataHelperException("cannot find a table called " + tableName);
        }
        Record record = struct.newRecord();
        Map<String, String> dataInput = input.getData();
        dataInput.forEach(record::set);
        return record;
    }
}
