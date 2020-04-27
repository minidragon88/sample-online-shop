package com.phu.onlineshop.service;

import com.phu.onlineshop.model.cart.Cart;
import com.phu.onlineshop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService
{
    @Autowired
    private CartRepository repository;

    public List<Cart> findAll()
    {
        return repository.findAll();
    }

    public Cart finbyId(final Long id)
    {
        return repository.findById(id).orElse(null);
    }
}
