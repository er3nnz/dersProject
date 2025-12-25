package org.dev.demoproject.service;

import org.dev.demoproject.entity.Author;
import org.dev.demoproject.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        author1 = new Author("John", "Doe", "Famous author");
        author1.setId(1L);

        author2 = new Author("Jane", "Smith", "Best-selling author");
        author2.setId(2L);
    }

    @Test
    void testFindAll() {
        // Given
        List<Author> authors = Arrays.asList(author1, author2);
        when(authorRepository.findAll()).thenReturn(authors);

        // When
        List<Author> result = authorService.findAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(author1, result.get(0));
        assertEquals(author2, result.get(1));
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        // Given
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));

        // When
        Optional<Author> result = authorService.findById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(author1, result.get());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        // Given
        when(authorRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<Author> result = authorService.findById(999L);

        // Then
        assertFalse(result.isPresent());
        verify(authorRepository, times(1)).findById(999L);
    }

    @Test
    void testSave() {
        // Given
        Author newAuthor = new Author("New", "Author", "Biography");
        when(authorRepository.save(any(Author.class))).thenReturn(newAuthor);

        // When
        Author result = authorService.save(newAuthor);

        // Then
        assertNotNull(result);
        assertEquals("New", result.getFirstName());
        assertEquals("Author", result.getLastName());
        verify(authorRepository, times(1)).save(newAuthor);
    }

    @Test
    void testUpdate_Success() {
        // Given
        Author updatedDetails = new Author("Updated", "Name", "Updated biography");
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        when(authorRepository.save(any(Author.class))).thenReturn(author1);

        // When
        Author result = authorService.update(1L, updatedDetails);

        // Then
        assertNotNull(result);
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).save(author1);
    }

    @Test
    void testUpdate_NotFound() {
        // Given
        Author updatedDetails = new Author("Updated", "Name", "Updated biography");
        when(authorRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authorService.update(999L, updatedDetails);
        });

        assertEquals("Author not found with id: 999", exception.getMessage());
        verify(authorRepository, times(1)).findById(999L);
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void testDeleteById_Success() {
        // Given
        when(authorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(authorRepository).deleteById(1L);

        // When
        authorService.deleteById(1L);

        // Then
        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteById_NotFound() {
        // Given
        when(authorRepository.existsById(999L)).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authorService.deleteById(999L);
        });

        assertEquals("Author not found with id: 999", exception.getMessage());
        verify(authorRepository, times(1)).existsById(999L);
        verify(authorRepository, never()).deleteById(anyLong());
    }

    @Test
    void testExistsById() {
        // Given
        when(authorRepository.existsById(1L)).thenReturn(true);

        // When
        boolean result = authorService.existsById(1L);

        // Then
        assertTrue(result);
        verify(authorRepository, times(1)).existsById(1L);
    }

    @Test
    void testFindByFirstNameAndLastName_Success() {
        // Given
        when(authorRepository.findByFirstNameAndLastName("John", "Doe"))
                .thenReturn(Optional.of(author1));

        // When
        Optional<Author> result = authorService.findByFirstNameAndLastName("John", "Doe");

        // Then
        assertTrue(result.isPresent());
        assertEquals(author1, result.get());
        verify(authorRepository, times(1)).findByFirstNameAndLastName("John", "Doe");
    }

    @Test
    void testFindByFirstNameAndLastName_NotFound() {
        // Given
        when(authorRepository.findByFirstNameAndLastName("Unknown", "Author"))
                .thenReturn(Optional.empty());

        // When
        Optional<Author> result = authorService.findByFirstNameAndLastName("Unknown", "Author");

        // Then
        assertFalse(result.isPresent());
        verify(authorRepository, times(1)).findByFirstNameAndLastName("Unknown", "Author");
    }

    @Test
    void testFindByFirstNameContaining() {
        // Given
        List<Author> authors = Arrays.asList(author1);
        when(authorRepository.findByFirstNameContainingIgnoreCase("John"))
                .thenReturn(authors);

        // When
        List<Author> result = authorService.findByFirstNameContaining("John");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(author1, result.get(0));
        verify(authorRepository, times(1)).findByFirstNameContainingIgnoreCase("John");
    }

    @Test
    void testFindByLastNameContaining() {
        // Given
        List<Author> authors = Arrays.asList(author2);
        when(authorRepository.findByLastNameContainingIgnoreCase("Smith"))
                .thenReturn(authors);

        // When
        List<Author> result = authorService.findByLastNameContaining("Smith");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(author2, result.get(0));
        verify(authorRepository, times(1)).findByLastNameContainingIgnoreCase("Smith");
    }
}

