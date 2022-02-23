import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * ElasticSearchConfig.
 *
 * @author 刘斌
 * @version 0.0.1
 * @serial 2022-02-23 : base version.
 */
public class ElasticSearchConfig {

  public static ElasticsearchRestTemplate getTemplate() {
    ClientConfiguration clientConfiguration =
        ClientConfiguration.builder()
            .connectedTo("172.16.20.163:9200", "172.16.20.176:9200", "172.16.20.184:9200")
            .build();
    final RestHighLevelClient highLevelClient = RestClients.create(clientConfiguration).rest();
    return new ElasticsearchRestTemplate(highLevelClient);
  }
}
