var Prefix = Java.type("me.tyler.terraria.Prefix");
var Data = Java.type("me.tyler.terraria.TerrariaData");

function packet_type(){
	return NPC_SHOP_ITEM;
}

function recieve(packet, proxy){

	print(packet.getSlot()+" "+Data.ITEMS.getValue(packet.getItemType()));

}