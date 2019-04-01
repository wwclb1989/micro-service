package cn.loveless.kafka.config;

import org.springframework.beans.factory.annotation.Value;

public class KafkaConsumerConfig {

    @Value("${kafka.consumer.servers}")
    private String servers;

    @Value("${kafka.consumer.enable.auto.commit")
    private boolean enableAutoCommit;

    @Value("${kafka.consumer.session.timeout")
    private String sessionTimeout;

    private String autoCommitInterval;

    private String groupId;

    private String autoOffsetReset;

    private int concurrency;
}
