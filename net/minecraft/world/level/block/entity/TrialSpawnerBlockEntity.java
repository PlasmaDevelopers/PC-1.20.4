/*    */ package net.minecraft.world.level.block.entity;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtOps;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.TrialSpawnerBlock;
/*    */ import net.minecraft.world.level.block.entity.trialspawner.PlayerDetector;
/*    */ import net.minecraft.world.level.block.entity.trialspawner.TrialSpawner;
/*    */ import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerState;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class TrialSpawnerBlockEntity extends BlockEntity implements Spawner, TrialSpawner.StateAccessor {
/* 23 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   private TrialSpawner trialSpawner;
/*    */   
/*    */   public TrialSpawnerBlockEntity(BlockPos $$0, BlockState $$1) {
/* 27 */     super(BlockEntityType.TRIAL_SPAWNER, $$0, $$1);
/* 28 */     PlayerDetector $$2 = PlayerDetector.PLAYERS;
/* 29 */     this.trialSpawner = new TrialSpawner(this, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void load(CompoundTag $$0) {
/* 34 */     super.load($$0);
/*    */ 
/*    */     
/* 37 */     Objects.requireNonNull(LOGGER); this.trialSpawner.codec().parse((DynamicOps)NbtOps.INSTANCE, $$0).resultOrPartial(LOGGER::error)
/* 38 */       .ifPresent($$0 -> this.trialSpawner = $$0);
/*    */     
/* 40 */     if (this.level != null) {
/* 41 */       markUpdated();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void saveAdditional(CompoundTag $$0) {
/* 47 */     super.saveAdditional($$0);
/* 48 */     this.trialSpawner.codec().encodeStart((DynamicOps)NbtOps.INSTANCE, this.trialSpawner).get()
/* 49 */       .ifLeft($$1 -> $$0.merge((CompoundTag)$$1))
/* 50 */       .ifRight($$0 -> LOGGER.warn("Failed to encode TrialSpawner {}", $$0.message()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/* 55 */     return ClientboundBlockEntityDataPacket.create(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag getUpdateTag() {
/* 60 */     return this.trialSpawner.getData().getUpdateTag((TrialSpawnerState)getBlockState().getValue((Property)TrialSpawnerBlock.STATE));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onlyOpCanSetNbt() {
/* 65 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEntityId(EntityType<?> $$0, RandomSource $$1) {
/* 70 */     this.trialSpawner.getData().setEntityId(this.trialSpawner, $$1, $$0);
/* 71 */     setChanged();
/*    */   }
/*    */   
/*    */   public TrialSpawner getTrialSpawner() {
/* 75 */     return this.trialSpawner;
/*    */   }
/*    */ 
/*    */   
/*    */   public TrialSpawnerState getState() {
/* 80 */     if (!getBlockState().hasProperty((Property)BlockStateProperties.TRIAL_SPAWNER_STATE)) {
/* 81 */       return TrialSpawnerState.INACTIVE;
/*    */     }
/* 83 */     return (TrialSpawnerState)getBlockState().getValue((Property)BlockStateProperties.TRIAL_SPAWNER_STATE);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setState(Level $$0, TrialSpawnerState $$1) {
/* 88 */     setChanged();
/* 89 */     $$0.setBlockAndUpdate(this.worldPosition, (BlockState)getBlockState().setValue((Property)BlockStateProperties.TRIAL_SPAWNER_STATE, (Comparable)$$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public void markUpdated() {
/* 94 */     setChanged();
/* 95 */     if (this.level != null)
/* 96 */       this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\TrialSpawnerBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */