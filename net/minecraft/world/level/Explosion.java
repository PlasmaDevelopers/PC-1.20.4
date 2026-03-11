/*     */ package net.minecraft.world.level;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.item.PrimedTnt;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.enchantment.ProtectionEnchantment;
/*     */ import net.minecraft.world.level.block.BaseFireBlock;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class Explosion {
/*     */   private static final int MAX_DROPS_PER_COMBINED_STACK = 16;
/*     */   private final boolean fire;
/*  42 */   private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new ExplosionDamageCalculator();
/*     */   private final BlockInteraction blockInteraction;
/*     */   
/*     */   public enum BlockInteraction {
/*  46 */     KEEP,
/*  47 */     DESTROY,
/*  48 */     DESTROY_WITH_DECAY,
/*  49 */     TRIGGER_BLOCK;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  54 */   private final RandomSource random = RandomSource.create();
/*     */   
/*     */   private final Level level;
/*     */   private final double x;
/*     */   private final double y;
/*     */   private final double z;
/*     */   @Nullable
/*     */   private final Entity source;
/*     */   private final float radius;
/*     */   private final DamageSource damageSource;
/*     */   private final ExplosionDamageCalculator damageCalculator;
/*     */   private final ParticleOptions smallExplosionParticles;
/*     */   private final ParticleOptions largeExplosionParticles;
/*     */   private final SoundEvent explosionSound;
/*  68 */   private final ObjectArrayList<BlockPos> toBlow = new ObjectArrayList();
/*  69 */   private final Map<Player, Vec3> hitPlayers = Maps.newHashMap();
/*     */   
/*     */   public static DamageSource getDefaultDamageSource(Level $$0, @Nullable Entity $$1) {
/*  72 */     return $$0.damageSources().explosion($$1, (Entity)getIndirectSourceEntityInternal($$1));
/*     */   }
/*     */   
/*     */   public Explosion(Level $$0, @Nullable Entity $$1, double $$2, double $$3, double $$4, float $$5, List<BlockPos> $$6, BlockInteraction $$7, ParticleOptions $$8, ParticleOptions $$9, SoundEvent $$10) {
/*  76 */     this($$0, $$1, getDefaultDamageSource($$0, $$1), null, $$2, $$3, $$4, $$5, false, $$7, $$8, $$9, $$10);
/*  77 */     this.toBlow.addAll($$6);
/*     */   }
/*     */   
/*     */   public Explosion(Level $$0, @Nullable Entity $$1, double $$2, double $$3, double $$4, float $$5, boolean $$6, BlockInteraction $$7, List<BlockPos> $$8) {
/*  81 */     this($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*  82 */     this.toBlow.addAll($$8);
/*     */   }
/*     */   
/*     */   public Explosion(Level $$0, @Nullable Entity $$1, double $$2, double $$3, double $$4, float $$5, boolean $$6, BlockInteraction $$7) {
/*  86 */     this($$0, $$1, getDefaultDamageSource($$0, $$1), null, $$2, $$3, $$4, $$5, $$6, $$7, (ParticleOptions)ParticleTypes.EXPLOSION, (ParticleOptions)ParticleTypes.EXPLOSION_EMITTER, SoundEvents.GENERIC_EXPLODE);
/*     */   }
/*     */   
/*     */   public Explosion(Level $$0, @Nullable Entity $$1, @Nullable DamageSource $$2, @Nullable ExplosionDamageCalculator $$3, double $$4, double $$5, double $$6, float $$7, boolean $$8, BlockInteraction $$9, ParticleOptions $$10, ParticleOptions $$11, SoundEvent $$12) {
/*  90 */     this.level = $$0;
/*  91 */     this.source = $$1;
/*  92 */     this.radius = $$7;
/*  93 */     this.x = $$4;
/*  94 */     this.y = $$5;
/*  95 */     this.z = $$6;
/*  96 */     this.fire = $$8;
/*  97 */     this.blockInteraction = $$9;
/*  98 */     this.damageSource = ($$2 == null) ? $$0.damageSources().explosion(this) : $$2;
/*  99 */     this.damageCalculator = ($$3 == null) ? makeDamageCalculator($$1) : $$3;
/* 100 */     this.smallExplosionParticles = $$10;
/* 101 */     this.largeExplosionParticles = $$11;
/* 102 */     this.explosionSound = $$12;
/*     */   }
/*     */   
/*     */   private ExplosionDamageCalculator makeDamageCalculator(@Nullable Entity $$0) {
/* 106 */     return ($$0 == null) ? EXPLOSION_DAMAGE_CALCULATOR : new EntityBasedExplosionDamageCalculator($$0);
/*     */   }
/*     */   
/*     */   public static float getSeenPercent(Vec3 $$0, Entity $$1) {
/* 110 */     AABB $$2 = $$1.getBoundingBox();
/* 111 */     double $$3 = 1.0D / (($$2.maxX - $$2.minX) * 2.0D + 1.0D);
/* 112 */     double $$4 = 1.0D / (($$2.maxY - $$2.minY) * 2.0D + 1.0D);
/* 113 */     double $$5 = 1.0D / (($$2.maxZ - $$2.minZ) * 2.0D + 1.0D);
/*     */     
/* 115 */     double $$6 = (1.0D - Math.floor(1.0D / $$3) * $$3) / 2.0D;
/* 116 */     double $$7 = (1.0D - Math.floor(1.0D / $$5) * $$5) / 2.0D;
/*     */     
/* 118 */     if ($$3 < 0.0D || $$4 < 0.0D || $$5 < 0.0D) {
/* 119 */       return 0.0F;
/*     */     }
/* 121 */     int $$8 = 0;
/* 122 */     int $$9 = 0; double $$10;
/* 123 */     for ($$10 = 0.0D; $$10 <= 1.0D; $$10 += $$3) {
/* 124 */       double $$11; for ($$11 = 0.0D; $$11 <= 1.0D; $$11 += $$4) {
/* 125 */         double $$12; for ($$12 = 0.0D; $$12 <= 1.0D; $$12 += $$5) {
/* 126 */           double $$13 = Mth.lerp($$10, $$2.minX, $$2.maxX);
/* 127 */           double $$14 = Mth.lerp($$11, $$2.minY, $$2.maxY);
/* 128 */           double $$15 = Mth.lerp($$12, $$2.minZ, $$2.maxZ);
/* 129 */           Vec3 $$16 = new Vec3($$13 + $$6, $$14, $$15 + $$7);
/* 130 */           if ($$1.level().clip(new ClipContext($$16, $$0, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, $$1)).getType() == HitResult.Type.MISS) {
/* 131 */             $$8++;
/*     */           }
/* 133 */           $$9++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     return $$8 / $$9;
/*     */   }
/*     */   
/*     */   public float radius() {
/* 142 */     return this.radius;
/*     */   }
/*     */   
/*     */   public Vec3 center() {
/* 146 */     return new Vec3(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public void explode() {
/* 150 */     this.level.gameEvent(this.source, GameEvent.EXPLODE, new Vec3(this.x, this.y, this.z));
/*     */     
/* 152 */     Set<BlockPos> $$0 = Sets.newHashSet();
/*     */     
/* 154 */     int $$1 = 16;
/* 155 */     for (int $$2 = 0; $$2 < 16; $$2++) {
/* 156 */       for (int $$3 = 0; $$3 < 16; $$3++) {
/* 157 */         for (int $$4 = 0; $$4 < 16; $$4++) {
/* 158 */           if ($$2 == 0 || $$2 == 15 || $$3 == 0 || $$3 == 15 || $$4 == 0 || $$4 == 15) {
/*     */ 
/*     */ 
/*     */             
/* 162 */             double $$5 = ($$2 / 15.0F * 2.0F - 1.0F);
/* 163 */             double $$6 = ($$3 / 15.0F * 2.0F - 1.0F);
/* 164 */             double $$7 = ($$4 / 15.0F * 2.0F - 1.0F);
/* 165 */             double $$8 = Math.sqrt($$5 * $$5 + $$6 * $$6 + $$7 * $$7);
/*     */             
/* 167 */             $$5 /= $$8;
/* 168 */             $$6 /= $$8;
/* 169 */             $$7 /= $$8;
/*     */             
/* 171 */             float $$9 = this.radius * (0.7F + this.level.random.nextFloat() * 0.6F);
/* 172 */             double $$10 = this.x;
/* 173 */             double $$11 = this.y;
/* 174 */             double $$12 = this.z;
/*     */             
/* 176 */             float $$13 = 0.3F;
/* 177 */             while ($$9 > 0.0F) {
/* 178 */               BlockPos $$14 = BlockPos.containing($$10, $$11, $$12);
/* 179 */               BlockState $$15 = this.level.getBlockState($$14);
/* 180 */               FluidState $$16 = this.level.getFluidState($$14);
/*     */               
/* 182 */               if (!this.level.isInWorldBounds($$14)) {
/*     */                 break;
/*     */               }
/*     */               
/* 186 */               Optional<Float> $$17 = this.damageCalculator.getBlockExplosionResistance(this, this.level, $$14, $$15, $$16);
/* 187 */               if ($$17.isPresent()) {
/* 188 */                 $$9 -= (((Float)$$17.get()).floatValue() + 0.3F) * 0.3F;
/*     */               }
/*     */               
/* 191 */               if ($$9 > 0.0F && this.damageCalculator.shouldBlockExplode(this, this.level, $$14, $$15, $$9)) {
/* 192 */                 $$0.add($$14);
/*     */               }
/*     */               
/* 195 */               $$10 += $$5 * 0.30000001192092896D;
/* 196 */               $$11 += $$6 * 0.30000001192092896D;
/* 197 */               $$12 += $$7 * 0.30000001192092896D;
/* 198 */               $$9 -= 0.22500001F;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 204 */     this.toBlow.addAll($$0);
/*     */     
/* 206 */     float $$18 = this.radius * 2.0F;
/*     */     
/* 208 */     int $$19 = Mth.floor(this.x - $$18 - 1.0D);
/* 209 */     int $$20 = Mth.floor(this.x + $$18 + 1.0D);
/* 210 */     int $$21 = Mth.floor(this.y - $$18 - 1.0D);
/* 211 */     int $$22 = Mth.floor(this.y + $$18 + 1.0D);
/* 212 */     int $$23 = Mth.floor(this.z - $$18 - 1.0D);
/* 213 */     int $$24 = Mth.floor(this.z + $$18 + 1.0D);
/* 214 */     List<Entity> $$25 = this.level.getEntities(this.source, new AABB($$19, $$21, $$23, $$20, $$22, $$24));
/* 215 */     Vec3 $$26 = new Vec3(this.x, this.y, this.z);
/*     */     
/* 217 */     for (Entity $$27 : $$25) {
/* 218 */       if ($$27.ignoreExplosion(this)) {
/*     */         continue;
/*     */       }
/* 221 */       double $$28 = Math.sqrt($$27.distanceToSqr($$26)) / $$18;
/*     */       
/* 223 */       if ($$28 <= 1.0D) {
/* 224 */         double $$36, $$29 = $$27.getX() - this.x;
/* 225 */         double $$30 = (($$27 instanceof PrimedTnt) ? $$27.getY() : $$27.getEyeY()) - this.y;
/* 226 */         double $$31 = $$27.getZ() - this.z;
/*     */         
/* 228 */         double $$32 = Math.sqrt($$29 * $$29 + $$30 * $$30 + $$31 * $$31);
/* 229 */         if ($$32 == 0.0D) {
/*     */           continue;
/*     */         }
/*     */         
/* 233 */         $$29 /= $$32;
/* 234 */         $$30 /= $$32;
/* 235 */         $$31 /= $$32;
/*     */         
/* 237 */         if (this.damageCalculator.shouldDamageEntity(this, $$27)) {
/* 238 */           $$27.hurt(this.damageSource, this.damageCalculator.getEntityDamageAmount(this, $$27));
/*     */         }
/*     */         
/* 241 */         double $$33 = (1.0D - $$28) * getSeenPercent($$26, $$27);
/*     */         
/* 243 */         if ($$27 instanceof LivingEntity) { LivingEntity $$34 = (LivingEntity)$$27;
/* 244 */           double $$35 = ProtectionEnchantment.getExplosionKnockbackAfterDampener($$34, $$33); }
/*     */         else
/* 246 */         { $$36 = $$33; }
/*     */ 
/*     */         
/* 249 */         $$29 *= $$36;
/* 250 */         $$30 *= $$36;
/* 251 */         $$31 *= $$36;
/*     */         
/* 253 */         Vec3 $$37 = new Vec3($$29, $$30, $$31);
/* 254 */         $$27.setDeltaMovement($$27.getDeltaMovement().add($$37));
/*     */         
/* 256 */         if ($$27 instanceof Player) { Player $$38 = (Player)$$27;
/* 257 */           if (!$$38.isSpectator() && (!$$38.isCreative() || !($$38.getAbilities()).flying)) {
/* 258 */             this.hitPlayers.put($$38, $$37);
/*     */           } }
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void finalizeExplosion(boolean $$0) {
/* 266 */     if (this.level.isClientSide) {
/* 267 */       this.level.playLocalSound(this.x, this.y, this.z, this.explosionSound, SoundSource.BLOCKS, 4.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
/*     */     }
/* 269 */     boolean $$1 = interactsWithBlocks();
/* 270 */     if ($$0) {
/*     */       ParticleOptions $$3;
/* 272 */       if (this.radius < 2.0F || !$$1) {
/* 273 */         ParticleOptions $$2 = this.smallExplosionParticles;
/*     */       } else {
/* 275 */         $$3 = this.largeExplosionParticles;
/*     */       } 
/* 277 */       this.level.addParticle($$3, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
/*     */     } 
/*     */     
/* 280 */     if ($$1) {
/* 281 */       this.level.getProfiler().push("explosion_blocks");
/*     */       
/* 283 */       List<Pair<ItemStack, BlockPos>> $$4 = new ArrayList<>();
/* 284 */       Util.shuffle((List)this.toBlow, this.level.random);
/*     */       
/* 286 */       for (ObjectListIterator<BlockPos> objectListIterator = this.toBlow.iterator(); objectListIterator.hasNext(); ) { BlockPos $$5 = objectListIterator.next();
/* 287 */         this.level.getBlockState($$5).onExplosionHit(this.level, $$5, this, ($$1, $$2) -> addOrAppendStack($$0, $$1, $$2)); }
/*     */ 
/*     */       
/* 290 */       for (Pair<ItemStack, BlockPos> $$6 : $$4) {
/* 291 */         Block.popResource(this.level, (BlockPos)$$6.getSecond(), (ItemStack)$$6.getFirst());
/*     */       }
/*     */       
/* 294 */       this.level.getProfiler().pop();
/*     */     } 
/*     */     
/* 297 */     if (this.fire) {
/* 298 */       for (ObjectListIterator<BlockPos> objectListIterator = this.toBlow.iterator(); objectListIterator.hasNext(); ) { BlockPos $$7 = objectListIterator.next();
/* 299 */         if (this.random.nextInt(3) == 0 && this.level.getBlockState($$7).isAir() && this.level.getBlockState($$7.below()).isSolidRender(this.level, $$7.below())) {
/* 300 */           this.level.setBlockAndUpdate($$7, BaseFireBlock.getState(this.level, $$7));
/*     */         } }
/*     */     
/*     */     }
/*     */   }
/*     */   
/*     */   private static void addOrAppendStack(List<Pair<ItemStack, BlockPos>> $$0, ItemStack $$1, BlockPos $$2) {
/* 307 */     for (int $$3 = 0; $$3 < $$0.size(); $$3++) {
/* 308 */       Pair<ItemStack, BlockPos> $$4 = $$0.get($$3);
/* 309 */       ItemStack $$5 = (ItemStack)$$4.getFirst();
/*     */       
/* 311 */       if (ItemEntity.areMergable($$5, $$1)) {
/* 312 */         $$0.set($$3, Pair.of(ItemEntity.merge($$5, $$1, 16), $$4.getSecond()));
/*     */         
/* 314 */         if ($$1.isEmpty()) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/* 319 */     $$0.add(Pair.of($$1, $$2));
/*     */   }
/*     */   
/*     */   public boolean interactsWithBlocks() {
/* 323 */     return (this.blockInteraction != BlockInteraction.KEEP);
/*     */   }
/*     */   
/*     */   public Map<Player, Vec3> getHitPlayers() {
/* 327 */     return this.hitPlayers;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static LivingEntity getIndirectSourceEntityInternal(@Nullable Entity $$0) {
/* 332 */     if ($$0 == null) {
/* 333 */       return null;
/*     */     }
/* 335 */     if ($$0 instanceof PrimedTnt) { PrimedTnt $$1 = (PrimedTnt)$$0;
/* 336 */       return $$1.getOwner(); }
/*     */     
/* 338 */     if ($$0 instanceof LivingEntity) { LivingEntity $$2 = (LivingEntity)$$0;
/* 339 */       return $$2; }
/*     */     
/* 341 */     if ($$0 instanceof Projectile) { Projectile $$3 = (Projectile)$$0;
/* 342 */       Entity $$4 = $$3.getOwner();
/* 343 */       if ($$4 instanceof LivingEntity) { LivingEntity $$5 = (LivingEntity)$$4;
/* 344 */         return $$5; }
/*     */        }
/*     */ 
/*     */     
/* 348 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public LivingEntity getIndirectSourceEntity() {
/* 353 */     return getIndirectSourceEntityInternal(this.source);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entity getDirectSourceEntity() {
/* 358 */     return this.source;
/*     */   }
/*     */   
/*     */   public void clearToBlow() {
/* 362 */     this.toBlow.clear();
/*     */   }
/*     */   
/*     */   public List<BlockPos> getToBlow() {
/* 366 */     return (List<BlockPos>)this.toBlow;
/*     */   }
/*     */   
/*     */   public BlockInteraction getBlockInteraction() {
/* 370 */     return this.blockInteraction;
/*     */   }
/*     */   
/*     */   public ParticleOptions getSmallExplosionParticles() {
/* 374 */     return this.smallExplosionParticles;
/*     */   }
/*     */   
/*     */   public ParticleOptions getLargeExplosionParticles() {
/* 378 */     return this.largeExplosionParticles;
/*     */   }
/*     */   
/*     */   public SoundEvent getExplosionSound() {
/* 382 */     return this.explosionSound;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\Explosion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */