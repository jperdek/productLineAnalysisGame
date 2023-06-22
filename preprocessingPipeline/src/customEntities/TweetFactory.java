package customEntities;

import base.GenericFactory;

//@{"dataEntity": "tweet"}
public class TweetFactory extends GenericFactory<Tweet> {

	@Override
	public Tweet create(String[] data) {
		return new Tweet(data);
	}

}
