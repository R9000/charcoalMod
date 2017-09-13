package uk.co.lastresorts.charcoalmod.biome;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class BiomeRegistry {
	
	public static BiomeGenBase biomeCharcoal;
	
	public static void initializeBiomes()
	{	//TODO: Put ID in Info class for easier use.
		biomeCharcoal = new BiomeGenCharcoal(128).setBiomeName("Charcoal Wastelands");
	}
	
	public static void registerBiomes()
	{
		BiomeDictionary.registerBiomeType(biomeCharcoal, Type.WASTELAND);
		BiomeManager.addSpawnBiome(biomeCharcoal);
		//	BiomeEntry(biomeGen, weight)
		BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(biomeCharcoal, 10));
	}
}
