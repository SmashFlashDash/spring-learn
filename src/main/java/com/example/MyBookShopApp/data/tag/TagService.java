package com.example.MyBookShopApp.data.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;
    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    public List<TagEntity> getTagsSortedByBooksCount(){
        return tagRepository.findAllTagsSortedByBookCount();
    }
}
