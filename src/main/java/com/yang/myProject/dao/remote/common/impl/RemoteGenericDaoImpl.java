package com.yang.myProject.dao.remote.common.impl;

import com.yang.myProject.dao.remote.common.RemoteGenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.util.List;

/**
 * @author Yangjing
 */
@Repository
@Transactional(transactionManager = "transactionManager", propagation = Propagation.SUPPORTS, readOnly = true)
public abstract class RemoteGenericDaoImpl implements RemoteGenericDao {
    private static final Logger logger = LoggerFactory.getLogger(RemoteGenericDaoImpl.class);
    @PersistenceContext(unitName = "persistenceUnit")
    protected EntityManager entityManager;

    @Override
    public Object executeSingleStatement(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        return query.getSingleResult();
    }

    @Override
    public List<Object[]> getResultList(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    public String ClobToString(Clob sc) {
        String reString = "";
        try {
            Reader is = sc.getCharacterStream();
            BufferedReader br = new BufferedReader(is);
            String s = br.readLine();
            StringBuffer sb = new StringBuffer();
            while (s != null) {
                sb.append(s);
                s = br.readLine();
            }

            reString = sb.toString();
        } catch (Exception e) {
            logger.error("reader exception={}", e);
        }
        return reString;
    }
}
