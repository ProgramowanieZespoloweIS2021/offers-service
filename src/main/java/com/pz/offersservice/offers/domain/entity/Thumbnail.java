package com.pz.offersservice.offers.domain.entity;

public class Thumbnail {

    private final Long id;
    private final String url;

    public Thumbnail(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public static ThumbnailBuilder builder() {
        return new ThumbnailBuilder();
    }

    public static class ThumbnailBuilder {

        private Long id;
        private String url;

        public ThumbnailBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ThumbnailBuilder url(String url) {
            this.url = url;
            return this;
        }

        public Thumbnail build() {
            return new Thumbnail(id, url);
        }
    }
}
