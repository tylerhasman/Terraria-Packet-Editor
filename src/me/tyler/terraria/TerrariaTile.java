package me.tyler.terraria;

public class TerrariaTile {

	private int tileType;
	private byte liquid;
	
	public TerrariaTile() {
		tileType = TerrariaData.frameImportantTiles.length-1;
		liquid = -1;
	}
	
	public boolean isFrameImportant() {
		return TerrariaData.frameImportantTiles[tileType];
	}
	
	public int getTileType() {
		return tileType;
	}
	
	public void setTileType(int tileType) {
		this.tileType = tileType;
	}

	public void copy(TerrariaTile lastTile) {
		tileType = lastTile.tileType;
	}

	public void setLiquid(byte liquid) {
		this.liquid = liquid;
	}
	
	public byte getLiquid() {
		return liquid;
	}
	
}
