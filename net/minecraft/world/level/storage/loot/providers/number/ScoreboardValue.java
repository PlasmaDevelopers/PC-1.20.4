/*    */ package net.minecraft.world.level.storage.loot.providers.number;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.server.ServerScoreboard;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.providers.score.ScoreboardNameProvider;
/*    */ import net.minecraft.world.scores.ScoreHolder;
/*    */ 
/*    */ public final class ScoreboardValue extends Record implements NumberProvider {
/*    */   private final ScoreboardNameProvider target;
/*    */   private final String score;
/*    */   private final float scale;
/*    */   public static final Codec<ScoreboardValue> CODEC;
/*    */   
/* 17 */   public ScoreboardValue(ScoreboardNameProvider $$0, String $$1, float $$2) { this.target = $$0; this.score = $$1; this.scale = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/providers/number/ScoreboardValue;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 17 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/providers/number/ScoreboardValue; } public ScoreboardNameProvider target() { return this.target; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/providers/number/ScoreboardValue;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/providers/number/ScoreboardValue; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/providers/number/ScoreboardValue;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/providers/number/ScoreboardValue;
/* 17 */     //   0	8	1	$$0	Ljava/lang/Object; } public String score() { return this.score; } public float scale() { return this.scale; }
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 22 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ScoreboardNameProviders.CODEC.fieldOf("target").forGetter(ScoreboardValue::target), (App)Codec.STRING.fieldOf("score").forGetter(ScoreboardValue::score), (App)Codec.FLOAT.fieldOf("scale").orElse(Float.valueOf(1.0F)).forGetter(ScoreboardValue::scale)).apply((Applicative)$$0, ScoreboardValue::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LootNumberProviderType getType() {
/* 30 */     return NumberProviders.SCORE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 35 */     return this.target.getReferencedContextParams();
/*    */   }
/*    */   
/*    */   public static ScoreboardValue fromScoreboard(LootContext.EntityTarget $$0, String $$1) {
/* 39 */     return fromScoreboard($$0, $$1, 1.0F);
/*    */   }
/*    */   
/*    */   public static ScoreboardValue fromScoreboard(LootContext.EntityTarget $$0, String $$1, float $$2) {
/* 43 */     return new ScoreboardValue(ContextScoreboardNameProvider.forTarget($$0), $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFloat(LootContext $$0) {
/* 48 */     ScoreHolder $$1 = this.target.getScoreHolder($$0);
/* 49 */     if ($$1 == null) {
/* 50 */       return 0.0F;
/*    */     }
/*    */     
/* 53 */     ServerScoreboard serverScoreboard = $$0.getLevel().getScoreboard();
/* 54 */     Objective $$3 = serverScoreboard.getObjective(this.score);
/* 55 */     if ($$3 == null) {
/* 56 */       return 0.0F;
/*    */     }
/*    */     
/* 59 */     ReadOnlyScoreInfo $$4 = serverScoreboard.getPlayerScoreInfo($$1, $$3);
/* 60 */     if ($$4 == null) {
/* 61 */       return 0.0F;
/*    */     }
/* 63 */     return $$4.value() * this.scale;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\number\ScoreboardValue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */