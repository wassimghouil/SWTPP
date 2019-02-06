/**
 * 
 */
package de.tuberlin.sese.swtpp.gameserver.model.deathstacks;
import java.io.Serializable;

/**
 * @author Ouassim
 *
 */
public class Stapel implements Serializable {
	private static final long serialVersionUID = 1L;

	/*********************************************************************
	 **the string that represent a stack with his position on the board
	***********************************************************************/
	private String data;
	private Position position;

	/**********************************************************************
	 * CONSTRUCTORS
	 * @param data
	 * @param position
	 *********************************************************************/
	public Stapel(String data, Position position) {
		this.data = data;
		this.position = position;
	}

	public Stapel(String data , String pos) {
		this(data, new Position(pos));
		
	}
	public Stapel(Position pos) {
		this(null,pos);
	}

	/**********
	 * GETTERS
	 **********/

	public Position GetPosition() {
		return this.position;
	}
	public char getOwner() {
		if(this.data == null || this.data.length() == 0)
		return 'n' ;
		return this.data.charAt(0);
	}
	public int getNumbofPieces() {
		return this.data.length();
	}
	/**********
	 * TOSTRING
	 **********/
	@Override
	public String toString() {
		return this.data;
	}
	/**this method pushes pieces into stack
	 * @param stapel is a string representation of the given stack
	 */
	public void addPieces(String stapel) { 
		if (this.data == null || this.data.isEmpty()) {
		this.data = stapel  ;
		}
		else {
		this.data = stapel + this.data ;	
		}
	}
	/**this method pops a x number of pieces from this stack
	 * @param x : how many pieces you want to take?
	 * @return the string representation of popped pieces
	 * @throws exception if x greater than stack pieces
	 */
	public String takePieces(int x) {
		if(x>this.data.length()) {
			throw new IllegalArgumentException("not enought pieces");
		}
		String removed = this.data.substring(0,x);
		this.data = this.data.substring(x);
		System.out.println("Remove piece are "+ removed);
		return removed  ;
		}
		
	}
	

