package com.project.service.abstraction;

import com.project.model.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {

	String saveImage(MultipartFile file, String picsFolder);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);

	void deleteAll();

	String getRandomFileNameString();

	void deleteImageByFileName(String fileName);

	void createNewPaperForImages(String namePaper);

	void copyDefaultPhotoToFolder(String namePaper);

	void cutImagesFromTmpPaperToNewPaperByLastIdBook(String namePaper, String picsFolder, List<Image> imageList);

	void cutImagesFromTmpPaperToNewPaperByLastIdBook(String namePaper, String picsFolder);

	void clearPaperTmp(String bookId);

	void clearAllTempPics();

	void deleteImageByFileNameByEditPage(String fileName);

	void saveImageByEditBook(MultipartFile file, String numberPaper);

	void deletePaperById(long number);

	void createTmpFolderForImages();

	boolean doesFolderTmpExist();

    void deleteImageByFromDB(Long id);
}
