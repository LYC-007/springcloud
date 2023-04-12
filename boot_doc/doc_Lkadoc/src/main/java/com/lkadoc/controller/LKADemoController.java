package com.lkadoc.controller;

import com.lk.api.annotation.*;
import com.lk.api.constant.ContentType;
import com.lkadoc.model.Role;
import com.lkadoc.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @LKARespose/@LKAResposes#用来描述响应参数的信息, 和@LKAParam注解属性用法差不多，常用属性:
 * name/names:参数名称，和type参数二选一【必须】
 * value/values:参数作用【必须】
 * description/descriptions:参数的描述【必须】
 * dataType/dataTypes:参数数据类型，当使用dataTypes不设置也可以自动识别【可选】
 * isArray/isArrays:是否是集合或数组，默认false【可选】
 * type:出参对象类型，和name/names参数二选一，可自动识别【可选】
 * group:和type配合使用，对象参数分组，可过滤没必要的参数【可选】
 * #父参数
 * parentName:父参名称【可选】
 * parentValue:父参作用【可选】
 * parentDescription:父参描述【可选】
 * parentIsArray:父参是否是数组或集合【可选】
 * #爷参数
 * grandpaName:爷参名称【可选】
 * grandpaValue:爷参作用【可选】
 * grandpaDescription:爷参描述【可选】
 * grandpaIsArray:爷参是否是数组或集合【可选】
 *
 * @LKAGroup #@LKAGroup是一个入参注解，作用是指定对象是哪组参数用来作为入参
 *
 */

/**
 * @LKAType#用来描述类的信息，常用属性： value:类的作用【必须】#例如:value="测试类"
 * description:类的描述【可选】#例如:description="该类只是用来测试"
 * hidden:是否在UI界面隐藏该类的信息，默认为false，如果设置为ture那么该类下在的所有接口也会隐藏【可选】#例如:hidden=false
 * order:排序，值越少越靠前【可选】#例如:order=1
 */
@LKAType(value = "用户登录注册模块")
@RestController
@RequestMapping("user")
public class LKADemoController {

    /**
     * @LKAMethod#用来描述接口的信息，常用属性：
     * value:接口的作用【必须】#例如:value="用户登录"
     * description:接口的描述【可选】#例如:description="用于APP端登录的接口"
     * contentType:请求头ContentType类型，默认为application/x-www-form-urlencoded【可选】#例如:contentType="application/json"
     * author:作者【可选】#例如:author="L.K"
     * createTime:接口创建时间【可选】#例如:createTime="2021-08-08"
     * updateTime:接口修改时间【可选】#例如:updateTime="2021-10-01"
     * hidden:是否在UI界面隐藏该接口，默认为false【可选】#例如:hidden=false
     * version:接口版本号，如果项目版本号相同，在UI界面会标记为新接口【可选】#例如:version="1.0"
     * download:是否是下载的方法，如果该接口涉及到下载文件必须设置成true，默认是false【可选】#例如:download=false
     * token:是否需要token验证，只标识该接口需要token验证，不会影响正常业务，默认是true【可选】#例如:token=true
     * order:排序，数字越少越靠前【可选】#例如:order=1
     * directory:指定上级目录（这里目录是指@LKAType的value属性值，上级目录必须存在，不然不会展示在接口文档，默认当前类的目录）【可选】#例如:directory="用户管理"
     */
    @LKAMethod(value = "登录")
    //@LKAParam(names= {"name","pwd"},values= {"用户名","密码"})
    @LKARespose(names = {"code", "msg"}, values = {"状态码", "消息"})
    @PostMapping("login")
    public Map<String, Object> login(@LKAParam(name = "name", value = "用户名") String name, @LKAParam(name = "pwd", value = "密码") String pwd) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("msg", "登录成功，欢迎" + name + "光临本系统");
        return map;
    }


    @LKAMethod(value = "用户注册", description = "APP的注册接口", version = "1.0", createTime = "2021-08-08", author = "LK")
    /**
     * @LKAParam / @LKAParams#用来描述请求参数的信息，2.0.0版开始@LKAParam除了可以用在方法上，还可以用在参数上。带s复数属性代表可以设置多个参数,但要注意参数顺序。带s和不带s设置时只能二选一，建议大家不管是多个参数还是单个参数，都用带s复数属性，带s复数属性要更灵活，更智能。常用属性：
     *
     * name/names:参数名称【必须】（用name设置参数名称时必须;用names设置参数名称时可省略，但JDK版本要1.8以上，编译的时候还要加上–parameters启动参数，这样Lkadoc可自动获取到参数名称,目前测试JDK11不加–parameters参数也可以识别参数名称，否则必须）
     * #例如:
     * #单个参数配置:name="name"
     * #多个参数配置:names={"name","pwd","age"} //这里如果和接口入参顺序一样，可省略不用配置
     * #或者，
     * #@LKAParams({
     *     #@LKAParam(name="name",...),
     *     #@LKAParam(name="pwd",...),
     *     #@LKAParam(name="age",...)
     * #})
     * #注意，@LKAParams用法的其它属性才name都一样，后面不再举例。
     *
     * value/values:参数作用【必须】
     * #例如:
     * #单个参数配置:value="姓名"
     * #多个参数配置:values={"姓名","密码","年龄"}
     *
     * description/descriptions:参数的描述【可选】
     * #例如:
     * #单个参数配置:description="姓名不能超过5个汉字"
     * #多个参数配置:descriptions={"姓名不能超过5个汉字","密码不能少于6位","年龄在18岁和60岁之间"}
     *
     * dataType/dataTypes:数据类型【可选】（用dataType配置时默认值String.class;用dataTypes配置时可自动获取参数的数据类型，可省略不配置，但要注意参数的顺序。）
     * #例如:
     * #单个参数配置:dataType=String.class //这里可省略，因为默认是String
     * #多个参数配置:dataTypes={String.class,Date.class,Integer.class} //如果和接口入参顺序一致可省略自动获取
     *
     * required/requireds:是否必传，默认为true【可选】(更简便的用法是在name属性值后面加"-n"或者在value属性值前面加“n~”代表非必传参数)
     * #例如:
     * #单个参数配置:required=false 或者 name="name-n" 或者 value="n~用户名" //三种方式都代表非必传（3选1）
     * #多个参数配置:#requireds={false,true,false}
     * #或者 names={"name-n","pwd","age-n"}
     * #或者 values={"n~用户名","密码","n~年龄"} //三种方式效果都一样，用户名和年龄非必传，密码必传（3选1）
     *
     * paramType/paramTypes:参数位置，query、header、path三选一【可选】（用paramType配置时默认为query;用paramTypes配置时Lkadoc可根据参数注解@PathVariable、@RequestHeader自动获取参数位置，可省略不配）
     * #例如:
     * #单个参数配置:paramType="query" //默认是query,可以省略不配置
     * #多个参数配置:paramTypes={"query","header","path"} //参数如果加上了@PathVariable、@RequestHeader注解，可自动获取参数位置，可省略不配置
     *
     * isArray/isArrays:该参数是否是集合或数组，默认false【可选】
     * #例如:
     * #单个参数配置:isArray=false
     * #多个参数配置:isArrays={false,true,false}
     *
     * testData/testDatas:测试数据【可选】(更简便的用法是在value后面的"^"符号后面加上测试数据)
     * #例如:
     * #单个参数配置:testData="张三" 或者 value="姓名^张三" //两种方式2选1
     * #多个参数配置:testDatas={"张三","123456","22"}
     * #或者 values={"n~姓名^张三","密码^123456","n~年龄^22"} //两种方式2选1
     *
     * type:入参对象类型【可选】（当接口请求参数是一个对象时使用，但一般不需要设置，可自动识别）
     * #例如:type=User.class  //一般不用配置，可自动识别
     *
     * group:和type配合使用，对象参数分组，可过滤没必要的参数【可选】
     * #例如:group="add"  //add是一个组名，事先在User对象的参数上面配置好的
     */
    @LKAParam(names = {"name", "pwd", "email", "age"}, values = {"用户名^张凡", "密码^123abc", "n~邮箱^123@qq.com", "n~年龄^21"})
    //注意:JDK8及以上@LKAParam注解的names={"name","pwd","email","age"}配置可省略
    @PostMapping("reg")
    public String reg(String name, String pwd, String email, Integer age) {
        if (name == null || "".equals(name) || pwd == null || "".equals(pwd)) {
            return "注册失败";
        }
        return "注册成功";
    }

    @LKAMethod(value = "path和header入参")
    @LKAParam(names = {"token", "name", "email"}, values = {"令牌^aaaa", "n~姓名^瓜瓜", "n~邮箱^123@qq.com"})
    @PostMapping("getUser/{name}/{email}")
    public String getUser(@RequestHeader("token") String token,
                          @PathVariable("name") String name,
                          @PathVariable("email") String email) {

        return "测试结果：token=" + token + ",name=" + name + ",email=" + email;
    }

    //isArrays = {true,false}代表第一个参数是数组，第二参数不是数组
    @LKAMethod(value = "数组入参")
    @LKAParam(names = {"ids", "name"}, values = {"用户ID^1001", "n~姓名^瓜瓜"}, isArrays = {true, false})
    @PostMapping("array")
    public String array(Integer[] ids, String name) {

        return "测试结果：ids=" + Arrays.toString(ids) + ",name=" + name;
    }

    //ContentType.FORMDATA常量的值为multipart/form-data
    @LKAMethod(value = "文件批量上传", contentType = ContentType.FORMDATA)
    @LKAParam(names = "files", values = "上传文件", isArrays = true)
    @PostMapping("fileUpload")
    public String fileUpload(MultipartFile[] files) {
        String fileNames = "";
        if (files != null) {
            for (MultipartFile f : files) {
                if ("".equals(fileNames)) {
                    fileNames = f.getOriginalFilename();
                } else {
                    fileNames = fileNames + "," + f.getOriginalFilename();
                }
            }
        }

        return "上传成功，文件名：" + fileNames;
    }

    //@LKAMethod注解的download=true代表这个方法是一个文件下载的方法，测试这个方法事先要在D盘准备一个test.txt文件。至于文件下载的业务逻辑，这里不做讲解。
    @LKAMethod(value = "文件下载", download = true)
    @PostMapping("fileDownload")
    public void fileDownload(HttpServletResponse response) throws Exception {
        String path = "D:\\test.txt";
        File file = new File(path);
        String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1).toUpperCase();
        InputStream fis = new BufferedInputStream(new FileInputStream(path));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        response.reset();
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes()));
        response.addHeader("Content-Length", "" + file.length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
    }

    @LKAMethod("基本对象入参")
    @GetMapping("getRole")
    public Role getRole(Role role) {
        return role;
    }

    /**
     * 1.复杂的对象需把@LKAMethod注解的contentType属性设置为"application/json"
     * 2.如果contentType="application/json"，需在接收对象参数前面加@RequestBody注解
     * 3.如果contentType="application/json"，那么接口的请求类型不能是get
     */
    @LKAMethod(value = "复杂的对象传参", contentType = ContentType.JSON)
    @PostMapping("addUser")
    public User addUser(@RequestBody User user) {
        return user;
    }

    //在入参对象User前面加一个注解@LKAGroup("getUser")，代表在Lkadoc只需展示组名为getUser的数据
    @LKAMethod(value = "对象参数分组", contentType = ContentType.JSON)
    @PostMapping("getUserArray")
    public User getUserArray(@RequestBody @LKAGroup("getUser") User user) {
        return user;
    }
}
