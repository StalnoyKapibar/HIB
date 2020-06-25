package com.project.service;

import com.project.dao.abstraction.LocalStringDao;
import com.project.model.Category;
import com.project.model.LocaleString;
import com.project.service.abstraction.LocalStringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LocalStringServiceImpl implements LocalStringService {

    @Autowired
    LocalStringDao localStringDao;

    @Override
    public void addLocalString(LocaleString localeString) {
        localStringDao.addLocalString(localeString);
    }
}
