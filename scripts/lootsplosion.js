var enabled = false;
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var CombatText = Java.type("me.tyler.terraria.packets.TerrariaPacketCombatText");
var Random = Java.type("java.util.Random");
var Data = Java.type("me.tyler.terraria.TerrariaData");

var command_name = "lootsplosion";
var command_description = "Make loot explode from a single point!";

var x = 0;
var y = 0;
var countdown = 0;

var rng = new Random();

function packet_type(){
}

function do_cycle(proxy){
	
	if(!enabled){
		return;	
	}
	
	if(countdown > 0){
		if(countdown % 2 == 0){
			var ct_pck = new CombatText(x, y, Color.GREEN, (countdown / 2));
			
			proxy.sendPacketToClient(ct_pck);
		}
		countdown--;
		return;
	}
	
	for(i = 0; i < 50; i++){
		proxy.dropItem(x, y, 20, Data.ITEMS.getRandomKey());
	}
	
}

function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("lootsplosion")){
	
		enabled = !enabled;
		
		if(enabled){
			proxy.getThePlayer().sendMessage(Color.GREEN, "Lootsplosion enabled!");
			countdown = 10;
			x = proxy.getThePlayer().getX();
			y = proxy.getThePlayer().getY() - 100;
		}else{
			proxy.getThePlayer().sendMessage(Color.RED, "Lootsplosion disabled!");
		}
		
		return true;
	
	}

}
