/*    */ package net.minecraft.network.chat.contents;
/*    */ 
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.chat.ComponentSerialization;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public interface DataSource
/*    */ {
/* 13 */   public static final MapCodec<DataSource> CODEC = ComponentSerialization.createLegacyComponentMatcher((StringRepresentable[])new Type[] { EntityDataSource.TYPE, BlockDataSource.TYPE, StorageDataSource.TYPE }, Type::codec, DataSource::type, "source");
/*    */   Stream<CompoundTag> getData(CommandSourceStack paramCommandSourceStack) throws CommandSyntaxException;
/*    */   Type<?> type();
/*    */   
/*    */   public static final class Type<T extends DataSource> extends Record implements StringRepresentable { private final MapCodec<T> codec;
/*    */     private final String id;
/*    */     
/*    */     public final String toString() {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/contents/DataSource$Type;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #28	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/contents/DataSource$Type;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/contents/DataSource$Type<TT;>;
/*    */     }
/*    */     
/*    */     public final int hashCode() {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/contents/DataSource$Type;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #28	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/contents/DataSource$Type;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/contents/DataSource$Type<TT;>;
/*    */     }
/*    */     
/* 28 */     public Type(MapCodec<T> $$0, String $$1) { this.codec = $$0; this.id = $$1; } public MapCodec<T> codec() { return this.codec; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/contents/DataSource$Type;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #28	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/chat/contents/DataSource$Type;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 28 */       //   0	8	0	this	Lnet/minecraft/network/chat/contents/DataSource$Type<TT;>; } public String id() { return this.id; }
/*    */     
/*    */     public String getSerializedName() {
/* 31 */       return this.id;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\contents\DataSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */