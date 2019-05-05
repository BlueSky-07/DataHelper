package me.ihint.datahelper.demo.controller;

import me.ihint.datahelper.annotation.DateTime;
import me.ihint.datahelper.annotation.Lang;
import me.ihint.datahelper.annotation.File;
import me.ihint.datahelper.demo.pojo.RecordInput;
import me.ihint.datahelper.demo.pojo.RecordsInput;
import me.ihint.datahelper.demo.service.DataHelperService;
import me.ihint.datahelper.exception.DataHelperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.*;

import me.ihint.datahelper.impl.helper.SQLHelper;

@RestController
public class DataHelperController {
    private static Logger logger = LoggerFactory.getLogger(DataHelperController.class);
    private final DataHelperService helperService;

    @Lang
    @DateTime
    @File(path = "classpath:students&teachers.txt")
    private static SQLHelper helper = new SQLHelper();

    @Autowired
    public DataHelperController(DataHelperService helperService) {
        this.helperService = helperService;
        helper.init(this, "helper");
        logger.info("DataHelper for \"" + helper.getInputFilePath() + "\" has been initialized");
        logger.info(Objects.requireNonNull(helper.getStructList()).toString());
    }

    @PostMapping("/insert")
    public Map<String, Serializable> insertThenGetId(@RequestBody RecordInput input) {
        logger.debug("[POST][/insert] << " + input.toString());
        Map<String, Serializable> result = new HashMap<>();
        result.put("result", "failure");
        try {
            int id = helperService.insertThenGetId(input, helper);
            result.put("result", "success");
            result.put("id", id);
        } catch (DataHelperException e) {
            result.put("error", e.getClass().getSimpleName());
            result.put("msg", e.getMessage());
        }
        logger.debug("[POST][/insert] >> " + result.toString());
        return result;
    }

    @PostMapping("/update/id")
    public Map<String, Serializable> updateById(@RequestBody RecordInput input) {
        logger.debug("[POST][/update/id] << " + input.toString());
        Map<String, Serializable> result = new HashMap<>();
        result.put("result", "failure");
        try {
            helperService.updateById(input, helper);
            result.put("result", "success");
        } catch (DataHelperException e) {
            result.put("error", e.getClass().getSimpleName());
            result.put("msg", e.getMessage());
        }
        logger.debug("[POST][/update/id] >> " + result.toString());
        return result;
    }

    @PostMapping("/update")
    public Map<String, Serializable> updateByCondition(@RequestBody RecordsInput input) {
        logger.debug("[POST][/update] << " + input.toString());
        Map<String, Serializable> result = new HashMap<>();
        result.put("result", "failure");
        try {
            helperService.updateByCondition(input.getTarget(), input.getCondition(), helper);
            result.put("result", "success");
        } catch (DataHelperException e) {
            result.put("error", e.getClass().getSimpleName());
            result.put("msg", e.getMessage());
        }
        logger.debug("[POST][/update] >> " + result.toString());
        return result;
    }

    @PostMapping("/select/id")
    public Map<String, Serializable> selectById(@RequestBody RecordInput input) {
        logger.debug("[POST][/select/id] << " + input.toString());
        Map<String, Serializable> result = new HashMap<>();
        result.put("result", "failure");
        try {
            Map<String, String> res = new HashMap<>(helperService.selectById(input, helper));
            result.put("result", "success");
            result.put("data", (HashMap) res);
        } catch (DataHelperException e) {
            result.put("error", e.getClass().getSimpleName());
            result.put("msg", e.getMessage());
        } catch (NullPointerException e) {
            result.put("msg", "not found");
        }
        logger.debug("[POST][/select/id] >> " + result.toString());
        return result;
    }

    @PostMapping("/select")
    public Map<String, Serializable> selectByCondition(@RequestBody RecordInput input) {
        logger.debug("[POST][/select] << " + input.toString());
        Map<String, Serializable> result = new HashMap<>();
        result.put("result", "failure");
        try {
            String offsetInput = input.getData().get("#offset");
            String sizeInput = input.getData().get("#size");
            long size = 20L;
            long offset = 0L;
            if (sizeInput != null) {
                size = Long.parseLong(sizeInput);
                input.getData().remove("#size");
            }
            if (offsetInput != null) {
                offset = Long.parseLong(offsetInput);
                input.getData().remove("#offset");
                offset = (offset - 1) * size;
            }
            List<Map<String, String>> res = new ArrayList<>(helperService.selectByConditionLimit(input, offset, size, helper));
            if (res.size() == 0) {
                result.put("msg", "not found");
            } else {
                result.put("result", "success");
                result.put("data", (ArrayList) res);
                result.put("total", res.size());
            }
        } catch (DataHelperException e) {
            result.put("error", e.getClass().getSimpleName());
            result.put("msg", e.getMessage());
        } catch (NumberFormatException e) {
            result.put("msg", "#offset 或 #size 必须为正整数");
        }
        logger.debug("[POST][/select] >> " + result.toString());
        return result;
    }

    @PostMapping("/delete/id")
    public Map<String, Serializable> deleteById(@RequestBody RecordInput input) {
        logger.debug("[POST][/delete/id] << " + input.toString());
        Map<String, Serializable> result = new HashMap<>();
        result.put("result", "failure");
        try {
            helperService.deleteById(input, helper);
            result.put("result", "success");
        } catch (DataHelperException e) {
            result.put("error", e.getClass().getSimpleName());
            result.put("msg", e.getMessage());
        }
        logger.debug("[POST][/delete/id] >> " + result.toString());
        return result;
    }

    @PostMapping("/delete")
    public Map<String, Serializable> deleteByCondition(@RequestBody RecordInput input) {
        logger.debug("[POST][/delete] << " + input.toString());
        Map<String, Serializable> result = new HashMap<>();
        result.put("result", "failure");
        try {
            helperService.deleteByCondition(input, helper);
            result.put("result", "success");
        } catch (DataHelperException e) {
            result.put("error", e.getClass().getSimpleName());
            result.put("msg", e.getMessage());
        }
        logger.debug("[POST][/delete] >> " + result.toString());
        return result;
    }
}
