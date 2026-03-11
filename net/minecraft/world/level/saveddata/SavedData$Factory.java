/*    */ package net.minecraft.world.level.saveddata;
/*    */ 
/*    */ public final class Factory<T extends SavedData> extends Record {
/*    */   private final Supplier<T> constructor;
/*    */   private final Function<CompoundTag, T> deserializer;
/*    */   private final DataFixTypes type;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/saveddata/SavedData$Factory;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/saveddata/SavedData$Factory;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/saveddata/SavedData$Factory<TT;>;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/saveddata/SavedData$Factory;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/saveddata/SavedData$Factory;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/saveddata/SavedData$Factory<TT;>;
/*    */   }
/*    */   
/* 16 */   public Factory(Supplier<T> $$0, Function<CompoundTag, T> $$1, DataFixTypes $$2) { this.constructor = $$0; this.deserializer = $$1; this.type = $$2; } public Supplier<T> constructor() { return this.constructor; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/saveddata/SavedData$Factory;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #16	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/saveddata/SavedData$Factory;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 16 */     //   0	8	0	this	Lnet/minecraft/world/level/saveddata/SavedData$Factory<TT;>; } public Function<CompoundTag, T> deserializer() { return this.deserializer; } public DataFixTypes type() { return this.type; }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\saveddata\SavedData$Factory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */