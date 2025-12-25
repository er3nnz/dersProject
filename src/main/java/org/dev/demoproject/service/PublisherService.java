package org.dev.demoproject.service;

import org.dev.demoproject.entity.Publisher;
import org.dev.demoproject.repository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    // CREATE
    public Publisher create(Publisher publisher) {
        if (publisherRepository.existsByName(publisher.getName())) {
            throw new RuntimeException("Publisher already exists: " + publisher.getName());
        }
        return publisherRepository.save(publisher);
    }

    // READ BY ID
    public Publisher getById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));
    }

    // READ ALL
    public List<Publisher> getAll() {
        return publisherRepository.findAll();
    }

    // UPDATE
    public Publisher update(Long id, Publisher publisher) {
        Publisher existing = getById(id);

        existing.setName(publisher.getName());
        existing.setAddress(publisher.getAddress());
        existing.setPhone(publisher.getPhone());
        existing.setWebsite(publisher.getWebsite());

        return publisherRepository.save(existing);
    }

    // DELETE
    public void delete(Long id) {
        Publisher publisher = getById(id);
        publisherRepository.delete(publisher);
    }
}
