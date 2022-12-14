package ru.job4j.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EchoServer {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    String str = in.readLine();
                    while (!(str).isEmpty()) {
                        System.out.println(str);
                        if (str.startsWith("GET /?")) {
                            String[] arguments = str.split("[= ]");
                            out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                            if (arguments[2].equals("Exit")) {
                                out.write("Sever closed.".getBytes());
                                socket.close();
                                server.close();
                                break;
                            } else if (arguments[2].equals("Hello")) {
                                out.write("Hello, dear friend.".getBytes());
                            } else {
                                out.write("Illegal argument".getBytes());
                            }
                        }
                        str = in.readLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("Server exception", e);
        }
    }
}
