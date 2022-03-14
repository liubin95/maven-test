import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.Document
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.aggregation.*
import org.springframework.data.mongodb.core.query.Collation
import org.springframework.data.mongodb.core.query.Criteria
import kotlin.system.exitProcess

fun main() {
    val template = MongoConfig.getTemplate()
    // 匹配 - 问题分类、成分、指标筛选
    val page = 1
    val size = 50

    // 条件 - 问题分类、成分、指标筛选
    val criteria = Criteria()
    criteria.andOperator(
        Criteria("company.ipo_plate_label").`is`("深交所创业板"),
    )

    val matchOperation: MatchOperation = Aggregation.match(criteria)
    // 字段
    val quotasStr = """
        [{"label":"案例ID","mapper":"mapper.iid","mapper_fields":[],"date_type":"STRING"},{"label":"IPO序号","mapper":"ingredient.ipo_serial_no","mapper_fields":[],"date_type":"INTEGER"},{"label":"公司简称","mapper":"company.company_short_name","mapper_fields":[],"date_type":"STRING"},{"label":"证券代码","mapper":"company.securities_code","mapper_fields":[],"date_type":"STRING"},{"label":"IPO申报次数","mapper":"ipo.ipo_times","mapper_fields":[],"date_type":"INTEGER"},{"label":"最新招股书报告期","mapper":"fin.newest_period_label","mapper_fields":[],"date_type":"DATE"},{"label":"总资产周转率","mapper":"fin_data.00026.1_1","mapper_fields":[{"label":"最新招股书报告期","value":1,"title":"报告期"},{"label":"第1年年报","value":1,"title":"报告期"}],"date_type":"NUMBER"},{"label":"总资产收益率","mapper":"fin_data.00002.1_1","mapper_fields":[{"label":"最新招股书报告期","value":1,"title":"报告期"},{"label":"第1年年报","value":1,"title":"报告期"}],"date_type":"NUMBER","unit_label":"%"},{"label":"营业收入","mapper":"fin_data.02016.1_1_0","mapper_fields":[{"label":"最新招股书报告期","value":1,"title":"报告期"},{"label":"第1年年报","value":1,"title":"报告期"},{"label":"万元","value":0,"title":"单位","type":"currency"}],"date_type":"NUMBER"},{"label":"营业收入","mapper":"fin_data.02016.1_2_0","mapper_fields":[{"label":"最新招股书报告期","value":1,"title":"报告期"},{"label":"第2年年报","value":2,"title":"报告期"},{"label":"万元","value":0,"title":"单位","type":"currency"}],"date_type":"NUMBER"},{"label":"营业收入","mapper":"fin_data.02016.1_3_0","mapper_fields":[{"label":"最新招股书报告期","value":1,"title":"报告期"},{"label":"第3年年报","value":3,"title":"报告期"},{"label":"万元","value":0,"title":"单位","type":"currency"}],"date_type":"NUMBER"},{"label":"净利润","mapper":"fin_data.02056.1_1_0","mapper_fields":[{"label":"最新招股书报告期","value":1,"title":"报告期"},{"label":"第1年年报","value":1,"title":"报告期"},{"label":"万元","value":0,"title":"单位","type":"currency"}],"date_type":"NUMBER"},{"label":"净利润","mapper":"fin_data.02056.1_2_0","mapper_fields":[{"label":"最新招股书报告期","value":1,"title":"报告期"},{"label":"第2年年报","value":2,"title":"报告期"},{"label":"万元","value":0,"title":"单位","type":"currency"}],"date_type":"NUMBER"},{"label":"净利润","mapper":"fin_data.02056.1_3_0","mapper_fields":[{"label":"最新招股书报告期","value":1,"title":"报告期"},{"label":"第3年年报","value":3,"title":"报告期"},{"label":"万元","value":0,"title":"单位","type":"currency"}],"date_type":"NUMBER"},{"label":"营业利润","mapper":"fin_data.02045.1_1_0","mapper_fields":[{"label":"最新招股书报告期","value":1,"title":"报告期"},{"label":"第1年年报","value":1,"title":"报告期"},{"label":"万元","value":0,"title":"单位","type":"currency"}],"date_type":"NUMBER"},{"label":"营业利润","mapper":"fin_data.02045.1_2_0","mapper_fields":[{"label":"最新招股书报告期","value":1,"title":"报告期"},{"label":"第2年年报","value":2,"title":"报告期"},{"label":"万元","value":0,"title":"单位","type":"currency"}],"date_type":"NUMBER"},{"label":"营业利润","mapper":"fin_data.02045.1_3_0","mapper_fields":[{"label":"最新招股书报告期","value":1,"title":"报告期"},{"label":"第3年年报","value":3,"title":"报告期"},{"label":"万元","value":0,"title":"单位","type":"currency"}],"date_type":"NUMBER"},{"label":"总资产增长率","mapper":"fin_data.00016.1_1","mapper_fields":[{"label":"最新招股书报告期","value":1,"title":"报告期"},{"label":"第1年年报","value":1,"title":"报告期"}],"date_type":"NUMBER","unit_label":"%"},{"label":"经营活动现金流入小计","mapper":"fin_data.03031.1_1_0","mapper_fields":[{"label":"最新招股书报告期","value":1,"title":"报告期"},{"label":"第1年年报","value":1,"title":"报告期"},{"label":"万元","value":0,"title":"单位","type":"currency"}],"date_type":"NUMBER"}]
    """.trimIndent()
    val objectMapper = ObjectMapper()
    val jsonNode: JsonNode = objectMapper.readTree(quotasStr)
    val mappers: List<String> = jsonNode.map { it.get("mapper").textValue() }
    val projectionOperation: ProjectionOperation = Aggregation.project(Fields.fields(*mappers.toTypedArray()))

    // 排序
    val sortOperation: SortOperation = Aggregation.sort(Sort.Direction.DESC, "ipo_serial_no")
    // 分页
    val skip: Long = page.toLong() * size.toLong()
    val skipOperation = Aggregation.skip(skip)
    val limitOperation = Aggregation.limit(size.toLong())
    // 聚合查询
    val aggregationOptions = AggregationOptions.builder()
        .allowDiskUse(true)
        .collation(
            Collation.of(Collation.CollationLocale.of("zh")).numericOrderingEnabled()
        )
        .build()
    val aggregation = Aggregation.newAggregation(
        matchOperation, projectionOperation, skipOperation, limitOperation, sortOperation
    )
        .withOptions(aggregationOptions) // 启用allowDiskUse启用汉字排序规则
    val totalAgg = Aggregation.newAggregation(
        matchOperation,
        Aggregation.count().`as`("count"),
        Aggregation.project("count")
    ).withOptions(aggregationOptions)
    val totalRes = template.aggregate(totalAgg, "tdbp_quota", Document::class.java)
    println("totalRes ${totalRes.mappedResults[0]}")
    val aggregate: AggregationResults<Document> = template.aggregate(aggregation, "tdbp_quota", Document::class.java)

    aggregate.mappedResults.forEach {
        println(it.toString())
    }
    exitProcess(0)

}
