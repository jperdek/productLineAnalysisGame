package difficulty;

import computerOpponent.ComputerInstantiator;

//@{"computerOpponent": "true"}
public aspect PlayersPrecedence {
	declare precedence: DifficultyManagement, ComputerInstantiator;
}
