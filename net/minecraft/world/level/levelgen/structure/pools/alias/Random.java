/*    */ package net.minecraft.world.level.levelgen.structure.pools.alias;
/*    */ 
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.util.random.SimpleWeightedRandomList;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*    */ 
/*    */ final class Random extends Record implements PoolAliasBinding {
/*    */   private final ResourceKey<StructureTemplatePool> alias;
/*    */   private final SimpleWeightedRandomList<ResourceKey<StructureTemplatePool>> targets;
/*    */   static Codec<Random> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/pools/alias/Random;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #21	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pools/alias/Random;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/pools/alias/Random;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #21	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pools/alias/Random;
/*    */   }
/*    */   
/* 21 */   Random(ResourceKey<StructureTemplatePool> $$0, SimpleWeightedRandomList<ResourceKey<StructureTemplatePool>> $$1) { this.alias = $$0; this.targets = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/pools/alias/Random;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #21	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/pools/alias/Random;
/* 21 */     //   0	8	1	$$0	Ljava/lang/Object; } public ResourceKey<StructureTemplatePool> alias() { return this.alias; } public SimpleWeightedRandomList<ResourceKey<StructureTemplatePool>> targets() { return this.targets; } static {
/* 22 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ResourceKey.codec(Registries.TEMPLATE_POOL).fieldOf("alias").forGetter(Random::alias), (App)SimpleWeightedRandomList.wrappedCodec(ResourceKey.codec(Registries.TEMPLATE_POOL)).fieldOf("targets").forGetter(Random::targets)).apply((Applicative)$$0, Random::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void forEachResolved(RandomSource $$0, BiConsumer<ResourceKey<StructureTemplatePool>, ResourceKey<StructureTemplatePool>> $$1) {
/* 29 */     this.targets.getRandom($$0).ifPresent($$1 -> $$0.accept(this.alias, (ResourceKey)$$1.getData()));
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<ResourceKey<StructureTemplatePool>> allTargets() {
/* 34 */     return this.targets.unwrap().stream().map(WeightedEntry.Wrapper::getData);
/*    */   }
/*    */ 
/*    */   
/*    */   public Codec<Random> codec() {
/* 39 */     return CODEC;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\alias\Random.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */