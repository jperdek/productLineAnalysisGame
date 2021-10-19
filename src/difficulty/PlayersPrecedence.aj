package difficulty;

import computerOpponent.ComputerInstantiator;

public aspect PlayersPrecedence {
	declare precedence: DifficultyManagement, ComputerInstantiator;
}
