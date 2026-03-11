/*    */ package net.minecraft.world;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.storage.loot.LootParams;
/*    */ import net.minecraft.world.level.storage.loot.LootTable;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public interface RandomizableContainer
/*    */   extends Container {
/*    */   public static final String LOOT_TABLE_TAG = "LootTable";
/*    */   public static final String LOOT_TABLE_SEED_TAG = "LootTableSeed";
/*    */   
/*    */   @Nullable
/*    */   ResourceLocation getLootTable();
/*    */   
/*    */   void setLootTable(@Nullable ResourceLocation paramResourceLocation);
/*    */   
/*    */   default void setLootTable(ResourceLocation $$0, long $$1) {
/* 33 */     setLootTable($$0);
/* 34 */     setLootTableSeed($$1);
/*    */   }
/*    */   
/*    */   long getLootTableSeed();
/*    */   
/*    */   void setLootTableSeed(long paramLong);
/*    */   
/*    */   BlockPos getBlockPos();
/*    */   
/*    */   @Nullable
/*    */   Level getLevel();
/*    */   
/*    */   static void setBlockEntityLootTable(BlockGetter $$0, RandomSource $$1, BlockPos $$2, ResourceLocation $$3) {
/* 47 */     BlockEntity $$4 = $$0.getBlockEntity($$2);
/* 48 */     if ($$4 instanceof RandomizableContainer) { RandomizableContainer $$5 = (RandomizableContainer)$$4;
/* 49 */       $$5.setLootTable($$3, $$1.nextLong()); }
/*    */   
/*    */   }
/*    */   
/*    */   default boolean tryLoadLootTable(CompoundTag $$0) {
/* 54 */     if ($$0.contains("LootTable", 8)) {
/* 55 */       setLootTable(new ResourceLocation($$0.getString("LootTable")));
/* 56 */       setLootTableSeed($$0.getLong("LootTableSeed"));
/* 57 */       return true;
/*    */     } 
/* 59 */     return false;
/*    */   }
/*    */   
/*    */   default boolean trySaveLootTable(CompoundTag $$0) {
/* 63 */     ResourceLocation $$1 = getLootTable();
/* 64 */     if ($$1 == null) {
/* 65 */       return false;
/*    */     }
/*    */     
/* 68 */     $$0.putString("LootTable", $$1.toString());
/* 69 */     long $$2 = getLootTableSeed();
/* 70 */     if ($$2 != 0L) {
/* 71 */       $$0.putLong("LootTableSeed", $$2);
/*    */     }
/* 73 */     return true;
/*    */   }
/*    */   
/*    */   default void unpackLootTable(@Nullable Player $$0) {
/* 77 */     Level $$1 = getLevel();
/* 78 */     BlockPos $$2 = getBlockPos();
/* 79 */     ResourceLocation $$3 = getLootTable();
/*    */     
/* 81 */     if ($$3 != null && $$1 != null && $$1.getServer() != null) {
/* 82 */       LootTable $$4 = $$1.getServer().getLootData().getLootTable($$3);
/* 83 */       if ($$0 instanceof ServerPlayer) {
/* 84 */         CriteriaTriggers.GENERATE_LOOT.trigger((ServerPlayer)$$0, $$3);
/*    */       }
/* 86 */       setLootTable(null);
/*    */       
/* 88 */       LootParams.Builder $$5 = (new LootParams.Builder((ServerLevel)$$1)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf((Vec3i)$$2));
/*    */       
/* 90 */       if ($$0 != null) {
/* 91 */         $$5.withLuck($$0.getLuck()).withParameter(LootContextParams.THIS_ENTITY, $$0);
/*    */       }
/*    */       
/* 94 */       $$4.fill(this, $$5.create(LootContextParamSets.CHEST), getLootTableSeed());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\RandomizableContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */