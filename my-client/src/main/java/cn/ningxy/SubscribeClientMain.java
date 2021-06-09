package cn.ningxy;

import cn.ningxy.client.SimpleSubscriber;

/**
 * @author ningxy
 */
public class SubscribeClientMain {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("参数 <host> <port> <topic>");
            throw new IllegalArgumentException("参数有误");
        }
        SimpleSubscriber simpleSubscriber = new SimpleSubscriber(args[0], Integer.parseInt(args[1]), args[2]);
        simpleSubscriber.work();
    }
}
