package caselab.test.file_storage.controller;

import caselab.test.file_storage.data.dto.FileCreateDto;
import caselab.test.file_storage.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file-storage")
@AllArgsConstructor
public class FileController {
    private FileService fileService;

    @PostMapping("/create")
    public ResponseEntity<Long> createFile(@RequestBody FileCreateDto fileCreateDto) {
        Long id = fileService.createFile(fileCreateDto);
        return ResponseEntity.ok(id);
    }

}
