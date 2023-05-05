package com.whz.esboot.es.resposity;

import com.whz.esboot.es.pojo.Stu;
import com.whz.esboot.es.resposity.customer.StuCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author: hong-zhi
 * @date: 2023/4/24 14:34
 * @Description 使用StudentRepository对ES中对Stu索引进行CRUD操作
 * springboot集成elasticsearch7.8 https://blog.csdn.net/qq_41893274/article/details/107039622
 * 继承ElasticsearchRepository和StuCustomRepository自定义接口操作
 *
 */
public interface StudentRepository extends ElasticsearchRepository<Stu, Long>, StuCustomRepository {
    /**
     * 通过文档ID列表查询文档
     * @param longs
     * @return
     */
    @Override
    Iterable<Stu> findAllById(Iterable<Long> longs);

    /**
     * 查询指定年龄访问内的学生数量
     * @param minAge 最小年龄
     * @param maxAge 最大年龄
     * @return
     */
    Long countStuByAgeBetween(int minAge, int maxAge);

    /**
     * 查询大于指定年龄的Stu文档
     * @param age
     * @return
     */
    List<Stu> findByAgeGreaterThan(int age);

    /**
     * 按照real-name进行分组
     * @return
     */
    // elasticSearch 7.8.0 以上版本不支持自定义查询语句，需要使用注解@Query
    //  @Query 不支持aggs

    //  @Query("{\"aggs\":{\"real_name_group\":{\"terms\":{\"field\":\"realname\"}}},\"size\":0}")
//    @Query("{\"aggs\":{\"real_name_group\":{\"terms\":{\"field\":\"realname\"}}},\"size\":0}")
//    Object groupByRealname();


    @Override
    Page<Stu> findAll(Pageable pageable);
}
