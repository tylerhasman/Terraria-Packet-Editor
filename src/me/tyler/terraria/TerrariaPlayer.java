package me.tyler.terraria;

import me.tyler.terraria.packets.TerrariaPacketInventorySlot;
import me.tyler.terraria.packets.TerrariaPacketPlayerInfo;
import me.tyler.terraria.packets.TerrariaPacketUpdatePlayer.Control;

public class TerrariaPlayer {

	private byte id;
	private String name;
	private float x, y;
	private float velocityX, velocityY;
	private boolean pvpEnabled;
	private Control[] controls;
	private boolean facingLeft;
	private int hp, maxHp;
	private int mana, maxMana;
	protected Proxy proxy;
	private int[] inventory;
	private PlayerInfo info;
	private float rotation;
	
	public TerrariaPlayer(byte id, Proxy proxy) {
		this.id = id;
		name = "<not set>";
		controls = new Control[0];
		facingLeft = false;
		this.proxy = proxy;
		inventory = new int[220];
		info = new PlayerInfo();
	}
	
	public PlayerInfo getInfo() {
		return info;
	}

	public void setPlayerId(byte playerId) {
		id = playerId;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public int getHp() {
		return hp;
	}
	
	public int getMaxHp() {
		return maxHp;
	}
	
	public void setHealth(int hp) {
		this.hp = hp;
	}
	
	public void setMaxHealth(int maxHp) {
		this.maxHp = maxHp;
	}
	
	public void setMana(int mana) {
		this.mana = mana;
	}
	
	public int getMana() {
		return mana;
	}
	
	public int getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}
	
	public void setInventoryItem(int index, int id){
		setInventoryItem(index, id, true);
	}
	
	public void setInventoryItem(int index, int id, boolean update){
		if(index >= inventory.length){
			System.out.println("Warning: tried to set inventory slot "+index+" "+id);
			return;
		}
		inventory[index] = id;

		if(update){
			TerrariaPacketInventorySlot slot = new TerrariaPacketInventorySlot(getId(), index, 1, 0, id);
			
			proxy.sendPacketToClient(slot);	
		}
	}
	
	public int getInventoryItem(int index){
		return inventory[index];
	}
	
	public int getInventoryItem(InventorySlot slot){
		return inventory[slot.getSlot()];
	}
	
	public int[] getInventory(){
		return inventory;
	}
	
	public int getInventorySize(){
		return inventory.length;
	}
	
	public boolean isPvpEnabled() {
		return pvpEnabled;
	}
	
	public void setPvpEnabled(boolean pvpEnabled) {
		this.pvpEnabled = pvpEnabled;
	}
	
	public String getName() {
		return name;
	}
	
	public float getVelocityX() {
		return velocityX;
	}
	
	public float getVelocityY() {
		return velocityY;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public byte getId() {
		return id;
	}

	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}
	
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}
	
	public boolean isFacingLeft() {
		return facingLeft;
	}
	
	public float distance(float x, float y){
		float x3 = (float) Math.pow(getX() - x, 2);
		float y3 = (float) Math.pow(getY() - y, 2);

		float distance = (float) Math.sqrt(x3 + y3);
		
		return distance;
	}

	public void setControls(Control[] controls) {
		this.controls = controls;
	}
	
	public boolean isUsingHeldItem(){
		for(Control ctrl : controls){
			if(ctrl == Control.USE_ITEM){
				return true;
			}
		}
		
		return false;
	}
	
	public void cycle(){
		for(Control control : controls){
			if(control == Control.LEFT){
				x -= 90;
				facingLeft = true;
			}
			if(control == Control.RIGHT){
				x += 90;
				facingLeft = false;
			}
		}
	}
	
	public void updateInfo(){
		TerrariaPacketPlayerInfo packet = new TerrariaPacketPlayerInfo(id, name, info);
		
		proxy.sendPacketToClient(packet);
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public float getRotation() {
		return rotation;
	}
	
}
