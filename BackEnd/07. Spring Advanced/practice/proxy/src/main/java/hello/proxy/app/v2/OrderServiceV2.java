package hello.proxy.app.v2;

import hello.proxy.app.v1.OrderRepositoryV1;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    public String orderItem(String itemId) {
        orderRepository.save(itemId);
        return itemId;
    }
}
