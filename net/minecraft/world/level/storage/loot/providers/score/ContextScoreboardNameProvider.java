/*    */ package net.minecraft.world.level.storage.loot.providers.score;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Set;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ public final class ContextScoreboardNameProvider extends Record implements ScoreboardNameProvider {
/*    */   private final LootContext.EntityTarget target;
/*    */   public static final Codec<ContextScoreboardNameProvider> CODEC;
/*    */   
/* 13 */   public ContextScoreboardNameProvider(LootContext.EntityTarget $$0) { this.target = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/providers/score/ContextScoreboardNameProvider;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 13 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/providers/score/ContextScoreboardNameProvider; } public LootContext.EntityTarget target() { return this.target; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/providers/score/ContextScoreboardNameProvider;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/providers/score/ContextScoreboardNameProvider; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/providers/score/ContextScoreboardNameProvider;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/providers/score/ContextScoreboardNameProvider;
/* 14 */     //   0	8	1	$$0	Ljava/lang/Object; } static { CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)LootContext.EntityTarget.CODEC.fieldOf("target").forGetter(ContextScoreboardNameProvider::target)).apply((Applicative)$$0, ContextScoreboardNameProvider::new)); }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public static final Codec<ContextScoreboardNameProvider> INLINE_CODEC = LootContext.EntityTarget.CODEC.xmap(ContextScoreboardNameProvider::new, ContextScoreboardNameProvider::target);
/*    */   
/*    */   public static ScoreboardNameProvider forTarget(LootContext.EntityTarget $$0) {
/* 21 */     return new ContextScoreboardNameProvider($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public LootScoreProviderType getType() {
/* 26 */     return ScoreboardNameProviders.CONTEXT;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ScoreHolder getScoreHolder(LootContext $$0) {
/* 32 */     return (ScoreHolder)$$0.getParamOrNull(this.target.getParam());
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 37 */     return (Set<LootContextParam<?>>)ImmutableSet.of(this.target.getParam());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\score\ContextScoreboardNameProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */