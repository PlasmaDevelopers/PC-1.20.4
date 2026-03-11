/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.tags.EntityTypeTags;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ 
/*    */ public class EntityTypeTagsProvider extends IntrinsicHolderTagsProvider<EntityType<?>> {
/*    */   public EntityTypeTagsProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 13 */     super($$0, Registries.ENTITY_TYPE, $$1, $$0 -> $$0.builtInRegistryHolder().key());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addTags(HolderLookup.Provider $$0) {
/* 18 */     tag(EntityTypeTags.SKELETONS).add((EntityType<?>[])new EntityType[] { EntityType.SKELETON, EntityType.STRAY, EntityType.WITHER_SKELETON, EntityType.SKELETON_HORSE });
/* 19 */     tag(EntityTypeTags.ZOMBIES).add((EntityType<?>[])new EntityType[] { EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN, EntityType.ZOGLIN, EntityType.DROWNED, EntityType.HUSK });
/* 20 */     tag(EntityTypeTags.RAIDERS).add((EntityType<?>[])new EntityType[] { EntityType.EVOKER, EntityType.PILLAGER, EntityType.RAVAGER, EntityType.VINDICATOR, EntityType.ILLUSIONER, EntityType.WITCH });
/* 21 */     tag(EntityTypeTags.UNDEAD).addTag(EntityTypeTags.SKELETONS).addTag(EntityTypeTags.ZOMBIES).add(EntityType.WITHER).add(EntityType.PHANTOM);
/* 22 */     tag(EntityTypeTags.BEEHIVE_INHABITORS).add(EntityType.BEE);
/* 23 */     tag(EntityTypeTags.ARROWS).add((EntityType<?>[])new EntityType[] { EntityType.ARROW, EntityType.SPECTRAL_ARROW });
/* 24 */     tag(EntityTypeTags.IMPACT_PROJECTILES).addTag(EntityTypeTags.ARROWS).add(EntityType.FIREWORK_ROCKET).add(new EntityType[] { EntityType.SNOWBALL, EntityType.FIREBALL, EntityType.SMALL_FIREBALL, EntityType.EGG, EntityType.TRIDENT, EntityType.DRAGON_FIREBALL, EntityType.WITHER_SKULL });
/* 25 */     tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add((EntityType<?>[])new EntityType[] { EntityType.RABBIT, EntityType.ENDERMITE, EntityType.SILVERFISH, EntityType.FOX });
/* 26 */     tag(EntityTypeTags.AXOLOTL_HUNT_TARGETS).add((EntityType<?>[])new EntityType[] { EntityType.TROPICAL_FISH, EntityType.PUFFERFISH, EntityType.SALMON, EntityType.COD, EntityType.SQUID, EntityType.GLOW_SQUID, EntityType.TADPOLE });
/* 27 */     tag(EntityTypeTags.AXOLOTL_ALWAYS_HOSTILES).add((EntityType<?>[])new EntityType[] { EntityType.DROWNED, EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN });
/* 28 */     tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add((EntityType<?>[])new EntityType[] { EntityType.STRAY, EntityType.POLAR_BEAR, EntityType.SNOW_GOLEM, EntityType.WITHER });
/* 29 */     tag(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES).add((EntityType<?>[])new EntityType[] { EntityType.STRIDER, EntityType.BLAZE, EntityType.MAGMA_CUBE });
/* 30 */     tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER).addTag(EntityTypeTags.UNDEAD).add(new EntityType[] { EntityType.AXOLOTL, EntityType.FROG, EntityType.GUARDIAN, EntityType.ELDER_GUARDIAN, EntityType.TURTLE, EntityType.GLOW_SQUID, EntityType.COD, EntityType.PUFFERFISH, EntityType.SALMON, EntityType.SQUID, EntityType.TROPICAL_FISH, EntityType.TADPOLE, EntityType.ARMOR_STAND });
/* 31 */     tag(EntityTypeTags.FROG_FOOD).add((EntityType<?>[])new EntityType[] { EntityType.SLIME, EntityType.MAGMA_CUBE });
/* 32 */     tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add((EntityType<?>[])new EntityType[] { EntityType.IRON_GOLEM, EntityType.SNOW_GOLEM, EntityType.SHULKER, EntityType.ALLAY, EntityType.BAT, EntityType.BEE, EntityType.BLAZE, EntityType.CAT, EntityType.CHICKEN, EntityType.GHAST, EntityType.PHANTOM, EntityType.MAGMA_CUBE, EntityType.OCELOT, EntityType.PARROT, EntityType.WITHER });
/* 33 */     tag(EntityTypeTags.DISMOUNTS_UNDERWATER).add((EntityType<?>[])new EntityType[] { EntityType.CAMEL, EntityType.CHICKEN, EntityType.DONKEY, EntityType.HORSE, EntityType.LLAMA, EntityType.MULE, EntityType.PIG, EntityType.RAVAGER, EntityType.SPIDER, EntityType.STRIDER, EntityType.TRADER_LLAMA, EntityType.ZOMBIE_HORSE });
/* 34 */     tag(EntityTypeTags.NON_CONTROLLING_RIDER).add((EntityType<?>[])new EntityType[] { EntityType.SLIME, EntityType.MAGMA_CUBE });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\EntityTypeTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */