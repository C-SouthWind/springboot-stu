# 微服务阶段

javase：OOP

mysql：持久化

html+css+js+jquery+框架：视图，框架

javaweb：独立开发MVC三层架构的网站

ssm：简化了我们的开发流程，配置也开始为复杂

**war：tomcat运行**

spring再简化：springboot-jar：内嵌tomcat；微服务架构！

服务越来越多：springcloud



## springboot

- 什么是springboot
- 配置如何编写yaml
- 自动装配原理
- 集成web开发：业务的核心
- 分布式开发：Dubbo（RPC）+zookeeper
- swagger：接口文档
- 任务调度
- SpringSecurity、shiro



## SpringCloud

- 微服务
- springcloud入门
- Restful
- Eureka
- Ribbon
- Feign
- HyStrix
- Zuul网关
- SpringCloud config：git



## Linux



## JVM





# Springboot

## 什么是spring

Spring是一个开源框架，2003年兴起的一个轻量级的Java开发框架，作者 Rod Johnson

Spring是为了解决企业级应用开发的复杂性而创建的，简化开发



## spring是如何简化java开发的

为了降低java开发的复杂性，spring采用了4种关键策略

1. 基于POJO的轻量级和最小入侵性编程
2. 通过IOC，依赖注入（DI）和面向接口实现松耦合
3. 基于切面（AOP）和惯例进行声明式编程
4. 通过切面和模板减少样式代码



## 什么是springboot

​		就是一个javaweb的开发框架，和SpringMvc类似，对比其他javaweb框架的好处，官方说是简化开发，**约定大于配置**，you can “ just run ” ，能迅速的开发web应用，几行代码开发一个http接口



## springboot的主要优点

- 为所有Spring开发者更快的入门
- **开箱即用** ，提供各种默认配置来简化项目配置
- 内嵌式容器简化Web项目
- 没有冗余代码生成和XML配置的要求



## 第一个SpringBoot程序

环境

- jdk1.8
- maven3.6.1
- springboot 最新版
- IDEA



1. new  project   选择  spring initializr  下一步.....

2. 创建controller包  

   ```java
   @RestController
   public class HelloController {
   
       //接口 ：http://localhost:8080/hello
       @RequestMapping("/hello")
       public String hello(){
           //调用前端业务，接受前端的参数
           return "hello world";
       }
   
   }
   
   ```

   启动类

   ```java
   //本身就是一个spring的一个组件
   //程序的主入口
   @SpringBootApplication
   public class HelloworldApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(HelloworldApplication.class, args);
       }
   
   }
   
   ```

   pom文件

   ```java
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
       <!--有一个父项目-->
       <parent>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-parent</artifactId>
           <version>2.6.4</version>
           <relativePath/> <!-- lookup parent from repository -->
       </parent>
       <groupId>com.chj</groupId>
       <artifactId>helloworld</artifactId>
       <version>0.0.1-SNAPSHOT</version>
       <name>helloworld</name>
       <description>Demo project for Spring Boot</description>
       <properties>
           <java.version>1.8</java.version>
       </properties>
       <dependencies>
           <dependency>
               <!--web 依赖：tomcat，dispathservlet，xml....-->
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           <!--spring-boot-starter 所有的springboot依赖都是使用这个开头的-->
   
           <!--单元测试-->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
               <scope>test</scope>
           </dependency>
       </dependencies>
   
       <build>
           <plugins>
               <plugin>
                   <!--打jar包插件-->
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-maven-plugin</artifactId>
               </plugin>
           </plugins>
       </build>
   
   </project>
   
   ```


```properties
#修改端口号
server.port=8081
创建banner.txt可以替换springboot启动时的插图
```



## 原理初探

### 自动配置：

#### pom.xml

spring-boot-starter-parent

- spring-boot-dependencies：核心依赖在父工程中
- 我们在写或者引入一些springboot依赖的时候，不需要指定版本，就因为有这些版本仓库

#### 启动器

- ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
  </dependency>
  ```

- 启动器：说白了就是Springboot的启动场景

- 自动导入web环境的所有依赖

- springboot会将所有的功能场景，都变成一个个的启动器

- 我们要使用什么功能，只需要找到对应的启动器就可以了  starter

#### 主程序

```java
//SpringBootApplication：标注这个类是一个springboot的应用
@SpringBootApplication
public class HelloworldApplication {
    //将springboot应用启动
    public static void main(String[] args) {
        SpringApplication.run(HelloworldApplication.class, args);
    }
}
```

- 注解

  - ```java
    @SpringBootConfiguration :  springboot的配置
    	@Configuration :  spring配置类
    		@Component	： 说明这也是一个spring的组件
            
    @EnableAutoConfiguration : 自动配置
        @AutoConfigurationPackage ： 自动配置包
        	@Import(AutoConfigurationPackages.Registrar.class) ： 自动配置 包注册（导入了元数据）
        @Import(AutoConfigurationImportSelector.class) ： 自动配置导入选择
        	//获取所有的配置
        	List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
    ```

    @EnableAutoConfiguration

    ​		 @Import(AutoConfigurationImportSelector.class)

    ​					AutoConfigurationImportSelector

    获取候选的配置

    ```java
    protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
        //调用getSpringFactoriesLoaderFactoryClass()方法
        List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
                                                                             getBeanClassLoader());
        //断言 非空
        Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
                        + "are using a custom packaging, make sure that file is correct.");
        return configurations;
    }
    
    
    //返回EnableAutoConfiguration
    //EnableAutoConfiguration又存在于@SpringBootApplication中 至此启动类下的所有资源被导入
    protected Class<?> getSpringFactoriesLoaderFactoryClass() {
    		return EnableAutoConfiguration.class;
    }
    ```

    META-INF/spring.factories：自动配置的核心文件

    ```txt
    spring-boot-autoconfigure:2.6.4
    	META-INF
    		spring.factories
    ```

    > ```
    > Properties properties = PropertiesLoaderUtils.loadProperties(resource);
    > 所有资源加载到配置类中
    > ```

    

![自动配置原理分析](F:\student\md文件\mdFile\SpringBoot.assets\自动配置原理分析.png)



结论：spingboot所有自动配置都是在启动的时候扫描并加载：spring.factories所有的自动配置类都在这里面，要判断条件是否成立。只要导入对应的start，就有对应的启动器了，有了启动器，我们自动配置就会生效，然后就配置成功



1. springboot在启动的时候，从类路径下 /META-INF/spring.factories获取指定的值
2. 将这些自动配置的类导入容器，自动配置就会生效，帮我们进行自动配置
3. 以前我们需要自动配置的东西，现在springboot帮我们做了
4. 正好javaee，解决方案和自动配置的东西都在spring-boot-autoconfigure-x.x.x.jar 这个包下
5. 它会把所有需要导入的组件，以类名的方式返回，这些组件就会被添加到容器
6. 容器中也会存在非常多的xxxAutoConfiguration的文件（@Bean），就是这些类给容器中导入了这个场景需要的所有组件，并且自动配置 
7. 有了自动配置类，免去了我们手动编写配置文件的工作



#### 了解：@Conditiona

 ![图片](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7IPEXZtUAUBhnSZvUmrPzbDGcJRvdK3PtqHPAWYBBmpe1XBVjQJeiatU4vasEaxckHlOga1BV9RPaw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1) 



#### springApplication.run分析

> springApplication

这个类主要做了以下四件事

1、推断应用的类型是普通的项目还是web项目

2、查找并加载所有可用初始化器，设置到initializers属性中

3、找出所有的应用程序监听器，设置到listeners属性中

4、推断并设置main方法的定义类，找到运行的主类



 <img src="https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7L1vFQMnaRIJSmeZ58T2eZicjafiawQLp9u8wc4ic1Mjy6OyfibzfjVofeL5pnS1NSFKVjlIg6neI9ySg/640?wx_fmt=png&amp;wxfrom=5&amp;wx_lazy=1&amp;wx_co=1" alt="图片" /> 



#### 自动配置例子

```yaml
# 配置文件到底能写什么？ ---联系--- spring.factories
# 在我们这配置文件中能配置的东西，都存在一个固有的规律
# xxxAutoConfiguration 默认值： xxxProperties 和配置文件绑定，我们就可以使用自定义的配置了
```

spring.factories中的HttpEncodingAutoConfiguration

```java
//表示这是一个配置类
@Configuration(proxyBeanMethods = false)
//自动配置属性：ServerProperties
@EnableConfigurationProperties(ServerProperties.class)
//spring的底层注解：根据不同的条件，来判断当前配置或者类是否生效
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(CharacterEncodingFilter.class)
@ConditionalOnProperty(prefix = "server.servlet.encoding", value = "enabled", matchIfMissing = true)
public class HttpEncodingAutoConfiguration {}
```

HttpEncodingAutoConfiguration中的ServerProperties类

```java
@ConfigurationProperties(prefix = "server", ignoreUnknownFields = true)
public class ServerProperties {

	/**
	 * Server HTTP port.
	 */
	private Integer port;

	/**
	 * Network address to which the server should bind.
	 */
	private InetAddress address;
    .....
}
```

推断出yaml文件可以配置

```yaml
server:
	port: 8080
```

**那么多的自动配置类，必须在一定的条件下才能生效；也就是说，我们加载了这么多的配置类，但不是所有的都生效了。**

我们怎么知道哪些自动配置类生效？

**我们可以通过启用 debug=true属性；来让控制台打印自动配置报告，这样我们就可以很方便的知道哪些自动配置类生效；**

```yaml
#开启springboot的调试类
debug=true
```

**Positive matches:（自动配置类启用的：正匹配）**

**Negative matches:（没有启动，没有匹配成功的自动配置类：负匹配）**

**Unconditional classes: （没有条件的类）**



**一句话总结 ：根据当前不同的条件判断，决定这个配置类是否生效！**

- 一但这个配置类生效；这个配置类就会给容器中添加各种组件；
- 这些组件的属性是从对应的properties类中获取的，这些类里面的每一个属性又是和配置文件绑定的；
- 所有在配置文件中能配置的属性都是在xxxxProperties类中封装着；
- 配置文件能配置什么就可以参照某个功能对应的这个属性类

#### 总结

1、SpringBoot启动会加载大量的自动配置类

2、我们看我们需要的功能有没有在SpringBoot默认写好的自动配置类当中；

3、我们再来看这个自动配置类中到底配置了哪些组件；（只要我们要用的组件存在在其中，我们就不需要再手动配置了）

4、给容器中自动配置类添加组件的时候，会从properties类中获取某些属性。我们只需要在配置文件中指定这些属性的值即可；

**xxxxAutoConfigurartion：自动配置类；**给容器中添加组件

**xxxxProperties:封装配置文件中相关属性；**

## springboot配置



参考：https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/htmlsingle/#boot-features-external-config



### 配置文件

SpringBoot使用一个全局的配置文件，配置文件名称是固定的

- application.properties
  - 语法结构：key=value
- application.yml
  - 语法结构：key：空格value

**配置文件的作用：**修改SpringBoot自动配置的默认值，因为SpringBoot在底层都给我们自动配置好了



### YAML

YAML是 ” YAML  Ain‘t  a  Markup  Language “ （YAML不是一种置标语言）的递归缩写

在开发的这种语言时，YAML的意思其实是：” Yet  Another  Markup  Language “（仍是一种置标语言）

YAML A Markup  Language ：是一个标记语言

YAML  isnot  Markup  Language：不是一个标记语言

**标记语言**

以前的配置文件，大多数都是使用xml来配置；比如一个简单的端口配置

yaml配置：

```yaml
server:
  port: 8081
```

xml配置：

```xml
<server>
	<port>8081</port>
</server>
```

#### YAML语法

**基础语法**

> k:（空格）v

以此来表示键值对（空格不能省略）；以空格的缩进来控制层级关系，只要是左边对齐的一列数据都是同一个层级的

注意：属性的值的大小写都是十分敏感的

```yaml
#k= v
#对空格的要求十分高
#普通的key-value
#注入到我们的配置类中
name: chj

#对象
student:
  name: chj
  age: 20

#行内写法
students: {name: chj,age: 20}

#数组
pets:
  - cat
  - dog
  - pig

petss: [cat,dog,pig]
```

#### yaml注入配置文件

 yaml文件更强大的地方在于，他可以给我们的实体类直接注入匹配值！ 

1、在springboot项目中的resources目录下新建一个文件 application.yml

2、编写一个实体类 Dog；

```java
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Dog {
    @Value("旺财")
    private String name;
    @Value("3")
    private Integer age;

}
```

 3、我们在编写一个复杂一点的实体类：Person 类 

```java
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ConfigurationProperties(prefix = "person")
public class Person {
    private String name;
    private Integer age;
    private Boolean happy;
    private Date birthl;
    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;
}

```

 4、我们来使用yaml配置的方式进行注入，大家写的时候注意区别和优势，我们编写一个yaml配置！ 

```yaml
person:
  name: chj
  age: 20
  happy: true
  birthl: 2022/05/26
  maps: {k1: v1,k2: v2}
  lists:
    - code
    - music
    - girl
  dog:
    name: 旺财
    age: 3


```

##### 加载指定的配置文件

**@PropertySource ：**加载指定的配置文件；

**@configurationProperties**：默认从全局配置文件中获取值；

```java

@PropertySource(value = "classpath:person.properties")
@Component //注册bean
public class Person {

    @Value("${name}")
    private String name;

    ......  
}
```

##### 配置文件占位符

 配置文件还可以编写占位符生成随机数 

```yaml
person:
    name: chj${random.uuid} # 随机uuid
    age: ${random.int}  # 随机int
    happy: false
    birth: 2000/01/01
    maps: {k1: v1,k2: v2}
    lists:
      - code
      - girl
      - music
    dog:
      name: ${person.hello:other}_旺财
      age: 1
```



##### 对比

 ![图片](https://mmbiz.qpic.cn/mmbiz_png/uJDAUKrGC7KtjyIb9NEaYlz0tCWSiboOYjMibiaov73iaTsiaWEPoArDcAB1Ooibx9uR5JxtacIuicHblEtUI9SrySX2A/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1) 

1、@ConfigurationProperties只需要写一次即可 ， @Value则需要每个字段都添加

2、松散绑定：这个什么意思呢? 比如我的yml中写的last-name，这个和lastName是一样的， - 后面跟着的字母默认是大写的。这就是松散绑定。可以测试一下

3、JSR303数据校验 ， 这个就是我们可以在字段是增加一层过滤器验证 ， 可以保证数据的合法性

4、复杂类型封装，yml中可以封装对象 ， 使用value就不支持

**结论：**

配置yml和配置properties都可以获取到值 ， 强烈推荐 yml；

如果我们在某个业务中，只需要获取配置文件中的某个值，可以使用一下 @value；

如果说，我们专门编写了一个JavaBean来和配置文件进行一一映射，就直接@configurationProperties，不要犹豫！



##### JSR303

```java
@ConfigurationProperties(prefix = "cat")
@Validated//数据校验
public class Cat {

    private String name;
    private Integer age;
    @Email(message = "邮箱格式错误")
    private String email;
}

```

```yacas
cat:
  name: 猫猫
  age: 3
  email: 1298365022
```

```xml
Binding to target org.springframework.boot.context.properties.bind.BindException: Failed to bind properties under 'cat' to com.chj.pojo.Cat failed:

    Property: cat.email
    Value: 1298365022
    Origin: class path resource [application.yaml] - 19:10
    Reason: 邮箱格式错误
```





 ![img](https://upload-images.jianshu.io/upload_images/3145530-8ae74d19e6c65b4c?imageMogr2/auto-orient/strip|imageView2/2/w/654/format/webp) 

 ![img](https://upload-images.jianshu.io/upload_images/3145530-10035c6af8e90a7c?imageMogr2/auto-orient/strip|imageView2/2/w/432/format/webp) 



### 多环境切换

#### 方式一：

创建文件application.yaml，application-test.yaml，application-dev.yaml

```yaml
#application.yaml
server:
  port: 8081
#springboot的多环境配置：可以选择激活哪一个配置文件
spring:
  profiles:
    active: dev
  
#application-test.yaml
server:
  port: 8082
  
#application-dev.yaml
server:
  port: 8083
```

#### 方式二：

创建文件application.yaml

```yaml
server:
  port: 8080

#springboot的多环境配置：可以选择激活哪一个配置文件
spring:
  profiles:
    active: dev

---
server:
  port: 8081
spring:
  profiles: dev
---
server:
  port: 8082
spring:
  profiles: test


```



# SpringBoot Web开发

自动装配

springboot到底帮我们配置了什么？我们能不能进行修改？能修改那些东西？能不能扩展

- xxxxAutoConfiguraion...  向容器中自动配置组件
- xxxxProperties：自动配置类，装配配置文件中自定义的一些内容！

要解决的问题

- 导入静态资源....
- 首页
- jsp，模板引擎Thymeleaf
- 装配扩展SpringMvc
- 增删改查
- 拦截器
- 国际化



## 静态资源

```java
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //是否启用默认资源处理（默认是true）
    if (!this.resourceProperties.isAddMappings()) {
        logger.debug("Default resource handling disabled");
        return;
    }
    //把classpath:/META-INF/resources/webjars/下的文件路径映射到 /webjars/**  项目启动可以使用localhost:8080/webjars/jquery/3.4.1/jquery.js
    addResourceHandler(registry, "/webjars/**", "classpath:/META-INF/resources/webjars/");
    
    //可以识别this.mvcProperties.getStaticPathPattern() 的/**
    // this.resourceProperties.getStaticLocations()的 classpath:/META-INF/resources/,
	//			classpath:/resources/, classpath:/static/, classpath:/public/
    addResourceHandler(registry, this.mvcProperties.getStaticPathPattern(), (registration) -> {
        registration.addResourceLocations(this.resourceProperties.getStaticLocations());
        if (this.servletContext != null) {
            ServletContextResource resource = new ServletContextResource(this.servletContext, SERVLET_LOCATION);
            registration.addResourceLocations(resource);
        }
    });
}
```

总结：

1、在springboot，我们可以使用以下方式处理静态资源

- webjars
- public，static，resources，/**

2、优先级：resources > static > public



## 首页如何定制

resources下放置图片名为 favicon.ico 的图片 可修改导航栏title



## 模板引擎

pom文件引入

```pom
<dependency>
	<groupId>org.thymeleaf</groupId>
	<artifactId>thymeleaf-spring5</artifactId>
</dependency>
<dependency>
	<groupId>org.thymeleaf.extras</groupId>
	<artifactId>thymeleaf-extras-java8time</artifactId>
</dependency>
```

在resources/templates下创建html文件  通过controller可以直接进入 配置可参考ThymeleafProperties

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<!--所有的html元素都可以被thymeleaf替换接管 用法  th：元素名-->
    <div th:text="${msg}"></div>
    <div th:utext="${msg}"></div>

<hr>

<h3 th:each="user:${users}" th:text="${user}"></h3>

<hr>

<h3 th:each="user:${users}">[[ ${user} ]]</h3>
</body>
</html>
```

java

```java
@RequestMapping("/test")
    public String index(Model model){
        model.addAttribute("msg","<h1>hello</h1>");
        model.addAttribute("users", Arrays.asList("chj","zs"));
        return "test";
    }
```



## 装配扩展springmvc

添加配置java

```java
package com.chj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;
//如果你想diy一些定制化的功能，只要写这个组件，然后将它交给springboot，springboot就会帮我们自动装配
//全面扩展springmvc
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    //viewResolver 实现了视图解析器接口的类，我们就可以把它看做视图解析器

    @Bean
    public ViewResolver myViewResolver(){
        return new MyViewResolver();
    }

    //自定义一个自己的视图解析器
    public static class MyViewResolver implements ViewResolver {
        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
            return null;
        }
    }
}

```



## 总结

在Springboot中，有非常多的xxxConfiguration帮助我们进行扩展配置，只要看见了这个东西，我们就要注意看这个类扩展了什么



# 项目实战

静态文件位置https://gitee.com/xuzebin/springboot-view/tree/master/zzz

## 1、将404、dashboard、index、list  放入resources下的 templates文件夹中

## 2、将css、js、img 放入resources下的 static文件夹中

![1646654799988](F:\student\md文件\mdFile\SpringBoot.assets\1646654799988.png)

## 3、创建pojo文件夹

创建类Department

```java
//部门表
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private Integer id;
    private String departmentName;
}
```

创建类Employee

```java
//员工表
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer id;
    private String lastName;
    private String email;
    private Integer gender; //0:女  1：男
    private Department department;
    private Date birth;
}
```

## 4、创建mapper文件夹

创建DepartmentMapper类

```java
//交给spring
@Repository
//部门mapper
public class DepartmentMapper {
    //模拟数据库中的数据
    private static Map<Integer, Department> departmentMap = null;

    static {
        departmentMap = new HashMap<Integer, Department>(); //创建一个部门表
        departmentMap.put(101,new Department(101,"教学部"));
        departmentMap.put(102,new Department(102,"市场部"));
        departmentMap.put(103,new Department(103,"教研部"));
        departmentMap.put(104,new Department(104,"运营部"));
        departmentMap.put(105,new Department(105,"后勤部"));
    }

    //获得所有部门信息
    public Collection<Department> getDepartments(){
        return departmentMap.values();
    }

    //通过id得到部门
    public Department getDepartment(Integer id){
        return departmentMap.get(id);
    }
}
```

创建EmployeeMapper类

```java
//交给spring
@Repository
//员工mapper
public class EmployeeMapper {
    //模拟数据库中的数据
    private static Map<Integer, Employee> employees = null;
    //员工有所属的部门
    @Autowired
    private DepartmentMapper departmentMapper;

    static {
        employees = new HashMap<Integer, Employee>(); //创建一个部门表
        employees.put(1001,new Employee(1001,"AA","A123456789",0,new Department(101,"教学部")));
        employees.put(1002,new Employee(1002,"BB","B123456789",1,new Department(102,"市场部")));
        employees.put(1003,new Employee(1003,"CC","C123456789",0,new Department(103,"教研部")));
        employees.put(1004,new Employee(1004,"DD","D123456789",1,new Department(104,"运营部")));
        employees.put(1005,new Employee(1005,"EE","E123456789",0,new Department(105,"后勤部")));
    }

    //主键自增！
    private static Integer initId = 1006;
    //增加一个员工
    public void save(Employee employee){
        if (employee.getId()==null) {
            employee.setId(initId);
        }
        employee.setDepartment(departmentMapper.getDepartment(employee.getDepartment().getId()));
        employees.put(employee.getId(),employee);
    }

    //查询全部员工
    public Collection<Employee> getAll(){
        return employees.values();
    }

    //通过id查询员工
    public Employee getEmployee(Integer id){
        return employees.get(id);
    }

    //删除员工
    public void delete(Integer id){
        employees.remove(id);
    }
}
```

## 5、创建controller文件夹

创建IndexController类

```java
@Controller
public class IndexController {

    @RequestMapping({"/","index.html"})
    public String index(){
        return "index";
    }
}

```

## 6、创建config文件夹

创建mvc配置MyMvcConfig

```java
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
    }
}
```

## 7、修改html中的路径 确保找到正确的js、css、img



## 8、配置yaml文件

```yaml
#关闭模板引擎的缓存
spring:
  thymeleaf:
    cache: false

#虚拟目录 访问localhost:8080/chj 代替localhost:8080/
server:
  servlet:
    context-path: /chj
```



## 9、配置页面国际化

### 创建文件夹i18n（internationalization）

i18n（internationalization）（首位字母18位所以叫i18n）

创建login.properties，login_en_US.properties，login_zh_CN.properties

![1646741901919](F:\student\md文件\mdFile\SpringBoot.assets\1646741901919.png)

使用Resource Bundle配置

## 修改html文件

th:href="@{/css/bootstrap.min.css}"

th:href="@{/css/signin.css}"

th:src="@{/img/bootstrap-solid.svg}"

th:text="#{login.tip}"

th:placeholder="#{login.usernam}" 

th:placeholder="#{login.password}"

[[#{login.remember}]]

th:text="#{login.btn}"

```html
<body class="text-center">
    <form class="form-signin" th:action="@{/user/login}">
        <img class="mb-4" th:src="@{/img/bootstrap-solid.svg}" alt="" width="72" height="72">
        <h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}">Please sign in</h1>
        <!--如果msg消息不为空才显示-->
        <p style="color: red" th:text="${msg}" th:if="${not #strings.isEmpty(msg)}"></p>
        <label class="sr-only" >Username</label>
        <input name="username" type="text" class="form-control" th:placeholder="#{login.usernam}" required="" autofocus="">
        <label class="sr-only" >Password</label>
        <input name="password"  type="password" class="form-control" th:placeholder="#{login.password}" required="">
        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" value="remember-me" > [[#{login.remember}]]
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" th:text="#{login.btn}">Sign in</button>
        <p class="mt-5 mb-3 text-muted">© 2017-2018</p>
        <a class="btn btn-sm" th:href="@{/index.html(l='zh_CN')}">中文</a>
        <a class="btn btn-sm" th:href="@{/index.html(l='en_US')}">English</a>
    </form>

</body><head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Signin Template for Bootstrap</title>
    <!-- Bootstrap core CSS -->
    <link th:href="@{/css/bootstrap.min.css}" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link th:href="@{/css/signin.css}" rel="stylesheet">
</head>

<body class="text-center">
    <form class="form-signin" action="/user/login">
        <img class="mb-4" th:src="@{/img/bootstrap-solid.svg}" alt="" width="72" height="72">
        <h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}">Please sign in</h1>
        <label class="sr-only" >Username</label>
        <input type="text" class="form-control" th:placeholder="#{login.usernam}" required="" autofocus="">
        <label class="sr-only" >Password</label>
        <input type="password" class="form-control" th:placeholder="#{login.password}" required="">
        <div class="checkbox mb-3">
            <label>
                <input type="checkbox" value="remember-me" > [[#{login.remember}]]
            </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" th:text="#{login.btn}">Sign in</button>
        <p class="mt-5 mb-3 text-muted">© 2017-2018</p>
        <a class="btn btn-sm" th:href="@{/index.html(l='zh_CN')}">中文</a>
        <a class="btn btn-sm" th:href="@{/index.html(l='en_US')}">English</a>
    </form>

</body>
```

## 在config文件夹中创建MyLocaleResolver

```java
@Configuration
public class MyLocaleResolver implements LocaleResolver {
    //解析请求
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        //获取请求中的语言参数
        String language = request.getParameter("l");

        Locale locale = Locale.getDefault();//如果没有就使用默认的
        //如果请求的链接携带了国际化的参数
        if (!StringUtils.isEmpty(language)) {
            //zh_CN
            String[] split = language.split("_");
            //语言，国家
            locale = new Locale(split[0],split[1]);
        }

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}

```

## 在config中创建MyMvcConfig

```java
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/main.html").setViewName("dashboard");
    }

    //自定义的国家化组件就生效了
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index.html","/","/user/login","/css/*","/img/*","/js/*");
    }
}

```



## 在controller中创建LoginController

```java
@Configuration
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录成功之后，应该有用户的session
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("msg","没有权限，请先登录");
            request.getRequestDispatcher("/index.html").forward(request,response);
            return false;
        }
        return true;
    }

}
```



## 404以及500

在templates中新建error文件夹

将404和500放入即可



# 链接数据库Data

## springBoot自带链接（类似于jdbc原生链接）

添加jar包

```properties
<dependencies>
        <!--web-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--jdbc-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <!--lombok-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <!--mysql驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--测试-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
    </dependencies>
```

测试类

```java
@EnableAutoConfiguration
@SpringBootTest(classes = DataTest.class)
public class DataTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void dataTest() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }
}
```

controller

```java
@RestController
public class JDBCController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    //查询数据库的所有信息
    @RequestMapping("/userList")
    public List<Map<String, Object>> userList(){
        String sql = "select * from user";
        List<Map<String, Object>> list_maps = jdbcTemplate.queryForList(sql);
        return list_maps;
    }
}
```

## druid

访问： http://localhost:8080/druid

账号：admin

密码：123456

### 方法1：

pom

```pro
 <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.2.8</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j</artifactId>
            <version>1.3.8.RELEASE</version>
        </dependency>
```

yaml

```yaml
spring:
  datasource:
    name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://115.159.191.57:3306/zln?useSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8
    username: root
    password: chj0526
    type: com.alibaba.druid.pool.DruidDataSource
    filters: stat,wall,log4j2
```

java

```java
@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    //后台监控 web.xml ServletRegistrationBean
    // 因为springBoot 内置了 servlet容器，所有没有web.xml  替代方法 ServletRegistrationBean
    @Bean
    public  ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        //后台需要有人登录，账号密码配置
        Map<String, String> initParameters = new HashMap<>();
        //增加配置
        initParameters.put("loginUsername","admin");//登录key是固定的 loginUsername loginPassword
        initParameters.put("loginPassword","123456");

        //允许谁可以访问
        initParameters.put("allow","");

        //禁止谁能让问
        //initParameters.put("chj","192.169.1.1");

        bean.setInitParameters(initParameters);//设置初始化参数
        return bean;
    }

    //filter
    @Bean
    public FilterRegistrationBean webSta(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());

        //可以过滤那些请求
        Map<String, String> initParameters = new HashMap<>();
        //这些东西不进行统计
        initParameters.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParameters);
        return bean;
    }
}
```



### 方法2：

pom

```pom
 <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.2.8</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j</artifactId>
            <version>1.3.8.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
```

yaml

```yaml
spring:
  datasource:
    name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://115.159.191.57:3306/zln?useSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8
    username: root
    password: chj0526
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      filters: stat,wall,log4j2
      loginUsername: admin
      loginPassword: 123456
      stat-view-servlet:
        url-pattern: /druid/*
```



# Swagger

官网 https://swagger.io/

- 使用swagger2
- ui



## SpringBoot集成Swagger



导入jar

```properties
 <!-- https://mvnrepository.com/artifact/io.springfox/springfox-boot-starter -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-boot-starter</artifactId>
            <version>3.0.0</version>
        </dependency>
```

配置swagger2

```java
@Configuration
@EnableSwagger2 //开启swagger2
public class SwaggerConfig {
}
```

启动类

```java
@SpringBootApplication
@EnableWebMvc
public class ApplicationWeb {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationWeb.class,args);
    }
}
```

访问  http://localhost:8080/swagger-ui/index.html



## 配置Swagger

Swagger 的bean实例 Docket

```java
@Configuration
@EnableSwagger2 //开启swagger2
public class SwaggerConfig {

    //配置了swagger的Docket的bean实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(apiInfo());
    }

    //配置Swagger信息=apiInfo
    private ApiInfo apiInfo(){
        //作者信息
        Contact contact = new Contact(
                "chen",
                "http://www.baidu.com",
                "15139530908");
        return new ApiInfo(
                "chj的swaggerApi文档",
                "chj",
                "v1.0",
                "http://www.baidu.com",
                 contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }

}
```



## Swagger配置扫描接口

```java
//配置了swagger的Docket的bean实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                /*
                配置要扫描接口的方式
                basePackage 指定要扫描的包
                any() 扫描全部
                none() 不扫描
                withClassAnnotation() 扫描类上的注解，参数是一个注解的反射对象
                withMethodAnnotation() 扫描方法上的注解
                */
                .apis(RequestHandlerSelectors.basePackage("com.chj.controller"))
                //paths() 扫描指定路径
                .paths(PathSelectors.ant("/chj/**"))
                .build();
    }
```

## 配置是否启动swagger

```java
//配置了swagger的Docket的bean实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //是否开启swagger
                .enable(true)
                .select()
                /*
                配置要扫描接口的方式
                basePackage 指定要扫描的包
                any() 扫描全部
                none() 不扫描
                withClassAnnotation() 扫描类上的注解，参数是一个注解的反射对象
                withMethodAnnotation() 扫描方法上的注解
                */
                .apis(RequestHandlerSelectors.basePackage("com.chj.controller"))
                //paths() 扫描指定路径
                //.paths(PathSelectors.ant("/chj/**"))
                .build();
    }

```

## 扫描当前环境

```java
 //配置了swagger的Docket的bean实例
    @Bean
    public Docket docket(Environment environment){
        //设置要显示的swagger环境
        Profiles profiles = Profiles.of("dev");
        //获取项目的环境
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //是否开启swagger
                .enable(flag)
                .select()
                /*
                配置要扫描接口的方式
                basePackage 指定要扫描的包
                any() 扫描全部
                none() 不扫描
                withClassAnnotation() 扫描类上的注解，参数是一个注解的反射对象
                withMethodAnnotation() 扫描方法上的注解
                */
                .apis(RequestHandlerSelectors.basePackage("com.chj.controller"))
                //paths() 扫描指定路径
                //.paths(PathSelectors.ant("/chj/**"))
                .build();
    }
```



```yaml
spring:
  profiles:
    active: dev
    

server:
  port: 8081
  
  
server:
  port: 8082
```

## 配置API文档的分组

```java
 @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("A");
    }
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("B");
    }
    @Bean
    public Docket docket3(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("C");
    }

    //配置了swagger的Docket的bean实例
    @Bean
    public Docket docket(Environment environment){
        //设置要显示的swagger环境
        Profiles profiles = Profiles.of("dev");
        //获取项目的环境
        boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("chen")
                //是否开启swagger
                .enable(flag)
                .select()
                /*
                配置要扫描接口的方式
                basePackage 指定要扫描的包
                any() 扫描全部
                none() 不扫描
                withClassAnnotation() 扫描类上的注解，参数是一个注解的反射对象
                withMethodAnnotation() 扫描方法上的注解
                */
                .apis(RequestHandlerSelectors.basePackage("com.chj.controller"))
                //paths() 扫描指定路径
                //.paths(PathSelectors.ant("/chj/**"))
                .build();
    }

```

多个docket实例实现分组



## swagger显示中文注释



实体类

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户实体类")
public class User {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
}
```

控制层

```java
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    //只要我们的接口中，返回值中存在实体类，他就会被扫描到swagger中
    @PostMapping("/user")
    public User user(){
        return new User();
    }


    @GetMapping("/hello2")
    @ApiOperation("hello控制类")
    public String hello2(@ApiParam("用户名") String username){
        return "hello"+username;
    }
}
```



## 总结

1、我们可以通过Swagger给一些比较男理解的属性或者接口，增加注释信息

2、接口文档实时更新

3、可以在线测试





# 任务

##  异步任务



方法添加注解

```java
@Service
public class AsyncService {

    //告诉spring这是一个异步的方法
    @Async
    public void hello(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("数据正在处理....");
    }
}

```



启动类添加注解

```java
//开启异步注解功能
@EnableAsync
@SpringBootApplication
public class ApplicationWeb {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationWeb.class,args);
    }
}

```



## 邮件任务

导入jar

```jar
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

配置

```yaml
spring:
  mail:
    username: 1298365022@qq.com
    password: jgmxszbymzasbafh
    host: smtp.qq.com
    #开启加密验证
    properties:
      mail:
        smtp:
          ssl:
            enable: true
```

java

```java
@SpringBootTest(classes = TestMail.class)
@EnableAutoConfiguration
public class TestMail {
    @Autowired
    JavaMailSenderImpl mailSender;
    @Test
    public void test(){
        //一个简单的邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("chj，你好");
        simpleMailMessage.setText("chj的邮件测试");
        simpleMailMessage.setTo("1298365022@qq.com");
        simpleMailMessage.setFrom("1298365022@qq.com");

        mailSender.send(simpleMailMessage);
    }
    @Test
    public void test2() throws Exception{
        //一个复杂的邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        //组装
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("chj，你好！====");
        helper.setText("<p style='color:red'>这是一段话</p>",true);
        //附件
        helper.addAttachment("1.jpg",new File("F:\\ideaWork\\springboot\\stu\\springBootStu\\springboot-09-webMail\\src\\main\\resources/1.jpg"));
        helper.setTo("1298365022@qq.com");
        helper.setFrom("1298365022@qq.com");

        mailSender.send(mimeMessage);
    }
}
```



## 定时任务

> TaskScheduler     任务调度者
>
> TaskExecutor        任务执行者
>
> 
>
> ```
> @EnableScheduling //开启定时功能的注解
> @scheduled //什么时候执行
> 
> cron表达式
> ```



启动类

```java
@SpringBootApplication
@EnableScheduling//开启定时功能的注解
public class ApplicationWeb {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationWeb.class,args);
    }
}

```



定时任务

```java
@Service
public class ScheduledService {
    //在一个特定的时间执行这个方法
    //cron  秒 分 时 日 月 周几
    //每三秒执行一次
    @Scheduled(cron = "0/3 * * * * *")
    public void hello(){
        System.out.println("hello，你被执行了");
    }
}

```



# redis

## 源码

```java
@Bean
@ConditionalOnMissingBean(name = "redisTemplate")//我们可以自定义一个RedisTemplate来替换
@ConditionalOnSingleCandidate(RedisConnectionFactory.class)
public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
    //默认的RedisTemplate没有过多的设置，redis对象都是需要序列化的
    //两个泛型都是<Object, Object> ，我们使用需要强制转换
    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
}

@Bean
@ConditionalOnMissingBean//由于string是redis中最常用的类型，所以单独提出来一个bean
@ConditionalOnSingleCandidate(RedisConnectionFactory.class)
public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    return new StringRedisTemplate(redisConnectionFactory);
}
```

RedisConnectionFactory中多有两个实现类LettuceConnectionFactory和JedisConnectionFactory

JedisConnectionFactory中很多标红的不能使用 

LettuceConnectionFactory中可以使用，默认使用的这个

## 整合redis

导入jar

```java
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

添加配置

```java
spring:
  redis:
    host: 115.159.191.57
    port: 6379
```

测试

```java
@SpringBootTest(classes = RedisTest.class)
@EnableAutoConfiguration
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void test(){
        //redisTemplate  操作不同的数据类型，api和我们的指令是一样的
        //opsForValue  操作字符串 类似String
        //opsForList  操作List 类似List
        //opsForSet
        //opsForHash
        //opsForZSet
        //opsForGeo

        //除了基本的操作，我们常用的方法都是可以直接通过redisTemplate操作

        //获取redis的连接对象
        /*RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.flushAll();
        connection.flushDb();*/

        redisTemplate.opsForValue().set("chj","aa");
        System.out.println(redisTemplate.opsForValue().get("chj"));
    }
}

```



## 编写自己的redis序列化

序列化才能进行传输，不然会报错

> 使用 new ObjectMapper().writeValueAsString(new User("aa", 11));
>
> 或者实现 Serializable



```java
@Configuration
public class redisConfig {

    //编写我们自己的redisTemplate
    @Bean("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        //配置具体的序列化方式
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        //key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //hash的key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //value采用jakson的序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash的value采用jakson的序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
```



# shiro

github：https://github.com/apache/shiro

subject：当前用户

ShiroSecurityManager：安全管理器，管理所有的subject

Realm：连接数据





- Subject ：任何可以与应用交互的  ‘ 用户 ’
- Security Manager：所有的交互都通过Security Manager进行控制，管理所有的Subject，负责进行认证，授权，会话，及缓存的管理
- Authenticator：负责Subject的认知，可以自定义实现，可以使用认证策略。即什么情况下算用户认证通过了
- Realm：安全实体数据源，可以多个
- SessionManager：管理Session生命周期的组件
- CacheManager：缓存控制器，管理用户，角色，权限等缓存，因为这些数据基本很少改变，放到缓存中可以提高访问的性能
- Cryptography：密码模块，Shiro提高了一些常见的加密组件用于密码加密，解密



## 测试

1. resources下创建 log4j2.xml

   ```xml
   <Configuration name="ConfigTest" status="ERROR" monitorInterval="5">
   
       <Appenders>
           <Console name="Console" target="SYSTEM_OUT">
               <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
           </Console>
       </Appenders>
       <Loggers>
           <Logger name="org.springframework" level="warn" additivity="false">
               <AppenderRef ref="Console"/>
           </Logger>
           <Logger name="org.apache" level="warn" additivity="false">
               <AppenderRef ref="Console"/>
           </Logger>
           <Logger name="net.sf.ehcache" level="warn" additivity="false">
               <AppenderRef ref="Console"/>
           </Logger>
           <Logger name="org.apache.shiro.util.ThreadContext" level="warn" additivity="false">
               <AppenderRef ref="Console"/>
           </Logger>
           <Root level="info">
               <AppenderRef ref="Console"/>
           </Root>
       </Loggers>
   </Configuration>
   ```

2. resources下创建 shiro.ini

```ini
[users]
# 用户“root”，密码“secret” 角色“admin”
root = secret, admin
# 用户“guest”，密码“guest” 角色“guest”
guest = guest, guest
# 用户“presidentskroob”，密码“12345” 角色“president”
presidentskroob = 12345, president
# 用户“darkhelmet”，密码“ludicrousspeed” 角色“darklord，schwartz”
darkhelmet = ludicrousspeed, darklord, schwartz
# 用户“lonestarr”，密码“vespa” 角色“goodguy，schwartz”
lonestarr = vespa, goodguy, schwartz

# -----------------------------------------------------------------------------
# 具有分配权限的角色
#
# 每一行都符合中定义的格式
# org.apache.shiro.realm.text.TextConfigurationRealm#setRoleDefinitions JavaDoc
# -----------------------------------------------------------------------------
[roles]
# 'admin' 角色具有所有权限，由通配符 '*' 指示
admin = *
# 'schwartz' 角色  可以具有lightsaber的所有权限：
schwartz = lightsaber:*
# goodguy' 角色  只能使用：winnebago下的drive下的eagle5权限
goodguy = winnebago:drive:eagle5
```

3. 创建shiro类

   ```java
   package com.chj.shiro;
   
   import org.apache.shiro.SecurityUtils;
   import org.apache.shiro.authc.*;
   import org.apache.shiro.config.IniSecurityManagerFactory;
   import org.apache.shiro.mgt.SecurityManager;
   import org.apache.shiro.session.Session;
   import org.apache.shiro.subject.Subject;
   import org.apache.shiro.util.Factory;
   import org.slf4j.Logger;
   import org.slf4j.LoggerFactory;
   
   public class ShiroOne {
   
   
       private static final transient Logger log = LoggerFactory.getLogger(ShiroOne.class);
       public static void main(String[] args) {
           //使用工厂读取shiro.ini
           Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
           //获得实例
           SecurityManager securityManager = factory.getInstance();
           //设置安全管理器
           SecurityUtils.setSecurityManager(securityManager);
           // 获取当前执行对象
           Subject currentUser = SecurityUtils.getSubject();
   
           //使用 Session 做一些事情
           Session session = currentUser.getSession();
           //设置Session值
           session.setAttribute("someKey", "aValue");
           //获取Session值
           String value = (String) session.getAttribute("someKey");
           if ("aValue".equals(value)) {
               log.info("session中设置的someKey值是：  [" + value + "]");
           }
           // 检查是否登录
           if (!currentUser.isAuthenticated()) {
               //获取token
               UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
               //记住我
               token.setRememberMe(true);
               try {
                   //登录
                   currentUser.login(token);
               } catch (UnknownAccountException uae) {//未知账户
                   log.info("没有用户名为 " + token.getPrincipal());
               } catch (IncorrectCredentialsException ice) {//凭证错误
                   log.info("帐户密码 " + token.getPrincipal() + " 不正确!");
               } catch (LockedAccountException lae) {//账户锁定
                   log.info("用户名的帐户 " + token.getPrincipal() + " 锁住了  ");
               }
               // ... catch more exceptions here (maybe custom ones specific to your application?
               catch (AuthenticationException ae) {//身份验证异常
                   //unexpected condition?  error?
               }
           }
           //获得当前用户的认证
           log.info("用户 [" + currentUser.getPrincipal() + "] 登录成功.");
           //是否拥有这个角色
           if (currentUser.hasRole("schwartz")) {
               log.info("你好  schwartz");
           } else {
               log.info("没有这个角色!");
           }
           //粗粒度权限
           if (currentUser.isPermitted("lightsaber:wield")) {
               log.info("你拥有lightsaber:wield 权限");
           } else {
               log.info("你没有lightsaber:wield权限");
           }
           //细粒度权限
           if (currentUser.isPermitted("winnebago:drive:eagle5")) {
               log.info("你拥有winnebago:drive:eagle5权限!");
           } else {
               log.info("你没有winnebago:drive:eagle5权限");
           }
           //退出
           currentUser.logout();
           System.exit(0);
       }
   }
   
   ```
   



