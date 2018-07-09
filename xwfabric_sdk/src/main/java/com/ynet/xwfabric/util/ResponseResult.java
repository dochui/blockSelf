package com.ynet.xwfabric.util;



import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 自定义响应结构
 * @author qiangjiyi
 * @date 2018年2月27日 下午4:10:14
 */
public class ResponseResult {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private String returnCode;

    // 响应消息
    private String returnMsg;

    // 响应中的数据
    private Object data;

    public static ResponseResult build(String returnCode, String returnMsg, Object data) {
        return new ResponseResult(returnCode, returnMsg, data);
    }

    public static ResponseResult ok(Object data) {
        return new ResponseResult(data);
    }

    public static ResponseResult ok() {
        return new ResponseResult(null);
    }

    public ResponseResult() {

    }

    public static ResponseResult build(String returnCode, String returnMsg) {
        return new ResponseResult(returnCode, returnMsg, null);
    }

    public ResponseResult(String returnCode, String returnMsg, Object data) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.data = data;
    }

    public ResponseResult(Object data) {
        this.returnCode = "000000";
        this.returnMsg = "OK";
        this.data = data;
    }

//    public Boolean isOK() {
//        return this.returnCode == "000000";
//    }
    
    public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public Object getData() {
        return data;
    }

	public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为ResponseResult对象
     * @param jsonData json数据
     * @param clazz ResponseResult中的object类型
     * @return
     */
    public static ResponseResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, ResponseResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("returnCode").asText(), jsonNode.get("returnMsg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     * @param json
     * @return
     */
    public static ResponseResult format(String json) {
        try {
            return MAPPER.readValue(json, ResponseResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static ResponseResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("returnCode").asText(), jsonNode.get("returnMsg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

}
