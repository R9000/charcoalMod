package uk.co.lastresorts.charcoalmod.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityConfigurableSides;

public abstract class BlockConfigurableSides extends BlockContainer {

	protected BlockConfigurableSides(Material material) {
		super(material);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == Items.stick) {
				TileEntity te = world.getTileEntity(x, y, z);
				if(te != null && te instanceof TileEntityConfigurableSides) {
					TileEntityConfigurableSides config = (TileEntityConfigurableSides)te;
					config.cycleSide(side);
				}
			}
		}
		//TODO: Put inside stick part? Does an update need to be triggered when not changing state?
		this.onNeighborBlockChange(world, x, y, z, this);
		return true;
	}
	
	/*
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventNumber, int eventArgument)
    {
        // Perform the event on the given location and get the tile entity there
        super.onBlockEventReceived(world, x, y, z, eventNumber, eventArgument);
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        // Make sure the tile entity is valid, if so, let it receive the event
        if (tileEntity == null)
            return false;
        return tileEntity.receiveClientEvent(eventNumber, eventArgument);
    }
    */
}
