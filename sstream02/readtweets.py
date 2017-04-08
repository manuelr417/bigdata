# Import the necessary package to process data in JSON format
try:
    import json
except ImportError:
    import simplejson as json

# Import the necessary methods from "twitter" library
from twitter import Twitter, OAuth, TwitterHTTPError, TwitterStream

def read_credentials():
    file_name = "credentials.json"
    try:
        with open(file_name) as data_file:
            return json.load(data_file)
    except:
        print ("Cannot load credentials.json")
        return None



def read_tweets(access_token, access_secret, consumer_key, consumer_secret):

    oauth = OAuth(access_token, access_secret, consumer_key, consumer_secret)

    # Initiate the connection to Twitter Streaming API
    twitter_stream = TwitterStream(auth=oauth)

    # Get a sample of the public data following through Twitter
    iterator = twitter_stream.statuses.sample()

    # Print each tweet in the stream to the screen
    # Here we set it to stop after getting 1000 tweets.
    # You don't have to set it to stop, but can continue running
    # the Twitter API to collect data for days or even longer.
    tweet_count = 1000
    for tweet in iterator:
        tweet_count -= 1
        # Twitter Python Tool wraps the data returned by Twitter
        # as a TwitterDictResponse object.
        try:
            # print screen_name and name
            print("TWEET: ", tweet['user']['screen_name'])
            # The command below will do pretty printing for JSON data, try it out
            print("TWEET JSON: ", json.dumps(tweet, indent=4))
            # This next command, prints the tweet as a string
            print ("TWEETS STRING", str(tweet))
        except:
            pass

        if tweet_count <= 0:
            print("Done")
            break

if __name__ == "__main__":
    print("Stating to read tweets")
    credentials = read_credentials()
    read_tweets(credentials['ACCESS_TOKEN'], credentials['ACCESS_SECRET'],
                credentials['CONSUMER_KEY'], credentials['CONSUMER_SECRET'])
