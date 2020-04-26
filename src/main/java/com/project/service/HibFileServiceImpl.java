package com.project.service;

import com.project.HIBParser.HibParser;
import com.project.dao.abstraction.HibFileDao;
import com.project.model.BookDTO;
import com.project.model.HibFileDto;
import com.project.service.abstraction.HibFileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@AllArgsConstructor
public class HibFileServiceImpl implements HibFileService {
    private final HibFileDao hibFileDao;
    private final HibParser hibParser;

    @Override
    public List<HibFileDto> getAllDto() {
        return hibFileDao.getAllDto();
    }

    @Override
    public BookDTO getBookDtoFromHibFileByName(String name) {
        try {
            return hibParser.getBookFromJSON(new String(Files.readAllBytes(Paths.get("hib/" + name + ".hib"))));
        } catch (IOException e) {
            e.printStackTrace();

        }
        throw new RuntimeException("An error occurred while reading the file");
    }

    @Override
    public void deleteByName(String name) {
        hibFileDao.deleteByName(name + ".hib");
    }

    @Override
    public void bulkLoading(MultipartFile[] files) {
        for (MultipartFile file : files) {
            try {
                hibFileDao.saveHibFile(new File("hib/" +
                        file.getOriginalFilename().replaceAll(" ", "_")), file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
