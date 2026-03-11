/*     */ package net.minecraft.world.entity.projectile;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.entity.AreaEffectCloud;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.animal.axolotl.Axolotl;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.Potion;
/*     */ import net.minecraft.world.item.alchemy.PotionUtils;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.AbstractCandleBlock;
/*     */ import net.minecraft.world.level.block.CampfireBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ 
/*     */ public class ThrownPotion
/*     */   extends ThrowableItemProjectile implements ItemSupplier {
/*     */   public static final double SPLASH_RANGE = 4.0D;
/*     */   
/*     */   static {
/*  39 */     WATER_SENSITIVE_OR_ON_FIRE = ($$0 -> 
/*  40 */       ($$0.isSensitiveToWater() || $$0.isOnFire()));
/*     */   } private static final double SPLASH_RANGE_SQ = 16.0D; public static final Predicate<LivingEntity> WATER_SENSITIVE_OR_ON_FIRE;
/*     */   public ThrownPotion(EntityType<? extends ThrownPotion> $$0, Level $$1) {
/*  43 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */   
/*     */   public ThrownPotion(Level $$0, LivingEntity $$1) {
/*  47 */     super(EntityType.POTION, $$1, $$0);
/*     */   }
/*     */   
/*     */   public ThrownPotion(Level $$0, double $$1, double $$2, double $$3) {
/*  51 */     super(EntityType.POTION, $$1, $$2, $$3, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDefaultItem() {
/*  56 */     return Items.SPLASH_POTION;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getGravity() {
/*  61 */     return 0.05F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHitBlock(BlockHitResult $$0) {
/*  66 */     super.onHitBlock($$0);
/*  67 */     if ((level()).isClientSide) {
/*     */       return;
/*     */     }
/*  70 */     ItemStack $$1 = getItem();
/*  71 */     Potion $$2 = PotionUtils.getPotion($$1);
/*  72 */     List<MobEffectInstance> $$3 = PotionUtils.getMobEffects($$1);
/*  73 */     boolean $$4 = ($$2 == Potions.WATER && $$3.isEmpty());
/*  74 */     Direction $$5 = $$0.getDirection();
/*  75 */     BlockPos $$6 = $$0.getBlockPos();
/*  76 */     BlockPos $$7 = $$6.relative($$5);
/*     */     
/*  78 */     if ($$4) {
/*  79 */       dowseFire($$7);
/*  80 */       dowseFire($$7.relative($$5.getOpposite()));
/*  81 */       for (Direction $$8 : Direction.Plane.HORIZONTAL) {
/*  82 */         dowseFire($$7.relative($$8));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHit(HitResult $$0) {
/*  89 */     super.onHit($$0);
/*  90 */     if ((level()).isClientSide) {
/*     */       return;
/*     */     }
/*  93 */     ItemStack $$1 = getItem();
/*     */     
/*  95 */     Potion $$2 = PotionUtils.getPotion($$1);
/*  96 */     List<MobEffectInstance> $$3 = PotionUtils.getMobEffects($$1);
/*  97 */     boolean $$4 = ($$2 == Potions.WATER && $$3.isEmpty());
/*     */     
/*  99 */     if ($$4) {
/* 100 */       applyWater();
/* 101 */     } else if (!$$3.isEmpty()) {
/* 102 */       if (isLingering()) {
/* 103 */         makeAreaOfEffectCloud($$1, $$2);
/*     */       } else {
/* 105 */         applySplash($$3, ($$0.getType() == HitResult.Type.ENTITY) ? ((EntityHitResult)$$0).getEntity() : null);
/*     */       } 
/*     */     } 
/* 108 */     int $$5 = $$2.hasInstantEffects() ? 2007 : 2002;
/* 109 */     level().levelEvent($$5, blockPosition(), PotionUtils.getColor($$1));
/*     */     
/* 111 */     discard();
/*     */   }
/*     */   
/*     */   private void applyWater() {
/* 115 */     AABB $$0 = getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
/* 116 */     List<LivingEntity> $$1 = level().getEntitiesOfClass(LivingEntity.class, $$0, WATER_SENSITIVE_OR_ON_FIRE);
/* 117 */     for (LivingEntity $$2 : $$1) {
/* 118 */       double $$3 = distanceToSqr((Entity)$$2);
/* 119 */       if ($$3 < 16.0D) {
/* 120 */         if ($$2.isSensitiveToWater()) {
/* 121 */           $$2.hurt(damageSources().indirectMagic(this, getOwner()), 1.0F);
/*     */         }
/* 123 */         if ($$2.isOnFire() && $$2.isAlive()) {
/* 124 */           $$2.extinguishFire();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     List<Axolotl> $$4 = level().getEntitiesOfClass(Axolotl.class, $$0);
/* 130 */     for (Axolotl $$5 : $$4) {
/* 131 */       $$5.rehydrate();
/*     */     }
/*     */   }
/*     */   
/*     */   private void applySplash(List<MobEffectInstance> $$0, @Nullable Entity $$1) {
/* 136 */     AABB $$2 = getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
/* 137 */     List<LivingEntity> $$3 = level().getEntitiesOfClass(LivingEntity.class, $$2);
/*     */     
/* 139 */     if (!$$3.isEmpty()) {
/* 140 */       Entity $$4 = getEffectSource();
/* 141 */       for (LivingEntity $$5 : $$3) {
/* 142 */         if (!$$5.isAffectedByPotions()) {
/*     */           continue;
/*     */         }
/* 145 */         double $$6 = distanceToSqr((Entity)$$5);
/* 146 */         if ($$6 < 16.0D) {
/*     */           double $$8;
/* 148 */           if ($$5 == $$1) {
/* 149 */             double $$7 = 1.0D;
/*     */           } else {
/* 151 */             $$8 = 1.0D - Math.sqrt($$6) / 4.0D;
/*     */           } 
/*     */           
/* 154 */           for (MobEffectInstance $$9 : $$0) {
/* 155 */             MobEffect $$10 = $$9.getEffect();
/* 156 */             if ($$10.isInstantenous()) {
/* 157 */               $$10.applyInstantenousEffect(this, getOwner(), $$5, $$9.getAmplifier(), $$8); continue;
/*     */             } 
/* 159 */             int $$11 = $$9.mapDuration($$1 -> (int)($$0 * $$1 + 0.5D));
/* 160 */             MobEffectInstance $$12 = new MobEffectInstance($$10, $$11, $$9.getAmplifier(), $$9.isAmbient(), $$9.isVisible());
/* 161 */             if (!$$12.endsWithin(20)) {
/* 162 */               $$5.addEffect($$12, $$4);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void makeAreaOfEffectCloud(ItemStack $$0, Potion $$1) {
/* 172 */     AreaEffectCloud $$2 = new AreaEffectCloud(level(), getX(), getY(), getZ());
/* 173 */     Entity $$3 = getOwner();
/* 174 */     if ($$3 instanceof LivingEntity) {
/* 175 */       $$2.setOwner((LivingEntity)$$3);
/*     */     }
/* 177 */     $$2.setRadius(3.0F);
/* 178 */     $$2.setRadiusOnUse(-0.5F);
/* 179 */     $$2.setWaitTime(10);
/* 180 */     $$2.setRadiusPerTick(-$$2.getRadius() / $$2.getDuration());
/* 181 */     $$2.setPotion($$1);
/* 182 */     for (MobEffectInstance $$4 : PotionUtils.getCustomEffects($$0)) {
/* 183 */       $$2.addEffect(new MobEffectInstance($$4));
/*     */     }
/*     */     
/* 186 */     CompoundTag $$5 = $$0.getTag();
/* 187 */     if ($$5 != null && $$5.contains("CustomPotionColor", 99)) {
/* 188 */       $$2.setFixedColor($$5.getInt("CustomPotionColor"));
/*     */     }
/*     */     
/* 191 */     level().addFreshEntity((Entity)$$2);
/*     */   }
/*     */   
/*     */   private boolean isLingering() {
/* 195 */     return getItem().is(Items.LINGERING_POTION);
/*     */   }
/*     */   
/*     */   private void dowseFire(BlockPos $$0) {
/* 199 */     BlockState $$1 = level().getBlockState($$0);
/* 200 */     if ($$1.is(BlockTags.FIRE)) {
/* 201 */       level().destroyBlock($$0, false, this);
/* 202 */     } else if (AbstractCandleBlock.isLit($$1)) {
/* 203 */       AbstractCandleBlock.extinguish(null, $$1, (LevelAccessor)level(), $$0);
/* 204 */     } else if (CampfireBlock.isLitCampfire($$1)) {
/* 205 */       level().levelEvent(null, 1009, $$0, 0);
/* 206 */       CampfireBlock.dowse(getOwner(), (LevelAccessor)level(), $$0, $$1);
/* 207 */       level().setBlockAndUpdate($$0, (BlockState)$$1.setValue((Property)CampfireBlock.LIT, Boolean.valueOf(false)));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\ThrownPotion.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */