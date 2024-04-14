package hello.proxy.app.v1;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderControllerV1Impl implements OrderControllerV1 {
    private final OrderServiceV1 orderService;
    @Override
    public String request(String itemId) {
        return orderService.orderItem(itemId);
    }

    @Override
    public String noLog() {
        return "ok";
    }
}
