package com.tszj.chain.eappserver.utils;

import com.tszj.chain.sdk.utils.ConvertUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConvertUtilTest {

    @Test
    public void hexString2Long() {
        long target = ConvertUtil.HexString2Long("DEE19B8F");
        assertEquals(3739327375l, target);

        String trxId = "dee19b8f9e82e77715207df5964192ad116054ddcdb84ae4bcff79f69b21175a";
        target = ConvertUtil.HexString2Long(trxId);
        assertEquals(3739327375l, target);
    }
}
