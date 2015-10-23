package me.tyler.terraria.packets;

import java.net.Socket;
import java.util.Random;

import me.tyler.terraria.Cheats;
import me.tyler.terraria.Proxy;
import me.tyler.terraria.TerrariaItemDrop;

public class TerrariaPacketUpdateItemOwner extends TerrariaPacket {

	public TerrariaPacketUpdateItemOwner(byte type, byte[] payload) {
		super(type, payload);
	}

	public short getItemId() {
		return getPayloadBuffer().getShort();
	}

	public byte getOwner() {
		return getPayloadBuffer(2).get();
	}

	@Override
	public boolean onReceive(Proxy proxy, Socket client) {

		TerrariaItemDrop item = proxy.getDroppedItem(getItemId());

		if (item != null) {

			if (Cheats.VAC_ENABLED) {

				float x3 = (float) Math.pow(proxy.getThePlayer().getX() - item.getX(), 2);
				float y3 = (float) Math.pow(proxy.getThePlayer().getY() - item.getY(), 2);

				float distance = (float) Math.sqrt(x3 + y3);

				if (distance > 200) {
					TerrariaPacket packet = TerrariaPacketUpdateItemDrop.getItemDropPacket(getItemId(), proxy.getThePlayer().getX(), proxy.getThePlayer().getY() - 100, item.getX(), item.getY(), item.getAmount(), item.getPrefix(), item.getDelay(), item.getItemType());

					proxy.sendPacketToServer(packet);

				}
			} else if (Cheats.VAC_POS_ENABLED) {

				float x3 = (float) Math.pow(Cheats.VAC_POS_X - item.getX(), 2);
				float y3 = (float) Math.pow(Cheats.VAC_POS_Y - item.getY(), 2);

				float distance = (float) Math.sqrt(x3 + y3);

				if (distance > 1000) {

					Random random = new Random();

					TerrariaPacket packet = TerrariaPacketUpdateItemDrop.getItemDropPacket(getItemId(), Cheats.VAC_POS_X + random.nextInt(700) - 700, Cheats.VAC_POS_Y - 100, item.getX(), item.getY(), item.getAmount(), item.getPrefix(), item.getDelay(), item.getItemType());

					proxy.sendPacketToServer(packet);

				}

			}
		}

		return super.onReceive(proxy, client);
	}

}
