package arkt.dev.encurtadorurl.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "urls")
public class UrlEntity {

    @Id
    private String id;

    private String url;

    @Indexed(expireAfter = "40s" )
    private LocalDateTime expires;

    public UrlEntity() {

    }

    public UrlEntity(String id, String url, LocalDateTime expires) {
        this.id = id;
        this.url = url;
        this.expires = expires;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setExpires(LocalDateTime expires) {
        this.expires = expires;
    }
}
