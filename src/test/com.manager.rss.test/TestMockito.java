package com.manager.rss.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manager.rss.controllers.RssFeedController;
import com.manager.rss.entity.RssFeed;
import com.manager.rss.service.RssFeedService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RssFeedController.class)
public class TestMockito {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private RssFeedService rssFeedService;

    private static ObjectMapper mapper = new ObjectMapper();

    RssFeed rssFeed;

    @Before
    public void setUpFeed() {
        rssFeed = new RssFeed();
        rssFeed.setIdRss(1L);
        rssFeed.setUrl("url");
        rssFeed.setCurrentPosition(true);
    }

    @Test
    public void testList() throws Exception {
        assertThat(this.rssFeedService).isNotNull();
        mockMvc.perform(get("/rssFeed/getAll"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetExample() throws Exception {
        List<RssFeed> feeds = new ArrayList<>();
        rssFeed = new RssFeed();
        rssFeed.setIdRss(1L);
        rssFeed.setUrl("url");
        rssFeed.setCurrentPosition(true);
        feeds.add(rssFeed);

        Mockito.when(rssFeedService.getAll()).thenReturn(feeds);
        mockMvc.perform(get("/rssFeed/getAll")).andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].url", Matchers.equalTo("url")));
    }
}