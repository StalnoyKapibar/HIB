package com.project.dao;

import com.project.model.DataEnterInAdminPanel;
import org.springframework.stereotype.Repository;

@Repository
public class DataEnterInAdminPanelDao extends AbstractDao<Long, DataEnterInAdminPanel> {
    DataEnterInAdminPanelDao(){ super(DataEnterInAdminPanel.class);
    }
}
