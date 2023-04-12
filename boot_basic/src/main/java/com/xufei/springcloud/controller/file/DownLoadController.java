package com.xufei.springcloud.controller.file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller
public class DownLoadController {
    private final static String utf8 = "utf-8";
    @RequestMapping("/down")
    public void downLoadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(utf8);
        //定义文件路径
        File file = new File("D:\\File\\a.mp4");
        InputStream is = null;
        OutputStream os = null;
        try {
            //分片下载
            long fSize = file.length();//获取长度
            response.setContentType("application/x-download");
            String fileName = URLEncoder.encode(file.getName(),utf8);
            response.addHeader("Content-Disposition","attachment;filename="+fileName);
            response.setHeader("Accept-Range","bytes");//根据前端传来的Range  判断支不支持分片下载
            response.setHeader("fSize",String.valueOf(fSize));//获取文件大小
            response.setHeader("fName",fileName);
            long pos = 0,last = fSize-1,sum = 0;//定义断点
            //判断前端需不需要分片下载
            if (null != request.getHeader("Range")){
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                String numRange = request.getHeader("Range").replaceAll("bytes=","");
                String[] strRange = numRange.split("-");
                if (strRange.length == 2){
                    pos = Long.parseLong(strRange[0].trim());
                    last = Long.parseLong(strRange[1].trim());
                    //若结束字节超出文件大小 取文件大小
                    if (last>fSize-1){
                        last = fSize-1;
                    }
                }else {
                    //若只给一个长度  开始位置一直到结束
                    pos = Long.parseLong(numRange.replaceAll("-","").trim());
                }
            }
            long rangeLenght = last-pos+1;
            String contentRange = "bytes" + pos + "-" + last + "/" + fSize;
            response.setHeader("Content-Range",contentRange);
            response.setHeader("Content-Lenght",String.valueOf(rangeLenght));
            os = new BufferedOutputStream(response.getOutputStream());
            is = new BufferedInputStream(new FileInputStream(file));
            is.skip(pos);//跳过已读的文件
            byte[] buffer = new byte[1024];
            int lenght = 0;
            //相等证明读完
            while (sum < rangeLenght){
                lenght = is.read(buffer,0, (rangeLenght-sum)<=buffer.length? (int) (rangeLenght - sum) :buffer.length);
                sum = sum+lenght;
                os.write(buffer,0,lenght);
            }
            System.out.println("下载完成");
        }finally {
            if (is!= null){
                is.close();
            }
            if (os!=null){
                os.close();
            }
        }
    }
}