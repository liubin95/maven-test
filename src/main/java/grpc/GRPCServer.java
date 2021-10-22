package grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * GRPCServer.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2021-10-22 : base version.
 */
public class GRPCServer {

  private static final int PORT = 9999;

  public static void main(String[] args) throws Exception {
    // 设置service接口.
    Server server = ServerBuilder.forPort(PORT).addService(new MyGrpcService()).build().start();
    System.out.printf("GRpc服务端启动成功, 端口号: %d.%n", PORT);
    server.awaitTermination();
  }
}
