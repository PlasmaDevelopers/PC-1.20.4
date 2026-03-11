/*     */ package net.minecraft.network.syncher;
/*     */ 
/*     */ import io.netty.handler.codec.DecoderException;
/*     */ import io.netty.handler.codec.EncoderException;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DataValue<T>
/*     */   extends Record
/*     */ {
/*     */   final int id;
/*     */   private final EntityDataSerializer<T> serializer;
/*     */   final T value;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/syncher/SynchedEntityData$DataValue;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #213	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/network/syncher/SynchedEntityData$DataValue;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	7	0	this	Lnet/minecraft/network/syncher/SynchedEntityData$DataValue<TT;>;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/syncher/SynchedEntityData$DataValue;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #213	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/network/syncher/SynchedEntityData$DataValue;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	7	0	this	Lnet/minecraft/network/syncher/SynchedEntityData$DataValue<TT;>;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/syncher/SynchedEntityData$DataValue;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #213	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/network/syncher/SynchedEntityData$DataValue;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	8	0	this	Lnet/minecraft/network/syncher/SynchedEntityData$DataValue<TT;>;
/*     */   }
/*     */   
/*     */   public DataValue(int $$0, EntityDataSerializer<T> $$1, T $$2) {
/* 213 */     this.id = $$0; this.serializer = $$1; this.value = $$2; } public int id() { return this.id; } public EntityDataSerializer<T> serializer() { return this.serializer; } public T value() { return this.value; }
/*     */    public static <T> DataValue<T> create(EntityDataAccessor<T> $$0, T $$1) {
/* 215 */     EntityDataSerializer<T> $$2 = $$0.getSerializer();
/* 216 */     return new DataValue<>($$0.getId(), $$2, $$2.copy($$1));
/*     */   }
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/* 220 */     int $$1 = EntityDataSerializers.getSerializedId(this.serializer);
/* 221 */     if ($$1 < 0) {
/* 222 */       throw new EncoderException("Unknown serializer type " + this.serializer);
/*     */     }
/* 224 */     $$0.writeByte(this.id);
/* 225 */     $$0.writeVarInt($$1);
/* 226 */     this.serializer.write($$0, this.value);
/*     */   }
/*     */   
/*     */   public static DataValue<?> read(FriendlyByteBuf $$0, int $$1) {
/* 230 */     int $$2 = $$0.readVarInt();
/* 231 */     EntityDataSerializer<?> $$3 = EntityDataSerializers.getSerializer($$2);
/* 232 */     if ($$3 == null) {
/* 233 */       throw new DecoderException("Unknown serializer type " + $$2);
/*     */     }
/*     */     
/* 236 */     return read($$0, $$1, $$3);
/*     */   }
/*     */   
/*     */   private static <T> DataValue<T> read(FriendlyByteBuf $$0, int $$1, EntityDataSerializer<T> $$2) {
/* 240 */     return new DataValue<>($$1, $$2, $$2.read($$0));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\syncher\SynchedEntityData$DataValue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */