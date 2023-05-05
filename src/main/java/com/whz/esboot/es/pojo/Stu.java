package com.whz.esboot.es.pojo;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author: hongzhi
 * @date: 2023/4/24 14:24
 * @Description 待存入Elasticsearch的数据结构
 */
@Data
@Builder
@Document(indexName = "stu")
public class Stu {
    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String name;

    @Field(type = FieldType.Integer)
    private Long age;

    @Field(type = FieldType.Auto)
    private String realname;

    /**
     * 将Stu对象转换为JSON字符串
     * @return
     */
    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
