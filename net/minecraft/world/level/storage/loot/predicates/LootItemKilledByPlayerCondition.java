/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Set;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*    */ 
/*    */ public class LootItemKilledByPlayerCondition
/*    */   implements LootItemCondition {
/* 12 */   private static final LootItemKilledByPlayerCondition INSTANCE = new LootItemKilledByPlayerCondition();
/* 13 */   public static final Codec<LootItemKilledByPlayerCondition> CODEC = Codec.unit(INSTANCE);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 20 */     return LootItemConditions.KILLED_BY_PLAYER;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 25 */     return (Set<LootContextParam<?>>)ImmutableSet.of(LootContextParams.LAST_DAMAGE_PLAYER);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(LootContext $$0) {
/* 30 */     return $$0.hasParam(LootContextParams.LAST_DAMAGE_PLAYER);
/*    */   }
/*    */   
/*    */   public static LootItemCondition.Builder killedByPlayer() {
/* 34 */     return () -> INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\LootItemKilledByPlayerCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */