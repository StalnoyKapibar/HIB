package com.project.dao.abstraction;

import com.project.model.HibFileDto;

import java.io.File;
import java.util.List;

public interface HibFileDao {
    List<HibFileDto> getAllDto();

    void deleteByName(String name);

    void saveHibFile(File file, byte[] data);
}
