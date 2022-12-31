package tk.mwacha;

import io.grpc.ServerBuilder;
import tk.mwacha.service.CategoryService;

import java.io.IOException;


public class Server {

    public static void main(String[] args) {
        int port = 8080;
        var server = ServerBuilder
                .forPort(port)
                .addService(new CategoryService()).build();

        try {
            server.start();
            System.out.println("Server UP on " + port + "  port");
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            System.out.println("Server error " + e.getMessage());
        }
    }
}
