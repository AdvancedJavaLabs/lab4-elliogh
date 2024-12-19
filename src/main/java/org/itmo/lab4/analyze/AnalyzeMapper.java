package org.itmo.lab4.analyze;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.itmo.lab4.util.Utils;

import java.io.IOException;

public class AnalyzeMapper extends Mapper<Object, Text, Text, SalesData> {

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(",");

        if (isValidRecord(fields)) {
            String category = fields[2];
            double price = Utils.parseDouble(fields[3]);
            int quantity = Utils.parseInt(fields[4]);

            context.write(new Text(category),
                    new SalesData(price * quantity, quantity));
        }
    }

    private boolean isValidRecord(String[] fields) {
        return !fields[0].equals("transaction_id") && fields.length == 5;
    }


}
