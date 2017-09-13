package uk.co.lastresorts.charcoalmod;

public interface ICharcoalEnergyGiver {
	
	//Charges the ICharcoalEnergyUser.
	public boolean charge(int chargeAmount);
	
	//Discharges the ICharcoalEnergyUser.
	public boolean discharge(int dischargeAmount);
	
	public boolean canCharge(int chargeAmount);
	
	public boolean canDischarge(int dischargeAmount);
	
	public void addReceiverToList(BlockPos pos);
	
	public void removeReceiverFromList(BlockPos pos);
	
	public boolean getCanAddTransmit(BlockPos wirePos);
}
