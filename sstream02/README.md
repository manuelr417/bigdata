# Purpose

This folder has a program that illustrates how to read tweets from the Twitter Stream API.
You need to install the twitter library in your Python virtual env. After you activate 
the environment type the following command:
<p>
<code> pip install twitter </code>

That will install the necessary libraries. 

## Developer account
You will need to signup for Twitter, and then, use that account to register as Twitter Developer. 
You will need to get 4 parameter values from your  developer account: 

ACCESS_TOKEN  
ACCESS_SECRET  
CONSUMER_KEY   
CONSUMER_SECRET  

## Running the program
You will need to put the 4 twitter parameters into a file named <code>credentials.json</code>. 
I have included a sample one, named <code>sample-credentials.json</code>. Rename and modify it.

Next, to runt the program simply type:

<code> python readtweets</code>

You will start to see the tweets on the console. 

You can use this program as a start point to create programs that take the tweets and put into a file, 
 HDFS, kafka, hive, MySQL, etc. 