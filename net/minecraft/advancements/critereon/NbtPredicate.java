/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.nbt.TagParser;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public final class NbtPredicate extends Record {
/*    */   private final CompoundTag tag;
/*    */   
/* 14 */   public NbtPredicate(CompoundTag $$0) { this.tag = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/NbtPredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 14 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/NbtPredicate; } public CompoundTag tag() { return this.tag; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/NbtPredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/NbtPredicate; } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/NbtPredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/NbtPredicate;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/* 17 */   } public static final Codec<NbtPredicate> CODEC = TagParser.AS_CODEC.xmap(NbtPredicate::new, NbtPredicate::tag);
/*    */   
/*    */   public boolean matches(ItemStack $$0) {
/* 20 */     return matches((Tag)$$0.getTag());
/*    */   }
/*    */   
/*    */   public boolean matches(Entity $$0) {
/* 24 */     return matches((Tag)getEntityTagToCompare($$0));
/*    */   }
/*    */   
/*    */   public boolean matches(@Nullable Tag $$0) {
/* 28 */     return ($$0 != null && NbtUtils.compareNbt((Tag)this.tag, $$0, true));
/*    */   }
/*    */   
/*    */   public static CompoundTag getEntityTagToCompare(Entity $$0) {
/* 32 */     CompoundTag $$1 = $$0.saveWithoutId(new CompoundTag());
/* 33 */     if ($$0 instanceof Player) {
/* 34 */       ItemStack $$2 = ((Player)$$0).getInventory().getSelected();
/* 35 */       if (!$$2.isEmpty()) {
/* 36 */         $$1.put("SelectedItem", (Tag)$$2.save(new CompoundTag()));
/*    */       }
/*    */     } 
/* 39 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\NbtPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */