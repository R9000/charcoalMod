package uk.co.lastresorts.charcoalmod;

import net.minecraft.item.ItemStack;

public interface ICharcoalEnergyStorage {

		//Charges the ICharcoalEnergyUser.
		public boolean charge(ItemStack stack, int chargeAmount);
		
		//Discharges the ICharcoalEnergyUser.
		public boolean discharge(ItemStack stack, int dischargeAmount);
}
