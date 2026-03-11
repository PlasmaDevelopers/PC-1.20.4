/*    */ package net.minecraft.data.models;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.world.item.ArmorMaterial;
/*    */ 
/*    */ 
/*    */ 
/*    */ final class TrimModelData
/*    */   extends Record
/*    */ {
/*    */   private final String name;
/*    */   private final float itemModelIndex;
/*    */   private final Map<ArmorMaterial, String> overrideArmorMaterials;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/data/models/ItemModelGenerators$TrimModelData;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/data/models/ItemModelGenerators$TrimModelData;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/data/models/ItemModelGenerators$TrimModelData;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/data/models/ItemModelGenerators$TrimModelData;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/data/models/ItemModelGenerators$TrimModelData;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/data/models/ItemModelGenerators$TrimModelData;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   TrimModelData(String $$0, float $$1, Map<ArmorMaterial, String> $$2) {
/* 28 */     this.name = $$0; this.itemModelIndex = $$1; this.overrideArmorMaterials = $$2; } public String name() { return this.name; } public float itemModelIndex() { return this.itemModelIndex; } public Map<ArmorMaterial, String> overrideArmorMaterials() { return this.overrideArmorMaterials; }
/*    */    public String name(ArmorMaterial $$0) {
/* 30 */     return this.overrideArmorMaterials.getOrDefault($$0, this.name);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\ItemModelGenerators$TrimModelData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */