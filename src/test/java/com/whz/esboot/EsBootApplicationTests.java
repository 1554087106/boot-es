package com.whz.esboot;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.HistogramBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.util.ObjectBuilder;
import com.google.gson.*;
import com.whz.esboot.es.pojo.Stu;
import com.whz.esboot.es.resposity.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.AggregationsContainer;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightFieldParameters;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * SearchHits<T> 搜索结果
 * SearchPage<T> 搜索页面
 * SearchScrollHits<T> 搜索卷轴
 * SearchHislterator<T> 搜索器
 *SearchOperations和ReactiveSearchOperations接口需要Query参数
 * Query接口的三种实现:
 *      CriteriaQuery
 *      StringQuery
 *      NativeQuery
 *
 */
@Slf4j
@SpringBootTest(classes = EsBootApplication.class)
class EsBootApplicationTests {
    @Autowired
    private StudentRepository studentRepository;
    /**
     * 用于文档操作和检索 extends DocumentOperations, SearchOperations
     * DocumentOperations 检索一个文件时，只返回找到的实体。SearchOperations 接口方法搜索时，每个实体都有额外的信息，例如，找到的实体的分数或排序值
     *
     */
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    /**
     * 基于CriteriaQuery的查询允许创建查询来搜索数据
     * 通过连锁和组合Criteria对象来创建查询
     */
    @Test
    public void criteriaQueryTest() {
        /**
         * 年龄大于20小于30
         */
        Criteria criteria = new Criteria("age").greaterThan(20).lessThan(30);
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        /**
         * 嵌套子查询
         */
        Criteria subCriteria = new Criteria("name").matches("蔡鸡")
                .subCriteria(new Criteria().and("age").greaterThan(20));
        CriteriaQuery subCriteriaQuery = new CriteriaQuery(subCriteria);

        SearchHits<Stu> result = elasticsearchOperations.search(subCriteriaQuery, Stu.class);
        log.info("result: {}", new Gson().toJson(result));

    }

    /**
     * 将Elasticsearch查询作为JSON字符串
     */
    @Test
    public void stringQueryTest() {
        StringBuilder queryStr = new StringBuilder();
        queryStr.append("{ \"match\": { \"realname\": { \"query\": \"澹台烬\" } } } ");
        StringQuery query = new StringQuery(queryStr.toString());
        SearchHits<Stu> searchHits = elasticsearchOperations.search(query, Stu.class);

        Iterator<SearchHit<Stu>> iterator = searchHits.stream().iterator();
        iterator.forEachRemaining(stu -> {
            log.info("stu: {}", new Gson().toJson(stu.getContent()));

        });
    }

    /**
     * 当有一个复杂的查询
     * 或者不能用Criteria的API来表示时使用NativeQuery
     * 在建立查询和使用聚合时
     */
    @Test
    public void nativeQueryTest() {
//        Query query = NativeQuery.builder()
//                .withAggregation("lastNames", Aggregation.of(a -> a
//                        .terms(ta -> ta.field("last-name").size(10))))
//                .withQuery(q -> q
//                        .match(m -> m
//                                .field("firstName")
//                                .query(firstName)
//                        )
//                )
//                .withPageable(pageable)
//                .build();

//        SearchHits<Person> searchHits = operations.search(query, Person.class);

        // 根据realname进行分组
        NativeQuery nativeQuery = NativeQuery.builder()
                .withAggregation("real_name_group",
                        Aggregation.of(f -> f.terms(t -> t.field("realname").size(5))))
                .build();
        SearchHits<Stu> result = elasticsearchOperations.search(nativeQuery, Stu.class);
        log.info("result: {}", new Gson().toJson(result));
        ArrayList aggResult = (ArrayList) result.getAggregations().aggregations();
        log.info("aggResult: {}", new Gson().toJson(aggResult));
        // 创建Gson对象
//        JsonArray jsonArray = new JsonArray();
        Gson gson = new Gson();
//        JsonElement jsonTree = gson.toJsonTree(aggResult);
//        boolean isJsonArray = jsonTree.isJsonArray();
//        log.info("可以被解析为JSON数组吗: {}", isJsonArray);

        // 解析SearchHits<Stu> result
        String resultStr = gson.toJson(result);
        //  SearchHits<Stu> result -> JsonObject resultJson
        JsonObject resultJson = JsonParser.parseString(resultStr).getAsJsonObject();
        // 对JsonObject进行解析


    }

    /**
     * 没有实现自动注入
     */
//    @Autowired
//    private ReactiveElasticsearchOperations reactiveElasticsearchOperations;
    /**
     * 没有实现自动注入
     */
//    @Autowired
//    private ReactiveElasticsearchClient reactiveElasticsearchClient;
    @Test
    public void setUp() {
        log.info("elasticsearchOperations: {}\r\nelasticsearchClient: {}\r\nreactiveElasticsearchOperations: {}\r\nreactiveElasticsearchClient: {}",
                elasticsearchOperations,
                elasticsearchClient);
    }
//    private final DocTestsTransport transport = new DocTestsTransport();
//    private final ElasticsearchClient esClient = new ElasticsearchClient(transport);
//    private Aggregation aggregation = ;


    private static final SearchResponse<JsonData> searchResponse = SearchResponse.of(b -> b
            .aggregations(new HashMap<>())
            .took(0)
            .timedOut(false)
            .hits(h -> h
                    .total(t -> t.value(0).relation(TotalHitsRelation.Eq))
                    .hits(new ArrayList<>())
            )
            .shards(s -> s
                    .total(1)
                    .failed(0)
                    .successful(1)
            )
            .aggregations("price-histogram", a -> a.histogram(h -> h
                    .buckets(bu -> bu.array(Collections.singletonList(HistogramBucket.of(hb -> hb
                            .key(50).docCount(1)
                    ))))
            ))
    );
    public void init() {
        log.info("studentRepository: {}", studentRepository);
    }

//    @Test
//    void saveTest() {
//        ArrayList<Stu> stuList = new ArrayList<Stu>();
//        // 添加Stu文档
//        Stu stu1 = Stu.builder().id(1000L).name("蔡鸡001").realname("叶冰裳").age(18).build();
//        Stu stu2 = Stu.builder().id(1001L).name("蔡鸡002").realname("叶振宇").age(23).build();
//        Stu stu3 = Stu.builder().id(1002L).name("蔡鸡003").realname("冥夜").age(32).build();
//        Stu stu4 = Stu.builder().id(1003L).name("蔡鸡004").realname("澹台烬").age(41).build();
//        Stu stu5 = Stu.builder().id(1004L).name("蔡鸡005").realname("叶夕雾").age(50).build();
//        Stu stu6 = Stu.builder().id(1005L).name("蔡鸡006").realname("青鸾").age(50).build();
//        Stu stu7 = Stu.builder().id(1006L).name("蔡鸡007").realname("叶夕雾").age(50).build();
//        Stu stu8 = Stu.builder().id(1008L).name("蔡鸡008").realname("叶夕雾").age(50).build();
//        Stu stu9 = Stu.builder().id(1009L).name("蔡鸡009").realname("澹台烬").age(50).build();
//        Stu stu10 = Stu.builder().id(1010L).name("蔡鸡010").realname("澹台烬").age(50).build();
//
//        stuList.add(stu1);
//        stuList.add(stu2);
//        stuList.add(stu3);
//        stuList.add(stu4);
//        stuList.add(stu5);
//        stuList.add(stu6);
//        stuList.add(stu7);
//        stuList.add(stu8);
//        stuList.add(stu9);
//        stuList.add(stu10);
//        Iterable<Stu> result = studentRepository.saveAll(stuList);
//        Spliterator<Stu> spliterator = result.spliterator();
//        spliterator.forEachRemaining(stu -> {
//            log.info("插入的Stu数据: {}", new Gson().toJson(stu));
//        });
//    }

    @Test
    public void delTest() {
//        List<Long> idList = Lists.newArrayList(1003L, 1004L, 1005L, 1010L);
//        studentRepository.deleteAllById(idList);
        studentRepository.deleteAll();
    }

    @Test
    public void ageBetweenTest() {
        Long count = studentRepository.countStuByAgeBetween(20, 30);
        log.info("年龄在20到30之间的学生数量: {}", count);

    }

    @Test
    public void ageMoreTest() {
        List<Stu> stuList = studentRepository.findByAgeGreaterThan(20);
        log.info("年龄大于20的学生数量: {}", stuList.size());
        Gson gson = new Gson();
        log.info("年龄大于20的学生列表: {}", gson.toJson(stuList));
    }

    /**
     * 实现分页查询
     */
    @Test
    public void groupByRealname() throws IOException {
//        Object stuList = studentRepository.groupByRealname();
//        Gson gson = new Gson();
//        log.info("按照realname进行分组的结果: {}", gson.toJson(stuList));

//        transport.setResult(searchResponse);

        //tag::price-histo-request
        String searchText = "bike";

        Query query = MatchQuery.of(m -> m
                .field("name")
                .query(searchText)
        )._toQuery();

        SearchResponse<Void> response = null;
//                esClient.search(b -> b
//                        .index("products")
//                        .size(0) // <1>
//                        .query(query) // <2>
//                        .aggregations("price-histogram", a -> a // <3>
//                                .histogram(h -> h // <4>
//                                        .field("price")
//                                        .interval(50.0)
//                                )
//                        ),
//                Void.class // <5>
//        );
        //end::price-histo-request

        //tag::price-histo-response
        List<HistogramBucket> buckets = response.aggregations()
                .get("price-histogram") // <1>
                .histogram() // <2>
                .buckets().array(); // <3>

        for (HistogramBucket bucket: buckets) {
            log.info("There are " + bucket.docCount() +
                    " bikes under " + bucket.key());
        }

        //end::price-histo-response
    }
    // 测试分页查询
    @Test
    public void pageableTest() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "age"));
        Page<Stu> pageInfo = studentRepository.findAll(pageable);
        // 总分页数
        int totalPages = pageInfo.getTotalPages();
        // 总文档数量
        long totalElements = pageInfo.getTotalElements();
        // 当前页面中展示的内容
        List<Stu> content = pageInfo.getContent();
        log.info("totalPages: {}\r\n totalElements: {}", totalPages, totalElements);

        log.info("当前页面展示的内容: {}", new Gson().toJson(content));

    }

    // 测试高亮展示
    @Test
    public void highLineTest() {


//        HighlightFieldParameters highlightFieldParameters =
        // 高亮展示设置
        HighlightFieldParameters highlightFieldParameters = new HighlightFieldParameters.HighlightFieldParametersBuilder()
                .withMatchedFields("name").withFragmentSize(20).withPreTags("").withPostTags("").build();
//        highlightFieldParameters.s
//        HighlightFieldParameters.HighlightFieldParametersBuilder
        Function<SearchRequest.Builder, ObjectBuilder<SearchRequest>> fn =
                f -> f.aggregations("real_name_group",
                a -> a.terms(t -> t.field("realname")));
//        new SearchRequest(fn);
        SearchRequest searchRequest = SearchRequest.of(fn);
    }



}
