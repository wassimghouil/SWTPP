package de.tuberlin.sese.swtpp.gameserver.model.deathstacks;
import java.io.Serializable;
import java.rmi.Remote;
import java.util.*;
import java.util.stream.Collectors;

import de.tuberlin.sese.swtpp.gameserver.model.Player;
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

		this.setBoard("rr,rr,rr,rr,rr,rr/,,,,,/,,,,,/,,,,,/,,,,,/bb,bb,bb,bb,bb,bb");
	}
	/******************
	 * SETTER
	 ******************/
	public void setBoard(String state) {
	LinkedList<LinkedList<Stapel>> result = new LinkedList<LinkedList<Stapel>>();
	LinkedList<Stapel> rowrez = null;
	String[] rows = state.split("/");
	for (int j = 0; j < rows.length; j++) {
		String[] stapeln = rows[j].split(",",-1);
		rowrez = new LinkedList<Stapel>();
		for (int i = 0; i < stapeln.length; i++) {
		rowrez.add(new Stapel(stapeln[i],new Position(i,j)));	
		}
		result.add(rowrez);
	}
	this.positions = result;
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
				.map(l-> l.stream().map(stapel -> (stapel.toString() == null || stapel.toString().isEmpty())?"":stapel.toString())
						.collect(Collectors.joining(",")))
				.collect(Collectors.joining("/"));
				}
	
	

	
	/**check if is there a Tall stack
	 * @param player
	 * @return true if it exist a Tall stack,false otherwise
	 */
	public boolean getTallStack(char player) {
		return this.positions.stream()
				.anyMatch(row-> row
						.stream()
						.anyMatch(stack-> stack.getOwner() == player && stack.getNumbofPieces()>= 4));
	}
	public boolean isWinner(LinkedList<Stapel> input,char p) {
		return input.stream().allMatch(l->l.getOwner() == 'n' || l.getOwner() == p);
	}
	public boolean isWinner(char p) {
	
		return this.positions.stream().allMatch(l->this.isWinner(l, p));
				
				
			

			
	
	}
	
	

}
