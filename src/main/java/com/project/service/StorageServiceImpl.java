package com.project.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.stream.Stream;

import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path path = Paths.get("img/tmp/");

    @Override
    public void saveImage(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, this.path.resolve(file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.path, 1)
                    .filter(path -> !path.equals(this.path))
                    .map(this.path::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return path.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);

            file.toFile();

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename + "");

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(path.toFile());
    }

    @Override
    public String getRandomFileNameString() {
        return String.valueOf(new Random().nextLong() + ".jpg");
    }

    @Override
    public void deleteImageByFileName(String fileName) {
        try {
            Files.delete(Paths.get(path + "\\" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createNewPaperForImages(String namePaper) {
        try {
            Files.createDirectory(Paths.get("img/" + namePaper));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cutImagesFromTmpPaperToNewPaperByLastIdBook(String namePaper) {
        try {
            Files.copy(Paths.get("img/tmp"), Paths.get("img/" + namePaper));
        } catch (IOException e) {
            e.printStackTrace();
        }
       // deleteAll();
    }


    @Override
    public void init() {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
