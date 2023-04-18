package com.excellentbook.excellentbook.service;

import com.excellentbook.excellentbook.ModelUtils;
import com.excellentbook.excellentbook.dto.tag.TagDto;
import com.excellentbook.excellentbook.entity.Tag;
import com.excellentbook.excellentbook.repository.TagRepository;
import com.excellentbook.excellentbook.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ModelMapper mapper;

    @Test
    void getAllTagsTest() {

        List<Tag> tags = ModelUtils.getTags();
        when(tagRepository.findAll()).thenReturn(tags);

        TagDto tagDto1 = new TagDto();
        tagDto1.setName("tag1");
        TagDto tagDto2 = new TagDto();
        tagDto2.setName("tag2");

        when(mapper.map(tags.get(0), TagDto.class)).thenReturn(tagDto1);
        when(mapper.map(tags.get(1), TagDto.class)).thenReturn(tagDto2);

        List<TagDto> actualTags = tagService.getAllTags();

        assertEquals(2, actualTags.size());
        assertEquals(tagDto1.getName(), actualTags.get(0).getName());
        assertEquals(tagDto2.getName(), actualTags.get(1).getName());
    }

}
