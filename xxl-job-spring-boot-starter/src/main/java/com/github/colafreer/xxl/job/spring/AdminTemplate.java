package com.github.colafreer.xxl.job.spring;

import com.github.colafreer.xxl.job.spring.model.XxlJobInfo;
import com.github.colafreer.xxl.job.spring.model.XxlJobPageParam;
import com.xxl.job.core.biz.model.ReturnT;

import java.util.Map;

/**
 * @author ren
 * @since 2023/12/19 00:02
 */
public interface AdminTemplate {
    Integer add(XxlJobInfo info);

    Map<String, Object> pageList(XxlJobPageParam param);

    ReturnT<String> update(XxlJobInfo info);

    ReturnT<String> remove(int id);

    ReturnT<String> stop(int id);

    ReturnT<String> start(int id);

}
