package uk.co.lastresorts.charcoalmod.biome;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import uk.co.lastresorts.charcoalmod.blocks.CMBlocks;
import uk.co.lastresorts.charcoalmod.entities.EntityCharcoalSlime;
import uk.co.lastresorts.charcoalmod.generation.WorldGenCharredTree;

public class BiomeGenCharcoal extends BiomeGenBase{

	public BiomeGenCharcoal(int id) {
		super(id);
		this.theBiomeDecorator.generateLakes = true;
		this.theBiomeDecorator.coalGen = new WorldGenMinable(Blocks.coal_ore, 0);
		
		this.topBlock = CMBlocks.charcoalBlock;
		this.fillerBlock = CMBlocks.charcoalBlock;
		this.enableRain = false;
		this.waterColorMultiplier = 0x333311;
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		//	spawnListEntry(EntityToSpawn.class, weighted probability, groupSizeMin, groupSizeMax)
		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityCharcoalSlime.class, 10, 1, 2));
		
	}

	public void decorate(World world, Random random, int x, int z){
		for(int i = 0; i < 20; i++)
		{
			int xCoord = x + random.nextInt(16);
			int yCoord = random.nextInt(90);
			int zCoord = z + random.nextInt(16);
			new WorldGenCharredTree(false, 5, 0, 0, false).generate(world, random, xCoord, yCoord, zCoord);
		}
		super.decorate(world, random, x, z);
    }
}
