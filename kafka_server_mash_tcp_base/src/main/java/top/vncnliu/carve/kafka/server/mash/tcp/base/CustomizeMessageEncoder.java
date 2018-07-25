package top.vncnliu.carve.kafka.server.mash.tcp.base;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;

/**
 * User: liuyq
 * Date: 7/14/18
 * Description:
 */
public class CustomizeMessageEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) {
        String jsonString = JSON.toJSONString(msg);
        ctx.write(jsonString.length());
        ctx.write(jsonString.getBytes(Charset.forName("utf-8")));
    }
}
