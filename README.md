# dynamicDs
### 基于SpringBoot动态切换数据源、连接池，并支持多数据源事务管理

## 使用方法
---
首先导入**dynamicDs.jar**，或导入**dynamic**目录下所有文件<br>
在配置文件**application.yml**内添加如下配置<br>
---
```properties
spring:
  datasource:
    main:
      driver-class-name: xxx.xxx.xxx.xxx
      password: xxxxxx
      username: xxxxxx
      url: jdbc:xxxx://xxxx:xxxx/xxxx
    jntzXXX:
      driver-class-name: xxx.xxx.xxx.xxx
      password: xxxxxx
      username: xxxxxx
      url: jdbc:xxxx://xxxx:xxxx/xxxx
    hanimeXXX:
      driver-class-name: xxx.xxx.xxx.xxx
      password: xxxxxx
      username: xxxxxx
      url: jdbc:xxxx://xxxx:xxxx/xxxx
      
ds:
  pool:
    type: hikari
#    type: druid
```
---
必须配置main数据源，main数据源为默认数据源，且名称必须命名为main，<br>
后续数据源可按照需求进行配置<br>
ds.pool.type为当前数据连接池 <br>
如不配置默认使用hikari 如需使用druid需pom.xml中添加相关依赖<br>
---
---
以下为相关函数方法
---
```java
// 当前方法为切换数据源
DbContext.switchDb("配置的数据源名称");
// 当前方法为重置数据源
DbContext.resetDb();
// 当前方法为获取数据源名称
DbContext.getCurDb();
```
