package hello.exception.servlet;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class ErrorPageController {

    public static final String ERROR_EXCEPTION = "jakarta.servlet.error.exception";

    /**
     * The name of the request attribute that should be set by the container when custom error-handling servlet or JSP
     * page is invoked. The value of the attribute is of type {@code java.lang.Class}. See the chapter "Error Handling"
     * in the Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String ERROR_EXCEPTION_TYPE = "jakarta.servlet.error.exception_type";

    /**
     * The name of the request attribute that should be set by the container when custom error-handling servlet or JSP
     * page is invoked. The value of the attribute is of type {@code String}. See the chapter "Error Handling" in the
     * Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String ERROR_MESSAGE = "jakarta.servlet.error.message";

    /**
     * The name of the request attribute that should be set by the container when custom error-handling servlet or JSP
     * page is invoked. The value of the attribute is of type {@code String}. See the chapter "Error Handling" in the
     * Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String ERROR_REQUEST_URI = "jakarta.servlet.error.request_uri";

    /**
     * The name of the request attribute that should be set by the container when custom error-handling servlet or JSP
     * page is invoked. The value of the attribute is of type {@code String}. See the chapter "Error Handling" in the
     * Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String ERROR_SERVLET_NAME = "jakarta.servlet.error.servlet_name";

    /**
     * The name of the request attribute that should be set by the container when custom error-handling servlet or JSP
     * page is invoked. The value of the attribute is of type {@code java.lang.Integer}. See the chapter "Error
     * Handling" in the Servlet Specification for details.
     *
     * @since Servlet 3.0
     */
    public static final String ERROR_STATUS_CODE = "jakarta.servlet.error.status_code";

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletRequest response) {
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletRequest response) {
        log.info("errorPage 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    private void printErrorInfo(HttpServletRequest request) {
        log.info(request.getAttribute(ERROR_EXCEPTION).toString());
        log.info(request.getAttribute(ERROR_EXCEPTION_TYPE).toString());
        log.info(request.getAttribute(ERROR_MESSAGE).toString());
        log.info(request.getAttribute(ERROR_REQUEST_URI).toString());
        log.info(request.getAttribute(ERROR_STATUS_CODE).toString());
        log.info(request.getDispatcherType().toString());
    }
}
