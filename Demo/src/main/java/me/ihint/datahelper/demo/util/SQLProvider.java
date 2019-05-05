package me.ihint.datahelper.demo.util;

import me.ihint.datahelper.impl.core.group.mysql.Record;
import me.ihint.datahelper.impl.helper.SQLHelper;

public class SQLProvider {
    public static String insert(SQLHelper helper, Record record) {
        return helper.insert(record);
    }

    public static String insertThenGetId(SQLHelper helper, Record record) {
        return helper.insertThenGetId(record);
    }

    public static String updateById(SQLHelper helper, Record record) {
        return helper.updateById(record);
    }

    public static String updateByCondition(SQLHelper helper, Record target, Record condition) {
        return helper.updateByCondition(target, condition);
    }

    public static String selectById(SQLHelper helper, Record condition) {
        return helper.selectById(condition);
    }

    public static String selectByCondition(SQLHelper helper, Record condition) {
        return helper.selectByCondition(condition);
    }

    public static String selectByIdLimit(SQLHelper helper, Record condition, Long offset, Long size) {
        return helper.selectByIdLimit(condition, offset, size);
    }

    public static String selectByConditionLimit(SQLHelper helper, Record condition, Long offset, Long size) {
        return helper.selectByConditionLimit(condition, offset, size);
    }

    public static String deleteById(SQLHelper helper, Record condition) {
        return helper.deleteById(condition);
    }

    public static String deleteByCondition(SQLHelper helper, Record condition) {
        return helper.deleteByCondition(condition);
    }
}
