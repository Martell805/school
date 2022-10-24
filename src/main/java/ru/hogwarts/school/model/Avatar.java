package ru.hogwarts.school.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Avatar {
    @Id
    @GeneratedValue
    private Long id;

    private String filePath;
    private long fileSize;

    private String mediaType;

    @Lob
    private byte[] preview;

    @OneToOne
    private Student student;
}
