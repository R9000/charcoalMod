package uk.co.lastresorts.charcoalmod.proxies;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import uk.co.lastresorts.charcoalmod.blocks.CMBlocks;
import uk.co.lastresorts.charcoalmod.client.RenderBattery;
import uk.co.lastresorts.charcoalmod.client.RenderCharcoalProjectile;
import uk.co.lastresorts.charcoalmod.client.RenderCharcoalWire;
import uk.co.lastresorts.charcoalmod.client.RenderFlare;
import uk.co.lastresorts.charcoalmod.client.TileEntityBaseWireRenderer;
import uk.co.lastresorts.charcoalmod.client.TileEntityBatteryRenderer;
import uk.co.lastresorts.charcoalmod.client.TileEntityChargerBaseRenderer;
import uk.co.lastresorts.charcoalmod.client.TileEntityFlareRenderer;
import uk.co.lastresorts.charcoalmod.entities.EntityAirProjectile;
import uk.co.lastresorts.charcoalmod.entities.EntityCharcoalProjectile;
import uk.co.lastresorts.charcoalmod.entities.EntityFireProjectile;
import uk.co.lastresorts.charcoalmod.entities.EntityPlantProjectile;
import uk.co.lastresorts.charcoalmod.entities.EntityPoisonProjectile;
import uk.co.lastresorts.charcoalmod.entities.EntityTeleportProjectile;
import uk.co.lastresorts.charcoalmod.entities.EntityWaterProjectile;
import uk.co.lastresorts.charcoalmod.entities.particles.EntityCharcoalPoisonFX;
import uk.co.lastresorts.charcoalmod.entities.particles.EntityCharcoalWaterFX;
import uk.co.lastresorts.charcoalmod.items.CMItems;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityBasicCharcoalWire2;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityBatteryBase;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityCharger;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityFlare;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityT1Battery;

public class ClientProxy extends CommonProxy {

	@Override
	public void initSounds() {
		//new SoundHandler();
	}

	@Override
	public void initRenderers() {
		//RenderManager renderer = Minecraft.getMinecraft().getRenderManager();
		RenderingRegistry.registerEntityRenderingHandler(EntityCharcoalPoisonFX.class, new RenderCharcoalProjectile());
		RenderingRegistry.registerEntityRenderingHandler(EntityCharcoalWaterFX.class, new RenderCharcoalProjectile());
		RenderingRegistry.registerEntityRenderingHandler(EntityCharcoalProjectile.class, new RenderCharcoalProjectile());
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterProjectile.class, new RenderCharcoalProjectile());
		RenderingRegistry.registerEntityRenderingHandler(EntityPoisonProjectile.class, new RenderCharcoalProjectile());
		RenderingRegistry.registerEntityRenderingHandler(EntityFireProjectile.class, new RenderCharcoalProjectile());
		RenderingRegistry.registerEntityRenderingHandler(EntityTeleportProjectile.class, new RenderCharcoalProjectile());
		RenderingRegistry.registerEntityRenderingHandler(EntityPlantProjectile.class, new RenderCharcoalProjectile());
		RenderingRegistry.registerEntityRenderingHandler(EntityAirProjectile.class, new RenderCharcoalProjectile());
		CMItems.registerRenders();
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCharger.class, new TileEntityChargerBaseRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBasicCharcoalWire2.class, new TileEntityBaseWireRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBatteryBase.class, new TileEntityBatteryRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CMBlocks.basicWire2), new RenderCharcoalWire(new TileEntityBaseWireRenderer(), new TileEntityBasicCharcoalWire2()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CMBlocks.storageBattery), new RenderBattery(new TileEntityBatteryRenderer(), new TileEntityT1Battery()));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(CMBlocks.flare), new RenderFlare(new TileEntityFlareRenderer(), new TileEntityFlare()));
	}	
	
}
