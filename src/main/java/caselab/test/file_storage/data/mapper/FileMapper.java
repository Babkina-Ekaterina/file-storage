package caselab.test.file_storage.data.mapper;

import caselab.test.file_storage.data.dto.FileCreateDto;
import caselab.test.file_storage.data.entity.FileEntity;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {
    public FileEntity fileCreateDtoToFileEntity (FileCreateDto fileCreateDto) {
        FileEntity fileEntity = new FileEntity();

        fileEntity.setTitle(fileCreateDto.getTitle());
        fileEntity.setDescription(fileCreateDto.getDescription());
        fileEntity.setFileContent(fileCreateDto.getFileContent());

        return fileEntity;
    }
}
