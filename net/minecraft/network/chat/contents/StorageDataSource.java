/*    */ package net.minecraft.network.chat.contents;
/*    */ 
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public final class StorageDataSource extends Record implements DataSource {
/*    */   private final ResourceLocation id;
/*    */   public static final MapCodec<StorageDataSource> SUB_CODEC;
/*    */   
/* 11 */   public StorageDataSource(ResourceLocation $$0) { this.id = $$0; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/contents/StorageDataSource;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/network/chat/contents/StorageDataSource; } public ResourceLocation id() { return this.id; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/contents/StorageDataSource;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/chat/contents/StorageDataSource;
/* 12 */     //   0	8	1	$$0	Ljava/lang/Object; } static { SUB_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("storage").forGetter(StorageDataSource::id)).apply((Applicative)$$0, StorageDataSource::new)); }
/*    */ 
/*    */ 
/*    */   
/* 16 */   public static final DataSource.Type<StorageDataSource> TYPE = new DataSource.Type<>(SUB_CODEC, "storage");
/*    */ 
/*    */   
/*    */   public Stream<CompoundTag> getData(CommandSourceStack $$0) {
/* 20 */     CompoundTag $$1 = $$0.getServer().getCommandStorage().get(this.id);
/* 21 */     return Stream.of($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public DataSource.Type<?> type() {
/* 26 */     return TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 31 */     return "storage=" + this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\contents\StorageDataSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */