# xxl job spring boot starter

## 1. Intro

xxl-job-spring-boot-starter,用来快速的在spring boot项目中集成xxl-job。

## 2. Usage
### 引入依赖

```xml

<dependency>
    <groupId>com.github.xxl-job</groupId>
    <artifactId>xxl-job-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 使用`AdminTemplate`管理任务
application.properties

```properties
xxl.job.admin.admin-address=http://127.0.0.1:8082/xxl-job-admin
xxl.job.admin.password=123456
xxl.job.admin.username=admin
```
your code

```java
import com.github.colafreer.xxl.job.spring.annotation.EnableXxlJobAdminTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableXxlJobAdminTemplate
class XxlConfig {
}
```

```java
@Service
public class MyXxlJobServiceImpl implements MyXxlJobService{
    @Autowired
    AdminTemplate adminTemplate;


    public Integer addJob(JobInfo jobInfo) {
        return adminTemplate.addJob(jobInfo);
    }
}
```
### 使用`ExecutorTemplate`执行任务
application.properties

```properties
xxl.job.executor.access-token=default
xxl.job.executor.address=http://
xxl.job.executor.admin-address=http://
xxl.job.executor.app-name=xxl-job-executor-sample
xxl.job.executor.ip=10.
xxl.job.executor.log-path=/data/applogs/xxl-job/jobhandler
xxl.job.executor.port=9999
```
your code

```java
import org.springframework.context.annotation.Configuration;
import com.github.com.github.colafreer.xxl.job.spring.annotation.EnableXxlJobExecutor;

@Configuration
@EnableXxlJobExecutor
class XxlConfig {
}
```

```java

```

```java
import com.xxl.job.core.context.XxlJobHelper;


@Component
public class MyXxlJobHandler{
    @XxlJob("demoJobHandler")
    public void hello() {
        String name =  XxlJobHelper.getJobParam("name");
        System.out.println("hello world"+name);
    }
}
```
## 3. TODO
- [ ] demo
- [ ] 上传Maven仓库
