package com.globaroman.bookshopproject;

import com.globaroman.bookshopproject.model.Book;
import com.globaroman.bookshopproject.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookshopProjectApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookshopProjectApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book1984 = Book.builder()
                        .title("1984")
                        .author("George Orwell")
                        .isbn("978-966-2355-57-4")
                        .price(BigDecimal.valueOf(140))
                        .description("The world where Big Brother controls its citizens like "
                                + "a boot stamping on a human face' has become "
                                + "a touchstone for human freedom, and one of "
                                + "the most widely-read books in the world.")
                        .coverImage("coverImage_1984")
                                .build();

                bookService.save(book1984);
                bookService.findAll().forEach(System.out::println);
            }
        };
    }
}
