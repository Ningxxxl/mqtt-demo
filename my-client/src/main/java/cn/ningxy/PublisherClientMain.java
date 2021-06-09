package cn.ningxy;

import cn.ningxy.client.SimplePublisher;

import java.io.IOException;

/**
 * @author ningxy
 */
public class PublisherClientMain {
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.out.println("参数 <host> <port> <topic>");
            throw new IllegalArgumentException("参数有误");
        }
        SimplePublisher simplePublisher = new SimplePublisher(args[0], Integer.parseInt(args[1]), args[2]);
        simplePublisher.work();
    }
}
