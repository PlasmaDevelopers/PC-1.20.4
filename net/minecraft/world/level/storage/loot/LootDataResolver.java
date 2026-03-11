/*    */ package net.minecraft.world.level.storage.loot;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface LootDataResolver
/*    */ {
/*    */   @Nullable
/*    */   <T> T getElement(LootDataId<T> paramLootDataId);
/*    */   
/*    */   @Nullable
/*    */   default <T> T getElement(LootDataType<T> $$0, ResourceLocation $$1) {
/* 15 */     return getElement(new LootDataId<>($$0, $$1));
/*    */   }
/*    */   
/*    */   default <T> Optional<T> getElementOptional(LootDataId<T> $$0) {
/* 19 */     return Optional.ofNullable(getElement($$0));
/*    */   }
/*    */   
/*    */   default <T> Optional<T> getElementOptional(LootDataType<T> $$0, ResourceLocation $$1) {
/* 23 */     return getElementOptional(new LootDataId<>($$0, $$1));
/*    */   }
/*    */   
/*    */   default LootTable getLootTable(ResourceLocation $$0) {
/* 27 */     return getElementOptional(LootDataType.TABLE, $$0).orElse(LootTable.EMPTY);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\LootDataResolver.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */