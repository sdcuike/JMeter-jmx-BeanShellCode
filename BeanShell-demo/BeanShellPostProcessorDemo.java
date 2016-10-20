import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import com.xx.x.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import com.doctor.commons.*;

    
 
try {

	  //先取自定义变量，等用到的时候，再取说找不到定义	
	   String secretKey =  ${secretKey}+"";
	   String deviceId = ${deviceId}+"";
	  //服务器返回的公共配置属性保存
	   String fileName = ${configFile}+"";
	   log.info("====*fileName*******************************************************************"+ fileName);
	    

	 //对返回的json数据处理
	  String response_data = prev.getResponseDataAsString();
       JSONObject parseObject = JSON.parseObject(response_data);
       log.info("====********************************************************************"+ parseObject);
       String data = parseObject.getString("data");

       //这是加密的数据处理 
       if (data != null) {

        	log.info("====PostProcessor********************************************************************"+ data);
            	//解密
	       	String reqDencryptJSON = AESUtis.appAESDencrypt(data, deviceId,secretKey );

 		  	log.info("===PostProcessor**********************************responseBody*****************************"+ reqDencryptJSON);
 		 
            //解密的数据写回返回body中
            prev.setResponseData(reqDencryptJSON.getBytes("UTF-8"));


              //取用户属性保存，以便以后用到
			JSONObject  resultJson= JSON.parseObject(reqDencryptJSON);
       	 	JSONObject result = resultJson.getJSONObject("result");
         		if (result != null) {
         			log.info("===PostProcessor*********************************result *******===************" );
            		String accessKey = result.getString("accessKey");
            
            		if (accessKey != null) {
                  		String accountId = result.getString("accountId");
                  		String token = result.getString("token");

                   //配置.设置头信息和公共信息
	 				
 
	 				String sign =  MD5Utils.md5To32LowerCaseHexString( secretKey + accountId+ deviceId + secretKey);

					//文件读取放到这里， 文件内容会被重写，而不是追加模式，所以不能实际保存内容的地方申明FileOutputStream
				     Properties properties = new Properties();
 	  
 	                FileOutputStream outputStream = new FileOutputStream(fileName);
 	   
	 				properties.setProperty("accountId",accountId);
	 				properties.setProperty("accessKey",accessKey);
 	 				properties.setProperty("sign",sign);
 	 				properties.setProperty("token",token);
 	 				properties.store(outputStream, "");//
 	 				log.info("===PostProcessor**********************************properties.store*******===************" );

 	 				 //资源释放
    
        
    				  if (outputStream != null) {
                            outputStream.close();
                    }
 	 				
 	 		    }
 	 		}
        }
	

   } catch (Exception ex) {
		
    log.info("Script execution failed================PostProcessor=========================", ex);
}

 

