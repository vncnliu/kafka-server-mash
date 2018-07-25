package top.vncnliu.carve.server.mash.kafka.store.chain;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import top.vncnliu.server.mash.base.BackEvent;
import top.vncnliu.server.mash.base.Constant;
import top.vncnliu.server.mash.base.MashResp;
import top.vncnliu.server.mash.base.TestEvent;

/**
 * User: liuyq
 * Date: 2018/7/25
 * Description:
 */
class TestEventHandler {
    @Subscribe
    public void handleCash(CashEvent baseEvent) {
        System.out.println("CashEvent handler ");
        //System.out.println(baseEvent.getContext());
        baseEvent.getRespFuture().complete(new MashResp(Constant.ErrorCode.ORDER_ERROR,"CashEvent"));
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
    @Subscribe
    public void baseHandle(BackEvent baseEvent) {
        System.out.println("BackEvent handler ");
        baseEvent.getRespFuture().complete(new MashResp(Constant.ErrorCode.SUCCESS,"BackEvent "+baseEvent.getId()));
    }
    @Subscribe
    public void baseHandle(DeadEvent baseEvent) {
        System.out.println("DeadEvent handler ");
    }
    @Subscribe
    public void baseHandle(TestEvent baseEvent) {
        System.out.println("TestEvent handler ");
    }
}
