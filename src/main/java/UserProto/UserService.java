package UserProto;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import protobuf.LoginRequest;
import protobuf.Logout;
import protobuf.Response;
import protobuf.userGrpc;

/**
 * @author LingChen @HumveeA6
 * Created on 2020-12-07
 */
@Slf4j
public class UserService extends userGrpc.userImplBase {

    @Override
    public void login(LoginRequest request, StreamObserver<Response> responseObserver) {
        log.info("login api enter");
        String username = request.getUsername();
        String pwd = request.getPwd();

        Response.Builder response = Response.newBuilder();
        if(username.equals(pwd)){
            response.setCode(200).setMsg("Login success!");
        }
        else{
            response.setCode(400).setMsg("Login fail!");
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void logout(Logout request, StreamObserver<Response> responseObserver) {
        super.logout(request, responseObserver);
    }
}
