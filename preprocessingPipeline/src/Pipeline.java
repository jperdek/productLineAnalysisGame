import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.opencsv.exceptions.CsvException;

import base.RecordLoader;
import configurationManagement.ConfigurationLoader;
import customEntities.Tweet;
import customEntities.TweetFactory;
import customEntities.TweetFilter;
import customEntities.TweetPipeline;


//@{}
public class Pipeline {

	public static final String FILE_NAME = "E://aosd/lightweigth/preprocessingPipeline/sources/datasets/covid19_tweets2.txt";
	
	public Pipeline() throws FileNotFoundException {
		RecordLoader<Tweet> rl = new RecordLoader<Tweet>();
		List<Tweet> tweets = rl.readData(Pipeline.FILE_NAME, "\t", new TweetFactory(), new TweetFilter());
		TweetPipeline tweetPipeline = new TweetPipeline(tweets);
		tweetPipeline.launchPipeline();
	}
	
	public static void main(String[] args) throws IOException, CsvException {
		ConfigurationLoader configurationLoader = new ConfigurationLoader("resources/preprocessingPipelineConfig.json");
		Pipeline pipeline = new Pipeline();
	}
}

