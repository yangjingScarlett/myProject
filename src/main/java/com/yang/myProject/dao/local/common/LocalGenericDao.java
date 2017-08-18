package com.yang.myProject.dao.local.common;

import com.yang.myProject.enums.EntityNameEnum;

/**
 * @author Yangjing
 */
public interface LocalGenericDao {
    Object executeSingleNativeStatement(String sql);

    Object executeSingleStatement(String sql);

    String getLogRecordDate(EntityNameEnum entityNameEnum);
}
