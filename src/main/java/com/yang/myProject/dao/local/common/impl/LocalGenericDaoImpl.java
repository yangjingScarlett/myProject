package com.yang.myProject.dao.local.common.impl;

import com.yang.myProject.dao.local.common.LocalGenericDao;
import com.yang.myProject.enums.EntityNameEnum;
import com.yang.myProject.enums.SQLQueryEnum;
import com.yang.myProject.server.SQLQueryProcessor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Yangjing
 */
@Repository
@Transactional(transactionManager = "h2transactionManager", propagation = Propagation.REQUIRED)
public class LocalGenericDaoImpl implements LocalGenericDao {

    @PersistenceContext(unitName = "h2PersistenceUnit")
    public
    EntityManager entityManager;

    @Override
    public Object executeSingleNativeStatement(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        return query.getSingleResult();
    }

    @Override
    public Object executeSingleStatement(String sql) {
        Query query = entityManager.createQuery(sql);
        return query.getSingleResult();
    }

    @Override
    public String getLogRecordDate(EntityNameEnum entityNameEnum) {
        String sql = String.format(SQLQueryProcessor.getSql(SQLQueryEnum.QUERY_STUDENT_DETAIL), entityNameEnum.toString());
        Object obj = executeSingleNativeStatement(sql);
        return obj == null ? null : (String) obj;
    }


}
