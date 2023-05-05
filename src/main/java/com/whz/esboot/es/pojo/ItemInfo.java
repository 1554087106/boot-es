package com.whz.esboot.es.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author: hong-zhi
 * @date: 2023/4/27 21:20
 * @Description 商品的信息
 * 用来同步商品信息到ES 并作为结果传输的数据结构
 * i.id as itemId,
 * ii.url as imgUrl,
 * i.item_name as itemName,
 * sp.price_discount as price,
 * i.sell_counts sellCounts
 */
@Data
@Document(indexName = "item-info", createIndex = false)
public class ItemInfo {

    @Id
    @Field(store = true, type = FieldType.Keyword)
    private String itemId;

    @Field(store = true, type = FieldType.Keyword)
    private String imgUrl;

    @Field(store = true, type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_smart")
    private String itemName;

    @Field(store = true, type = FieldType.Integer)
    private Integer price;

    @Field(store = true, type = FieldType.Integer)
    private Integer sellCounts;
}
