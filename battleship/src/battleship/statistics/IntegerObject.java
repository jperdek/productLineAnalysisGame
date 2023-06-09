package battleship.statistics;

//@{"statistics": "true"}
public class IntegerObject implements VariableObject {
	int variable = 0;
	
	public IntegerObject(int initialValue) {
		this.variable = initialValue;
	}
	
	public void increaseValue() {
		this.variable++;
	}
	
	public void decreaseValue() {
		this.variable--;
	}
	
	public void increaseAbout(int amountToIncrease) {
		this.variable += amountToIncrease;
	}
	
	public void decreaseAbout(int amountToDecrease) {
		this.variable -= amountToDecrease;
	}
	
	public int getValue() {
		return this.variable;
	}
}
