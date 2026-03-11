/*     */ package net.minecraft.world.entity.ai.control;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.level.pathfinder.NodeEvaluator;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class MoveControl
/*     */   implements Control {
/*     */   public static final float MIN_SPEED = 5.0E-4F;
/*     */   public static final float MIN_SPEED_SQR = 2.5000003E-7F;
/*     */   protected static final int MAX_TURN = 90;
/*     */   protected final Mob mob;
/*     */   protected double wantedX;
/*     */   protected double wantedY;
/*     */   protected double wantedZ;
/*     */   protected double speedModifier;
/*     */   protected float strafeForwards;
/*     */   protected float strafeRight;
/*  28 */   protected Operation operation = Operation.WAIT;
/*     */   
/*     */   public MoveControl(Mob $$0) {
/*  31 */     this.mob = $$0;
/*     */   }
/*     */   
/*     */   public boolean hasWanted() {
/*  35 */     return (this.operation == Operation.MOVE_TO);
/*     */   }
/*     */   
/*     */   public double getSpeedModifier() {
/*  39 */     return this.speedModifier;
/*     */   }
/*     */   
/*     */   public void setWantedPosition(double $$0, double $$1, double $$2, double $$3) {
/*  43 */     this.wantedX = $$0;
/*  44 */     this.wantedY = $$1;
/*  45 */     this.wantedZ = $$2;
/*  46 */     this.speedModifier = $$3;
/*  47 */     if (this.operation != Operation.JUMPING) {
/*  48 */       this.operation = Operation.MOVE_TO;
/*     */     }
/*     */   }
/*     */   
/*     */   public void strafe(float $$0, float $$1) {
/*  53 */     this.operation = Operation.STRAFE;
/*  54 */     this.strafeForwards = $$0;
/*  55 */     this.strafeRight = $$1;
/*  56 */     this.speedModifier = 0.25D;
/*     */   }
/*     */   
/*     */   public void tick() {
/*  60 */     if (this.operation == Operation.STRAFE) {
/*  61 */       float $$0 = (float)this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
/*  62 */       float $$1 = (float)this.speedModifier * $$0;
/*     */       
/*  64 */       float $$2 = this.strafeForwards;
/*  65 */       float $$3 = this.strafeRight;
/*  66 */       float $$4 = Mth.sqrt($$2 * $$2 + $$3 * $$3);
/*  67 */       if ($$4 < 1.0F) {
/*  68 */         $$4 = 1.0F;
/*     */       }
/*  70 */       $$4 = $$1 / $$4;
/*  71 */       $$2 *= $$4;
/*  72 */       $$3 *= $$4;
/*     */       
/*  74 */       float $$5 = Mth.sin(this.mob.getYRot() * 0.017453292F);
/*  75 */       float $$6 = Mth.cos(this.mob.getYRot() * 0.017453292F);
/*  76 */       float $$7 = $$2 * $$6 - $$3 * $$5;
/*  77 */       float $$8 = $$3 * $$6 + $$2 * $$5;
/*     */       
/*  79 */       if (!isWalkable($$7, $$8)) {
/*     */         
/*  81 */         this.strafeForwards = 1.0F;
/*  82 */         this.strafeRight = 0.0F;
/*     */       } 
/*     */       
/*  85 */       this.mob.setSpeed($$1);
/*  86 */       this.mob.setZza(this.strafeForwards);
/*  87 */       this.mob.setXxa(this.strafeRight);
/*     */       
/*  89 */       this.operation = Operation.WAIT;
/*  90 */     } else if (this.operation == Operation.MOVE_TO) {
/*  91 */       this.operation = Operation.WAIT;
/*     */       
/*  93 */       double $$9 = this.wantedX - this.mob.getX();
/*  94 */       double $$10 = this.wantedZ - this.mob.getZ();
/*  95 */       double $$11 = this.wantedY - this.mob.getY();
/*  96 */       double $$12 = $$9 * $$9 + $$11 * $$11 + $$10 * $$10;
/*  97 */       if ($$12 < 2.500000277905201E-7D) {
/*  98 */         this.mob.setZza(0.0F);
/*     */         
/*     */         return;
/*     */       } 
/* 102 */       float $$13 = (float)(Mth.atan2($$10, $$9) * 57.2957763671875D) - 90.0F;
/*     */       
/* 104 */       this.mob.setYRot(rotlerp(this.mob.getYRot(), $$13, 90.0F));
/* 105 */       this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
/*     */       
/* 107 */       BlockPos $$14 = this.mob.blockPosition();
/* 108 */       BlockState $$15 = this.mob.level().getBlockState($$14);
/* 109 */       VoxelShape $$16 = $$15.getCollisionShape((BlockGetter)this.mob.level(), $$14);
/* 110 */       if (($$11 > this.mob.maxUpStep() && $$9 * $$9 + $$10 * $$10 < Math.max(1.0F, this.mob.getBbWidth())) || (
/* 111 */         !$$16.isEmpty() && this.mob.getY() < $$16.max(Direction.Axis.Y) + $$14.getY() && !$$15.is(BlockTags.DOORS) && !$$15.is(BlockTags.FENCES))) {
/*     */         
/* 113 */         this.mob.getJumpControl().jump();
/* 114 */         this.operation = Operation.JUMPING;
/*     */       } 
/* 116 */     } else if (this.operation == Operation.JUMPING) {
/* 117 */       this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
/* 118 */       if (this.mob.onGround()) {
/* 119 */         this.operation = Operation.WAIT;
/*     */       }
/*     */     } else {
/* 122 */       this.mob.setZza(0.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isWalkable(float $$0, float $$1) {
/* 127 */     PathNavigation $$2 = this.mob.getNavigation();
/* 128 */     if ($$2 != null) {
/* 129 */       NodeEvaluator $$3 = $$2.getNodeEvaluator();
/* 130 */       if ($$3 != null && $$3.getBlockPathType((BlockGetter)this.mob.level(), Mth.floor(this.mob.getX() + $$0), this.mob.getBlockY(), Mth.floor(this.mob.getZ() + $$1)) != BlockPathTypes.WALKABLE) {
/* 131 */         return false;
/*     */       }
/*     */     } 
/* 134 */     return true;
/*     */   }
/*     */   
/*     */   protected float rotlerp(float $$0, float $$1, float $$2) {
/* 138 */     float $$3 = Mth.wrapDegrees($$1 - $$0);
/* 139 */     if ($$3 > $$2) {
/* 140 */       $$3 = $$2;
/*     */     }
/* 142 */     if ($$3 < -$$2) {
/* 143 */       $$3 = -$$2;
/*     */     }
/* 145 */     float $$4 = $$0 + $$3;
/* 146 */     if ($$4 < 0.0F) {
/* 147 */       $$4 += 360.0F;
/* 148 */     } else if ($$4 > 360.0F) {
/* 149 */       $$4 -= 360.0F;
/*     */     } 
/* 151 */     return $$4;
/*     */   }
/*     */   
/*     */   public double getWantedX() {
/* 155 */     return this.wantedX;
/*     */   }
/*     */   
/*     */   public double getWantedY() {
/* 159 */     return this.wantedY;
/*     */   }
/*     */   
/*     */   public double getWantedZ() {
/* 163 */     return this.wantedZ;
/*     */   }
/*     */   
/*     */   protected enum Operation {
/* 167 */     WAIT,
/* 168 */     MOVE_TO,
/* 169 */     STRAFE,
/* 170 */     JUMPING;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\control\MoveControl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */