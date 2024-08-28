package caselab.test.file_storage.controller;

import caselab.test.file_storage.data.dto.FileCreateDto;
import caselab.test.file_storage.data.dto.FileGetDto;
import caselab.test.file_storage.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/get")
    public ResponseEntity<FileGetDto> getFile(@RequestParam Long id) {
        Optional<FileGetDto> fileEntityOptional = fileService.getFile(id);

        return fileEntityOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<FileGetDto>> getAllFiles(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {

        return ResponseEntity.ok(fileService.getAllFiles(pageNumber, pageSize));
    }
}
