package com.excellentbook.excellentbook.dto.book;

import lombok.Data;

import java.util.List;

@Data
public class BookPageableDto {
    private List<BookDtoResponse> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private long totalPages;
    private boolean last;
}
