package com.mobvoi.ticwatch.framework.cache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuaUtils {

  private static final Logger logger = LoggerFactory.getLogger(LuaUtils.class);

  /**
   * 加载lua脚本
   */
  public static String load(String path) {
    StringBuffer lua = new StringBuffer();
    BufferedReader reader = null;
    try {
      InputStream input = LuaUtils.class.getResourceAsStream(path);
      if (input == null) {
        input = LuaUtils.class.getClassLoader().getResourceAsStream(path);
      }
      String read;
      reader = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
      int linenum = 0;
      while ((read = reader.readLine()) != null) {
        linenum++;
        if (read.trim().startsWith("--") && !read.trim().startsWith("--[[") && !read.trim()
            .startsWith("--]]")) {
          //忽略单行注释 节省网络io
          continue;
        }
        if (read == null || read.length() == 0) {
          //忽略空行
          continue;
        }
//                if(read.trim().startsWith("redis.log(")){
//                    /*if(!logger.isDebugEnabled()){//非调试模式下不需要redis.log
//                        continue;
//                    }
//                    //追加行号
//                    lua.append("redis.log(redis.LOG_WARNING,'"+path+linenum+":')").append("\n");*/
//                    continue;
//                }
        lua.append(read).append("\n");
      }
    } catch (Throwable t) {
      logger.error("lua脚本读取失败:" + path, t);
      throw new RuntimeException("lua脚本读取失败" + path);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
//        if(logger.isDebugEnabled()){
//            logger.debug("path={},lua=\n=============================LUA SCRIPT START========================\n" +
//                    "{}=============================LUA SCRIPT END========================",path,lua.toString());
//        }
    return lua.toString();
  }

}
