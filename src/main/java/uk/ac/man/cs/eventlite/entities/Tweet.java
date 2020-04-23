package uk.ac.man.cs.eventlite.entities;

import twitter4j.Status;
import twitter4j.URLEntity;
import java.util.Date;

public class Tweet 
{
	private Status status;
	private String url;

	public void setURL(String givenUrl)
	{
		url = givenUrl;
	}
	
	public void setStatus(Status givenStatus)
	{
		status = givenStatus;
	}
	
	public Status getStatus()
	{
		return status;
	}
	
	public String getUrl()
	{
		return url;
	}
}
