package com.xufei.file.zero;

import org.junit.Test;

import java.io.File;
public class FileTest {
    /*
    1.如何创建File类的实例
        File(String filePath)
        File(String parentPath,String childPath)
        File(File parentFile,String childPath)
    2.相对路径：相较于某个路径下，指明的路径。绝对路径：包含盘符在内的文件或文件目录的路径
    3.路径分隔符: windows:\\     unix:/
     */
    @Test
    public void test1(){
        //构造器1
        File file1 = new File("hello.txt");//相对于当前module
        File file2 =  new File("D:\\workspace_idea1\\JavaSenior\\day08\\he.txt");
        File file3 = new File("d:" + File.separator + "atguigu" + File.separator + "info.txt");
        //构造器2：
        File file4 = new File("D:\\workspace_idea1","JavaSenior");
        //构造器3：
        File file5 = new File(file3,"hi.txt");
    }
    /*
        public static final String separator。根据操作系统，动态的提供分隔符。
        public String getAbsolutePath()：获取绝对路径
        public String getPath() ：获取路径
        public String getName() ：获取名称
        public String getParent()：获取上层文件目录路径。若无，返回null
        public long length() ：获取文件长度（即：字节数）。不能获取目录的长度。
        public long lastModified() ：获取最后一次的修改时间，毫秒值

        如下的两个方法适用于文件目录：
        public String[] list() ：获取指定目录下的所有文件或者文件目录的名称数组
        public File[] listFiles() ：获取指定目录下的所有文件或者文件目录的File数组

        File类的判断功能
        public boolean isDirectory()：判断是否是文件目录
        public boolean isFile() ：判断是否是文件
        public boolean exists() ：判断是否存在
        public boolean canRead() ：判断是否可读
        public boolean canWrite() ：判断是否可写
        public boolean isHidden() ：判断是否隐藏
        File类的创建功能
        public boolean createNewFile() ：创建文件。若文件存在，则不创建，返回false
        public boolean mkdir() ：创建文件目录。如果此文件目录存在，就不创建了。如果此文件目录的上层目录不存在，也不创建。
        public boolean mkdirs() ：创建文件目录。如果上层文件目录不存在，一并创建;注意事项：如果你创建文件或者文件目录没有写盘符路径，那么，默认在项目路径下。
        File类的删除功能
        public boolean delete()：删除文件或者文件夹删除注意事项：Java中的删除不走回收站。要删除一个文件目录，请注意该文件目录内不能包含文件或者文件目录
        public boolean renameTo(File dest):把文件重命名为指定的文件路径;比如：file1.renameTo(file2)为例：要想保证返回true,需要file1在硬盘中是存在的，且file2不能在硬盘中存在。
     */
}
