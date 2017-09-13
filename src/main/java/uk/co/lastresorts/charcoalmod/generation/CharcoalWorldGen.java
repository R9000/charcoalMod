package uk.co.lastresorts.charcoalmod.generation;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import uk.co.lastresorts.charcoalmod.blocks.CMBlocks;

public class CharcoalWorldGen implements IWorldGenerator{

	private WorldGenerator gen_smore_ore; //Generates Smore Ore, only used in Overworld.
	
	public CharcoalWorldGen() {
		this.gen_smore_ore = new WorldGenMinable(CMBlocks.smoreOre, 6);
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
	    case 0: //Overworld
	    	generateSurface(world, random, chunkX, chunkZ);
	    	this.runGenerator(this.gen_smore_ore, world, random, chunkX, chunkZ, 10, 0, 64);
	        break;
	    case -1: //Nether
	    	generateNether(world, random, chunkX, chunkZ);
	        break;
	    case 1: //End
	    	generateEnd(world, random, chunkX, chunkZ);
	        break;
	    }
		
	}

	private void generateEnd(World world, Random random, int x, int z) {
		// TODO Auto-generated method stub
		
	}

	private void generateNether(World world, Random random, int x, int z) {
		// TODO Auto-generated method stub
		
	}

	private void generateSurface(World world, Random random, int x, int z) {
		for(int i = 0; i < 20; i++)
		{
			int xCoord = x + random.nextInt(16);
			int yCoord = random.nextInt(90);
			int zCoord = z + random.nextInt(16);
			new WorldGenCharredTree(false, 5, 0, 0, false).generate(world, random, xCoord, yCoord, zCoord);
		}
		
	}
	
	//Generic ore generator method, courtesy of _Bedrock_Miner_.
	private void runGenerator(WorldGenerator generator, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight) {
	    if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
	        throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

	    int heightDiff = maxHeight - minHeight + 1;
	    for (int i = 0; i < chancesToSpawn; i ++) {	//Iron is at 20. Can be found in net.minecraft.world.gen.ChunkProviderSettings$Factory.
	        int x = chunk_X * 16 + rand.nextInt(16);
	        int y = minHeight + rand.nextInt(heightDiff);
	        int z = chunk_Z * 16 + rand.nextInt(16);
	        generator.generate(world, rand, x, y, z);
	    }
	}

}
