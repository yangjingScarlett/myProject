package com.yang.myProject.dao.local.common.impl;

import com.yang.myProject.dao.local.common.BusinessDateDao;
import com.yang.myProject.dao.remote.common.impl.RemoteGenericDaoImpl;
import com.yang.myProject.enums.SQLQueryEnum;
import com.yang.myProject.server.SQLQueryProcessor;
import com.yang.myProject.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Yangjing
 */
@Repository
@Transactional(transactionManager = "transactionManager", propagation = Propagation.SUPPORTS, readOnly = true)
public class BusinessDateDaoImpl extends RemoteGenericDaoImpl implements BusinessDateDao {
    private static final Logger logger = LoggerFactory.getLogger(BusinessDateDaoImpl.class);

    @Override
    public Date getBusinessDate() {
        String sql = String.format(SQLQueryProcessor.getSql(SQLQueryEnum.QUERY_BUSINESS_DATE));
        Object obj = executeSingleStatement(sql);
        if (obj == null) {
            logger.error("系统异常，无法获取系统业务日期！");
            return null;
        } else {
            return DateTimeUtil.valueOfDate((String) obj);
        }
    }
}
