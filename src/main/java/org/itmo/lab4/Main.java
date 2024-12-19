package org.itmo.lab4;

import org.apache.hadoop.conf.Configuration;
import org.itmo.lab4.config.JobConfig;
import org.itmo.lab4.jobs.JobsLauncher;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 4) {
            System.exit(-1);
        }

        String inputDir = args[0];
        String outputDir = args[1];
        int reducerCount = Integer.parseInt(args[2]);
        int dataBlockSizeBytes = Integer.parseInt(args[3]) * 1024;
        String intermediateResultDir = outputDir + "-intermediate";

        Configuration configuration = JobConfig.getConfiguration(dataBlockSizeBytes);

        JobsLauncher jobsLauncher = new JobsLauncher();
        jobsLauncher.startJobs(configuration, inputDir, outputDir, reducerCount, intermediateResultDir);
    }
}
