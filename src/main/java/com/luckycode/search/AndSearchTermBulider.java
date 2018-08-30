package com.luckycode.search;


import javax.mail.search.*;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
* @author ：huangxueyuan
* @Desc ：与关系查询构造类
* @Date ：2018/08/29
*
*/
public class AndSearchTermBulider extends SearchTermBulider {


    private ConcurrentHashMap<String, Object> currentTermMap = new ConcurrentHashMap<>();



    /**
     * 添加对应搜索项
     * 由于同一个and进行多次赋值没意义，所以添加map来进行过滤，保留第一次添加的搜索项
     *
     * @param key        搜索项唯一标识
     * @param searchTerm 搜索项
     */
    protected void add(String key, SearchTerm searchTerm) {
        if (currentTermMap.get(key) == null) {
            this.searchTerms.add(searchTerm);
        }
    }


    @Override
    public SearchTerm build() {
        SearchTerm[] searchTermsArr=new SearchTerm[this.searchTerms.size()];
        return new AndTerm(this.searchTerms.toArray(searchTermsArr));
    }
}
