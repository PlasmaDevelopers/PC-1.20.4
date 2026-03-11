/*    */ package net.minecraft.world.level.levelgen.structure.pools.alias;
/*    */ 
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.util.random.SimpleWeightedRandomList;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*    */ 
/*    */ final class RandomGroup extends Record implements PoolAliasBinding {
/*    */   private final SimpleWeightedRandomList<List<PoolAliasBinding>> groups;
/*    */   static Codec<RandomGroup> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/pools/alias/RandomGroup;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #21	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pools/alias/RandomGroup;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/pools/alias/RandomGroup;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #21	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pools/alias/RandomGroup;
/*    */   }
/*    */   
/* 21 */   RandomGroup(SimpleWeightedRandomList<List<PoolAliasBinding>> $$0) { this.groups = $$0; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/pools/alias/RandomGroup;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #21	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/pools/alias/RandomGroup;
/* 21 */     //   0	8	1	$$0	Ljava/lang/Object; } public SimpleWeightedRandomList<List<PoolAliasBinding>> groups() { return this.groups; } static {
/* 22 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)SimpleWeightedRandomList.wrappedCodec(Codec.list(PoolAliasBinding.CODEC)).fieldOf("groups").forGetter(RandomGroup::groups)).apply((Applicative)$$0, RandomGroup::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void forEachResolved(RandomSource $$0, BiConsumer<ResourceKey<StructureTemplatePool>, ResourceKey<StructureTemplatePool>> $$1) {
/* 28 */     this.groups.getRandom($$0).ifPresent($$2 -> ((List)$$2.getData()).forEach(()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Stream<ResourceKey<StructureTemplatePool>> allTargets() {
/* 35 */     return this.groups.unwrap().stream()
/* 36 */       .flatMap($$0 -> ((List)$$0.getData()).stream())
/* 37 */       .flatMap(PoolAliasBinding::allTargets);
/*    */   }
/*    */ 
/*    */   
/*    */   public Codec<RandomGroup> codec() {
/* 42 */     return CODEC;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\alias\RandomGroup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */