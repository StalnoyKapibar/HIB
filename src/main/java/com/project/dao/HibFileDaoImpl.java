package com.project.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dao.abstraction.HibFileDao;
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

@Repository
@AllArgsConstructor
public class HibFileDaoImpl implements HibFileDao {
    private final ObjectMapper objectMapper;

    @Override
    public List<HibFileDto> getAllDto() {
        List<File> hibFiles = null;
        List<HibFileDto> hibFileDtoList = new ArrayList<>();
        try {
            //TODO: Pay attention when deploying
            hibFiles = Files.walk(Paths.get("hib"))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (hibFiles != null) {
            for (File file : hibFiles) {
                hibFileDtoList.add(convertFileToDto(file));
            }
        }
        return hibFileDtoList;
    }

    private HibFileDto convertFileToDto(File file) {
        JsonNode hibFileTree = null;
        HibFileDto hibFileDto;
        try {
            hibFileTree = objectMapper.readTree(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (hibFileTree == null) {
            throw new RuntimeException("An error occurred while parsing the hib file");
        }
        hibFileDto = HibFileDto
                .builder()
                .nameOfBook(hibFileTree
                        .get("name")
                        .get("en")
                        .asText() + " - " +
                        hibFileTree
                                .get("author")
                                .get("en")
                                .asText())
                .name(file.getName().substring(0, file.getName().indexOf(".hib")))
                .imageAsBase64(hibFileTree.get("avatar").asText())
                .build();
        return hibFileDto;
    }

    @Override
    public void deleteByName(String name) {
        try {
            Files.delete(Paths.get("hib/" + name));
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
