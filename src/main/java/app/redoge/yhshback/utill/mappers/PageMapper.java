package app.redoge.yhshback.utill.mappers;

import app.redoge.yhshback.dto.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.Math.min;
import static java.lang.Math.toIntExact;

@Component
public class PageMapper<T> {
    public Page<T> mapToPage(List<T> content, Pageable pageable) {
        int size = content.size();
        int start = min(toIntExact(pageable.getOffset()), size);
        int end = min((start + pageable.getPageSize()), size);
        return new Page<T>(new PageImpl<>(content.subList(start, end), pageable, size));
    }
}