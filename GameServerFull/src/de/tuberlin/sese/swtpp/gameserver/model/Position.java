/**
 * 
 */
package de.tuberlin.sese.swtpp.gameserver.model;

import java.io.Serializable;

/**
 * @author Ouassim
 *
 */
public class Position implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	
	
	
	public Position(int x,int y) {
		
		this.x=x;
		this.y=y;
		
	}
	
	
	public Position(String pos) {
		this(((int)pos.charAt(0))-97,6-Character.getNumericValue(pos.charAt(1)));
		 }
	
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	
	@Override
	public String toString() {
		return (char)(x+97) + String.valueOf(6-y);
	}
	
	public int getDistance (Position to) {
		int xDif = Math.abs(this.x-to.x);
		int yDif = Math.abs(this.y-to.y);
		if(xDif == yDif || xDif*yDif == 0)
		return	Math.max(xDif, yDif);
				
		return -1;
	}
	public boolean isRand() {
		return (x==0 || x==5 || y ==0 || y==5 );
	}
	
	
	
	
	

}
