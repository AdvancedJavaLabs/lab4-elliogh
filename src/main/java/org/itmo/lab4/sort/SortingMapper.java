package org.itmo.lab4.sort;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.itmo.lab4.util.Utils;

import java.io.IOException;

public class SortingMapper extends Mapper<Object, Text, DoubleWritable, CompositeData> {
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split("\t");

        if (fields.length == 3) {
            String category = fields[0];
            double revenue = Utils.parseDouble(fields[1]);
            int quantity = Utils.parseInt(fields[2]);

            context.write(new DoubleWritable(revenue),
                    new CompositeData(category, quantity));
        }
    }
}
