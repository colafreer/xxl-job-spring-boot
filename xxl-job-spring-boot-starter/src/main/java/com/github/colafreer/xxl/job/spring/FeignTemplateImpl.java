package com.github.colafreer.xxl.job.spring;

import com.github.colafreer.xxl.job.spring.config.XxlJobAdminProperties;
import com.github.colafreer.xxl.job.spring.model.XxlJobInfo;
import com.github.colafreer.xxl.job.spring.model.XxlJobPageParam;
import com.xxl.job.core.biz.model.ReturnT;
import feign.*;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author ren
 * @since 2023/12/19 00:29
 */
@Slf4j
public class FeignTemplateImpl implements AdminTemplate {

    private XxlJobAdminProperties properties;
    private AdminApi adminApi;
    private volatile String COOKIE;

    public FeignTemplateImpl(XxlJobAdminProperties properties) {
        this.properties = properties;
        this.adminApi = Feign.builder()
                .decoder(new JacksonDecoder())
                .encoder(new FormEncoder(new JacksonEncoder()))
                .requestInterceptor(new CookieInterceptor())
                .target(AdminApi.class, properties.getAdminAddress());
        log.info("login to {}", properties.getAdminAddress());
        refreshCookie();
    }

    /**
     * login to xxl-job admin
     * </p>
     * cause feign impl couldn't get Set-Cookie header, so we use restTemplate to login
     */
    private void refreshCookie() {
        String url = properties.getAdminAddress() + "/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("userName", properties.getUsername());
        map.add("password", properties.getPassword());
        map.add("ifRemember", "on");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = new RestTemplate().postForEntity(url, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            COOKIE = response.getHeaders().getFirst("Set-Cookie");
        } else {
            throw new RuntimeException("xxl-job admin login failed");
        }
    }


    @Override
    public Integer add(XxlJobInfo info) {
        return adminApi.add(info);
    }

    @Override
    public Map<String, Object> pageList(XxlJobPageParam param) {
        return adminApi.pageList(param);
    }

    @Override
    public ReturnT<String> update(XxlJobInfo info) {
        return adminApi.update(info);
    }

    @Override
    public ReturnT<String> remove(int id) {
        return adminApi.remove(id);
    }

    @Override
    public ReturnT<String> stop(int id) {
        return adminApi.stop(id);
    }

    @Override
    public ReturnT<String> start(int id) {
        return adminApi.start(id);
    }

    public interface AdminApi {
        @RequestLine("POST /login")
        Response login(@Param("userName") String userName,
                       @Param("password") String password,
                       @Param("ifRemember") String ifRemember);

        @RequestLine("POST /jobinfo/add")
        @Headers("Content-Type: application/x-www-form-urlencoded")
        Integer add(XxlJobInfo info);

        @Headers("Content-Type: application/x-www-form-urlencoded")
        @RequestLine("POST /jobinfo/pageList")
        Map<String, Object> pageList(XxlJobPageParam param);

        @Headers("Content-Type: application/x-www-form-urlencoded")
        @RequestLine("POST /jobinfo/update")
        ReturnT<String> update(XxlJobInfo info);

        @Headers("Content-Type: application/x-www-form-urlencoded")
        @RequestLine("POST /jobinfo/remove")
        ReturnT<String> remove(int id);

        @Headers("Content-Type: application/x-www-form-urlencoded")
        @RequestLine("POST /jobinfo/stop")
        ReturnT<String> stop(int id);

        @Headers("Content-Type: application/x-www-form-urlencoded")
        @RequestLine("POST /jobinfo/start")
        ReturnT<String> start(int id);

    }

    class CookieInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate template) {
            if (COOKIE != null) {
                template.header("Cookie", COOKIE);
            }
        }
    }
}
