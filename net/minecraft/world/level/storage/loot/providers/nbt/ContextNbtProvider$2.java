/*    */ package net.minecraft.world.level.storage.loot.providers.nbt;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.advancements.critereon.NbtPredicate;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
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
/*    */   @Nullable
/*    */   public Tag get(LootContext $$0) {
/* 52 */     Entity $$1 = (Entity)$$0.getParamOrNull(target.getParam());
/* 53 */     return ($$1 != null) ? (Tag)NbtPredicate.getEntityTagToCompare($$1) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 58 */     return target.name();
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 63 */     return (Set<LootContextParam<?>>)ImmutableSet.of(target.getParam());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\nbt\ContextNbtProvider$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */