package de.tuberlin.sese.swtpp.gameserver.model.deathstacks;
import java.io.Serializable;
import java.rmi.Remote;
import java.util.*;
import java.util.stream.Collectors;
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
			tempRow.add(new Stapel(new Position(x,y)));
			}
			else {
			Stemp = (y==0)?"rr":"bb";
			tempRow.add(new Stapel(Stemp, new Position(x, y)));
			}
		} 
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
	this.getStapel(to).addPieces(movedPieces);
	return true;	
	}
	
	/** 
	 * 
	 */
	public String toString() {
		return this.positions.stream()
				.map(l-> l.stream().map(stapel -> (stapel == null || stapel.toString() == null)?"":stapel.toString())
						.collect(Collectors.joining(",")))
				.collect(Collectors.joining("/"));
				}
	
	
	/**create a stack at a specified position
	 * @param input
	 * @param pos
	 */
	private void createStapelAt(String input,Position pos) {
	this.positions.get(pos.getY()).set(pos.getX(),new Stapel(input, pos));	
	}
	
	/**check if is there a Tall stack
	 * @param player
	 * @return true if it exist a Tall stack,false otherwise
	 */
	public boolean getTallStack(char player) {
		return this.positions.stream().anyMatch(row-> row.stream().anyMatch(stack -> stack.getOwner() == player && stack.getNumbofPieces()>= 4));
	}

}
