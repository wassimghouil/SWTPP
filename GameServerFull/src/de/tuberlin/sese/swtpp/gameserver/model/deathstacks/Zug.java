/**
 * 
 */
package de.tuberlin.sese.swtpp.gameserver.model.deathstacks;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jdk.nashorn.internal.ir.SetSplitState;

/**
 * @author Ouassim
 *
 */
public class Zug implements Serializable {
	 /**
	 * the cost of the move with the start position
	 */
	private static final long serialVersionUID = 1L;
	private int cost;
	private Position from;
	
	/**Constructor
	 * @param cost
	 * @param from
	 */
	public Zug(int cost, Position from) {
		super();
		this.cost = cost;
		this.from = from;
	}
	
	/**move a point withing one axis
	 * @param from
	 * @param cost
	 * @param richtung
	 * @return
	 */
	private int flex(int from, int cost, int richtung) {
		int to = from + cost*richtung;
		if(richtung == 1 && to>5)
			return flex(5,to-5,richtung*-1);
		if(richtung == -1 && to<0)
			return flex(0,0-to,richtung*-1);
		return to;
	}
	/**move a point withing two axis
	 * @param richtung
	 * @return
	 */
	private String flex(Position richtung) {
		return new Position(flex(from.getX(),cost,richtung.getX()),
				flex(from.getY(),cost,richtung.getY())).toString();
	}
	
	/**get possible destination of this move
	 * @return
	 */
	public Set<String> getPossibleDest() {
		Set<Position> result = Arrays.asList("b6","a5","b5").stream().map(s->new Position(s)).collect(Collectors.toSet());
		result.add(new Position(1,-1));
		result.addAll(result.stream().map(p -> new Position(p.getX()*-1,p.getY()*-1)).collect(Collectors.toSet()));
		return result.parallelStream()
				.map(p -> flex(p))
				.distinct()
				.filter(s-> !s.equals(this.from.toString()))
				.collect(Collectors.toSet());
		
	}
//	private Position getPossibleDest(int x,int y) {
//		int richtung
//		int grenzex,grenzey;
//		int x = this.from.getX();
//		int y = this.from.getY();
//		int cost = this.cost;
//		while(cost>0)
//			grenzex = (x==1)?5:0;
//			grenzey = (y==1)?5:0;
//			x +=cost*x;
//			y +=cost*y;
//			if(x>grenzex) {
//			cost = x-grenzey;
//			x = grenze ;
//			x *= -1;
//			if(y>grenze)
//			then cost = y-grenze
//			and y = grenze and b *= -1
//		return result;	
//	}
//	public Set<Position> getPossibleDest(){
//		//TODO need to be implemented to get the possible destinations and compare it to the input destination
//	Set<Position> result = new HashSet<>();
//	int richtung = 1;
//	int cost = this.cost;
//	int newY = this.from.getY();
//	while(cost>0) {
//	newY =+ cost*richtung;
//	if(newY>5) {
//	cost-=(newY-from.getY());
//	richtung *= -1;
//	}
//	}
//	return result;
//	//innit vector
//	
//	}
	
	
}
