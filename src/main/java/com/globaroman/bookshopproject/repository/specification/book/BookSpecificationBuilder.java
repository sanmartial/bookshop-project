package com.globaroman.bookshopproject.repository.specification.book;

import com.globaroman.bookshopproject.dto.book.BookSearchParameters;
import com.globaroman.bookshopproject.model.Book;
import com.globaroman.bookshopproject.repository.specification.SpecificationBuilder;
import com.globaroman.bookshopproject.repository.specification.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {

    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    public Specification<Book> build(BookSearchParameters searchParameter) {
        Specification<Book> spec = Specification.where(null);

        if (searchParameter.author() != null && searchParameter.author().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("author")
                    .getSpecification(searchParameter.author()));

        }
        if (searchParameter.title() != null && searchParameter.title().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("title")
                    .getSpecification(searchParameter.title()));

        }
        if (searchParameter.isbn() != null && searchParameter.isbn().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("isbn")
                    .getSpecification(searchParameter.isbn()));

        }
        return spec;
    }
}
