package arkt.dev.encurtadorurl.repository;

import arkt.dev.encurtadorurl.entities.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<UrlEntity, String> {
}
