/*     */ package net.minecraft.world.level.block.entity;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.SculkSensorBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.BlockPositionSource;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.gameevent.GameEventListener;
/*     */ import net.minecraft.world.level.gameevent.PositionSource;
/*     */ import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SculkSensorBlockEntity extends BlockEntity implements GameEventListener.Holder<VibrationSystem.Listener>, VibrationSystem {
/*  23 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private VibrationSystem.Data vibrationData;
/*     */   private final VibrationSystem.Listener vibrationListener;
/*     */   private final VibrationSystem.User vibrationUser;
/*     */   private int lastVibrationFrequency;
/*     */   
/*     */   protected SculkSensorBlockEntity(BlockEntityType<?> $$0, BlockPos $$1, BlockState $$2) {
/*  30 */     super($$0, $$1, $$2);
/*  31 */     this.vibrationUser = createVibrationUser();
/*  32 */     this.vibrationData = new VibrationSystem.Data();
/*  33 */     this.vibrationListener = new VibrationSystem.Listener(this);
/*     */   }
/*     */   
/*     */   public SculkSensorBlockEntity(BlockPos $$0, BlockState $$1) {
/*  37 */     this(BlockEntityType.SCULK_SENSOR, $$0, $$1);
/*     */   }
/*     */   
/*     */   public VibrationSystem.User createVibrationUser() {
/*  41 */     return new VibrationUser(getBlockPos());
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/*  46 */     super.load($$0);
/*  47 */     this.lastVibrationFrequency = $$0.getInt("last_vibration_frequency");
/*     */     
/*  49 */     if ($$0.contains("listener", 10)) {
/*     */ 
/*     */       
/*  52 */       Objects.requireNonNull(LOGGER); VibrationSystem.Data.CODEC.parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$0.getCompound("listener"))).resultOrPartial(LOGGER::error)
/*  53 */         .ifPresent($$0 -> this.vibrationData = $$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*  59 */     super.saveAdditional($$0);
/*  60 */     $$0.putInt("last_vibration_frequency", this.lastVibrationFrequency);
/*     */ 
/*     */ 
/*     */     
/*  64 */     Objects.requireNonNull(LOGGER); VibrationSystem.Data.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.vibrationData).resultOrPartial(LOGGER::error)
/*  65 */       .ifPresent($$1 -> $$0.put("listener", $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public VibrationSystem.Data getVibrationData() {
/*  70 */     return this.vibrationData;
/*     */   }
/*     */ 
/*     */   
/*     */   public VibrationSystem.User getVibrationUser() {
/*  75 */     return this.vibrationUser;
/*     */   }
/*     */   
/*     */   public int getLastVibrationFrequency() {
/*  79 */     return this.lastVibrationFrequency;
/*     */   }
/*     */   
/*     */   public void setLastVibrationFrequency(int $$0) {
/*  83 */     this.lastVibrationFrequency = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public VibrationSystem.Listener getListener() {
/*  88 */     return this.vibrationListener;
/*     */   }
/*     */   
/*     */   protected class VibrationUser
/*     */     implements VibrationSystem.User {
/*     */     public static final int LISTENER_RANGE = 8;
/*     */     protected final BlockPos blockPos;
/*     */     private final PositionSource positionSource;
/*     */     
/*     */     public VibrationUser(BlockPos $$1) {
/*  98 */       this.blockPos = $$1;
/*  99 */       this.positionSource = (PositionSource)new BlockPositionSource($$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getListenerRadius() {
/* 104 */       return 8;
/*     */     }
/*     */ 
/*     */     
/*     */     public PositionSource getPositionSource() {
/* 109 */       return this.positionSource;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canTriggerAvoidVibration() {
/* 114 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean canReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, @Nullable GameEvent.Context $$3) {
/* 123 */       if ($$1.equals(this.blockPos) && ($$2 == GameEvent.BLOCK_DESTROY || $$2 == GameEvent.BLOCK_PLACE)) {
/* 124 */         return false;
/*     */       }
/*     */       
/* 127 */       return SculkSensorBlock.canActivate(SculkSensorBlockEntity.this.getBlockState());
/*     */     }
/*     */ 
/*     */     
/*     */     public void onReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, @Nullable Entity $$3, @Nullable Entity $$4, float $$5) {
/* 132 */       BlockState $$6 = SculkSensorBlockEntity.this.getBlockState();
/* 133 */       if (SculkSensorBlock.canActivate($$6)) {
/* 134 */         SculkSensorBlockEntity.this.setLastVibrationFrequency(VibrationSystem.getGameEventFrequency($$2));
/* 135 */         int $$7 = VibrationSystem.getRedstoneStrengthForDistance($$5, getListenerRadius());
/* 136 */         Block block = $$6.getBlock(); if (block instanceof SculkSensorBlock) { SculkSensorBlock $$8 = (SculkSensorBlock)block;
/* 137 */           $$8.activate($$3, (Level)$$0, this.blockPos, $$6, $$7, SculkSensorBlockEntity.this.getLastVibrationFrequency()); }
/*     */       
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onDataChanged() {
/* 144 */       SculkSensorBlockEntity.this.setChanged();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean requiresAdjacentChunksToBeTicking() {
/* 149 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\SculkSensorBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */