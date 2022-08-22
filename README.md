# 0.Springboot博客练手实战项目说明

码神之路网站所使用的博客，项目简单，需求明确，容易上手，非常适合做为练手级项目。

blog.mszlu.com

项目讲解说明：

1. 提供前端工程，只需要实现后端接口即可
2. 项目以单体架构入手，先快速开发，不考虑项目优化，降低开发负担
3. 开发完成后，开始优化项目，提升编程思维能力
4. 比如页面静态化，缓存，云存储，日志等
5. docker部署上线
6. 云服务器购买，域名购买，域名备案等

项目使用技术 ：

springboot + mybatisplus+redis+mysql

# 1. 工程搭建

前端的工程：

~~~shell
npm install
npm run build
npm run dev
~~~



## 1.1 新建maven工程

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.mszlu</groupId>
    <artifactId>blog-parent</artifactId>
    <version>1.0-SNAPSHOT</version>


    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.5.0</version>
        <relativePath/>
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <!-- 排除 默认使用的logback  -->
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!-- log4j2 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>


        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.76</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>

        <dependency>
            <groupId>commons-collections</groupId>
            <artifactId>commons-collections</artifactId>
            <version>3.2.2</version>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.3</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <!-- https://mvnrepository.com/artifact/joda-time/joda-time -->
        <dependency>
            <groupId>joda-time</groupId>
            <artifactId>joda-time</artifactId>
            <version>2.10.10</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
~~~

>   ⚠️`spring-boot-maven-plugin`要加版本号!!!



删除src文件夹创建分模块`blog-api`

将pom.xml中的依赖复制到`blog-api`中

### 使用\<dependencyManagement>包裹\<dependencies>

当我们的项目模块很多的时候，我们使用Maven管理项目非常方便，帮助我们管理构建、文档、报告、依赖、scms、发布、分发的方法。可以方便的编译代码、进行依赖管理、管理二进制库等等。

由于我们的模块很多，所以我们又抽象了一层，抽出一个itoo-base-parent来管理子项目的公共的依赖。为了项目的正确运行，必须让所有的子项目使用依赖项的==统一版本==，必须确保应用的各个项目的依赖项和版本一致，才能保证测试的和发布的是相同的结果。

在我们项目顶层的POM文件中，我们会看到dependencyManagement元素。通过它元素来管理jar包的版本，让子项目中引用一个依赖而不用显示的列出版本号。Maven会沿着父子层次向上走，直到找到一个拥有dependencyManagement元素的项目，然后它就会使用在这个dependencyManagement元素中指定的版本号。

概念项目pom继承关系图：

![img](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/2022021611322326.jpg)

itoo-base-parent(pom.xml)

```xml
<dependencyManagement>		
		<dependencies>
			<dependency>
				<groupId>org.eclipse.persistence</groupId>
				<artifactId>org.eclipse.persistence.jpa</artifactId>
				<version>${org.eclipse.persistence.jpa.version}</version>
				<scope>provided</scope>
			</dependency>
			
			<dependency>
				<groupId>javax</groupId>
				<artifactId>javaee-api</artifactId>
				<version>${javaee-api.version}</version>
			</dependency>
		</dependencies>
</dependencyManagement>
```

itoo-base(pom.xml)

```xml
    <!--...继承父类-->
    <parent>
            <artifactId>itoo-base-parent</artifactId>
            <groupId>com.tgb</groupId>

            <version>0.0.1-SNAPSHOT</version>
            <relativePath>../itoo-base-parent/pom.xml</relativePath>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <artifactId>itoo-base</artifactId>
    <packaging>ejb</packaging>
		
	<!--依赖关系-->
	<dependencies>
		<dependency>
			<groupId>javax</groupId>
			<artifactId>javaee-api</artifactId>
		</dependency>
		
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-annotations</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.eclipse.persistence</groupId>
			<artifactId>org.eclipse.persistence.jpa</artifactId>
			<scope>provided</scope>
		</dependency>
	</dependencies>
</project>
```

这样做的好处：统一管理项目的版本号，确保应用的各个项目的依赖和版本一致，才能保证测试的和发布的是相同的成果，因此，在顶层pom中定义共同的依赖关系。同时可以避免在每个使用的子项目中都声明一个版本号，这样想升级或者切换到另一个版本时，只需要在父类容器里更新，不需要任何一个子项目的修改；**如果某个子项目需要另外一个版本号时，只需要在dependencies中声明一个版本号即可**。子类就会使用子类声明的版本号，不继承于父类版本号。

-   相对于dependencyManagement，所有声明在dependencies里的依赖都会==自动引入==，并默认被所有的子项目继承。

#### dependencyManagement与dependencies的区别

（1）dependencies : 自动引入声明在dependencies里的所有依赖，并默认被所有的子项目继承。如果项目中不写依赖项，则会从父项目继承（属性全部继承）声明在父项目dependencies里的依赖项。

（2）dependencyManagement : 这个标签里==只是声明依赖==，并不实现引入，因此子项目需要显示的声明需要用的依赖。如果不在子项目中声明依赖，是不会从父项目中继承下来的；只有在子项目中写了该依赖项，并且没有指定具体版本，才会从父项目中继承该项，并且version和scope都读取自父pom;另外如果子项目中指定了版本号，那么会使用子项目中指定的jar版本。

（3）dependencyManagement 中的 dependencies 并不影响项目的依赖项；而独立dependencies元素则影响项目的依赖项。只有当外层的dependencies元素中没有指明版本信息时，dependencyManagement 中的 dependencies 元素才起作用。一个是项目依赖，一个是maven项目多模块情况时作依赖管理控制的。

## 1.2 配置

在`blog-api`中src/main/resources中创建`application.properties`

~~~properties
#server
server.port= 8888
spring.application.name=mszlu_blog
# datasource
spring.datasource.url=jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=UTF-8&serverTimeZone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

#mybatis-plus
	#打印sql语句日志
mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
	#标识表前缀
mybatis-plus.global-config.db-config.table-prefix=ms_
~~~

在config目录下创建`MybatisPlusConfig`用来分页

~~~java
@Configuration
//@MapperScan("com.hsxy.blog.mapper")
public class MybatisPlusConfig {
	//分页插件
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor(){
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor());//增加分页内拦截器
		return interceptor;
	}
}
~~~

在config目录下创建`WebMVCConfig`用来前后端跨域访问

~~~java
package com.mszlu.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置，不可设置为*，不安全, 前后端分离项目，可能域名不一致
        //本地测试 端口不一致 也算跨域
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }
}

~~~



## 1.3 启动类

### 启动报错:创建“dataSource”的bean时出错

```cmd
错误:Error starting ApplicationContext. To display the conditions report re-run your application with 'debug' enabled.
2022-08-08 09:58:03.665 ERROR 9900 --- [           main] o.s.b.SpringApplication                  : Application run failed

org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'dataSource' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.zaxxer.hikari.HikariDataSource]: Factory method 'dataSource' threw exception; nested exception is java.lang.IllegalStateException: Cannot load driver class: com.mysql.cj.jdbc.Driver...
```

-   原因
    导入了jdbc的依赖，使用@Configuration注解向spring注入了dataSource bean。
    但是因为工程中没有关于dataSource相关的配置信息，当spring创建dataSource bean因缺少相关的信息就会报错。

-   解决方法
    在启动类SpringbootApplication.class里添加注解

    >   `@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})`
    >
    >   ⚠️@EnableAutoConfiguration可能会导致Caused by: java.lang.IllegalArgumentException: Property ‘sqlSessionFactory’ or ‘sqlSessionTemplate’ are required

### BlogApp.java

~~~java
package com.mszlu.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class BlogApp {

    public static void main(String[] args) {
        SpringApplication.run(BlogApp.class,args);
    }
}
~~~



# 2. 首页-文章列表

## 2.1 接口说明

接口url：/articles

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明           |
| -------- | -------- | -------------- |
| page     | int      | 当前页数       |
| pageSize | int      | 每页显示的数量 |

返回数据：

~~~json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "title": "springboot介绍以及入门案例",
            "summary": "通过Spring Boot实现的服务，只需要依靠一个Java类，把它打包成jar，并通过`java -jar`命令就可以运行起来。\r\n\r\n这一切相较于传统Spring应用来说，已经变得非常的轻便、简单。",
            "commentCounts": 2,
            "viewCounts": 54,
            "weight": 1,
            "createDate": "2609-06-26 15:58",
            "author": "12",
            "body": null,
            "tags": [
                {
                    "id": 5,
                    "avatar": null,
                    "tagName": "444"
                },
                {
                    "id": 7,
                    "avatar": null,
                    "tagName": "22"
                },
                {
                    "id": 8,
                    "avatar": null,
                    "tagName": "11"
                }
            ],
            "categorys": null
        },
        {
            "id": 9,
            "title": "Vue.js 是什么",
            "summary": "Vue (读音 /vjuː/，类似于 view) 是一套用于构建用户界面的渐进式框架。",
            "commentCounts": 0,
            "viewCounts": 3,
            "weight": 0,
            "createDate": "2609-06-27 11:25",
            "author": "12",
            "body": null,
            "tags": [
                {
                    "id": 7,
                    "avatar": null,
                    "tagName": "22"
                }
            ],
            "categorys": null
        },
        {
            "id": 10,
            "title": "Element相关",
            "summary": "本节将介绍如何在项目中使用 Element。",
            "commentCounts": 0,
            "viewCounts": 3,
            "weight": 0,
            "createDate": "2609-06-27 11:25",
            "author": "12",
            "body": null,
            "tags": [
                {
                    "id": 5,
                    "avatar": null,
                    "tagName": "444"
                },
                {
                    "id": 6,
                    "avatar": null,
                    "tagName": "33"
                },
                {
                    "id": 7,
                    "avatar": null,
                    "tagName": "22"
                },
                {
                    "id": 8,
                    "avatar": null,
                    "tagName": "11"
                }
            ],
            "categorys": null
        }
    ]
}
~~~

## 2.2 编码

### 2.2.1 表结构

~~~sql
CREATE TABLE `blog`.`ms_article`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `comment_counts` int(0) NULL DEFAULT NULL COMMENT '评论数量',
  `create_date` bigint(0) NULL DEFAULT NULL COMMENT '创建时间',
  `summary` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `view_counts` int(0) NULL DEFAULT NULL COMMENT '浏览数量',
  `weight` int(0) NOT NULL COMMENT '是否置顶',
  `author_id` bigint(0) NULL DEFAULT NULL COMMENT '作者id',
  `body_id` bigint(0) NULL DEFAULT NULL COMMENT '内容id',
  `category_id` int(0) NULL DEFAULT NULL COMMENT '类别id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
~~~

~~~sql
CREATE TABLE `blog`.`ms_tag`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `article_id` bigint(0) NOT NULL,
  `tag_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `article_id`(`article_id`) USING BTREE,
  INDEX `tag_id`(`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
~~~

~~~sql
CREATE TABLE `blog`.`ms_sys_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `admin` bit(1) NULL DEFAULT NULL COMMENT '是否管理员',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_date` bigint(0) NULL DEFAULT NULL COMMENT '注册时间',
  `deleted` bit(1) NULL DEFAULT NULL COMMENT '是否删除',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `last_login` bigint(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `mobile_phone_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '加密盐',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
~~~

~~~java
package com.mszlu.blog.dao.pojo;

import lombok.Data;

@Data
public class Article {

    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    private Long id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private int weight = Article_Common;


    /**
     * 创建时间
     */
    private Long createDate;
}

~~~

~~~java
package com.mszlu.blog.dao.pojo;

import lombok.Data;

@Data
public class SysUser {

    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}

~~~

~~~java
package com.mszlu.blog.dao.pojo;

import lombok.Data;

@Data
public class Tag {

    private Long id;

    private String avatar;

    private String tagName;

}

~~~



### 2.2.2 Controller

~~~java
package com.mszlu.blog.api;

import com.mszlu.blog.dao.pojo.Article;
import com.mszlu.blog.service.ArticleService;
import com.mszlu.blog.vo.Archive;
import com.mszlu.blog.vo.ArticleVo;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
	//Result是统一结果返回
    @PostMapping
    public Result articles(@RequestBody PageParams pageParams) {
        //ArticleVo 页面接收的数据
        List<ArticleVo> articles = articleService.listArticlesPage(pageParams);

        return Result.success(articles);
    }


}

~~~





### 2.2.3 Service

service层主要是写业务逻辑方法，service层经常要调用dao层（也叫mapper层）的方法对数据进行增删改查的操作。

ArticleService

~~~java
public interface ArticleService {

    List<ArticleVo> listArticlesPage(PageParams pageParams);

}
~~~

Service.impl.ArticleServiceImpl

~~~java
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
   @Autowired
   private SysUserService sysUserService;
    @Autowired
    private TagsService tagsService;

    public ArticleVo copy(Article article,boolean isAuthor,boolean isBody,boolean isTags){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        if (isAuthor) {
            SysUser sysUser = sysUserService.findSysUserById(article.getAuthorId());
            articleVo.setAuthor(sysUser.getNickname());
        }
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if (isTags){
           List<TagVo> tags = tagsService.findTagsByArticleId(article.getId());
           articleVo.setTags(tags);
        }
        return articleVo;
    }

    private List<ArticleVo> copyList(List<Article> records,boolean isAuthor,boolean isBody,boolean isTags) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records) {
            ArticleVo articleVo = copy(article,isAuthor,isBody,isTags);
            articleVoList.add(articleVo);
        }
        return articleVoList;
    }


    @Override
    public List<ArticleVo> listArticlesPage(PageParams pageParams) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<ArticleVo> articleVoList = copyList(articlePage.getRecords(),true,false,true);
        return articleVoList;
    }


}
~~~

>   `BeanUtils.copyProperties(article, articleVo);`把A对象的name、age等属性复制到B对象中
>
>   必须要进行get,set才能进行(使用@data亦可)
>
>   若属性类型不同则会复制失败,需另行方法



Service.UserService


~~~java
public interface UserService {

    SysUser findUserById(Long userId);
}
~~~

UserServiceImpl


~~~java
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser findUserById(Long userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("码神之路");
        }
        return sysUser;
    }
}

~~~




~~~java
public interface TagsService {
    List<TagVo> findTagsByArticleId(Long id);
}
~~~




~~~java
@Service
public class TagsServiceImpl implements TagsService {
    @Autowired
    private TagMapper tagMapper;

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    @Override
    public List<TagVo> findTagsByArticleId(Long id) {
        List<Tag> tags = tagMapper.findTagsByArticleId(id);
        return copyList(tags);
    }
}
~~~



### 2.2.4 Dao

Mapper 继承`BaseMapper`接口后，无需编写 mapper.xml 文件，即可获得CRUD功能

~~~java
public interface ArticleMapper extends BaseMapper<Article> {
	
}

~~~



~~~java
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> findTagsByArticleId(Long articleId);
}
~~~

~~~java
public interface SysUserMapper extends BaseMapper<SysUser> {
}
~~~

blog-parent\blog-api\src\main\resources\com\hsxy\blog\dao\mapper\TagMapper.xml

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!--MyBatis配置文件-->
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.hsxy.blog.dao.mapper.TagMapper">

    <!--<sql id="all">
        id,avatar,tag_name as tagName
    </sql>-->
    <!--List<Tag>findTagsByArticleId(Long articleId);-->
    <select id="findTagsByArticleId" parameterType="long" resultType="com.hsxy.blog.dao.pojo.Tag">
        select id,avatar,tag_name as tagName from ms_tag
        where id in
        (select tag_id from ms_article_tag where article_id=#{articleId})

    </select>

</mapper>
~~~

>   ⚠️未标准填写很可能是导致500的罪魁祸首

### vo层 (View Object)[页面展示对象]

由请求参数可知应创建两个文件:

vo.params.PageParams

```java
@Data
public class PageParams {
	
	private int page = 1;
	
	private int pageSize = 10;
	
/*	private Long categoryId;
	
	private Long tagId;
	
	private String year;
	
	private String month;
	
	public String getMonth(){
		if (this.month != null && this.month.length() == 1){
			return "0"+this.month;
		}
		return this.month;
	}*/
}
```

vo.Result

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private boolean success;

    private Integer code;

    private String msg;

    private Object data;


    public static Result success(Object data) {
        return new Result(true,200,"success",data);
    }
    public static Result fail(Integer code, String msg) {
        return new Result(false,code,msg,null);
    }
}
```

>   类比机场项目font-termibal-device\font-termibal-device-common\src\main\java\com\szairport\fonttermibaldevice\common\core\domain\AjaxResult.java

vo.ArticleVo

```java
@Data
public class ArticleVo {

//    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private UserVo author;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;

}
```

vo.TagVo

```java
@Data
public class TagVo {

    private String id;

    private String tagName;

    private String avatar;
}
```



### Lombok注解快捷化

首先在pom.xml中添加依赖

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

- `@Data` 生成getter,setter ,toString等函数
- `@NoArgsConstructor` 生成无参构造函数
- `@AllArgsConstructor` /生成全参数构造函数



### 2.2.5 测试



# 3. 首页-最热标签

## 3.1 接口说明

接口url：/tags/hot

请求方式：GET

请求参数：无

返回数据：

~~~json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id":1,
            "tagName":"4444"
        }
    ]
}
~~~



## 3.2 编码

### 3.2.1 Controller

~~~java
package com.mszlu.blog.api;

import com.mszlu.blog.service.ArticleService;
import com.mszlu.blog.service.TagsService;
import com.mszlu.blog.vo.Archive;
import com.mszlu.blog.vo.ArticleVo;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @GetMapping("/hot")
    public Result listHotTags() {
        int limit = 6;
        List<TagVo> tagVoList = tagsService.hot(limit);
        return Result.success(tagVoList);
    }

}

~~~

~~~java
package com.mszlu.blog.vo;

import lombok.Data;

@Data
public class TagVo {

    private Long id;

    private String tagName;
}

~~~

### 3.2.2 Service

~~~java
package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.dao.TagMapper;
import com.mszlu.blog.dao.pojo.Tag;
import com.mszlu.blog.service.TagsService;
import com.mszlu.blog.vo.TagVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {
    @Autowired
    private TagMapper tagMapper;

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }
    @Override
    public List<TagVo> hot(int limit) {

        List<Long> hotsTagIds = tagMapper.findHotsTagIds(limit);
        if (CollectionUtils.isEmpty(hotsTagIds)){
            return Collections.emptyList();
        }
        List<Tag> tagList = tagMapper.findTagsByTagIds(hotsTagIds);
        return copyList(tagList);
    }

}

~~~

~~~java
package com.mszlu.blog.service;

import com.mszlu.blog.vo.TagVo;

import java.util.List;

public interface TagsService {

    List<TagVo> hot(int limit);

}

~~~

### 3.2.3 Dao

TagMapper.java

~~~java
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 查询最热的标签前n条
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
~~~

TagMapper.xml文件  
一定要了解所有表的业务逻辑，知道自己要返回什么值再进行操作  
我们通过findHotsTagIds这个方法在ms\_article\_tag表中找到了tag\_id  
![image-20220810145111630](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220810145111630.png)
然后多表查询，tag\_id就是ms\_tag表中的id我们在findHotsTagIds这个方法中找到了我们想要的前两条id，然后再利用动态mysql这个方法将id，tagName两个选项选择出来。多写多看基本上可以成为一个合格的crud工程师  
![image-20220810145117992](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220810145117992.png)

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!--MyBatis配置文件-->
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.mszlu.blog.dao.TagMapper">

    <!--    List<Long> findHotsTagIds(int limit);-->
<!--    parameterType="int"是自己加的因为不会自动生成我们输入的标签,#{limit}为我们自己传的参数-->
<!--  GROUP by 用法  https://www.runoob.com/sql/sql-groupby.html-->
<!--    sql语句的意思是在ms_article_tag表中查找tag_id，根据tag_id将其聚合在一起，再根据count（*）的数量以递减的顺序排序最后限制输出两条数据-->
    <select id="findHotsTagIds" parameterType="int" resultType="java.lang.Long">
        select tag_id from ms_article_tag GROUP BY tag_id ORDER BY count(*) DESC LIMIT #{limit}
    </select>
<!--    List<Tag> findTagsByTagIds(List<Long> tagIds);因为输入的类型是list所以parameterType的值是list-->
<!--    foreach用法 https://www.cnblogs.com/fnlingnzb-learner/p/10566452.html
            相当于for循环找传进来的一个id集合，每个id通过sql语句找到对应的tag对象-->
    <select id="findTagsByTagIds" parameterType="list" resultType="com.mszlu.blog.dao.pojo.Tag">
        select id,tag_name as tagName from ms_tag
        where  id in
        <foreach collection="tagIds" item="tagId" separator="," open="(" close=")">
            #{tagId}
        </foreach>
    </select>

</mapper>
~~~

### 3.2.4 测试

![image-20220810145235972](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220810145235972.png)

# 4.1. 统一异常处理

不管是controller层还是service，dao层，都有可能报异常，如果是预料中的异常，可以直接捕获处理，如果是意料之外的异常，需要统一进行处理，进行记录，并给用户提示相对比较友好的信息。

handler/AllExceptionHandler

```java
package com.mszlu.blog.handler;

import com.mszlu.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//对加了@Controller注解的方法进行拦截处理 Aop的实现
@ControllerAdvice
public class AllExceptionHandler {

    //进行异常处理，处理Exception.class的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody //返回json数据如果不加就返回页面了
    public Result doException(Exception ex) {
        //e.printStackTrace();是打印异常的堆栈信息，指明错误原因，
        // 其实当发生异常时，通常要处理异常，这是编程的好习惯，所以e.printStackTrace()可以方便你调试程序！
        ex.printStackTrace();
        return Result.fail(-999,"系统异常");

    }
}
```

![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-ba20f52c7c858ba236f5b514954e2ca1.png)

# 4.2. 首页-最热文章

在ms\_article表中的view\_counts表示浏览数量，越多表示越火热  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-8b153fd3895b56e7d72ab85c820968e5.png)

## 4.2.1 接口说明

接口url：/articles/hot

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明 |
| -------- | -------- | ---- |
|          |          |      |
|          |          |      |
|          |          |      |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "title": "springboot介绍以及入门案例",
        },
        {
            "id": 9,
            "title": "Vue.js 是什么",
        },
        {
            "id": 10,
            "title": "Element相关",
            
        }
    ]
}
```

## 4.2.2 Controller

ArticleController.java

```java
	/**
	 * @Description 首页 最热文章
	 * @Param []
	 * @return com.hsxy.blog.vo.Result
	 */
	@PostMapping("/hot")
	public Result hotArticle(){//无参删除
		int limit = 5;
		return articleService.hotArticle(limit);
	}
```

## 4.2.3 Service

src/main/java/com/mszlu/blog/service/ArticleService.java

```java
public interface ArticleService {
	/**
	 * @Description 分页查询 文章列表
	 * @Param [pageParams]
	 * @return com.hsxy.blog.vo.Result
	 */
	Result listArticle(PageParams pageParams);
	
	/**
	 * @Description 最热文章
	 * @Param [limit]
	 * @return com.hsxy.blog.vo.Result
	 */
	Result hotArticle(int limit);

}
```

src/main/java/com/mszlu/blog/service/impl/ArticleServiceImpl.java

```java
    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        //"limit"字待串后要加空格，不要忘记加空格，不然会把数据拼到一起
        queryWrapper.last("limit "+limit);
        //select id,title from article order by view_counts desc limt 5
        List<Article> articles = articleMapper.selectList(queryWrapper);

        //返回vo对象
        return Result.success(copyList(articles,false,false));
    }
```

>   如果不加空格,则会数据拼到一起报错
>
>   ![image-20220810161617382](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220810161617382.png)



## 4.2.4 测试

![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-61232d8b18a38a00c8921281aac31b11.png)

# 4.3. 首页-最新文章

和最热文章==非常类似==，一个是根据浏览量来选择，一个是根据最新创建时间来选择

## 4.3.1 接口说明

接口url：/articles/new

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明 |
| -------- | -------- | ---- |
|          |          |      |
|          |          |      |
|          |          |      |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 1,
            "title": "springboot介绍以及入门案例",
        },
        {
            "id": 9,
            "title": "Vue.js 是什么",
        },
        {
            "id": 10,
            "title": "Element相关",
            
        }
    ]
}
```

## 4.3.1 Controller

```java
 /**
     * 首页 最新文章
     * @return
     */
    @PostMapping("new")
    public Result newArticles(){
        int limit = 5;
        return articleService.newArticles(limit);
    }
```

## 4.3.2 Service

src/main/java/com/mszlu/blog/service/ArticleService.java

```java
    /**
     * 最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);
```

src/main/java/com/mszlu/blog/service/impl/ArticleServiceImpl.java

```java
	@Override
	public Result newArticles(int limit) {
		//查询条件
		LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
		//与sql 执行顺序有出入:7select->9order by->10limit
		queryWrapper.select(Article::getId,Article::getTitle);
		queryWrapper.orderByDesc(Article::getCreateDate);
		queryWrapper.last("limit "+limit);
		//select id,title from article order by creat_date desc limit 5
		List<Article> articles = articleMapper.selectList(queryWrapper);
		
		return Result.success(copyList(articles,false,false));
	}
```

# 4.4. 首页-文章归档

每一篇文章根据创建时间某年某月发表多少篇文章

## 4.4.1接口说明

接口url：/articles/listArchives

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明 |
| -------- | -------- | ---- |
|          |          |      |
|          |          |      |
|          |          |      |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": [
        {
            "year": "2021",
            "month": "6",
            "count": 2
        }
            
    ]
}
```

文章归档并不来源于数据库表,而是来源于sql语句:

```sql
select year(create_date) as year,month(create_date) as month,count(*) as count from ms_article group by year,month
```

但是  
p9 up主给的sql里面create\_date 为bigint 13位(==时间戳==)，直接year()不行，需要先转date型后year()。

```sql
select year(FROM_UNIXTIME(create_date/1000)) year,month(FROM_UNIXTIME(create_date/1000)) month, count(*) count from ms_article group by year,month;
```

这样才能查出来结果

## 4.4.1 Controller

src/main/java/com/mszlu/blog/controller/ArticleController.java

```java
  /**
     * 首页 文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }
```

下面这个是在src/main/java/com/mszlu/blog/service/impl/ArticleServiceImpl.java中使用的==返回值==



do 对象 数据库 查询出来的对象但是不需要持久化，由于do是关键字所以加了个s成为dos

com.mszlu.blog.dao.dos/Archives

```java
@Data
public class Archives {

    private Integer year;

    private Integer month;

    private Long count;
}
```

## 4.4.2 Service

src/main/java/com/mszlu/blog/service/ArticleService.java

```java
    /**
     * 文章归档
     * @return
     */
    Result listArchives();
```

src/main/java/com/mszlu/blog/service/impl/ArticleServiceImpl.java

```java
@Override
public Result listArchives() {
        /*
        文章归档
         */
    List<Archives> archivesList = articleMapper.listArchives();
    return Result.success(archivesList);
}
```

## 4.4.3 Dao

src/main/java/com/mszlu/blog/dao/mapper/ArticleMapper.java

```java
package com.mszlu.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.dao.pojo.Article;

import java.util.List;
import java.util.Map;

public interface ArticleMapper extends BaseMapper<Article> {

  List<Archives> listArchives();

}

```

src/main/resources/com/mszlu/blog/dao/mapper/ArticleMapper.xml

返回值返回给dos/Archives的属性中

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!--MyBatis配置文件-->
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--创立ArticleMapper.xml文件后再利用mybatisX一键生成select语句-->
<mapper namespace="com.mszlu.blog.dao.mapper.ArticleMapper">


    <select id="listArchives" resultType="com.mszlu.blog.dao.dos.Archives">
        select year(FROM_UNIXTIME(create_date/1000)) as year,month(FROM_UNIXTIME(create_date/1000)) as month, count(*) as count from ms_article
        group by year,month
    </select>

</mapper>
```

## 4.4.4 测试

![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-0ec605af3787cb7f46811931a2c22223.png)

注意：前端工程 需使用当天资料下的app

# 5.1. 登录

## 5.1.1 接口说明

接口url：/login

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明 |
| -------- | -------- | ---- |
| account  | string   | 账号 |
| password | string   | 密码 |
|          |          |      |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": "token"
}
```

## 5.1.2 JWT

登录使用JWT技术。

jwt 可以生成 一个加密的token，做为用户登录的令牌，当用户登录成功之后，发放给客户端。

请求需要登录的资源或者接口的时候，将token携带，后端验证token是否合法。

jwt 有三部分组成：A.B.C

A：Header，{“type”:“JWT”,“alg”:“HS256”} 固定

B：playload，存放信息，比如，用户id，过期时间等等，可以被解密，不能存放敏感信息

C: 签证，A和B加上秘钥 加密而成，只要秘钥不丢失，可以认为是安全的。

jwt 验证，主要就是验证C部分 是否合法。

导入依赖包

依赖包:

```xml
  <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt</artifactId>
        <version>0.9.1</version>
    </dependency>
```

src/main/java/com/mszlu/blog/utils/JWTUtils.java  
工具类:

```java
package com.mszlu.blog.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    private static final String jwtToken = "123456Mszlu!@#$$";

    public static String createToken(Long userId){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken
                .setClaims(claims) // body数据，要唯一，自行设置
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000));// 六十天的有效时间
        String token = jwtBuilder.compact();
        return token;
    }

    public static Map<String, Object> checkToken(String token){
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //测试Token
	/*public static void main(String[] args){
		String token = JWTUtils.createToken(100L);
		System.out.println(token);
		Map<String,Object> map = JWTUtils.checkToken(token);
		System.out.println(map.get("userId"));
	}*/
}
```

## 5.1.3 Controller

src/main/java/com/mszlu/blog/controller/LoginController.java

```java
package com.mszlu.blog.controller;

import com.mszlu.blog.service.LoginService;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    //@RequestBody,@ResponseBody的用法 和理解 https://blog.csdn.net/zhanglf02/article/details/78470219
    //浅谈@RequestMapping @ResponseBody 和 @RequestBody 注解的用法与区别
//https://blog.csdn.net/ff906317011/article/details/78552426?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-2.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-2.no_search_link
//@RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；而最常用的使用请求体传参的无疑是POST请求了，所以使用@RequestBody接收数据时，一般都用POST方式进行提交。

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        //登陆 验证用户 访问用户表
        return loginService.login(loginParam);

    }
}

```

构造LoginParam也就是我们的请求数据  
src/main/java/com/mszlu/blog/vo/params/LoginParam.java

```java
package com.mszlu.blog.vo.params;

import lombok.Data;

/**
 * @Author ljm
 * @Date 2021/10/12 20:06
 * @Version 1.0
 */
@Data
public class LoginParam {
    private String account;

    private String password;
}
```

## 5.1.4 Service

src/main/java/com/mszlu/blog/service/LoginService.java

```java
package com.mszlu.blog.service;

import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginParam;

public interface LoginService {
    /**
     * 登录
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);
}
```

导入依赖包 

```xml
        <!--redis-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <!--像Md5加密呀-->
        <dependency>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
        </dependency>
```

src/main/java/com/mszlu/blog/service/impl/LoginServiceImpl.java

```java
package com.mszlu.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.service.LoginService;
import com.mszlu.blog.service.SysUserService;
import com.mszlu.blog.utils.JWTUtils;
import com.mszlu.blog.vo.ErrorCode;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
//Spring 自动扫描组件// https://blog.csdn.net/u010002184/article/details/72870065
// @Component – 指示自动扫描组件。
//@Repository – 表示在持久层DAO组件。
//@Service – 表示在业务层服务组件。
//@Controller – 表示在表示层控制器组件。
@Service
public class LoginServiceImpl implements LoginService {

	//加密盐用于加密
    private static final String slat = "mszlu!@#";
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Result login(LoginParam loginParam) {
            /**
         * 1. 检查参数是否合法
         * 2. 根据用户名和密码去user表中查询 是否存在
         * 3. 如果不存在 登录失败
         * 4. 如果存在 ，使用jwt 生成token 返回给前端
         * 5. token放入redis当中，redis  token：user信息 设置过期时间（相比来说session会给服务器产生压力，这么做也是为了实现jwt的续签）
         *  (登录认证的时候 先认证token字符串是否合法，去redis认证是否存在)
         */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        String pwd = DigestUtils.md5Hex(password + slat);
        SysUser sysUser = sysUserService.findUser(account,pwd);
        if (sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        //登录成功，使用JWT生成token，返回token和redis中
        String token = JWTUtils.createToken(sysUser.getId());
        // JSON.toJSONString 用法    https://blog.csdn.net/antony9118/article/details/71023009
        //过期时间是一百天
        //redisTemplate用法  https://blog.csdn.net/lydms/article/details/105224210 
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),100, TimeUnit.DAYS);
        return Result.success(token);
    }

//生成我们想要的密码，放于数据库用于登陆
    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("admin"+slat));
    }
}

```

src/main/java/com/mszlu/blog/service/impl/SysUserServiceImpl.java

```java
   @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        //account id 头像 名称
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAdmin,SysUser::getNickname);
        //增加查询效率，只查询一条
        queryWrapper.last("limit 1");
//selectOne的坑https://www.guangmuhua.com/articleDetail/2625
        return sysUserMapper.selectOne(queryWrapper);
    }
```

src/main/java/com/mszlu/blog/service/SysUserService.java

```java
    SysUser findUser(String account, String pwd);
```

## 5.1.5 登录参数，redis配置，统一错误码

src/main/resources/application.properties

```properties
spring.redis.host=localhost
spring.redis.port=6379
```

src/main/java/com/mszlu/blog/vo/ErrorCode.java

```java
package com.mszlu.blog.vo;

public enum  ErrorCode {

    PARAMS_ERROR(10001,"参数有误"),
    ACCOUNT_PWD_NOT_EXIST(10002,"用户名或密码不存在"),
    NO_PERMISSION(70001,"无访问权限"),
    SESSION_TIME_OUT(90001,"会话超时"),
    NO_LOGIN(90002,"未登录"),;

    private int code;
    private String msg;

    ErrorCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
```

## 5.1.6 测试

使用postman测试，因为登录后，需要跳转页面，进行token认证，有接口未写，前端会出现问题。

token前端获取到之后，会存储在 storage中 h5 ，本地存储

postman  

![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAbGptXzk5,size_20,color_FFFFFF,t_70,g_se,x_16.png)

redis查看  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-16c9b869b2d4dfae0dcc5c922f83f572.png)

# 5.2. 获取用户信息

为什么实现完获取用户信息才能登陆测试呢？

token前端获取到之后，会存储在 storage中 h5 ，本地存储，存储好后，拿到storage中的token去获取用户信息，如果这个接口没实现，他就会一直请求陷入==死循环==(双端重启也没解决[估计是缓存了])

## 5.2.1 接口说明

得从http的head里面拿到这个参数，这样传参相对来说安全一些，  
返回是数据是我们用户相关的数据，id，账号、昵称和头像

接口url：/users/currentUser

请求方式：GET	(视频有误)

请求参数：

| 参数名称      | 参数类型 | 说明            |
| ------------- | -------- | --------------- |
| Authorization | string   | 头部信息(TOKEN) |
|               |          |                 |
|               |          |                 |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        "id":1,
        "account":"1",
        "nickaname":"1",
        "avatar":"ss"
    }
}
```

## 5.2.2 Controller

src/main/java/com/mszlu/blog/controller/UsersController.java

```java
package com.mszlu.blog.controller;

import com.mszlu.blog.service.SysUserService;
import com.mszlu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//浅谈@RequestMapping @ResponseBody 和 @RequestBody 注解的用法与区别
//https://blog.csdn.net/ff906317011/article/details/78552426?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-2.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-2.no_search_link
//@RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；而最常用的使用请求体传参的无疑是POST请求了，所以使用@RequestBody接收数据时，一般都用POST方式进行提交。
//@RequestHeader获取头部信息
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){

        return sysUserService.findUserByToken(token);
    }
}

```

## 5.2.3 Service

src/main/java/com/mszlu/blog/service/SysUserService.java

```java
    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
 Result findUserByToken(String token);
```

src/main/java/com/mszlu/blog/service/impl/SysUserServiceImpl.java

```java
 	    //这个爆红只需要在对应的mapper上加上@Repository,让spring识别到即可解决爆红的问题
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private LoginService loginService;
    @Override
    public Result findUserByToken(String token) {
        /**
         * 1、token合法性校验
         * 是否为空 ，解析是否成功，redis是否存在
         * 2、如果校验失败，返回错误
         *3、如果成功，返回对应结果 LoginUserVo
         */

        //去loginservice中去校验token
        SysUser sysUser = loginService.checkToken(token);
        if(sysUser == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());
        return Result.success(loginUserVo);

    }
```

src/main/java/com/mszlu/blog/service/LoginService.java

```java
package com.mszlu.blog.service;

import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginParam;

/**
 * @Author ljm
 * @Date 2021/10/12 20:04
 * @Version 1.0
 */
public interface LoginService {
    /**
     * 登陆功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);
}

```

src/main/java/com/mszlu/blog/service/impl/LoginServiceImpl.java

```java
    @Override
    public SysUser checkToken(String token) {
        //token为空返回null
        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        //解析失败
        if(stringObjectMap ==null){
            return null;
        }
        //如果成功
        String userJson =  redisTemplate.opsForValue().get("TOKEN_"+token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }
        //解析回sysUser对象
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }
```

## 5.2.4 LoginUserVo

src/main/java/com/mszlu/blog/vo/LoginUserVo.java

```java
package com.mszlu.blog.vo;

import lombok.Data;

@Data
public class LoginUserVo {
	//与页面交互

    private Long id;

    private String account;

    private String nickname;

    private String avatar;
}
```

## 5.2.5 测试

# 5.3. 退出登录

登陆一个的对token进行认证，一个是在redis中进行注册，token字符串没法更改掉，只能由前端进行清除，后端能做的就是把redis进行清除

## 5.3.1 接口说明

接口url：/logout

请求方式：GET

请求参数：

| 参数名称      | 参数类型 | 说明            |
| ------------- | -------- | --------------- |
| Authorization | string   | 头部信息(TOKEN) |
|               |          |                 |
|               |          |                 |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```

## 5.3.2 Controller

src/main/java/com/mszlu/blog/controller/LogoutController.java

```java
@RestController
@RequestMapping("logout")
public class LogoutController {

    @Autowired
    private LoginService loginService;
    
	/**
	 * @Description 退出与登录不同,使用的是GET --> 效仿users/currentUser
	 * @Param [loginParam]
	 * @return com.hsxy.blog.vo.Result
	 */
//获取头部信息这样一个参数
    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token){
        return loginService.logout(token);
    }
}
```

## 5.3.3 Service

src/main/java/com/mszlu/blog/service/LoginService.java

```java
    /**
     * 退出登陆
     * @param token
     * @return
     */
    Result logout(String token);
```

src/main/java/com/mszlu/blog/service/impl/LoginServiceImpl.java

```java
  @Override
    public Result logout(String token) {
    //后端直接删除redis中的token
        redisTemplate.delete("TOKEN_"+token);
        return Result.success(null);
    }
```

## 5.3.4 测试

# 6.1. 注册

## 6.1.1 接口说明

接口url：/register

请求方式：POST  
post传参意味着请求参数是按照json方式传  
具体可以看这篇  
[post和@Requestbody](https://www.cnblogs.com/ivan5277/articles/11626198.html)

请求参数：

| 参数名称 | 参数类型 | 说明 |
| -------- | -------- | ---- |
| account  | string   | 账号 |
| password | string   | 密码 |
| nickname | string   | 昵称 |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": "token"
}
```

## 6.1.2 Controller

src/main/java/com/mszlu/blog/controller/RegisterController.java

```java
package com.mszlu.blog.controller;

import com.mszlu.blog.service.LoginService;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;
    
//后端传递多个参数，前端只选用其需要的参数就可以了
    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
    //sso 单点登录，后期如果把登录注册功能 提出去（单独的服务，可以独立提供接口服务）
        return loginService.register(loginParam);
    }
}
```

参数LoginParam中 添加新的参数nickname。  
src/main/java/com/mszlu/blog/vo/params/LoginParam.java

```java
package com.mszlu.blog.vo.params;

import lombok.Data;

@Data
public class LoginParam {

    private String account;

    private String password;

    private String nickname;
}
```

## 6.1.3 Service

src/main/java/com/mszlu/blog/service/impl/LoginServiceImpl.java

```java
 @Override
    public Result register(LoginParam loginParam) {
            /**
         * 1. 判断参数 是否合法
         * 2. 判断账户是否存在，存在 返回账户已经被注册
         * 3. 不存在，注册用户
         * 4. 生成token
         * 5. 存入redis 并返回
         * 6. 注意 加上事务，一旦中间的任何过程出现问题，注册的用户 需要回滚
         */
         String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if (StringUtils.isBlank(account)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(nickname)
        ){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser = this.sysUserService.findUserByAccount(account);
        if (sysUser != null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);

        //token
        String token = JWTUtils.createToken(sysUser.getId());

        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

```

在ErrorCode.java中添加一条  
src/main/java/com/mszlu/blog/vo/ErrorCode.java

```java
ACCOUNT_EXIST(10004,"账号已存在"),
```

sysUserService中save 和findUserByAccount方法没有需要构造接口和实现类  
src/main/java/com/mszlu/blog/service/SysUserService.java

```java
    /**
     * 根据账户查找用户
     * @param account
     * @return
     */
 SysUser findUserByAccount(String account);
    /**
     * 保存用户
     * @param sysUser
     */
    void save(SysUser sysUser);
```

src/main/java/com/mszlu/blog/service/impl/SysUserServiceImpl.java

```java
  	@Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        //确保只能查询一条
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser sysUser) {
         //保存用户这 id会自动生成
        //这个地方 默认生成的id是 分布式id 雪花算法
        //mybatis-plus
        this.sysUserMapper.insert(sysUser);
    }
```

## 6.1.4 加事务

出现错误就进行回滚防止添加异常  
增加@Transactional注解  
src/main/java/com/mszlu/blog/service/impl/LoginServiceImpl.java

```java
@Service
@Transactional
public class LoginServiceImpl implements LoginService {}
```

当然 一般建议将事务注解@Transactional加在 接口上，通用一些。  
src/main/java/com/mszlu/blog/service/LoginService.java

```java
@Transactional
public interface LoginService {
    /**
     * 登陆功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    /**
     * 退出登陆
     * @param token
     * @return
     */
    Result logout(String token);


    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);

}

```

测试的时候 可以将redis 停掉，那么redis连接异常后，新添加的用户 应该执行回滚操作。

## 6.1.5 测试

# 6.2. 登录拦截器

每次访问需要登录的资源的时候，都需要在代码中进行判断，一旦登录的逻辑有所改变，代码都得进行变动，非常不合适。

那么可不可以统一进行登录判断呢？

springMVC中拦截器

可以，使用拦截器，进行登录拦截，如果遇到需要登录才能访问的接口，如果未登录，拦截器直接返回，并跳转登录页面。  
[Javas三大器：过滤器-监听器-拦截器](https://www.jianshu.com/p/d25349f0ab02)

## 6.2.1 拦截器实现

src/main/java/com/mszlu/blog/handler/LoginInterceptor.java

```java
/**
 * @name LoginInterceptor
 * @Description 登录拦截器
 * @author WU
 * @Date 2022/8/12 9:18
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private LoginService loginService;
	
	/**
	 * 该方法是在执行执行servlet的 service方法之前执行的
	 * 即在进入controller之前调用
	 * @return 如果返回true表示继续执行下一个拦截器的PreHandle方法；如果没有拦截器了,则执行controller
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//在执行controller方法(Handler)之前进行执行
		/**
		 * 1. 需要判断 请求的接口路径 是否为 HandlerMethod (controller方法)
		 * 2. 判断 token是否为空，如果为空 未登录
		 * 3. 如果token 不为空，登录验证 loginService checkToken
		 * 4. 如果认证成功 放行即可
		 */
		//1. 如果不是我们的方法进行放行
		if (!(handler instanceof HandlerMethod)){
			//handler 可能是 RequestResourceHandler springboot 程序 访问静态资源 默认去classpath下的static目录去查询
			return true;
		}
		String token = request.getHeader("Authorization");//users/currentUser
		
		log.info("=================request start===========================");
		String requestURI = request.getRequestURI();
		log.info("request uri:{}",requestURI);
		log.info("request method:{}",request.getMethod());
		log.info("token:{}", token);
		log.info("=================request end===========================");
		//未登录
		if(StringUtils.isBlank(token)){
			Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
			//设置浏览器识别返回的是json
			response.setContentType("application/json;charset=utf-8");
			//https://www.cnblogs.com/qlqwjy/p/7455706.html response.getWriter().print()
			//SON.toJSONString则是将对象转化为Json字符串
			response.getWriter().print(JSON.toJSONString(result));
			return false;
		}
		//Token不为空,认证
		SysUser sysUser = loginService.checkToken(token);
		if (sysUser == null){
			//同上
			Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(JSON.toJSONString(result));
			return false;
		}
		//是登录状态，放行
		//登录验证成功，放行
		//我希望在controller中 直接获取用户的信息 怎么获取?
		
		return true;
	}
}
```

## 6.2.2 使拦截器生效

src/main/java/com/mszlu/blog/config/WebMVCConfig.java

```java
package com.mszlu.blog.config;

import com.mszlu.blog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }

	/**
	 * @Description 增加拦截器
	 * @Param [registry]
	 * @return void
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//假设拦截test接口后续实际遇到拦截的接口是时，再配置真正的拦截接口
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/test");
				//.excludePathPatterns("/login");
	}
}

```

## 6.2.3 测试

src/main/java/com/mszlu/blog/controller/TestController.java

```java
package com.mszlu.blog.controller;

import com.mszlu.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping
    public Result test(){
        return Result.success(null);
    }
}

```

src/main/java/com/mszlu/blog/handler/LoginInterceptor.java返回true进行放行，test这个接口就可以正常访问了

![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-32d6957b613126b470cf1b67ef335cc0.png)

如果在WebMVCConfig将拦截器修改为

```java
	/**
	 * @Description 增加拦截器
	 * @Param [registry]
	 * @return void
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//假设拦截test接口后续实际遇到拦截的接口是时，再配置真正的拦截接口
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns("/login");
	}
}
```

测试结果为只能进行登录操作

>   ⚠️登录后也无法进行其他操作

![image-20220812105621199](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220812105621199.png)



# 6.3. ThreadLocal保存用户信息

redis中只放了token我们希望直接获取用户信息  
好处和如何使用的  
[使用ThreadLocal保存用户登录信息](https://blog.csdn.net/qq_42804736/article/details/115064701)  
使用ThreadLocal替代Session完成保存用户登录信息功能

使用ThreadLocal替代Session的好处：

>   可以在同一线程中很方便的获取用户信息，不需要频繁的传递session对象。

具体实现流程：

   在登录业务代码中，当用户登录成功时，生成一个==登录凭证==存储到redis中，
   将凭证中的字符串保存在cookie中返回给客户端。
   使用一个==拦截器==拦截请求，从cookie中获取凭证字符串与redis中的凭证进行匹配，获取用户信息，
   将用户信息==存储到==`ThreadLocal`中，在本次请求中持有用户信息，即可在后续操作中使用到用户信息。

相关问题  
[Session原理](https://blog.csdn.net/weixin_42217767/article/details/92760353)  
[COOKIE和SESSION有什么区别？](https://www.zhihu.com/question/19786827)

src/main/java/com/mszlu/blog/utils/UserThreadLocal.java

```java
package com.mszlu.blog.utils;

import com.mszlu.blog.dao.pojo.SysUser;

public class UserThreadLocal {

    private UserThreadLocal(){}
    //线程变量隔离
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser){
        LOCAL.set(sysUser);
    }
    public static SysUser get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}

```

src/main/java/com/mszlu/blog/handler/LoginInterceptor.java

```java
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ...
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       //如果不删除 ThreadLocal中用完的信息 会有内存泄漏的风险
        UserThreadLocal.remove();
    }
}
```

src/main/java/com/mszlu/blog/controller/TestController.java

```java
package com.mszlu.blog.controller;

import com.mszlu.blog.dao.pojo.SysUser;
import com.mszlu.blog.utils.UserThreadLocal;
import com.mszlu.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
//        SysUser
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}

```

# 7.1. ThreadLocal内存泄漏

[ThreadLocal原理及内存泄露预防](https://blog.csdn.net/puppylpg/article/details/80433271)  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-856bbcc9e5b69cbe609f1bba26ae0bbf.png)

**实线代表强引用,虚线代表弱引用**

每一个Thread维护一个ThreadLocalMap, ==key为使用弱引用的ThreadLocal实例==，value为线程变量的副本。

**强引用**，使用最普遍的引用，一个对象具有强引用，不会被垃圾回收器回收。当内存空间不足，Java虚拟机宁愿抛出OutOfMemoryError错误，使程序异常终止，也不回收这种对象。

**如果想取消强引用和某个对象之间的关联，可以显式地将引用赋值为null，这样可以使JVM在合适的时间就会回收该对象。**

**弱引用**，JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象。在java中，用java.lang.ref.WeakReference类来表示。

![引用分类](JVM#2.3 再谈引用)

# 7.2. 文章详情

## 7.2.1 接口说明

接口url：/articles/view/{id}

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明               |
| -------- | -------- | ------------------ |
| id       | long     | 文章id（路径参数） |
|          |          |                    |
|          |          |                    |

返回数据：

```json
{success: true, code: 200, msg: "success",…}
code: 200
data: {id: "1405916999732707330", title: "SpringBoot入门案例", summary: "springboot入门案例", commentCounts: 0,…}
msg: "success"
success: true
```

## 7.2.2 涉及到的表

内容表

content存放makedown格式的信息  
content\_html存放html格式的信息

```sql
CREATE TABLE `blog`.`ms_article_body`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `content_html` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `article_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `article_id`(`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
```

src/main/java/com/mszlu/blog/dao/pojo/ArticleBody.java

```java
package com.mszlu.blog.dao.pojo;

import lombok.Data;
//内容表
@Data
public class ArticleBody {

    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}
```

类别表  
avata分类图标路径  
category\_name图标分类的名称  
description分类的描述  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-e98aef6aba457cdb6d5bf5bce1215938.png)

```sql
CREATE TABLE `blog`.`ms_category`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
```

src/main/java/com/mszlu/blog/dao/pojo/Category.java

```java
package com.mszlu.blog.dao.pojo;

import lombok.Data;
//类别表
@Data
public class Category {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}

```

## 7.2.3 Controller

src/main/java/com/mszlu/blog/controller/ArticleController.java

```java
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId){
        return articleService.findArticleById(articleId);
    }
```

## 7.2.4 Service

src/main/java/com/mszlu/blog/service/ArticleService.java

```java
    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    Result findArticleById(Long articleId);
```

文章表里面只有tiltle以及一些简介  
ms\_article 中body\_id对应第二张表ms\_article\_body上的id  
ms\_category会映射到ms\_article 中的category\_id  
需要做一些相对的关联查询  
![表一](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-7e0718f769033e6283f00bd67ed8c6e7.png)
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/e9f889a0f76546c8b0c03cabd136bb61.png)
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAbGptXzk5,size_20,color_FFFFFF,t_70,g_se,x_16-16602909877266.png)
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-631eeb00d0d1f7e959fd5279fa754a1a.png)

src/main/java/com/mszlu/blog/service/impl/ArticleServiceImpl.java

```java
    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1. 根据id查询 文章信息
         * 2. 根据bodyId和categoryid 去做关联查询
         */
        Article article = this.articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true, true,true,true);
        //查看完文章了，新增阅读数，有没有问题呢？
        //查看完文章之后，本应该直接返回数据了，这时候做了一个更新操作，更新时加写锁，阻塞其他的读操作，性能就会比较低
        // 更新 增加了此次接口的 耗时 如果一旦更新出问题，不能影响 查看文章的操作
        //线程池  可以把更新操作 扔到线程池中去执行，和主线程就不相关了
       //threadService.updateArticleViewCount(articleMapper,article);
        return Result.success(articleVo);
    }
```

src/main/java/com/mszlu/blog/vo/ArticleVo.java

```java
@Data
public class ArticleVo {
	//private String id; --> 导致url显示http://localhost:8080/#/view/null
	//@JsonSerialize(using = ToStringSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING) //<-- 前端JSON转化时精度发生了丢失,需要JSON序列化时转换回String
	private Long id;
	
	private String title;
	
	private String summary;
	
	private Integer commentCounts;
	
	private Integer viewCounts;
	
	private Integer weight;
	/**
	 * 创建时间
	 * ##!与Article不同,Article为Long类型
	 */
	private String createDate;
	private String author;
	//private UserVo author;
	
	private ArticleBodyVo body;
	
	private List<TagVo> tags;
	
	private CategoryVo category;
	
}
```

>   原因:java中long数据能表示的范围比js中number大,在跟前端交互时，这样也就意味着部分数值在js中存不下(变成不准确的值)。
>
>   首先`private String id;` --> 导致url显示http://localhost:8080/#/view/null
>
>   然后更改为`Long`类型,但是会有精度损失
>
>   ![image-20220812182216979](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220812182216979.png)
>
>   给Vo实体类加上`@JsonFormat(shape = JsonFormat.Shape.STRING)`然后再启动就没问题了，取到了全值。
>
>   >   **@JsonFormat 用来表示json序列化的一种格式或者类型，shape表示序列化后的一种类型**

src/main/java/com/mszlu/blog/vo/ArticleBodyVo.java

```java
package com.mszlu.blog.vo;

import lombok.Data;

@Data
public class ArticleBodyVo {

//内容
    private String content;
}

```

src/main/java/com/mszlu/blog/vo/CategoryVo.java

```java
package com.mszlu.blog.vo;

import lombok.Data;

@Data
public class CategoryVo {

//id，图标路径，图标名称
    private Long id;

    private String avatar;

    private String categoryName;
}

```

ArticleVo中的属性填充：  
src/main/java/com/mszlu/blog/service/impl/ArticleServiceImpl.java

```java
//方法重载，方法名相同参数数量不同
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,false,false));
        }
        return articleVoList;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor,boolean isBody) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,false));
        }
        return articleVoList;
    }
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }

    @Autowired
    private CategoryService categoryService;
//带body信息，带category信息
    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        BeanUtils.copyProperties(article,articleVo);
        //时间没法copy因为是long型
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有的接口 都需要标签 ，作者信息
        if (isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    @Autowired
    private CategoryService categoryService;

    private CategoryVo findCategory(Long categoryId) {
        return categoryService.findCategoryById(categoryId);
    }
    //构建ArticleBodyMapper
    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

```

src/main/java/com/mszlu/blog/dao/mapper/ArticleBodyMapper.java

```java
package com.mszlu.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.dao.pojo.ArticleBody;

public interface ArticleBodyMapper extends BaseMapper<ArticleBody> {
}

```

src/main/java/com/mszlu/blog/dao/mapper/CategoryMapper.java

```java
package com.mszlu.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.dao.pojo.Category;

public interface CategoryMapper extends BaseMapper<Category> {
}

```

src/main/java/com/mszlu/blog/service/CategoryService.java

```java
package com.mszlu.blog.service;

import com.mszlu.blog.vo.CategoryVo;

public interface CategoryService {

    CategoryVo findCategoryById(Long id);
}

```

src/main/java/com/mszlu/blog/service/impl/CategoryServiceImpl.java

```java
package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.dao.mapper.CategoryMapper;
import com.mszlu.blog.dao.pojo.Category;
import com.mszlu.blog.service.CategoryService;
import com.mszlu.blog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//注入spring 
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Long id){
        Category category = categoryMapper.selectById(id);
        CategoryVo categoryVo = new CategoryVo();
        //因为category,categoryVo属性一样所以可以使用 BeanUtils.copyProperties
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}

```

## 7.2.5 测试

![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-4b471fd9b8f038c12e79891207c7ab93.png)

# 7.3. 使用线程池 更新阅读次数

//查看完文章了，新增阅读数，有没有问题呢？  
//查看完文章之后，本应该直接返回数据了，这时候做了一个更新操作，更新时加写锁，阻塞其他的读操作，性能就会比较低（没办法解决，增加阅读数必然要加锁）  
//更新增加了此次接口的耗时（考虑减少耗时）如果一旦更新出问题，不能影响查看操作  
想到了一个技术 线程池  
可以把更新操作扔到 线程池中去执行和主线程就不相关了

![乐观锁与悲观锁](MySQL面试#乐观锁与悲观锁⭕)  
[什么是乐观锁，什么是悲观锁](https://www.jianshu.com/p/d2ac26ca6525)  
[CAS原理分析](https://blog.csdn.net/qq_37113604/article/details/81582784)

## 7.3.1 线程池配置

做一个线程池的配置来开启线程池  
src/main/java/com/mszlu/blog/config/ThreadPoolConfig.java

```java
package com.mszlu.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

//https://www.jianshu.com/p/0b8443b1adc9   关于@Configuration和@Bean的用法和理解
@Configuration
@EnableAsync //开启多线程
public class ThreadPoolConfig {

    @Bean("taskExecutor")
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(5);
        // 设置最大线程数
        executor.setMaxPoolSize(20);
        //配置队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("码神之路博客项目");
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();
        return executor;
    }
}

```

## 7.3.1 使用

src/main/java/com/mszlu/blog/service/ThreadService.java

```java
package com.mszlu.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.dao.mapper.ArticleMapper;
import com.mszlu.blog.dao.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {
    //期望此操作在线程池执行不会影响原有主线程
    //这里线程池不了解可以去看JUC并发编程
    @Async("taskExcutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {

        Integer viewCounts = article.getViewCounts();
        Article articleupdate = new Article();
        articleupdate.setViewCounts(viewCounts+1);
        LambdaQueryWrapper<Article> updatewrapper = new LambdaQueryWrapper<>();
        //根据id更新
        updatewrapper.eq(Article::getId ,article.getId());
        //设置一个为了在多线程的环境下线程安全
        //改之前再确认这个值有没有被其他线程抢先修改，类似于CAS操作 cas加自旋，加个循环就是cas
        //查看次数只增不减,可看做乐观锁的时间戳（timestamp）
        updatewrapper.eq(Article ::getViewCounts,viewCounts );
        // update article set view_count=100 where view_count=99 and id =111
        //实体类加更新条件
        articleMapper.update(articleupdate,updatewrapper);
        try {
            Thread.sleep(5000);
            System.out.println("更新完成了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
```

src/main/java/com/mszlu/blog/service/impl/ArticleServiceImpl.java

```java
    @Autowired
    private ThreadService threadService;

    @Override
    public ArticleVo findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        //线程池
        threadService.updateViewCount(articleMapper,article);
        return copy(article,true,true,true,true);
    }

```

## 7.3.3 测试

睡眠 ThredService中的方法 5秒，不会影响主线程的使用，即文章详情会很快的显示出来，不受影响

# Bug修正

之前Article中的commentCounts，viewCounts，weight 字段为int，会造成更新阅读次数的时候，将其余两个字段设为初始值0  
mybatisplus在更新文章阅读次数的时候虽然只设立了articleUpdate.setviewsCounts(viewCounts+1),  
但是int默认基本数据类型为0，  
mybatisplus但凡不是null就会生成到sql语句中进行更新。会出现  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-24ffabc7822d654412eb0657d8a5fc99.png)理想中应该是只有views\_counts改变但是因为mybatisplus规则所以会出现这个现象  
所以将int改为Integer就不会出现这个问题。  
src/main/java/com/mszlu/blog/dao/pojo/Article.java

```java
package com.mszlu.blog.dao.pojo;

import lombok.Data;

@Data
public class Article {

    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight;


    /**
     * 创建时间
     */
    private Long createDate;
}

```

# 8.1. 评论列表

评论表  
id评论id  
content评论内容  
create\_date评论时间  
article\_id评论文章  
author\_id谁评论的  
parent\_id盖楼功能对评论的评论进行回复  
to\_uid给谁评论  
level评论的是第几层（1级表示最上层的评论，2表示对评论的评论）

```sql
CREATE TABLE `blog`.`ms_comment`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_date` bigint(0) NOT NULL,
  `article_id` int(0) NOT NULL,
  `author_id` bigint(0) NOT NULL,
  `parent_id` bigint(0) NOT NULL,
  `to_uid` bigint(0) NOT NULL,
  `level` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `article_id`(`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
```

src/main/java/com/mszlu/blog/dao/pojo/Comment.java

```java
package com.mszlu.blog.dao.pojo;

import lombok.Data;

@Data
public class Comment {

    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;
}

```

## 8.1.1 接口说明

接口url：/comments/article/{id}

请求方式：GET

请求参数：

| 参数名称 | 参数类型 | 说明               |
| -------- | -------- | ------------------ |
| id       | long     | 文章id（路径参数） |
|          |          |                    |
|          |          |                    |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 53,
            "author": {
                "nickname": "李四",
                "avatar": "http://localhost:8080/static/img/logo.b3a48c0.png",
                "id": 1
            },
            "content": "写的好",
            "childrens": [
                {
                    "id": 54,
                    "author": {
                        "nickname": "李四",
                        "avatar": "http://localhost:8080/static/img/logo.b3a48c0.png",
                        "id": 1
                    },
                    "content": "111",
                    "childrens": [],
                    "createDate": "1973-11-26 08:52",
                    "level": 2,
                    "toUser": {
                        "nickname": "李四",
                        "avatar": "http://localhost:8080/static/img/logo.b3a48c0.png",
                        "id": 1
                    }
                }
            ],
            "createDate": "1973-11-27 09:53",
            "level": 1,
            "toUser": null
        }
    ]
}
```

代码结构  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-6405669104455f68b5e9e00868e4c36d.png)

## 8.1.2 Controller

src/main/java/com/mszlu/blog/controller/CommentsController.java

```java
package com.mszlu.blog.controller;

import com.mszlu.blog.service.CommentsService;
import com.mszlu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long articleId){

        return commentsService.commentsByArticleId(articleId);

    }
}

```

## 8.1.3 Service

src/main/java/com/mszlu/blog/service/CommentsService.java

```java
package com.mszlu.blog.service;

import com.mszlu.blog.vo.Result;

public interface CommentsService {

    /**
     * 根据文章id查询所有的评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);
}

```

src/main/java/com/mszlu/blog/service/impl/CommentsServiceImpl.java

```java
package com.mszlu.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mszlu.blog.dao.mapper.CommentMapper;
import com.mszlu.blog.dao.pojo.Comment;
import com.mszlu.blog.service.CommentsService;
import com.mszlu.blog.service.SysUserService;
import com.mszlu.blog.vo.CommentVo;
import com.mszlu.blog.vo.Result;
import com.mszlu.blog.vo.UserVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SysUserService sysUserService;
    @Override
	public Result commentsByArticleId(Long articleId) {
		/**
		 * 1. 根据文章id 查询 评论列表 从 comment 表中查询
		 * 2. 根据作者的id 查询作者的信息
		 * 3. 判断 如果 level = 1 要去查询它有没有子评论
		 * 4. 如果有 根据评论id 进行查询 （parent_id）
		 */
		LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
		//根据文章id进行查询
		queryWrapper.eq(Comment::getArticleId, articleId );
		//根据层级关系进行查询
		queryWrapper.eq(Comment::getLevel,1 );
		List<Comment> comments = commentMapper.selectList(queryWrapper);
		Collections.reverse(comments);//更新的评论在下方,颠倒顺序,调整楼数
		List<CommentVo> commentVoList = copyList(comments);
		return Result.success(commentVoList);
	}
    //对list表中的comment进行判断
    public List<CommentVo> copyList(List<Comment> commentList){
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        // 相同属性copy
        BeanUtils.copyProperties(comment,commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        //作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = this.sysUserService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        //子评论
        Integer level = comment.getLevel();
        if (1 == level){
            Long id = comment.getId();
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildrens(commentVoList);
        }
        //to User 给谁评论
        if (level > 1){
            Long toUid = comment.getToUid();
            UserVo toUserVo = this.sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId,id);
        queryWrapper.eq(Comment::getLevel,2);
        List<Comment> comments = this.commentMapper.selectList(queryWrapper);
        return copyList(comments);
    }

}
```

返回的数据：  
src/main/java/com/mszlu/blog/vo/CommentVo.java

```java
package com.mszlu.blog.vo;

import com.mszlu.blog.dao.pojo.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class CommentVo  {

    private Long id;

    private UserVo author;

    private String content;

    private List<CommentVo> childrens;

    private String createDate;

    private Integer level;

    private UserVo toUser;
}
```

src/main/java/com/mszlu/blog/vo/UserVo.java

```java
package com.mszlu.blog.vo;

import lombok.Data;

@Data
public class UserVo {

    private String nickname;

    private String avatar;

    private Long id;
}
```

在SysUserService中提供 查询用户信息的服务：  
src/main/java/com/mszlu/blog/service/SysUserService.java

```java
  UserVo findUserVoById(Long id);
```

src/main/java/com/mszlu/blog/service/impl/SysUserServiceImpl.java

```java
    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("码神之路");
        }
        UserVo userVo = new UserVo();
        userVo.setAvatar(sysUser.getAvatar());
        userVo.setNickname(sysUser.getNickname());
        userVo.setId(sysUser.getId());
        return userVo;
    }
```

# 8.2. 评论

## 8.2.1 接口说明

接口url：/comments/create/change

请求方式：POST

请求参数：

| 参数名称  | 参数类型 | 说明           |
| --------- | -------- | -------------- |
| articleId | long     | 文章id         |
| content   | string   | 评论内容       |
| parent    | long     | 父评论id       |
| toUserId  | long     | 被评论的用户id |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": null
}
```

## 8.2.2 加入到登录拦截器中

src/main/java/com/mszlu/blog/config/WebMVCConfig.java

```java
@Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截test接口，后续实际遇到需要拦截的接口时，在配置为真正的拦截接口
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test").addPathPatterns("/comments/create/change");
    }
```

## 8.2.3 Controller

代码结构  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-fe87229199787cb74478e2b1b2e6dd5d.png)

构建评论参数对象：  
src/main/java/com/mszlu/blog/vo/params/CommentParam.java

```java
package com.mszlu.blog.vo.params;

import lombok.Data;

@Data
public class CommentParam {

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}

```

src/main/java/com/mszlu/blog/controller/CommentsController.java

```java

    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentsService.comment(commentParam);
    }
```

## 8.2.4 Service

src/main/java/com/mszlu/blog/service/CommentsService.java

```java
    Result comment(CommentParam commentParam);
```

src/main/java/com/mszlu/blog/service/impl/CommentsServiceImpl.java

```java
 @Override
    public Result comment(CommentParam commentParam) {
    //拿到当前用户
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        //如果是空，parent就是0
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        this.commentMapper.insert(comment);
        return Result.success(null);
    }
```

```java
  //防止前端 精度损失 把id转为string
// 分布式id 比较长，传到前端 会有精度损失，必须转为string类型 进行传输，就不会有问题了
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
```

# 写文章

写文章需要 三个接口：

1.  获取所有文章类别

2.  获取所有标签

3.  发布文章

# 9.1. 所有文章分类

## 9.1.1 接口说明

接口url：/categorys

请求方式：GET

请求参数：

| 参数名称 | 参数类型 | 说明 |
| -------- | -------- | ---- |
|          |          |      |
|          |          |      |
|          |          |      |

返回数据：

```json
{
    "success":true,
 	"code":200,
    "msg":"success",
    "data":
    [
        {"id":1,"avatar":"/category/front.png","categoryName":"前端"},	
        {"id":2,"avatar":"/category/back.png","categoryName":"后端"},
        {"id":3,"avatar":"/category/lift.jpg","categoryName":"生活"},
        {"id":4,"avatar":"/category/database.png","categoryName":"数据库"},
        {"id":5,"avatar":"/category/language.png","categoryName":"编程语言"}
    ]
}
```

## 9.1.2 Controller

src/main/java/com/mszlu/blog/controller/CategoryController.java

```java
package com.mszlu.blog.controller;

import com.mszlu.blog.service.CategoryService;
import com.mszlu.blog.vo.CategoryVo;
import com.mszlu.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result listCategory() {
        return categoryService.findAll();
    }
}

```

## 9.1.3 Service

src/main/java/com/mszlu/blog/service/impl/CategoryServiceImpl.java

```java
@Override
	public Result findAll() {
		List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());//因为空参,所以new一个空QueryWrapper
		//页面交互的对象
		return Result.success(copyList(categories));
	}
	
	public CategoryVo copy(Category category){
		CategoryVo categoryVo = new CategoryVo();
		BeanUtils.copyProperties(category,categoryVo);
		//id不一致要重新设立
		//categoryVo.setId(String.valueOf(category.getId()));//没看出异常
		return categoryVo;
	}
	public List<CategoryVo> copyList(List<Category> categoryList){
		List<CategoryVo> categoryVoList = new ArrayList<>();
		for (Category category : categoryList) {
			categoryVoList.add(copy(category));
		}
		return categoryVoList;
	}
```

![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-09f9e07e283bcf6be5d7f020e906a170.png)

# 9.2. 所有文章标签

## 9.2.1 接口说明

接口url：/tags

请求方式：GET

请求参数：

| 参数名称 | 参数类型 | 说明 |
| -------- | -------- | ---- |
|          |          |      |
|          |          |      |
|          |          |      |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": [
        {
            "id": 5,
            "tagName": "springboot"
        },
        {
            "id": 6,
            "tagName": "spring"
        },
        {
            "id": 7,
            "tagName": "springmvc"
        },
        {
            "id": 8,
            "tagName": "11"
        }
    ]
}
```

## 9.2.2 Controller

src/main/java/com/mszlu/blog/controller/TagsController.java

```java
    @Autowired
    private TagService tagService;
 	@GetMapping
    public Result findAll(){
        /**
     * 查询所有的文章标签
     * @return
     */
        return tagService.findAll();
    }
```

## 9.2.3 Service

src/main/java/com/mszlu/blog/service/TagService.java

```java
    /**
     * 查询所有文章标签
     * @return
     */
    Result findAll();
```

src/main/java/com/mszlu/blog/service/impl/TagServiceImpl.java

```java
	@Override
    public Result findAll() {
        List<Tag> tags = this.tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(tags));
    }
```

![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-7c34a1dff99702486158c47425d7595c.png)

# 9.3. 发布文章

## 9.3.1 接口说明

```java
请求内容是object（{content: “ww”, contentHtml: “ww↵”}）是因为本身为makedown的编辑器
id指的是文章id
```

![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-f48c6cf1690b50fe1ffc072d8cb51eea.png)

接口url：/articles/publish

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明                                   |
| -------- | -------- | -------------------------------------- |
| title    | string   | 文章标题                               |
| id       | long     | 文章id（编辑有值）                     |
| body     | object（{content: “ww”, contentHtml: “<p>ww</p>↵”}） | 文章内容 |
| category | {id: 2, avatar: “/category/back.png”, categoryName: “后端”} | 文章类别 |
| summary | string | 文章概述 |
| tags | \[{id: 5}, {id: 6}\] | 文章标签 |

返回数据：

```json
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {
        		"id":12232323//其余为Null
            }
}
```

代码结构  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-040072173a34a58307e2af8d07091017.png)

## 9.3.2 Controller

src/main/java/com/mszlu/blog/controller/ArticleController.java

```java
    //  @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；
    //  而最常用的使用请求体传参的无疑是POST请求了，所以使用@RequestBody接收数据时，一般都用POST方式进行提交。
	@PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
```

我们需要建立参数对象需要用于接收前端传过来的数据  
src/main/java/com/mszlu/blog/vo/params/ArticleParam.java

```java
package com.mszlu.blog.vo.params;

import com.mszlu.blog.vo.CategoryVo;
import com.mszlu.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
}

```

src/main/java/com/mszlu/blog/vo/params/ArticleBodyParam.java

```java
package com.mszlu.blog.vo.params;

import lombok.Data;

@Data
public class ArticleBodyParam {

    private String content;

    private String contentHtml;

}

```

## 9.3.3 Service

src/main/java/com/mszlu/blog/service/ArticleService.java

```java
    /**
     * 文章发布服务
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);
```

src/main/java/com/mszlu/blog/service/impl/ArticleServiceImpl.java

```java
	@Resource
	private ArticleTagMapper articleTagMapper;
	
	@Override
	@Transactional
	public Result publish(ArticleParam articleParam) {
		//注意想要拿到数据必须将接口加入拦截器
		SysUser sysUser = UserThreadLocal.get();
		
		/**
		 * 1. 发布文章 目的 构建Article对象
		 * 2. 作者id  当前的登录用户
		 * 3. 标签  要将标签加入到 关联列表当中
		 * 4. body 内容存储 article bodyId
		 */
		Article article = new Article();
		article.setAuthorId(sysUser.getId());
		article.setCategoryId(articleParam.getCategory().getId());
		article.setCreateDate(System.currentTimeMillis());
		article.setCommentCounts(0);
		article.setSummary(articleParam.getSummary());
		article.setTitle(articleParam.getTitle());
		article.setViewCounts(0);
		article.setWeight(Article.Article_Common);
		article.setBodyId(-1L);
		//插入之后 会生成一个文章id（因为新建的文章没有文章id所以要insert一下
		//官网解释："insert后主键会自动'set到实体的ID字段。所以你只需要"getid()就好
		//利用主键自增，mp的insert操作后id值会回到参数对象中
		//https://blog.csdn.net/HSJ0170/article/details/107982866
		this.articleMapper.insert(article);
		
		//tags
		List<TagVo> tags = articleParam.getTags();
		if (tags != null) {
			for (TagVo tag : tags) {
				ArticleTag articleTag = new ArticleTag();
				articleTag.setArticleId(article.getId());
				articleTag.setTagId(tag.getId());
				this.articleTagMapper.insert(articleTag);
			}
		}
		//body
		ArticleBody articleBody = new ArticleBody();
		articleBody.setContent(articleParam.getBody().getContent());
		articleBody.setContentHtml(articleParam.getBody().getContentHtml());
		articleBody.setArticleId(article.getId());
		articleBodyMapper.insert(articleBody);
		//插入完之后生成BodyId
		article.setBodyId(articleBody.getId());
		//MybatisPlus中的save方法什么时候执行insert，什么时候执行update
		// https://www.cxyzjd.com/article/Horse7/103868144
		//只有当更改数据库时才插入或者更新，一般查询就可以了
		articleMapper.updateById(article);
		
		ArticleVo articleVo = new ArticleVo();
		articleVo.setId(article.getId());
		return Result.success(articleVo);//只设置了ID值,只返回ID
		
		/*//第二种返回方法
		Map<String,String> map = new HashMap<>();
		map.put("id",article.getId().toString());
		return Result.success(map);*/
	}
```

src/main/java/com/mszlu/blog/config/WebMVCConfig.java

当然登录拦截器中，需要加入发布文章的配置：

```java
/**
	 * @Description 增加拦截器
	 * @Param [registry]
	 * @return void
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//假设拦截test接口后续实际遇到拦截的接口是时，再配置真正的拦截接口
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/test")
				.addPathPatterns("/comments/create/change")//评论需要登录
				.addPathPatterns("/articles/publish");//发布文章要拿到SysUser需要加入拦截器
				//.excludePathPatterns("/login");
	}
```

src/main/java/com/mszlu/blog/dao/mapper/ArticleTagMapper.java

```java
package com.mszlu.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.dao.pojo.ArticleTag;

public interface ArticleTagMapper  extends BaseMapper<ArticleTag> {
}

```

src/main/java/com/mszlu/blog/dao/mapper/ArticleBodyMapper.java

```java
package com.mszlu.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.dao.pojo.ArticleBody;

public interface ArticleBodyMapper extends BaseMapper<ArticleBody> {
}

```

src/main/java/com/mszlu/blog/vo/ArticleVo.java

```java
package com.mszlu.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
	//一定要记得加 要不然 会出现精度损失
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;

}

```

src/main/java/com/mszlu/blog/dao/pojo/ArticleTag.java

```java
package com.mszlu.blog.dao.pojo;

import lombok.Data;

@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}

```

## 9.3.4 测试

# 9.4. AOP日志

IOC是spring的两大核心概念之一，IOC给我们提供了一个IOCbean容器，这个容器会帮我们自动去创建对象，不需要我们手动创建，IOC实现创建的通过DI（Dependency Injection 依赖注入），我们可以通过写Java注解代码或者是XML配置方式，把我们想要注入对象所依赖的一些其他的bean，自动的注入进去，他是通过byName或byType类型的方式来帮助我们注入。正是因为有了依赖注入，使得IOC有这非常强大的好处，解耦。

可以举个例子，JdbcTemplate 或者 SqlSessionFactory 这种bean，如果我们要把他注入到容器里面，他是需要依赖一个数据源的，如果我们把JdbcTemplate 或者 Druid 的数据源强耦合在一起，会导致一个问题，当我们想要使用jdbctemplate必须要使用Druid数据源，那么依赖注入能够帮助我们在Jdbc注入的时候，只需要让他依赖一个DataSource接口，不需要去依赖具体的实现，这样的好处就是，将来我们给容器里面注入一个Druid数据源，他就会自动注入到JdbcTemplate如果我们注入一个其他的也是一样的。比如说c3p0也是一样的，这样的话，JdbcTemplate和数据源完全的解耦了，不强依赖与任何一个数据源，在spring启动的时候，就会把所有的bean全部创建好，这样的话，程序在运行的时候就不需要创建bean了，运行速度会更快，还有IOC管理bean的时候默认是单例的，可以节省时间，提高性能，

[Spring IOC ,AOP，MVC 的理解](https://blog.csdn.net/lijiaming_99/article/details/120835843)

[Springboot AOP日志相关讲解](https://segmentfault.com/a/1190000020176315)

在不改变原有方法基础上对原有方法进行增强  
src/main/java/com/mszlu/blog/common/aop/LogAnnotation.java

```java
package com.mszlu.blog.common.aop;

import java.lang.annotation.*;

/**
 * 日志注解
 */
 //ElementType.TYPE代表可以放在类上面  method代表可以放在方法上
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operation() default "";
}

```

加上此注解代表着我们需要对此接口进行日志输出  
src/main/java/com/mszlu/blog/controller/ArticleController.java

```java
    @PostMapping
    //加上此注解，代表要对此接口记录日志
    @LogAnnotation(module = "文章",operation = "获取文章列表")
    public Result listArticle(@RequestBody PageParams pageParams){

        return articleService.listArticle(pageParams);

    }
```

src/main/java/com/mszlu/blog/common/aop/LogAspect.java

```java
package com.mszlu.blog.common.aop;

import com.alibaba.fastjson.JSON;
import com.mszlu.blog.utils.HttpContextUtils;
import com.mszlu.blog.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @Author ljm
 * @Date 2021/10/18 21:01
 * @Version 1.0
 */
@Component
@Aspect //切面 定义了通知和切点的关系
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.mszlu.blog.common.aop.LogAnnotation)")
    public void pt(){
    }

    //环绕通知
    @Around("pt()")
    public Object log(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        recordLog(point, time);
        return result;

    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("=====================log start================================");
        log.info("module:{}",logAnnotation.module());
        log.info("operation:{}",logAnnotation.operation());

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}",className + "." + methodName + "()");

//        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        log.info("params:{}",params);

        //获取request 设置IP地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.info("ip:{}", IpUtils.getIpAddr(request));


        log.info("excute time : {} ms",time);
        log.info("=====================log end================================");
    }




}

```

用到的方法类  
src/main/java/com/mszlu/blog/utils/HttpContextUtils.java

```java
package com.mszlu.blog.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequest
 *
 */
public class HttpContextUtils {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}

```

src/main/java/com/mszlu/blog/utils/IpUtils.java

```java
package com.mszlu.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取Ip
 *
 */
@Slf4j
public class IpUtils {

    /**
     * 获取IP地址
     * <p>
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null, unknown = "unknown", seperator = ",";
        int maxLength = 15;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.error("IpUtils ERROR ", e);
        }

        // 使用代理，则获取第一个IP地址
        if (StringUtils.isEmpty(ip) && ip.length() > maxLength) {
            int idx = ip.indexOf(seperator);
            if (idx > 0) {
                ip = ip.substring(0, idx);
            }
        }

        return ip;
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getIpAddr() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        return getIpAddr(request);
    }
}

```

结果是  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-3d2715d89b9467f443ae06f0634ec103.png)

ip地址查询的是

![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-e819ca37138dbff295449321112ef304.png)

# bug修正

防止拿到的值是null值，因为拿到的是毫秒值，需要对其进行转化，Y表示年，m表示月，对时间进行重写。  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-a6ff4227a5fe2f25b3493774744dbdf3.png)

文章归档：

[相关函数说明](https://www.cnblogs.com/ltian123/p/11077901.html)

```sql
select FROM_UNIXTIME(create_date/1000,'%Y') as year, FROM_UNIXTIME(create_date/1000,'%m') as month,count(*) as count from ms_article group by year,month
```

# 10.1. 文章图片上传

## 10.1.1 接口说明

接口url：/upload

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明           |
| -------- | -------- | -------------- |
| image    | file     | 上传的文件名称 |
|          |          |                |
|          |          |                |

返回数据：

```json
{
    "success":true,
 	"code":200,
    "msg":"success",
    "data":"https://static.mszlu.com/aa.png"
}
```

修改pom文件引入七牛云的sdk	[七牛云JavaSDK](https://developer.qiniu.com/kodo/1239/java)
pom.xml

```xml
<dependency>
  <groupId>com.qiniu</groupId>
  <artifactId>qiniu-java-sdk</artifactId>
  <version>[7.7.0, 7.7.99]</version>
</dependency>
```

## 10.1.2 Controller

src/main/java/com/mszlu/blog/controller/UploadController.java

```java
package com.hsxy.blog.controller;

import com.hsxy.blog.utils.QiniuUtils;
import com.hsxy.blog.vo.ErrorCode;
import com.hsxy.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @name UploadController
 * @Description 控制器:文件上传
 * @author WU
 * @Date 2022/8/17 14:01
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
	@Autowired
	private QiniuUtils qiniuUtils;
	
	//https://blog.csdn.net/justry_deng/article/details/80855235 MultipartFile介绍
	/**
	 * @Description 图片上传
	 * @Param [file] --> image
	 * @return com.hsxy.blog.vo.Result
	 */
	@PostMapping
	public Result upload(@RequestParam("image") MultipartFile file) {
		//原始文件名称 比如说aa.png
		String originalFilename = file.getOriginalFilename();
		//唯一的文件名称(UUID随机数+后缀名)<有字符串不必强转>
		String fileName = UUID.randomUUID() + "." + StringUtils.substringAfterLast(originalFilename, ".");
		//上传文件上传到那里呢？　七牛云　云服务器
		//降低我们自身应用服务器的带宽消耗
		boolean upload = qiniuUtils.upload(file, fileName);
		if (upload) {//	测试:[/test%2F]无效,只能放在根目录下,原因未知
			return Result.success(QiniuUtils.URL + "/" + fileName);
		}
		return Result.fail(ErrorCode.UPLOAD_FAIL.getCode(), ErrorCode.UPLOAD_FAIL.getMsg());
		
	}
}

```

## 10.1.3 使用七牛云

注意七牛云测试域名 一个月一回收，记得去修改。  
springboot默认只上传1M的图片大小所以修改文件配置  
src/main/resources/application.properties

```properties
#七牛云
    # 上传文件总的最大值
spring.servlet.multipart.max-request-size=100MB
    # 单个文件的最大值
spring.servlet.multipart.max-file-size=20MB
    # 七牛云密钥(Access/Secret Key)
qiniu.accessKey=jAAL6yip7nCan_v5p99eID8tzbOVgYuYn-t3rL2o
qiniu.accessSecretKey=f3IQ_1fSv7qetuQgxKiDh3g8Tb3p3CwdnMA4hjXB
```

[七牛云建立存储空间教程](https://jingyan.baidu.com/article/0bc808fc267b8f1bd485b93b.html)

src/main/java/com/mszlu/blog/utils/QiniuUtils.java

```java
package com.hsxy.blog.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @name QiniuUtils
 * @Description 七牛云(图床)
 * @author WU
 * @Date 2022/8/17 15:01
 */
@Component
@Slf4j
public class QiniuUtils {
	//七牛云测试域名(30天)
	public static final String URL = "rgqys1n0s.hn-bkt.clouddn.com";
	
	//修改以下两个值放到proprietarties中，在密钥管理中获取
	@Value("${qiniu.accessKey}")
	private  String accessKey;
	@Value("${qiniu.accessSecretKey}")
	private  String accessSecretKey;
	
	/**
	 * @Description 七牛云-服务器直传-数据流上传(图片上传)
	 * (<a href="https://developer.qiniu.com/kodo/1239/java#server-upload">...</a>)
	 * @Param [file, fileName]
	 * @return boolean
	 */
	public boolean upload(MultipartFile file, String fileName){
		
		//构造一个带指定 Region 对象的配置类(华南地区)
		Configuration cfg = new Configuration(Region.huanan());
		//...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		//...生成上传凭证，然后准备上传，修改上传名称为自己创立空间的空间名称（是你自己的）
		String bucket = "blog-hsxy";
		//默认不指定key的情况下，以文件内容的hash值作为文件名
		try {
			byte[] uploadBytes = file.getBytes();
			Auth auth = Auth.create(accessKey, accessSecretKey);
			String upToken = auth.uploadToken(bucket);
			Response response = uploadManager.put(uploadBytes, fileName, upToken);
			//解析上传成功的结果
			DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
			//按官网另加
			//代替下方
			log.info("=================七牛云 start===========================");
			log.info("putRet.key:    {}",putRet.key);
			log.info("putRet.hash:   {}",putRet.hash);
			log.info("=================七牛云 end===========================");
			/*System.out.println(putRet.key);
			System.out.println(putRet.hash);*/
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
```

## 10.1.4 测试

# 10.2. 导航-文章分类

## 10.2.1 查询所有的文章分类

### 10.2.1.1 接口说明

接口url：/categorys/detail

请求方式：GET

请求参数：

| 参数名称 | 参数类型 | 说明 |
| -------- | -------- | ---- |
|          |          |      |
|          |          |      |
|          |          |      |

返回数据：

```json
{
    "success": true, 
    "code": 200, 
    "msg": "success", 
    "data": [
        {
            "id": 1, 
            "avatar": "/static/category/front.png", 
            "categoryName": "前端", 
            "description": "前端是什么，大前端"
        }, 
        {
            "id": 2, 
            "avatar": "/static/category/back.png", 
            "categoryName": "后端", 
            "description": "后端最牛叉"
        }, 
        {
            "id": 3, 
            "avatar": "/static/category/lift.jpg", 
            "categoryName": "生活", 
            "description": "生活趣事"
        }, 
        {
            "id": 4, 
            "avatar": "/static/category/database.png", 
            "categoryName": "数据库", 
            "description": "没数据库，啥也不管用"
        }, 
        {
            "id": 5, 
            "avatar": "/static/category/language.png", 
            "categoryName": "编程语言", 
            "description": "好多语言，该学哪个？"
        }
    ]
}

```

src/main/java/com/mszlu/blog/vo/CategoryVo.java

```java
package com.mszlu.blog.vo;

import lombok.Data;

@Data
public class CategoryVo {

    private Long id;

    private String avatar;

    private String categoryName;

    private String description;
}

```

### 10.2.1.2 Controller

src/main/java/com/mszlu/blog/controller/CategoryController.java

```java
@GetMapping("detail")
    public Result categoriesDetail(){
        return categoryService.findAllDetail();
    }
```

### 10.2.1.3 Service

src/main/java/com/mszlu/blog/service/CategoryService.java

```java
    Result findAllDetail();
```

src/main/java/com/mszlu/blog/service/impl/CategoryServiceImpl.java

>   当`CategoryVo`

```java
 @Override
    public Result findAllDetail() {
        List<Category> categories = categoryMapper.selectList(new LambdaQueryWrapper<>());
        //页面交互的对象
        return Result.success(copyList(categories));
    }
```

文章分类显示  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-00cfc8556556925408acc278f19195e5.png)

## 10.2.2 查询所有的标签

### 10.2.2.1 接口说明

接口url：/tags/detail

请求方式：GET

请求参数：

| 参数名称 | 参数类型 | 说明 |
| -------- | -------- | ---- |
|          |          |      |
|          |          |      |
|          |          |      |

返回数据：

```json
{
    "success": true, 
    "code": 200, 
    "msg": "success", 
    "data": [
        {
            "id": 5, 
            "tagName": "springboot", 
            "avatar": "/static/tag/java.png"
        }, 
        {
            "id": 6, 
            "tagName": "spring", 
            "avatar": "/static/tag/java.png"
        }, 
        {
            "id": 7, 
            "tagName": "springmvc", 
            "avatar": "/static/tag/java.png"
        }, 
        {
            "id": 8, 
            "tagName": "11", 
            "avatar": "/static/tag/css.png"
        }
    ]
}
```

### 10.2.2.3 Controller

src/main/java/com/mszlu/blog/vo/TagVo.java

```java
package com.mszlu.blog.vo;

import lombok.Data;

@Data
public class TagVo {

    private Long id;

    private String tagName;

    private String avatar;
}

```

src/main/java/com/mszlu/blog/controller/TagsController.java

```java
 @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }
```

### 10.2.2.4 Service

src/main/java/com/mszlu/blog/service/TagService.java

```java
    Result findAllDetail();
```

src/main/java/com/mszlu/blog/service/impl/TagServiceImpl.java

```java
 @Override
    public Result findAllDetail() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        List<Tag> tags = this.tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tags));
    }

```

标签显示  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-71a51fdcc23ec173993c1d18158c195b.png)

# 10.3. 分类文章列表

## 10.3.1 接口说明

接口url：/categorys/detail/{id}

请求方式：GET

请求参数：

| 参数名称 | 参数类型 | 说明     |
| -------- | -------- | -------- |
| id       | 分类id   | 路径参数 |
|          |          |          |
|          |          |          |

返回数据：

```json
{
    "success": true, 
    "code": 200, 
    "msg": "success", 
    "data": 
        {
            "id": 1, 
            "avatar": "/static/category/front.png", 
            "categoryName": "前端", 
            "description": "前端是什么，大前端"
        }
}

```

## 10.3.2 Controller

src/main/java/com/mszlu/blog/controller/CategoryController.java

```java
  @GetMapping("detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") Long id){
        return categoryService.categoriesDetailById(id);
    }

```

## 10.3.3 Service

src/main/java/com/mszlu/blog/service/CategoryService.java

```java
Result categoryDetailById(Long id);
```

src/main/java/com/mszlu/blog/service/impl/CategoryServiceImpl.java

```java
@Override
    public Result categoriesDetailById(Long id) {
        Category category = categoryMapper.selectById(id);
        //转换为CategoryVo
        CategoryVo categoryVo = copy(category);
        return Result.success(categoryVo);
    }
```

完成上面这些只能说是可以显示文章分类的图标了  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-e213caa5470b3cb047ca4089014c1d84.png)

显示分类下的文章:

但是如果想显示后端所有的归属内容得在文章查询列表处进行queryWrapper查找，当文章分类标签不是null时，加入文章分类标签这个查询元素进行分类修改。  
src/main/java/com/mszlu/blog/service/impl/ArticleServiceImpl.java

```java
	
    @Override
    public Result listArticle(PageParams pageParams) {
        /**
         * 1、分页查询article数据库表
         */
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //查询文章的参数 加上分类id，判断不为空 加上分类条件  
        if (pageParams.getCategoryId()!=null) {
            //and category_id=#{categoryId}
            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
        }
        //是否置顶进行排序,        //时间倒序进行排列相当于order by create_data desc
        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        //分页查询用法 https://blog.csdn.net/weixin_41010294/article/details/105726879
        List<Article> records = articlePage.getRecords();
        // 要返回我们定义的vo数据，就是对应的前端数据，不应该只返回现在的数据需要进一步进行处理
        List<ArticleVo> articleVoList =copyList(records,true,true);
        return Result.success(articleVoList);
    }
```

src/main/java/com/mszlu/blog/vo/params/PageParams.java

```java
package com.mszlu.blog.vo.params;

import lombok.Data;

@Data
public class PageParams {

    private int page = 1;

    private int pageSize = 10;

    private Long categoryId;

    private Long tagId;
}

```

最后就可以显示所有文章分类的每个标签下的内容了  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-682fa3b5529452372114a56330123945.png)

# 10.4. 标签文章列表

## 10.4.1 接口说明

接口url：/tags/detail/{id}

请求方式：GET

请求参数：

| 参数名称 | 参数类型 | 说明     |
| -------- | -------- | -------- |
| id       | 标签id   | 路径参数 |
|          |          |          |
|          |          |          |

返回数据：

```json
{
    "success": true, 
    "code": 200, 
    "msg": "success", 
    "data": 
        {
            "id": 5, 
            "tagName": "springboot", 
            "avatar": "/static/tag/java.png"
        }
}

```

## 10.4.2 Controller

src/main/java/com/mszlu/blog/controller/TagsController.java

```java
	/**
	 * @Description 查询文章标签下所有的文章
	 * @Param [id]
	 * @return com.hsxy.blog.vo.Result
	 */
	@GetMapping("detail/{id}")
	public Result findDetailById(@PathVariable("id") Long id){
		return tagService.findDetailById(id);
	}
```

## 10.4.3 Service

src/main/java/com/mszlu/blog/service/TagService.java

```java
	Result findDetailById(Long id);
```

src/main/java/com/mszlu/blog/service/impl/TagServiceImpl.java

```java
 @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        TagVo copy = copy(tag);
        return Result.success(copy);
    }
```

完成上面这些这保证了文章标签显示出来了我们需要重写文章查询接口，保证当遇到标签查询时我们可以做到正确查询文章标签所对应的内容，要不每一个标签查出来的内容都是一样的。  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-c514a987af910154468f2128d9c79560.png)

## 10.4.4 修改原有的查询文章接口

src/main/java/com/mszlu/blog/service/impl/ArticleServiceImpl.java

```java
        //加入标签条件查询
        //article表中并没有tag字段 一篇文章有多个标签
        //articie_tog article_id 1：n tag_id
        //我们需要利用一个全新的属于文章标签的queryWrapper将这篇文章的article_Tag查出来，保存到一个list当中。
        // 然后再根据queryWrapper的in方法选择我们需要的标签即可。
```

```java
    @Override
    public Result listArticle(PageParams pageParams) {
        /**
         * 1、分页查询article数据库表
         */
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (pageParams.getCategoryId()!=null) {
            //and category_id=#{categoryId}
            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
        }
        List<Long> articleIdList = new ArrayList<>();
        if(pageParams.getTagId()!=null){
            //加入标签条件查询
            //article表中并没有tag字段 一篇文章有多个标签
            //articie_tog article_id 1：n tag_id
            //我们需要利用一个全新的属于文章标签的queryWrapper将这篇文章的article_Tag查出来，保存到一个list当中。
            // 然后再根据queryWrapper的in方法选择我们需要的标签即可。

            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
            for (ArticleTag articleTag : articleTags) {
                articleIdList.add(articleTag.getArticleId());
            }
            if (articleTags.size() > 0) {
                // and id in(1,2,3)
                queryWrapper.in(Article::getId,articleIdList);
            }

        }
        //是否置顶进行排序,        //时间倒序进行排列相当于order by create_data desc
        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        //分页查询用法 https://blog.csdn.net/weixin_41010294/article/details/105726879
        List<Article> records = articlePage.getRecords();
        // 要返回我们定义的vo数据，就是对应的前端数据，不应该只返回现在的数据需要进一步进行处理
        List<ArticleVo> articleVoList =copyList(records,true,true);
        return Result.success(articleVoList);
    }

```

## 10.4.5 测试

最终的结果如下，每一个标签下都对应着该标签所对应的文章  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-31237bb00e3a796c91e47886afbd4ace.png)

模拟通过点击标签进入文章,提取SQL语句分析:

```sql
# 进入导航-标签,先查询子导航下的文章分类/标签
SELECT id,avatar,category_name,description FROM ms_category;
SELECT id,avatar,tag_name FROM ms_tag;
# 点击11后查找标签id=8的信息
SELECT id,avatar,tag_name FROM ms_tag WHERE id=8;
# 查询标签id=8的文章id
SELECT id,article_id,tag_id FROM ms_article_tag WHERE (tag_id = 8);
# 连表查询筛选出标签id=8的文章信息
SELECT COUNT(*) FROM ms_article WHERE (id IN (1, 10, 1405916999732707331, 1405916999732707334, 1405916999732707335));
# 分页查询符合标签id的文章列表
SELECT id,title,summary,comment_counts,view_counts,author_id,body_id,category_id,weight,create_date 
FROM ms_article 
WHERE (id IN (1,10,1405916999732707331,1405916999732707334,1405916999732707335)) 
ORDER BY weight DESC,create_date DESC 
LIMIT 5;
# 通过文章id获取Tag详细信息(下同)
select id,avatar,tag_name as tagName 
from ms_tag 
where id in (select tag_id 
             from ms_article_tag 
             where article_id=1405916999732707335);
# 获取用户信息(下同)
SELECT id,account,admin,avatar,create_date,deleted,email,last_login,mobile_phone_number,nickname,password,salt,status 
FROM ms_sys_user 
WHERE id=1;

select id,avatar,tag_name as tagName 
from ms_tag 
where id in (select tag_id 
             from ms_article_tag 
             where article_id=1405916999732707334);
# 同上(省略)             
# ...

select id,avatar,tag_name as tagName 
from ms_tag 
where id in (select tag_id 
             from ms_article_tag 
             where article_id=1405916999732707331);

# 同上(省略)             
# ...

select id,avatar,tag_name as tagName from ms_tag where id in (select tag_id from ms_article_tag where article_id=1);

# 同上(省略)             
# ...

select id,avatar,tag_name as tagName from ms_tag where id in (select tag_id from ms_article_tag where article_id=10);

# 同上(省略)             
# ...
```



# 11.1. 归档文章列表

## 11.1.1 接口说明

接口url：/articles

请求方式：POST

请求参数：

| 参数名称 | 参数类型 | 说明 |
| -------- | -------- | ---- |
| year     | string   | 年   |
| month    | string   | 月   |
|          |          |      |

返回数据：

```json
{
    "success": true, 
    "code": 200, 
    "msg": "success", 
    "data": [文章列表，数据同之前的文章列表接口]
        
}
```

[mybatisplus驼峰命名和mapper.xml使用](https://blog.csdn.net/lijiaming_99/article/details/120863442)

## 11.1.2 文章列表参数

src/main/java/com/mszlu/blog/vo/params/PageParams.java

```java
package com.mszlu.blog.vo.params;

import lombok.Data;

@Data
public class PageParams {

    private int page = 1;

    private int pageSize = 10;

    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;

    //传递6的话变成06
    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }
}

```

## 11.1.3 使用自定义sql 实现文章列表

src/main/java/com/mszlu/blog/service/impl/ArticleServiceImpl.java

```java
  @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = this.articleMapper.listArticle(page,pageParams.getCategoryId(),pageParams.getTagId(),pageParams.getYear(),pageParams.getMonth());
        return Result.success(copyList(articleIPage.getRecords(),true,true));
    }
```

```xml
    <resultMap id="articleMap" type="com.mszlu.blog.dao.pojo.Article">
        <id column="id" property="id" />
        <result column="author_id" property="authorId"/>
        <result column="comment_counts" property="commentCounts"/>
        <result column="create_date" property="createDate"/>
        <result column="summary" property="summary"/>
        <result column="title" property="title"/>
        <result column="view_counts" property="viewCounts"/>
        <result column="weight" property="weight"/>
        <result column="body_id" property="bodyId"/>
        <result column="category_id" property="categoryId"/>
    </resultMap>

 
<!-- resultMap和resultType区别   https://blog.csdn.net/xushiyu1996818/article/details/89075069?spm=1001.2101.3001.6650.4&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-4.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-4.no_search_link-->
<!--驼峰命名法   https://blog.csdn.net/A_Java_Dog/article/details/107006391?spm=1001.2101.3001.6650.6&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-6.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-6.no_search_link-->
<!--    Long categoryId,-->
<!--    Long tagId,-->
<!--    String year,-->
<!--    String month-->
<!--mybatis中xml文件用法    https://blog.csdn.net/weixin_43882997/article/details/85625805-->
<!--动态sql    https://www.jianshu.com/p/e309ae5e4a77-->
<!--驼峰命名    https://zoutao.blog.csdn.net/article/details/82685918?spm=1001.2101.3001.6650.18&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-18.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-18.no_search_link-->
    <select id="listArticle" resultMap="articleMap">
        select * from ms_article
        <where>
            1 = 1
            <if test="categoryId != null">
                and category_id=#{categoryId}
            </if>
            <if test="tagId != null">
                and id in (select article_id from ms_article_tag where tag_id=#{tagId})
            </if>
            <if test="year != null and year.length>0 and month != null and month.length>0">
                and (FROM_UNIXTIME(create_date/1000,'%Y') =#{year} and FROM_UNIXTIME(create_date/1000,'%m')=#{month})
            </if>
        </where>
        order by weight,create_date desc
    </select>
```

## 11.1.4 测试

结果如下  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-24631bcefa30e38175dd815d6ca2f32c.png)

# 11.2. 统一缓存处理（优化）

内存的访问速度 远远大于 磁盘的访问速度 （1000倍起）  
[Spring Cache介绍](https://juejin.cn/post/6997440726627778597#heading-0)  
src/main/java/com/mszlu/blog/common/cache/Cache.java

```java
package com.hsxy.blog.common.cache;

import java.lang.annotation.*;

/**
 * @name Cache
 * @Description 缓存注解
 * @author WU
 * @Date 2022/8/18 15:49
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
	/**
	 * @Description 过期时间
	 * @Param []
	 * @return long
	 */
	long expire() default 1 * 60 * 1000;
	/**
	 * @Description 缓存标识Key
	 * @Param []
	 * @return java.lang.String
	 */
	String name() default "";
	
}

```

src/main/java/com/mszlu/blog/common/cache/CacheAspect.java

```java
package com.mszlu.blog.common.cache;

import com.alibaba.fastjson.JSON;
import com.mszlu.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;

@Aspect
@Component
@Slf4j
public class CacheAspect {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Pointcut("@annotation(com.mszlu.blog.common.cache.Cache)")
    public void pt(){}

    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp){
        try {
            Signature signature = pjp.getSignature();
            //类名
            String className = pjp.getTarget().getClass().getSimpleName();
            //调用的方法名
            String methodName = signature.getName();


            Class[] parameterTypes = new Class[pjp.getArgs().length];
            Object[] args = pjp.getArgs();
            //参数
            String params = "";
            for(int i=0; i<args.length; i++) {
                if(args[i] != null) {
                    params += JSON.toJSONString(args[i]);
                    parameterTypes[i] = args[i].getClass();
                }else {
                    parameterTypes[i] = null;
                }
            }
            if (StringUtils.isNotEmpty(params)) {
                //加密 以防出现key过长以及字符转义获取不到的情况
                params = DigestUtils.md5Hex(params);
            }
            Method method = pjp.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            //获取Cache注解
            Cache annotation = method.getAnnotation(Cache.class);
            //缓存过期时间
            long expire = annotation.expire();
            //缓存名称
            String name = annotation.name();
            //先从redis获取
            String redisKey = name + "::" + className+"::"+methodName+"::"+params;
            String redisValue = redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isNotEmpty(redisValue)){
                log.info("走了缓存~~~,{},{}",className,methodName);
                return JSON.parseObject(redisValue, Result.class);
            }
            Object proceed = pjp.proceed();
            redisTemplate.opsForValue().set(redisKey,JSON.toJSONString(proceed), Duration.ofMillis(expire));
            log.info("存入缓存~~~ {},{}",className,methodName);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Result.fail(-999,"系统错误");
    }

}

```

使用：

```java
   @PostMapping("hot")
    @Cache(expire = 5 * 60 * 1000,name = "hot_article")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }
```

然而由于JSON精度损失,无法进行文章访问

解决方案:

SpringBoot全局配置long转String丢失精度的问题解决:使用Jackson2

blog-parent\blog-api\src\main\java\com\hsxy\blog\config\JacksonConfig

```java
@Configuration
public class JacksonConfig {
 
     /**
      * Jackson全局转化long类型为String，解决jackson序列化时long类型缺失精度问题
      * @return Jackson2ObjectMapperBuilderCustomizer 注入的对象
      */
        @Bean
        public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
                return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder
                                .serializerByType(Long.class, ToStringSerializer.instance)
                                .serializerByType(Long.TYPE, ToStringSerializer.instance);
        }
}
```

# 11.3. 思考别的优化

[mongodb](https://blog.csdn.net/yanpenglei/article/details/79261875)  
[redis incr](https://www.cnblogs.com/sheseido/p/11243341.html)

1.  文章可以放入es当中，便于后续中文分词搜索。springboot教程有和es的整合
2.  评论数据，可以考虑放入mongodb当中 电商系统当中 评论数据放入mongo中
3.  阅读数和评论数 ，考虑把阅读数和评论数 增加的时候 放入redis incr自增，使用定时任务 定时把数据固话到数据库当中
4.  为了加快访问速度，部署的时候，可以把图片，js，css等放入七牛云存储中，加快网站访问速度

做一个后台 用springsecurity 做一个权限系统，对工作帮助比较大

将域名注册，备案，部署相关

# 管理后台

这部分教程比较乱,适合先复制完在看讲解

# 12.1. 搭建项目

## 12.1.1 新建maven工程 blog-admin

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>blog-parent2</artifactId>
        <groupId>com.mszlu</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>blog-admin</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <!-- 排除 默认使用的logback  -->
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!-- log4j2 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.76</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>

        <dependency>
            <groupId>commons-collections</groupId>
            <artifactId>commons-collections</artifactId>
            <version>3.2.2</version>
        </dependency>
        <dependency>
            <groupId>commons-codec</groupId>
            <artifactId>commons-codec</artifactId>
        </dependency>

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.3</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>joda-time</groupId>
            <artifactId>joda-time</artifactId>
            <version>2.10.10</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
    </dependencies>
</project>
```

## 12.1.2 配置

application.properties:

```properties
server.port=8889
spring.application.name=mszlu_admin_blog

#数据库的配置
# datasource
spring.datasource.url=jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=UTF-8&serverTimeZone=UTC
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

#mybatis-plus
mybatis-plus.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
mybatis-plus.global-config.db-config.table-prefix=ms_
```

mybatis-plus配置：

```java
package com.mszlu.blog.admin.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.mszlu.blog.admin.mapper")
public class MybatisPlusConfig {

    //分页插件
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}

```

## 12.1.3 启动类

```java
package com.mszlu.blog.admin;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdminApp {

    public static void main(String[] args) {
        SpringApplication.run(AdminApp.class,args);
    }
}

```

## 12.1.4 导入前端工程

放入resources下的static目录中，前端工程在资料中有

## 12.1.5 新建表

后台管理用户表

```sql
CREATE TABLE `blog`.`ms_admin`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;
```

权限表

```sql
CREATE TABLE `blog`.`ms_permission`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;
```

用户和权限的关联表

```sql
CREATE TABLE `blog`.`ms_admin_permission`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(0) NOT NULL,
  `permission_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;
```

网址:http://localhost:8889/pages/main.html

# 12.2. 权限管理

## 12.2.1 Controller

```java
package com.mszlu.blog.admin.controller;

import com.mszlu.blog.admin.model.params.PageParam;
import com.mszlu.blog.admin.pojo.Permission;
import com.mszlu.blog.admin.service.PermissionService;
import com.mszlu.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("permission/permissionList")
    public Result permissionList(@RequestBody PageParam pageParam){
        return permissionService.listPermission(pageParam);
    }

    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}

```

```java
package com.mszlu.blog.admin.model.params;

import lombok.Data;

@Data
public class PageParam {

    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}

```

```java
package com.mszlu.blog.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Permission {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String path;

    private String description;
}

```

## 12.2.2 Service

```java
package com.mszlu.blog.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mszlu.blog.admin.mapper.PermissionMapper;
import com.mszlu.blog.admin.model.params.PageParam;
import com.mszlu.blog.admin.pojo.Permission;
import com.mszlu.blog.admin.vo.PageResult;
import com.mszlu.blog.admin.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    public Result listPermission(PageParam pageParam){
        Page<Permission> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(pageParam.getQueryString())) {
            queryWrapper.eq(Permission::getName,pageParam.getQueryString());
        }
        Page<Permission> permissionPage = this.permissionMapper.selectPage(page, queryWrapper);
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setList(permissionPage.getRecords());
        pageResult.setTotal(permissionPage.getTotal());
        return Result.success(pageResult);
    }

    public Result add(Permission permission) {
        this.permissionMapper.insert(permission);
        return Result.success(null);
    }

    public Result update(Permission permission) {
        this.permissionMapper.updateById(permission);
        return Result.success(null);
    }

    public Result delete(Long id) {
        this.permissionMapper.deleteById(id);
        return Result.success(null);
    }
}

```

```java
package com.mszlu.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.admin.pojo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    
}

```

```java
package com.mszlu.blog.admin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

    private boolean success;

    private int code;

    private String msg;

    private Object data;


    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }

    public static Result fail(int code, String msg){
        return new Result(false,code,msg,null);
    }
}

```

```java
package com.mszlu.blog.admin.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private List<T> list;

    private Long total;
}

```

## 12.2.3 测试

# 12.3. Security集成

## 12.3.1 添加依赖

```xml
 <dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

## 12.3.2 配置

```java
package com.hsxy.blog.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @name SecurityConfig
 * @Description SpringSecurity配置
 * @author WU
 * @Date 2022/8/18 20:13
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/**
	 * @Description BCrypt密码加密策略(安全)<包含时间戳:每次生成都不同>
	 * @Param []
	 * @return org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	/**
	 * @Description 手动生成密码加入数据库, 不另做注册
	 * @Param [args]
	 * @return void
	 */
	public static void main(String[] args) {
		//加密策略 MD5 不安全 彩虹表  MD5 加盐
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
		System.out.println(new BCryptPasswordEncoder().encode("hsxy"));
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests() //开启登录认证
//                .antMatchers("/user/findAll").hasRole("admin") //访问接口需要admin的角色
				.antMatchers("/css/**").permitAll()
				.antMatchers("/img/**").permitAll()
				.antMatchers("/js/**").permitAll()
				.antMatchers("/plugins/**").permitAll()
				.antMatchers("/admin/**").access("@authService.auth(request,authentication)") //自定义service 来去实现实时的权限认证
				.antMatchers("/pages/**").authenticated()
				.and().formLogin()
				.loginPage("/login.html") //自定义的登录页面
				.loginProcessingUrl("/login") //登录处理接口
				.usernameParameter("username") //定义登录时的用户名的key 默认为username
				.passwordParameter("password") //定义登录时的密码key，默认是password
				.defaultSuccessUrl("/pages/main.html")
				.failureUrl("/login.html")
				.permitAll() //通过 不拦截，更加前面配的路径决定，这是指和登录表单相关的接口 都通过
				.and().logout() //退出登录配置
				.logoutUrl("/admin/logout") //退出登录接口(要和前端匹配)
				.logoutSuccessUrl("/login.html")
				.permitAll() //退出登录的接口放行
				.and()
				.httpBasic()
				.and()
				.csrf().disable() //csrf关闭:跨站请求伪造,默认只能通过post方式提交logout请求 如果自定义登录 需要关闭
				.headers().frameOptions().sameOrigin();//支持iframe嵌套
	}
}

```

## 12.3.3 登录认证

```java
package com.mszlu.blog.admin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Admin {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;
}

```

```java
package com.hsxy.blog.admin.service;

import com.hsxy.blog.admin.pojo.Admin;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @name SecurityUserService
 * @Description
 * @author WU
 * @Date 2022/8/19 9:34
 */
@Service
@Slf4j
public class SecurityUserServiceImpl implements UserDetailsService {
	@Autowired
	private AdminService adminService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("username:{}",username);
		//当用户登录的时候，springSecurity 就会将请求 转发到此,会把username 传递到这里
		//根据用户名查询 admin表 查找用户，不存在 抛出异常，存在 将用户名，密码，授权列表 组装成springSecurity的User对象 并返回
		Admin adminUser = adminService.findAdminByUserName(username);
		if (adminUser == null){
			throw new UsernameNotFoundException("用户名不存在");
		}
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		//剩下的认证 就由框架帮我们完成
		return new User(username,adminUser.getPassword(), authorities);
	}
	
}
```

```java
package com.hsxy.blog.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hsxy.blog.admin.mapper.AdminMapper;
import com.hsxy.blog.admin.mapper.PermissionMapper;
import com.hsxy.blog.admin.pojo.Admin;
import com.hsxy.blog.admin.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @name AdminService
 * @Description
 * @author WU
 * @Date 2022/8/19 9:38
 */
@Service
public class AdminService {
	
	@Resource
	private AdminMapper adminMapper;
	@Resource
	private PermissionMapper permissionMapper;
	
	public Admin findAdminByUserName(String username){
		LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Admin::getUsername,username).last("limit 1");
		return adminMapper.selectOne(queryWrapper);
	}
	

	/**
	 * @Description 按管理员Id查找权限(多加了个s)
	 * @Param [adminId]
	 * @return java.util.List<com.hsxy.blog.admin.pojo.Permission>
	 */
	public List<Permission> findPermissionsByAdminId(Long adminId){
		return adminMapper.findPermissionsByAdminId(adminId);
	}
	
}
```

```java
package com.hsxy.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsxy.blog.admin.pojo.Admin;
import com.hsxy.blog.admin.pojo.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @name AdminMapper
 * @Description
 * @author WU
 * @Date 2022/8/19 9:38
 */
public interface AdminMapper extends BaseMapper<Admin> {
	
	/**
	 * @Description 按管理员Id查找权限(多加了个s)
	 * @Param [adminId]
	 * @return java.util.List<com.hsxy.blog.admin.pojo.Permission>
	 */
	@Select("SELECT * FROM ms_permission where id in (select permission_id from ms_admin_permission where admin_id=#{adminId})")
	List<Permission> findPermissionsByAdminId(Long adminId);
}

```

```java
package com.mszlu.blog.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mszlu.blog.admin.pojo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

}

```

## 12.3.4 权限认证

```java
package com.mszlu.blog.admin.service;

import com.mszlu.blog.admin.mapper.AdminMapper;
import com.mszlu.blog.admin.pojo.Admin;
import com.mszlu.blog.admin.pojo.Permission;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private AdminService adminService;

    public boolean auth(HttpServletRequest request, Authentication authentication){
  //权限认证，请求路径
        String requestURI = request.getRequestURI();
        log.info("request url:{}", requestURI);
        //true代表放行 false 代表拦截
        Object principal = authentication.getPrincipal();
        if (principal == null || "anonymousUser".equals(principal)){
            //未登录
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.findAdminByUserName(username);
        if (admin == null){
            return false;
        }
        if (admin.getId() == 1){
            //认为是超级管理员
            return true;
        }
        List<Permission> permissions = adminService.findPermissionsByAdminId(admin.getId());
        requestURI = StringUtils.split(requestURI,'?')[0];
        for (Permission permission : permissions) {
            if (requestURI.equals(permission.getPath())){
                log.info("权限通过");
                return true;
            }
        }
        return false;
    }
}

```

未发现实际作用(可能为练手):

```java
package com.mszlu.blog.admin.service;

import org.springframework.security.core.GrantedAuthority;

public class MySimpleGrantedAuthority implements GrantedAuthority {
    private String authority;
    private String path;

    public MySimpleGrantedAuthority(){}

    public MySimpleGrantedAuthority(String authority){
        this.authority = authority;
    }

    public MySimpleGrantedAuthority(String authority,String path){
        this.authority = authority;
        this.path = path;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public String getPath() {
        return path;
    }
}

```



```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!--MyBatis配置文件-->
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.mszlu.blog.admin.mapper.PermissionMapper">

    <select id="findPermissionsByAdminId" parameterType="long" resultType="com.mszlu.blog.admin.pojo.Permission">
        select * from ms_permission where id in (select permission_id from ms_admin_permission where admin_id=#{adminId})
    </select>
</mapper>
```

# 12.4. 作业

添加角色，用户拥有多个角色，一个角色拥有多个权限

# 13.总结技术亮点

1、jwt + redis

token令牌的登录方式，访问认证速度快，session共享，安全性

redis做了令牌和用户信息的对应管理，  
		1，进一步增加了安全性  
		2\. 登录用户做了缓存  
		3.灵活控制用户的过期（续期，踢掉线等）

2、threadLocal使用了保存用户信息，请求的线程之内，可以随时获取登录的用户，做了线程隔离

3、在使用完ThreadLocal之后，做了value的删除，防止了内存泄漏（这面试说强引用。弱引用。不是明摆着让面试官间**JVM**嘛）

4、线程安全-update table set value = newValue where id=1 and value=oldValue (CAS自旋)

5、线程池应用非常广，面试7个核心参数（对当前的主业务流程无影响的操作，放入线程池执行）

​		1.登录，记录日志

6·权限系统重点内容

7·统一日志记录，统一缓存处理

# 14.前端

先找到Home.vue,一般这里放主页  
views文件夹一般存放页面  
components文件夹一般存放vue自定义的组件  
一般views用到各个组件

router文件夹存放路由，通过不同的路径跳转到不同的页面  
store一般做存储用的  
utils文件夹一般是工具类  
request一般是请求  
api就是跟后端的一些接口的定义  
dist文件夹打包之后产生的静态页面  
![在这里插入图片描述](../../../Downloads/%25E6%25AF%259B%25E7%25BA%25BF%25E5%2589%25AA%25E8%2597%258F/springboot-vue%25E7%25BB%2583%25E6%2589%258B%25E7%25BA%25A7%25E9%25A1%25B9%25E7%259B%25AE-%25E7%259C%259F%25E5%25AE%259E%25E7%259A%2584%25E5%259C%25A8%25E7%25BA%25BF%25E5%258D%259A%25E5%25AE%25A2%25E7%25B3%25BB%25E7%25BB%259F_ljm_99%25E7%259A%2584%25E5%258D%259A%25E5%25AE%25A2-CSDN%25E5%258D%259A%25E5%25AE%25A2_springboot-vue%25E9%25A1%25B9%25E7%259B%25AE%2520-%25202022-08-09-1660030228/assets/1660030228-c24fa356b42f48b68650c75de4871921.png)  
首先看  
config目录中的dev.env.js配置后端访问路径  
![在这里插入图片描述](../../../Downloads/%25E6%25AF%259B%25E7%25BA%25BF%25E5%2589%25AA%25E8%2597%258F/springboot-vue%25E7%25BB%2583%25E6%2589%258B%25E7%25BA%25A7%25E9%25A1%25B9%25E7%259B%25AE-%25E7%259C%259F%25E5%25AE%259E%25E7%259A%2584%25E5%259C%25A8%25E7%25BA%25BF%25E5%258D%259A%25E5%25AE%25A2%25E7%25B3%25BB%25E7%25BB%259F_ljm_99%25E7%259A%2584%25E5%258D%259A%25E5%25AE%25A2-CSDN%25E5%258D%259A%25E5%25AE%25A2_springboot-vue%25E9%25A1%25B9%25E7%259B%25AE%2520-%25202022-08-09-1660030228/assets/1660030228-c241f22f72772a6d7aba12177541b684.png)部署生产环境  
![在这里插入图片描述](../../../Downloads/%25E6%25AF%259B%25E7%25BA%25BF%25E5%2589%25AA%25E8%2597%258F/springboot-vue%25E7%25BB%2583%25E6%2589%258B%25E7%25BA%25A7%25E9%25A1%25B9%25E7%259B%25AE-%25E7%259C%259F%25E5%25AE%259E%25E7%259A%2584%25E5%259C%25A8%25E7%25BA%25BF%25E5%258D%259A%25E5%25AE%25A2%25E7%25B3%25BB%25E7%25BB%259F_ljm_99%25E7%259A%2584%25E5%258D%259A%25E5%25AE%25A2-CSDN%25E5%258D%259A%25E5%25AE%25A2_springboot-vue%25E9%25A1%25B9%25E7%259B%25AE%2520-%25202022-08-09-1660030228/assets/1660030228-9b23e126426d5d656c678582531d25a2.png)  
再看static目录，category是图片路径  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-6a414f12d9645eabc14cabd16a94cf23.png)  
在数据库中这样配置  
![在这里插入图片描述](../../../Downloads/%25E6%25AF%259B%25E7%25BA%25BF%25E5%2589%25AA%25E8%2597%258F/springboot-vue%25E7%25BB%2583%25E6%2589%258B%25E7%25BA%25A7%25E9%25A1%25B9%25E7%259B%25AE-%25E7%259C%259F%25E5%25AE%259E%25E7%259A%2584%25E5%259C%25A8%25E7%25BA%25BF%25E5%258D%259A%25E5%25AE%25A2%25E7%25B3%25BB%25E7%25BB%259F_ljm_99%25E7%259A%2584%25E5%258D%259A%25E5%25AE%25A2-CSDN%25E5%258D%259A%25E5%25AE%25A2_springboot-vue%25E9%25A1%25B9%25E7%259B%25AE%2520-%25202022-08-09-1660030228/assets/1660030228-736b3a9bd1d9e7d2bb5f0dd4007f175e.png)  
再看src目录  
![在这里插入图片描述](../../../Downloads/%25E6%25AF%259B%25E7%25BA%25BF%25E5%2589%25AA%25E8%2597%258F/springboot-vue%25E7%25BB%2583%25E6%2589%258B%25E7%25BA%25A7%25E9%25A1%25B9%25E7%259B%25AE-%25E7%259C%259F%25E5%25AE%259E%25E7%259A%2584%25E5%259C%25A8%25E7%25BA%25BF%25E5%258D%259A%25E5%25AE%25A2%25E7%25B3%25BB%25E7%25BB%259F_ljm_99%25E7%259A%2584%25E5%258D%259A%25E5%25AE%25A2-CSDN%25E5%258D%259A%25E5%25AE%25A2_springboot-vue%25E9%25A1%25B9%25E7%259B%25AE%2520-%25202022-08-09-1660030228/assets/1660030228-2970f9267cfc7a8df38062da87f3bb3f.png)  
api表示后端接口访问的定义，囊括了所有后端的访问接口

以api文件夹下的article.js为例子

```javascript
import request from '@/request'


export function getArticles(query, page) {
  return request({
    url: '/articles',//访问路径
    method: 'post',//访问方式post
    //传递参数
    data: {
      page: page.pageNumber,
      pageSize: page.pageSize,
      name: page.name,
      sort: page.sort,
      year: query.year,
      month: query.month,
      tagId: query.tagId,
      categoryId: query.categoryId
    }
  })
}

export function getHotArtices() {
  return request({
    url: '/articles/hot',//接口路径的名称也可以随意更改
    method: 'post'//访问方式，想改成get直接修改即可
  })
}

export function getNewArtices() {
  return request({
    url: '/articles/new',
    method: 'post'
  })
}

export function viewArticle(id) {
  return request({
    url: `/articles/view/${id}`,
    method: 'post'
  })
}

export function getArticlesByCategory(id) {
  return request({
    url: `/articles/category/${id}`,
    method: 'post'
  })
}

export function getArticlesByTag(id) {
  return request({
    url: `/articles/tag/${id}`,
    method: 'post'
  })
}


export function publishArticle(article,token) {
  return request({
    headers: {'Authorization': token},
    url: '/articles/publish',
    method: 'post',
    data: article
  })
}

export function listArchives() {
  return request({
    url: '/articles/listArchives',
    method: 'post'
  })
}

export function getArticleById(id) {
  return request({
    url: `/articles/${id}`,
    method: 'post'
  })
}

```

在login.js文件中

```javascript
import request from '@/request'

export function login(account, password) {
  const data = {
    account,
    password
  }
  return request({
    url: '/login',
    method: 'post',
    data
  })
}

export function logout(token) {
  return request({
    headers: {'Authorization': token},//在后端通过headers获取token
    url: '/logout',
    method: 'get'
  })
}

export function getUserInfo(token) {
  return request({
    headers: {'Authorization': token},
    url: '/users/currentUser',
    method: 'get'
  })
}

export function register(account, nickname, password) {
  const data = {
    account,
    nickname,
    password
  }
  return request({
    url: '/register',
    method: 'post',
    data
  })
}

```

在home.vue文件夹中

```javascript
<template>
  <div id="home">
    <el-container>
    	
    	<base-header :activeIndex="activeIndex"></base-header>//头
		  
		  <router-view class="me-container"/>//容器
		  
			<base-footer v-show="footerShow"></base-footer>//尾
		  
		</el-container>
		
  </div>
  
</template>

<script>
//components对应components目录，views对应views目录
import BaseFooter from '@/components/BaseFooter'
import BaseHeader from '@/views/BaseHeader'

export default {
  name: 'Home',
  data (){
  	return {
  			activeIndex: '/',
  			footerShow:true
  	}
  },
  components:{
  	'base-header':BaseHeader,
  	'base-footer':BaseFooter
  },
  beforeRouteEnter (to, from, next){
  	 next(vm => {
    	vm.activeIndex = to.path
  	})
  },
  beforeRouteUpdate (to, from, next) {
	  if(to.path == '/'){
	  	this.footerShow = true
	  }else{
	  	this.footerShow = false
	  }
	  this.activeIndex = to.path
	  next()
	}
}
</script>

<style>

.me-container{
  margin: 100px auto 140px;
}
</style>

```

components文件夹下的src\\components\\BaseFooter.vue文件夹

```javascript
<template>
  <el-footer class="me-area">
    <div class="me-footer">
      <p>Designed by
        <strong>
          <router-link to="/" class="me-login-design-color">码神之路</router-link>
        </strong>
      </p>
    </div>
  </el-footer>

</template>

<script>

  export default {
    name: 'BaseFooter',
    data() {
      return {}
    },
    methods: {},
    mounted() {
    }
  }
</script>

<style>

  .el-footer {
    min-width: 100%;
    box-shadow: 0 -2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: 1024;
  }

  .me-footer {
    text-align: center;
    line-height: 60px;
    font-family: 'Open Sans', sans-serif;
    font-size: 18px;

  }

  .me-login-design-color {
    color: #5FB878 !important;
  }

</style>

```

对应图片最下方  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-3a2b62e04c112d3c0a8735e247d0ca7c.png)src\\views\\BaseHeader.vue文件头

```javascript
<template>
  <el-header class="me-area">
    <el-row class="me-header">

      <el-col :span="4" class="me-header-left">
        <router-link to="/" class="me-title">
          <img src="../assets/img/logo.png" />
        </router-link>
      </el-col>

      <el-col v-if="!simple" :span="16">
        <el-menu :router=true menu-trigger="click" active-text-color="#5FB878" :default-active="activeIndex"
                 mode="horizontal">
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/category/all">文章分类</el-menu-item>
          <el-menu-item index="/tag/all">标签</el-menu-item>
          <el-menu-item index="/archives">文章归档</el-menu-item>

          <el-col :span="4" :offset="4">
            <el-menu-item index="/write"><i class="el-icon-edit"></i>写文章</el-menu-item>
          </el-col>

        </el-menu>
      </el-col>

      <template v-else>
        <slot></slot>
      </template>

      <el-col :span="4">
        <el-menu :router=true menu-trigger="click" mode="horizontal" active-text-color="#5FB878">

          <template v-if="!user.login">
            <el-menu-item index="/login">
              <el-button type="text">登录</el-button>
            </el-menu-item>
            <el-menu-item index="/register">
              <el-button type="text">注册</el-button>
            </el-menu-item>
          </template>

          <template v-else>
            <el-submenu index>
              <template slot="title">
                <img class="me-header-picture" :src="user.avatar"/>//头像获取
              </template>
              <el-menu-item index @click="logout"><i class="el-icon-back"></i>退出</el-menu-item>
            </el-submenu>
          </template>
        </el-menu>
      </el-col>

    </el-row>
  </el-header>
</template>

<script>
  export default {
    name: 'BaseHeader',
    props: {
      activeIndex: String,
      simple: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {}
    },
    computed: {
      user() {
        let login = this.$store.state.account.length != 0
        let avatar = this.$store.state.avatar
        return {
          login, avatar
        }
      }
    },
    methods: {
      logout() {
        let that = this
        this.$store.dispatch('logout').then(() => {
          this.$router.push({path: '/'})
        }).catch((error) => {
          if (error !== 'error') {
            that.$message({message: error, type: 'error', showClose: true});
          }
        })
      }
    }
  }
</script>

<style>

  .el-header {
    position: fixed;
    z-index: 1024;
    min-width: 100%;
    box-shadow: 0 2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
  }

  .me-title {
    margin-top: 10px;
    font-size: 24px;
  }

  .me-header-left {
    margin-top: 10px;
  }

  .me-title img {
    max-height: 2.4rem;
    max-width: 100%;
  }

  .me-header-picture {
    width: 36px;
    height: 36px;
    border: 1px solid #ddd;
    border-radius: 50%;
    vertical-align: middle;
    background-color: #5fb878;
  }
</style>

```

对应图片最上方  
![在这里插入图片描述](../../../Downloads/%25E6%25AF%259B%25E7%25BA%25BF%25E5%2589%25AA%25E8%2597%258F/springboot-vue%25E7%25BB%2583%25E6%2589%258B%25E7%25BA%25A7%25E9%25A1%25B9%25E7%259B%25AE-%25E7%259C%259F%25E5%25AE%259E%25E7%259A%2584%25E5%259C%25A8%25E7%25BA%25BF%25E5%258D%259A%25E5%25AE%25A2%25E7%25B3%25BB%25E7%25BB%259F_ljm_99%25E7%259A%2584%25E5%258D%259A%25E5%25AE%25A2-CSDN%25E5%258D%259A%25E5%25AE%25A2_springboot-vue%25E9%25A1%25B9%25E7%259B%25AE%2520-%25202022-08-09-1660030228/assets/1660030228-1101578e595e42b9442721740577ece9.png)  
BaseHeader.vue中的logout本质上调用store文件夹下的index.js文件  
![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660030228-993c91bd58cd8c0709224b64853f2bec.png)



# 部署

## 1. 云服务器

购买阿里云服务器，如果是学生的话，很便宜

安装环境 ：yum 去安装

比如要安装java  ，百度搜 yum -y install java

比如要安装数据库，百度搜 yum如何安装mysql 5.7 

## 2. 域名备案

## 3. 部署

### 3.1 打包

可能遇到的问题，打包不成功, 但是代码可以正常运行

解决方案：

1. 文件编码不对，在setting -> File Encoding 中 改为UTF-8

    ![image-20210621231653304](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20210621231653304.png)

2. 将原有的编译插件 更换为

    ~~~xml
     <plugins>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-resources-plugin</artifactId>
                        <version>2.7</version>
                        <dependencies>
                            <dependency>
                                <groupId>org.apache.maven.shared</groupId>
                                <artifactId>maven-filtering</artifactId>
                                <version>1.3</version>
                            </dependency>
                        </dependencies>
                    </plugin>
                </plugins>
    ~~~


## 4. 安装docker

~~~shell
## 1、yum 包更新到最新 
yum update
## 2、安装需要的软件包， yum-util 提供yum-config-manager功能，另外两个是devicemapper驱动依赖的 
yum install -y yum-utils device-mapper-persistent-data lvm2
## 3、 设置yum源
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
## 4、 安装docker，出现输入的界面都按 y 
yum install -y docker-ce
## 5、 查看docker版本，验证是否验证成功
docker -v
#启动docker
/bin/systemctl start docker.service
~~~

## 5. 拉取镜像

~~~shell
docker pull nginx
docker pull redis:5.0.3
docker pull java:8
docker pull mysql:5.7
~~~

### 5.1 mysql

创建容器，设置端口映射、目录映射

~~~shell
mkdir /mnt/docker/mysql
cd /mnt/docker/mysql
~~~

~~~shell
docker run -id \
-p 3306:3306 \
--name=c_mysql \
--privileged=true \
-v /tmp/mysql/conf:/etc/mysql/conf.d \
-v /tmp/mysql/logs:/logs \
-v /tmp/mysql/data:/var/lib/mysql \
-v /etc/localtime:/etc/localtime \
-e MYSQL_ROOT_PASSWORD=root \
mysql:5.7
~~~

在/mnt/docker/mysql/conf 创建my.cnf

~~~java
[mysqld]
#
## Remove leading ## and set to the amount of RAM for the most important data
## cache in MySQL. Start at 70% of total RAM for dedicated server, else 10%.
## innodb_buffer_pool_size = 128M
#
## Remove leading ## to turn on a very important data integrity option: logging
## changes to the binary log between backups.
## log_bin
#
## Remove leading ## to set options mainly useful for reporting servers.
## The server defaults are faster for transactions and fast SELECTs.
## Adjust sizes as needed, experiment to find the optimal values.
## join_buffer_size = 128M
## sort_buffer_size = 2M
## read_rnd_buffer_size = 2M
datadir=/var/lib/mysql
socket=/var/lib/mysql/mysql.sock
character-set-server=utf8
## Disabling symbolic-links is recommended to prevent assorted security risks
symbolic-links=0
#设置容器时间和宿主机时间一致
lower_case_table_names=1
log_timestamps=SYSTEM

pid-file=/var/run/mysqld/mysqld.pid
sql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION
~~~

后续如果改mysql的配置文件 可以改这里

> 接下来我们需要进入容器，开放权限

*   `docker ps` 查看容器是否已经运行

    *   应该可以看到mysql5.7的容器已经在运行

*   进入容器`docker exec -it c_mysql bash`

*   进入容器中的MYSQL`mysql -uroot -proot`

    *   输入密码，如上，我设置的为root,直接输入就可以。（界面上显示空白，但其实密码已经输入了，按回车键确定。）

*   接下来的步骤和**上面安装宿主机mysql**的**第10步**类似

    *   ```sql
        show databases;
        use mysql;
        show tables;
        select user,host from user;		#（可能会报sql错误，加上\`\`包裹字段即可）
        ```

        ![image-20220821142544841](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220821142544841.png)

        *   可以看到root用户对应的host并不是%，我们要改为%以开放权限。

    *   `update user set host ='%'where user ='root';`

    *   `update user set host ='%'where user ='root' and host='localhost';`

    *   最好两句都执行下，我当时的情况是==有两个root用户==，执行的结果是一个root对应host为空，另一个host为%。

    *   （甚至可以开放高级权限`grant all privileges on *.* to root@'%' identified by 'root' with grant option;`）

    *   刷新权限`flush privileges;`

*   `exit` 退出MySQL,再`exit`退出MySQL的容器



检查mysql的Docker容器是否配置成功，并查看其暴露出来的docker ip地址

*   用这个`docker logs -f c_mysql` 看看有没有配置成功![在这里插入图片描述](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660986348-b919a0fca116839d30a0777fb47301b2.png)
*   `docker inspect c_mysql` →查看对应的mysql 的docker容器的ip地址172.17.0.2，请记住这个地址，下面打包后台应用需要用到。

~~~shell
#去查看mysql对应的docker容器ip地址，配置到项目
docker inspect c_mysql
~~~

![image-20220819174336110](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220819174336110.png)

> 环境无误后我们需要导入我们的数据库文件blog.sql

*   上面提到，我们已经将宿主机`/mnt/docker/mysql/data`目录和docker容器的`/var/lib/mysql`地址之间实现了映射，因此，我们如果将blog.sql文件导入宿主机的==该目录==下，blog.sql同样也会出现在docker的对应目录下，具体操作如下：

```
cd /mnt/docker/mysql/data
rz
```

*   其中cd 是切换目录的意思，rz是上传文件。此时我们将blog.sql导入其中即可。接下来我们检查一下docker容器中是否出现该文件
*   进入容器`docker exec -it c_mysql bash`
*   切换到挂载目录`cd /var/lib/mysql`
*   查看该目录下所有文件`ls`，理论上可以看到blog.sql也在该目录下。
*   接下来在docker中执行该sql。
    *   `mysql -uroot -p` ,输入密码，进入docker中的数据库
    *   创建数据库`create database blog;`
    *   退出回到容器`exit`
    *   将文件导入数据库`mysql -uroot -p blog < blog.sql;`
    *   进入数据库`mysql -uroot -proot`
    *   切换数据库`use blog;`
    *   执行sql并保存数据库`source blog.sql;`
*   结果检查
    *   `use blog;` 
    *   `show tables;` 
    *   `select * from ms_admin;`
    *   如果能查出来结果（如下图），那就说明我们的部署成功了。  
        ![数据库数据成功导入](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660986348-b97e419b235541f7397f663516022d8e.png)
*   在实际部署中，数据库上我花的时间最多，link failure,access denied之类，jdbc connection failed之类，很大可能都是数据库权限的问题。大家多查查root对应的host权限是不是%这些。
*   还有码神笔记中是写了一个数据库配置文件的，我这里没有写，暂时用默认的配置即可。







### 5.2 reids

~~~shell
#docker run -id --name=redis -p 6379:6379 redis:5.0.3
docker run --name redis -p 6379:6379 -d redis redis-server --appendonly yes
~~~

```shell
docker inspect redis
```

我们要查看一下redis docker所对应的ip地址`docker inspect redis`，后面打包项目时要用，我这里就是172.17.0.3  
![image-20220821163342767](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220821163342767.png)

##  6. Dockerfile构建后端镜像

* Dockerfile 是一个文本文件
* 包含了一条条的指令
* 每一条指令构建一层，基于基础镜像，最终构建出一个新的镜像
* 对于开发人员：可以为开发团队提供一个完全一致的开发环境
* 对于测试人员：可以直接拿开发时所构建的镜像或者通过Dockerfile文件
    构建一个新的镜像开始工作了
* 对于运维人员：在部署时，可以实现应用的无缝移植

诸如MySQL ,redis的镜像我们可以直接拉取，但后端项目的镜像需要我们自己打包，构建。

### 6.2 发布springboot项目

> 首先，我们需要在idea上配置参数并且打包

*   配置连接参数![idea配置](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660986348-93c730ce61e6c51367d835f7d34f8017.png)

*   还有一个参数是关于跨域配置的，大家也可以参考以下，需要允许自己的域通过，然后跨域配置的方式也从老师写的那种，换成了第二种，原因如图，请大家注意！

    ![image-20220821164357694](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220821164357694.png)

- 打包生成jar包

  生产环境:打包不能用IDEA右侧打包了,需在右上新建一个maven运行

  ![image-20220821165016199](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220821165016199.png)
  
  ```
  -U clean package
  ```
  
- 找到本地文件，把他放到一个好找的地方就可以了，准备待会导入我们的目录下  
  ![打包文件处理](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/1660986348-94423faaf7bcdc02a39f645397afd3a3.png)
  
  > 接下来，我们需要在/opt/my_app/blog/app目录下导入jar包，构建镜像的配置文件并运行镜像。
  
  *   先新建目录 `mkdir /opt/my_app/blog/app`
  
  *   可以使用上传功能的配置`yum -y install lrzsz`
  
  *   切换到该目录下`cd /opt/my_app/blog/app`
  
  *   上传文件`rz`
  
  *   修改名字 `mv blog-api-1.0-SNAPSHOT.jar blog_api.jar`
  
      >   注意下划线
  
  *   配置构建镜像的配置文件
  
      *   新建文件 `touch blog_dockerfile`
      *   修改文件内容`vim blog_dockerfile`
      *   将以下内容粘贴进入即可（个人参数自己修改一下）
  
  ​     ① 定义父镜像：FROM java:8 
  
  ​     ② 定义作者信息：MAINTAINER mszlu <test@mszlu.com> 
  
  ​     ③ 将jar包添加到容器： ADD springboot.jar app.jar 
  
  ​     ④ 定义容器启动执行的命令：CMD java –jar app.jar 
  
  ​     ⑤ 通过dockerfile构建镜像：docker bulid –f dockerfile文件路径 –t 镜像名称:版本
  
  ```dockerfile
  FROM java:8
  MAINTAINER hsxy <hsxy0@189.cn>
  ADD ./blog_api.jar /app.jar
  CMD java -jar /app.jar --spring.profiles.active=prod
  ```
  
  *   运行镜像`docker build -f ./blog_dockerfile -t app .`
  *   检查，`docker images`应该可以看到app的docker已经在运行了
  
    
  

## 6.1 构建前端镜像

> 修改前端的配置，打包，上传，并微调文件位置。

*   我们先来到前端

到\blog-app\config\prod.env.js下修改生产环境配置

```js
'use strict'
module.exports = {
  NODE_ENV: '"production"'
  BASE_API: '"http://43.142.80.183/api/"'
}
```

运行前端项目终端命令

```shell
npm run build
```

build项目，他会自动将项目生成到dist，将该文件夹下所有文件复制到服务器的/opt/my_app/blog/blog下

![image-20220821165712859](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220821165712859.png)

## 7. 服务编排

### 7.1 安装Docker Compose

~~~shell
## Compose目前已经完全支持Linux、Mac OS和Windows，在我们安装Compose之前，需要先安装Docker。下面我 们以编译好的二进制包方式安装在Linux系统中。 
curl -L https://github.com/docker/compose/releases/download/1.22.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
## 设置文件可执行权限 
chmod +x /usr/local/bin/docker-compose
## 查看版本信息 
docker-compose -version
~~~

### 7.2 使用docker compose编排nginx+springboot项目

服务编排主要是利用Docker Compose将springboot和nginx分批启动

1. 创建docker-compose目录

    ~~~shell
    mkdir /opt/my_app/blog/docker-compose
    cd /opt/my_app/blog/docker-compose
    ~~~

2. 编写 docker-compose.yml 文件

    ~~~shell
    version: '3'
    services:
      nginx:
       image: nginx
       container_name: blog_nginx
       ports:
        - 80:80
        - 443:443
       links:
        - app
       depends_on:
        - app
       volumes:
        - /opt/my_app/blog/docker-compose/nginx/:/etc/nginx/
        - /opt/my_app/blog/blog:/blog/blog
       network_mode: "bridge"
      app:
        image: app
        container_name: app
        expose:
          - "8888"
        network_mode: "bridge"
    ~~~

    >   这个文件中有个`depends_on`就是起到了编排目录的功能，关于这个挂载目录，请大家千万注意自己的目录顺序，跟着各种教程做的时候要分清他们的目录顺序和自己的目录顺序，下面是我的目录顺序 
    >
    >   ![image-20220821170125696](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220821170125696.png)

3. 创建./nginx目录

    ~~~shell
    mkdir -p ./nginx
    ~~~

4. 在./nginx目录下 编写nginx.conf文件

    ~~~conf
    user nginx;
    worker_processes  1;
    error_log  /var/log/nginx/error.log warn;
    pid        /var/run/nginx.pid;
    events {
        worker_connections  1024;
    }
    http {
        include       /etc/nginx/mime.types;
        default_type  application/octet-stream;
        log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                          '$status $body_bytes_sent "$http_referer" '
                          '"$http_user_agent" "$http_x_forwarded_for"';
        access_log  /var/log/nginx/access.log  main;
        sendfile        on;
        #tcp_nopush     on;
        keepalive_timeout  65;
        #gzip  on;
        include /etc/nginx/conf.d/*.conf;
    }
    ~~~
    
    
    
5. 在nginx下创建conf.d目录，conf.d下创建blog.conf

    ~~~shell
    gzip_min_length 1k;
    gzip_buffers 4 16k;
    gzip_comp_level 2;
    gzip_vary off;
    upstream appstream{
         
            server app:8888;
    }
    server{
        # 监听端口
        listen  80;
        # 主机名称/地址(无域名情况:localhost)
        server_name localhost;
        index   index.html;
        # 前端服务
         location / {
            root /blog/blog/;
            # 刷新页面后跳转到当前页面
            try_files $uri $uri/ /index.html;
            index index.html;
         }
    
         # 后端api
         location /api {
                    proxy_pass http://appstream;
        }
        # 缓存
        location ~* \.(jpg|jpeg|gif|png|swf|rar|zip|css|js|map|svg|woff|ttf|txt)$ {
            root /blog/blog/;
            index index.html;
            add_header Access-Control-Allow-Origin *;
        }
    }
    ~~~
    
    >   码神的配置中还有个**ssl/tsl**配置，大家有需要可以去申请了再配置，我这里直接把他省略了。
    
6. 还有一个文件叫做`mime.types`,这个玩意是nginx中必带的东西，我是从网上下载了一个nginx然后拿出`mime.types`文件然后`rz`进去的，大家也可以直接新建文件把代码复制进去，代码如下。

    ```typescript
    
    types {
        text/html                                        html htm shtml;
        text/css                                         css;
        text/xml                                         xml;
        image/gif                                        gif;
        image/jpeg                                       jpeg jpg;
        application/javascript                           js;
        application/atom+xml                             atom;
        application/rss+xml                              rss;
    
        text/mathml                                      mml;
        text/plain                                       txt;
        text/vnd.sun.j2me.app-descriptor                 jad;
        text/vnd.wap.wml                                 wml;
        text/x-component                                 htc;
    
        image/png                                        png;
        image/svg+xml                                    svg svgz;
        image/tiff                                       tif tiff;
        image/vnd.wap.wbmp                               wbmp;
        image/webp                                       webp;
        image/x-icon                                     ico;
        image/x-jng                                      jng;
        image/x-ms-bmp                                   bmp;
    
        font/woff                                        woff;
        font/woff2                                       woff2;
    
        application/java-archive                         jar war ear;
        application/json                                 json;
        application/mac-binhex40                         hqx;
        application/msword                               doc;
        application/pdf                                  pdf;
        application/postscript                           ps eps ai;
        application/rtf                                  rtf;
        application/vnd.apple.mpegurl                    m3u8;
        application/vnd.google-earth.kml+xml             kml;
        application/vnd.google-earth.kmz                 kmz;
        application/vnd.ms-excel                         xls;
        application/vnd.ms-fontobject                    eot;
        application/vnd.ms-powerpoint                    ppt;
        application/vnd.oasis.opendocument.graphics      odg;
        application/vnd.oasis.opendocument.presentation  odp;
        application/vnd.oasis.opendocument.spreadsheet   ods;
        application/vnd.oasis.opendocument.text          odt;
        application/vnd.openxmlformats-officedocument.presentationml.presentation
                                                         pptx;
        application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
                                                         xlsx;
        application/vnd.openxmlformats-officedocument.wordprocessingml.document
                                                         docx;
        application/vnd.wap.wmlc                         wmlc;
        application/x-7z-compressed                      7z;
        application/x-cocoa                              cco;
        application/x-java-archive-diff                  jardiff;
        application/x-java-jnlp-file                     jnlp;
        application/x-makeself                           run;
        application/x-perl                               pl pm;
        application/x-pilot                              prc pdb;
        application/x-rar-compressed                     rar;
        application/x-redhat-package-manager             rpm;
        application/x-sea                                sea;
        application/x-shockwave-flash                    swf;
        application/x-stuffit                            sit;
        application/x-tcl                                tcl tk;
        application/x-x509-ca-cert                       der pem crt;
        application/x-xpinstall                          xpi;
        application/xhtml+xml                            xhtml;
        application/xspf+xml                             xspf;
        application/zip                                  zip;
    
        application/octet-stream                         bin exe dll;
        application/octet-stream                         deb;
        application/octet-stream                         dmg;
        application/octet-stream                         iso img;
        application/octet-stream                         msi msp msm;
    
        audio/midi                                       mid midi kar;
        audio/mpeg                                       mp3;
        audio/ogg                                        ogg;
        audio/x-m4a                                      m4a;
        audio/x-realaudio                                ra;
    
        video/3gpp                                       3gpp 3gp;
        video/mp2t                                       ts;
        video/mp4                                        mp4;
        video/mpeg                                       mpeg mpg;
        video/quicktime                                  mov;
        video/webm                                       webm;
        video/x-flv                                      flv;
        video/x-m4v                                      m4v;
        video/x-mng                                      mng;
        video/x-ms-asf                                   asx asf;
        video/x-ms-wmv                                   wmv;
        video/x-msvideo                                  avi;
    }
    ```

6. 可以`docker ps`检查一下，4个容器都在运行了

8. 在/mnt/docker/docker-compose 目录下 使用docker-compose 启动容器

    ~~~shell
    docker-compose up #直接启动
    
    docker-compose up -d #代表后台启动
    
    docker-compose down  #停止并删除容器
    
    docker-compose start #启动已有容器
    
    docker-compose stop  #停止运行的容器docker-compose up #直接启动
    
    docker-compose up -d #代表后台启动
    
    docker-compose down  #停止并删除容器
    
    docker-compose start #启动已有容器
    
    docker-compose stop  #停止运行的容器
    ~~~

9. 测试访问

    *   一般来说，我们先`docker-compose up`直接启动看看网站有没有问题，如果有问题，通过以下两个指令来完善。
        *   查看ngix日志：`docker logs nginx`
        *   查看后端日志：`docker-compose logs`
    *   当我们觉得部署完全搞定的时候，就可以docker-compose up -d后台启动，这样的话关掉服务器也能运行了。



报错后如果要修改后端代码要将app和nginx卸载重装

```shell
#先获取CONTAINER ID
docker ps
#以CONTAINER ID形式暂停,卸载
docker stop 694980daf100 d7deef6077cb
docker rm 694980daf100 d7deef6077cb
#进入到app目录(/opt/my_app/blog/app)重新构建app
docker build -f ./blog_dockerfile -t app .
```

>   还有个问题，大家可能会问后台管理部署为什么没有？
>
>   *   大家仔细看下后台管理，它只能说是一个SpringSecurity的案例而已，根本没有起到后台管理的作用…虽然部署完了，但是没什么意义。



# 15、缓存一致性问题

> 之前在文章列表读取，最新文章等接口的时候我们加了缓存，但是加了缓存会有一些问题，当我们修改或者用户浏览了文章，那么最新的修改和文章的浏览数量无法及时的更新，那么应该怎么做呢？



这里我们采用RocketMQ来解决这个问题。

## 安装rocketMQ

~~~shell
#docker 拉取
docker pull foxiswho/rocketmq:4.8.0
#启动nameserver
docker run -d -v /usr/local/rocketmq/logs:/opt/docker/rocketmq/logs \
      --name rmqnamesrv \
      -e "JAVA_OPT_EXT=-Xms512M -Xmx512M -Xmn128m" \
      -p 9876:9876 \
      foxiswho/rocketmq:4.8.0 \
      sh mqnamesrv
#broker.conf
brokerIP1=192.168.200.100
namesrvAddr=192.168.200.100:9876
brokerName=broker_all
#启动broker
docker run -d  -v /opt/docker/rocketmq/logs:/usr/local/rocketmq/logs -v /opt/docker/rocketmq/store:/usr/local/rocketmq/store \
      -v /opt/docker/rocketmq/conf:/usr/local/rocketmq/conf \
      --name rmqbroker \
      -e "NAMESRV_ADDR=192.168.200.100:9876" \
      -e "JAVA_OPT_EXT=-Xms512M -Xmx512M -Xmn128m" \
      -p 10911:10911 -p 10912:10912 -p 10909:10909 \
      foxiswho/rocketmq:4.8.0 \
      sh mqbroker -c /usr/local/rocketmq/conf/broker.conf
#rocketmq-console-ng
docker pull styletang/rocketmq-console-ng

docker run --name rmqconsole --link rmqnamesrv:rmqnamesrv \
-e "JAVA_OPTS=-Drocketmq.namesrv.addr=192.168.200.100:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false" \
-p 8180:8080 -t styletang/rocketmq-console-ng

#启动访问 http://192.168.200.100:8180/

~~~

## 集成

~~~xml
        <dependency>
            <groupId>org.apache.rocketmq</groupId>
            <artifactId>rocketmq-spring-boot-starter</artifactId>
            <version>2.2.0</version>
        </dependency>
~~~

添加对应的配置：

~~~properties
#rocketmq配置
rocketmq.name-server=192.168.200.100:9876
rocketmq.producer.group=blog_group
~~~

ArticleController中增加

```java
	/**
	 * @Description 编辑文章时查看文章
	 * @Param [articleId]
	 * @return com.hsxy.blog.vo.Result
	 */
	@PostMapping("/{id}")
	public Result ArticleById(@PathVariable("id") Long articleId){
		return articleService.findArticleById(articleId);
	}
```

ArticleServiceImpl修改publish

```java
@Override
	@Transactional
	public Result publish(ArticleParam articleParam) {
		//注意想要拿到数据必须将接口加入拦截器
		SysUser sysUser = UserThreadLocal.get();
		
		/**
		 * 1. 发布文章 目的 构建Article对象
		 * 2. 作者id  当前的登录用户
		 * 3. 标签  要将标签加入到 关联列表当中
		 * 4. body 内容存储 article bodyId
		 */
		Article article = new Article();
		boolean isEdit = false;
		if (articleParam.getId() != null){//article之前已获取过id则认为是编辑模式
			article.setId(articleParam.getId());
			article.setTitle(articleParam.getTitle());
			article.setSummary(articleParam.getSummary());
			article.setCategoryId(articleParam.getCategory().getId());//Long.parseLong(articleParam.getCategory().getId())[非]
			articleMapper.updateById(article);
			isEdit = true;
		}else {
			//article = new Article();
			article.setAuthorId(sysUser.getId());
			article.setWeight(Article.Article_Common);
			article.setViewCounts(0);
			article.setTitle(articleParam.getTitle());
			article.setSummary(articleParam.getSummary());
			article.setCommentCounts(0);
			article.setCreateDate(System.currentTimeMillis());
			article.setCategoryId(articleParam.getCategory().getId());//Long.parseLong(articleParam.getCategory().getId())[非]
			//插入之后 会生成一个文章id（因为新建的文章没有文章id所以要insert一下
			//官网解释："insert后主键会自动'set到实体的ID字段。所以你只需要"getid()就好
			//利用主键自增，mp的insert操作后id值会回到参数对象中
			//https://blog.csdn.net/HSJ0170/article/details/107982866
			this.articleMapper.insert(article);
		}
		
		//增加文章编辑模式
		/*article.setAuthorId(sysUser.getId());
		article.setCategoryId(articleParam.getCategory().getId());
		article.setCreateDate(System.currentTimeMillis());
		article.setCommentCounts(0);
		article.setSummary(articleParam.getSummary());
		article.setTitle(articleParam.getTitle());
		article.setViewCounts(0);
		article.setWeight(Article.Article_Common);
		article.setBodyId(-1L);
		this.articleMapper.insert(article);*/
		
		//tags
		List<TagVo> tags = articleParam.getTags();
		if (tags != null) {
			for (TagVo tag : tags) {
				ArticleTag articleTag = new ArticleTag();
				articleTag.setArticleId(article.getId());
				articleTag.setTagId(tag.getId());
				this.articleTagMapper.insert(articleTag);
			}
		}
		//body
		ArticleBody articleBody = new ArticleBody();
		articleBody.setContent(articleParam.getBody().getContent());
		articleBody.setContentHtml(articleParam.getBody().getContentHtml());
		articleBody.setArticleId(article.getId());
		articleBodyMapper.insert(articleBody);
		//插入完之后生成BodyId
		article.setBodyId(articleBody.getId());
		//MybatisPlus中的save方法什么时候执行insert，什么时候执行update
		// https://www.cxyzjd.com/article/Horse7/103868144
		//只有当更改数据库时才插入或者更新，一般查询就可以了
		articleMapper.updateById(article);
		
		ArticleVo articleVo = new ArticleVo();
		articleVo.setId(article.getId());
		//编辑模式
		if (isEdit){
			//发送一条消息给rocketmq 当前文章更新了，更新一下缓存吧
			/*ArticleMessage articleMessage = new ArticleMessage();
			articleMessage.setArticleId(article.getId());*/
            rocketMQTemplate.convertAndSend("blog-update-article",articleMessage);
		}
		return Result.success(articleVo);//只设置了ID值,只返回ID
		
		/*//第二种返回方法
		Map<String,String> map = new HashMap<>();
		map.put("id",article.getId().toString());
		return Result.success(map);*/
	}
```

在vo下新增ArticleMessage

```java
/**
 * @name ArticleMessage
 * @Description 文章消息队列
 * @author WU
 * @Date 2022/8/19 16:32
 */
@Data
public class ArticleMessage implements Serializable {
	
	private Long articleId;
	
}
```

创建service.mp.ArticleListener

```java
package com.hsxy.blog.service.mq;

import com.alibaba.fastjson.JSON;
import com.hsxy.blog.service.ArticleService;
import com.hsxy.blog.vo.ArticleMessage;
import com.hsxy.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

/**
 * @name ArticleListener
 * @Description
 * @author WU
 * @Date 2022/8/19 16:44
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "blog-update-article",consumerGroup = "blog-update-article-group")
public class ArticleListener implements RocketMQListener<ArticleMessage> {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onMessage(ArticleMessage message) {
        log.info("收到的消息:{}",message);
        //做什么了，更新缓存
        //1. 更新查看文章详情的缓存
        Long articleId = message.getArticleId();
        String params = DigestUtils.md5Hex(articleId.toString());
        String redisKey = "view_article::ArticleController::findArticleById::"+params;
        Result articleResult = articleService.findArticleById(articleId);
        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(articleResult), Duration.ofMillis(5 * 60 * 1000));
        log.info("更新了缓存:{}",redisKey);
        //2. 文章列表的缓存 不知道参数,解决办法 直接删除缓存
        Set<String> keys = redisTemplate.keys("listArticle*");
        keys.forEach(s -> {
            redisTemplate.delete(s);
            log.info("删除了文章列表的缓存:{}",s);
        });

    }
}

```

# 修BUG

## 登录后无法访问文章列表

这个Bug存在了很长时间,一直不知道如何解决.今天部署到服务器时发现了个报错



然后对比了热心网友提供的参数,发现文章页面的响应JSON格式不同

<img src="blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220821222959304.png" alt="image-20220821222959304" style="zoom:50%;" />

JSON格式:

![image-20220821222807395](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/image-20220821222807395.png)

可能问题出在这上面,试试查找,没发现异常,不是在该处

找不到问题,疑惑ing,花太多时间了,等以后经验丰富了再找吧

//TODO

## 未解决

![屏幕截图_2022-08-21_21-36-20](blog-springboot%20%E7%BB%83%E6%89%8B%E5%AE%9E%E6%88%98%E9%A1%B9%E7%9B%AE.assets/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE_2022-08-21_21-36-20.jpg)



# 总结

这是我第一次完整做完的项目,耗时两星期.个人感觉还有很多不足,在部署方面视频讲的不是很好,BUG至今未找到

>   发现BUG后要第一时间找出或记录快照,等到后面代码多了是真的找不到了🥲

2022/8/22

项目链接:

https://m26360.com

http://zhangshidi.space/



参考文档:

https://www.mszlu.com/java/blog/01/01.html



## 面试细节

### 0、结合仿牛客项目知识点总结

好多技术是相通的我们的个人博客只是完成了其中的一小部分。  
[仿牛客项目知识点总结](https://blog.csdn.net/weixin_48610702/article/details/115859656?utm_source=app&app_version=5.0.1&code=app_1562916241&uLinkId=usr1mkqgl919blen)  
除下面的知识点外，此网站也是一个很好的学习资料[网站链接](https://doocs.github.io/advanced-java/#/?id=%E4%BA%92%E8%81%94%E7%BD%91-java-%E5%B7%A5%E7%A8%8B%E5%B8%88%E8%BF%9B%E9%98%B6%E7%9F%A5%E8%AF%86%E5%AE%8C%E5%85%A8%E6%89%AB%E7%9B%B2)

### 1、项目本身

1.1. 项目的背景是什么，解决一个什么样的问题？  
1.2. 项目中你的职责是什么？  
1.3. 项目的基础功能有哪些？  
1.4. 项目使用的技术栈是什么，[技术架构](https://so.csdn.net/so/search?q=%E6%8A%80%E6%9C%AF%E6%9E%B6%E6%9E%84&spm=1001.2101.3001.7020)是怎么样的？  
1.5. 使用[微服务](https://so.csdn.net/so/search?q=%E5%BE%AE%E6%9C%8D%E5%8A%A1&spm=1001.2101.3001.7020)了吗？ 项目是怎么搭建的，机器配置是什么样的，有做分布式吗？  
1.6. 项目的具体功能细节，比如论坛项目中评论是如何存储的？怎么展示所有的评论？  
1.7. 项目中[框架](https://so.csdn.net/so/search?q=%E6%A1%86%E6%9E%B6&spm=1001.2101.3001.7020)或者中间件的使用细节。项目里怎么用ES的，ES怎么支持搜索的？缓存和DB是如何结合使用的？

### 2、项目扩展

2.1. 项日存在哪些问题，你准备怎么解决？  
2.2. 项目的具体功能点如何优化？如论坛项目，查询评论是在DB里扫表查询吗？想要查询更快可以做哪些优化？  
2.3. 项目中最有挑战的模块是哪个，你是怎么解决的？  
2.4. 项目中使用某种框架的原因，比如使用了本地缓存Caffeine，为什么使用这个Caffeine，不使用Guava？  
项目要增大10倍的qps，你会怎么设计？  
2.5. 项目上线后出现线上问题怎么解决？如频繁fullGc，定时任务失败怎么办？

### 3、高频问题

1、找个印象最深的项目说说？(简历中不止一个项目)  
2、你项目中遇到的最大的问题是什么？你是怎么解决的？  
3、你项目中用到的技术栈是如何学习的？  
4、为什么做这个项目，技术选型为什么是这样的？  
5、登录怎么做的？单点登录说说你的理解？  
6、项目遇到的最大挑战是什么？(类似问题2)  
7、说说项目中的闪光点和亮点？  
8、项目怎么没有尝试部署上线呢？  
9、介绍项目具体做了什么？(项目背景)  
10、如果让你对这个项目优化，你会从哪几个点来优化呢？

以上总结的10大高频问题，均来自网友的面试问题分享。

大家做完一个项目之后，一定要去细扣一两个模块，并在面试中与面试官进行深入的交流。

比如说登录，可以思考一下登录具体的流程，前后端如何执行步骤。

比如一些电商类的[分布式](https://so.csdn.net/so/search?q=%E5%88%86%E5%B8%83%E5%BC%8F&spm=1001.2101.3001.7020)锁，是如何实现的？分布式事务等？这些均可以细致去思考准备等。

通过自己具体介绍项目中的一两个模块，面试官就会对你有比较深入的了解，这样给你的面评就会比较好。

当然在项目中可能还会引出一些其他的内容，顺延可能就到八股文环节了~

如果是实现的比较简单，没有使用什么中间件，只有增删改查，就会针对表的设计，一些模块的设计思路，还有场景问题，大多是那些你没有使用的中间件解决的问题：问如果很多用户访问你的主页，你会怎么办（这种高并发的问题是使用中间件解决的，你没用到，看你能不能很好的回答上来怎么解决）

### 4、结合电商类面试题分析一些流程

*   秒杀三问题: 高并发, 少卖, 超卖. 问题描述和解决方法？
*   问秒杀项目：介绍一下你对项目高并发和高可用的理解？
*   库存超卖如何解决的？(商城类项目)
*   Redis缓存的库存怎么解决库存的超卖？
*   项目支持多大的并发量？有没有测试过呢？
*   你这个项目中消息中间件用来做什么的？ 限流如何实现？ 分布式锁和分布式事务项目有用到吗？详细聊聊？
*   分布式锁有哪些实现方式？你项目中用到的是哪一种？ 谈谈你对分布式事务的理解，你觉得重要吗？ 分布式事务有哪些实现方式?Seata 用过吗？
*   抢购业务流程说说？ 如何实现在秒杀场景下的限流服务？ 流量削峰在秒杀场景下有考虑过存在的问题和解决方案吗？
*   如果请求的数据丢失该怎么办？有什么解决方法吗？

#### 5、论坛问题参考

絮叨  
高频问题大家可结合自己的项目去思考和整理一下答案，可能在后面的面试中就会遇到相似的问题。

其实大家做的项目，不管是什么类型，面试官更多关注的是通过这个项目你学到了什么，有什么收获，有什么自己的思考等，这些才是更重要的。

强烈建议大家好好去看看推送的项目在面试中如何准备的第一期推文，里面包含了10个非常非常高频的问题。

尤其是自己在项目遇到最大困难或者问题是什么？是怎么思考和解决的？

很多朋友可能会说，这个项目是跟着视频和文档一步步来的，似乎也没遇到很大的问题。

你可以这么回答(提供两个点，其他的大家可以发散一下思维)

我在做xx项目的时候，可能遇到的最大的问题就是xx技术的问题，在处理xx模块的时候，对xx技术的使用不太熟练等。  
再或者是一些细节的错误等，如Redis连接不上SpringBoot等，或者虚拟机配置网关错误等。  
以上只是两个方面，仅供参考，一定要加入自己的思考！

论坛类项目  
今天给大家分享一下论坛类项目的高频问题。

做论坛类项目的朋友也比较多，如仿牛客论坛、仿CSDN、仿博客园等。

这类项目主要涉及到文章或者帖子的发布，所以更多的面试问题是围绕这些实际问题来提问的。

通过一些面经问题和实际的论坛类项目的背景，整理出下面10个高频的项目问题。

论10大高频问题汇总  
1、登录用微信或者QQ登录的方式，说一下有几次交互过程？  
2、怎么同时多篇文章的提交，多个评论的产生，如何解决高并发问题。  
3、项目中的xx技术栈的作用是什么？当时为何没有考虑其他技术栈呢？  
4、对于帖子中的敏感词、评论区的敏感词是如何处理的？  
5、关注、点赞和收藏是否会提醒？如何做到的呢？用了什么技术栈？  
6、ES的功能是什么？如何解决ES和数据库的同步功能？  
7、帖子是否有置顶、加精和删除的功能？置顶是如何实现的？  
8、是否有热榜排序功能？使用的是Redis那个数据结构？  
9、是否做过测试，同时支持多少人发帖？  
10、对于同名的文章怎么处理？会检测恶意刷帖吗？
