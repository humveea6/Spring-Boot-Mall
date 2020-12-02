package com.imooc.mall.grpcdemo;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-08-24
 */
public class GrpcService {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(9090)
                .addService(new UserInfoService())
                .build()
                .start();
        System.out.println("Server start at: "+server.getPort());

        server.awaitTermination();
    }
}
