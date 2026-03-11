/*     */ package net.minecraft.client.renderer.item;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.GlobalPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class CompassItemPropertyFunction implements ClampedItemPropertyFunction {
/*     */   public static final int DEFAULT_ROTATION = 0;
/*  17 */   private final CompassWobble wobble = new CompassWobble();
/*  18 */   private final CompassWobble wobbleRandom = new CompassWobble();
/*     */   public final CompassTarget compassTarget;
/*     */   
/*     */   public CompassItemPropertyFunction(CompassTarget $$0) {
/*  22 */     this.compassTarget = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public float unclampedCall(ItemStack $$0, @Nullable ClientLevel $$1, @Nullable LivingEntity $$2, int $$3) {
/*  27 */     Entity $$4 = ($$2 != null) ? (Entity)$$2 : $$0.getEntityRepresentation();
/*     */     
/*  29 */     if ($$4 == null) {
/*  30 */       return 0.0F;
/*     */     }
/*     */     
/*  33 */     $$1 = tryFetchLevelIfMissing($$4, $$1);
/*     */     
/*  35 */     if ($$1 == null) {
/*  36 */       return 0.0F;
/*     */     }
/*     */     
/*  39 */     return getCompassRotation($$0, $$1, $$3, $$4);
/*     */   }
/*     */   
/*     */   private float getCompassRotation(ItemStack $$0, ClientLevel $$1, int $$2, Entity $$3) {
/*  43 */     GlobalPos $$4 = this.compassTarget.getPos($$1, $$0, $$3);
/*  44 */     long $$5 = $$1.getGameTime();
/*     */     
/*  46 */     if (!isValidCompassTargetPos($$3, $$4)) {
/*  47 */       return getRandomlySpinningRotation($$2, $$5);
/*     */     }
/*     */     
/*  50 */     return getRotationTowardsCompassTarget($$3, $$5, $$4.pos());
/*     */   }
/*     */   
/*     */   private float getRandomlySpinningRotation(int $$0, long $$1) {
/*  54 */     if (this.wobbleRandom.shouldUpdate($$1)) {
/*  55 */       this.wobbleRandom.update($$1, Math.random());
/*     */     }
/*  57 */     double $$2 = this.wobbleRandom.rotation + (hash($$0) / 2.1474836E9F);
/*  58 */     return Mth.positiveModulo((float)$$2, 1.0F);
/*     */   }
/*     */   
/*     */   private float getRotationTowardsCompassTarget(Entity $$0, long $$1, BlockPos $$2) {
/*  62 */     double $$3 = getAngleFromEntityToPos($$0, $$2);
/*  63 */     double $$4 = getWrappedVisualRotationY($$0);
/*     */ 
/*     */ 
/*     */     
/*  67 */     if ($$0 instanceof Player) { Player $$5 = (Player)$$0; if ($$5.isLocalPlayer())
/*  68 */       { if (this.wobble.shouldUpdate($$1)) {
/*  69 */           this.wobble.update($$1, 0.5D - $$4 - 0.25D);
/*     */         }
/*  71 */         double $$6 = $$3 + this.wobble.rotation;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  76 */         return Mth.positiveModulo((float)$$6, 1.0F); }  }  double $$7 = 0.5D - $$4 - 0.25D - $$3; return Mth.positiveModulo((float)$$7, 1.0F);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ClientLevel tryFetchLevelIfMissing(Entity $$0, @Nullable ClientLevel $$1) {
/*  81 */     if ($$1 == null && 
/*  82 */       $$0.level() instanceof ClientLevel) {
/*  83 */       return (ClientLevel)$$0.level();
/*     */     }
/*     */     
/*  86 */     return $$1;
/*     */   }
/*     */   
/*     */   private boolean isValidCompassTargetPos(Entity $$0, @Nullable GlobalPos $$1) {
/*  90 */     return ($$1 != null && $$1.dimension() == $$0.level().dimension() && $$1
/*  91 */       .pos().distToCenterSqr((Position)$$0.position()) >= 9.999999747378752E-6D);
/*     */   }
/*     */   
/*     */   private double getAngleFromEntityToPos(Entity $$0, BlockPos $$1) {
/*  95 */     Vec3 $$2 = Vec3.atCenterOf((Vec3i)$$1);
/*  96 */     return Math.atan2($$2.z() - $$0.getZ(), $$2.x() - $$0.getX()) / 6.2831854820251465D;
/*     */   }
/*     */   
/*     */   private double getWrappedVisualRotationY(Entity $$0) {
/* 100 */     return Mth.positiveModulo(($$0.getVisualRotationYInDegrees() / 360.0F), 1.0D);
/*     */   }
/*     */   
/*     */   private int hash(int $$0) {
/* 104 */     return $$0 * 1327217883;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class CompassWobble
/*     */   {
/*     */     double rotation;
/*     */     
/*     */     private double deltaRotation;
/*     */     
/*     */     private long lastUpdateTick;
/*     */ 
/*     */     
/*     */     boolean shouldUpdate(long $$0) {
/* 118 */       return (this.lastUpdateTick != $$0);
/*     */     }
/*     */     
/*     */     void update(long $$0, double $$1) {
/* 122 */       this.lastUpdateTick = $$0;
/* 123 */       double $$2 = $$1 - this.rotation;
/* 124 */       $$2 = Mth.positiveModulo($$2 + 0.5D, 1.0D) - 0.5D;
/*     */       
/* 126 */       this.deltaRotation += $$2 * 0.1D;
/* 127 */       this.deltaRotation *= 0.8D;
/* 128 */       this.rotation = Mth.positiveModulo(this.rotation + this.deltaRotation, 1.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface CompassTarget {
/*     */     @Nullable
/*     */     GlobalPos getPos(ClientLevel param1ClientLevel, ItemStack param1ItemStack, Entity param1Entity);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\item\CompassItemPropertyFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */