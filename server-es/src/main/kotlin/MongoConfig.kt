import com.mongodb.client.MongoClients
import org.springframework.data.mongodb.core.MongoTemplate

class MongoConfig private constructor() {

    companion object MongoConfig {

        fun getTemplate(): MongoTemplate {
            val str = "mongodb://capital_tdb_dev:cde3VFR$@172.16.20.163:27017,172.16.20.176:27017,172.16.20.184:27017/capital_tdb_dev"
            val mongoClient = MongoClients.create(str)
            return MongoTemplate(mongoClient, "capital_tdb_dev")
        }
    }

}