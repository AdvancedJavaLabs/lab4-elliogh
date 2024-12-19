package org.itmo.lab4.jobs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.itmo.lab4.analyze.AnalyzeMapper;
import org.itmo.lab4.analyze.AnalyzeReducer;
import org.itmo.lab4.analyze.SalesData;

public class AnalyzeJob extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        String inputDir = args[0];
        String outputDir = args[1];
        int reducerCount = Integer.parseInt(args[2]);

        Configuration configuration = getConf();

        Job analysisJob = Job.getInstance(configuration, "analysis");
        setJobOptions(analysisJob, reducerCount);

        FileInputFormat.addInputPath(analysisJob, new Path(inputDir));
        FileOutputFormat.setOutputPath(analysisJob, new Path(outputDir));

        boolean success = analysisJob.waitForCompletion(true);

        return success? 0: 1;
    }

    private void setJobOptions(Job job, int reducerCount) {
        job.setNumReduceTasks(reducerCount);

        job.setJarByClass(AnalyzeJob.class);

        job.setMapperClass(AnalyzeMapper.class);
        job.setReducerClass(AnalyzeReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(SalesData.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
    }
}
