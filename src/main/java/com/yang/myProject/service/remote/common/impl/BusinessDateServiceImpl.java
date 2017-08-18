package com.yang.myProject.service.remote.common.impl;

import com.yang.myProject.dao.local.common.BusinessDateDao;
import com.yang.myProject.service.remote.common.BusinessDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Yangjing
 */
@Service
@Transactional(transactionManager = "transactionManager",propagation = Propagation.SUPPORTS,readOnly = true)
public class BusinessDateServiceImpl implements BusinessDateService {
    @Autowired
    private BusinessDateDao businessDateDao;
    public Date getBusinessDate(){
        return businessDateDao.getBusinessDate();
    }
}
