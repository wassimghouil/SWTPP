/**
 * 
 */
package de.tuberlin.sese.swtpp.gameserver.model.deathstacks;

import java.util.Stack;
import de.tuberlin.sese.swtpp.gameserver.model.Position;

/**
 * @author Ouassim
 *
 */
public class Stapel {
	private static final long serialVersionUID = 1L;

	private String data;
	private Position position;

	public Stapel(String data, Position position) {
		this.data = data;
		this.position = position;
	}

	public String GetData() {
		return this.data;
	}

	public Position GetPosition() {
		return this.position;
	}
}
