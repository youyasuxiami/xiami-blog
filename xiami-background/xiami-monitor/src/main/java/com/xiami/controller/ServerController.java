package com.xiami.controller;

import com.xiami.annotation.OperatorLog;
import com.xiami.base.ResponseResult;
import com.xiami.server.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/monitor/server")
/**
 * 服务器监控
 */
public class ServerController {

    @GetMapping()
    @OperatorLog("服务器监控")
    public ResponseResult getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return ResponseResult.ok(server,"获取服务器信息成功");
    }
}
