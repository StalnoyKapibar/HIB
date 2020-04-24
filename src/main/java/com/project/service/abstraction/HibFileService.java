package com.project.service.abstraction;

import com.project.model.BookDTO;
import com.project.model.HibFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HibFileService {
    List<HibFileDto> getAllDto();

    BookDTO getBookDtoFromHibFileByName(String name);

    void deleteByName(String name);

    void bulkLoading(MultipartFile[] files);
}
