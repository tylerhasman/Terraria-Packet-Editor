package me.tyler.terraria.packets;

import java.net.Socket;
import me.tyler.terraria.PacketUtil;
import me.tyler.terraria.Proxy;

public class TerrariaPacketWorldInfo extends TerrariaPacket {

	public TerrariaPacketWorldInfo(byte type, byte[] payload) {
		super(type, payload);
	}
	
	public int getTime(){
		return getPayloadBuffer().getInt();
	}
	
	public byte getDayMoonInfo(){
		return getPayloadBuffer(4).get();
	}
	
	public byte getMoonPhase(){
		return getPayloadBuffer(5).get();
	}
	
	public int getMaxTilesX(){
		return getPayloadBuffer(6).getShort() & 0xFFFF;
	}
	
	public int getMaxTilesY(){
		return getPayloadBuffer(8).getShort() & 0xFFFF;
	}
	
	public short getSpawnX(){
		return getPayloadBuffer(10).getShort();
	}
	
	public short getSpawnY(){
		return getPayloadBuffer(12).getShort();
	}
	
	public short getWorldSurface(){
		return getPayloadBuffer(14).getShort();
	}
	
	public short getRockLayer(){
		return getPayloadBuffer(16).getShort();
	}
	
	public int getWorldId(){
		return getPayloadBuffer(18).getInt();
	}
	
	public String getWorldName(){
		return PacketUtil.readString(getPayload(), 22);
	}
	
	private int getWorldNameOffset(){
		return getPayload()[22] + 1;
	}
	
	public byte getMoonType(){
		return getPayloadBuffer(getWorldNameOffset() + 22).get();
	}
	
	public byte getTreeBackground(){
		return getPayloadBuffer(getWorldNameOffset() + 23).get();
	}
	
	public byte getCorruptionBackground(){
		return getPayloadBuffer(getWorldNameOffset() + 24).get();
	}
	
	public byte getJungleBackground(){
		return getPayloadBuffer(getWorldNameOffset() + 25).get();
	}
	
	public byte getSnowBackground(){
		return getPayloadBuffer(getWorldNameOffset() + 26).get();
	}
	
	public byte getHallowBackground(){
		return getPayloadBuffer(getWorldNameOffset() + 27).get();
	}
	
	public byte getCrimsonBackground(){
		return getPayloadBuffer(getWorldNameOffset() + 28).get();
	}
	
	public byte getDesertBackground(){
		return getPayloadBuffer(getWorldNameOffset() + 29).get();
	}
	
	public byte getOceanBackground(){
		return getPayloadBuffer(getWorldNameOffset() + 30).get();
	}
	
	public byte getIceBackStyle(){
		return getPayloadBuffer(getWorldNameOffset() + 31).get();
	}
	
	public byte getJungleBackStyle(){
		return getPayloadBuffer(getWorldNameOffset() + 32).get();
	}
	
	public byte getHellBackStyle(){
		return getPayloadBuffer(getWorldNameOffset() + 33).get();
	}
	
	public float getWindSpeedSet(){
		return getPayloadBuffer(getWorldNameOffset() + 34).getFloat();
	}
	
	public byte getCloudNumber(){
		return getPayloadBuffer(getWorldNameOffset() + 38).get();
	}
	
	public int getTree1(){
		return getPayloadBuffer(getWorldNameOffset() + 39).getInt();
	}
	
	public int getTree2(){
		return getPayloadBuffer(getWorldNameOffset() + 43).getInt();
	}
	
	public int getTree3(){
		return getPayloadBuffer(getWorldNameOffset() + 47).getInt();
	}

	public byte getTreeStyle1(){
		return getPayloadBuffer(getWorldNameOffset() + 51).get();
	}
	
	public byte getTreeStyle2(){
		return getPayloadBuffer(getWorldNameOffset() + 52).get();
	}
	
	public byte getTreeStyle3(){
		return getPayloadBuffer(getWorldNameOffset() + 53).get();
	}
	
	public byte getTreeStyle4(){
		return getPayloadBuffer(getWorldNameOffset() + 54).get();
	}
	
	public int getCaveBack1(){
		return getPayloadBuffer(getWorldNameOffset() + 55).getInt();
	}
	
	public int getCaveBack2(){
		return getPayloadBuffer(getWorldNameOffset() + 59).getInt();
	}
	
	public int getCaveBack3(){
		return getPayloadBuffer(getWorldNameOffset() + 63).getInt();
	}
	
	public byte getCaveBackStyle1(){
		return getPayloadBuffer(getWorldNameOffset() + 67).get();
	}
	
	public byte getCaveBackStyle2(){
		return getPayloadBuffer(getWorldNameOffset() + 68).get();
	}
	
	public byte getCaveBackStyle3(){
		return getPayloadBuffer(getWorldNameOffset() + 69).get();
	}
	
	public byte getCaveBackStyle4(){
		return getPayloadBuffer(getWorldNameOffset() + 70).get();
	}
	
	public float getRain(){
		return getPayloadBuffer(getWorldNameOffset() + 71).getFloat();
	}
	
	public byte getEventInfo1(){
		return getPayloadBuffer(getWorldNameOffset() + 75).get();
	}
	
	public byte getEventInfo2(){
		return getPayloadBuffer(getWorldNameOffset() + 76).get();
	}
	
	public byte getEventInfo3(){
		return getPayloadBuffer(getWorldNameOffset() + 77).get();
	}
	
	public byte getEventInfo4(){
		return getPayloadBuffer(getWorldNameOffset() + 78).get();
	}
	
	public byte getInvasionType(){
		return getPayloadBuffer(getWorldNameOffset() + 79).get();
	}
	
	public long getLobby(){
		return getPayloadBuffer(getWorldNameOffset() + 80).getLong();
	}
	
	public boolean isServerSideCharacters(){
		return (getEventInfo1() & 64) > 0;
	}
	
	@Override
	public boolean onReceive(Proxy proxy, Socket client) {
		
		if(!isServerSideCharacters()){
			getPayload()[getWorldNameOffset() + 75] += 64;
		}
		
		if(!proxy.areDimensionsSet()){
			proxy.setWorldDimensions(getMaxTilesX(), getMaxTilesY());
			System.out.println("World dimensions set to "+getMaxTilesX()+" "+getMaxTilesY());
		}
		
		return super.onReceive(proxy, client);
	}
	

}
