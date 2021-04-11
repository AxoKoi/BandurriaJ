package com.axokoi.bandurriaj.services.tagging.musicbrainz.imageQuery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
public class ImageQueryImpl implements ReleaseImageQuery {

   private final String url;
   private final String imagesLocalPath;

   public ImageQueryImpl(@Value("${musicbrainz.image.url}") String url, @Value("${images.local.path}") String  imagesLocalPath) {
      this.url = url;
      this.imagesLocalPath = imagesLocalPath;
   }

   @Override
   public Optional<String> downloadFrontImage(String mbIdentifier) {
      RestTemplateBuilder builder = new RestTemplateBuilder();
      org.springframework.web.client.RestTemplate restTemplate = builder.build();

      RequestCallback requestCallback = request -> request
              .getHeaders()
              .setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
      Path path = Paths.get(imagesLocalPath + "/front_" + mbIdentifier + ".jpg");

      ResponseExtractor<Void> responseExtractor = response -> {
         if (response.getStatusCode().is2xxSuccessful()) {
            Files.copy(response.getBody(), path);
         } else {
            log.info("CD image was not found for release:" + mbIdentifier + " . Status was:" + response.getStatusCode());
         }
         return null;
      };

      try {
         restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor, mbIdentifier);
      } catch (RuntimeException e) {
         log.error("Couldn't download image for release " + mbIdentifier, e);
         return Optional.empty();
      }
      return Optional.of(path.toAbsolutePath().toString());
   }
}
