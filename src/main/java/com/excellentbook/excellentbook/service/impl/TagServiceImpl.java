package com.excellentbook.excellentbook.service.impl;

import com.excellentbook.excellentbook.dto.tag.TagDto;
import com.excellentbook.excellentbook.entity.Tag;
import com.excellentbook.excellentbook.repository.TagRepository;
import com.excellentbook.excellentbook.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper mapper;

    public TagServiceImpl(TagRepository tagRepository, ModelMapper mapper) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

    @Override
    public List<TagDto> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(tag -> mapper.map(tag, TagDto.class))
                .toList();
    }
}
