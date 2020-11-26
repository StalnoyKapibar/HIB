package com.project.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.HIBParser.HibParser;
import com.project.dao.abstraction.HibFileDao;
import com.project.model.Book;
import com.project.model.HibFileDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@AllArgsConstructor
public class HibFileDaoImpl implements HibFileDao {
    private final ObjectMapper objectMapper;
    private final HibParser hibParser;

    public List<Book> getAllBooks() {
        List<File> hibFiles = null;
        List<Book> booksFromHIBs = new ArrayList<>();

        try {
            //TODO: Pay attention when deploying
            hibFiles = Files.walk(Paths.get("./HIB/"))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (hibFiles != null) {
            for (File file : hibFiles) {
                JsonNode hibFileTree = null;
                try {
                    hibFileTree = objectMapper.readTree(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (hibFileTree != null) {
                    booksFromHIBs.add(hibParser.getBookFromJSON(hibFileTree.toString(), file.getName().replaceAll(" ", "_")));
                }
            }
        }
        return booksFromHIBs;
    }



    @Override
    public void deleteByName(String name) {
        try {
            Files.delete(Paths.get("./HIB/" + name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveHibFile(File file, byte[] data) {
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
