package base;

import java.util.List;
import java.util.Arrays;


//@{}
public class GenericEntity {

	public GenericEntity() {
		
	}
	
	public boolean parseBoolean(String valueToParse) { return true; }
	
	public List<String> parseArray(String array) { return Arrays.asList(array.split(",")); }
	
}
