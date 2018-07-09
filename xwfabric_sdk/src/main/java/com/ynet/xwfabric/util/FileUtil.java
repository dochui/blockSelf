package com.ynet.xwfabric.util;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 * @author qiangjiyi
 * @date 2018年2月23日 下午3:41:24
 */
public class FileUtil {
	
	/**
	 * 读取文件并返回每行内容的集合
	 */
	public static List<String> readFile(String path) throws IOException {
		List<String> result = null;
		
		FileReader fr = null;
		BufferedReader reader = null;
		try {
			FileInputStream in = new FileInputStream(path);
			reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			
			String str = null;
			while ((str = reader.readLine()) != null) {
				if(result == null) {
					result = new ArrayList<String>();
				}
				result.add(str);
//				System.out.println(str);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (fr != null) {
				fr.close();
			}
		}
		return result;
	}
}
