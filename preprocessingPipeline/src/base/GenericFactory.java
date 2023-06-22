package base;

//@{}
public abstract class GenericFactory<T> {
	
	public abstract T create(String[] data);
}
