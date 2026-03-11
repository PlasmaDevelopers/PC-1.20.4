/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.storage.loot.IntRange;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.scores.Objective;
/*    */ import net.minecraft.world.scores.Scoreboard;
/*    */ 
/*    */ public final class EntityHasScoreCondition extends Record implements LootItemCondition {
/*    */   private final Map<String, IntRange> scores;
/*    */   private final LootContext.EntityTarget entityTarget;
/*    */   public static final Codec<EntityHasScoreCondition> CODEC;
/*    */   
/* 19 */   public EntityHasScoreCondition(Map<String, IntRange> $$0, LootContext.EntityTarget $$1) { this.scores = $$0; this.entityTarget = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/predicates/EntityHasScoreCondition;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 19 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/EntityHasScoreCondition; } public Map<String, IntRange> scores() { return this.scores; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/predicates/EntityHasScoreCondition;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/EntityHasScoreCondition; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/predicates/EntityHasScoreCondition;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/predicates/EntityHasScoreCondition;
/* 19 */     //   0	8	1	$$0	Ljava/lang/Object; } public LootContext.EntityTarget entityTarget() { return this.entityTarget; }
/*    */ 
/*    */   
/*    */   static {
/* 23 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.unboundedMap((Codec)Codec.STRING, IntRange.CODEC).fieldOf("scores").forGetter(EntityHasScoreCondition::scores), (App)LootContext.EntityTarget.CODEC.fieldOf("entity").forGetter(EntityHasScoreCondition::entityTarget)).apply((Applicative)$$0, EntityHasScoreCondition::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 30 */     return LootItemConditions.ENTITY_SCORES;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 35 */     return (Set<LootContextParam<?>>)Stream.concat(Stream.of(this.entityTarget.getParam()), this.scores.values().stream().flatMap($$0 -> $$0.getReferencedContextParams().stream())).collect(ImmutableSet.toImmutableSet());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(LootContext $$0) {
/* 40 */     Entity $$1 = (Entity)$$0.getParamOrNull(this.entityTarget.getParam());
/*    */     
/* 42 */     if ($$1 == null) {
/* 43 */       return false;
/*    */     }
/*    */     
/* 46 */     Scoreboard $$2 = $$1.level().getScoreboard();
/* 47 */     for (Map.Entry<String, IntRange> $$3 : this.scores.entrySet()) {
/* 48 */       if (!hasScore($$0, $$1, $$2, $$3.getKey(), $$3.getValue())) {
/* 49 */         return false;
/*    */       }
/*    */     } 
/* 52 */     return true;
/*    */   }
/*    */   
/*    */   protected boolean hasScore(LootContext $$0, Entity $$1, Scoreboard $$2, String $$3, IntRange $$4) {
/* 56 */     Objective $$5 = $$2.getObjective($$3);
/* 57 */     if ($$5 == null) {
/* 58 */       return false;
/*    */     }
/* 60 */     ReadOnlyScoreInfo $$6 = $$2.getPlayerScoreInfo((ScoreHolder)$$1, $$5);
/* 61 */     if ($$6 == null) {
/* 62 */       return false;
/*    */     }
/* 64 */     return $$4.test($$0, $$6.value());
/*    */   }
/*    */   
/*    */   public static class Builder implements LootItemCondition.Builder {
/* 68 */     private final ImmutableMap.Builder<String, IntRange> scores = ImmutableMap.builder();
/*    */     private final LootContext.EntityTarget entityTarget;
/*    */     
/*    */     public Builder(LootContext.EntityTarget $$0) {
/* 72 */       this.entityTarget = $$0;
/*    */     }
/*    */     
/*    */     public Builder withScore(String $$0, IntRange $$1) {
/* 76 */       this.scores.put($$0, $$1);
/* 77 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public LootItemCondition build() {
/* 82 */       return new EntityHasScoreCondition((Map<String, IntRange>)this.scores.build(), this.entityTarget);
/*    */     }
/*    */   }
/*    */   
/*    */   public static Builder hasScores(LootContext.EntityTarget $$0) {
/* 87 */     return new Builder($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\EntityHasScoreCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */