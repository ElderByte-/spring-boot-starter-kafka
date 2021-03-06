package com.elderbyte.kafka.streams.builder.dsl;

import com.elderbyte.kafka.streams.builder.KafkaStreamsContextBuilder;

public class ElStreamBase<K,V> {

    /***************************************************************************
     *                                                                         *
     * Fields                                                                  *
     *                                                                         *
     **************************************************************************/

    private final ElStreamsBuilder<K,V> elBuilder;

    /***************************************************************************
     *                                                                         *
     * Constructor                                                             *
     *                                                                         *
     **************************************************************************/

    /**
     * Creates a new ElKtable
     */
    public ElStreamBase(ElStreamsBuilder<K,V> elBuilder) {
        this.elBuilder = elBuilder;
    }

    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    public ElStreamsBuilder<K,V> builder(){
        return elBuilder;
    }

    public KafkaStreamsContextBuilder context() {
        return elBuilder.context();
    }

    public KStreamSerde<K, V> serde() {
        return elBuilder.serde();
    }

    /***************************************************************************
     *                                                                         *
     * Private methods                                                         *
     *                                                                         *
     **************************************************************************/

}
