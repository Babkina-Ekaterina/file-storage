package caselab.test.file_storage;

import caselab.test.file_storage.controller.FileController;
import caselab.test.file_storage.data.dto.FileCreateDto;
import caselab.test.file_storage.data.dto.FileGetDto;
import caselab.test.file_storage.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileControllerTest {

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createFile_ShouldReturnFileId() {
        // Given
        FileCreateDto fileCreateDto = new FileCreateDto();
        when(fileService.createFile(any(FileCreateDto.class))).thenReturn(1L);

        // When
        ResponseEntity<Long> response = fileController.createFile(fileCreateDto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody());
        verify(fileService, times(1)).createFile(any(FileCreateDto.class));
    }

    @Test
    void getFile_ShouldReturnFileGetDto_WhenFileExists() {
        // Given
        FileGetDto fileGetDto = new FileGetDto();
        when(fileService.getFile(1L)).thenReturn(Optional.of(fileGetDto));

        // When
        ResponseEntity<FileGetDto> response = fileController.getFile(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fileGetDto, response.getBody());
        verify(fileService, times(1)).getFile(1L);
    }

    @Test
    void getFile_ShouldReturnEmpty_WhenFileDoesNotExist() {
        // Given
        when(fileService.getFile(1L)).thenReturn(Optional.empty());

        // When
        ResponseEntity<FileGetDto> response = fileController.getFile(1L);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(fileService, times(1)).getFile(1L);
    }

    @Test
    void getAllFiles_ShouldReturnListOfFileGetDto() {
        // Given
        FileGetDto file1 = new FileGetDto();
        FileGetDto file2 = new FileGetDto();
        List<FileGetDto> files = Arrays.asList(file1, file2);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("creationDate").descending());
        when(fileService.getAllFiles(pageable)).thenReturn(files);

        // When
        ResponseEntity<List<FileGetDto>> response = fileController.getAllFiles(0, 10);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(files, response.getBody());
        verify(fileService, times(1)).getAllFiles(pageable);
    }

    @Test
    void getAllFiles_ShouldReturnEmpty_WhenFilesDoesNotExist() {
        // Given
        List<FileGetDto> files = Collections.emptyList();
        Pageable pageable = PageRequest.of(0, 10, Sort.by("creationDate").descending());
        when(fileService.getAllFiles(pageable)).thenReturn(files);

        // When
        ResponseEntity<List<FileGetDto>> response = fileController.getAllFiles(0, 10);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(files, response.getBody());
        verify(fileService, times(1)).getAllFiles(pageable);
    }
}
