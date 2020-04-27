package com.phu.onlineshop.service;

import com.phu.onlineshop.model.product.Product;
import com.phu.onlineshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository repository;

    public List<Product> findAll()
    {
        return repository.findAll();
    }

    public Product findById(final Long id)
    {
        return repository.findById(id).orElse(null);
    }
}
