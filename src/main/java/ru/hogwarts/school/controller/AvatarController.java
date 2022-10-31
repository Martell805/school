package ru.hogwarts.school.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarService avatarService;

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if(avatar.getSize() > 1024 * 1024) {
            return ResponseEntity.badRequest().body("File is too big");
        }

        this.avatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/avatar/preview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = this.avatarService.findAvatarByStudentId(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getPreview().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getPreview());
    }

    @GetMapping("/{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException{
        Avatar avatar = this.avatarService.findAvatarByStudentId(id);

        Path path = Path.of(avatar.getFilePath());

        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream();
                ) {
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping("/avatars")
    public List<Avatar> findAvatars(@RequestParam(required = false) Integer pageNumber,
                                    @RequestParam(required = false) Integer pageSize){

        return this.avatarService.getAllAvatars(pageNumber, pageSize);
    }
}
