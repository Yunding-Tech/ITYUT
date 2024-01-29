<h1 align="center"> ITYUT</h1>
<h4 align="center">一个用于连接TYUT并获取相关信息的开发工具</h4>

<p align="center">
    <a href="https://opensource.org/licenses/mit-license.php"> 
        <img src="https://img.shields.io/github/license/ruafafa/ITYUT" alt="License">
    </a>
    <a href="https://www.oracle.com/java/technologies/javase/javase-jdk17-downloads.html">
        <img src="https://img.shields.io/badge/JDK-17+-green" alt="jdk">
    </a>
    <a href="https://spring.io/projects/spring-boot/"> 
        <img src="https://img.shields.io/badge/Springboot3+-green" alt="springboot">
    </a>
</p>

## 前言
本项目参考自[PYTYUT](https://github.com/Yunding-Tech/PyTYUT)，在该项目基础上使用Java重构，更改了一些实现，并提供了一些新的功能，该项目仍在完善中，后续会进行优化并添加新功能

## 环境安装
!!! 使用前请确保您拥有权限安装软件包，具体请参考 [GitHub Packages](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry#authenticating-to-github-packages) 

将该存储库添加到pom中
```xml
    <repositories>
        <repository>
            <id>github</id>
            <name>GitHub OWNER Apache Maven Packages</name>
            <url>https://maven.pkg.github.com/ruafafa/ITYUT</url>
        </repository>
        ...
    </repositories>
```

使用maven安装
``` xml
    <dependency>
      <groupId>fun.ruafafa</groupId>
      <artifactId>ityut-spring-boot3-starter</artifactId>
      <version>1.1</version> 
    </dependency>
```

## 基本使用
ITYUT 的使用十分简单，以登录为例，您只需要：
```java
ITyutUtil.login("YOUR_ACCOUNT", "YOUR_PASSWORD");
```
即可完成登录，您可以在登录后使用：
```java
ITyutUtil.isLogin("YOUR_ACCOUNT"); 
```
该方法将返回一个布尔值，用于判断是否登录成功，当然，您可能会觉得每次都需要传入账号密码十分麻烦，您可以使用提供的代理类来接收 login 返回的结果，并使用代理类来完成 ITyutUtil 能完成的所有功能（除登录外）：
```java
ITyutUtilProxy user = ITyutUtil.login("YOUR_ACCOUNT", "YOUR_PASSWORD");
user.isLogin();
```
如果您想要登出账号，您可以使用：
```java
// 代理对象
user.logout();
// 指定账户
ITyutUtil.logout("USER_ACCOUNT");
```
当您登录成功后，就可以使用其他功能了

## 功能
通过 ITyutUtil 或者其代理对象的 Api,您可以做到:
- [x] 登录
- [x] 登出
- [x] 获取GPA等
- [x] 获取考试成绩
- [x] 获取班级课表
- [x] 获取学年学期
- [x] 获取考试安排
- [x] 获取教学建筑
- [x] 获取考试成绩
- [x] 获取学生信息
- [x] 一键教评
- [ ] 选课


## 自动配置
当前版本默认引入了 4 个访问节点，其中 [Ruafafa-JWC](http://jwc.ruafafa.fun)  节点无需连接校园网即可使用，其余节点需要自行连接TYUT校园网使用，不用就保证节点可用性，
一些默认节点可能会在后续版本中被修改或删除，如果您需要使用其他节点，请参考
下文引入自定义节点，您引入的节点将被优先选择

```yaml
ityut:
  cutomNodes:
    - xxxxxxxx
    - ....
```
