package arkt.dev.encurtadorurl.controller;

import arkt.dev.encurtadorurl.controller.dto.UrlEncurtadaRequest;
import arkt.dev.encurtadorurl.controller.dto.UrlEncurtadaResponse;
import arkt.dev.encurtadorurl.entities.UrlEntity;
import arkt.dev.encurtadorurl.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
public class UrlController {

    private final UrlRepository urlRepository;

    public UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @PostMapping(value = "/encurtador")
    public ResponseEntity<UrlEncurtadaResponse> encurtador(@RequestBody UrlEncurtadaRequest request,
                                           HttpServletRequest httpServletRequest) {

        String id;
        do {
            id = RandomStringUtils.randomAlphanumeric(5, 10);

        } while (urlRepository.existsById(id));

        urlRepository.save(new UrlEntity(id, request.url(), LocalDateTime.now().plusMinutes(1)));

        var redirectUrl = httpServletRequest.getRequestURL().toString().replace("/encurtador","/" + id);

        return ResponseEntity.ok(new UrlEncurtadaResponse(redirectUrl));
    }

    @GetMapping("{id}")
    public ResponseEntity<Void> get(@PathVariable("id") String id) {

        var url = urlRepository.findById(id);
        if (url.isPresent()) {

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(url.get().getUrl()));

            return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

