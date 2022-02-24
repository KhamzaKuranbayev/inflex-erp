package ai.ecma.appgateway.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class RequestThrottleFilter implements WebFilter {

    private static final Map<String, Integer> REQUEST_LIMITS_BY_METHOD = new HashMap<>();

    private static final int BLOCKING_DURATION = 5; //NECHI SEKUND ICHIDA?

    private static LoadingCache<String, Map<String, Integer>> requestCountsPerIpAddress;

    public RequestThrottleFilter() {
        super();
        REQUEST_LIMITS_BY_METHOD.put(RestConstants.GET, 50);
        REQUEST_LIMITS_BY_METHOD.put(RestConstants.POST, 5);
        REQUEST_LIMITS_BY_METHOD.put(RestConstants.PATCH, 5);
        REQUEST_LIMITS_BY_METHOD.put(RestConstants.PUT, 5);
        REQUEST_LIMITS_BY_METHOD.put(RestConstants.DELETE, 5);
        requestCountsPerIpAddress = CacheBuilder.newBuilder().
                expireAfterWrite(BLOCKING_DURATION, TimeUnit.SECONDS).build(new CacheLoader<>() {
            public Map<String, Integer> load(String key) {
                return new HashMap<>();
            }
        });

    }


    private boolean isMaximumRequestsPerSecondExceeded(Map<String, String> map) {
        Integer requestCount;
        Map<String, Integer> allRequestsThisIpAddress;
        try {
            allRequestsThisIpAddress = requestCountsPerIpAddress.get(map.get(RestConstants.IP_ADDRESS));
            requestCount = allRequestsThisIpAddress.getOrDefault(map.get(RestConstants.METHOD), 0);
            requestCount++;
            if (requestCount > REQUEST_LIMITS_BY_METHOD.get(map.get(RestConstants.METHOD))) {
                allRequestsThisIpAddress.put(map.get(RestConstants.METHOD), requestCount);
                requestCountsPerIpAddress.put(map.get(RestConstants.IP_ADDRESS), allRequestsThisIpAddress);
                return true;
            }
        } catch (ExecutionException e) {
            return false;
        }
        allRequestsThisIpAddress.put(map.get(RestConstants.METHOD), requestCount);
        requestCountsPerIpAddress.put(map.get(RestConstants.IP_ADDRESS), allRequestsThisIpAddress);
        return false;
    }

    public Map<String, String> getClientIP(ServerWebExchange request) {
//        List<String> xfHeader = new ArrayList<>(Collections.singletonList(request.getHeader("X-Forwarded-For")));
        Map<String, String> map = new HashMap<>();
        map.put(RestConstants.METHOD, Objects.requireNonNull(request.getRequest().getMethod()).name());
        map.put(RestConstants.IP_ADDRESS, Objects.requireNonNull(request.getRequest().getRemoteAddress()).getHostString());
        return map;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange,
                             WebFilterChain webFilterChain) {
        if (isMaximumRequestsPerSecondExceeded(getClientIP(serverWebExchange))) {
            return Mono.error(() -> new TooManyRequestsException("Too many requests"));
        }
        return webFilterChain.filter(serverWebExchange);
    }
}
