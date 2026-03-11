/*    */ package net.minecraft.world.level.storage.loot.providers.nbt;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements ContextNbtProvider.Getter
/*    */ {
/*    */   public Tag get(LootContext $$0) {
/* 32 */     BlockEntity $$1 = (BlockEntity)$$0.getParamOrNull(LootContextParams.BLOCK_ENTITY);
/* 33 */     return ($$1 != null) ? (Tag)$$1.saveWithFullMetadata() : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 38 */     return "block_entity";
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 43 */     return (Set<LootContextParam<?>>)ImmutableSet.of(LootContextParams.BLOCK_ENTITY);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\nbt\ContextNbtProvider$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */