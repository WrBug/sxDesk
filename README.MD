# 闪讯wifi助手桌面版

## 闪讯wifi助手Android版：
[https://github.com/WrBug/wtshanxun](https://github.com/WrBug/wtshanxun)
## 介绍
闪讯wifi助手桌面版是一款破解浙江校园闪讯网络的免费拨号器，使用该软件可以让大多数老版的TP-LINK、水星等路由器路由器连上网络，软件自带心跳，解决某些学校的心跳验证。
## 更新地址：
[http://www.mandroid.cn](http://www.mandroid.cn)
## 开发环境：
开发工具: intellij idea
java环境：JDK/JRE 7+
## 软件截图
![](https://github.com/WrBug/sxDesk/blob/master/20160329235451.png)![](https://github.com/WrBug/sxDesk/blob/master/20160329235439.png)![](https://github.com/WrBug/sxDesk/blob/master/20160418101622.png)![](https://github.com/WrBug/sxDesk/blob/master/20160418100929.png)

## 心跳接口：
### request
Url: [http://sx.mandroid.cn/index.php/heart/getByRouter](http://sx.mandroid.cn/index.php/heart/getByRouter)

Method: POST/GET

Query:
  user=[闪讯账号]
  
  ip=[IP地址]
  
示例：[http://sx.mandroid.cn/index.php/heart/getByRouter?user=123456@ABC.XY&ip=192.168.1.1](http://sx.mandroid.cn/index.php/heart/getByRouter?user=123456@ABC.XY&ip=192.168.1.1)
## response:
    {
    	"status": 200,
    	"data": {
    		"packData": "U04AYwOCQpY85mPaVnecguWKlDde4wIAB8CoAQEDAAwxLjIuMTguMjgUACNiNTcyYjI1NjEwODA4ZGE1N2QyY2Y0YWEyMGViZmE2ORIAB1br6zcBABAxMjM0NTZAQUJDLlhZ",  //base64encode
    		"sendIp": "115.239.134.167",
    		"sendPort": 8080
    	}
    }

