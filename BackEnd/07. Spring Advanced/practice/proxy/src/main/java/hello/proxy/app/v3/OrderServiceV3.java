package hello.proxy.app.v3;

import hello.proxy.app.v2.OrderRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepository;
    public String orderItem(String itemId) {
        orderRepository.save(itemId);
        return itemId;
    }

}
