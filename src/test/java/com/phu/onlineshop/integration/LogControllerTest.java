package com.phu.onlineshop.integration;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.phu.onlineshop.OnlineShopApp;
import com.phu.onlineshop.TestHelpers;
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
@SqlGroup({
    @Sql(scripts = "classpath:clean_data.sql"),
    @Sql(scripts = "classpath:prepare_data.sql")
})
public class LogControllerTest
{
    @Autowired
    private MockMvc mvc;
    private final ObjectNode requestData = TestHelpers.getData("log_message.json");

    @Test
    public void logSuccess() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/log")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("logSuccess").toString()))
            .andExpect(status().is(201))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void logMissingAction() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/log")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("logMissingAction").toString()))
            .andExpect(status().is(400))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void logEmptyAction() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/log")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("logEmptyAction").toString()))
            .andExpect(status().is(400))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void logMissingData() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/log")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("logMissingData").toString()))
            .andExpect(status().is(400))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void logEmptyData() throws Exception
    {
        mvc.perform(MockMvcRequestBuilders.post("/log")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestData.get("logEmptyData").toString()))
            .andExpect(status().is(400))
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
