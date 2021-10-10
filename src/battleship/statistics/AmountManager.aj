package battleship.statistics;

import java.util.HashMap;

public abstract aspect AmountManager { 
	public pointcut increaseGivenAmount();
	
	after(): increaseGivenAmount() {
		System.out.println("<Security aspect: check/>");
	}
}
