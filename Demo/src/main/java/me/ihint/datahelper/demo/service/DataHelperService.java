package me.ihint.datahelper.demo.service;

import me.ihint.datahelper.demo.dao.DataHelperDao;
import me.ihint.datahelper.demo.pojo.RecordInput;
import me.ihint.datahelper.demo.util.RecordFactory;
import me.ihint.datahelper.exception.DataHelperException;
import me.ihint.datahelper.impl.core.group.mysql.Record;
import me.ihint.datahelper.impl.helper.SQLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataHelperService {
    private static Logger logger = LoggerFactory.getLogger(DataHelperService.class);
    private final DataHelperDao helperDao;

    @Autowired
    public DataHelperService(DataHelperDao helperDao) {
        this.helperDao = helperDao;
    }

    public void insert(RecordInput input, SQLHelper helper) throws DataHelperException {
        Record record = RecordFactory.create(helper, input);
        logger.debug(helper.insert(record)); // for DataHelperException thrown to catch
        helperDao.insert(helper, record);
    }

    public Integer insertThenGetId(RecordInput input, SQLHelper helper) throws DataHelperException {
        Record record = RecordFactory.create(helper, input);
        logger.debug(helper.insertThenGetId(record)); // for DataHelperException thrown to catch
        return helperDao.insertThenGetID(helper, record);
    }

    public void updateById(RecordInput input, SQLHelper helper) throws DataHelperException {
        Record record = RecordFactory.create(helper, input);
        logger.debug(helper.updateById(record)); // for DataHelperException thrown to catch
        helperDao.updateById(helper, record);
    }

    public void updateByCondition(RecordInput targetInput, RecordInput conditionInput, SQLHelper helper) throws DataHelperException {
        Record targetRecord = RecordFactory.create(helper, targetInput);
        Record conditionRecord = RecordFactory.create(helper, conditionInput);
        logger.debug(helper.updateByCondition(targetRecord, conditionRecord)); // for DataHelperException thrown to catch
        helperDao.updateByCondition(helper, targetRecord, conditionRecord);
    }

    public Map<String, String> selectById(RecordInput conditionInput, SQLHelper helper) throws DataHelperException {
        Record record = RecordFactory.create(helper, conditionInput);
        logger.debug(helper.selectById(record)); // for DataHelperException thrown to catch
        return helperDao.selectById(helper, record);
    }

    public List<Map<String, String>> selectByCondition(RecordInput input, SQLHelper helper) throws DataHelperException {
        Record record = RecordFactory.create(helper, input);
        logger.debug(helper.selectByCondition(record)); // for DataHelperException thrown to catch
        return helperDao.selectByCondition(helper, record);
    }

    public List<Map<String, String>> selectByConditionLimit(RecordInput input, long offset, long size, SQLHelper helper) throws DataHelperException {
        Record record = RecordFactory.create(helper, input);
        logger.debug(helper.selectByConditionLimit(record, offset, size)); // for DataHelperException thrown to catch
        return helperDao.selectByConditionLimit(helper, record, offset, size);
    }

    public void deleteById(RecordInput conditionInput, SQLHelper helper) throws DataHelperException {
        Record record = RecordFactory.create(helper, conditionInput);
        logger.debug(helper.deleteById(record)); // for DataHelperException thrown to catch
        helperDao.deleteById(helper, record);
    }

    public void deleteByCondition(RecordInput input, SQLHelper helper) throws DataHelperException {
        Record record = RecordFactory.create(helper, input);
        logger.debug(helper.deleteByCondition(record)); // for DataHelperException thrown to catch
        helperDao.deleteByCondition(helper, record);
    }
}
