package top.vncnliu.carve.server.mash.kafka.store.chain;

import com.google.common.eventbus.Subscribe;
import top.vncnliu.server.mash.base.BaseEvent;
import top.vncnliu.server.mash.base.Constant;
import top.vncnliu.server.mash.base.MashResp;

/**
 * User: liuyq
 * Date: 2018/7/25
 * Description:
 */
class TestEventHandler {
    @Subscribe
    public void handleCash(CashEvent baseEvent) {
        System.out.println("CashEvent handler ");
        baseEvent.getRespFuture().complete(new MashResp(Constant.ErrorCode.SUCCESS,"CashEvent"));
    }
    @Subscribe
    public void handleOrder(OrderEvent baseEvent) {
        System.out.println("OrderEvent handler ");
        baseEvent.getRespFuture().complete(new MashResp(Constant.ErrorCode.SUCCESS,"OrderEvent"));
    }
    @Subscribe
    public void handleInventory(InventoryEvent baseEvent) {
        System.out.println("InventoryEvent handler ");
        baseEvent.getRespFuture().complete(new MashResp(Constant.ErrorCode.SUCCESS,"InventoryEvent"));
    }
    @Subscribe
    public void baseHandle(BaseEvent baseEvent) {
        System.out.println("handler ");
        baseEvent.getRespFuture().complete(new MashResp(Constant.ErrorCode.SUCCESS,"base"));
    }
}
