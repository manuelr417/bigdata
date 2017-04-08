from pyspark import SparkContext, SparkConf
from pyspark.streaming import StreamingContext

# Create a local StreamingContext attached to yarn
# It will listen to a TCP stream on port 9999
#
# Producer with TCP Stream
# The TCP stream can be run with netcat by running
# nc -lk <port>
# where <port> is the port number to use
# For example to run on local host on port 9999 run:
#
# nc -lk 9999
#
# Running the stream with spark-submit
# You run the stream with the following command:
#
# spark-submit ./socketlistener01.py --master yarn <host> <port>
# where <host> is the host name of the machine where the producer is
# is running, and <port> is the port number where the messages are
# being sent. For example, to run from local host on port 9999:
#
# spark-submit ./socketlistener01.py --master yarn localhost 9999
#
# ERRORS
# if the nc application is not running, the streaming app will
# throw connection errors
#
import sys

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: socketlistener01.py <hostname> <port>", file=sys.stderr)
        exit(-1)

    host = sys.argv[1]
    port = sys.argv[2]

    # Setup spark configuration
    conf = SparkConf().setAppName("SocketWordCountYarn")
    # get the spark context, by default it will use YARN since we are calling
    # spark-submit with the option --master yarn
    sc = SparkContext(conf=conf)

    # create a Spark Streaming context, with batchs of data created every 4 secs
    # (if data is available)
    ssc = StreamingContext(sc, 4)
    # Use the context to create a streaming  client on a TCP socket on a port on a given host
    # lines is DStream, in particular an input DStream
    lines = ssc.socketTextStream("localhost", 9999)

    # Now take each line, an break it into a list of individual words, assume words are
    # separated by blank space.
    # Variable words is a DStream representing this list of words
    words = lines.flatMap(lambda line: line.split(" "))

    # Next, we will count the words, using a MapReduce style of computation.
    # First, for each word we will emit a pair (word, 1), indicating that an occurence
    # of the work was found.
    # Variable pairs is a DStream representing these pairs
    pairs = words.map(lambda word: (word, 1))
    # Finally, we use a reduce by key operation to aggregate the counts of words.
    # words is DStream that represents these counts
    wordCounts = pairs.reduceByKey(lambda x, y: x + y)

    # We can now print the values in wordCounts DStream by accessing the underlying RDD.
    # Print the first ten elements of each RDD generated in this DStream to the console
    wordCounts.pprint()

    # Up to this point we have just specified a set of DStreams and the computations to be
    # performed on them. Nothing happen until we start the stream. In doing, so the
    # DStream are continously computing, each time a batch is formed (in our case, every 4 secs).

    # These two commands start the computation and then wait for it to terminate (if ever).
    # Start
    ssc.start()
    # Wait until end
    ssc.awaitTermination()

