package com.excellentbook.excellentbook.controller;

import com.excellentbook.excellentbook.dto.tag.TagDto;
import com.excellentbook.excellentbook.service.TagService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDto> getTags(){
       return tagService.getAllTags();
    }
}
