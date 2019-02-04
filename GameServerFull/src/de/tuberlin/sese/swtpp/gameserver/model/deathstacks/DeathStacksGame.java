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
	private final Board board = new Board() ;
	private String currentpositions = "" ;
	
	/************************
	 * constructors
	 ***********************/
	
	public DeathStacksGame() throws Exception{
	 super();
		this.currentpositions = this.board.toString();
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

	@Override
	public void setBoard(String state) {
		this.currentpositions = state;
	}
	
	@Override
	public String getBoard() {
//		System.out.println(this.board.toString());
//		System.out.println(this.currentpositions);
		return this.board.toString() ;
	}
	
	@Override
	public boolean tryMove(String moveString, Player player) {
//		String[] move = moveString.split("-");
//		
		//falls moveString gültig und spiel gestartet ist
		if(verifyMove(moveString) && getStatus().equals("Started")) {
			String[] move = moveString.split("-");
			Stapel fromStack = board.getStapel(move[0]);
			System.out.println("falls moveString gültig und spiel gestartet ist");
			//falls die Obere stueck zum spieler gehoert
			if(fromStack.toString().charAt(0)==nextPlayerString().charAt(0)) {
				System.out.println("falls die Obere stueck zum spieler gehoert");
				//too Tall Regel
				if((fromStack.getNumbofPieces()>=4 && fromStack.getNumbofPieces()-Integer.parseInt(move[1])<=4)  ||!board.getTallStack(nextPlayerString().charAt(0))) {
					System.out.println("too Tall Regel");
						//falls nicht start und ziel im rand stehen
						if(!(new Position(move[0]).isRand()&&(new Position(move[2]).isRand()))){
						System.out.println("falls nicht start und ziel im rand stehen");
						//Züg ausfuhren
						this.board.movePieces(move[0],move[2],Integer.parseInt(move[1]));
						setNextPlayer((isRedNext())?bluePlayer:redPlayer);
						return true;
						}
					}
				}	
		}				//(anzahl der Schritte<=anzahl der figuren)?
						//if(!(currentStack.length() > 4 && Integer.parseInt(move[1])>=(currentStack.length()-4))) {
						
						//TODO kein bewegung im rand richtung wenn stapel schon am rand steht
		return false;
	}

	// Checks if moveString format is valid
	private boolean verifyMove(String moveString) {
		String re = "[a-f][1-6]-+\\d-[a-f][1-6]";
		if(!moveString.matches(re)) {
			return false;
		}
		/*
		String[] move = moveString.split("-");
		String stapel = this.getStapel(move[0]);
		*/


		return true;
	}

	// Searches for a stapel.length > 4 for the current player
	// TODO: check if stapel.position == moveString.position
	// TODO: check if moveString moves enough stones so is stapel.length < 5
	private Stapel checkStapels(String moveString) {
		String[] reihen = getBoard().split("/");
		for (int i = 0; i < reihen.length - 1; i++) {
			String[] felder = reihen[i].split(",");
			for (int j = 0; j < felder.length - 1; i++) {
				if (felder[j].length() < 5) {
					continue;
				}
				if (felder[j].startsWith(nextPlayerString())) {
					return new Stapel(felder[j], new Position(j, i).toString());
				}

			}
		}

		return null;
	};
}
