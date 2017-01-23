package me.tyler.terraria;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import me.tyler.terraria.packets.TerrariaPacketWorldInfo;

public class WorldInfo {

	private int time;
	private byte dayMoonInfo;
	private byte moonPhase;
	private int maxTileX, maxTileY;
	private short spawnX, spawnY;
	private short worldSurface, rockLayer;
	private int worldId;
	private String name;
	private byte moonType;
	private byte treeBg, corruptionBg, jungleBg, snowBg, hallowBg, crimsonBg, desertBg, oceanBg;
	private byte iceBackStyle, jungleBackStyle, hellBackStyle;
	private float windSpeed;
	private byte cloudNumber;
	private int tree1, tree2, tree3;
	private byte treeStyle1, treeStyle2, treeStyle3, treeStyle4;
	private int caveBack1, caveBack2, caveBack3;
	private byte caveBackStyle1, caveBackStyle2, caveBackStyle3, caveBackStyle4;
	private float rain;
	private byte event1, event2, event3, event4, event5;
	private byte invasionType;
	private long lobby;
	private byte sandstormSeverity;
	
	public WorldInfo(TerrariaPacketWorldInfo packet) {
		load(packet);
	}
	
	public TerrariaPacketWorldInfo getPacket(){
		ByteBuffer buf = ByteBuffer.allocate(89 + PacketUtil.calculateLength(name)).order(ByteOrder.LITTLE_ENDIAN);
		
		buf.putInt(time);
		buf.put(dayMoonInfo);
		buf.put(moonPhase);
		buf.putShort((short) maxTileX);
		buf.putShort((short) maxTileY);
		buf.putShort(spawnX);
		buf.putShort(spawnY);
		buf.putShort(worldSurface);
		buf.putShort(rockLayer);
		buf.putInt(worldId);
		PacketUtil.writeString(buf, name);
		buf.put(moonType);
		buf.put(treeBg);
		buf.put(corruptionBg);
		buf.put(jungleBg);
		buf.put(snowBg);
		buf.put(hallowBg);
		buf.put(crimsonBg);
		buf.put(desertBg);
		buf.put(oceanBg);
		buf.put(iceBackStyle);
		buf.put(jungleBackStyle);
		buf.put(hellBackStyle);
		buf.putFloat(windSpeed);
		buf.put(cloudNumber);
		buf.putInt(tree1);
		buf.putInt(tree2);
		buf.putInt(tree3);
		buf.put(treeStyle1);
		buf.put(treeStyle2);
		buf.put(treeStyle3);
		buf.put(treeStyle4);
		buf.putInt(caveBack1);
		buf.putInt(caveBack2);
		buf.putInt(caveBack3);
		buf.put(caveBackStyle1);
		buf.put(caveBackStyle2);
		buf.put(caveBackStyle3);
		buf.put(caveBackStyle4);
		buf.putFloat(rain);
		buf.put(event1);
		buf.put(event2);
		buf.put(event3);
		buf.put(event4);
		buf.put(event5);
		buf.put(invasionType);
		buf.putLong(lobby);
		buf.put(sandstormSeverity);
		
		return new TerrariaPacketWorldInfo(PacketType.WORLD_INFO.getId(), buf.array());
	}
	
	private void load(TerrariaPacketWorldInfo info){
		time = info.getTime();
		dayMoonInfo = info.getDayMoonInfo();
		moonPhase = info.getMoonPhase();
		maxTileX = info.getMaxTilesX();
		maxTileY = info.getMaxTilesY();
		spawnX = info.getSpawnX();
		spawnY = info.getSpawnY();
		worldSurface = info.getWorldSurface();
		rockLayer = info.getRockLayer();
		worldId = info.getWorldId();
		name = info.getWorldName();
		moonType = info.getMoonType();
		treeBg = info.getTreeBackground();
		corruptionBg = info.getCorruptionBackground();
		jungleBg = info.getJungleBackground();
		snowBg = info.getSnowBackground();
		hallowBg = info.getHallowBackground();
		crimsonBg = info.getCrimsonBackground();
		desertBg = info.getDesertBackground();
		oceanBg = info.getOceanBackground();
		iceBackStyle = info.getIceBackStyle();
		jungleBackStyle = info.getJungleBackStyle();
		hellBackStyle = info.getHellBackStyle();
		windSpeed = info.getWindSpeedSet();
		cloudNumber = info.getCloudNumber();
		tree1 = info.getTree1();
		tree2 = info.getTree2();
		tree3 = info.getTree3();
		treeStyle1 = info.getTreeStyle1();
		treeStyle2 = info.getTreeStyle2();
		treeStyle3 = info.getTreeStyle3();
		treeStyle4 = info.getTreeStyle4();
		caveBack1 = info.getCaveBack1();
		caveBack2 = info.getCaveBack2();
		caveBack3 = info.getCaveBack3();
		caveBackStyle1 = info.getCaveBackStyle1();
		caveBackStyle2 = info.getCaveBackStyle2();
		caveBackStyle3 = info.getCaveBackStyle3();
		caveBackStyle4 = info.getCaveBackStyle4();
		rain = info.getRain();
		event1 = info.getEventInfo1();
		event2 = info.getEventInfo2();
		event3 = info.getEventInfo3();
		event4 = info.getEventInfo4();
		event5 = info.getEventInfo5();
		invasionType = info.getInvasionType();
		lobby = info.getLobby();
		sandstormSeverity = info.getSandstormSeverity();
	}

	public int getTime() {
		return time;
	}

	public byte getDayMoonInfo() {
		return dayMoonInfo;
	}

	public byte getMoonPhase() {
		return moonPhase;
	}

	public int getMaxTileX() {
		return maxTileX;
	}

	public int getMaxTileY() {
		return maxTileY;
	}

	public short getSpawnX() {
		return spawnX;
	}

	public short getSpawnY() {
		return spawnY;
	}

	public short getWorldSurface() {
		return worldSurface;
	}

	public short getRockLayer() {
		return rockLayer;
	}

	public int getWorldId() {
		return worldId;
	}

	public String getName() {
		return name;
	}

	public byte getMoonType() {
		return moonType;
	}

	public byte getTreeBg() {
		return treeBg;
	}

	public byte getCorruptionBg() {
		return corruptionBg;
	}

	public byte getJungleBg() {
		return jungleBg;
	}

	public byte getSnowBg() {
		return snowBg;
	}

	public byte getHallowBg() {
		return hallowBg;
	}

	public byte getCrimsonBg() {
		return crimsonBg;
	}

	public byte getDesertBg() {
		return desertBg;
	}

	public byte getOceanBg() {
		return oceanBg;
	}

	public byte getIceBackStyle() {
		return iceBackStyle;
	}

	public byte getJungleBackStyle() {
		return jungleBackStyle;
	}

	public byte getHellBackStyle() {
		return hellBackStyle;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public byte getCloudNumber() {
		return cloudNumber;
	}

	public int getTree1() {
		return tree1;
	}

	public int getTree2() {
		return tree2;
	}

	public int getTree3() {
		return tree3;
	}

	public byte getTreeStyle1() {
		return treeStyle1;
	}

	public byte getTreeStyle2() {
		return treeStyle2;
	}

	public byte getTreeStyle3() {
		return treeStyle3;
	}

	public byte getTreeStyle4() {
		return treeStyle4;
	}

	public int getCaveBack1() {
		return caveBack1;
	}

	public int getCaveBack2() {
		return caveBack2;
	}

	public int getCaveBack3() {
		return caveBack3;
	}

	public byte getCaveBackStyle1() {
		return caveBackStyle1;
	}

	public byte getCaveBackStyle2() {
		return caveBackStyle2;
	}

	public byte getCaveBackStyle3() {
		return caveBackStyle3;
	}

	public byte getCaveBackStyle4() {
		return caveBackStyle4;
	}

	public float getRain() {
		return rain;
	}

	public byte getEvent1() {
		return event1;
	}

	public byte getEvent2() {
		return event2;
	}

	public byte getEvent3() {
		return event3;
	}

	public byte getEvent4() {
		return event4;
	}
	
	public byte getEvent5() {
		return event5;
	}

	public byte getInvasionType() {
		return invasionType;
	}

	public long getLobby() {
		return lobby;
	}
	
	public byte getSandstormSeverity() {
		return sandstormSeverity;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setDayMoonInfo(byte dayMoonInfo) {
		this.dayMoonInfo = dayMoonInfo;
	}

	public void setMoonPhase(byte moonPhase) {
		this.moonPhase = moonPhase;
	}

	public void setMaxTileX(int maxTileX) {
		this.maxTileX = maxTileX;
	}

	public void setMaxTileY(int maxTileY) {
		this.maxTileY = maxTileY;
	}

	public void setSpawnX(short spawnX) {
		this.spawnX = spawnX;
	}

	public void setSpawnY(short spawnY) {
		this.spawnY = spawnY;
	}

	public void setWorldSurface(short worldSurface) {
		this.worldSurface = worldSurface;
	}

	public void setRockLayer(short rockLayer) {
		this.rockLayer = rockLayer;
	}

	public void setWorldId(int worldId) {
		this.worldId = worldId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMoonType(byte moonType) {
		this.moonType = moonType;
	}

	public void setTreeBg(byte treeBg) {
		this.treeBg = treeBg;
	}

	public void setCorruptionBg(byte corruptionBg) {
		this.corruptionBg = corruptionBg;
	}

	public void setJungleBg(byte jungleBg) {
		this.jungleBg = jungleBg;
	}

	public void setSnowBg(byte snowBg) {
		this.snowBg = snowBg;
	}

	public void setHallowBg(byte hallowBg) {
		this.hallowBg = hallowBg;
	}

	public void setCrimsonBg(byte crimsonBg) {
		this.crimsonBg = crimsonBg;
	}

	public void setDesertBg(byte desertBg) {
		this.desertBg = desertBg;
	}

	public void setOceanBg(byte oceanBg) {
		this.oceanBg = oceanBg;
	}

	public void setIceBackStyle(byte iceBackStyle) {
		this.iceBackStyle = iceBackStyle;
	}

	public void setJungleBackStyle(byte jungleBackStyle) {
		this.jungleBackStyle = jungleBackStyle;
	}

	public void setHellBackStyle(byte hellBackStyle) {
		this.hellBackStyle = hellBackStyle;
	}

	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}

	public void setCloudNumber(byte cloudNumber) {
		this.cloudNumber = cloudNumber;
	}

	public void setTree1(int tree1) {
		this.tree1 = tree1;
	}

	public void setTree2(int tree2) {
		this.tree2 = tree2;
	}

	public void setTree3(int tree3) {
		this.tree3 = tree3;
	}

	public void setTreeStyle1(byte treeStyle1) {
		this.treeStyle1 = treeStyle1;
	}

	public void setTreeStyle2(byte treeStyle2) {
		this.treeStyle2 = treeStyle2;
	}

	public void setTreeStyle3(byte treeStyle3) {
		this.treeStyle3 = treeStyle3;
	}

	public void setTreeStyle4(byte treeStyle4) {
		this.treeStyle4 = treeStyle4;
	}

	public void setCaveBack1(int caveBack1) {
		this.caveBack1 = caveBack1;
	}

	public void setCaveBack2(int caveBack2) {
		this.caveBack2 = caveBack2;
	}

	public void setCaveBack3(int caveBack3) {
		this.caveBack3 = caveBack3;
	}

	public void setCaveBackStyle1(byte caveBackStyle1) {
		this.caveBackStyle1 = caveBackStyle1;
	}

	public void setCaveBackStyle2(byte caveBackStyle2) {
		this.caveBackStyle2 = caveBackStyle2;
	}

	public void setCaveBackStyle3(byte caveBackStyle3) {
		this.caveBackStyle3 = caveBackStyle3;
	}

	public void setCaveBackStyle4(byte caveBackStyle4) {
		this.caveBackStyle4 = caveBackStyle4;
	}

	public void setRain(float rain) {
		this.rain = rain;
	}

	public void setEvent1(byte event1) {
		this.event1 = event1;
	}

	public void setEvent2(byte event2) {
		this.event2 = event2;
	}

	public void setEvent3(byte event3) {
		this.event3 = event3;
	}

	public void setEvent4(byte event4) {
		this.event4 = event4;
	}

	public void setEvent5(byte event5) {
		this.event5 = event5;
	}
	
	public void setInvasionType(byte invasionType) {
		this.invasionType = invasionType;
	}

	public void setLobby(long lobby) {
		this.lobby = lobby;
	}
	
	public void setSandstormSeverity(byte sandstormSeverity) {
		this.sandstormSeverity = sandstormSeverity;
	}
	
	private byte getEventInfo(int type){
		switch(type){
		case 1:
			return event1;
		case 2:
			return event2;
		case 3:
			return event3;
		case 4:
			return event4;
		case 5:
			return event5;
			default:
				throw new IllegalArgumentException("type must be between 1 and 5, inclusively");
		}
	}
	
	public boolean getEventInfoState(EventInfo type){
		byte info = getEventInfo(type.getId());
		
		return (info & type.getBit()) == type.getBit();
	}
	
	public void setEventInfoState(EventInfo type, boolean state){
		if(getEventInfoState(type) == state){
			return;
		}
	
		int amount = type.getBit();
		if(!state){
			amount = -amount;
		}
		
		if(type.getId() == 1){
			event1 += amount;
		}else if(type.getId() == 2){
			event2 += amount;
		}else if(type.getId() == 3){
			event3 += amount;
		}else if(type.getId() == 4){
			event4 += amount;
		}else if(type.getId() == 5){
			event5 += amount;
		}else{
			throw new IllegalArgumentException("No event info number "+type.getId());
		}
		
	}
	


}
