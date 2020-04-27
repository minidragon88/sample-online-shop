package com.phu.onlineshop.integration;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.phu.onlineshop.OnlineShopApp;
import com.phu.onlineshop.TestHelpers;
import org.hamcrest.Matchers;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = OnlineShopApp.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@SqlGroup({
    @Sql(scripts = "classpath:clean_data.sql"),
    @Sql(scripts = "classpath:prepare_data.sql")
})
public class ProductIntegrationTest
{
    @Autowired
    private MockMvc mvc;
    private final ObjectNode requestData = TestHelpers.getData("product_search_message.json");

    @Test
    public void allDataFirstPage() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("allDataFirstPage").toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.results").isArray())
            .andExpect(jsonPath("$.results", Matchers.hasSize(10)));
    }

    @Test
    public void allDataSecondPage() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("allDataSecondPage").toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.results").isArray())
            .andExpect(jsonPath("$.results", Matchers.hasSize(6)));
    }

    @Test
    public void likeOnNameColumn() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("likeOnNameColumn").toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.results").isArray())
            .andExpect(jsonPath("$.results", Matchers.hasSize(2)));
    }

    @Test
    public void equalOnNameColumn() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("equalOnNameColumn").toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.results").isArray())
            .andExpect(jsonPath("$.results", Matchers.hasSize(1)));
    }

    @Test
    public void likeOnNameColumnAsc() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("likeOnNameColumnAsc").toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.results").isArray())
            .andExpect(jsonPath("$.results", Matchers.hasSize(2)))
            .andExpect(jsonPath("$.results[0].id", Matchers.is(1)));
    }

    @Test
    public void likeOnNameColumnDesc() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("likeOnNameColumnDesc").toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.results").isArray())
            .andExpect(jsonPath("$.results", Matchers.hasSize(2)))
            .andExpect(jsonPath("$.results[0].id", Matchers.is(2)));
    }

    @Test
    public void equalPrice() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("equalPrice").toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.results").isArray())
            .andExpect(jsonPath("$.results", Matchers.hasSize(1)));
    }

    @Test
    public void gtPrice() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("gtPrice").toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.results").isArray())
            .andExpect(jsonPath("$.results", Matchers.hasSize(6)));
    }

    @Test
    public void gtOrEqualPrice() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("gtOrEqualPrice").toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.results").isArray())
            .andExpect(jsonPath("$.results", Matchers.hasSize(7)));
    }

    @Test
    public void ltPrice() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("ltPrice").toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.results").isArray())
            .andExpect(jsonPath("$.results", Matchers.hasSize(9)));
    }

    @Test
    public void ltOrEqualPrice() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("ltOrEqualPrice").toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.results").isArray())
            .andExpect(jsonPath("$.results", Matchers.hasSize(10)));
    }

    @Test
    public void noResult() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("noResult").toString()))
            .andExpect(status().is(204));
    }

    @Test
    public void negativePage() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("negativePage").toString()))
            .andExpect(status().is(400));
    }

    @Test
    public void gtOnNameColumn() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("gtOnNameColumn").toString()))
            .andExpect(status().is(400));
    }
}
