package org.dev.demoproject.service;

import org.dev.demoproject.entity.Publisher;
import org.dev.demoproject.repository.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    @Test
    void shouldCreatePublisher() {
        Publisher publisher = new Publisher("O'Reilly");

        when(publisherRepository.existsByName("O'Reilly")).thenReturn(false);
        when(publisherRepository.save(publisher)).thenReturn(publisher);

        Publisher result = publisherService.create(publisher);

        assertNotNull(result);
        assertEquals("O'Reilly", result.getName());
        verify(publisherRepository).save(publisher);
    }

    @Test
    void shouldThrowExceptionWhenPublisherAlreadyExists() {
        Publisher publisher = new Publisher("O'Reilly");

        when(publisherRepository.existsByName("O'Reilly")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> publisherService.create(publisher));

        assertTrue(exception.getMessage().contains("already exists"));
        verify(publisherRepository, never()).save(any());
    }

    @Test
    void shouldGetPublisherById() {
        Publisher publisher = new Publisher("Penguin");
        publisher.setId(1L);

        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

        Publisher result = publisherService.getById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Penguin", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenPublisherNotFound() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> publisherService.getById(1L));

        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void shouldGetAllPublishers() {
        List<Publisher> publishers = List.of(
                new Publisher("A"),
                new Publisher("B")
        );

        when(publisherRepository.findAll()).thenReturn(publishers);

        List<Publisher> result = publisherService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void shouldUpdatePublisher() {
        Publisher existing = new Publisher("Old Name");
        existing.setId(1L);

        Publisher updated = new Publisher("New Name");
        updated.setAddress("New Address");

        when(publisherRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(publisherRepository.save(existing)).thenReturn(existing);

        Publisher result = publisherService.update(1L, updated);

        assertEquals("New Name", result.getName());
        assertEquals("New Address", result.getAddress());
    }

    @Test
    void shouldDeletePublisher() {
        Publisher publisher = new Publisher("Delete Me");
        publisher.setId(1L);

        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));
        doNothing().when(publisherRepository).delete(publisher);

        publisherService.delete(1L);

        verify(publisherRepository).delete(publisher);
    }
}
