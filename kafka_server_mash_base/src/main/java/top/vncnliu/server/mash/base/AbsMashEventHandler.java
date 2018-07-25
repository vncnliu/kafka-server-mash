/*
package top.vncnliu.server.mash.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

*/
/**
 * User: liuyq
 * Date: 2018/7/25
 * Description:
 *//*

@Slf4j
class AbsMashEventHandler {
    @Subscribe
    public void baseHandle(AbsMashEvent baseEvent) {
        log.debug("handle event: {}",JSON.toJSONString(baseEvent,SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue));
        baseEvent.getRespFuture().complete(new MashResp<>());
    }
}
*/
