package org.itmo.lab4.jobs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

public class JobsLauncher {

    public void startJobs(Configuration configuration,
                                  String inputDir,
                                  String outputDir,
                                  int reducerCount,
                                  String intermediateResultDir) throws Exception {
        long start = System.currentTimeMillis();

        startAnalyzeJob(configuration, inputDir, reducerCount, intermediateResultDir);
        startSortJob(configuration, outputDir, reducerCount, intermediateResultDir);

        long end = System.currentTimeMillis();
        System.out.println("Finished in " + (end - start) + " milliseconds.");
    }

    private void startAnalyzeJob(Configuration configuration,
                                        String inputDir,
                                        int reducerCount,
                                        String intermediateResultDir) throws Exception {
        String[] analysisArgs = new String[]{inputDir, intermediateResultDir, String.valueOf(reducerCount)};

        int exitCode = ToolRunner.run(configuration, new AnalyzeJob(), analysisArgs);

        exitIfError(exitCode);
    }

    private void startSortJob(Configuration configuration,
                                     String outputDir,
                                     int reducerCount,
                                     String intermediateResultDir) throws Exception {
        String[] sortingArgs = new String[]{intermediateResultDir, outputDir, String.valueOf(reducerCount)};

        int exitCode = ToolRunner.run(configuration, new SortJob(), sortingArgs);

        exitIfError(exitCode);
    }

    private void exitIfError(int exitCode) {
        if (exitCode != 0) {
            System.exit(1);
        }
    }
}
