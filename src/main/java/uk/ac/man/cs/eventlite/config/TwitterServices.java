package uk.ac.man.cs.eventlite.config;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;
import java.util.stream.Collectors;

class TwitterServices{
    public Twitter getTwitterInstance(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("d39duHx0v0RGayhYMuka2ioLo")
                .setOAuthConsumerSecret("K0FtLKNv3rf9DsyJZSUOfrMzk82BEod2nEJcRV4AcaOeBUHz2I")
                .setOAuthAccessToken("1252169836597215233-UUsiUNtugYTmHR2U9lCAv2LQ3li0Bk")
                .setOAuthAccessTokenSecret("GyDcIaxeXGvvVXnM4up2ovsUi8jrvbISfVXvAmqiGDIXm");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;
    }

    public String createTweet(String tweet) throws TwitterException {
        Twitter twitter = getTwitterInstance();
        Status status = twitter.updateStatus(tweet);
        return status.getText();
    }

    public List<String> getTimeLine() throws TwitterException {
        Twitter twitter = getTwitterInstance();
        return twitter.getHomeTimeline().stream()
                .map(Status::getText)
                .collect(Collectors.toList());
    }
}
