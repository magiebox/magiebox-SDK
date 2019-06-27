package com.tszj.chain.sdk.service.impl;


import com.tszj.chain.sdk.service.LockCoinsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class LockCoinsServiceImpl implements LockCoinsService {

    @Value("${contract.eappcode}")
    String code;
    @Value("${gmc4j.url.nodeos}")
    String url;



    @Value("${contract.gameowner}")
    public String gameOwner;

    /**
     * 保存充值记录
     *
     * @param id             从区块链上获取
     * @param owner          用户
     * @param referrer       推荐人
     * @param amount         充值（激活合约）数量
     * @param t_time         时间戳，从区块链上获取
     * @param transaction_id 交易ID，从区块链上获取
     * @return
     */
    public void writeDbFromChain(int id, String owner, String referrer, BigDecimal amount, long t_time, String transaction_id) {

        //todo
    }


    /**
     * 轮询获取链上充值信息写入库中（注释代码为示例代码，仅供参考）
     */
    @Override
    public void queryChainSaveLockCoins() {

//        while (true) {
//            Integer maxId = investmentLogMapper.selectMaxId();
//            Map<String, Object> paramMap = new HashMap<>();
//            paramMap.put("reverse", false);
//            if (!(maxId == null))
//                paramMap.put("lower_bound", maxId + 1);
//            paramMap.put("limit", 10);
//
//            JSONObject jsonRecords = ParamApi.getTableRecords(code, code, "chargelog", url, paramMap);
//
//            JSONArray rows = jsonRecords.getJSONArray("rows");
//
//            Boolean hasMore = (Boolean) jsonRecords.get("more");
//            if (!hasMore && rows.isEmpty()) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    log.error("query chain thread error,error message:{}", e.getMessage());
//                }
//            }
//            rows.forEach(record -> {
//                try {
//                    JSONObject jsonRecord = JSONObject.parseObject(record.toString());
//                    String[] quantity = jsonRecord.getString("quantity").trim().split(" ");
//
//                    BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(quantity[0]));
//
//                    String[] memo = jsonRecord.getString("memo").split(",");
//                    if (!gameOwner.equals(memo[0]))
//                        return;
//                    String owner = jsonRecord.getString("from");
//                    Integer id = jsonRecord.getInteger("id");
//                    Long time = jsonRecord.getLong("time");
//                    String transactionId = jsonRecord.getString("trxid");
//                    writeDbFromChain(id, owner, memo[1], amount, time, transactionId);
//                } catch (Exception e) {
//                    log.error("write record from chain error,error message:{}", e.getMessage());
//                    e.printStackTrace();
//                }
//            });
//        }
    }
}
