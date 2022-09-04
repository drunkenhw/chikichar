package com.chikichar.chikichar.dto.page;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class CustomPageResponse<T> {
    private List<T> dtoList;
    private int totalPage;
    private int page;
    private int size;
    private int start, end;
    private boolean prev, next;
    private List<Integer> pageList;

    public CustomPageResponse(Page<T> result){
        dtoList = result.getContent();
        totalPage = result.getTotalPages();
        getPageList(result.getPageable());
    }

    private void getPageList(Pageable pageable) {
        page = pageable.getPageNumber() + 1;
        size = pageable.getPageSize();
        int tempEnd = (int)(Math.ceil(page/10.0))*10;
        start = tempEnd -9;
        end = Math.min(totalPage, tempEnd);
        prev = start > 1;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }
}
