package com.whz.esboot.es.bo;

import lombok.Data;
/**
 * @author: hongzhi
 * @date: 2023/4/24 14:24
 * @Description 待存入Elasticsearch的数据结构
 */
@Data
public class StuGroup {
    private String name;
    private Long count;
}
