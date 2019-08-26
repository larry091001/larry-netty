gRPC 地址：https://www.grpc.io/docs/quickstart/java/

<h2>Download the example</h2>
You’ll need a local copy of the example code to work through this quickstart. Download the example code from our GitHub repository (the following command clones the entire repository, but you just need the examples for this quickstart and other tutorials):
=====================操作步骤=======================
$ # Clone the repository at the latest release to get the example code:
$ git clone -b v1.23.0 https://github.com/grpc/grpc-java
$ # Navigate to the Java examples:
$ cd grpc-java/examples
============================================
<h2>Run a gRPC application</h2>
From the examples directory:
1.Compile the client and server
$ ./gradlew installDist
 
1.Run the server
$ ./build/install/examples/bin/hello-world-server

1.In another terminal, run the client
$ ./build/install/examples/bin/hello-world-client

===============================================
<h2>Update a gRPC service</h2>
Now let’s look at how to update the application with an extra method on the server for the client to call. Our gRPC service is defined using protocol buffers; you can find out lots more about how to define a service in a .proto file in gRPC Basics: Java. For now all you need to know is that both the server and the client “stub” have a SayHello RPC method that takes a HelloRequest parameter from the client and returns a HelloReply from the server, and that this method is defined like this:
// The greeting service definition.
service Greeter {
  // Sends a greeting
  rpc SayHello (HelloRequest) returns (HelloReply) {}
}

// The request message containing the user's name.
message HelloRequest {
  string name = 1;
}

// The response message containing the greetings
message HelloReply {
  string message = 1;
}

Let’s update this so that the Greeter service has two methods. Edit src/main/proto/helloworld.proto and update it with a new SayHelloAgain method, with the same request and response types:
// The greeting service definition.
service Greeter {
  // Sends a greeting
  rpc SayHello (HelloRequest) returns (HelloReply) {}
  // Sends another greeting
  rpc SayHelloAgain (HelloRequest) returns (HelloReply) {}
}

// The request message containing the user's name.
message HelloRequest {
  string name = 1;
}

// The response message containing the greetings
message HelloReply {
  string message = 1;
}

<h2>Update and run the application</h2>
When we recompile the example, normal compilation will regenerate GreeterGrpc.java, which contains our generated gRPC client and server classes. This also regenerates classes for populating, serializing, and retrieving our request and response types.
However, we still need to implement and call the new method in the human-written parts of our example application.

<h2>Update the server</h2>
In the same directory, open src/main/java/io/grpc/examples/helloworld/HelloWorldServer.java. Implement the new method like this:

private class GreeterImpl extends GreeterGrpc.GreeterImplBase {

  @Override
  public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
    HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }

  @Override
  public void sayHelloAgain(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
    HelloReply reply = HelloReply.newBuilder().setMessage("Hello again " + req.getName()).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }
}
...

<h2>Update the client</h2>
In the same directory, open src/main/java/io/grpc/examples/helloworld/HelloWorldClient.java. Call the new method like this:
public void greet(String name) {
  logger.info("Will try to greet " + name + " ...");
  HelloRequest request = HelloRequest.newBuilder().setName(name).build();
  HelloReply response;
  try {
    response = blockingStub.sayHello(request);
  } catch (StatusRuntimeException e) {
    logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
    return;
  }
  logger.info("Greeting: " + response.getMessage());
  try {
    response = blockingStub.sayHelloAgain(request);
  } catch (StatusRuntimeException e) {
    logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
    return;
  }
  logger.info("Greeting: " + response.getMessage());
}

<h2>Run!</h2>
Just like we did before, from the examples directory:

1.Compile the client and server
  $ ./gradlew installDist
1.Run the server
  $ ./build/install/examples/bin/hello-world-server
1.In another terminal, run the client
  $ ./build/install/examples/bin/hello-world-client
