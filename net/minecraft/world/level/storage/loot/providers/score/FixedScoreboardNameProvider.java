/*    */ package net.minecraft.world.level.storage.loot.providers.score;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Set;
/*    */ 
/*    */ public final class FixedScoreboardNameProvider extends Record implements ScoreboardNameProvider {
/*    */   private final String name;
/*    */   public static final Codec<FixedScoreboardNameProvider> CODEC;
/*    */   
/* 12 */   public FixedScoreboardNameProvider(String $$0) { this.name = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/providers/score/FixedScoreboardNameProvider;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 12 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/providers/score/FixedScoreboardNameProvider; } public String name() { return this.name; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/providers/score/FixedScoreboardNameProvider;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/providers/score/FixedScoreboardNameProvider; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/providers/score/FixedScoreboardNameProvider;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/providers/score/FixedScoreboardNameProvider;
/* 13 */     //   0	8	1	$$0	Ljava/lang/Object; } static { CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.STRING.fieldOf("name").forGetter(FixedScoreboardNameProvider::name)).apply((Applicative)$$0, FixedScoreboardNameProvider::new)); }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ScoreboardNameProvider forName(String $$0) {
/* 18 */     return new FixedScoreboardNameProvider($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public LootScoreProviderType getType() {
/* 23 */     return ScoreboardNameProviders.FIXED;
/*    */   }
/*    */ 
/*    */   
/*    */   public ScoreHolder getScoreHolder(LootContext $$0) {
/* 28 */     return ScoreHolder.forNameOnly(this.name);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 33 */     return (Set<LootContextParam<?>>)ImmutableSet.of();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\score\FixedScoreboardNameProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */