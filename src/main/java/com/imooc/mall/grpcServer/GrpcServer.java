package com.imooc.mall.grpcServer;

import UserProto.UserService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LingChen @HumveeA6
 * Created on 2020-12-07
 */
@Slf4j
public class GrpcServer {

    public static void main(String[] args) {
        Server server = ServerBuilder.forPort(9090).addService(new UserService()).build();
        try {
            server.start();
            log.info("server started! Port: "+server.getPort());

            server.awaitTermination();
        }
        catch (Exception e){
            log.info("Server error: "+e);
        }
    }
}
