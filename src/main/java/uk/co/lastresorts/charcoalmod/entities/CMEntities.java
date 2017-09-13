package uk.co.lastresorts.charcoalmod.entities;


import cpw.mods.fml.common.registry.EntityRegistry;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.entities.particles.EntityCharcoalPoisonFX;
import uk.co.lastresorts.charcoalmod.entities.particles.EntityCharcoalWaterFX;

public class CMEntities {

	public static void init() {
		EntityRegistry.registerModEntity(EntityCharcoalSlime.class, "EntityCharcoalSlime", 1, CharcoalMod.instance, 80, 3, false);
		EntityRegistry.registerModEntity(EntityCharcoalPoisonFX.class, "EntityCharcoalPoisonFX", 2, CharcoalMod.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityCharcoalWaterFX.class, "EntityCharcoalWaterFX", 3, CharcoalMod.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityCharcoalProjectile.class, "EntityCharcoalProjectile", 4, CharcoalMod.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityWaterProjectile.class, "EntityWaterProjectile", 5, CharcoalMod.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityPoisonProjectile.class, "EntityPoisonProjectile", 6, CharcoalMod.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityFireProjectile.class, "EntityFireProjectile", 7, CharcoalMod.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityTeleportProjectile.class, "EntityTeleportProjectile", 8, CharcoalMod.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityPlantProjectile.class, "EntityPlantProjectile", 9, CharcoalMod.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityAirProjectile.class, "EntityAirProjectile", 10, CharcoalMod.instance, 64, 10, true);
		EntityRegistry.registerModEntity(EntityAshes.class, "EntityAshes", 11, CharcoalMod.instance, 64, 10, true);
	}
	
}
