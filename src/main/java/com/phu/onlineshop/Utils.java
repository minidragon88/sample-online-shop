package com.phu.onlineshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.phu.onlineshop.model.product.Product;
import com.phu.onlineshop.model.product.SearchMessage;
import org.yaml.snakeyaml.Yaml;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import java.io.FileReader;
import java.io.IOException;

public final class Utils
{
    private Utils()
    {}

    public static final Yaml YAML = new Yaml();
    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static final Configuration RUNTIME_CONFIGURATION = loadConfig();

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static TypedQuery<Product> buildProductSearchQuery(final EntityManager entityManager, final SearchMessage message)
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
        query.setFirstResult(message.getPage() * RUNTIME_CONFIGURATION.getPageSize());
        query.setMaxResults(RUNTIME_CONFIGURATION.getPageSize());
        return query;
    }

    private static Configuration loadConfig()
    {
        try (FileReader reader = new FileReader(Resources.getResource("online_shop.yml").getPath())) {
            return YAML.loadAs(reader, Configuration.class);
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
