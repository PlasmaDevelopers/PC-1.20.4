/*     */ package net.minecraft.world.entity.decoration;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class LeashFenceKnotEntity extends HangingEntity {
/*     */   public static final double OFFSET_Y = 0.375D;
/*     */   
/*     */   public LeashFenceKnotEntity(EntityType<? extends LeashFenceKnotEntity> $$0, Level $$1) {
/*  33 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */   
/*     */   public LeashFenceKnotEntity(Level $$0, BlockPos $$1) {
/*  37 */     super(EntityType.LEASH_KNOT, $$0, $$1);
/*  38 */     setPos($$1.getX(), $$1.getY(), $$1.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void recalculateBoundingBox() {
/*  43 */     setPosRaw(this.pos.getX() + 0.5D, this.pos.getY() + 0.375D, this.pos.getZ() + 0.5D);
/*  44 */     double $$0 = getType().getWidth() / 2.0D;
/*  45 */     double $$1 = getType().getHeight();
/*  46 */     setBoundingBox(new AABB(getX() - $$0, getY(), getZ() - $$0, getX() + $$0, getY() + $$1, getZ() + $$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDirection(Direction $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  56 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  61 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getEyeHeight(Pose $$0, EntityDimensions $$1) {
/*  66 */     return 0.0625F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderAtSqrDistance(double $$0) {
/*  71 */     return ($$0 < 1024.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropItem(@Nullable Entity $$0) {
/*  76 */     playSound(SoundEvents.LEASH_KNOT_BREAK, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {}
/*     */ 
/*     */   
/*     */   public InteractionResult interact(Player $$0, InteractionHand $$1) {
/*  89 */     if ((level()).isClientSide) {
/*  90 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/*  93 */     boolean $$2 = false;
/*  94 */     double $$3 = 7.0D;
/*  95 */     List<Mob> $$4 = level().getEntitiesOfClass(Mob.class, new AABB(getX() - 7.0D, getY() - 7.0D, getZ() - 7.0D, getX() + 7.0D, getY() + 7.0D, getZ() + 7.0D));
/*  96 */     for (Mob $$5 : $$4) {
/*  97 */       if ($$5.getLeashHolder() == $$0) {
/*  98 */         $$5.setLeashedTo(this, true);
/*  99 */         $$2 = true;
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     boolean $$6 = false;
/* 104 */     if (!$$2) {
/* 105 */       discard();
/* 106 */       if (($$0.getAbilities()).instabuild) {
/* 107 */         for (Mob $$7 : $$4) {
/* 108 */           if ($$7.isLeashed() && $$7.getLeashHolder() == this) {
/* 109 */             $$7.dropLeash(true, false);
/* 110 */             $$6 = true;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 116 */     if ($$2 || $$6) {
/* 117 */       gameEvent(GameEvent.BLOCK_ATTACH, (Entity)$$0);
/*     */     }
/*     */     
/* 120 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean survives() {
/* 126 */     return level().getBlockState(this.pos).is(BlockTags.FENCES);
/*     */   }
/*     */   
/*     */   public static LeashFenceKnotEntity getOrCreateKnot(Level $$0, BlockPos $$1) {
/* 130 */     int $$2 = $$1.getX();
/* 131 */     int $$3 = $$1.getY();
/* 132 */     int $$4 = $$1.getZ();
/*     */     
/* 134 */     List<LeashFenceKnotEntity> $$5 = $$0.getEntitiesOfClass(LeashFenceKnotEntity.class, new AABB($$2 - 1.0D, $$3 - 1.0D, $$4 - 1.0D, $$2 + 1.0D, $$3 + 1.0D, $$4 + 1.0D));
/* 135 */     for (LeashFenceKnotEntity $$6 : $$5) {
/* 136 */       if ($$6.getPos().equals($$1)) {
/* 137 */         return $$6;
/*     */       }
/*     */     } 
/*     */     
/* 141 */     LeashFenceKnotEntity $$7 = new LeashFenceKnotEntity($$0, $$1);
/* 142 */     $$0.addFreshEntity($$7);
/* 143 */     return $$7;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playPlacementSound() {
/* 148 */     playSound(SoundEvents.LEASH_KNOT_PLACE, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet<ClientGamePacketListener> getAddEntityPacket() {
/* 153 */     return (Packet<ClientGamePacketListener>)new ClientboundAddEntityPacket(this, 0, getPos());
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getRopeHoldPosition(float $$0) {
/* 158 */     return getPosition($$0).add(0.0D, 0.2D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getPickResult() {
/* 163 */     return new ItemStack((ItemLike)Items.LEAD);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\decoration\LeashFenceKnotEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */