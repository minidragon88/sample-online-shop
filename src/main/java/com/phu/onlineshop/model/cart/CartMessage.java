package com.phu.onlineshop.model.cart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.phu.onlineshop.Utils;

import java.util.ArrayList;
import java.util.List;

public class CartMessage
{
    private String user;
    private final List<Item> items = new ArrayList<>();

    public String getUser()
    {
        return user;
    }
    public void setUser(final String user)
    {
        this.user = user;
    }

    public List<Item> getItems()
    {
        return items;
    }

    public static class Item
    {
        private Long product;
        private Integer quantity;

        public Long getProduct()
        {
            return product;
        }

        public void setProduct(final Long product)
        {
            this.product = product;
        }

        public Integer getQuantity()
        {
            return quantity;
        }

        public void setQuantity(final Integer quantity)
        {
            this.quantity = quantity;
        }
    }

    @Override
    public String toString()
    {
        try {
            return Utils.MAPPER.writeValueAsString(this);
        }
        catch (final JsonProcessingException e) {
            e.printStackTrace();
        }
        return super.toString();
    }
}
