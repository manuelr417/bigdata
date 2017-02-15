package edu.uprm.cse.bigdata;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by ubuntu on 2/6/17.
 */
public class SchoolByStateReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        //super.reduce(key, values, context);

        // key is the abreviation of a state
        // values is a list of 1s, one for each school found for the state

        // setup a counter
        int count = 0;
        // iterator over list of 1s, to count them (no size() or length() method available)
        for (IntWritable value : values ){
            count++;
        }
        // emit key-pair: key, count
        // key is the abreviation for the state
        // count is the number of schools in the state
        //Logger logger = LogManager.getRootLogger();
        //logger.trace("Red: " + key.toString());

        context.write(key, new IntWritable(count));
    }
}
