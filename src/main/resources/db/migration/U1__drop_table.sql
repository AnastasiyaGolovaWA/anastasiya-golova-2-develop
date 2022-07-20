drop table object_xslt;

ALTER TABLE rss_news
    DROP CONSTRAINT rss_news_id_news_fkey,
    ADD CONSTRAINT id_news FOREIGN KEY (id_news)
        REFERENCES news (id_news) ON DELETE CASCADE;

ALTER TABLE rss_news
    DROP CONSTRAINT rss_news_id_rss_fkey,
    ADD CONSTRAINT id_rss FOREIGN KEY (id_rss)
        REFERENCES rss_feed (id_rss) ON DELETE CASCADE;