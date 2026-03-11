/*    */ package net.minecraft.world.damagesource;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.worldgen.BootstapContext;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public interface DamageTypes
/*    */ {
/* 10 */   public static final ResourceKey<DamageType> IN_FIRE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("in_fire"));
/* 11 */   public static final ResourceKey<DamageType> LIGHTNING_BOLT = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("lightning_bolt"));
/* 12 */   public static final ResourceKey<DamageType> ON_FIRE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("on_fire"));
/* 13 */   public static final ResourceKey<DamageType> LAVA = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("lava"));
/* 14 */   public static final ResourceKey<DamageType> HOT_FLOOR = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("hot_floor"));
/* 15 */   public static final ResourceKey<DamageType> IN_WALL = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("in_wall"));
/* 16 */   public static final ResourceKey<DamageType> CRAMMING = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("cramming"));
/* 17 */   public static final ResourceKey<DamageType> DROWN = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("drown"));
/* 18 */   public static final ResourceKey<DamageType> STARVE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("starve"));
/* 19 */   public static final ResourceKey<DamageType> CACTUS = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("cactus"));
/* 20 */   public static final ResourceKey<DamageType> FALL = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("fall"));
/* 21 */   public static final ResourceKey<DamageType> FLY_INTO_WALL = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("fly_into_wall"));
/* 22 */   public static final ResourceKey<DamageType> FELL_OUT_OF_WORLD = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("out_of_world"));
/* 23 */   public static final ResourceKey<DamageType> GENERIC = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("generic"));
/* 24 */   public static final ResourceKey<DamageType> MAGIC = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("magic"));
/* 25 */   public static final ResourceKey<DamageType> WITHER = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("wither"));
/* 26 */   public static final ResourceKey<DamageType> DRAGON_BREATH = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("dragon_breath"));
/* 27 */   public static final ResourceKey<DamageType> DRY_OUT = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("dry_out"));
/* 28 */   public static final ResourceKey<DamageType> SWEET_BERRY_BUSH = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("sweet_berry_bush"));
/* 29 */   public static final ResourceKey<DamageType> FREEZE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("freeze"));
/* 30 */   public static final ResourceKey<DamageType> STALAGMITE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("stalagmite"));
/* 31 */   public static final ResourceKey<DamageType> FALLING_BLOCK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("falling_block"));
/* 32 */   public static final ResourceKey<DamageType> FALLING_ANVIL = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("falling_anvil"));
/* 33 */   public static final ResourceKey<DamageType> FALLING_STALACTITE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("falling_stalactite"));
/* 34 */   public static final ResourceKey<DamageType> STING = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("sting"));
/* 35 */   public static final ResourceKey<DamageType> MOB_ATTACK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("mob_attack"));
/* 36 */   public static final ResourceKey<DamageType> MOB_ATTACK_NO_AGGRO = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("mob_attack_no_aggro"));
/* 37 */   public static final ResourceKey<DamageType> PLAYER_ATTACK = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("player_attack"));
/* 38 */   public static final ResourceKey<DamageType> ARROW = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("arrow"));
/* 39 */   public static final ResourceKey<DamageType> TRIDENT = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("trident"));
/* 40 */   public static final ResourceKey<DamageType> MOB_PROJECTILE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("mob_projectile"));
/* 41 */   public static final ResourceKey<DamageType> FIREWORKS = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("fireworks"));
/* 42 */   public static final ResourceKey<DamageType> FIREBALL = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("fireball"));
/* 43 */   public static final ResourceKey<DamageType> UNATTRIBUTED_FIREBALL = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("unattributed_fireball"));
/* 44 */   public static final ResourceKey<DamageType> WITHER_SKULL = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("wither_skull"));
/* 45 */   public static final ResourceKey<DamageType> THROWN = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("thrown"));
/* 46 */   public static final ResourceKey<DamageType> INDIRECT_MAGIC = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("indirect_magic"));
/* 47 */   public static final ResourceKey<DamageType> THORNS = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("thorns"));
/* 48 */   public static final ResourceKey<DamageType> EXPLOSION = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("explosion"));
/* 49 */   public static final ResourceKey<DamageType> PLAYER_EXPLOSION = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("player_explosion"));
/* 50 */   public static final ResourceKey<DamageType> SONIC_BOOM = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("sonic_boom"));
/* 51 */   public static final ResourceKey<DamageType> BAD_RESPAWN_POINT = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("bad_respawn_point"));
/* 52 */   public static final ResourceKey<DamageType> OUTSIDE_BORDER = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("outside_border"));
/* 53 */   public static final ResourceKey<DamageType> GENERIC_KILL = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("generic_kill"));
/*    */   
/*    */   static void bootstrap(BootstapContext<DamageType> $$0) {
/* 56 */     $$0.register(IN_FIRE, new DamageType("inFire", 0.1F, DamageEffects.BURNING));
/* 57 */     $$0.register(LIGHTNING_BOLT, new DamageType("lightningBolt", 0.1F));
/* 58 */     $$0.register(ON_FIRE, new DamageType("onFire", 0.0F, DamageEffects.BURNING));
/* 59 */     $$0.register(LAVA, new DamageType("lava", 0.1F, DamageEffects.BURNING));
/* 60 */     $$0.register(HOT_FLOOR, new DamageType("hotFloor", 0.1F, DamageEffects.BURNING));
/* 61 */     $$0.register(IN_WALL, new DamageType("inWall", 0.0F));
/* 62 */     $$0.register(CRAMMING, new DamageType("cramming", 0.0F));
/* 63 */     $$0.register(DROWN, new DamageType("drown", 0.0F, DamageEffects.DROWNING));
/* 64 */     $$0.register(STARVE, new DamageType("starve", 0.0F));
/* 65 */     $$0.register(CACTUS, new DamageType("cactus", 0.1F));
/* 66 */     $$0.register(FALL, new DamageType("fall", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.0F, DamageEffects.HURT, DeathMessageType.FALL_VARIANTS));
/* 67 */     $$0.register(FLY_INTO_WALL, new DamageType("flyIntoWall", 0.0F));
/* 68 */     $$0.register(FELL_OUT_OF_WORLD, new DamageType("outOfWorld", 0.0F));
/* 69 */     $$0.register(GENERIC, new DamageType("generic", 0.0F));
/* 70 */     $$0.register(MAGIC, new DamageType("magic", 0.0F));
/* 71 */     $$0.register(WITHER, new DamageType("wither", 0.0F));
/* 72 */     $$0.register(DRAGON_BREATH, new DamageType("dragonBreath", 0.0F));
/* 73 */     $$0.register(DRY_OUT, new DamageType("dryout", 0.1F));
/* 74 */     $$0.register(SWEET_BERRY_BUSH, new DamageType("sweetBerryBush", 0.1F, DamageEffects.POKING));
/* 75 */     $$0.register(FREEZE, new DamageType("freeze", 0.0F, DamageEffects.FREEZING));
/* 76 */     $$0.register(STALAGMITE, new DamageType("stalagmite", 0.0F));
/* 77 */     $$0.register(FALLING_BLOCK, new DamageType("fallingBlock", 0.1F));
/* 78 */     $$0.register(FALLING_ANVIL, new DamageType("anvil", 0.1F));
/* 79 */     $$0.register(FALLING_STALACTITE, new DamageType("fallingStalactite", 0.1F));
/* 80 */     $$0.register(STING, new DamageType("sting", 0.1F));
/* 81 */     $$0.register(MOB_ATTACK, new DamageType("mob", 0.1F));
/* 82 */     $$0.register(MOB_ATTACK_NO_AGGRO, new DamageType("mob", 0.1F));
/* 83 */     $$0.register(PLAYER_ATTACK, new DamageType("player", 0.1F));
/* 84 */     $$0.register(ARROW, new DamageType("arrow", 0.1F));
/* 85 */     $$0.register(TRIDENT, new DamageType("trident", 0.1F));
/* 86 */     $$0.register(MOB_PROJECTILE, new DamageType("mob", 0.1F));
/* 87 */     $$0.register(FIREWORKS, new DamageType("fireworks", 0.1F));
/* 88 */     $$0.register(UNATTRIBUTED_FIREBALL, new DamageType("onFire", 0.1F, DamageEffects.BURNING));
/* 89 */     $$0.register(FIREBALL, new DamageType("fireball", 0.1F, DamageEffects.BURNING));
/* 90 */     $$0.register(WITHER_SKULL, new DamageType("witherSkull", 0.1F));
/* 91 */     $$0.register(THROWN, new DamageType("thrown", 0.1F));
/* 92 */     $$0.register(INDIRECT_MAGIC, new DamageType("indirectMagic", 0.0F));
/* 93 */     $$0.register(THORNS, new DamageType("thorns", 0.1F, DamageEffects.THORNS));
/* 94 */     $$0.register(EXPLOSION, new DamageType("explosion", DamageScaling.ALWAYS, 0.1F));
/* 95 */     $$0.register(PLAYER_EXPLOSION, new DamageType("explosion.player", DamageScaling.ALWAYS, 0.1F));
/* 96 */     $$0.register(SONIC_BOOM, new DamageType("sonic_boom", DamageScaling.ALWAYS, 0.0F));
/* 97 */     $$0.register(BAD_RESPAWN_POINT, new DamageType("badRespawnPoint", DamageScaling.ALWAYS, 0.1F, DamageEffects.HURT, DeathMessageType.INTENTIONAL_GAME_DESIGN));
/* 98 */     $$0.register(OUTSIDE_BORDER, new DamageType("outsideBorder", 0.0F));
/* 99 */     $$0.register(GENERIC_KILL, new DamageType("genericKill", 0.0F));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\damagesource\DamageTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */