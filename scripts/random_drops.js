var ItemPacket = Java.type("me.tyler.terraria.packets.TerrariaPacketUpdateItemDrop");
var Random  = Java.type("java.util.Random");
var nextId = 200;

var Color = Java.type("me.tyler.terraria.TerrariaColor");

var rng = new Random();

var command_name = "randomitems";
var command_description = "spawn a ton of random items";

function packet_type(){
	return PROJECTILE_UPDATE;
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("randomitems")){

		var player = proxy.getThePlayer();
		
		if(args.length < 1){
			player.sendMessage(Color.RED, "Error! You need to enter how many items to drop. Example: -randomitems 50");
			return true;
		}
	
		var amount = args[0];
	
		for(var i = 0; i < amount;i++){
			var item_packet = ItemPacket.getItemDropPacket(nextId++, player.getX(), player.getY(), rng.nextFloat() * 3, rng.nextFloat() * 3, 1, 0, 0, rng.nextInt(1000));
		
			proxy.sendPacketToServer(item_packet);
		}
		
		player.sendMessage(Color.GREEN, "Dropped "+amount+" items");
		
		nextId = 200;
		
		return true;
	
	}

}