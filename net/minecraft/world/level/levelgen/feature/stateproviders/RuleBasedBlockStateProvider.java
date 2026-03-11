/*    */ package net.minecraft.world.level.levelgen.feature.stateproviders;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
/*    */ 
/*    */ public final class RuleBasedBlockStateProvider extends Record {
/*    */   private final BlockStateProvider fallback;
/*    */   private final List<Rule> rules;
/*    */   public static final Codec<RuleBasedBlockStateProvider> CODEC;
/*    */   
/* 14 */   public RuleBasedBlockStateProvider(BlockStateProvider $$0, List<Rule> $$1) { this.fallback = $$0; this.rules = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 14 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider; } public BlockStateProvider fallback() { return this.fallback; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider;
/* 14 */     //   0	8	1	$$0	Ljava/lang/Object; } public List<Rule> rules() { return this.rules; } static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockStateProvider.CODEC.fieldOf("fallback").forGetter(RuleBasedBlockStateProvider::fallback), (App)Rule.CODEC.listOf().fieldOf("rules").forGetter(RuleBasedBlockStateProvider::rules)).apply((Applicative)$$0, RuleBasedBlockStateProvider::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static RuleBasedBlockStateProvider simple(BlockStateProvider $$0) {
/* 21 */     return new RuleBasedBlockStateProvider($$0, List.of());
/*    */   }
/*    */   
/*    */   public static RuleBasedBlockStateProvider simple(Block $$0) {
/* 25 */     return simple(BlockStateProvider.simple($$0));
/*    */   }
/*    */   
/*    */   public BlockState getState(WorldGenLevel $$0, RandomSource $$1, BlockPos $$2) {
/* 29 */     for (Rule $$3 : this.rules) {
/* 30 */       if ($$3.ifTrue().test($$0, $$2)) {
/* 31 */         return $$3.then().getState($$1, $$2);
/*    */       }
/*    */     } 
/* 34 */     return this.fallback.getState($$1, $$2);
/*    */   }
/*    */   public static final class Rule extends Record { private final BlockPredicate ifTrue; private final BlockStateProvider then; public static final Codec<Rule> CODEC;
/* 37 */     public Rule(BlockPredicate $$0, BlockStateProvider $$1) { this.ifTrue = $$0; this.then = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider$Rule;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #37	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider$Rule; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider$Rule;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #37	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider$Rule; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider$Rule;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #37	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/stateproviders/RuleBasedBlockStateProvider$Rule;
/* 37 */       //   0	8	1	$$0	Ljava/lang/Object; } public BlockPredicate ifTrue() { return this.ifTrue; } public BlockStateProvider then() { return this.then; } static {
/* 38 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockPredicate.CODEC.fieldOf("if_true").forGetter(Rule::ifTrue), (App)BlockStateProvider.CODEC.fieldOf("then").forGetter(Rule::then)).apply((Applicative)$$0, Rule::new));
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\stateproviders\RuleBasedBlockStateProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */