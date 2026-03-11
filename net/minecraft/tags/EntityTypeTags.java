/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class EntityTypeTags
/*    */ {
/* 11 */   public static final TagKey<EntityType<?>> SKELETONS = create("skeletons");
/* 12 */   public static final TagKey<EntityType<?>> ZOMBIES = create("zombies");
/* 13 */   public static final TagKey<EntityType<?>> RAIDERS = create("raiders");
/* 14 */   public static final TagKey<EntityType<?>> UNDEAD = create("undead");
/* 15 */   public static final TagKey<EntityType<?>> BEEHIVE_INHABITORS = create("beehive_inhabitors");
/* 16 */   public static final TagKey<EntityType<?>> ARROWS = create("arrows");
/* 17 */   public static final TagKey<EntityType<?>> IMPACT_PROJECTILES = create("impact_projectiles");
/* 18 */   public static final TagKey<EntityType<?>> POWDER_SNOW_WALKABLE_MOBS = create("powder_snow_walkable_mobs");
/* 19 */   public static final TagKey<EntityType<?>> AXOLOTL_ALWAYS_HOSTILES = create("axolotl_always_hostiles");
/* 20 */   public static final TagKey<EntityType<?>> AXOLOTL_HUNT_TARGETS = create("axolotl_hunt_targets");
/* 21 */   public static final TagKey<EntityType<?>> FREEZE_IMMUNE_ENTITY_TYPES = create("freeze_immune_entity_types");
/* 22 */   public static final TagKey<EntityType<?>> FREEZE_HURTS_EXTRA_TYPES = create("freeze_hurts_extra_types");
/* 23 */   public static final TagKey<EntityType<?>> CAN_BREATHE_UNDER_WATER = create("can_breathe_under_water");
/* 24 */   public static final TagKey<EntityType<?>> FROG_FOOD = create("frog_food");
/* 25 */   public static final TagKey<EntityType<?>> FALL_DAMAGE_IMMUNE = create("fall_damage_immune");
/* 26 */   public static final TagKey<EntityType<?>> DISMOUNTS_UNDERWATER = create("dismounts_underwater");
/* 27 */   public static final TagKey<EntityType<?>> NON_CONTROLLING_RIDER = create("non_controlling_rider");
/* 28 */   public static final TagKey<EntityType<?>> DEFLECTS_ARROWS = create("deflects_arrows");
/* 29 */   public static final TagKey<EntityType<?>> DEFLECTS_TRIDENTS = create("deflects_tridents");
/* 30 */   public static final TagKey<EntityType<?>> CAN_TURN_IN_BOATS = create("can_turn_in_boats");
/*    */   
/*    */   private static TagKey<EntityType<?>> create(String $$0) {
/* 33 */     return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\EntityTypeTags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */