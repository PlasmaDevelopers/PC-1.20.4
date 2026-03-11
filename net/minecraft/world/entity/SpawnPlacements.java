/*     */ package net.minecraft.world.entity;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.ambient.Bat;
/*     */ import net.minecraft.world.entity.animal.Animal;
/*     */ import net.minecraft.world.entity.animal.Fox;
/*     */ import net.minecraft.world.entity.animal.MushroomCow;
/*     */ import net.minecraft.world.entity.animal.Ocelot;
/*     */ import net.minecraft.world.entity.animal.Parrot;
/*     */ import net.minecraft.world.entity.animal.PolarBear;
/*     */ import net.minecraft.world.entity.animal.Rabbit;
/*     */ import net.minecraft.world.entity.animal.TropicalFish;
/*     */ import net.minecraft.world.entity.animal.Turtle;
/*     */ import net.minecraft.world.entity.animal.WaterAnimal;
/*     */ import net.minecraft.world.entity.animal.Wolf;
/*     */ import net.minecraft.world.entity.animal.axolotl.Axolotl;
/*     */ import net.minecraft.world.entity.animal.frog.Frog;
/*     */ import net.minecraft.world.entity.animal.goat.Goat;
/*     */ import net.minecraft.world.entity.animal.horse.SkeletonHorse;
/*     */ import net.minecraft.world.entity.animal.horse.ZombieHorse;
/*     */ import net.minecraft.world.entity.monster.Drowned;
/*     */ import net.minecraft.world.entity.monster.Endermite;
/*     */ import net.minecraft.world.entity.monster.Ghast;
/*     */ import net.minecraft.world.entity.monster.Guardian;
/*     */ import net.minecraft.world.entity.monster.Husk;
/*     */ import net.minecraft.world.entity.monster.MagmaCube;
/*     */ import net.minecraft.world.entity.monster.Monster;
/*     */ import net.minecraft.world.entity.monster.PatrollingMonster;
/*     */ import net.minecraft.world.entity.monster.Silverfish;
/*     */ import net.minecraft.world.entity.monster.Slime;
/*     */ import net.minecraft.world.entity.monster.Stray;
/*     */ import net.minecraft.world.entity.monster.Strider;
/*     */ import net.minecraft.world.entity.monster.ZombifiedPiglin;
/*     */ import net.minecraft.world.entity.monster.hoglin.Hoglin;
/*     */ import net.minecraft.world.entity.monster.piglin.Piglin;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SpawnPlacements
/*     */ {
/*     */   private static class Data
/*     */   {
/*     */     final Heightmap.Types heightMap;
/*     */     final SpawnPlacements.Type placement;
/*     */     final SpawnPlacements.SpawnPredicate<?> predicate;
/*     */     
/*     */     public Data(Heightmap.Types $$0, SpawnPlacements.Type $$1, SpawnPlacements.SpawnPredicate<?> $$2) {
/*  58 */       this.heightMap = $$0;
/*  59 */       this.placement = $$1;
/*  60 */       this.predicate = $$2;
/*     */     }
/*     */   }
/*     */   
/*  64 */   private static final Map<EntityType<?>, Data> DATA_BY_TYPE = Maps.newHashMap();
/*     */   
/*     */   private static <T extends Mob> void register(EntityType<T> $$0, Type $$1, Heightmap.Types $$2, SpawnPredicate<T> $$3) {
/*  67 */     Data $$4 = DATA_BY_TYPE.put($$0, new Data($$2, $$1, $$3));
/*  68 */     if ($$4 != null) {
/*  69 */       throw new IllegalStateException("Duplicate registration for type " + BuiltInRegistries.ENTITY_TYPE.getKey($$0));
/*     */     }
/*     */   }
/*     */   
/*     */   static {
/*  74 */     register(EntityType.AXOLOTL, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Axolotl::checkAxolotlSpawnRules);
/*  75 */     register(EntityType.COD, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
/*  76 */     register(EntityType.DOLPHIN, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
/*  77 */     register(EntityType.DROWNED, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Drowned::checkDrownedSpawnRules);
/*  78 */     register(EntityType.GUARDIAN, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Guardian::checkGuardianSpawnRules);
/*  79 */     register(EntityType.PUFFERFISH, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
/*  80 */     register(EntityType.SALMON, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
/*  81 */     register(EntityType.SQUID, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
/*  82 */     register(EntityType.TROPICAL_FISH, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TropicalFish::checkTropicalFishSpawnRules);
/*     */     
/*  84 */     register(EntityType.BAT, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Bat::checkBatSpawnRules);
/*  85 */     register(EntityType.BLAZE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
/*  86 */     register(EntityType.CAVE_SPIDER, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/*  87 */     register(EntityType.CHICKEN, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
/*  88 */     register(EntityType.COW, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
/*  89 */     register(EntityType.CREEPER, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/*  90 */     register(EntityType.DONKEY, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
/*  91 */     register(EntityType.ENDERMAN, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/*  92 */     register(EntityType.ENDERMITE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Endermite::checkEndermiteSpawnRules);
/*  93 */     register(EntityType.ENDER_DRAGON, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
/*  94 */     register(EntityType.FROG, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Frog::checkFrogSpawnRules);
/*  95 */     register(EntityType.GHAST, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Ghast::checkGhastSpawnRules);
/*  96 */     register(EntityType.GIANT, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/*  97 */     register(EntityType.GLOW_SQUID, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlowSquid::checkGlowSquidSpawnRules);
/*  98 */     register(EntityType.GOAT, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Goat::checkGoatSpawnRules);
/*  99 */     register(EntityType.HORSE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
/* 100 */     register(EntityType.HUSK, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Husk::checkHuskSpawnRules);
/* 101 */     register(EntityType.IRON_GOLEM, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
/* 102 */     register(EntityType.LLAMA, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
/* 103 */     register(EntityType.MAGMA_CUBE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MagmaCube::checkMagmaCubeSpawnRules);
/* 104 */     register(EntityType.MOOSHROOM, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MushroomCow::checkMushroomSpawnRules);
/* 105 */     register(EntityType.MULE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
/* 106 */     register(EntityType.OCELOT, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Ocelot::checkOcelotSpawnRules);
/* 107 */     register(EntityType.PARROT, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Parrot::checkParrotSpawnRules);
/* 108 */     register(EntityType.PIG, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
/* 109 */     register(EntityType.HOGLIN, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Hoglin::checkHoglinSpawnRules);
/* 110 */     register(EntityType.PIGLIN, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Piglin::checkPiglinSpawnRules);
/* 111 */     register(EntityType.PILLAGER, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PatrollingMonster::checkPatrollingMonsterSpawnRules);
/* 112 */     register(EntityType.POLAR_BEAR, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PolarBear::checkPolarBearSpawnRules);
/* 113 */     register(EntityType.RABBIT, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Rabbit::checkRabbitSpawnRules);
/* 114 */     register(EntityType.SHEEP, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
/* 115 */     register(EntityType.SILVERFISH, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Silverfish::checkSilverfishSpawnRules);
/* 116 */     register(EntityType.SKELETON, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/* 117 */     register(EntityType.SKELETON_HORSE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SkeletonHorse::checkSkeletonHorseSpawnRules);
/* 118 */     register(EntityType.SLIME, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Slime::checkSlimeSpawnRules);
/* 119 */     register(EntityType.SNOW_GOLEM, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
/* 120 */     register(EntityType.SPIDER, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/* 121 */     register(EntityType.STRAY, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Stray::checkStraySpawnRules);
/* 122 */     register(EntityType.STRIDER, Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Strider::checkStriderSpawnRules);
/* 123 */     register(EntityType.TURTLE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Turtle::checkTurtleSpawnRules);
/* 124 */     register(EntityType.VILLAGER, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
/* 125 */     register(EntityType.WITCH, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/* 126 */     register(EntityType.WITHER, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/* 127 */     register(EntityType.WITHER_SKELETON, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/* 128 */     register(EntityType.WOLF, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wolf::checkWolfSpawnRules);
/* 129 */     register(EntityType.ZOMBIE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/* 130 */     register(EntityType.ZOMBIE_HORSE, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombieHorse::checkZombieHorseSpawnRules);
/* 131 */     register(EntityType.ZOMBIFIED_PIGLIN, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ZombifiedPiglin::checkZombifiedPiglinSpawnRules);
/* 132 */     register(EntityType.ZOMBIE_VILLAGER, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/*     */ 
/*     */     
/* 135 */     register(EntityType.CAT, Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
/* 136 */     register(EntityType.ELDER_GUARDIAN, Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Guardian::checkGuardianSpawnRules);
/* 137 */     register(EntityType.EVOKER, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/* 138 */     register(EntityType.FOX, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Fox::checkFoxSpawnRules);
/* 139 */     register(EntityType.ILLUSIONER, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/* 140 */     register(EntityType.PANDA, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
/* 141 */     register(EntityType.PHANTOM, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
/* 142 */     register(EntityType.RAVAGER, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/* 143 */     register(EntityType.SHULKER, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
/* 144 */     register(EntityType.TRADER_LLAMA, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
/* 145 */     register(EntityType.VEX, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/* 146 */     register(EntityType.VINDICATOR, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
/* 147 */     register(EntityType.WANDERING_TRADER, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
/* 148 */     register(EntityType.WARDEN, Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
/*     */   }
/*     */   
/*     */   public static Type getPlacementType(EntityType<?> $$0) {
/* 152 */     Data $$1 = DATA_BY_TYPE.get($$0);
/* 153 */     return ($$1 == null) ? Type.NO_RESTRICTIONS : $$1.placement;
/*     */   }
/*     */   
/*     */   public static Heightmap.Types getHeightmapType(@Nullable EntityType<?> $$0) {
/* 157 */     Data $$1 = DATA_BY_TYPE.get($$0);
/* 158 */     return ($$1 == null) ? Heightmap.Types.MOTION_BLOCKING_NO_LEAVES : $$1.heightMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T extends Entity> boolean checkSpawnRules(EntityType<T> $$0, ServerLevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 163 */     Data $$5 = DATA_BY_TYPE.get($$0);
/* 164 */     return ($$5 == null || $$5.predicate.test($$0, $$1, $$2, $$3, $$4));
/*     */   }
/*     */   
/*     */   public enum Type {
/* 168 */     ON_GROUND,
/* 169 */     IN_WATER,
/* 170 */     NO_RESTRICTIONS,
/* 171 */     IN_LAVA;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface SpawnPredicate<T extends Entity> {
/*     */     boolean test(EntityType<T> param1EntityType, ServerLevelAccessor param1ServerLevelAccessor, MobSpawnType param1MobSpawnType, BlockPos param1BlockPos, RandomSource param1RandomSource);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\SpawnPlacements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */