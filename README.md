# magiebox-SDK 

Magie box-SDK is a system development tool for third-party projects to access Magie Box platform (http://box.191381.com). The tool aims to reduce the development threshold of block chain projects and make it easy for developers of centralized projects to develop block chain projects.

magiebox-SDK 是第三方项目接入 Magie Box 平台的系统开发工具，该工具旨在降低区块链项目的开发门槛，使中心化项目的开发者也能轻松地开发区块链项目。

This SDK provides a secure login mechanism, based on EOS digital signature and Shiro security framework technology, to provide security for system projects; at the same time, it provides safe and convenient token universal operation functions such as recharge, transfer, withdrawal of money, so that developers can focus on the business logic of their own projects.

本 SDK 提供安全的登录机制，基于 EOS 数字签名及 Shiro 安全框架技术，为系统项目提供安全保障；同时，为项目提供安全便利的充值、转账、提币等 token 通用操作功能，使开发者能够把精力聚焦在自己项目的业务逻辑上。


## Getting Started / 快速上手：

We provide a generic project template that you can use directly. Please check the src>main>resources>static>magieBox-frontend-sdk file in the root directory of the project. If you do not wish to use the templates we provide, please read on. Let's take the vue framework as an example.

我们提供了通用的项目模板，你可以直接使用。请查看项目根目录下src>main>resources>static>magieBox-frontend-sdk文件 如果不希望使用我们提供的模板，请继续阅读。我们以vue框架为例。

### Use vue-cli / 使用 vue-cli

We can also use vue-cli to initialize the project, the command is as follows (note that axios must be installed):

我们还可以使用 vue-cli 初始化项目，命令如下(注意必须安装axios)：

```js
> npm i -g vue-cli
> mkdir my-project && cd my-project
> vue init webpack
```

### Install blockchain plugin / 安装区块链插件及加密算法

We need to install the following blockchain plugin and encryption algorithm:Install EOSJS, install scatterjs-core, install scatterjs-plugin-eosjs, install sha256

我们需要安装以下的区块链插件及加密算法：安装EOSJS、安装scatterjs-core、安装scatterjs-plugin-eosjs、安装sha256

```js
> npm i eosjs
> npm install scatterjs-core --save 
> npm install scatterjs-plugin-eosjs --save
> npm install js-sha256
```

## start using / 开始使用

* JS file loading(We can only introduce it within the required components to achieve the purpose of reducing the volume of the project) / js 文件加载（我们可以只在需要的组件内引入，以达到减小项目体积的目的）
```js
import "../../../static/js/magieBox-frontend";
```
* login (projectname: project name, IP: background request IP address)/ 登录功能应用（projectname：项目名称，IP：后台请求IP地址）
```**js**
new Scatterr(projectname,IP);
```
* login (name: the name of the user who logged in)/ 用户名获取（name：登录的用户名称）
```**js**
var timer = setInterval(() => {
    new Login().vurrentlogin;
    if (new Login().vurrentlogin) {
    name = new ScatterName().vurrentname;
    clearInterval(timer);
    }
}, 1000);
```
* recharge (Rechargeamount: the amount of recharge, pure number)/ 充值功能应用（Rechargeamount：充值的额度，纯数字）
```js
 var quantitys = Number(Rechargeamount).toFixed(4) + " EOS";
 new Transfer(quantitys);
```
* Transfer (transferamount: the amount of the transfer, pure number; Transfertoaccount: transfer to the account; IP: background request IP address)/ 转账功能应用（transferamount：转账的额度，纯数字；Transfertoaccount：转入账户；IP：后台请求IP地址）
```js
new Withdraww(2,Number(transferamount),Transfertoaccount,IP);
```
* withdraw (Withdrawalamount: the amount of withdrawal, pure number; IP: background request IP address)/ 提币功能应用（Withdrawalamount：提现的额度，纯数字；IP：后台请求IP地址）
```js
new Withdraww(1,Number(Withdrawalamount),"",IP);
```

## Technical Selection / 技术选型：
SpringBoot + shiro + jwt

## Points for Attention in Accessing SDK / 接入SDK注意事项
 * This project depends on the use of com.tszj.chain series jar package, download address please see the project root directory settings.XML file  
 * 本工程依赖需用到com.tszj.chain系列jar包，下载地址请查看项目根目录下settings.xml文件 

 * This project does not provide data layer implementation, it can add data layer implementation and verification at TODO annotation according to its own business 
 * 本工程不提供数据层实现，可根据自身业务在标注TODO处添加数据层实现和校验 

 * This project uses the secret key plus the username to encrypt the token, and can modify the encryption rules according to its own needs. For details, see the JwtUtils.createToken method. 
 * 本工程使用秘钥加用户名加密token，可根据自身需求修改加密规则，详情见JwtUtils.createToken方法 

 * This project uses secret key and username to encrypt token. It can modify the encrypting rules according to its own needs. See JwtUtils. createToken method for details 
 * 权限配置详情查看AuthRealm.doGetAuthorizationInfo方法，注释部分为授权配置DEMO 

 * Login authentication mainly depends on block chain checking. For more details, see LoginController. Login method and AuthRealm. doGetAuthentication Info method, which can be adjusted according to their own business 
 * 登录认证主要依赖于区块链校验，详情查看LoginController.login方法和AuthRealm.doGetAuthenticationInfo方法，可根据自身业务进行调整