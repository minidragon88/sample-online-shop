package com.phu.onlineshop.controller;

import com.phu.onlineshop.model.product.Product;
import com.phu.onlineshop.model.product.SearchMessage;
import com.phu.onlineshop.model.product.SearchMessage.Column;
import com.phu.onlineshop.model.product.SearchMessage.Operator;
import com.phu.onlineshop.response.APIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController
{
    @Autowired
    private EntityManager entityManager;
    private static final int PAGE_SIZE = 10;

    @PostMapping("/search")
    public ResponseEntity<APIResponse<List<Product>>> findAll(@RequestBody final SearchMessage message)
    {
        final Column column = message.getColumn();
        final Operator operator = message.getOperator();
        final List<Operator> allowOperators = column.getAllowOperators();
        if (!allowOperators.contains(operator)) {
            final String errorMessage = String.format(
                    "Filtering by method \"%s\" is not allowed in column \"%s\". Supported operators %s",
                    operator.getName(),
                    column.getColumn(),
                    allowOperators.stream().map(allowOperator -> allowOperator.getName()).collect(Collectors.toList()));
            return new APIResponse<List<Product>>(HttpStatus.BAD_REQUEST.value(), errorMessage, null).toResponseEntity();
        }
        if (message.getPage() < 0) {
            final String errorMessage = "Page should start from 0";
            return new APIResponse<List<Product>>(HttpStatus.BAD_REQUEST.value(), errorMessage, null).toResponseEntity();
        }
        return new APIResponse<List<Product>>(HttpStatus.OK.value(), null, createTypedQuery(message).getResultList()).toResponseEntity();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private TypedQuery<Product> createTypedQuery(final SearchMessage message)
    {
        TypedQuery<Product> query = null;
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
        final Root<Product> root = criteria.from(Product.class);
        criteria.select(root);
        if (message.getTerm().isEmpty()) {
            query = entityManager.createQuery(criteria);
        }
        else {
            ParameterExpression param;
            switch (message.getColumn()) {
            case PRICE:
                param = cb.parameter(Double.class);
                switch (message.getOperator()) {
                case EQUAL:
                    criteria.where(cb.equal(root.get(message.getColumn().getColumn()), param));
                    break;
                case GREATER:
                    criteria.where(cb.greaterThan(root.get(message.getColumn().getColumn()), param));
                    break;
                case LESS:
                    criteria.where(cb.lessThan(root.get(message.getColumn().getColumn()), param));
                    break;
                case GREATER_THAN_OR_EQUAL:
                    criteria.where(cb.greaterThanOrEqualTo(root.get(message.getColumn().getColumn()), param));
                    break;
                default:
                    criteria.where(cb.lessThanOrEqualTo(root.get(message.getColumn().getColumn()), param));
                    break;
                }
                break;

            default:
                param = cb.parameter(String.class);
                switch (message.getOperator()) {
                case EQUAL:
                    criteria.where(cb.equal(root.get(message.getColumn().getColumn()), param));
                    break;
                default:
                    criteria.where(cb.like(root.get(message.getColumn().getColumn()), param));
                    break;
                }
                break;
            }
            query = entityManager.createQuery(criteria);
            switch (message.getColumn()) {
            case PRICE:
                query.setParameter(param, Double.valueOf(message.getTerm()));
                break;

            default:
                switch (message.getOperator()) {
                case LIKE:
                    query.setParameter(param, "%" + message.getTerm() + "%");
                    break;

                default:
                    query.setParameter(param, message.getTerm());
                    break;
                }
                break;
            }
        }
        query.setFirstResult(message.getPage() * PAGE_SIZE);
        query.setMaxResults(PAGE_SIZE);
        return query;
    }
}
