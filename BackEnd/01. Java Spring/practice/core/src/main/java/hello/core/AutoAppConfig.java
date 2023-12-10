package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// @Component가 달려있는 클래스들을 모두 가져다가 빈으로 등록해준다.
@ComponentScan(
//        basePackages = "com.hello.core.member",
        // 예제 코드 때문에 AppConfig 만들어놓은 거 빼기 위해..
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
