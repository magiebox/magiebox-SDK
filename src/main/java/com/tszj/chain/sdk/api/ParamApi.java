package com.tszj.chain.sdk.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tszj.chain.sdk.Constant;
import com.tszj.chain.sdk.utils.ConvertUtil;
import com.tszj.chain.gmc4j.util.HttpRemote;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


@Component
public class ParamApi {


    /**
     * 通过table，code，scope查询区块链数据
     * @param code
     * @param scope
     * @param table
     * @param url
     * @return
     */
    public synchronized static JSONObject getTableRecords(String code, String scope, String table,  String url, Map<String, Object> paramMap) {

        JSONObject param = new JSONObject();
        param.put("json", true);

        if (!StringUtils.isEmpty(code))
            param.put("code", code);
        if (!StringUtils.isEmpty(scope))
            param.put("scope", scope);
        if (!StringUtils.isEmpty(table))
            param.put("table", table);


        if (!(paramMap == null))
        paramMap.forEach((k,v) -> param.put(k, v));

        JSONObject result = HttpRemote.getJson(url + "/v1/chain/get_table_rows", param);
        param.clear();
        return result;
    }


    public static Map<String, Object> getParamMap(Object lowerBound) {
        System.out.println(Thread.currentThread().getName() + ":" + lowerBound);
        Map<String, Object> map = new HashMap<>();
        map.put("limit", 1);
        map.put("key_type", "i64");
        map.put("index_position", "2");
        map.put("lower_bound",lowerBound);
        return map;
    }

    /**
     * 区块链校验数据是否合法
     * @param transactionId
     * @param code
     * @param scope
     * @param table
     * @param url
     * @return
     */
    public static String check(String transactionId, String code, String scope, String table, String url, String player) {

        long aLong = ConvertUtil.HexString2Long(transactionId);
        Map<String, Object> paramMap = getParamMap(aLong);
        JSONObject notetaker = ParamApi.getTableRecords(code, scope, table, url, paramMap);
        JSONArray rows = notetaker.getJSONArray("rows");

        if (rows.size() != 1)
            return null;
        JSONObject parseResult = JSONObject.parseObject(rows.get(0).toString());

        boolean isTrx = parseResult.getString("trxid").equals(transactionId);
        if (!isTrx)
            return null;
        boolean isPlayer = parseResult.getString("player").equals(player);
        if (!isPlayer)
            return null;
        boolean isGameOwner = Constant.ADMIN.equals(parseResult.getString("gameowner"));
        if (!isGameOwner)
            return null;
        return parseResult.getString("message");
    }
}
