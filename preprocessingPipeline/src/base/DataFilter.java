package base;

//@{}
public abstract class DataFilter<T> {
	
	public boolean passedFiltering(T object) {
		if (this.filterBoolean(false)) {
			return false;
		}
		if (this.filterString("")) {
			return false;
		}
		return true;
	}
	
	public boolean filterBoolean(boolean value) {
		return false;
	}
	
	public boolean filterString(String value) {
		return false;
	}
}
