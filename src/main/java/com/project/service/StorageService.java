package com.project.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	void saveImage(MultipartFile file);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

	String getRandomFileNameString();

	void deleteImageByFileName(String fileName) throws IOException;

	void createNewPaperForImages(String namePaper);

	void cutImagesFromTmpPaperToNewPaperByLastIdBook(String namePaper);

}
