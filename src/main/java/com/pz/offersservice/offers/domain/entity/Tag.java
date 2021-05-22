package com.pz.offersservice.offers.domain.entity;

public class Tag {

    private final String name;

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TagBuilder builder() {
        return new TagBuilder();
    }

    public static class TagBuilder { // TODO: is needed?

        private String name;

        public TagBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Tag build() {
            return new Tag(name);
        }
    }
}
