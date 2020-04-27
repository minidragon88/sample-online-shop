package com.phu.onlineshop.model.cart;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.phu.onlineshop.model.product.Product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart_items")
public class CartItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @JsonIgnore
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Product product;
    private int quantity;

    public CartItem()
    {}

    public CartItem(final Long id, final Cart cart, final Product product, final int quantity)
    {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public Cart getCart()
    {
        return cart;
    }

    public void setCart(final Cart cart)
    {
        this.cart = cart;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(final Product product)
    {
        this.product = product;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(final int quantity)
    {
        this.quantity = quantity;
    }

    public Double getTotal()
    {
        return quantity * product.getPrice();
    }
}
