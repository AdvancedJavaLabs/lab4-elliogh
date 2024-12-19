package org.itmo.lab4.config;

import org.apache.hadoop.conf.Configuration;

public class JobConfig {

    public static Configuration getConfiguration(int dataBlockSizeBytes) {
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.input.fileinputformat.split.maxsize", Integer.toString(dataBlockSizeBytes));
        return configuration;
    };
}
