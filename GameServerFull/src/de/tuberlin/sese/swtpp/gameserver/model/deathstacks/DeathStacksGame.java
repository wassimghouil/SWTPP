package de.tuberlin.sese.swtpp.gameserver.model.deathstacks;

import de.tuberlin.sese.swtpp.gameserver.model.Game;
import de.tuberlin.sese.swtpp.gameserver.model.Player;
import de.tuberlin.sese.swtpp.gameserver.model.Position;
// TODO: more imports allowed

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
	private String currentBoard = "";
	
	/************************
	 * constructors
	 ***********************/
	
	public DeathStacksGame() throws Exception{
		super();
		
		// TODO: Initialization, if necessary
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
	private void setStapel(String pos) {
		Position p = new Position(pos);
		String reihe = getBoard().split("/")[p.getY()];
		//this.currentBoard = Arrays.as
		//TODO not complete
		
	}
	/**
	 * @param Position dieser stapel
	 * @return a string that represent a Stapel
	 */
	private String getStapel(String pos) {
		Position position = new Position(pos);
		String reihe = getBoard().split("/")[position.getY()];
		return reihe.split(",")[position.getX()];
	}
	@Override
	public void setBoard(String state) {
		this.currentBoard = state;
	}
	
	@Override
	public String getBoard() {
		return this.currentBoard;
		//return "rr,rr,rr,rr,rr,rr/,,,,,/,,,,,/,,,,,/,,,,,/bb,bb,bb,bb,bb,bb";
	}
	
	@Override
	public boolean tryMove(String moveString, Player player) {
		String[] move = moveString.split("-");
		String currentStack = getStapel(move[0]);
		if(getStatus().equals("Started")) {
			if(currentStack.charAt(0)==nextPlayerString().charAt(0)) { 
				//falls die Obere stück zum spieler gehört
					if(Integer.parseInt(move[1]) <= currentStack.length()) {
						//(anzahl der Schritte<=anzahl der figuren)?
						if(!(currentStack.length() > 4 && Integer.parseInt(move[1])>=(currentStack.length()-4))) {
						//too Tall Regel
						//TODO kein bewegung im rand richtung wenn stapel schon am rand steht
						
					}	
				}
			}
		}
		
		
		
		
		// TODO: replace with implementation 
		return false;
	}
		
}
