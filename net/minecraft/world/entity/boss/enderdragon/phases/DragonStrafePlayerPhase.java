/*     */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*     */ import net.minecraft.world.entity.projectile.DragonFireball;
/*     */ import net.minecraft.world.level.pathfinder.Node;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class DragonStrafePlayerPhase
/*     */   extends AbstractDragonPhaseInstance {
/*  18 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int FIREBALL_CHARGE_AMOUNT = 5;
/*     */   private int fireballCharge;
/*     */   @Nullable
/*     */   private Path currentPath;
/*     */   @Nullable
/*     */   private Vec3 targetLocation;
/*     */   @Nullable
/*     */   private LivingEntity attackTarget;
/*     */   private boolean holdingPatternClockwise;
/*     */   
/*     */   public DragonStrafePlayerPhase(EnderDragon $$0) {
/*  31 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doServerTick() {
/*  36 */     if (this.attackTarget == null) {
/*  37 */       LOGGER.warn("Skipping player strafe phase because no player was found");
/*  38 */       this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
/*     */       
/*     */       return;
/*     */     } 
/*  42 */     if (this.currentPath != null && this.currentPath.isDone()) {
/*  43 */       double $$0 = this.attackTarget.getX();
/*  44 */       double $$1 = this.attackTarget.getZ();
/*     */       
/*  46 */       double $$2 = $$0 - this.dragon.getX();
/*  47 */       double $$3 = $$1 - this.dragon.getZ();
/*  48 */       double $$4 = Math.sqrt($$2 * $$2 + $$3 * $$3);
/*  49 */       double $$5 = Math.min(0.4000000059604645D + $$4 / 80.0D - 1.0D, 10.0D);
/*     */       
/*  51 */       this.targetLocation = new Vec3($$0, this.attackTarget.getY() + $$5, $$1);
/*     */     } 
/*     */     
/*  54 */     double $$6 = (this.targetLocation == null) ? 0.0D : this.targetLocation.distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
/*  55 */     if ($$6 < 100.0D || $$6 > 22500.0D) {
/*  56 */       findNewTarget();
/*     */     }
/*     */     
/*  59 */     double $$7 = 64.0D;
/*  60 */     if (this.attackTarget.distanceToSqr((Entity)this.dragon) < 4096.0D) {
/*  61 */       if (this.dragon.hasLineOfSight((Entity)this.attackTarget)) {
/*  62 */         this.fireballCharge++;
/*  63 */         Vec3 $$8 = (new Vec3(this.attackTarget.getX() - this.dragon.getX(), 0.0D, this.attackTarget.getZ() - this.dragon.getZ())).normalize();
/*  64 */         Vec3 $$9 = (new Vec3(Mth.sin(this.dragon.getYRot() * 0.017453292F), 0.0D, -Mth.cos(this.dragon.getYRot() * 0.017453292F))).normalize();
/*  65 */         float $$10 = (float)$$9.dot($$8);
/*  66 */         float $$11 = (float)(Math.acos($$10) * 57.2957763671875D);
/*  67 */         $$11 += 0.5F;
/*     */         
/*  69 */         if (this.fireballCharge >= 5 && $$11 >= 0.0F && $$11 < 10.0F) {
/*  70 */           double $$12 = 1.0D;
/*  71 */           Vec3 $$13 = this.dragon.getViewVector(1.0F);
/*  72 */           double $$14 = this.dragon.head.getX() - $$13.x * 1.0D;
/*  73 */           double $$15 = this.dragon.head.getY(0.5D) + 0.5D;
/*  74 */           double $$16 = this.dragon.head.getZ() - $$13.z * 1.0D;
/*     */           
/*  76 */           double $$17 = this.attackTarget.getX() - $$14;
/*  77 */           double $$18 = this.attackTarget.getY(0.5D) - $$15;
/*  78 */           double $$19 = this.attackTarget.getZ() - $$16;
/*     */           
/*  80 */           if (!this.dragon.isSilent()) {
/*  81 */             this.dragon.level().levelEvent(null, 1017, this.dragon.blockPosition(), 0);
/*     */           }
/*  83 */           DragonFireball $$20 = new DragonFireball(this.dragon.level(), (LivingEntity)this.dragon, $$17, $$18, $$19);
/*  84 */           $$20.moveTo($$14, $$15, $$16, 0.0F, 0.0F);
/*  85 */           this.dragon.level().addFreshEntity((Entity)$$20);
/*  86 */           this.fireballCharge = 0;
/*     */           
/*  88 */           if (this.currentPath != null) {
/*  89 */             while (!this.currentPath.isDone()) {
/*  90 */               this.currentPath.advance();
/*     */             }
/*     */           }
/*     */           
/*  94 */           this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
/*     */         }
/*     */       
/*  97 */       } else if (this.fireballCharge > 0) {
/*  98 */         this.fireballCharge--;
/*     */       }
/*     */     
/*     */     }
/* 102 */     else if (this.fireballCharge > 0) {
/* 103 */       this.fireballCharge--;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void findNewTarget() {
/* 109 */     if (this.currentPath == null || this.currentPath.isDone()) {
/* 110 */       int $$0 = this.dragon.findClosestNode();
/* 111 */       int $$1 = $$0;
/*     */       
/* 113 */       if (this.dragon.getRandom().nextInt(8) == 0) {
/* 114 */         this.holdingPatternClockwise = !this.holdingPatternClockwise;
/* 115 */         $$1 += 6;
/*     */       } 
/*     */       
/* 118 */       if (this.holdingPatternClockwise) {
/* 119 */         $$1++;
/*     */       } else {
/* 121 */         $$1--;
/*     */       } 
/*     */       
/* 124 */       if (this.dragon.getDragonFight() == null || this.dragon.getDragonFight().getCrystalsAlive() <= 0) {
/*     */         
/* 126 */         $$1 -= 12;
/* 127 */         $$1 &= 0x7;
/* 128 */         $$1 += 12;
/*     */       } else {
/*     */         
/* 131 */         $$1 %= 12;
/* 132 */         if ($$1 < 0) {
/* 133 */           $$1 += 12;
/*     */         }
/*     */       } 
/*     */       
/* 137 */       this.currentPath = this.dragon.findPath($$0, $$1, null);
/*     */       
/* 139 */       if (this.currentPath != null) {
/* 140 */         this.currentPath.advance();
/*     */       }
/*     */     } 
/*     */     
/* 144 */     navigateToNextPathNode();
/*     */   }
/*     */   
/*     */   private void navigateToNextPathNode() {
/* 148 */     if (this.currentPath != null && !this.currentPath.isDone()) {
/* 149 */       double $$3; BlockPos blockPos = this.currentPath.getNextNodePos();
/*     */       
/* 151 */       this.currentPath.advance();
/* 152 */       double $$1 = blockPos.getX();
/*     */       
/* 154 */       double $$2 = blockPos.getZ();
/*     */       
/*     */       do {
/* 157 */         $$3 = (blockPos.getY() + this.dragon.getRandom().nextFloat() * 20.0F);
/* 158 */       } while ($$3 < blockPos.getY());
/*     */       
/* 160 */       this.targetLocation = new Vec3($$1, $$3, $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void begin() {
/* 166 */     this.fireballCharge = 0;
/* 167 */     this.targetLocation = null;
/* 168 */     this.currentPath = null;
/* 169 */     this.attackTarget = null;
/*     */   }
/*     */   
/*     */   public void setTarget(LivingEntity $$0) {
/* 173 */     this.attackTarget = $$0;
/*     */     
/* 175 */     int $$1 = this.dragon.findClosestNode();
/* 176 */     int $$2 = this.dragon.findClosestNode(this.attackTarget.getX(), this.attackTarget.getY(), this.attackTarget.getZ());
/*     */     
/* 178 */     int $$3 = this.attackTarget.getBlockX();
/* 179 */     int $$4 = this.attackTarget.getBlockZ();
/*     */     
/* 181 */     double $$5 = $$3 - this.dragon.getX();
/* 182 */     double $$6 = $$4 - this.dragon.getZ();
/* 183 */     double $$7 = Math.sqrt($$5 * $$5 + $$6 * $$6);
/* 184 */     double $$8 = Math.min(0.4000000059604645D + $$7 / 80.0D - 1.0D, 10.0D);
/* 185 */     int $$9 = Mth.floor(this.attackTarget.getY() + $$8);
/*     */     
/* 187 */     Node $$10 = new Node($$3, $$9, $$4);
/*     */     
/* 189 */     this.currentPath = this.dragon.findPath($$1, $$2, $$10);
/*     */     
/* 191 */     if (this.currentPath != null) {
/* 192 */       this.currentPath.advance();
/*     */       
/* 194 */       navigateToNextPathNode();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Vec3 getFlyTargetLocation() {
/* 201 */     return this.targetLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnderDragonPhase<DragonStrafePlayerPhase> getPhase() {
/* 206 */     return EnderDragonPhase.STRAFE_PLAYER;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\DragonStrafePlayerPhase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */