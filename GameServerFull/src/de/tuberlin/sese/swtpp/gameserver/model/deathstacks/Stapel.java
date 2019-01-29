/**
 * 
 */
package de.tuberlin.sese.swtpp.gameserver.model.deathstacks;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import de.tuberlin.sese.swtpp.gameserver.model.Position;

/**
 * @author Ouassim
 *
 */
public class Stapel {
	private static final long serialVersionUID = 1L;

	/*********************************************************************
	 **the string that represent a stack with his position on the board
	***********************************************************************/
	private List<Character> data;
	private Position position;

	/**********************************************************************
	 * CONSTRUCTORS
	 * @param data
	 * @param position
	 *********************************************************************/
	public Stapel(LinkedList<Character> data, Position position) {
		this.data = data;
		this.position = position;
	}
	public Stapel(String data,Position position) {
		this(data.chars()
				.mapToObj(i->(char)i)
				.collect(Collectors.toCollection(LinkedList::new)),position);
	}
	public Stapel(String data , String pos) {
		this(data.chars()
				.mapToObj(i->(char)i)
				.collect(Collectors.toCollection(LinkedList::new)), new Position(pos));
		
	}

	
	/**********
	 * GETTERS
	 **********/
	public List GetData() {
		return this.data;
	}

	public Position GetPosition() {
		return this.position;
	}
	
	@Override
	public String toString() {
		if(data == null)
		return "null";
		return data.stream()
				.map(Object::toString)
				.collect(Collectors.joining());
	}
	/**
	 * @param stapel
	 */
	public String addStapel(String stapel) {
		if(stapel == null)
			return null ;
		if(data == null) 
		this.data = new Stapel(stapel,"a0").data;
		else
		this.data.addAll(0,new Stapel(stapel,"a0").data);
		return this.toString();
	}
	/**
	 * @param x
	 */
	public String removePieces(int x) {
		if(x >= this.data.size()) {
			this.data = null;
			return null;
		}
		else 
			this.data = this.data.subList(x,data.size());
		return this.toString();
		
	}
	
}
