package edu.uprm.cse.bigdata;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by ubuntu on 2/6/17.
 */
public class SchoolByStateMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //super.map(key, value, context);

        //StringTokenizer strTok = new StringTokenizer(value.toString(), ",");
        String cols[] = value.toString().split(",");
        // get the state name, located in column
        String state = cols[3];
        //System.err.println("col: " + state);

        // now emit the following key-pair: state, 1
        // state is the abreviation of the state
        // 1 is a counter just telling that for the given state, we found a record
        //Logger logger = LogManager.getRootLogger();
        //logger.trace("Map: " + state);
        context.write(new Text(state), new IntWritable(1));
    }
}
