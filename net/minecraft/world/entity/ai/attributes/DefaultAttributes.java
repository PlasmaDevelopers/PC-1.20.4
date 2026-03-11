/*     */ package net.minecraft.world.entity.ai.attributes;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.GlowSquid;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobCategory;
/*     */ import net.minecraft.world.entity.ambient.Bat;
/*     */ import net.minecraft.world.entity.animal.AbstractFish;
/*     */ import net.minecraft.world.entity.animal.Bee;
/*     */ import net.minecraft.world.entity.animal.Cat;
/*     */ import net.minecraft.world.entity.animal.Chicken;
/*     */ import net.minecraft.world.entity.animal.Cow;
/*     */ import net.minecraft.world.entity.animal.Dolphin;
/*     */ import net.minecraft.world.entity.animal.Fox;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ import net.minecraft.world.entity.animal.Ocelot;
/*     */ import net.minecraft.world.entity.animal.Panda;
/*     */ import net.minecraft.world.entity.animal.Parrot;
/*     */ import net.minecraft.world.entity.animal.Pig;
/*     */ import net.minecraft.world.entity.animal.PolarBear;
/*     */ import net.minecraft.world.entity.animal.Rabbit;
/*     */ import net.minecraft.world.entity.animal.Sheep;
/*     */ import net.minecraft.world.entity.animal.SnowGolem;
/*     */ import net.minecraft.world.entity.animal.Squid;
/*     */ import net.minecraft.world.entity.animal.Turtle;
/*     */ import net.minecraft.world.entity.animal.Wolf;
/*     */ import net.minecraft.world.entity.animal.allay.Allay;
/*     */ import net.minecraft.world.entity.animal.axolotl.Axolotl;
/*     */ import net.minecraft.world.entity.animal.camel.Camel;
/*     */ import net.minecraft.world.entity.animal.frog.Frog;
/*     */ import net.minecraft.world.entity.animal.frog.Tadpole;
/*     */ import net.minecraft.world.entity.animal.goat.Goat;
/*     */ import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
/*     */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*     */ import net.minecraft.world.entity.animal.horse.Llama;
/*     */ import net.minecraft.world.entity.animal.horse.SkeletonHorse;
/*     */ import net.minecraft.world.entity.animal.horse.ZombieHorse;
/*     */ import net.minecraft.world.entity.animal.sniffer.Sniffer;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*     */ import net.minecraft.world.entity.boss.wither.WitherBoss;
/*     */ import net.minecraft.world.entity.monster.AbstractSkeleton;
/*     */ import net.minecraft.world.entity.monster.Blaze;
/*     */ import net.minecraft.world.entity.monster.CaveSpider;
/*     */ import net.minecraft.world.entity.monster.Creeper;
/*     */ import net.minecraft.world.entity.monster.ElderGuardian;
/*     */ import net.minecraft.world.entity.monster.EnderMan;
/*     */ import net.minecraft.world.entity.monster.Endermite;
/*     */ import net.minecraft.world.entity.monster.Evoker;
/*     */ import net.minecraft.world.entity.monster.Ghast;
/*     */ import net.minecraft.world.entity.monster.Giant;
/*     */ import net.minecraft.world.entity.monster.Guardian;
/*     */ import net.minecraft.world.entity.monster.Illusioner;
/*     */ import net.minecraft.world.entity.monster.MagmaCube;
/*     */ import net.minecraft.world.entity.monster.Monster;
/*     */ import net.minecraft.world.entity.monster.Pillager;
/*     */ import net.minecraft.world.entity.monster.Ravager;
/*     */ import net.minecraft.world.entity.monster.Shulker;
/*     */ import net.minecraft.world.entity.monster.Silverfish;
/*     */ import net.minecraft.world.entity.monster.Spider;
/*     */ import net.minecraft.world.entity.monster.Strider;
/*     */ import net.minecraft.world.entity.monster.Vex;
/*     */ import net.minecraft.world.entity.monster.Vindicator;
/*     */ import net.minecraft.world.entity.monster.Witch;
/*     */ import net.minecraft.world.entity.monster.Zoglin;
/*     */ import net.minecraft.world.entity.monster.Zombie;
/*     */ import net.minecraft.world.entity.monster.ZombifiedPiglin;
/*     */ import net.minecraft.world.entity.monster.breeze.Breeze;
/*     */ import net.minecraft.world.entity.monster.hoglin.Hoglin;
/*     */ import net.minecraft.world.entity.monster.piglin.Piglin;
/*     */ import net.minecraft.world.entity.monster.piglin.PiglinBrute;
/*     */ import net.minecraft.world.entity.monster.warden.Warden;
/*     */ import net.minecraft.world.entity.npc.Villager;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class DefaultAttributes {
/*  84 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  86 */   private static final Map<EntityType<? extends LivingEntity>, AttributeSupplier> SUPPLIERS = (Map<EntityType<? extends LivingEntity>, AttributeSupplier>)ImmutableMap.builder()
/*  87 */     .put(EntityType.ALLAY, Allay.createAttributes().build())
/*  88 */     .put(EntityType.ARMOR_STAND, LivingEntity.createLivingAttributes().build())
/*  89 */     .put(EntityType.AXOLOTL, Axolotl.createAttributes().build())
/*  90 */     .put(EntityType.BAT, Bat.createAttributes().build())
/*  91 */     .put(EntityType.BEE, Bee.createAttributes().build())
/*  92 */     .put(EntityType.BLAZE, Blaze.createAttributes().build())
/*  93 */     .put(EntityType.CAT, Cat.createAttributes().build())
/*  94 */     .put(EntityType.CAMEL, Camel.createAttributes().build())
/*  95 */     .put(EntityType.CAVE_SPIDER, CaveSpider.createCaveSpider().build())
/*  96 */     .put(EntityType.CHICKEN, Chicken.createAttributes().build())
/*  97 */     .put(EntityType.COD, AbstractFish.createAttributes().build())
/*  98 */     .put(EntityType.COW, Cow.createAttributes().build())
/*  99 */     .put(EntityType.CREEPER, Creeper.createAttributes().build())
/* 100 */     .put(EntityType.DOLPHIN, Dolphin.createAttributes().build())
/* 101 */     .put(EntityType.DONKEY, AbstractChestedHorse.createBaseChestedHorseAttributes().build())
/* 102 */     .put(EntityType.DROWNED, Zombie.createAttributes().build())
/* 103 */     .put(EntityType.ELDER_GUARDIAN, ElderGuardian.createAttributes().build())
/* 104 */     .put(EntityType.ENDERMAN, EnderMan.createAttributes().build())
/* 105 */     .put(EntityType.ENDERMITE, Endermite.createAttributes().build())
/* 106 */     .put(EntityType.ENDER_DRAGON, EnderDragon.createAttributes().build())
/* 107 */     .put(EntityType.EVOKER, Evoker.createAttributes().build())
/* 108 */     .put(EntityType.BREEZE, Breeze.createAttributes().build())
/* 109 */     .put(EntityType.FOX, Fox.createAttributes().build())
/* 110 */     .put(EntityType.FROG, Frog.createAttributes().build())
/* 111 */     .put(EntityType.GHAST, Ghast.createAttributes().build())
/* 112 */     .put(EntityType.GIANT, Giant.createAttributes().build())
/* 113 */     .put(EntityType.GLOW_SQUID, GlowSquid.createAttributes().build())
/* 114 */     .put(EntityType.GOAT, Goat.createAttributes().build())
/* 115 */     .put(EntityType.GUARDIAN, Guardian.createAttributes().build())
/* 116 */     .put(EntityType.HOGLIN, Hoglin.createAttributes().build())
/* 117 */     .put(EntityType.HORSE, AbstractHorse.createBaseHorseAttributes().build())
/* 118 */     .put(EntityType.HUSK, Zombie.createAttributes().build())
/* 119 */     .put(EntityType.ILLUSIONER, Illusioner.createAttributes().build())
/* 120 */     .put(EntityType.IRON_GOLEM, IronGolem.createAttributes().build())
/* 121 */     .put(EntityType.LLAMA, Llama.createAttributes().build())
/* 122 */     .put(EntityType.MAGMA_CUBE, MagmaCube.createAttributes().build())
/* 123 */     .put(EntityType.MOOSHROOM, Cow.createAttributes().build())
/* 124 */     .put(EntityType.MULE, AbstractChestedHorse.createBaseChestedHorseAttributes().build())
/* 125 */     .put(EntityType.OCELOT, Ocelot.createAttributes().build())
/* 126 */     .put(EntityType.PANDA, Panda.createAttributes().build())
/* 127 */     .put(EntityType.PARROT, Parrot.createAttributes().build())
/* 128 */     .put(EntityType.PHANTOM, Monster.createMonsterAttributes().build())
/* 129 */     .put(EntityType.PIG, Pig.createAttributes().build())
/* 130 */     .put(EntityType.PIGLIN, Piglin.createAttributes().build())
/* 131 */     .put(EntityType.PIGLIN_BRUTE, PiglinBrute.createAttributes().build())
/* 132 */     .put(EntityType.PILLAGER, Pillager.createAttributes().build())
/* 133 */     .put(EntityType.PLAYER, Player.createAttributes().build())
/* 134 */     .put(EntityType.POLAR_BEAR, PolarBear.createAttributes().build())
/* 135 */     .put(EntityType.PUFFERFISH, AbstractFish.createAttributes().build())
/* 136 */     .put(EntityType.RABBIT, Rabbit.createAttributes().build())
/* 137 */     .put(EntityType.RAVAGER, Ravager.createAttributes().build())
/* 138 */     .put(EntityType.SALMON, AbstractFish.createAttributes().build())
/* 139 */     .put(EntityType.SHEEP, Sheep.createAttributes().build())
/* 140 */     .put(EntityType.SHULKER, Shulker.createAttributes().build())
/* 141 */     .put(EntityType.SILVERFISH, Silverfish.createAttributes().build())
/* 142 */     .put(EntityType.SKELETON, AbstractSkeleton.createAttributes().build())
/* 143 */     .put(EntityType.SKELETON_HORSE, SkeletonHorse.createAttributes().build())
/* 144 */     .put(EntityType.SLIME, Monster.createMonsterAttributes().build())
/* 145 */     .put(EntityType.SNIFFER, Sniffer.createAttributes().build())
/* 146 */     .put(EntityType.SNOW_GOLEM, SnowGolem.createAttributes().build())
/* 147 */     .put(EntityType.SPIDER, Spider.createAttributes().build())
/* 148 */     .put(EntityType.SQUID, Squid.createAttributes().build())
/* 149 */     .put(EntityType.STRAY, AbstractSkeleton.createAttributes().build())
/* 150 */     .put(EntityType.STRIDER, Strider.createAttributes().build())
/* 151 */     .put(EntityType.TADPOLE, Tadpole.createAttributes().build())
/* 152 */     .put(EntityType.TRADER_LLAMA, Llama.createAttributes().build())
/* 153 */     .put(EntityType.TROPICAL_FISH, AbstractFish.createAttributes().build())
/* 154 */     .put(EntityType.TURTLE, Turtle.createAttributes().build())
/* 155 */     .put(EntityType.VEX, Vex.createAttributes().build())
/* 156 */     .put(EntityType.VILLAGER, Villager.createAttributes().build())
/* 157 */     .put(EntityType.VINDICATOR, Vindicator.createAttributes().build())
/* 158 */     .put(EntityType.WARDEN, Warden.createAttributes().build())
/* 159 */     .put(EntityType.WANDERING_TRADER, Mob.createMobAttributes().build())
/* 160 */     .put(EntityType.WITCH, Witch.createAttributes().build())
/* 161 */     .put(EntityType.WITHER, WitherBoss.createAttributes().build())
/* 162 */     .put(EntityType.WITHER_SKELETON, AbstractSkeleton.createAttributes().build())
/* 163 */     .put(EntityType.WOLF, Wolf.createAttributes().build())
/* 164 */     .put(EntityType.ZOGLIN, Zoglin.createAttributes().build())
/* 165 */     .put(EntityType.ZOMBIE, Zombie.createAttributes().build())
/* 166 */     .put(EntityType.ZOMBIE_HORSE, ZombieHorse.createAttributes().build())
/* 167 */     .put(EntityType.ZOMBIE_VILLAGER, Zombie.createAttributes().build())
/* 168 */     .put(EntityType.ZOMBIFIED_PIGLIN, ZombifiedPiglin.createAttributes().build())
/* 169 */     .build();
/*     */   
/*     */   public static AttributeSupplier getSupplier(EntityType<? extends LivingEntity> $$0) {
/* 172 */     return SUPPLIERS.get($$0);
/*     */   }
/*     */   
/*     */   public static boolean hasSupplier(EntityType<?> $$0) {
/* 176 */     return SUPPLIERS.containsKey($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validate() {
/* 183 */     Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE); BuiltInRegistries.ENTITY_TYPE.stream().filter($$0 -> ($$0.getCategory() != MobCategory.MISC)).filter($$0 -> !hasSupplier($$0)).map(BuiltInRegistries.ENTITY_TYPE::getKey)
/* 184 */       .forEach($$0 -> Util.logAndPauseIfInIde("Entity " + $$0 + " has no attributes"));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\attributes\DefaultAttributes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */