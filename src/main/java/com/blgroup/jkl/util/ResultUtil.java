package com.blgroup.jkl.util;

import java.util.HashMap;
import java.util.Map;

import com.blgroup.jkl.util.utilBean.ComErrorCodeConstants;

import net.sf.json.JSONObject;

/**
 * 格式化响应信息的工具类
 * @author yuanmj
 *
 */
public class ResultUtil {

    public static Map<String, Object> creObjSucResult(Object obj, boolean successBoo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (successBoo) {
        	resultMap.put("resCode", ComErrorCodeConstants.ErrorCode.SYSTEM_STATUS_0.getErrorCode());
        	resultMap.put("msg", ComErrorCodeConstants.ErrorCode.SYSTEM_STATUS_0.getMemo());
		} else {
			JSONObject jsonObj = JSONObject.fromObject(obj);
			Object errorCode = jsonObj.get("errcode");
			Map<Object, Object> map = new ComErrorCodeConstants().getMap();
			for (Map.Entry<Object, Object> errorCodeAll: map.entrySet()) {
				if (errorCodeAll.getKey().equals(errorCode.toString())) {
					resultMap.put("resCode", errorCode);
			        resultMap.put("msg", errorCodeAll.getValue());
				}
			}
		}
        resultMap.put("obj", obj);
        return resultMap;
    }

}
