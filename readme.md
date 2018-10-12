本文会以一个简单而完整的业务来阐述Spring Cloud Finchley.RELEASE版本常用组件的使用。如下图所示，本文会覆盖的组件有：
1.	Spring Cloud Netflix Zuul网关服务器
2.	Spring Cloud Netflix Eureka发现服务器
3.	Spring Cloud Netflix Turbine断路器监控
4.	Spring Cloud Sleuth + Zipkin服务调用监控
5.	Sping Cloud Stream + RabbitMQ做异步消息
6.	Spring Data JPA做数据访问

本文的例子使用的依赖版本是：
1.	Spring Cloud - Finchley.RELEASE
2.	Spring Data - Lovelace-RELEASE
3.	Spring Cloud Stream - Fishtown.M3
4.	Spring Boot - 2.0.5.RELEASE

各项组件详细使用请参见官网，Spring组件版本变化差异较大，网上代码复制粘贴不一定能够适用，最最好的资料来源只有官网+阅读源代码，直接给出地址方便你阅读本文的时候阅读官网的文档：
1.	全链路监控：http://cloud.spring.io/spring-cloud-static/spring-cloud-sleuth/2.0.1.RELEASE/single/spring-cloud-sleuth.html
2.	服务发现、网关、断路器：http://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.0.1.RELEASE/single/spring-cloud-netflix.html 
3.	服务调用：http://cloud.spring.io/spring-cloud-static/spring-cloud-openfeign/2.0.1.RELEASE/single/spring-cloud-openfeign.html
4.	异步消息：https://docs.spring.io/spring-cloud-stream/docs/Fishtown.M3/reference/htmlsingle/
5.	数据访问：https://docs.spring.io/spring-data/jpa/docs/2.1.0.RELEASE/reference/html/

完整文章见 https://juejin.im/post/5bc013f45188255c3050156b
 
