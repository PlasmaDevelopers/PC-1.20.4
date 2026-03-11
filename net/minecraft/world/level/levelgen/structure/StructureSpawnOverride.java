/*    */ package net.minecraft.world.level.levelgen.structure;
/*    */ 
/*    */ 
/*    */ public final class StructureSpawnOverride extends Record {
/*    */   private final BoundingBoxType boundingBox;
/*    */   private final WeightedRandomList<MobSpawnSettings.SpawnerData> spawns;
/*    */   public static final Codec<StructureSpawnOverride> CODEC;
/*    */   
/*  9 */   public StructureSpawnOverride(BoundingBoxType $$0, WeightedRandomList<MobSpawnSettings.SpawnerData> $$1) { this.boundingBox = $$0; this.spawns = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride; } public BoundingBoxType boundingBox() { return this.boundingBox; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/StructureSpawnOverride;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public WeightedRandomList<MobSpawnSettings.SpawnerData> spawns() { return this.spawns; }
/*    */ 
/*    */   
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BoundingBoxType.CODEC.fieldOf("bounding_box").forGetter(StructureSpawnOverride::boundingBox), (App)WeightedRandomList.codec(MobSpawnSettings.SpawnerData.CODEC).fieldOf("spawns").forGetter(StructureSpawnOverride::spawns)).apply((Applicative)$$0, StructureSpawnOverride::new));
/*    */   }
/*    */   
/*    */   public enum BoundingBoxType
/*    */     implements StringRepresentable
/*    */   {
/* 19 */     PIECE("piece"),
/* 20 */     STRUCTURE("full");
/*    */     
/* 22 */     public static final Codec<BoundingBoxType> CODEC = (Codec<BoundingBoxType>)StringRepresentable.fromEnum(BoundingBoxType::values); private final String id;
/*    */     static {
/*    */     
/*    */     }
/*    */     BoundingBoxType(String $$0) {
/* 27 */       this.id = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public String getSerializedName() {
/* 32 */       return this.id;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\StructureSpawnOverride.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */