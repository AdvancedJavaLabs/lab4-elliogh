package org.itmo.lab4.jobs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.itmo.lab4.sort.CompositeData;
import org.itmo.lab4.sort.SortComparator;
import org.itmo.lab4.sort.SortReducer;
import org.itmo.lab4.sort.SortingMapper;

public class SortJob extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        String inputDir = args[0];
        String outputDir = args[1];
        int reducerCount = Integer.parseInt(args[2]);

        Configuration configuration = getConf();

        Job sortingJob = Job.getInstance(configuration, "sorting");
        setJobOptions(sortingJob, reducerCount);

        FileInputFormat.addInputPath(sortingJob, new Path(inputDir));
        FileOutputFormat.setOutputPath(sortingJob, new Path(outputDir));

        boolean success = sortingJob.waitForCompletion(true);

        return success? 0: 1;
    }

    private void setJobOptions(Job job, int reducerCount) {
        job.setNumReduceTasks(reducerCount);

        job.setJarByClass(SortJob.class);

        job.setSortComparatorClass(SortComparator.class);

        job.setMapperClass(SortingMapper.class);
        job.setReducerClass(SortReducer.class);

        job.setMapOutputKeyClass(DoubleWritable.class);
        job.setMapOutputValueClass(CompositeData.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
    }
}
