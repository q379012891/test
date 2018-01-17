package com.blgroup.jkl.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 集合工具类
 * 
 * @version 1.0
 * 
 * @createDate 2012-7-31
 * 
 * @modifyDate 2012-8-14
 */
@SuppressWarnings("rawtypes")
public class CollectionUtil {

    /**
     * 判断Collection是否不为空
     * 
     * @param collection
     *            需要判断的Collection
     * @return boolean
     */

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断Collection是否为空
     * 
     * @param collection
     *            需要判断的Collection
     * @return boolean
     */
    public static boolean isEmpty(Collection collection) {
        if (collection == null || collection.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断Map是否不为空
     * 
     * @param map
     *            需要判断的Collection
     * @return boolean
     */
    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    /**
     * 判断Map是否为空
     * 
     * @param map
     *            需要判断的Collection
     * @return boolean
     */
    public static boolean isEmpty(Map map) {
        if (map == null || map.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断对象数组是否为空
     * 
     * @param objs
     *            需要判断的对象数组
     * @return boolean
     */
    public static boolean isNotEmpty(Object[] objs) {
        if (objs == null || objs.length == 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断对象数组是否为空
     * 
     * @param objs
     *            需要判断的对象数组
     * @return boolean
     */
    public static boolean isEmpty(Object[] objs) {
        if (objs == null || objs.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * 删除集合中的空对象
     * 
     * @param c
     *            集合对象
     */
    @SuppressWarnings("unchecked")
    public static void removeNull(Collection c) {
        if (c != null) {
            List listnull = new ArrayList();
            listnull.add(null);
            c.removeAll(listnull);
        }
    }

    /**
     * 判断List中的对象数据是否与传入的对象一致
     * 
     * @param listValue
     *            需要判断的List
     * @param sample
     *            样本
     * @return boolean
     * @throws Exception 异常
     */
    public static boolean sameValueInList(List listValue, Object sample) throws Exception {
        boolean mark = true;
        for (Object oo : listValue) {
            if (!oo.equals(sample)) {
                mark = false;
                break;
            }
        }
        return mark;
    }

    /**
     * 如果List中不存在这个元素，则增加，反之不增加
     * 
     * @param list  数据源
     * @param obj   检查对象
     * @return 增加了返回true，否则返回false
     */
    @SuppressWarnings("unchecked")
    public static boolean addUniqueObjList(List list, Object obj) {
        if (list.contains(obj) || obj == null) {
            return false;
        } else {
            list.add(obj);
            return true;
        }
    }

}
