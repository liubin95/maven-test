import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.SearchHits
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import kotlin.system.exitProcess

fun main() {
    val template = ElasticSearchConfig.getTemplate()
    val nativeSearchQuery: NativeSearchQuery = NativeSearchQueryBuilder().withQuery(
            QueryBuilders.boolQuery()
                    .must(QueryBuilders.termQuery("quotaZone", "TDBP"))
                    .must(QueryBuilders.termQuery("hidden", false))
                    .must(QueryBuilders.termQuery("leaf", true))
                    .must(QueryBuilders.matchPhraseQuery("label", "公司"))
    )
            .withHighlightFields(
                    HighlightBuilder.Field("label").preTags("<em>").postTags("</em>"))
            .withPageable(PageRequest.of(1, 10))
            .build()
    val searchRes: SearchHits<TDBIConfig> = template.search(nativeSearchQuery, TDBIConfig::class.java)
    try {
        // 处理高亮
        searchRes.stream()
                .map {
                    val label: MutableList<String> = it.getHighlightField("label")
                    val content = it.content
                    content.labelHL = label.firstOrNull() ?: content.label
                    content
                }
                .forEach {
                    println(it)
                }
    } catch (ex: Exception) {
        println(ex)
    }

    exitProcess(0)

}
