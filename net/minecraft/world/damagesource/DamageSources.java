/*     */ package net.minecraft.world.damagesource;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*     */ import net.minecraft.world.entity.projectile.Fireball;
/*     */ import net.minecraft.world.entity.projectile.FireworkRocketEntity;
/*     */ import net.minecraft.world.entity.projectile.WitherSkull;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class DamageSources {
/*     */   private final Registry<DamageType> damageTypes;
/*     */   private final DamageSource inFire;
/*     */   private final DamageSource lightningBolt;
/*     */   private final DamageSource onFire;
/*     */   private final DamageSource lava;
/*     */   private final DamageSource hotFloor;
/*     */   private final DamageSource inWall;
/*     */   private final DamageSource cramming;
/*     */   private final DamageSource drown;
/*     */   private final DamageSource starve;
/*     */   private final DamageSource cactus;
/*     */   private final DamageSource fall;
/*     */   private final DamageSource flyIntoWall;
/*     */   private final DamageSource fellOutOfWorld;
/*     */   private final DamageSource generic;
/*     */   private final DamageSource magic;
/*     */   private final DamageSource wither;
/*     */   private final DamageSource dragonBreath;
/*     */   private final DamageSource dryOut;
/*     */   private final DamageSource sweetBerryBush;
/*     */   private final DamageSource freeze;
/*     */   private final DamageSource stalagmite;
/*     */   private final DamageSource outsideBorder;
/*     */   private final DamageSource genericKill;
/*     */   
/*     */   public DamageSources(RegistryAccess $$0) {
/*  46 */     this.damageTypes = $$0.registryOrThrow(Registries.DAMAGE_TYPE);
/*  47 */     this.inFire = source(DamageTypes.IN_FIRE);
/*  48 */     this.lightningBolt = source(DamageTypes.LIGHTNING_BOLT);
/*  49 */     this.onFire = source(DamageTypes.ON_FIRE);
/*  50 */     this.lava = source(DamageTypes.LAVA);
/*  51 */     this.hotFloor = source(DamageTypes.HOT_FLOOR);
/*  52 */     this.inWall = source(DamageTypes.IN_WALL);
/*  53 */     this.cramming = source(DamageTypes.CRAMMING);
/*  54 */     this.drown = source(DamageTypes.DROWN);
/*  55 */     this.starve = source(DamageTypes.STARVE);
/*  56 */     this.cactus = source(DamageTypes.CACTUS);
/*  57 */     this.fall = source(DamageTypes.FALL);
/*  58 */     this.flyIntoWall = source(DamageTypes.FLY_INTO_WALL);
/*  59 */     this.fellOutOfWorld = source(DamageTypes.FELL_OUT_OF_WORLD);
/*  60 */     this.generic = source(DamageTypes.GENERIC);
/*  61 */     this.magic = source(DamageTypes.MAGIC);
/*  62 */     this.wither = source(DamageTypes.WITHER);
/*  63 */     this.dragonBreath = source(DamageTypes.DRAGON_BREATH);
/*  64 */     this.dryOut = source(DamageTypes.DRY_OUT);
/*  65 */     this.sweetBerryBush = source(DamageTypes.SWEET_BERRY_BUSH);
/*  66 */     this.freeze = source(DamageTypes.FREEZE);
/*  67 */     this.stalagmite = source(DamageTypes.STALAGMITE);
/*  68 */     this.outsideBorder = source(DamageTypes.OUTSIDE_BORDER);
/*  69 */     this.genericKill = source(DamageTypes.GENERIC_KILL);
/*     */   }
/*     */   
/*     */   private DamageSource source(ResourceKey<DamageType> $$0) {
/*  73 */     return new DamageSource((Holder<DamageType>)this.damageTypes.getHolderOrThrow($$0));
/*     */   }
/*     */   
/*     */   private DamageSource source(ResourceKey<DamageType> $$0, @Nullable Entity $$1) {
/*  77 */     return new DamageSource((Holder<DamageType>)this.damageTypes.getHolderOrThrow($$0), $$1);
/*     */   }
/*     */   
/*     */   private DamageSource source(ResourceKey<DamageType> $$0, @Nullable Entity $$1, @Nullable Entity $$2) {
/*  81 */     return new DamageSource((Holder<DamageType>)this.damageTypes.getHolderOrThrow($$0), $$1, $$2);
/*     */   }
/*     */   
/*     */   public DamageSource inFire() {
/*  85 */     return this.inFire;
/*     */   }
/*     */   
/*     */   public DamageSource lightningBolt() {
/*  89 */     return this.lightningBolt;
/*     */   }
/*     */   
/*     */   public DamageSource onFire() {
/*  93 */     return this.onFire;
/*     */   }
/*     */   
/*     */   public DamageSource lava() {
/*  97 */     return this.lava;
/*     */   }
/*     */   
/*     */   public DamageSource hotFloor() {
/* 101 */     return this.hotFloor;
/*     */   }
/*     */   
/*     */   public DamageSource inWall() {
/* 105 */     return this.inWall;
/*     */   }
/*     */   
/*     */   public DamageSource cramming() {
/* 109 */     return this.cramming;
/*     */   }
/*     */   
/*     */   public DamageSource drown() {
/* 113 */     return this.drown;
/*     */   }
/*     */   
/*     */   public DamageSource starve() {
/* 117 */     return this.starve;
/*     */   }
/*     */   
/*     */   public DamageSource cactus() {
/* 121 */     return this.cactus;
/*     */   }
/*     */   
/*     */   public DamageSource fall() {
/* 125 */     return this.fall;
/*     */   }
/*     */   
/*     */   public DamageSource flyIntoWall() {
/* 129 */     return this.flyIntoWall;
/*     */   }
/*     */   
/*     */   public DamageSource fellOutOfWorld() {
/* 133 */     return this.fellOutOfWorld;
/*     */   }
/*     */   
/*     */   public DamageSource generic() {
/* 137 */     return this.generic;
/*     */   }
/*     */   
/*     */   public DamageSource magic() {
/* 141 */     return this.magic;
/*     */   }
/*     */   
/*     */   public DamageSource wither() {
/* 145 */     return this.wither;
/*     */   }
/*     */   
/*     */   public DamageSource dragonBreath() {
/* 149 */     return this.dragonBreath;
/*     */   }
/*     */   
/*     */   public DamageSource dryOut() {
/* 153 */     return this.dryOut;
/*     */   }
/*     */   
/*     */   public DamageSource sweetBerryBush() {
/* 157 */     return this.sweetBerryBush;
/*     */   }
/*     */   
/*     */   public DamageSource freeze() {
/* 161 */     return this.freeze;
/*     */   }
/*     */   
/*     */   public DamageSource stalagmite() {
/* 165 */     return this.stalagmite;
/*     */   }
/*     */   
/*     */   public DamageSource fallingBlock(Entity $$0) {
/* 169 */     return source(DamageTypes.FALLING_BLOCK, $$0);
/*     */   }
/*     */   
/*     */   public DamageSource anvil(Entity $$0) {
/* 173 */     return source(DamageTypes.FALLING_ANVIL, $$0);
/*     */   }
/*     */   
/*     */   public DamageSource fallingStalactite(Entity $$0) {
/* 177 */     return source(DamageTypes.FALLING_STALACTITE, $$0);
/*     */   }
/*     */   
/*     */   public DamageSource sting(LivingEntity $$0) {
/* 181 */     return source(DamageTypes.STING, (Entity)$$0);
/*     */   }
/*     */   
/*     */   public DamageSource mobAttack(LivingEntity $$0) {
/* 185 */     return source(DamageTypes.MOB_ATTACK, (Entity)$$0);
/*     */   }
/*     */   
/*     */   public DamageSource noAggroMobAttack(LivingEntity $$0) {
/* 189 */     return source(DamageTypes.MOB_ATTACK_NO_AGGRO, (Entity)$$0);
/*     */   }
/*     */   
/*     */   public DamageSource playerAttack(Player $$0) {
/* 193 */     return source(DamageTypes.PLAYER_ATTACK, (Entity)$$0);
/*     */   }
/*     */   
/*     */   public DamageSource arrow(AbstractArrow $$0, @Nullable Entity $$1) {
/* 197 */     return source(DamageTypes.ARROW, (Entity)$$0, $$1);
/*     */   }
/*     */   
/*     */   public DamageSource trident(Entity $$0, @Nullable Entity $$1) {
/* 201 */     return source(DamageTypes.TRIDENT, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public DamageSource mobProjectile(Entity $$0, @Nullable LivingEntity $$1) {
/* 206 */     return source(DamageTypes.MOB_PROJECTILE, $$0, (Entity)$$1);
/*     */   }
/*     */   
/*     */   public DamageSource fireworks(FireworkRocketEntity $$0, @Nullable Entity $$1) {
/* 210 */     return source(DamageTypes.FIREWORKS, (Entity)$$0, $$1);
/*     */   }
/*     */   
/*     */   public DamageSource fireball(Fireball $$0, @Nullable Entity $$1) {
/* 214 */     if ($$1 == null) {
/* 215 */       return source(DamageTypes.UNATTRIBUTED_FIREBALL, (Entity)$$0);
/*     */     }
/* 217 */     return source(DamageTypes.FIREBALL, (Entity)$$0, $$1);
/*     */   }
/*     */   
/*     */   public DamageSource witherSkull(WitherSkull $$0, Entity $$1) {
/* 221 */     return source(DamageTypes.WITHER_SKULL, (Entity)$$0, $$1);
/*     */   }
/*     */   
/*     */   public DamageSource thrown(Entity $$0, @Nullable Entity $$1) {
/* 225 */     return source(DamageTypes.THROWN, $$0, $$1);
/*     */   }
/*     */   
/*     */   public DamageSource indirectMagic(Entity $$0, @Nullable Entity $$1) {
/* 229 */     return source(DamageTypes.INDIRECT_MAGIC, $$0, $$1);
/*     */   }
/*     */   
/*     */   public DamageSource thorns(Entity $$0) {
/* 233 */     return source(DamageTypes.THORNS, $$0);
/*     */   }
/*     */   
/*     */   public DamageSource explosion(@Nullable Explosion $$0) {
/* 237 */     return ($$0 != null) ? explosion($$0.getDirectSourceEntity(), (Entity)$$0.getIndirectSourceEntity()) : explosion(null, null);
/*     */   }
/*     */   
/*     */   public DamageSource explosion(@Nullable Entity $$0, @Nullable Entity $$1) {
/* 241 */     return source(($$1 != null && $$0 != null) ? DamageTypes.PLAYER_EXPLOSION : DamageTypes.EXPLOSION, $$0, $$1);
/*     */   }
/*     */   
/*     */   public DamageSource sonicBoom(Entity $$0) {
/* 245 */     return source(DamageTypes.SONIC_BOOM, $$0);
/*     */   }
/*     */   
/*     */   public DamageSource badRespawnPointExplosion(Vec3 $$0) {
/* 249 */     return new DamageSource((Holder<DamageType>)this.damageTypes.getHolderOrThrow(DamageTypes.BAD_RESPAWN_POINT), $$0);
/*     */   }
/*     */   
/*     */   public DamageSource outOfBorder() {
/* 253 */     return this.outsideBorder;
/*     */   }
/*     */   
/*     */   public DamageSource genericKill() {
/* 257 */     return this.genericKill;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\damagesource\DamageSources.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */