package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.tag.TagDto;
import com.excellentbook.excellentbook.repository.TagRepository;
import com.excellentbook.excellentbook.service.TagService;
import org.modelmapper.ModelMapper;

public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper mapper;

    public TagServiceImpl(TagRepository tagRepository, ModelMapper mapper) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

    @Override
    public TagDto getAllTags() {
        return null;
    }
}
