package com.whz.esboot.es.resposity.customer;

import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import com.whz.esboot.es.pojo.Stu;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.QueryBuilders;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchPage;

/**
 * @author: hong-zhi
 * @date: 2023/4/25 20:22
 * @Description 实现自定义操作ES聚合方法
 */
public class StuCustomRepositoryImpl implements StuCustomRepository {
    private ElasticsearchOperations operations;
    private ElasticsearchTemplate elasticsearchTemplate;
//    private ElasticsearchTemplate

    public StuCustomRepositoryImpl(ElasticsearchOperations operations) {
        this.operations = operations;
    }
    @Override
    public SearchPage<Stu> groupByRealname() {

//        new SearchSourceBuilder();
        /**
         * SearchQuery searchQuery = new NativeSearchQueryBuilder()
         *     .withQuery(QueryBuilders.matchAllQuery())
         *     .addAggregation(AggregationBuilders.terms("group_by_field").field("field_name"))
         *     .build();
         *
         * Aggregations aggregations = elasticsearchTemplate.query(searchQuery,
         *     response -> response.getAggregations());
         *
         * Terms terms = aggregations.get("group_by_field");
         * List<? extends Terms.Bucket> buckets = terms.getBuckets();
         *
         * for (Terms.Bucket bucket : buckets) {
         *     System.out.println(bucket.getKeyAsString() + " - " + bucket.getDocCount());
         * }
         */
//        new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery())
//                .addAggregation(AggregationBuilders.terms("group_by_field").field("name")
//                .build();
        return null;
    }
}
