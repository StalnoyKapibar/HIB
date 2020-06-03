package com.project.service;

import com.project.dao.DataEnterInAdminPanelDao;
import com.project.model.DataEnterInAdminPanel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class DataEnterInAdminPanelService {

    DataEnterInAdminPanelDao dao;

    public DataEnterInAdminPanel findById(Long id) {
        return dao.findById(id);
    }

    public void add(DataEnterInAdminPanel entity) {
        dao.add(entity);
    }

    public DataEnterInAdminPanel update(DataEnterInAdminPanel entity) {
        return dao.update(entity);
    }
}
