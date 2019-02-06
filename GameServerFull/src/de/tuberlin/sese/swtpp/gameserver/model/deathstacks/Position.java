/**
 * 
 */
package de.tuberlin.sese.swtpp.gameserver.model.deathstacks;

import java.io.Serializable;

/**
 * @author Ouassim
 *
 */
public class Position implements Serializable {
	
	/**
	 * Coordinate
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
	
	
	
	public void setX(int x) {
		this.x = x;
	}


	public void setY(int y) {
		this.y = y;
	}


	@Override
	public String toString() {
		return (char)(x+97) + String.valueOf(6-y);
	}
	
	/**
	 * @param to
	 * @return
	 */
//	public int getDistance (Position to) {
//		int xDif = Math.abs(this.x-to.x);
//		int yDif = Math.abs(this.y-to.y);
//		if(xDif == yDif || xDif*yDif == 0)
//		return	Math.max(xDif, yDif);
//				
//		return -1;
//	}

	/**
	 * @return
	 */
//	public boolean isEcke() {
//		return ((x==0 || x==5)&&( y ==0 || y==5 ));
//	}
	/**
	 * @return
	 */
//	public boolean isRand() {
//		return (x==0 || x==5 || y ==0 || y==5 );
//	}


	
//	@Override
//	public boolean equals(Object obj) {
//		if(obj == this) return true;
//		if(!(obj instanceof Position)) return false;
//		Position pos = (Position)obj;
//		return this.toString().equals(pos.toString());
//	}
	
	
	
	
	
	

}
