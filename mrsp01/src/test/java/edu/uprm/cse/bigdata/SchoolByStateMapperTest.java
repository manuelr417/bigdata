package edu.uprm.cse.bigdata;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;


/**
 * Created by ubuntu on 2/9/17.
 */
public class SchoolByStateMapperTest {
    @Test
    public void processMapRecord() throws IOException, InterruptedException{
        Text row = new Text("100654,Alabama A & M University,Normal,AL,35762,1,5,\"Dr. Andrew Hugine, Jr.\",President,25\n" +
                "63725000,2563725030,636001109,100200,1,www.aamu.edu/,www.aamu.edu/admissions/pages/defaul\n" +
                "t.aspx,www.aamu.edu/Admissions/fincialaid/Pages/default.aspx,www.aamu.edu/Admissions/appl\n" +
                "y/Pages/default.aspx,galileo.aamu.edu/netpricecalculator/npcalc.htm,1,1,1,9,1,1,12,1,1,2,\n" +
                "2,2,12,1,A ,-2,-2,-2,1,1,1,1,1,AAMU,2,18,13,18,9,4,14,16,1,3,26620,1,290,-2,2, ,-2,1089,M\n" +
                "adison County,105,-86.568502,34.783368");
        new MapDriver<LongWritable, Text, Text,IntWritable>()
                .withMapper(new SchoolByStateMapper())
                .withInput(new LongWritable(0), row)
                .withOutput(new Text("AL"), new IntWritable(1))
                .runTest();
    }

    @Test
    public void processReducedRecord() throws IOException, InterruptedException {
        Text key = new Text("AL");

        new ReduceDriver<Text, IntWritable, Text, IntWritable>()
                .withReducer(new SchoolByStateReducer())
                .withInput(key, Arrays.asList(new IntWritable(1), new IntWritable(1), new IntWritable(1),
                        new IntWritable(1)))
                .withOutput(key, new IntWritable(4))
                .runTest();
    }
}
