package com.xiami.controller;

import com.xiami.annotation.OperatorLog;
import com.xiami.base.ResponseResult;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：api电泳
 *
 * @version v1.0.0
 * @author：zj
 * @date：2020­12­27 12:31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/monitor/apiInfo")
public class ApiInfoController {
    private final JdbcTemplate jdbcTemplate;

    @GetMapping()
    @OperatorLog("API监控数据")
    public ResponseResult getInfo() throws Exception {
        Map<String, Object> statMap = new HashMap<>();
        String sql = "select oper_url name, sum(1) value from sys_oper_log group by oper_url order by value";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        statMap.put("pieSeries", list);
        List<String> nameList = new ArrayList<>();
        List<Integer> valueList = new ArrayList<>();
        list.stream().forEach(map -> {
            nameList.add(map.get("name").toString());
            valueList.add(Integer.parseInt(map.get("value").toString()));
        });
        statMap.put("pieLegend", nameList);
        statMap.put("barSeries", valueList);
        statMap.put("barYAxis", nameList);
        return ResponseResult.ok(statMap,"获取API监控数据成功");
    }

}
