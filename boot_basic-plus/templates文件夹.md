SpringBoot里面没有我们之前常规web开发的 WebContent（WebApp），它只有src目录（正常的maven结构）

在src/main/resources下面有两个文件夹，static和templates。springboot默认static中放静态页面，而templates中放动态页面
静态页面:
    方式一:直接在static（或者讲过的任何一个静态资源文件夹）放一个example.html,然后直接输入http://localhost:8080/example.html便能成功访问
    方式二:可以通过controller控制器跳转：
            @Controller
            public class ExampleController {
                @GetMapping("/example")
                public String sayHello() {
                    return "example.html";
                }
            }
            然后输入http://localhost:8080/example就可以成功访问！
    这两种请求的方式当我们的 DispatcherServlet 在进行处理的时候，差别为根据九大组件之一的 HandlerMapping 返回的请求处理的执行链 HandlerExecutionChain 中的处理器 Handler 不同


动态页面:动态页面需要先请求服务器，访问后台应用程序，然后再转向到页面，比如访问JSP。spring boot建议不要使用JSP，默认使用Thymeleaf模板引擎来做动态页面
    在pom文件中添加Thymeleaf的场景启动器:
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

静态页面的return默认是跳转到/static/index.html，
当在pom.xml中引入了thymeleaf组件，动态跳转会覆盖默认的静态跳转，默认就会跳转到/templates/index.html，注意看两者return代码也有区别，动态没有html后缀。
如果在使用动态页面时还想跳转到/static/index.html，可以使用重定向return “redirect:/example.html”
    