/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.level.BaseSpawner;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.SpawnData;
/*    */ import net.minecraft.world.level.Spawner;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class SpawnerBlockEntity
/*    */   extends BlockEntity implements Spawner {
/* 20 */   private final BaseSpawner spawner = new BaseSpawner()
/*    */     {
/*    */       public void broadcastEvent(Level $$0, BlockPos $$1, int $$2) {
/* 23 */         $$0.blockEvent($$1, Blocks.SPAWNER, $$2, 0);
/*    */       }
/*    */ 
/*    */       
/*    */       public void setNextSpawnData(@Nullable Level $$0, BlockPos $$1, SpawnData $$2) {
/* 28 */         super.setNextSpawnData($$0, $$1, $$2);
/* 29 */         if ($$0 != null) {
/* 30 */           BlockState $$3 = $$0.getBlockState($$1);
/* 31 */           $$0.sendBlockUpdated($$1, $$3, $$3, 4);
/*    */         } 
/*    */       }
/*    */     };
/*    */   
/*    */   public SpawnerBlockEntity(BlockPos $$0, BlockState $$1) {
/* 37 */     super(BlockEntityType.MOB_SPAWNER, $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void load(CompoundTag $$0) {
/* 42 */     super.load($$0);
/* 43 */     this.spawner.load(this.level, this.worldPosition, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void saveAdditional(CompoundTag $$0) {
/* 48 */     super.saveAdditional($$0);
/* 49 */     this.spawner.save($$0);
/*    */   }
/*    */   
/*    */   public static void clientTick(Level $$0, BlockPos $$1, BlockState $$2, SpawnerBlockEntity $$3) {
/* 53 */     $$3.spawner.clientTick($$0, $$1);
/*    */   }
/*    */   
/*    */   public static void serverTick(Level $$0, BlockPos $$1, BlockState $$2, SpawnerBlockEntity $$3) {
/* 57 */     $$3.spawner.serverTick((ServerLevel)$$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/* 62 */     return ClientboundBlockEntityDataPacket.create(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag getUpdateTag() {
/* 67 */     CompoundTag $$0 = saveWithoutMetadata();
/* 68 */     $$0.remove("SpawnPotentials");
/* 69 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean triggerEvent(int $$0, int $$1) {
/* 74 */     if (this.spawner.onEventTriggered(this.level, $$0)) {
/* 75 */       return true;
/*    */     }
/* 77 */     return super.triggerEvent($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onlyOpCanSetNbt() {
/* 82 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEntityId(EntityType<?> $$0, RandomSource $$1) {
/* 87 */     this.spawner.setEntityId($$0, this.level, $$1, this.worldPosition);
/* 88 */     setChanged();
/*    */   }
/*    */   
/*    */   public BaseSpawner getSpawner() {
/* 92 */     return this.spawner;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\SpawnerBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */