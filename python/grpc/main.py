# 这是一个示例 Python 脚本。

# 按 Shift+F10 执行或将其替换为您的代码。
# 按 按两次 Shift 在所有地方搜索类、文件、工具窗口、操作和设置。
import grpc

from proto import test_pb2_grpc, test_pb2

# 按间距中的绿色按钮以运行脚本。
if __name__ == '__main__':
    channel = grpc.insecure_channel('127.0.0.1:9999')
    stub = test_pb2_grpc.RPCDateServiceStub(channel=channel)
    response = stub.getDate(test_pb2.RPCDateRequest(userName='grpc'))
    print("Greeter client received: " + response.serverDate)
