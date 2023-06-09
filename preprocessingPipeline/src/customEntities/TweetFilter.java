package customEntities;

import base.DataFilter;

//@{"dataEntity": "tweet"}
public class TweetFilter extends DataFilter<Tweet> {

	public boolean passedFiltering(Tweet tweet) {
		if (this.filterBoolean(tweet.isUserVerified())) {
			return false;
		}
		// Another conditions can be added
		return true;
	}
	
	public boolean filterBoolean(boolean value) {
		return false;
	}
}
