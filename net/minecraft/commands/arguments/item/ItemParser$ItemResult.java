/*    */ package net.minecraft.commands.arguments.item;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.nbt.CompoundTag;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ItemResult
/*    */   extends Record
/*    */ {
/*    */   private final Holder<Item> item;
/*    */   @Nullable
/*    */   private final CompoundTag nbt;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/commands/arguments/item/ItemParser$ItemResult;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #52	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/commands/arguments/item/ItemParser$ItemResult;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/arguments/item/ItemParser$ItemResult;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #52	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/commands/arguments/item/ItemParser$ItemResult;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/commands/arguments/item/ItemParser$ItemResult;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #52	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/commands/arguments/item/ItemParser$ItemResult;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public ItemResult(Holder<Item> $$0, @Nullable CompoundTag $$1) {
/* 52 */     this.item = $$0; this.nbt = $$1; } public Holder<Item> item() { return this.item; } @Nullable public CompoundTag nbt() { return this.nbt; }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\item\ItemParser$ItemResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */