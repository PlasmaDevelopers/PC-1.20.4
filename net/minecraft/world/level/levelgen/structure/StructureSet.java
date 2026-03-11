/*    */ package net.minecraft.world.level.levelgen.structure;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
/*    */ 
/*    */ public final class StructureSet extends Record {
/*    */   private final List<StructureSelectionEntry> structures;
/*    */   private final StructurePlacement placement;
/*    */   public static final Codec<StructureSet> DIRECT_CODEC;
/*    */   
/* 16 */   public StructureSet(List<StructureSelectionEntry> $$0, StructurePlacement $$1) { this.structures = $$0; this.placement = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/StructureSet;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 16 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/StructureSet; } public List<StructureSelectionEntry> structures() { return this.structures; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/StructureSet;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/StructureSet; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/StructureSet;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/StructureSet;
/* 16 */     //   0	8	1	$$0	Ljava/lang/Object; } public StructurePlacement placement() { return this.placement; }
/*    */ 
/*    */   
/*    */   static {
/* 20 */     DIRECT_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)StructureSelectionEntry.CODEC.listOf().fieldOf("structures").forGetter(StructureSet::structures), (App)StructurePlacement.CODEC.fieldOf("placement").forGetter(StructureSet::placement)).apply((Applicative)$$0, StructureSet::new));
/*    */   }
/*    */ 
/*    */   
/* 24 */   public static final Codec<Holder<StructureSet>> CODEC = (Codec<Holder<StructureSet>>)RegistryFileCodec.create(Registries.STRUCTURE_SET, DIRECT_CODEC);
/*    */   
/*    */   public StructureSet(Holder<Structure> $$0, StructurePlacement $$1) {
/* 27 */     this(List.of(new StructureSelectionEntry($$0, 1)), $$1);
/*    */   }
/*    */   public static final class StructureSelectionEntry extends Record { private final Holder<Structure> structure; private final int weight; public static final Codec<StructureSelectionEntry> CODEC;
/* 30 */     public StructureSelectionEntry(Holder<Structure> $$0, int $$1) { this.structure = $$0; this.weight = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/StructureSet$StructureSelectionEntry;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/StructureSet$StructureSelectionEntry; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/StructureSet$StructureSelectionEntry;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/StructureSet$StructureSelectionEntry; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/StructureSet$StructureSelectionEntry;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/StructureSet$StructureSelectionEntry;
/* 30 */       //   0	8	1	$$0	Ljava/lang/Object; } public Holder<Structure> structure() { return this.structure; } public int weight() { return this.weight; }
/*    */ 
/*    */     
/*    */     static {
/* 34 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Structure.CODEC.fieldOf("structure").forGetter(StructureSelectionEntry::structure), (App)ExtraCodecs.POSITIVE_INT.fieldOf("weight").forGetter(StructureSelectionEntry::weight)).apply((Applicative)$$0, StructureSelectionEntry::new));
/*    */     } }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static StructureSelectionEntry entry(Holder<Structure> $$0, int $$1) {
/* 41 */     return new StructureSelectionEntry($$0, $$1);
/*    */   }
/*    */   
/*    */   public static StructureSelectionEntry entry(Holder<Structure> $$0) {
/* 45 */     return new StructureSelectionEntry($$0, 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\StructureSet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */