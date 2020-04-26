package com.phu.onlineshop.model.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String colour;
    @ManyToOne
    @JoinColumn(name = "catalog_id", referencedColumnName = "name")
    private Catalog catalog;
    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "name")
    private Branch branch;

    public Product()
    {}

    public Product(final Long id, final String name, final String description, final Double price, final String colour)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.colour = colour;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(final Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(final String description)
    {
        this.description = description;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(final Double price)
    {
        this.price = price;
    }

    public String getColour()
    {
        return colour;
    }

    public void setColour(final String colour)
    {
        this.colour = colour;
    }

    public Catalog getCatalog()
    {
        return catalog;
    }

    public void setCatalog(final Catalog catalog)
    {
        this.catalog = catalog;
    }

    public Branch getBranch()
    {
        return branch;
    }

    public void setBranch(final Branch branch)
    {
        this.branch = branch;
    }
}
