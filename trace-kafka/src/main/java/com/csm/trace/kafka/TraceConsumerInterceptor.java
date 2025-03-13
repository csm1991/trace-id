package com.csm.trace.kafka;

import com.csm.trace.core.TraceIdContext;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import java.util.Map;

public class TraceConsumerInterceptor implements ConsumerInterceptor<String, String> {

    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
        records.forEach(record -> {
            record.headers().headers(TraceIdContext.TRACE_HEADER_KEY).forEach(header -> {
                String traceId = new String(header.value());
                TraceIdContext.setTraceId(traceId);
            });
        });
        return records;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> configs) {
    }
}