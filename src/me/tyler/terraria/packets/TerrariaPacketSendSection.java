package me.tyler.terraria.packets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaTile;
import me.tyler.terraria.tools.CSharpDecompressor;

public class TerrariaPacketSendSection extends TerrariaPacket {

	private byte[] decompressed;
	
	public TerrariaPacketSendSection(byte type, byte[] payload) {
		super(type, payload);
		
		if(isCompressed() && CSharpDecompressor.isEnabled()){
			
			byte[] toDecompress = new byte[getPayload().length-1];
			
			for(int i = 1; i < getPayload().length;i++){
				toDecompress[i-1] = getPayload()[i];
			}
			
			decompressed = CSharpDecompressor.decompress(toDecompress);
			
			/*ByteBuffer buf = ByteBuffer.allocate(decompressed.length+1).order(ByteOrder.LITTLE_ENDIAN);
			
			buf.put((byte) 0);
			buf.put(decompressed);*/
			
			if(decompressed.length < Short.MAX_VALUE){
				setPayload(decompressed);
			}
			
		}else{
			decompressed = new byte[getPayload().length-1];
			
			getPayloadBuffer().get(decompressed);
		}
		
	}
	
	@Override
	public ByteBuffer getPayloadBuffer(int offset) {
		
		if(decompressed == null){
			return super.getPayloadBuffer(offset);
		}
		
		return (ByteBuffer) ByteBuffer.wrap(decompressed, offset, decompressed.length - offset).order(ByteOrder.LITTLE_ENDIAN);
	}
	
	public boolean isCompressed(){
		return getPayload()[0] > 0;
	}
	
	public int getX(){
		return getPayloadBuffer().getInt();
	}
	
	public int getY(){
		return getPayloadBuffer(4).getInt();
	}

	public short getWidth(){
		return getPayloadBuffer(8).getShort();
	}
	
	public short getHeight(){
		return getPayloadBuffer(10).getShort();
	}
	
	/*@SuppressWarnings("unused")
	public TerrariaTile[][] getTiles(Proxy proxy){
		
		TerrariaTile[][] tiles = new TerrariaTile[getWidth()][getHeight()];
		
		ByteBuffer buf = getPayloadBuffer(13);
		
		int repeat = 0;
		
		TerrariaTile lastTile = null;

		for(int j = 0; j < getHeight();j++){
			for(int i = 0; i < getWidth();i++){
				
				int actualX = getX() + i;
				int actualY = getY() + j;
			
				if(repeat != 0){
					repeat--;
					tiles[i][j] = lastTile;
					proxy.getTileOrMake(actualX, actualY).copy(lastTile);
					continue;
				}
				
				TerrariaTile tile = proxy.getTileOrMake(actualX, actualY);
				
				byte flag1 = buf.get();
				byte flag2 = 0;
				byte flag3 = 0;
				if((flag1 & 1) == 1){
					flag2 = buf.get();
				}
				
				if((flag2 & 1) == 1){
					flag3 = buf.get();
				}
				
				if((flag1 & 2) == 2){
					int tileType;
					if((flag1 & 32) == 32){
						int b = buf.get() & 0xFF;
						tileType = (buf.get() << 8 | b);
					}else{
						tileType = buf.get();
					}
					if(tile.isFrameImportant()){
						short frameX = buf.getShort();
						short frameY = buf.getShort();
					}
					if((flag3 & 8) == 8){
						byte color = buf.get();
					}
					tile.setTileType(tileType);
				}
				
				if((flag1 & 4) == 4){
					byte wall = buf.get();
					if((flag3 & 16) == 16){
						byte wallColor = buf.get();
					}
				}
				
				if((flag1 & 8) == 8){
					byte liquidType = 0;//Water
					
					tile.setLiquid(liquidType);
					
					//Im not sure about this
					//Research will have to be done
					//as to how to find out the liquid type
				}
				
				if((flag2 & 2) == 2){
					byte wire = buf.get();
				}
				
				if((flag2 & 4) == 4){
					byte wire2 = buf.get();
				}
				
				if((flag2 & 8) == 8){
					byte wire3 = buf.get();
				}
				
				if((flag1 & 64) == 64){
					short repeatCount = 0;
					if((flag1 & 128) == 128){
						repeatCount = buf.getShort();
					}else{
						repeatCount = buf.get();
					}
					repeat += repeatCount;
				}
				
				lastTile = tile;
				
				tiles[i][j] = tile;
			}
		}
		
		return tiles;
	}*/
	
	

	@Override
	public boolean onReceive(Proxy proxy) {
		
		if(CSharpDecompressor.isEnabled() || !isCompressed()){

			//getTiles(proxy);//Gets the tiles and saves them in memory
			
			System.out.println("Downloaded tiles!");
		
		}
		
		return super.onReceive(proxy);
	}

}
