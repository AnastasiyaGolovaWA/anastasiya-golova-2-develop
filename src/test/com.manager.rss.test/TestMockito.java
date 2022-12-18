package com.manager.rss.test;

import com.manager.rss.controllers.RssFeedController;
import com.manager.rss.entity.RssFeed;
import com.manager.rss.service.RssFeedService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RssFeedController.class)
public class TestMockito {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private RssFeedService rssFeedService;

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
        mockMvc.perform(MockMvcRequestBuilders.get("/rssFeed/getAll"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}