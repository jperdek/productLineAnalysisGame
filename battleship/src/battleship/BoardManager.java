package battleship;

import java.util.Map;
import java.util.ArrayList;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;

import java.util.HashMap;

//@{}
public class BoardManager {
	private Map<String, Board> boards;
	
	public BoardManager() {
		boards = new  HashMap<String, Board>();
	}
	
	public void registerPlayerComputer(String boardId, Board playerBoard, 
			String computerId, Board computerBoard) {
		this.registerBoard(computerId, computerBoard);
		this.registerBoard(boardId, playerBoard);
	}
	
	public void registerBoard(String boardId, Board board) {
		this.boards.put(boardId, board);
	}
	
	public ArrayList<Board> getOppositeBoards(String playerId) {
		Board notInclude = this.boards.get(playerId);
		ArrayList<Board> boardsList = new ArrayList<Board>(this.boards.values());
		boardsList.remove(notInclude);
		return boardsList;
	}
	
	public Board getBoard(String playerId) {
		return this.boards.get(playerId);
	}
}
