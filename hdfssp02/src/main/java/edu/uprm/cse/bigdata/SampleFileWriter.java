package edu.uprm.cse.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URI;

/**
 * Created by ubuntu on 1/26/17.
 */
public class SampleFileWriter {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: SampleFileWriter <local in file> <hdfs out file>");
            throw new IllegalArgumentException("Argument missing.");
        }
        // Start the Logger
        Logger logger = LogManager.getRootLogger();
        logger.trace("Starting application.");
        String inputFileName = args[0];
        logger.trace("Local Source File: " + inputFileName);
        String outputFileName = args[1];
        logger.trace("Output HDFS File: " + outputFileName);
        URI fileUri = URI.create(outputFileName);
        // get the configuration variable
        Configuration conf = new Configuration();
        // get a handle to the underlying hadoop file system
        FileSystem hdfs = FileSystem.get(fileUri, conf);
        InputStream dataIn = null;
        OutputStream dataOut = null;
        long bytesToSend = 0;

        try {
            logger.trace("Opening input file.");
            File fileIn = new File(inputFileName);
            bytesToSend = fileIn.length();
            logger.trace("Bytes in file + " + inputFileName + " : " + bytesToSend);
            dataIn = new BufferedInputStream(new FileInputStream(fileIn));

        }
        catch (FileNotFoundException e1){
            logger.trace("File not found or cannot be opened: " + inputFileName);
            System.exit(1);
        }
        try {
            logger.trace("Opening output file.");
            dataOut = hdfs.create(new Path(outputFileName), new Progressable() {
                public void progress() {
                    System.out.print(".");
                }
            });
            logger.trace("Copying data.");
            IOUtils.copyBytes(dataIn, dataOut, bytesToSend, false);
        }
        catch (Exception e2){
            e2.printStackTrace(System.err);
            logger.trace("Error while copying data.");
        }
        finally {
            IOUtils.closeStream(dataOut);
            dataIn.close();
        }
        logger.trace("Done copying data.");

    }
}
