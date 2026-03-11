/*    */ package net.minecraft.world.level.saveddata;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtIo;
/*    */ import net.minecraft.util.datafix.DataFixTypes;
/*    */ 
/*    */ public abstract class SavedData {
/*    */   public static final class Factory<T extends SavedData> extends Record { private final Supplier<T> constructor;
/*    */     private final Function<CompoundTag, T> deserializer;
/*    */     private final DataFixTypes type;
/*    */     
/* 16 */     public Factory(Supplier<T> $$0, Function<CompoundTag, T> $$1, DataFixTypes $$2) { this.constructor = $$0; this.deserializer = $$1; this.type = $$2; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/saveddata/SavedData$Factory;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #16	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/saveddata/SavedData$Factory;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 16 */       //   0	7	0	this	Lnet/minecraft/world/level/saveddata/SavedData$Factory<TT;>; } public Supplier<T> constructor() { return this.constructor; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/saveddata/SavedData$Factory;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #16	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/saveddata/SavedData$Factory;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/saveddata/SavedData$Factory<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/saveddata/SavedData$Factory;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #16	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/level/saveddata/SavedData$Factory;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 16 */       //   0	8	0	this	Lnet/minecraft/world/level/saveddata/SavedData$Factory<TT;>; } public Function<CompoundTag, T> deserializer() { return this.deserializer; } public DataFixTypes type() { return this.type; }
/*    */      }
/* 18 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   private boolean dirty;
/*    */   
/*    */   public abstract CompoundTag save(CompoundTag paramCompoundTag);
/*    */   
/*    */   public void setDirty() {
/* 24 */     setDirty(true);
/*    */   }
/*    */   
/*    */   public void setDirty(boolean $$0) {
/* 28 */     this.dirty = $$0;
/*    */   }
/*    */   
/*    */   public boolean isDirty() {
/* 32 */     return this.dirty;
/*    */   }
/*    */   
/*    */   public void save(File $$0) {
/* 36 */     if (!isDirty()) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     CompoundTag $$1 = new CompoundTag();
/* 41 */     $$1.put("data", (Tag)save(new CompoundTag()));
/* 42 */     NbtUtils.addCurrentDataVersion($$1);
/*    */     
/*    */     try {
/* 45 */       NbtIo.writeCompressed($$1, $$0.toPath());
/* 46 */     } catch (IOException $$2) {
/* 47 */       LOGGER.error("Could not save data {}", this, $$2);
/*    */     } 
/* 49 */     setDirty(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\saveddata\SavedData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */