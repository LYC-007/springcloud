package com.fileupload.controller;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileUpLoadController {

    @GetMapping("/get")
    public void test(){

    }
    @PostMapping("/post")
    public void test1(){

    }
    @PostMapping("/post1")
    public void test2(){

    }

}
