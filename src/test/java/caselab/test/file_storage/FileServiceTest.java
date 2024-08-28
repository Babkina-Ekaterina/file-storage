package caselab.test.file_storage;

import caselab.test.file_storage.data.dto.FileCreateDto;
import caselab.test.file_storage.data.dto.FileGetDto;
import caselab.test.file_storage.data.entity.FileEntity;
import caselab.test.file_storage.data.mapper.FileMapper;
import caselab.test.file_storage.data.repository.FileRepository;
import caselab.test.file_storage.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private FileMapper fileMapper;

    @InjectMocks
    private FileService fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createFile_ShouldReturnFileId() {
        // Given
        FileCreateDto fileCreateDto = new FileCreateDto();
        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(1L);

        when(fileMapper.fileCreateDtoToFileEntity(fileCreateDto)).thenReturn(fileEntity);
        when(fileRepository.save(any(FileEntity.class))).thenReturn(fileEntity);

        // When
        Long result = fileService.createFile(fileCreateDto);

        // Then
        assertEquals(1L, result);
    }

    @Test
    void getFile_ShouldReturnFileGetDto_WhenFileExists() {
        // Given
        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(1L);
        FileGetDto fileGetDto = new FileGetDto();

        when(fileRepository.findById(1L)).thenReturn(Optional.of(fileEntity));
        when(fileMapper.fileEntityToFileGetDto(fileEntity)).thenReturn(fileGetDto);

        // When
        Optional<FileGetDto> result = fileService.getFile(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(fileGetDto, result.get());
    }

    @Test
    void getFile_ShouldReturnEmpty_WhenFileDoesNotExist() {
        // Given
        when(fileRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Optional<FileGetDto> result = fileService.getFile(1L);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void getAllFiles_ShouldReturnListOfFileGetDto() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        FileEntity fileEntity1 = new FileEntity(1L, "File 1",
                (LocalDateTime.of(2024, 8, 1, 10, 10, 10)),
                "Description 1", "content 1");
        FileEntity fileEntity2 = new FileEntity(2L, "File 2",
                (LocalDateTime.of(2024, 7, 28, 11, 20, 20)),
                "Description 2", "content 2");

        FileGetDto fileGetDto1 = new FileGetDto("File 1", LocalDateTime.now(), "Description 1", "content 1");
        FileGetDto fileGetDto2 = new FileGetDto("File 2", LocalDateTime.now(), "Description 2", "content 2");

        Page<FileEntity> fileEntityPage = new PageImpl<>(Arrays.asList(fileEntity1, fileEntity2), pageable, 2);
        when(fileRepository.findAll(pageable)).thenReturn(fileEntityPage);
        when(fileMapper.fileEntityToFileGetDto(fileEntity1)).thenReturn(fileGetDto1);
        when(fileMapper.fileEntityToFileGetDto(fileEntity2)).thenReturn(fileGetDto2);

        // When
        List<FileGetDto> result = fileService.getAllFiles(pageable);

        // Then
        assertEquals(2, result.size());
        assertEquals("File 1", result.get(0).getTitle());
        assertEquals("File 2", result.get(1).getTitle());
    }

    @Test
    void getAllFiles_ShouldReturnEmpty_WhenFilesDoesNotExist() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<FileEntity> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(fileRepository.findAll(pageable)).thenReturn(emptyPage);

        // When
        List<FileGetDto> result = fileService.getAllFiles(pageable);

        // Then
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }

    @Test
    void getAllFiles_ShouldReturnListOfFileGetDto_WhenSecondPage() {
        // Given
        Pageable pageable = PageRequest.of(1, 1);
        FileEntity fileEntity1 = new FileEntity(1L, "File 1",
                (LocalDateTime.of(2024, 8, 1, 10, 10, 10)),
                "Description 1", "content 1");
        FileEntity fileEntity2 = new FileEntity(2L, "File 2",
                (LocalDateTime.of(2024, 7, 28, 11, 20, 20)),
                "Description 2", "content 2");

        FileGetDto fileGetDto2 = new FileGetDto("File 2", LocalDateTime.now(), "Description 2", "content 2");

        Page<FileEntity> fileEntityPage = new PageImpl<>(Arrays.asList(fileEntity2), pageable, 1);
        when(fileRepository.findAll(pageable)).thenReturn(fileEntityPage);
        when(fileMapper.fileEntityToFileGetDto(fileEntity2)).thenReturn(fileGetDto2);

        // When
        List<FileGetDto> result = fileService.getAllFiles(pageable);

        // Then
        assertEquals(1, result.size());
        assertEquals("File 2", result.get(0).getTitle());
    }
}
