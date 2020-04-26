package com.phu.onlineshop.model.cart;

import com.phu.onlineshop.model.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User owner;
    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    List<CartItem> items = new ArrayList<>();

    public Cart()
    {}

    public Cart(final Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(final Long id, final User owner, final List<CartItem> items)
    {
        this.id = id;
        this.owner = owner;
        this.items = items;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(final User owner)
    {
        this.owner = owner;
    }

    public List<CartItem> getItems()
    {
        return items;
    }

    public void setItems(final List<CartItem> items)
    {
        this.items = items;
    }

    public Double getTotal()
    {
        return items.stream().mapToDouble(item -> item.getTotal()).sum();
    }
}
