package com.phu.onlineshop.model.cart;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username")
    @JsonIdentityReference(alwaysAsId = true)
    private User owner;
    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    @JsonIgnoreProperties({"cart"})
    private List<CartItem> items = new ArrayList<>();

    public Cart()
    {}

    public Cart(final Long id, final User owner, final List<CartItem> items)
    {
        this.id = id;
        this.owner = owner;
        this.items = items;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
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
