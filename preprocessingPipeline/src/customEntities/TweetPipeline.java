package customEntities;
import java.util.List;

import base.DataCarrier;
import base.PipelineCommand;
import base.PipelineHandlers;


//@{"dataEntity": "tweet"}
public class TweetPipeline extends PipelineHandlers implements PipelineCommand {

	private List<Tweet> tweets;
	
	public TweetPipeline(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	
	@Override
	public void launchPipeline() {
		this.cleanData();
	}
	
	@Override
	public void cleanData() {
		String output;
		Tweet processedTweet;
		System.out.println( this.tweets.size());
		for(int index = 0; index < this.tweets.size(); index++) {
			processedTweet = this.tweets.get(index);
			output = this.processText(processedTweet.getContent());
			processedTweet.setContent(output);
		}
		
		for(int index = 0; index < this.tweets.size(); index++) {
			processedTweet = this.tweets.get(index);
			System.out.println(processedTweet.getContent());	
		}
	}

	@Override
	public void fillMissingValues() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void encodeCategorical() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DataCarrier transformData(DataCarrier data) {
		// TODO Auto-generated method stub
		return null;
	}
}
