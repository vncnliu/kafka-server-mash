package top.vncnliu.carve.kafka.server.mash.tcp.base;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * User: liuyq
 * Date: 7/14/18
 * Description:
 */
public class CustomizeMessageCodec extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {

    }
}
