package org.dev.demoproject.controller;

import org.dev.demoproject.entity.Publisher;
import org.dev.demoproject.service.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Publisher create(@RequestBody Publisher publisher) {
        return publisherService.create(publisher);
    }

    // READ ALL
    @GetMapping
    public List<Publisher> getAll() {
        return publisherService.getAll();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Publisher getById(@PathVariable Long id) {
        return publisherService.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Publisher update(@PathVariable Long id,
                            @RequestBody Publisher publisher) {
        return publisherService.update(id, publisher);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        publisherService.delete(id);
    }
}
