package me.ihint.datahelper.demo.pojo;

import java.util.Map;

public class RecordInput {
    private Map<String, String> data;
    private String table;

    public RecordInput() {
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "[" + table + "]" + data.toString();
    }
}
