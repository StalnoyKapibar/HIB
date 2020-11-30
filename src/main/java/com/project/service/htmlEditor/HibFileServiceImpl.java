package com.project.service.htmlEditor;

import com.project.HIBParser.HibParser;
import com.project.dao.abstraction.HibFileDao;
import com.project.model.Book;
import com.project.model.HibFileDto;
import com.project.service.abstraction.HibFileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class HibFileServiceImpl implements HibFileService {

    private final HibFileDao hibFileDao;
    private final HibParser hibParser;


    @Override
    public List<Book> getAllBooks() {
        return hibFileDao.getAllBooks();
    }

    @Override
    public Book getBookFromHibFileByName(String name) {
        try {
            return hibParser.getBookFromJSON(new String(Files.readAllBytes(Paths.get("./HIB/" + name))), name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("An error occurred while reading the file");
    }

    @Override
    public void deleteByName(String name) {
        hibFileDao.deleteByName(name);
    }

    @Override
    public void bulkLoading(MultipartFile[] files) {
        File hibPath = new File("./HIB/");
        if (!hibPath.exists()){
            hibPath.mkdir();
        }
        for (MultipartFile file : files) {
            try {
                hibFileDao.saveHibFile(new File("./HIB/" +
                        file.getOriginalFilename().replaceAll(" ", "_")), file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteAllHIBs() {
        File folder = new File("./HIB/");
        for (File myFile : folder.listFiles()) {
            if (myFile.isFile()) {
                myFile.delete();
            }
        }
    }

}
