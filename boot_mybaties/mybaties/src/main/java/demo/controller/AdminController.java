package demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import demo.commom.BaseController;
import demo.commom.page.TableDataInfo;
import demo.domain.Admin;
import demo.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


//http://localhost:8003/admin/finAll
@RequestMapping("/admin")
@RestController
public class AdminController extends BaseController {

    @Autowired
    AdminMapper adminMapper;
    @GetMapping("/finAll")
    public void findAll(){
        ArrayList<Admin> arrayList= adminMapper.findAll();
        for (Admin admin :arrayList) {
            System.out.println(admin);
        }
    }

    @PostMapping("/insert")
    public void  insertAdmin(){
        Admin admin = new Admin();
        admin.setPassword("1234");
        admin.setUserName("xufei");
        adminMapper.insertOneAdmin01(admin);
    }

    /**
     * 分页查询测试代码
     * @return
     */

    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<Admin> list =adminMapper.findAll();
        TableDataInfo dataTable = getDataTable(list);
        System.out.println(dataTable.toString());

        return getDataTable(list);
    }


    /**
     * 分页查询测试代码
     */
    @GetMapping("/listAdmin")
    public TableDataInfo adminList() {   //这种方法需要配置 分页插件

        // 设置第几条记录开始，多少页记录为一页
        PageHelper.startPage(1,5);
        // 获取 User，sql 语句为 select * from tb_user
        // 因为已经注册了 PageHelper插件，所以 PageHelper会在原 sql 语句上增加 limit，从而实现分页
        List<Admin> list =adminMapper.findAll();
        PageInfo<Admin> adminPageInfo=new PageInfo<>(list);

        int pages = adminPageInfo.getPages();
        //.........

        TableDataInfo dataTable = getDataTable(list);
        return getDataTable(list);
    }


}
