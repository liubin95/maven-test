import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * RestTemplateTest.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-02-23 : base version.
 */
@Slf4j
public class RestTemplateTest {

  public static void main(String[] args) {
    val template = ElasticSearchConfig.getTemplate();
    NativeSearchQuery nativeSearchQuery =
        new NativeSearchQueryBuilder()
            .withQuery(
                QueryBuilders.boolQuery()
                    .must(QueryBuilders.termQuery("quotaZone", "TDBP"))
                    .must(QueryBuilders.termQuery("hidden", false))
                    .must(QueryBuilders.termQuery("leaf", true))
                    .must(QueryBuilders.matchPhraseQuery("label", "公司")))
            // 高亮查询 选项
            .withHighlightFields(
                new HighlightBuilder.Field("label").preTags("<em>").postTags("</em>"))
            .withPageable(PageRequest.of(1, 10))
            .build();
    final SearchHits<TDBIConfig> searchRes = template.search(nativeSearchQuery, TDBIConfig.class);
    // 处理高亮
    searchRes.stream()
        .map(
            hit -> {
              final List<String> label = hit.getHighlightField("label");
              final TDBIConfig content = hit.getContent();
              content.setLabelHL(label.get(0));
              return content;
            })
        .forEach(it -> log.info(it.toString()));
    System.exit(0);
  }
}
