package de.tuberlin.sese.swtpp.gameserver.model.deathstacks;
import java.io.Serializable;
import java.rmi.Remote;
import java.util.*;
import java.util.stream.Collectors;

import de.tuberlin.sese.swtpp.gameserver.model.Position;
/**
 * @author Ouassim
 *
 */
public class Board implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * board representation
	 */
	private LinkedList<LinkedList<Stapel>> positions = new LinkedList<LinkedList<Stapel>>();
	
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
			tempRow.add(new Stapel(new Position(x,y)));
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
	 * @return board positions
	 */
	public LinkedList<LinkedList<Stapel>> getPositions() {
		return positions;
	}
	
	/**
	 * @param position
	 * @return the 
	 */
	public Stapel getStapel(String position) {
		Position temp = new Position(position);
		return this.positions.get(temp.getY()).stream()
										.filter(s->s.GetPosition().getX() == temp.getX())
										.findAny().orElse(null);
			}
	/**
	 * @param move that will be played
	 * (the move will not be checked)
	 */
	public boolean movePieces(String from,String to,int x) {
	String movedPieces = this.getStapel(from).takePieces(x);
	
	Stapel toStapel = this.getStapel(to);
	this.getStapel(to).addPieces(movedPieces);
	return true;	
	}
	/** TOSTRING
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return this.positions.stream()
				.map(l-> l.stream().map(stapel -> (stapel == null || stapel.toString() == null)?"":stapel.toString())
						.collect(Collectors.joining(",")))
				.collect(Collectors.joining("/"));
				}
	private void createStapelAt(String input,Position pos) {
	this.positions.get(pos.getY()).set(pos.getX(),new Stapel(input, pos));	
	}
	public boolean getTallStack(char player) {
		return this.positions.stream().anyMatch(row-> row.stream().anyMatch(stack -> stack.getOwner() == player && stack.getNumbofPieces()>= 4));
	}
	
//	public void setPositions(String position) {
//		LinkedList<LinkedList<Stapel>> result = new LinkedList<LinkedList<Stapel>>();
//		String[] rows = position.split("/");
//		String[]row = null;
//		LinkedList<Stapel> tempRow = null;
//		
//		for (int y = 0; y < 6; y++) {
//			row = rows[y].split(",");
//			tempRow = new LinkedList<Stapel>();
//			for (int x = 0; x < 6; x++) {
//				tempRow.add(new Stapel(row[x], new Position(x, y)));
//				}
//			result.add(tempRow);		
//	}
//		this.positions = result ;
//}
}
