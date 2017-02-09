package edu.uprm.cse.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.SyslogAppender;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by ubuntu on 1/26/17.
 */
public class SampleFileReader {
    public static void main(String[] args) throws Exception{
        if (args.length < 1){
            System.err.println("Usage: SampleFileReader <hdfs file name>");
            throw new IllegalArgumentException("Argument missing.");
        }

        // Start the Logger
        Logger logger = LogManager.getRootLogger();
        logger.trace("Starting application.");
        String fileName = args[0];
        logger.trace("File to open: " + fileName);
        URI fileUri = URI.create(fileName);
        // get the configuration variable
        Configuration conf = new Configuration();
        // get a handle the underlying hadoop file system
        FileSystem hdfs = FileSystem.get(fileUri, conf);
        InputStream dataIn = null;

        try {
            long bytesToRead = 0L;
            // Build path to file
            Path path = new Path(fileUri);
            bytesToRead =  hdfs.getFileStatus(path).getLen();
            logger.trace("Total bytes to read: " + bytesToRead);
            // open the input stream on the file
            dataIn = hdfs.open(path);
            IOUtils.copyBytes(dataIn, System.out, bytesToRead, false);
        }
        catch(IOException e){
            System.err.println("Fatal I/O Error.");
            e.printStackTrace(System.err);
        }
        finally {
            IOUtils.closeStream(dataIn);
        }
        logger.trace("Done Reading File.");
        System.exit(0);
    }
}
