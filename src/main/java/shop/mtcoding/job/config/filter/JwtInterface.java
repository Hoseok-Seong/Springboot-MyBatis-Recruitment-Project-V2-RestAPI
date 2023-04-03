package shop.mtcoding.job.config.filter;

import java.util.List;

public interface JwtInterface {
    List<String> whiteUrlList = List.of("/ns/", "/swagger-ui/", "/v3/api-docs/", "/v3/api-docs");
}
