package com.luckycode.codding.test;

import com.luckycode.utils.RegUtil;
import org.junit.Assert;
import org.junit.Test;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class RegUtilTest {

    @Test
    public void vaildEmailTest(){
        Assert.assertEquals(RegUtil.vaildEmail("demo@126.com"),TRUE);
        Assert.assertEquals(RegUtil.vaildEmail("demo.com@126.com"),TRUE);
        Assert.assertEquals(RegUtil.vaildEmail("demo-com@126.com"),TRUE);
        Assert.assertEquals(RegUtil.vaildEmail("hxy9104##126.com"),FALSE);
    }


}
