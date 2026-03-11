/*    */ package net.minecraft.world.level.chunk;
/*    */ 
/*    */ import java.util.stream.LongStream;
/*    */ 
/*    */ public interface PalettedContainerRO<T> {
/*    */   T get(int paramInt1, int paramInt2, int paramInt3);
/*    */   
/*    */   void getAll(Consumer<T> paramConsumer);
/*    */   
/*    */   void write(FriendlyByteBuf paramFriendlyByteBuf);
/*    */   
/*    */   int getSerializedSize();
/*    */   
/*    */   boolean maybeHas(Predicate<T> paramPredicate);
/*    */   
/*    */   void count(PalettedContainer.CountConsumer<T> paramCountConsumer);
/*    */   
/*    */   PalettedContainer<T> recreate();
/*    */   
/*    */   PackedData<T> pack(IdMap<T> paramIdMap, PalettedContainer.Strategy paramStrategy);
/*    */   
/*    */   public static interface Unpacker<T, C extends PalettedContainerRO<T>> {
/*    */     DataResult<C> read(IdMap<T> param1IdMap, PalettedContainer.Strategy param1Strategy, PalettedContainerRO.PackedData<T> param1PackedData);
/*    */   }
/*    */   
/*    */   public static final class PackedData<T> extends Record {
/*    */     private final List<T> paletteEntries;
/*    */     private final Optional<LongStream> storage;
/*    */     
/* 30 */     public PackedData(List<T> $$0, Optional<LongStream> $$1) { this.paletteEntries = $$0; this.storage = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/chunk/PalettedContainerRO$PackedData;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/chunk/PalettedContainerRO$PackedData;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 30 */       //   0	7	0	this	Lnet/minecraft/world/level/chunk/PalettedContainerRO$PackedData<TT;>; } public List<T> paletteEntries() { return this.paletteEntries; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/chunk/PalettedContainerRO$PackedData;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/chunk/PalettedContainerRO$PackedData;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/chunk/PalettedContainerRO$PackedData<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/chunk/PalettedContainerRO$PackedData;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/level/chunk/PalettedContainerRO$PackedData;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 30 */       //   0	8	0	this	Lnet/minecraft/world/level/chunk/PalettedContainerRO$PackedData<TT;>; } public Optional<LongStream> storage() { return this.storage; }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\PalettedContainerRO.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */