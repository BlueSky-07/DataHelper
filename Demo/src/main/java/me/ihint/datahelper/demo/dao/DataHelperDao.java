package me.ihint.datahelper.demo.dao;

import me.ihint.datahelper.demo.util.SQLProvider;
import me.ihint.datahelper.impl.core.group.mysql.Record;
import me.ihint.datahelper.impl.helper.SQLHelper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface DataHelperDao {
    @InsertProvider(type = SQLProvider.class, method = "insert")
    void insert(SQLHelper helper, Record record);

    @SelectProvider(type = SQLProvider.class, method = "insertThenGetId")
    Integer insertThenGetID(SQLHelper helper, Record record);

    @UpdateProvider(type = SQLProvider.class, method = "updateById")
    void updateById(SQLHelper helper, Record record);

    @UpdateProvider(type = SQLProvider.class, method = "updateByCondition")
    void updateByCondition(SQLHelper helper, Record target, Record condition);

    @SelectProvider(type = SQLProvider.class, method = "selectById")
    Map<String, String> selectById(SQLHelper helper, Record condition);

    @SelectProvider(type = SQLProvider.class, method = "selectByCondition")
    List<Map<String, String>> selectByCondition(SQLHelper helper, Record condition);

    @SelectProvider(type = SQLProvider.class, method = "selectByConditionLimit")
    List<Map<String, String>> selectByConditionLimit(SQLHelper helper, Record condition, long offset, long size);

    @DeleteProvider(type = SQLProvider.class, method = "deleteById")
    void deleteById(SQLHelper helper, Record condition);

    @DeleteProvider(type = SQLProvider.class, method = "deleteByCondition")
    void deleteByCondition(SQLHelper helper, Record condition);
}
