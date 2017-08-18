package com.yang.myProject.dao.remote.common;

import java.util.List;

/**
 * @author Yangjing
 */
public interface RemoteGenericDao {
    Object executeSingleStatement(String sql);

    List getResultList(String sql);
}
