/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public interface ComponentContents
/*    */ {
/*    */   default <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
/* 14 */     return Optional.empty();
/*    */   }
/*    */   
/*    */   default <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
/* 18 */     return Optional.empty();
/*    */   }
/*    */   
/*    */   default MutableComponent resolve(@Nullable CommandSourceStack $$0, @Nullable Entity $$1, int $$2) throws CommandSyntaxException {
/* 22 */     return MutableComponent.create(this);
/*    */   }
/*    */   Type<?> type();
/*    */   public static final class Type<T extends ComponentContents> extends Record implements StringRepresentable { private final MapCodec<T> codec; private final String id;
/*    */     
/* 27 */     public Type(MapCodec<T> $$0, String $$1) { this.codec = $$0; this.id = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/ComponentContents$Type;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #27	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/ComponentContents$Type;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 27 */       //   0	7	0	this	Lnet/minecraft/network/chat/ComponentContents$Type<TT;>; } public MapCodec<T> codec() { return this.codec; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/ComponentContents$Type;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #27	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/ComponentContents$Type;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/ComponentContents$Type<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/ComponentContents$Type;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #27	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/chat/ComponentContents$Type;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 27 */       //   0	8	0	this	Lnet/minecraft/network/chat/ComponentContents$Type<TT;>; } public String id() { return this.id; }
/*    */     
/*    */     public String getSerializedName() {
/* 30 */       return this.id;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ComponentContents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */