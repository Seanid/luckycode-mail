package com.luckycode.search;

import javax.mail.search.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sean on 2018/08/23.
 * 邮件查找过滤器
 */
public abstract class SearchTermBulider {

    protected List<SearchTerm> searchTerms=new ArrayList<>();


    /**
     * 添加搜索项
     *
     * @param type  搜索类型
     * @param value 搜索值
     * @return
     */
    public SearchTermBulider add(SearchType type, String value) {
        switch (type) {
            case BODY:
                add(type.getValue(), new BodyTerm(value));
                break;
            case FROM:
                add(type.getValue(), new FromStringTerm(value));
                break;
            case SUBJECT:
                add(type.getValue(), new SubjectTerm(value));
                break;
            default:
                break;
        }
        return this;
    }


    /**
     * 添加日期筛选项
     * @param type          搜索类型
     * @param compareType   对比类型
     * @param date          日期
     * @return
     */
    public SearchTermBulider add(SearchType type, CompareType compareType, Date date) {
        switch (type) {
            case SENDDATE:
                switch (compareType) {
                    case EQ:
                        add(type.getValue(), new SentDateTerm(ComparisonTerm.EQ, date));
                        break;
                    case GT:
                        add(type.getValue(), new SentDateTerm(ComparisonTerm.GT, date));
                        break;
                    case GE:
                        add(type.getValue(), new SentDateTerm(ComparisonTerm.GE, date));
                        break;
                    case LE:
                        add(type.getValue(), new SentDateTerm(ComparisonTerm.LE, date));
                        break;
                    case LT:
                        add(type.getValue(), new SentDateTerm(ComparisonTerm.LT, date));
                        break;
                    case NE:
                        add(type.getValue(), new SentDateTerm(ComparisonTerm.NE, date));
                        break;
                    default:
                        break;
                }
            case RECEIVEDDATE:
                switch (compareType) {
                    case EQ:
                        add(type.getValue(), new ReceivedDateTerm(ComparisonTerm.EQ, date));
                        break;
                    case GT:
                        add(type.getValue(), new ReceivedDateTerm(ComparisonTerm.GT, date));
                        break;
                    case GE:
                        add(type.getValue(), new ReceivedDateTerm(ComparisonTerm.GE, date));
                        break;
                    case LE:
                        add(type.getValue(), new ReceivedDateTerm(ComparisonTerm.LE, date));
                        break;
                    case LT:
                        add(type.getValue(), new ReceivedDateTerm(ComparisonTerm.LT, date));
                        break;
                    case NE:
                        add(type.getValue(), new ReceivedDateTerm(ComparisonTerm.NE, date));
                        break;
                    default:
                        break;
                }
            default:
                break;
        }
        return this;
    }

    /**
     * 添加邮件大小查询
     *
     * @param compareType 对比类型
     * @param i           大小，以B为单位，kb=1024
     * @return
     */
    public SearchTermBulider add(CompareType compareType, int i) {

        SearchType type = SearchType.SIZE;

        switch (compareType) {
            case EQ:
                add(type.getValue(), new SizeTerm(IntegerComparisonTerm.EQ, i));
                break;
            case GT:
                add(type.getValue(), new SizeTerm(IntegerComparisonTerm.GT, i));
                break;
            case GE:
                add(type.getValue(), new SizeTerm(IntegerComparisonTerm.GE, i));
                break;
            case LE:
                add(type.getValue(), new SizeTerm(IntegerComparisonTerm.LE, i));
                break;
            case LT:
                add(type.getValue(), new SizeTerm(IntegerComparisonTerm.LT, i));
                break;
            case NE:
                add(type.getValue(), new SizeTerm(IntegerComparisonTerm.NE, i));
                break;
            default:
                break;
        }

        return this;
    }



    public abstract SearchTerm build();

    protected abstract  void add(String key, SearchTerm searchTerm);

}
