package battleship.statistics;
import battleship.Player;

public aspect StatisticConnnections {
	declare parents: Player implements VariableObject; // define connection to make statistics for this object
}
