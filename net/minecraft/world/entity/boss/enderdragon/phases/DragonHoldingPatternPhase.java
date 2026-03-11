/*     */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class DragonHoldingPatternPhase extends AbstractDragonPhaseInstance {
/*  18 */   private static final TargetingConditions NEW_TARGET_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight();
/*     */   
/*     */   @Nullable
/*     */   private Path currentPath;
/*     */   @Nullable
/*     */   private Vec3 targetLocation;
/*     */   private boolean clockwise;
/*     */   
/*     */   public DragonHoldingPatternPhase(EnderDragon $$0) {
/*  27 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnderDragonPhase<DragonHoldingPatternPhase> getPhase() {
/*  32 */     return EnderDragonPhase.HOLDING_PATTERN;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doServerTick() {
/*  37 */     double $$0 = (this.targetLocation == null) ? 0.0D : this.targetLocation.distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
/*  38 */     if ($$0 < 100.0D || $$0 > 22500.0D || this.dragon.horizontalCollision || this.dragon.verticalCollision) {
/*  39 */       findNewTarget();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void begin() {
/*  45 */     this.currentPath = null;
/*  46 */     this.targetLocation = null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Vec3 getFlyTargetLocation() {
/*  52 */     return this.targetLocation;
/*     */   }
/*     */   
/*     */   private void findNewTarget() {
/*  56 */     if (this.currentPath != null && this.currentPath.isDone()) {
/*  57 */       double $$4; BlockPos $$0 = this.dragon.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos((Vec3i)EndPodiumFeature.getLocation(this.dragon.getFightOrigin())));
/*     */ 
/*     */ 
/*     */       
/*  61 */       int $$1 = (this.dragon.getDragonFight() == null) ? 0 : this.dragon.getDragonFight().getCrystalsAlive();
/*     */       
/*  63 */       if (this.dragon.getRandom().nextInt($$1 + 3) == 0) {
/*  64 */         this.dragon.getPhaseManager().setPhase(EnderDragonPhase.LANDING_APPROACH);
/*     */         
/*     */         return;
/*     */       } 
/*  68 */       Player $$2 = this.dragon.level().getNearestPlayer(NEW_TARGET_TARGETING, (LivingEntity)this.dragon, $$0.getX(), $$0.getY(), $$0.getZ());
/*  69 */       if ($$2 != null) {
/*  70 */         double $$3 = $$0.distToCenterSqr((Position)$$2.position()) / 512.0D;
/*     */       } else {
/*  72 */         $$4 = 64.0D;
/*     */       } 
/*  74 */       if ($$2 != null && (this.dragon.getRandom().nextInt((int)($$4 + 2.0D)) == 0 || this.dragon.getRandom().nextInt($$1 + 2) == 0)) {
/*     */         
/*  76 */         strafePlayer($$2);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     if (this.currentPath == null || this.currentPath.isDone()) {
/*  83 */       int $$5 = this.dragon.findClosestNode();
/*  84 */       int $$6 = $$5;
/*     */       
/*  86 */       if (this.dragon.getRandom().nextInt(8) == 0) {
/*  87 */         this.clockwise = !this.clockwise;
/*  88 */         $$6 += 6;
/*     */       } 
/*     */       
/*  91 */       if (this.clockwise) {
/*  92 */         $$6++;
/*     */       } else {
/*  94 */         $$6--;
/*     */       } 
/*     */       
/*  97 */       if (this.dragon.getDragonFight() == null || this.dragon.getDragonFight().getCrystalsAlive() < 0) {
/*     */         
/*  99 */         $$6 -= 12;
/* 100 */         $$6 &= 0x7;
/* 101 */         $$6 += 12;
/*     */       } else {
/*     */         
/* 104 */         $$6 %= 12;
/* 105 */         if ($$6 < 0) {
/* 106 */           $$6 += 12;
/*     */         }
/*     */       } 
/*     */       
/* 110 */       this.currentPath = this.dragon.findPath($$5, $$6, null);
/* 111 */       if (this.currentPath != null) {
/* 112 */         this.currentPath.advance();
/*     */       }
/*     */     } 
/*     */     
/* 116 */     navigateToNextPathNode();
/*     */   }
/*     */   
/*     */   private void strafePlayer(Player $$0) {
/* 120 */     this.dragon.getPhaseManager().setPhase(EnderDragonPhase.STRAFE_PLAYER);
/* 121 */     ((DragonStrafePlayerPhase)this.dragon.getPhaseManager().<DragonStrafePlayerPhase>getPhase(EnderDragonPhase.STRAFE_PLAYER)).setTarget((LivingEntity)$$0);
/*     */   }
/*     */   
/*     */   private void navigateToNextPathNode() {
/* 125 */     if (this.currentPath != null && !this.currentPath.isDone()) {
/* 126 */       double $$3; BlockPos blockPos = this.currentPath.getNextNodePos();
/*     */       
/* 128 */       this.currentPath.advance();
/* 129 */       double $$1 = blockPos.getX();
/* 130 */       double $$2 = blockPos.getZ();
/*     */ 
/*     */       
/*     */       do {
/* 134 */         $$3 = (blockPos.getY() + this.dragon.getRandom().nextFloat() * 20.0F);
/* 135 */       } while ($$3 < blockPos.getY());
/*     */       
/* 137 */       this.targetLocation = new Vec3($$1, $$3, $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCrystalDestroyed(EndCrystal $$0, BlockPos $$1, DamageSource $$2, @Nullable Player $$3) {
/* 143 */     if ($$3 != null && this.dragon.canAttack((LivingEntity)$$3))
/* 144 */       strafePlayer($$3); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\DragonHoldingPatternPhase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */