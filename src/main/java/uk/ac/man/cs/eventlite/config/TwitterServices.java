package uk.ac.man.cs.eventlite.config;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TwitterServices{
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
    
    public List<Date> getTimeLineDates() throws TwitterException {
        Twitter twitter = getTwitterInstance();
        return twitter.getHomeTimeline().stream().map(Status::getCreatedAt).collect(Collectors.toList());
    }
    
    public List<Long> getTimeLineIDs() throws TwitterException {
        Twitter twitter = getTwitterInstance();
        return twitter.getHomeTimeline().stream().map(Status::getId).collect(Collectors.toList());
    }
    
    public List<User> getTimeLineScreeNames() throws TwitterException {
        Twitter twitter = getTwitterInstance();
        return twitter.getHomeTimeline().stream().map(Status::getUser).collect(Collectors.toList());
    }
    
    public ResponseList<Status> getTimeLineStatus() throws TwitterException {
        Twitter twitter = getTwitterInstance();
        return twitter.getUserTimeline(new Paging(1,5));
    }
    
    
}
