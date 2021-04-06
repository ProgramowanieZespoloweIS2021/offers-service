package com.pz.offersservice.tags.controller;

import com.pz.offersservice.tags.model.Tag;
import com.pz.offersservice.tags.service.TagsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/tags")
public class TagsController {

    private final TagsService tagsService;

    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping
    public Collection<Tag> getAllTags() {
        return tagsService.getAllTags();
    }

}
