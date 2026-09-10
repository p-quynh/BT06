package vn.iotstar.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

public final class ImageStorage {
    private static final long MAX_SIZE = 5L * 1024 * 1024;
    private static final Map<String, String> EXTENSIONS = Map.of(
            "image/jpeg", ".jpg", "image/png", ".png",
            "image/webp", ".webp", "image/gif", ".gif"
    );

    private ImageStorage() {}

    public static String store(MultipartFile file, String folder) throws IOException {
        if (file == null || file.isEmpty()) return null;
        if (file.getSize() > MAX_SIZE) {
            throw new IllegalArgumentException("Ảnh không được vượt quá 5 MB");
        }
        String extension = EXTENSIONS.get(file.getContentType());
        if (extension == null) {
            throw new IllegalArgumentException("Chỉ chấp nhận ảnh JPG, PNG, WEBP hoặc GIF");
        }
        Path root = Path.of("src", "main", "webapp", "uploads", folder).toAbsolutePath().normalize();
        Files.createDirectories(root);
        String fileName = UUID.randomUUID() + extension;
        Path destination = root.resolve(fileName).normalize();
        if (!destination.getParent().equals(root)) {
            throw new IOException("Đường dẫn upload không hợp lệ");
        }
        try (var input = file.getInputStream()) {
            Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
        }
        return fileName;
    }
}
