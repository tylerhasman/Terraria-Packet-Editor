var enabled = false;
var Color = Java.type("me.tyler.terraria.TerrariaColor");
var MathUtil = Java.type("me.tyler.terraria.tools.MathUtil");
var Random = Java.type("java.util.Random");

var command_name = "vac";
var command_description = "Pull dropped items towards you";

var MODE_NORMAL = 0;
var MODE_LOCATION = 1;

var x = 0;
var y = 0;
var mode = 0;
var rng = new Random();

function packet_type(){
	return UPDATE_PLAYER;
}

function do_cycle(proxy){
	
	if(!enabled){
		return;	
	}
	
	items = proxy.getDroppedItems();
	iterator = items.iterator();
	
	while(iterator.hasNext()){
		
		item = iterator.next();
		
		if(MathUtil.distance(item.getX(), item.getY(), x, y) > 100){
			item.teleport(x + (rng.nextInt(100) - rng.nextInt(100)) / 2, y);
		}

	}
	
}

function send(packet, proxy){
	if(mode == MODE_NORMAL){
		x = packet.getPositionX();
		y = packet.getPositionY();
	}
}


function chat_command(proxy, command, args){

	if(command.equalsIgnoreCase("vac")){
		
		if(args.length == 0){
			mode = MODE_NORMAL;
		}else if(args.length == 1){
			if(args[0].equalsIgnoreCase("normal")){
				mode = MODE_NORMAL;
			}else if(args[0].equalsIgnoreCase("pos")){
				mode = MODE_LOCATION;
				x = proxy.getThePlayer().getX();
				y = proxy.getThePlayer().getY();
			}else{
				proxy.getThePlayer().sendMessage(Color.RED, "Unknown mode "+args[0]);
				return;
			}
		}
		
		enabled = !enabled;
		
		if(enabled){
			proxy.getThePlayer().sendMessage(Color.GREEN, "VAC enabled!");
		}else{
			proxy.getThePlayer().sendMessage(Color.RED, "VAC disabled!");
		}
		
		return true;
	
	}

}
