var Random = Java.type("java.util.Random");
var HashMap = Java.type("java.util.HashMap");
var Calendar = Java.type("java.util.Calendar");

var enabled = Calendar.getInstance().get(Calendar.MONTH) == 10;

var rng = new Random();

var equipped = new HashMap();

var costumes = [
[1772, 1773], //Princess Outfit
[1777, 1778], //Bride of Frankenstein
[1749, 1760, 1751], //Cat Costume
[1752, 1753], //Ghost Costume
[1767, 1768, 1769], //Leprechaun
[1779, 1780, 1781], //Karate Tortoise *wink* *wink* *nudge* *nudge*
[1819, 1820], //Reaper
[1766, 1775, 1776], //Witch
[1754, 1755, 1756] //Pumpkin
];

var slots = [
59, //Helmet
60, //Chestplate
61 //Greaves
];

var vanity = [
69, //Helmet
70, //Chestplate
71 //Greaves
];

function packet_type(){
	return INVENTORY_SLOT;
}

function recieve(packet, proxy){

	if(!enabled){
		return;
	}


	var id;

	var player = proxy.getPlayer(packet.getPlayerId());
	
	if(equipped.containsKey(player.getName())){
		id = equipped.get(player.getName());
	}else{
		id = rng.nextInt(costumes.length);
		equipped.put(player.getName(), id);
	}
	
	var costume = costumes[id];
	
	for(i = 0; i < slots.length;i++){
		if(slots[i] == packet.getSlot()){
			packet.getPayloadBuffer(5).putShort(costume[i]);
			break;
		}else if(vanity[i] == packet.getSlot()){
			packet.getPayloadBuffer(5).putShort(costume[i]);
			break;
		}else if(packet.getSlot() >= 79 && packet.getSlot <= 88){
			packet.getPayloadBuffer(5).putShort(2884);
			break;
		}
	}
	
}