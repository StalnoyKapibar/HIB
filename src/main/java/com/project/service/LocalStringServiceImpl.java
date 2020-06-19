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

    @Override
    public List<LocaleString> getLocalString(String name, List<Category> categories) {
        for (int i = 0; i < 4; i++) {
            if (localStringDao.getLocalString(name, categories).get(i) == null) {
                for (int s = 0; s < 4; s++) {
                    if (localStringDao.getLocalString("en", categories).get(s) == null) {
                        return localStringDao.getLocalString("it", categories);
                    } else if (s == 3) {
                        return localStringDao.getLocalString("en", categories);
                    }
                }
            }
        }
        return localStringDao.getLocalString(name, categories);
    }
}
