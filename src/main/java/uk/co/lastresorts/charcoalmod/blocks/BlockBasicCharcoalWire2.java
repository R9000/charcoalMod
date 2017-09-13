package uk.co.lastresorts.charcoalmod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityBasicCharcoalWire2;

public class BlockBasicCharcoalWire2 extends BlockCharcoalWire2 {

	protected BlockBasicCharcoalWire2(String unlocalizedName, Material material) {
		super(unlocalizedName, material);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityBasicCharcoalWire2(world);
	}
}
