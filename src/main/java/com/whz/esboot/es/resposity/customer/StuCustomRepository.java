package com.whz.esboot.es.resposity.customer;

import com.whz.esboot.es.pojo.Stu;
import org.springframework.data.elasticsearch.core.SearchPage;

/**
 * @author: hong-zhi
 * @date: 2023/4/25 20:17
 * @Description 定义JPA中不包含的操作Elasticsearch的接口
 */
public interface StuCustomRepository {
    /**
     * 按照real-name进行分组
     * @return
     */
    SearchPage<Stu> groupByRealname();
}
