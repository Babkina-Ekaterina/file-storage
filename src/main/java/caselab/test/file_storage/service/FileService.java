package caselab.test.file_storage.service;

import caselab.test.file_storage.data.dto.FileCreateDto;
import caselab.test.file_storage.data.dto.FileGetDto;
import caselab.test.file_storage.data.entity.FileEntity;
import caselab.test.file_storage.data.mapper.FileMapper;
import caselab.test.file_storage.data.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FileService {
    private FileRepository fileRepository;
    private FileMapper fileMapper;

    public Long createFile(FileCreateDto fileCreateDto) {
        return fileRepository.save(fileMapper.fileCreateDtoToFileEntity(fileCreateDto)).getId();
    }

    public Optional<FileGetDto> getFile(Long id) {
        Optional<FileEntity> fileEntityOptional = fileRepository.findById(id);

        if (fileEntityOptional.isPresent()) {
            return fileEntityOptional.map(entity -> fileMapper.fileEntityToFileGetDto(entity));
        }
        return Optional.empty();
    }
}
