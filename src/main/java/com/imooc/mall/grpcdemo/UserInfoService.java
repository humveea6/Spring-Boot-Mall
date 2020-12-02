package com.imooc.mall.grpcdemo;


import com.imooc.mall.grpc.Userinfo;
import com.imooc.mall.grpc.userinfoGrpc;
import io.grpc.stub.StreamObserver;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-08-24
 */
public class UserInfoService extends userinfoGrpc.userinfoImplBase {

    @Override
    public void login(Userinfo.LoginRequest request, StreamObserver<Userinfo.Response> responseObserver) {
        System.out.println("Login request reach!");
        String username = request.getUsername();
        String pwd = request.getPwd();
        System.out.println(String.format("Username: %s , pwd: %s",username,pwd));

        Userinfo.Response.Builder builder = Userinfo.Response.newBuilder();
        if("admin".equals(username)){
            builder.setResCode(0)
                    .setResMsg("admin!!!");
        }
        else{
            builder.setResCode(1)
                    .setResMsg("not admin!");
        }

        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void logout(Userinfo.Empty request, StreamObserver<Userinfo.Response> responseObserver) {
        System.out.println("Logout request reach!");
    }
}
