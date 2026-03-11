/*    */ package net.minecraft.world.level.levelgen.structure.pools.alias;
/*    */ 
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*    */ 
/*    */ final class Direct extends Record implements PoolAliasBinding {
/*    */   private final ResourceKey<StructureTemplatePool> alias;
/*    */   private final ResourceKey<StructureTemplatePool> target;
/*    */   static Codec<Direct> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/pools/alias/Direct;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pools/alias/Direct;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/pools/alias/Direct;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/pools/alias/Direct;
/*    */   }
/*    */   
/* 19 */   Direct(ResourceKey<StructureTemplatePool> $$0, ResourceKey<StructureTemplatePool> $$1) { this.alias = $$0; this.target = $$1; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/pools/alias/Direct;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/pools/alias/Direct;
/* 19 */     //   0	8	1	$$0	Ljava/lang/Object; } public ResourceKey<StructureTemplatePool> alias() { return this.alias; } public ResourceKey<StructureTemplatePool> target() { return this.target; } static {
/* 20 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ResourceKey.codec(Registries.TEMPLATE_POOL).fieldOf("alias").forGetter(Direct::alias), (App)ResourceKey.codec(Registries.TEMPLATE_POOL).fieldOf("target").forGetter(Direct::target)).apply((Applicative)$$0, Direct::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void forEachResolved(RandomSource $$0, BiConsumer<ResourceKey<StructureTemplatePool>, ResourceKey<StructureTemplatePool>> $$1) {
/* 27 */     $$1.accept(this.alias, this.target);
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<ResourceKey<StructureTemplatePool>> allTargets() {
/* 32 */     return Stream.of(this.target);
/*    */   }
/*    */ 
/*    */   
/*    */   public Codec<Direct> codec() {
/* 37 */     return CODEC;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\alias\Direct.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */