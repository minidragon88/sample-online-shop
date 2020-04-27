package com.phu.onlineshop;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = OnlineShopApp.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class CartControllerTest
{
    @Autowired
    private MockMvc mvc;
    private final ObjectNode requestData = TestHelpers.getData("cart_message.json");

    @SqlGroup({
        @Sql(scripts = "classpath:clean_data.sql"),
        @Sql(scripts = "classpath:prepare_data.sql")
    })
    @Test
    public void getAllCart() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get("/cart")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @SqlGroup({
        @Sql(scripts = "classpath:clean_data.sql")
    })
    @Test
    public void getAllCartNoData() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get("/cart")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(204));
    }

    @SqlGroup({
        @Sql(scripts = "classpath:clean_data.sql"),
        @Sql(scripts = "classpath:prepare_data.sql")
    })
    @Test
    public void getCartById() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get("/cart/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @SqlGroup({
        @Sql(scripts = "classpath:clean_data.sql"),
        @Sql(scripts = "classpath:prepare_data.sql")
    })
    @Test
    public void getCartByIdNoResult() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.get("/cart/10000")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(204));
    }

    @SqlGroup({
        @Sql(scripts = "classpath:clean_data.sql"),
        @Sql(scripts = "classpath:prepare_data.sql")
    })
    @Test
    public void checkoutSuccess() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/cart/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("checkoutSuccess").toString()))
            .andExpect(status().is(201))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @SqlGroup({
        @Sql(scripts = "classpath:clean_data.sql"),
        @Sql(scripts = "classpath:prepare_data.sql")
    })
    @Test
    public void checkoutMissingUser() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/cart/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("checkoutMissingUser").toString()))
            .andExpect(status().is(400))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @SqlGroup({
        @Sql(scripts = "classpath:clean_data.sql"),
        @Sql(scripts = "classpath:prepare_data.sql")
    })
    @Test
    public void checkoutNonExistedUser() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/cart/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("checkoutNonExistedUser").toString()))
            .andExpect(status().is(400))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @SqlGroup({
        @Sql(scripts = "classpath:clean_data.sql"),
        @Sql(scripts = "classpath:prepare_data.sql")
    })
    @Test
    public void checkoutMissingProduct() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/cart/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("checkoutMissingProduct").toString()))
            .andExpect(status().is(400))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @SqlGroup({
        @Sql(scripts = "classpath:clean_data.sql"),
        @Sql(scripts = "classpath:prepare_data.sql")
    })
    @Test
    public void checkoutNonExistedProduct() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/cart/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("checkoutNonExistedProduct").toString()))
            .andExpect(status().is(400))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @SqlGroup({
        @Sql(scripts = "classpath:clean_data.sql"),
        @Sql(scripts = "classpath:prepare_data.sql")
    })
    @Test
    public void checkoutMissingQuantity() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/cart/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("checkoutMissingQuantity").toString()))
            .andExpect(status().is(400))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @SqlGroup({
        @Sql(scripts = "classpath:clean_data.sql"),
        @Sql(scripts = "classpath:prepare_data.sql")
    })
    @Test
    public void checkoutNegativeQuantity() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/cart/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("checkoutNegativeQuantity").toString()))
            .andExpect(status().is(400))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
