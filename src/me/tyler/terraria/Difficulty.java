package me.tyler.terraria;

public enum Difficulty {
	SOFTCORE(0),
	MEDIUMCORE(1),
	HARDCORE(2),
	EXTRA(4),
	UNKNOWN(5);
	
	private final int id;
	
	private Difficulty(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public static Difficulty getFromId(int difficulty) {
		
		for(Difficulty d : values()){
			if(d.id == difficulty){
				return d;
			}
		}
		
		throw new IllegalArgumentException("no difficulty with id "+difficulty);
	}
}
