package com.zw.restaurantmanagementsystem;
import com.rabbitmq.client.*;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
/**
 * 完成简单模式一发一接的结构
 */
public class SimpleMode {
    //初始化连接对象 短连接
    private Channel channel;
    @Before
    public void channelInit() throws IOException, TimeoutException {
        //ip:port tedu/123456
        /*
            1.长链接工厂,提供4个属性 ip port tedu 123456
            2.获取长连接
            3.给成员变量channel赋值
         */
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        channel=connection.createChannel();
    }
    //测试包含3个方法
    //声明组件,交换机和队列,简单模式案例,交换机使用默认交换机.队列需要声明
    @Test
    public void myQueueDeclare() throws IOException {
        //调用channel的方法,声明队列
        channel.queueDeclare(
                "simple",//设置路由key
                false,//boolean类型,队列是否持久化
                false,//boolean类型,队列是否专属,
                // 只有创建声明队列的连接没有断开,队列才可用
                false,//boolean类型,队列是否自动删除.从第一个消费端监听队列开始
                //计算,直到最后一个消费端断开连接,队列就会自动删除
                null);//map类型,key值是固定一批属性
        System.out.println("队列声明成功");
    }
    //发送消息到队列 生产端,永远不会吧消息直接发给队列,发给交换机
    //目前可以使用7个交换机来接收消息
    @Test
    public void send() throws IOException {
        //准备个消息 发送的是byte[]
        String msg="宝贝一到手,风紧扯呼";
        byte[] msgByte=msg.getBytes();
        //将消息发给(AMQP DEFAULT)交换机 名字""
        for (int i = 0; i < 100000000; i++) {
            channel.basicPublish(
                    "",//发送给的交换机的名字,默认为空
                    "simple",//路由key,你想让交换机把消息传递给哪个队列的名称
                    null,//发送消息时,携带的头,属性等.例如
                    // app_id content-type priority优先级
                    msgByte//消息体
            );
        }

    }
    //消费端
    @Test
    public void consume() throws IOException {
        //消费消息
        channel.basicConsume("simple", false,
                new DeliverCallback() {
                    /**传递回调对象. 消息就在这个对象里
                     * @param consumerTag 当前消费端id
                     * @param message 封装了消息的对象
                     * @throws IOException
                     */
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                //从消息对象中拿到信息
                byte[] body = message.getBody();
                System.out.println(new String(body));
                //如果autoAck false说明消费完消息,需要手动确认
                channel.basicAck(
                        message.getEnvelope().getDeliveryTag(),
                        false);
            }
        }, new CancelCallback() {
                    /**
                     * 当连接对象channel 主动关闭消费端连接时 cancel 这个方法才会被调用
                     * @param consumerTag 消费端id
                     * @throws IOException
                     */
            @Override
            public void handle(String consumerTag) throws IOException {
            }
        });
        //使用while true 将线程卡死,否则看不到消息消费逻辑
        while(true);
    }
}