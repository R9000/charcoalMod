package uk.co.lastresorts.charcoalmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import uk.co.lastresorts.charcoalmod.biome.BiomeRegistry;
import uk.co.lastresorts.charcoalmod.blocks.CMBlocks;
import uk.co.lastresorts.charcoalmod.client.interfaces.GuiHandler;
import uk.co.lastresorts.charcoalmod.entities.CMEntities;
import uk.co.lastresorts.charcoalmod.generation.CharcoalWorldGen;
import uk.co.lastresorts.charcoalmod.items.CMItems;
import uk.co.lastresorts.charcoalmod.network.ButtonPacketC2S;
import uk.co.lastresorts.charcoalmod.network.ButtonPacketHandlerC2S;
import uk.co.lastresorts.charcoalmod.network.TextPacketC2S;
import uk.co.lastresorts.charcoalmod.network.TextPacketHandlerC2S;
import uk.co.lastresorts.charcoalmod.proxies.CommonProxy;

@Mod(modid = CharcoalMod.MODID, version = CharcoalMod.VERSION)
public class CharcoalMod
{
    public static final String MODID = "charcoalmod";
    public static final String VERSION = "0.7.2-1.7.10";
    public static final String MODNAME = "The Charcoal Mod";
    
    @Instance(MODID)
	public static CharcoalMod instance;

	@SidedProxy(clientSide = "uk.co.lastresorts.charcoalmod.proxies.ClientProxy", serverSide = "uk.co.lastresorts.charcoalmod.proxies.CommonProxy")
	public static CommonProxy proxy;
	public static SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel("charChannel");
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		CMItems.init();
		CMItems.registerItems();
		CMBlocks.init();
		CMItems.registerRecipes();
		CMBlocks.registerTileEntities();
		CMBlocks.registerRecipes();
		
		CMEntities.init();
		
		GameRegistry.registerWorldGenerator(new CharcoalWorldGen(), 0);
		GameRegistry.registerFuelHandler(new CMFuelHandler());
		BiomeRegistry.initializeBiomes();
		BiomeRegistry.registerBiomes();
		new GuiHandler();
		wrapper.registerMessage(ButtonPacketHandlerC2S.class, ButtonPacketC2S.class, 0, Side.SERVER);
		wrapper.registerMessage(TextPacketHandlerC2S.class, TextPacketC2S.class, 1, Side.SERVER);
		
		proxy.initRenderers();
		proxy.initSounds();
		System.out.println("Charcoal Mod successfully initialized.");
	}
	
	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent event) {
	
	}
}
