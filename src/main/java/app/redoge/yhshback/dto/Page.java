package app.redoge.yhshback.dto;

import java.util.List;

public record Page<T>(List<T> content, boolean first, boolean last, int size, int number, int numberOfElements,
                      int totalElements, int totalPages, boolean empty) {

    public Page(org.springframework.data.domain.Page<T> page) {
        this(page.getContent(), page.isFirst(), page.isLast(), page.getSize(), page.getNumber(),
                page.getNumberOfElements(), (int) page.getTotalElements(), page.getTotalPages(), page.isEmpty());
    }
}
