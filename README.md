# JMeter-jmx-BeanShellCode

###  利用自定义变量动态设置请求体
###   请求头的值合理利用自定义变量来取值
###   自定义变量的值脚本设置（从文件中读取设置）
###   利用 String fileName = ${configFile}+""; 来获取自定义变量configFile的值
###   利用 vars.put("accountId",accountId);来设置自定义变量的accountId值
###   利用以下代码模式修改请求json数据内容：
     Arguments arguments =  sampler.getArguments();
     Argument arg = arguments.getArgument(0);
     arg.setValue("postData");
     


###  利用以下代码模式修改rest响应内容：
     String response_data = prev.getResponseDataAsString();
     prev.setResponseData("reqDencryptJSON".getBytes("UTF-8"));

### 利用日志打印信息

    log.info("Script execution failed================PostProcessor=========================", exception);
    
    log.info("Script execution failed================PostProcessor=========================");
    
### jmeter、beanshell 官网url

[https://github.com/apache/jmeter](https://github.com/apache/jmeter)
[https://github.com/beanshell/beanshell](https://github.com/beanshell/beanshell)

### 利用mvn dependency:copy-dependencies -DoutputDirectory=D:/lib  -DincludeScope=compile 把自定义lib的依赖包copy出来



https://github.com/apache/jmeter
