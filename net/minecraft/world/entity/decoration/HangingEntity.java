/*     */ package net.minecraft.world.entity.decoration;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LightningBolt;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.DiodeBlock;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public abstract class HangingEntity extends Entity {
/*     */   protected static final Predicate<Entity> HANGING_ENTITY;
/*  31 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   static {
/*  33 */     HANGING_ENTITY = ($$0 -> $$0 instanceof HangingEntity);
/*     */   }
/*     */   private int checkInterval;
/*     */   protected BlockPos pos;
/*  37 */   protected Direction direction = Direction.SOUTH;
/*     */   
/*     */   protected HangingEntity(EntityType<? extends HangingEntity> $$0, Level $$1) {
/*  40 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   protected HangingEntity(EntityType<? extends HangingEntity> $$0, Level $$1, BlockPos $$2) {
/*  44 */     this($$0, $$1);
/*  45 */     this.pos = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {}
/*     */ 
/*     */   
/*     */   protected void setDirection(Direction $$0) {
/*  53 */     Validate.notNull($$0);
/*  54 */     Validate.isTrue($$0.getAxis().isHorizontal());
/*     */     
/*  56 */     this.direction = $$0;
/*  57 */     setYRot((this.direction.get2DDataValue() * 90));
/*  58 */     this.yRotO = getYRot();
/*     */     
/*  60 */     recalculateBoundingBox();
/*     */   }
/*     */   
/*     */   protected void recalculateBoundingBox() {
/*  64 */     if (this.direction == null) {
/*     */       return;
/*     */     }
/*     */     
/*  68 */     double $$0 = this.pos.getX() + 0.5D;
/*  69 */     double $$1 = this.pos.getY() + 0.5D;
/*  70 */     double $$2 = this.pos.getZ() + 0.5D;
/*     */     
/*  72 */     double $$3 = 0.46875D;
/*  73 */     double $$4 = offs(getWidth());
/*  74 */     double $$5 = offs(getHeight());
/*     */     
/*  76 */     $$0 -= this.direction.getStepX() * 0.46875D;
/*  77 */     $$2 -= this.direction.getStepZ() * 0.46875D;
/*  78 */     $$1 += $$5;
/*     */     
/*  80 */     Direction $$6 = this.direction.getCounterClockWise();
/*  81 */     $$0 += $$4 * $$6.getStepX();
/*  82 */     $$2 += $$4 * $$6.getStepZ();
/*     */     
/*  84 */     setPosRaw($$0, $$1, $$2);
/*     */     
/*  86 */     double $$7 = getWidth();
/*  87 */     double $$8 = getHeight();
/*  88 */     double $$9 = getWidth();
/*     */     
/*  90 */     if (this.direction.getAxis() == Direction.Axis.Z) {
/*  91 */       $$9 = 1.0D;
/*     */     } else {
/*  93 */       $$7 = 1.0D;
/*     */     } 
/*     */     
/*  96 */     $$7 /= 32.0D;
/*  97 */     $$8 /= 32.0D;
/*  98 */     $$9 /= 32.0D;
/*  99 */     setBoundingBox(new AABB($$0 - $$7, $$1 - $$8, $$2 - $$9, $$0 + $$7, $$1 + $$8, $$2 + $$9));
/*     */   }
/*     */   
/*     */   private double offs(int $$0) {
/* 103 */     return ($$0 % 32 == 0) ? 0.5D : 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 109 */     checkBelowWorld();
/* 110 */     if (!(level()).isClientSide && this.checkInterval++ == 100) {
/* 111 */       this.checkInterval = 0;
/* 112 */       if (!isRemoved() && !survives()) {
/* 113 */         discard();
/* 114 */         dropItem((Entity)null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean survives() {
/* 121 */     if (!level().noCollision(this)) {
/* 122 */       return false;
/*     */     }
/*     */     
/* 125 */     int $$0 = Math.max(1, getWidth() / 16);
/* 126 */     int $$1 = Math.max(1, getHeight() / 16);
/*     */     
/* 128 */     BlockPos $$2 = this.pos.relative(this.direction.getOpposite());
/* 129 */     Direction $$3 = this.direction.getCounterClockWise();
/*     */     
/* 131 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/* 132 */     for (int $$5 = 0; $$5 < $$0; $$5++) {
/* 133 */       for (int $$6 = 0; $$6 < $$1; $$6++) {
/* 134 */         int $$7 = ($$0 - 1) / -2;
/* 135 */         int $$8 = ($$1 - 1) / -2;
/*     */         
/* 137 */         $$4.set((Vec3i)$$2).move($$3, $$5 + $$7).move(Direction.UP, $$6 + $$8);
/*     */         
/* 139 */         BlockState $$9 = level().getBlockState((BlockPos)$$4);
/* 140 */         if (!$$9.isSolid() && !DiodeBlock.isDiode($$9)) {
/* 141 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     return level().getEntities(this, getBoundingBox(), HANGING_ENTITY).isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPickable() {
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean skipAttackInteraction(Entity $$0) {
/* 156 */     if ($$0 instanceof Player) { Player $$1 = (Player)$$0;
/* 157 */       if (!level().mayInteract($$1, this.pos)) {
/* 158 */         return true;
/*     */       }
/* 160 */       return hurt(damageSources().playerAttack($$1), 0.0F); }
/*     */     
/* 162 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Direction getDirection() {
/* 167 */     return this.direction;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 172 */     if (isInvulnerableTo($$0)) {
/* 173 */       return false;
/*     */     }
/* 175 */     if (!isRemoved() && !(level()).isClientSide) {
/* 176 */       kill();
/* 177 */       markHurt();
/* 178 */       dropItem($$0.getEntity());
/*     */     } 
/* 180 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(MoverType $$0, Vec3 $$1) {
/* 185 */     if (!(level()).isClientSide && !isRemoved() && $$1.lengthSqr() > 0.0D) {
/* 186 */       kill();
/* 187 */       dropItem((Entity)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(double $$0, double $$1, double $$2) {
/* 193 */     if (!(level()).isClientSide && !isRemoved() && $$0 * $$0 + $$1 * $$1 + $$2 * $$2 > 0.0D) {
/* 194 */       kill();
/* 195 */       dropItem((Entity)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 201 */     BlockPos $$1 = getPos();
/* 202 */     $$0.putInt("TileX", $$1.getX());
/* 203 */     $$0.putInt("TileY", $$1.getY());
/* 204 */     $$0.putInt("TileZ", $$1.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 209 */     BlockPos $$1 = new BlockPos($$0.getInt("TileX"), $$0.getInt("TileY"), $$0.getInt("TileZ"));
/* 210 */     if (!$$1.closerThan((Vec3i)blockPosition(), 16.0D)) {
/* 211 */       LOGGER.error("Hanging entity at invalid position: {}", $$1);
/*     */       return;
/*     */     } 
/* 214 */     this.pos = $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemEntity spawnAtLocation(ItemStack $$0, float $$1) {
/* 227 */     ItemEntity $$2 = new ItemEntity(level(), getX() + (this.direction.getStepX() * 0.15F), getY() + $$1, getZ() + (this.direction.getStepZ() * 0.15F), $$0);
/* 228 */     $$2.setDefaultPickUpDelay();
/* 229 */     level().addFreshEntity((Entity)$$2);
/* 230 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean repositionEntityAfterLoad() {
/* 235 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPos(double $$0, double $$1, double $$2) {
/* 240 */     this.pos = BlockPos.containing($$0, $$1, $$2);
/* 241 */     recalculateBoundingBox();
/* 242 */     this.hasImpulse = true;
/*     */   }
/*     */   
/*     */   public BlockPos getPos() {
/* 246 */     return this.pos;
/*     */   }
/*     */ 
/*     */   
/*     */   public float rotate(Rotation $$0) {
/* 251 */     if (this.direction.getAxis() != Direction.Axis.Y) {
/* 252 */       switch ($$0) {
/*     */         case CLOCKWISE_180:
/* 254 */           this.direction = this.direction.getOpposite();
/*     */           break;
/*     */         case COUNTERCLOCKWISE_90:
/* 257 */           this.direction = this.direction.getCounterClockWise();
/*     */           break;
/*     */         case CLOCKWISE_90:
/* 260 */           this.direction = this.direction.getClockWise();
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 267 */     float $$1 = Mth.wrapDegrees(getYRot());
/* 268 */     switch ($$0) {
/*     */       case CLOCKWISE_180:
/* 270 */         return $$1 + 180.0F;
/*     */       case COUNTERCLOCKWISE_90:
/* 272 */         return $$1 + 90.0F;
/*     */       case CLOCKWISE_90:
/* 274 */         return $$1 + 270.0F;
/*     */     } 
/* 276 */     return $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float mirror(Mirror $$0) {
/* 282 */     return rotate($$0.getRotation(this.direction));
/*     */   }
/*     */   
/*     */   public void thunderHit(ServerLevel $$0, LightningBolt $$1) {}
/*     */   
/*     */   public void refreshDimensions() {}
/*     */   
/*     */   public abstract int getWidth();
/*     */   
/*     */   public abstract int getHeight();
/*     */   
/*     */   public abstract void dropItem(@Nullable Entity paramEntity);
/*     */   
/*     */   public abstract void playPlacementSound();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\decoration\HangingEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */