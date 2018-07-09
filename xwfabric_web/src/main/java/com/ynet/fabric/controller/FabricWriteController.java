package com.ynet.fabric.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynet.fabric.service.impl.FabricWriteHandler;
import com.ynet.xwfabric.util.ResponseResult;

/**
 * chainCode性能测试
 * @author chengcaiyi
 */
@Controller
@RequestMapping("/fabric")
public class FabricWriteController {
	
	@Resource
    private FabricWriteHandler fabricWriteHandler;
	

    @RequestMapping("writeData")
    @ResponseBody
    public ResponseResult writeData(HttpServletRequest request){

    	//批量文件目录地址
        String filePath = request.getParameter("filePath");
        //文件日期
        String dataday = request.getParameter("dataday");

        ResponseResult result = fabricWriteHandler.writeData(filePath,dataday);
        
        return result;
    }
    
    
    
   
}
