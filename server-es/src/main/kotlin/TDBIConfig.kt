import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Mapping

@Document(indexName = "tdbi_config")
@Mapping(mappingPath = "/tdbi-config-mapping.json")
data class TDBIConfig(
        @Id val id: String,
        val quotaZone: String,
        val label: String,
        @Transient var labelHL: String?
)
