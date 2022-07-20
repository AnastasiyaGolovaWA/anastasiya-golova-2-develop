package com.manager.rss.test;

import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Objects;

public class NewsElasticsearchContainer extends ElasticsearchContainer {
    private static final long MEMORY_IN_BYTES = 32L * 1024L * 1024L * 20L;//640 MB (in binary)
    private static final long MEMORY_SWAP_IN_BYTES = 48L * 1024L * 1024L * 20L;//960 MB (in binary)
    private static final String ELASTIC_SEARCH_DOCKER = "docker.elastic.co/elasticsearch/elasticsearch:7.9.2-amd64";

    private static final String CLUSTER_NAME = "cluster.name";

    private static final String ELASTIC_SEARCH = "elasticsearch";

    public NewsElasticsearchContainer() {
        super(DockerImageName
                .parse("docker.elastic.co/elasticsearch/elasticsearch-oss")
                .withTag("7.9.3"));
        this
        .withEnv("ES_JAVA_OPTS", "-Xms512m -Xmx512m")
                .withCreateContainerCmdModifier(
                        cmd -> Objects.requireNonNull(cmd.getHostConfig())
                                .withMemory(MEMORY_IN_BYTES)
                                .withMemorySwap(MEMORY_SWAP_IN_BYTES)
                );
        this.addEnv(CLUSTER_NAME, ELASTIC_SEARCH);
    }
}
