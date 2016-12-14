package me.tyler.terraria;

import me.tyler.terraria.packets.TerrariaPacketPlayerInfo;

public class PlayerInfo {

	private byte skinVariant;
	private byte hair;
	private byte hairDye;
	private byte hideVisuals, hideVisuals2, hideMisc;
	private TerrariaColor hairColor = TerrariaColor.WHITE, skinColor = TerrariaColor.WHITE, eyeColor = TerrariaColor.WHITE, shirtColor = TerrariaColor.WHITE, underShirtColor = TerrariaColor.WHITE, pantsColor = TerrariaColor.WHITE, shoeColor = TerrariaColor.WHITE;
	private Difficulty difficulty = Difficulty.SOFTCORE;
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	
	public byte getSkinVariant() {
		return skinVariant;
	}
	public byte getHair() {
		return hair;
	}
	public byte getHairDye() {
		return hairDye;
	}
	public byte getHideVisuals() {
		return hideVisuals;
	}
	public byte getHideVisuals2() {
		return hideVisuals2;
	}
	public byte getHideMisc() {
		return hideMisc;
	}
	public TerrariaColor getHairColor() {
		return hairColor;
	}
	public TerrariaColor getSkinColor() {
		return skinColor;
	}
	public TerrariaColor getEyeColor() {
		return eyeColor;
	}
	public TerrariaColor getShirtColor() {
		return shirtColor;
	}
	public TerrariaColor getUnderShirtColor() {
		return underShirtColor;
	}
	public TerrariaColor getPantsColor() {
		return pantsColor;
	}
	public TerrariaColor getShoeColor() {
		return shoeColor;
	}
	public void setSkinVariant(byte skinVariant) {
		this.skinVariant = skinVariant;
	}
	public void setHair(byte hair) {
		this.hair = hair;
	}
	public void setHairDye(byte hairDye) {
		this.hairDye = hairDye;
	}
	public void setHideVisuals(byte hideVisuals) {
		this.hideVisuals = hideVisuals;
	}
	public void setHideVisuals2(byte hideVisuals2) {
		this.hideVisuals2 = hideVisuals2;
	}
	public void setHideMisc(byte hideMisc) {
		this.hideMisc = hideMisc;
	}
	public void setHairColor(TerrariaColor hairColor) {
		this.hairColor = hairColor;
	}
	public void setSkinColor(TerrariaColor skinColor) {
		this.skinColor = skinColor;
	}
	public void setEyeColor(TerrariaColor eyeColor) {
		this.eyeColor = eyeColor;
	}
	public void setShirtColor(TerrariaColor shirtColor) {
		this.shirtColor = shirtColor;
	}
	public void setUnderShirtColor(TerrariaColor underShirtColor) {
		this.underShirtColor = underShirtColor;
	}
	public void setPantsColor(TerrariaColor pantsColor) {
		this.pantsColor = pantsColor;
	}
	public void setShoeColor(TerrariaColor shoeColor) {
		this.shoeColor = shoeColor;
	}

	public void load(TerrariaPacketPlayerInfo packet) {
		skinVariant = packet.getSkinVariant();
		hair = packet.getHair();
		hairDye = packet.getHairDye();
		hideVisuals = packet.getHideVisuals();
		hideVisuals2 = packet.getHideVisuals2();
		hideMisc = packet.getHideMisc();
		hairColor = packet.getHairColor();
		skinColor = packet.getSkinColor();
		eyeColor = packet.getEyeColor();
		shirtColor = packet.getShirtColor();
		underShirtColor = packet.getUnderShirtColor();
		pantsColor = packet.getPantsColor();
		shoeColor = packet.getShoeColor();
		difficulty = Difficulty.getFromId(packet.getDifficulty());
	}
	
	public void clone(PlayerInfo info){
		skinVariant = info.getSkinVariant();
		hair = info.getHair();
		hairDye = info.getHairDye();
		hideVisuals = info.getHideVisuals();
		hideVisuals2 = info.getHideVisuals2();
		hideMisc = info.getHideMisc();
		hairColor = info.getHairColor();
		skinColor = info.getSkinColor();
		eyeColor = info.getEyeColor();
		shirtColor = info.getShirtColor();
		underShirtColor = info.getUnderShirtColor();
		pantsColor = info.getPantsColor();
		shoeColor = info.getShoeColor();
		difficulty = info.getDifficulty();
	}
	
}
