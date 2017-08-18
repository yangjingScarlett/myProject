package com.yang.myProject.dao.local.student.impl;

import com.yang.myProject.dao.GenericQuery;
import com.yang.myProject.dao.local.common.impl.LocalGenericDaoImpl;
import com.yang.myProject.dao.local.student.LocalStudentDao;
import com.yang.myProject.entity.local.Student;
import com.yang.myProject.util.DateTimeUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collection;
import java.util.Date;

/**
 * @author Yangjing
 */
@Repository
@Transactional(transactionManager = "h2TransactionManager", propagation = Propagation.REQUIRED)
public class LocalStudentDaoImpl extends LocalGenericDaoImpl implements LocalStudentDao {
    @Override
    public String getMaxEnterDate() {
        String sql = "SELECT MAX(ENTER_DATE) FROM L_STUDENT";
        String str = (String) executeSingleNativeStatement(sql);
        return str;
    }

    @Override
    public String getMinEnterDate() {
        String sql = "SELECT MIN(ENTER_DATE) FROM L_STUDENT";
        return (String) executeSingleNativeStatement(sql);
    }

    @Override
    public void saveStudent(Collection<Student> students) {
        for (Student student : students) {
            entityManager.persist(student);
        }
    }

    @Override
    public void saveStudent(Student student) {
        entityManager.persist(student);
    }

    @Override
    public Student getStudent(String enterDate) {
        return entityManager.find(Student.class, enterDate);
    }

    @Override
    public Collection<Student> queryStatsData(Date startDate, Date endDate) {
        String start = DateTimeUtil.toDateString(startDate);
        String end = DateTimeUtil.toDateString(endDate);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        GenericQuery<Student> genericQuery = GenericQuery.forClass(Student.class, criteriaBuilder);
        genericQuery.between("enterDate", start, end);
        genericQuery.addOrder("enterDate", true);
        return entityManager.createQuery(genericQuery.newCriteriaQuery()).getResultList();
        /*CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery=criteriaBuilder.createQuery(Student.class);
        //相当于select语句里的from ...
        Root<Student> studentRoot=criteriaQuery.from(Student.class);
        //相当于select语句里面的where enterDate between start and end;
        Expression expression=studentRoot.get("enterDate");
        Predicate predicate=criteriaBuilder.between(expression, end, start);
        criteriaQuery.where(predicate);
        TypedQuery<Student> typedQuery=entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();*/
    }
}
