package me.ihint.datahelper.test.java;

import me.ihint.datahelper.test.java.impl.core.datatype.mysql.*;
import me.ihint.datahelper.test.java.impl.core.group.mysql.GroupTest;

public class TestAll {
    public static void main(String[] args) {
        testDatatypeMysql();
        testGroupMysql();
    }

    private static void testDatatypeMysql() {
        IntegerTest.test();
        DoubleTest.test();
        BooleanTest.test();
        VarcharTest.test();
        TextTest.test();
        TimestampTest.test();
    }

    private static void testGroupMysql() {
        GroupTest.test();
    }
}
