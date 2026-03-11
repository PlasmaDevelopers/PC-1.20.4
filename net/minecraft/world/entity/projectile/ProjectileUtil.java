/*     */ package net.minecraft.world.entity.projectile;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.item.ArrowItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ProjectileUtil
/*     */ {
/*     */   private static final float DEFAULT_ENTITY_HIT_RESULT_MARGIN = 0.3F;
/*     */   
/*     */   public static HitResult getHitResultOnMoveVector(Entity $$0, Predicate<Entity> $$1) {
/*  29 */     Vec3 $$2 = $$0.getDeltaMovement();
/*  30 */     Level $$3 = $$0.level();
/*     */     
/*  32 */     Vec3 $$4 = $$0.position();
/*  33 */     return getHitResult($$4, $$0, $$1, $$2, $$3, 0.3F, ClipContext.Block.COLLIDER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HitResult getHitResultOnMoveVector(Entity $$0, Predicate<Entity> $$1, ClipContext.Block $$2) {
/*  40 */     Vec3 $$3 = $$0.getDeltaMovement();
/*  41 */     Level $$4 = $$0.level();
/*     */     
/*  43 */     Vec3 $$5 = $$0.position();
/*  44 */     return getHitResult($$5, $$0, $$1, $$3, $$4, 0.3F, $$2);
/*     */   }
/*     */   
/*     */   public static HitResult getHitResultOnViewVector(Entity $$0, Predicate<Entity> $$1, double $$2) {
/*  48 */     Vec3 $$3 = $$0.getViewVector(0.0F).scale($$2);
/*  49 */     Level $$4 = $$0.level();
/*     */     
/*  51 */     Vec3 $$5 = $$0.getEyePosition();
/*  52 */     return getHitResult($$5, $$0, $$1, $$3, $$4, 0.0F, ClipContext.Block.COLLIDER);
/*     */   }
/*     */   private static HitResult getHitResult(Vec3 $$0, Entity $$1, Predicate<Entity> $$2, Vec3 $$3, Level $$4, float $$5, ClipContext.Block $$6) {
/*     */     EntityHitResult entityHitResult1;
/*  56 */     Vec3 $$7 = $$0.add($$3);
/*  57 */     BlockHitResult blockHitResult = $$4.clip(new ClipContext($$0, $$7, $$6, ClipContext.Fluid.NONE, $$1));
/*     */     
/*  59 */     if (blockHitResult.getType() != HitResult.Type.MISS) {
/*  60 */       $$7 = blockHitResult.getLocation();
/*     */     }
/*  62 */     EntityHitResult entityHitResult2 = getEntityHitResult($$4, $$1, $$0, $$7, $$1.getBoundingBox().expandTowards($$3).inflate(1.0D), $$2, $$5);
/*     */     
/*  64 */     if (entityHitResult2 != null) {
/*  65 */       entityHitResult1 = entityHitResult2;
/*     */     }
/*     */     
/*  68 */     return (HitResult)entityHitResult1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static EntityHitResult getEntityHitResult(Entity $$0, Vec3 $$1, Vec3 $$2, AABB $$3, Predicate<Entity> $$4, double $$5) {
/*  73 */     Level $$6 = $$0.level();
/*  74 */     double $$7 = $$5;
/*  75 */     Entity $$8 = null;
/*  76 */     Vec3 $$9 = null;
/*     */     
/*  78 */     for (Entity $$10 : $$6.getEntities($$0, $$3, $$4)) {
/*  79 */       AABB $$11 = $$10.getBoundingBox().inflate($$10.getPickRadius());
/*  80 */       Optional<Vec3> $$12 = $$11.clip($$1, $$2);
/*  81 */       if ($$11.contains($$1)) {
/*  82 */         if ($$7 >= 0.0D) {
/*  83 */           $$8 = $$10;
/*  84 */           $$9 = $$12.orElse($$1);
/*  85 */           $$7 = 0.0D;
/*     */         }  continue;
/*     */       } 
/*  88 */       if ($$12.isPresent()) {
/*  89 */         Vec3 $$13 = $$12.get();
/*  90 */         double $$14 = $$1.distanceToSqr($$13);
/*  91 */         if ($$14 < $$7 || $$7 == 0.0D) {
/*  92 */           if ($$10.getRootVehicle() == $$0.getRootVehicle()) {
/*  93 */             if ($$7 == 0.0D) {
/*  94 */               $$8 = $$10;
/*  95 */               $$9 = $$13;
/*     */             }  continue;
/*     */           } 
/*  98 */           $$8 = $$10;
/*  99 */           $$9 = $$13;
/* 100 */           $$7 = $$14;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 107 */     if ($$8 == null) {
/* 108 */       return null;
/*     */     }
/* 110 */     return new EntityHitResult($$8, $$9);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static EntityHitResult getEntityHitResult(Level $$0, Entity $$1, Vec3 $$2, Vec3 $$3, AABB $$4, Predicate<Entity> $$5) {
/* 115 */     return getEntityHitResult($$0, $$1, $$2, $$3, $$4, $$5, 0.3F);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static EntityHitResult getEntityHitResult(Level $$0, Entity $$1, Vec3 $$2, Vec3 $$3, AABB $$4, Predicate<Entity> $$5, float $$6) {
/* 120 */     double $$7 = Double.MAX_VALUE;
/* 121 */     Entity $$8 = null;
/*     */     
/* 123 */     for (Entity $$9 : $$0.getEntities($$1, $$4, $$5)) {
/* 124 */       AABB $$10 = $$9.getBoundingBox().inflate($$6);
/* 125 */       Optional<Vec3> $$11 = $$10.clip($$2, $$3);
/* 126 */       if ($$11.isPresent()) {
/* 127 */         double $$12 = $$2.distanceToSqr($$11.get());
/* 128 */         if ($$12 < $$7) {
/* 129 */           $$8 = $$9;
/* 130 */           $$7 = $$12;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     if ($$8 == null) {
/* 136 */       return null;
/*     */     }
/* 138 */     return new EntityHitResult($$8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void rotateTowardsMovement(Entity $$0, float $$1) {
/* 145 */     Vec3 $$2 = $$0.getDeltaMovement();
/*     */     
/* 147 */     if ($$2.lengthSqr() == 0.0D) {
/*     */       return;
/*     */     }
/*     */     
/* 151 */     double $$3 = $$2.horizontalDistance();
/* 152 */     $$0.setYRot((float)(Mth.atan2($$2.z, $$2.x) * 57.2957763671875D) + 90.0F);
/* 153 */     $$0.setXRot((float)(Mth.atan2($$3, $$2.y) * 57.2957763671875D) - 90.0F);
/*     */     
/* 155 */     while ($$0.getXRot() - $$0.xRotO < -180.0F) {
/* 156 */       $$0.xRotO -= 360.0F;
/*     */     }
/* 158 */     while ($$0.getXRot() - $$0.xRotO >= 180.0F) {
/* 159 */       $$0.xRotO += 360.0F;
/*     */     }
/*     */     
/* 162 */     while ($$0.getYRot() - $$0.yRotO < -180.0F) {
/* 163 */       $$0.yRotO -= 360.0F;
/*     */     }
/* 165 */     while ($$0.getYRot() - $$0.yRotO >= 180.0F) {
/* 166 */       $$0.yRotO += 360.0F;
/*     */     }
/*     */     
/* 169 */     $$0.setXRot(Mth.lerp($$1, $$0.xRotO, $$0.getXRot()));
/* 170 */     $$0.setYRot(Mth.lerp($$1, $$0.yRotO, $$0.getYRot()));
/*     */   }
/*     */   
/*     */   public static InteractionHand getWeaponHoldingHand(LivingEntity $$0, Item $$1) {
/* 174 */     return $$0.getMainHandItem().is($$1) ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
/*     */   }
/*     */   
/*     */   public static AbstractArrow getMobArrow(LivingEntity $$0, ItemStack $$1, float $$2) {
/* 178 */     ArrowItem $$3 = ($$1.getItem() instanceof ArrowItem) ? (ArrowItem)$$1.getItem() : (ArrowItem)Items.ARROW;
/* 179 */     AbstractArrow $$4 = $$3.createArrow($$0.level(), $$1, $$0);
/* 180 */     $$4.setEnchantmentEffectsFromEntity($$0, $$2);
/*     */     
/* 182 */     if ($$1.is(Items.TIPPED_ARROW) && 
/* 183 */       $$4 instanceof Arrow) {
/* 184 */       ((Arrow)$$4).setEffectsFromItem($$1);
/*     */     }
/*     */ 
/*     */     
/* 188 */     return $$4;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\ProjectileUtil.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */