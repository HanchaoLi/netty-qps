package practice.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;

import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import practice.config.Config;

public class NettyClient {
    public static void main(String[] args) {
        new NettyClient().run(Config.BEGIN_PORT, Config.END_PORT);
    }

    public void run(int beginPort, int endPort) {
        System.out.println("client starting...!");

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group)
                 .channel(NioSocketChannel.class)
                 .option(ChannelOption.SO_REUSEADDR, true)
                 .handler(new ChannelInitializer<SocketChannel>() {
                     @Override
                     protected void initChannel(SocketChannel ch) throws Exception {

                     }
                 });

        int index = 0;
        int finalPort = 0;
        while (true) {
            finalPort = beginPort + index;
            try {
                bootstrap.connect(Config.SERVER_IP, finalPort).addListener((ChannelFutureListener)future -> {

                    if (!future.isSuccess()) {
                        System.out.println("connection to host failed! Port : ");
                    }

                }).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            ++index;
            if (index == (endPort - beginPort)) {
                index = 0;
            }
        }
    }
}
