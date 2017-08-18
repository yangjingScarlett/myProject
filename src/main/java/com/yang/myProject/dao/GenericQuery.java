package com.yang.myProject.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yangjing
 */
public class GenericQuery<T> implements Serializable{
    private static final Logger logger = LoggerFactory.getLogger(GenericQuery.class);

    /**
     * 查询条件列表
     */
    private Root<T> from;

    private List<Predicate> predicates;

    private CriteriaQuery<T> criteriaQuery;

    private CriteriaBuilder criteriaBuilder;

    private List<Order> orders;

    private GenericQuery() {
    }

    private GenericQuery(Class<T> clazz, CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
        this.criteriaQuery = criteriaBuilder.createQuery(clazz);
        this.from = criteriaQuery.from(clazz);
        this.predicates = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    /**
     * 通过类创建查询条件。
     *
     * @param clazz           要查询的模型对象
     * @param criteriaBuilder {@link CriteriaBuilder}对象
     * @return 查询条件对象
     */
    @SuppressWarnings({"unchecked"})
    public static GenericQuery forClass(Class clazz, CriteriaBuilder criteriaBuilder) {
        return new GenericQuery(clazz, criteriaBuilder);
    }

    public void eq(String propertyName, Object value) {
        if (value != null) {
            this.predicates.add(criteriaBuilder.equal(from.get(propertyName), value));
        }
    }

    public void isNull(String propertyName) {
        this.predicates.add(criteriaBuilder.isNull(from.get(propertyName)));
    }

    public void isNotNull(String propertyName) {
        this.predicates.add(criteriaBuilder.isNotNull(from.get(propertyName)));
    }

    public void notEq(String propertyName, Object value) {
        if (value != null) {
            this.predicates.add(criteriaBuilder.notEqual(from.get(propertyName), value));
        }
    }

    /**
     * 时间区间查询
     *
     * @param propertyName 属性名称
     * @param lo           属性起始值
     * @param go           属性结束值
     */
    public void between(String propertyName, String lo, String go) {
        if (lo != null && go != null) {
            Expression expression=from.get(propertyName);
            this.predicates.add(criteriaBuilder.between(expression, lo, go));
        }
    }

    /**
     * 直接添加JPA内部的查询条件,用于应付一些复杂查询的情况,例如或
     */
    public void addCriterions(Predicate predicate) {
        this.predicates.add(predicate);
    }

    /**
     * 创建查询条件
     *
     * @return JPA离线查询
     */
    public CriteriaQuery<T> newCriteriaQuery() {
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        if (this.orders != null) {
            criteriaQuery.orderBy(orders);
        }
        return criteriaQuery;
    }

    public void addOrder(String propertyName, boolean isAsc) {
        if (propertyName == null) {
            return;
        }

        if (this.orders == null)
            this.orders = new ArrayList<>();

        if (isAsc) {
            this.orders.add(criteriaBuilder.asc(from.get(propertyName)));
        } else {
            this.orders.add(criteriaBuilder.desc(from.get(propertyName)));
        }
    }
}
