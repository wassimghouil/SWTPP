package de.tuberlin.sese.swtpp.gameserver.test.deathstacks;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.tuberlin.sese.swtpp.gameserver.control.GameController;
import de.tuberlin.sese.swtpp.gameserver.model.Player;
import de.tuberlin.sese.swtpp.gameserver.model.User;
import de.tuberlin.sese.swtpp.gameserver.model.deathstacks.DeathStacksGame;

public class TryMoveTest {

	User user1 = new User("Alice", "alice");
	User user2 = new User("Bob", "bob");
	
	Player redPlayer = null;
	Player bluePlayer = null;
	DeathStacksGame game = null;
	GameController controller;
	
	String gameType ="deathstacks";
	
	@Before
	public void setUp() throws Exception {
		controller = GameController.getInstance();
		controller.clear();
		
		int gameID = controller.startGame(user1, "", gameType);
		
		game = (DeathStacksGame) controller.getGame(gameID);
		redPlayer = game.getPlayer(user1);

	}
	
	public void startGame(String initialBoard, boolean redNext) {
		controller.joinGame(user2, gameType);		
		bluePlayer = game.getPlayer(user2);
		
		game.setBoard(initialBoard);
		game.setNextPlayer(redNext? redPlayer:bluePlayer);
	}
	
	public void assertMove(String move, boolean red, boolean expectedResult) {
		if (red)
			assertEquals(expectedResult, game.tryMove(move, redPlayer));
		else 
			assertEquals(expectedResult,game.tryMove(move, bluePlayer));
	}
	
	public void assertGameState(String expectedBoard, boolean redNext, boolean finished, boolean draw, boolean redWon) {
		String board = game.getBoard();
				
		assertEquals(expectedBoard,board);
		assertEquals(finished, game.isFinished());
		if (!game.isFinished()) {
			assertEquals(redNext, game.isRedNext());
		} else {
			assertEquals(draw, game.isDraw());
			if (!draw) {
				assertEquals(redWon, redPlayer.isWinner());
				assertEquals(!redWon, bluePlayer.isWinner());
			}
		}
	}

	/*******************************************
	 * !!!!!!!!! To be implemented !!!!!!!!!!!!
	 *******************************************/
	
	@Test
	public void exampleTest() {
		startGame("rr,rr,rr,rr,rr,rr/,,,,,/,,,,,/,,,,,/,,,,,/bb,bb,bb,bb,bb,bb",true);
		assertMove("d6-1-d4",true,false);
		System.out.println(this.game.getBoard());
		assertGameState("rr,rr,rr,rr,rr,rr/,,,,,/,,,,,/,,,,,/,,,,,/bb,bb,bb,bb,bb,bb",true,false,false,false);
		assertMove("d6~1~d4",true,false);
		System.out.println(this.game.getBoard());
		assertGameState("rr,rr,rr,rr,rr,rr/,,,,,/,,,,,/,,,,,/,,,,,/bb,bb,bb,bb,bb,bb",true,false,false,false);
		assertMove("a1-1-b1",true,false);
		assertMove("b6-2-b6", true, false);
		//move #1
		assertMove("a6-1-b6", true, true);
		assertGameState("r,rrr,rr,rr,rr,rr/,,,,,/,,,,,/,,,,,/,,,,,/bb,bb,bb,bb,bb,bb",false,false,false,false);
		//setUp
		game.setBoard("r,rrr,rr,rr,rr,rr/,,,,,/,,,,,/,,,,,/,,,,,/,bb,bbbbbb,,bb,bb");
		//move #2
		assertMove("b1-1-c1", false, false);
		assertMove("c1-1-d1",false,false);
		assertMove("a6-1-b6", false, false);
		assertGameState("r,rrr,rr,rr,rr,rr/,,,,,/,,,,,/,,,,,/,,,,,/,bb,bbbbbb,,bb,bb",false,false,false,false);
		assertMove("c1-3-b4",false, true);
		assertGameState("r,rrr,rr,rr,rr,rr/,,,,,/,bbb,,,,/,,,,,/,,,,,/,bb,bbb,,bb,bb",true,false,false,false);
		//setUp
		game.setBoard(",,,,,/,,,,,/,,,,,/bbrr,rrbb,rrbb,rrbb,rrbb,rrbb/,,,,,/,,,,,");
		assertGameState(",,,,,/,,,,,/,,,,,/bbrr,rrbb,rrbb,rrbb,rrbb,rrbb/,,,,,/,,,,,", true,false,false, false);
		//move #3(redWin)
		assertMove("b3-1-a3",true, true);
		assertGameState(",,,,,/,,,,,/,,,,,/rbbrr,rbb,rrbb,rrbb,rrbb,rrbb/,,,,,/,,,,,", false,true,false, true);
		//game #2
		try {
			setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startGame("rr,rr,rr,rr,rr,rr/,,,,,/,,,,,/,,,,,/,,,,,/bb,bb,bb,bb,bb,bb",false);
		System.out.println(game.isFinished());
		assertMove("a1-1-b1", false,true);
		assertMove("a6-1-b6", true, true);
		assertMove("b1-1-a1", false, true);
		assertMove("b6-1-a6", true, true);
		assertMove("a1-1-b1", false, true);
		assertMove("a6-1-b6", true, true);
		assertMove("b1-1-a1", false, true);
		assertMove("b6-1-a6", true, true);
		assertGameState("rr,rr,rr,rr,rr,rr/,,,,,/,,,,,/,,,,,/,,,,,/bb,bb,bb,bb,bb,bb", false, true, true, false);
		assertMove("a1-1-b1", false, false);
		//game #3
		try {
			setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startGame("rr,rr,rr,rr,rr,rr/,,,,,/,,,,,/,,,,,/,,,,,/bb,bb,bb,bb,bb,bb",true);
		assertMove("a6-3-d6", true, false);
		startGame(",,,,,/,,,,,/bbrr,bbrr,bbrr,bbrr,bbrr,rrbb/,,,,,/,,,,,/,,,,,",false);
		assertMove("e4-1-f4", false, true);
		assertGameState(",,,,,/,,,,,/bbrr,bbrr,bbrr,bbrr,brr,brrbb/,,,,,/,,,,,/,,,,,", true, true, false, false);
		//game#4
		try {
			setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startGame("rr,rr,rr,rr,rr,rr/,,,,,/,,,,,/,,,,,/,,,,,/bb,bb,bb,bb,bb,bb",true);
		game.callDraw(redPlayer);
		game.callDraw(bluePlayer);
		game.finish(redPlayer);
		

		
		
	}

	//TODO: implement test cases of same kind as example here
}
