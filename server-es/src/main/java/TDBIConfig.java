import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

import lombok.Data;

/**
 * TDBIConfig.
 *
 * @author Joker.Cheng.
 * @version 0.0.1.
 * @serial 2019-03-25 : base version.
 */
@Document(indexName = "tdbi_config")
@Mapping(mappingPath = "/tdbi-config-mapping.json")
@Data
public class TDBIConfig {

  /*ES ID*/
  @Id private String id;

  private String quotaZone;

  private String mapper;

  private String parent;

  private String code;

  private Integer level;

  private Boolean leaf;

  private Integer display;

  private Integer mapperMulti;

  private String dataType;

  private String unitLabel;

  private Boolean expand;

  private Boolean disabled;

  private Boolean hidden;

  private String path;

  @Transient private String description;

  private String label;

  private String labelPath;

  @Transient private String labelHL;

  @Transient private String labelPathHL;
}
