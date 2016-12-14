var ItemPacket = Java.type("me.tyler.terraria.packets.TerrariaPacketUpdateItemDrop");
var DestroyProjectile = Java.type("me.tyler.terraria.packets.TerrariaPacketDestroyProjectile");
var Random  = Java.type("java.util.Random");

var enabled = false;
var Color = Java.type("me.tyler.terraria.TerrariaColor");

var rng = new Random();

var command_name = "itemlauncher";
var command_description = "shoot items instead of bullets";

function packet_type(){
	return PROJECTILE_UPDATE;
}

function send(packet, proxy){
	if(enabled){
		
		var destroy_packet = new DestroyProjectile(packet.getProjectileId(), packet.getOwner());
		var item_packet = ItemPacket.getItemDropPacket(400, packet.getX() + packet.getVelocityX() * 3, packet.getY() + packet.getVelocityY() * 3, packet.getVelocityX() , packet.getVelocityY(), 1, 0, 0, rng.nextInt(1000));
			
		proxy.sendPacketToClient(destroy_packet);
		proxy.sendPacketToServer(item_packet);
		
		return false;
	}
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("itemlauncher")){
	
		enabled = !enabled;
		if(enabled){
			proxy.getThePlayer().sendMessage("Item launcher enabled!");
		}else{
			proxy.getThePlayer().sendMessage("Item launcher disabled!");
		}
		
		return true;
	
	}

}