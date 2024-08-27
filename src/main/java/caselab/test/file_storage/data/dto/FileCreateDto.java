package caselab.test.file_storage.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileCreateDto {
    private String title;
    private String description;
    private String fileContent;
}
