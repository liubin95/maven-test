package grpc;

import io.grpc.stub.StreamObserver;

/**
 * MyGrpcService.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-10-22 : base version.
 */
public class MyGrpcService extends RPCDateServiceGrpc.RPCDateServiceImplBase {

  @Override
  public void getDate(
      Test.RPCDateRequest request, StreamObserver<Test.RPCDateResponse> responseObserver) {
    final String userName = request.getUserName();
    System.out.println("request.getUserName() = " + userName);
    final Test.RPCDateResponse response =
        Test.RPCDateResponse.newBuilder().setServerDate("hello " + userName).build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
