package ru.hogwarts.school.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AvatarService {
    @Value("${avatars.path}")
    private String avatarsPath;

    private final Logger logger = LoggerFactory.getLogger(AvatarService.class);

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public void uploadAvatar(Long id, MultipartFile avatar) throws IOException {
        this.logger.info("Uploaded avatar {}", id);

        Student student = this.studentService.findStudent(id);

        Path filePath = Path.of(avatarsPath, id + "." + getExtension(avatar.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = avatar.getInputStream();
                OutputStream os = Files.newOutputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar newAvatar = this.findAvatarByStudentId(id);

        if(newAvatar == null) {
            newAvatar = new Avatar();
        }

        newAvatar.setStudent(student);
        newAvatar.setFilePath(filePath.toString());
        newAvatar.setFileSize(avatar.getSize());
        newAvatar.setMediaType(avatar.getContentType());
        newAvatar.setPreview(generateImagePreview(filePath));
        
        this.avatarRepository.save(newAvatar);
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        this.logger.info("Generated preview for {}", filePath);

        try (
                InputStream is = Files.newInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, 100, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    public Avatar findAvatar(Long id) {
        this.logger.info("Found avatar {}", id);
        return this.avatarRepository.findById(id).orElse(null);
    }

    public Avatar findAvatarByStudentId(Long id) {
        this.logger.info("Found avatar by student id {}", id);

        return this.avatarRepository.findByStudentId(id).orElse(null);
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

    public List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize){
        this.logger.info("Found all avatars on page number {} (size {})", pageNumber, pageSize);

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return this.avatarRepository.findAll(pageRequest).getContent();
    }
}
