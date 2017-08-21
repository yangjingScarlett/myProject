package com.yang.myProject.testAop;

import org.springframework.stereotype.Service;

/**
 * @author Yangjing
 */
@Service("daoImpl")
public class DaoImpl implements Dao {
    @Override
    public void insert(){
        System.out.println("DaoImpl.insert()");
    }

    @Override
    public void update(){
        System.out.println("DaoImpl.update()");
    }

    @Override
    public void delete(){
        System.out.println("DaoImpl.delete()");
    }
}
