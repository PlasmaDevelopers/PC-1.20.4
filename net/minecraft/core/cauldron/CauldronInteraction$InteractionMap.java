/*    */ package net.minecraft.core.cauldron;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.world.item.Item;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class InteractionMap
/*    */   extends Record
/*    */ {
/*    */   private final String name;
/*    */   private final Map<Item, CauldronInteraction> map;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/core/cauldron/CauldronInteraction$InteractionMap;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #35	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/core/cauldron/CauldronInteraction$InteractionMap;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/cauldron/CauldronInteraction$InteractionMap;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #35	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/core/cauldron/CauldronInteraction$InteractionMap;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/core/cauldron/CauldronInteraction$InteractionMap;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #35	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/core/cauldron/CauldronInteraction$InteractionMap;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public InteractionMap(String $$0, Map<Item, CauldronInteraction> $$1) {
/* 35 */     this.name = $$0; this.map = $$1; } public String name() { return this.name; } public Map<Item, CauldronInteraction> map() { return this.map; }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\cauldron\CauldronInteraction$InteractionMap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */