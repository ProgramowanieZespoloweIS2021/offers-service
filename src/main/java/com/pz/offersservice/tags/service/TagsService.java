package com.pz.offersservice.tags.service;

import com.pz.offersservice.tags.model.Tag;
import com.pz.offersservice.tags.repository.TagsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {

    private final TagsRepository tagsRepository;

    public TagsService(TagsRepository tagsRepository) {
        this.tagsRepository = tagsRepository;
    }

    public List<Tag> getAllTags() {
        return tagsRepository.findAll();
    }

    public List<Tag> getTagsByIds(List<Long> tagIds) {
        return tagsRepository.findAllById(tagIds);
    }

}
