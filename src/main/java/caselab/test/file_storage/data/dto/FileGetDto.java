package caselab.test.file_storage.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileGetDto {
    private String title;
    private LocalDateTime creationDate;
    private String description;
    private String fileContent;
}
