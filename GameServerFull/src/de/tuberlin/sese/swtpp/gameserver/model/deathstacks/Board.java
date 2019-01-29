package de.tuberlin.sese.swtpp.gameserver.model.deathstacks;
import java.util.*;

import de.tuberlin.sese.swtpp.gameserver.model.Position;
/**
 * @author Ouassim
 *
 */
public class Board {
	
	private final LinkedList<LinkedList<Stapel>> positions = new LinkedList<LinkedList<Stapel>>();
	
	/****************
	 * Constructor
	 **************/
	public Board() {
	String Stemp;	
	LinkedList<Stapel> tempRow = null;
	for (int y = 0; y < 6; y++) {
		tempRow = new LinkedList<>();
		for (int x = 0; x < 6; x++) {
			
			if(y>0 && y<5) {
			System.out.println("hier "+y);
			tempRow.add(null);
			}
			
			else {
			Stemp = (y==0)?"rr":"bb";
			tempRow.add(new Stapel(Stemp, new Position(x, y)));
			}
		} 
		//System.out.println(tempRow);
		this.positions.add(tempRow);
	}
	}
	
	/**
	 * @return
	 */
	public LinkedList<LinkedList<Stapel>> getPositions() {
		return positions;
	}
	
	/**
	 * @param position
	 * @return
	 */
	public Stapel getStapel(String position) {
		Position temp = new Position(position);
		return this.positions.get(temp.getY()).stream()
										.filter(s->s.GetPosition().getX() == temp.getX())
										.findAny().orElse(null);
			}
	/**
	 * @param move
	 */
	public void setBoard(String move) {
//		String[] movet = move.split("-");
//		Stapel fromStapel = getStapel(movet[0]);
//		Stapel toStapel	= getStapel(movet[2]);
//		toStapel.addStapel(fromStapel.removePieces(Integer.parseInt(movet[1])));
//		int y=0;
//		for (LinkedList<Stapel> row : this.positions) {
//		if(y == fromStapel.GetPosition().getY())
//		row.set(fromStapel.GetPosition().getX(), fromStapel);
//		if(y == toStapel.GetPosition().getY())
//			row.set(toStapel.GetPosition().getX(), toStapel);
//		}
//		
	//TODO
	}
	

}
