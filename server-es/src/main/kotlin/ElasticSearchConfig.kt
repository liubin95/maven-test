import org.elasticsearch.client.RestHighLevelClient
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate

class ElasticSearchConfig private constructor() {

    companion object ElasticSearchConfig {

        fun getTemplate(): ElasticsearchRestTemplate {
            val clientConfiguration: ClientConfiguration =
                    ClientConfiguration.builder()
                            .connectedTo("172.16.20.163:9200", "172.16.20.176:9200", "172.16.20.184:9200")
                            .build()
            val highLevelClient: RestHighLevelClient = RestClients.create(clientConfiguration).rest()
            return ElasticsearchRestTemplate(highLevelClient)
        }
    }

}