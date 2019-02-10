package de.tuberlin.sese.swtpp.gameserver.model.deathstacks;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.tuberlin.sese.swtpp.gameserver.model.Game;
import de.tuberlin.sese.swtpp.gameserver.model.Move;
import de.tuberlin.sese.swtpp.gameserver.model.Player;

public class DeathStacksGame extends Game {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3053592017994489843L;
	/************************
	 * member
	 ***********************/
	
	// just for better comprehensibility of the code: assign blue and red player
	private Player bluePlayer;
	private Player redPlayer;

	// TODO: internal representation of the game state 
	/**
	 * represent the current position of the pieces
	 */
	private final Board board = new Board() ;
	private String lastBoardstate = "";
	private int counter = 0;
	

	
	/************************
	 * constructors
	 ***********************/
	
	public DeathStacksGame() throws Exception{
	 super();
	 this.lastBoardstate = this.board.toString();
	}
	
	public String getType() {
		return "deathstacks";
	}
	
	/*******************************************
	 * Game class functions already implemented
	 ******************************************/
	
	@Override
	public boolean addPlayer(Player player) {
		if (!started) {
			players.add(player);
			
			if (players.size() == 2) {
				started = true;
				this.redPlayer = players.get(0);
				this.bluePlayer = players.get(1);
				nextPlayer = this.redPlayer;
			}
			return true;
		}
		
		return false;
	}

	@Override
	public String getStatus() {
		if (error) return "Error";
		if (!started) return "Wait";
		if (!finished) return "Started";
		if (surrendered) return "Surrendered";
		if (draw) return "Draw";
		
		return "Finished";
	}
	
	@Override
	public String gameInfo() {
		String gameInfo = "";
		
		if(started) {
			if(blueGaveUp()) gameInfo = "blue gave up";
			else if(redGaveUp()) gameInfo = "red gave up";
			else if(didRedDraw() && !didBlueDraw()) gameInfo = "red called draw";
			else if(!didRedDraw() && didBlueDraw()) gameInfo = "blue called draw";
			else if(draw) gameInfo = "draw game";
			else if(finished)  gameInfo = bluePlayer.isWinner()? "blue won" : "red won";
		}
			
		return gameInfo;
	}	
	
	@Override
	public String nextPlayerString() {
		return isRedNext()? "r" : "b";
	}

	@Override
	public int getMinPlayers() {
		return 2;
	}

	@Override
	public int getMaxPlayers() {
		return 2;
	}
	
	@Override
	public boolean callDraw(Player player) {
		
		// save to status: player wants to call draw 
		if (this.started && ! this.finished) {
			player.requestDraw();
		} else {
			return false; 
		}
	
		// if both agreed on draw:
		// game is over
		if(players.stream().allMatch(p -> p.requestedDraw())) {
			this.finished = true;
			this.draw = true;
			redPlayer.finishGame();
			bluePlayer.finishGame();
		}	
		return true;
	}
	
	@Override
	public boolean giveUp(Player player) {
		if (started && !finished) {
			if (this.redPlayer == player) { 
				redPlayer.surrender();
				bluePlayer.setWinner();
			}
			if (this.bluePlayer == player) {
				bluePlayer.surrender();
				redPlayer.setWinner();
			}
			finished = true;
			surrendered = true;
			redPlayer.finishGame();
			bluePlayer.finishGame();
			
			return true;
		}
		
		return false;
	}

	/*******************************************
	 * Helpful stuff
	 ******************************************/
	
	/**
	 * 
	 * @return True if it's white player's turn
	 */
	public boolean isRedNext() {
		return nextPlayer == redPlayer;
	}
	
	/**
	 * Finish game after regular move (save winner, move game to history etc.)
	 * 
	 * @param player
	 * @return
	 */
	public boolean finish(Player player) {
		// public for tests
		if (started && !finished) {
			player.setWinner();
			finished = true;
			redPlayer.finishGame();
			bluePlayer.finishGame();
			
			return true;
		}
		return false;
	}

	public boolean didRedDraw() {
		return redPlayer.requestedDraw();
	}

	public boolean didBlueDraw() {
		return bluePlayer.requestedDraw();
	}

	public boolean redGaveUp() {
		return redPlayer.surrendered();
	}

	public boolean blueGaveUp() {
		return bluePlayer.surrendered();
	}

	/*******************************************
	 * !!!!!!!!! To be implemented !!!!!!!!!!!!
	 ******************************************/

	@Override
	public void setBoard(String state) {
		this.board.setBoard(state);
	}
	
	@Override
	public String getBoard() {
		return this.board.toString() ;
	}
	
	@Override
	public boolean tryMove(String moveString, Player player) {	
		//moveString gültig? + spiel gestartet ist?
		if(!(verifyMove(moveString) && getStatus().equals("Started")))
			return false;
			String[] move = moveString.split("-");
			Stapel fromStack = board.getStapel(move[0]);
				//too Tall Regel? + stack owner?
				if(fromStack.toString().charAt(0)==nextPlayerString().charAt(0)&&((fromStack.getNumbofPieces()>=4 && fromStack.getNumbofPieces()-Integer.parseInt(move[1])<=4)  ||!board.getTallStack(nextPlayerString().charAt(0)))) {
					Zug zug = new Zug(Integer.parseInt(move[1]),new Position(move[0]));
						//move reicht um dest zu erreichen?
						if(zug.getPossibleDest().contains(move[2])){
						System.out.println("falls zueg möglich");
						//Züg ausfuhren & setLaststate & add to statistics
						this.lastBoardstate = this.board.toString();
						try {
						this.board.movePieces(move[0],move[2],Integer.parseInt(move[1]));
						}catch(IllegalArgumentException e) {return false;}
						super.getHistory().add(new Move(moveString,this.lastBoardstate,player));
						//winner?
						if(board.isWinner(nextPlayerString().charAt(0))) {
						setWinner(nextPlayerString());
						finish(player);
						
						}
						//draw?
						else if(this.isRepeatingState()) {
						super.draw = true;
						finish(player);
						
						}
						//set nextplayer
						setNextPlayer((isRedNext())?bluePlayer:redPlayer);
						return true;
						}
				}	
					
		return false;
	}


	/**check if the 
	 * @return
	 */
	private boolean isRepeatingState() {
	return super.getHistory().stream()
				.map(m->m.getBoard())
				.collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
				.entrySet().stream().filter(e -> e.getValue() == 2)
				.anyMatch(e -> this.board.toString().equals(e.getKey()));
	}
	/**checks the moveString's syntax
	 * @param moveString
	 * @return true if moveString sytax is correct, false otherwise
	 */
	private boolean verifyMove(String moveString) {
		String re = "[a-f][1-6]-+\\d-[a-f][1-6]";
		if(!moveString.matches(re)) {
			return false;
		}
		return true;
	}
	/**
	 * @param p
	 */
	private void setWinner(String p) {
		if(p=="r")
			this.redPlayer.setWinner();
		else 
			this.bluePlayer.setWinner();

	}

}
