package com.whz.esboot;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.util.ObjectBuilder;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * 分页查询测试
 */
@Slf4j
@SpringBootTest(classes = EsBootApplication.class)
class EsAggregationsTests {
    @Autowired
    private ElasticsearchClient elasticsearchClient;

//    private final DocTestsTransport transport = new DocTestsTransport();
//    private final ElasticsearchClient esClient = new ElasticsearchClient(transport);
//    private static final SearchResponse<JsonData> searchResponse = SearchResponse.of(b -> b
//            .aggregations(new HashMap<>())
//            .took(0)
//            .timedOut(false)
//            .hits(h -> h
//                    .total(t -> t.value(0).relation(TotalHitsRelation.Eq))
//                    .hits(new ArrayList<>())
//            )
//            .shards(s -> s
//                    .total(1)
//                    .failed(0)
//                    .successful(1)
//            )
//            .aggregations("real_name_group", a -> )
//                    /**
//                     * aggregations("real_name_group", a -> a // <3>
//                     * ////                                .histogram(h -> h // <4>
//                     * ////                                        .field("price")
//                     * ////                                        .interval(50.0)
//                     * //                                        .terms(h -> h.field("realname"))
//                     * ////                                )
//                     * //                        )
//                     */
////            .aggregations("real_name_group", a -> a.multiTerms(f -> f.))
////            .aggregations("price-histogram", a -> a.histogram(h -> h
////                    .buckets(bu -> bu.array(Collections.singletonList(HistogramBucket.of(hb -> hb
////                            .key(50).docCount(1)
////                    ))))
////            ))
//
//    );

    /**
     * 实现分页查询
     */
    @Test
    public void groupByRealname() throws IOException {

//        Object stuList = studentRepository.groupByRealname();
//        Gson gson = new Gson();
//        log.info("按照realname进行分组的结果: {}", gson.toJson(stuList));

//        Function<SearchRequest.Builder, ObjectBuilder<SearchRequest>> fn =
//                f -> f.aggregations("real_name_group",
//                        a -> a.terms(t -> t.field("realname")));
//        SearchRequest searchRequest = SearchRequest.of(fn);
//        SearchResponse<Stu> searchResponse = esClient.search(fn, Stu.class);
//        log.info("searchResponse/: {}", new Gson().toJson(searchResponse));
//        transport.setResult(searchRequest);

        //tag::price-histo-request
//        String searchText = "bike";
//
//        Query query = MatchQuery.of(m -> m
//                .field("name")
//                .query(searchText)
//        )._toQuery();

//        SearchResponse<Stu> response = esClient.search(b -> b
//                        .index("stu")
//                        .size(0) // <1>
////                        .query(query) // <2>
//                        .aggregations("real_name_group", a -> a // <3>
////                                .histogram(h -> h // <4>
////                                        .field("price")
////                                        .interval(50.0)
//                                        .terms(h -> h.field("realname"))
////                                )
//                        ),
//                Stu.class // <5>
//        );
//        Aggregate aggregate = response.aggregations().get("real_name_group");
//        JsonData jsonData = aggregate._custom();
//        log.info("response: {}", new Gson().toJson(response));
        //end::price-histo-request
        /**
         * SearchResponse
         */

        //tag::price-histo-response
//        List<HistogramBucket> buckets = response.aggregations()
//                .get("real_name_group")

//                .get("price-histogram") // <1>
//                .histogram() // <2>
//                .buckets().array(); // <3>

//        for (HistogramBucket bucket: buckets) {
//            log.info("There are " + bucket.docCount() +
//                    " bikes under " + bucket.key());
//        }

        //end::price-histo-response
    }

    @Test
    public void aggregationBuildTest() {
//        Function<TermsAggregation.Builder, ObjectBuilder<TermsAggregation>> fn =
        /**
         * Function<TermsAggregation.Builder, ObjectBuilder<TermsAggregation>> fn
         */
//        TermsAggregation.Builder
//        Map<String, Aggregation> realnameMap = AggregationBuilders.terms(t -> t.field("realname")).aggregations();
//
//        log.info("realnameMap: {}", new Gson().toJson(realnameMap));
//                .field("")
//                .order()
//                .size(10);
    }
}
