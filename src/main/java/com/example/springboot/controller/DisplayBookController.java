package com.example.springboot.controller;

import com.example.springboot.entity.Book;
import com.example.springboot.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;



@Controller
@RequestMapping("/show")
public class DisplayBookController {
    RestTemplate restTemplate= new RestTemplate();
    @GetMapping("/books")
    public String findAll(Model model) {


        ResponseEntity<Book[]> response =
                restTemplate.getForEntity(
                        "http://localhost:8081/springbootapp/api/books",
                        Book[].class);
        Book[] books = response.getBody();
        model.addAttribute("books", books);
        return "displayBook";
    }
    @GetMapping("/add")
    public String addBookG(){
        return "add";
    }
    @PostMapping("/add")
    public String addBookP(Book book){
        HttpEntity<Book> request = new HttpEntity<>(book);
        restTemplate.postForObject("http://localhost:8081/springbootapp/api/books", request, Book.class);
        return "home";
    }
    @GetMapping("/update/{id}")
    public String updateBookG(@PathVariable Long id,Model model){
        ResponseEntity<Book> response =
                restTemplate.getForEntity(
                        "http://localhost:8081/springbootapp/api/books/"+ String.valueOf(id),
                        Book.class);
        Book book = response.getBody();
        model.addAttribute("book", book);
        return "update";
    }
    @PostMapping("/update/{id}")
    public String updateBookP(@PathVariable Long id,Book book){


        book.setId(id);
        String resourceUrl =
                "http://localhost:8081/springbootapp/api/books" + '/' + String.valueOf(book.getId());
        HttpEntity<Book> requestUpdate = new HttpEntity<>(book, new HttpHeaders());
        restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Void.class);
        return "redirect:/show/books";
    }
    @GetMapping("/delete/{id}")
    public String deleteBookG(@PathVariable Long id){


        String entityUrl = "http://localhost:8081/springbootapp/api/books" + "/" + String.valueOf(id);
        restTemplate.delete(entityUrl);
        return "redirect:/show/books";
    }
}
