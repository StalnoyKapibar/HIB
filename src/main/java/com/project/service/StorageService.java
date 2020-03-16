package com.project.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void saveImage(MultipartFile file);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

	String getRandomFileNameString();

	void deleteImageByFileName(String fileName);

	void createNewPaperForImages(String namePaper);

	void cutImagesFromTmpPaperToNewPaperByLastIdBook(String namePaper);

	void clearPaperTmp();

	void deleteImageByFileNameByEditPage(String fileName);

	void saveImageByEditBook(MultipartFile file, String numberPaper);

	void deletePaperById(long number);

}
