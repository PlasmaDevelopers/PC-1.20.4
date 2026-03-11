/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.StringTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.Items;
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
/*     */ public final class Decorations
/*     */   extends Record
/*     */ {
/*     */   private final Item back;
/*     */   private final Item left;
/*     */   private final Item right;
/*     */   private final Item front;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity$Decorations;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #123	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity$Decorations;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity$Decorations;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #123	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity$Decorations;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity$Decorations;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #123	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/world/level/block/entity/DecoratedPotBlockEntity$Decorations;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public Decorations(Item $$0, Item $$1, Item $$2, Item $$3) {
/* 123 */     this.back = $$0; this.left = $$1; this.right = $$2; this.front = $$3; } public Item back() { return this.back; } public Item left() { return this.left; } public Item right() { return this.right; } public Item front() { return this.front; }
/* 124 */    public static final Decorations EMPTY = new Decorations(Items.BRICK, Items.BRICK, Items.BRICK, Items.BRICK);
/*     */   
/*     */   public CompoundTag save(CompoundTag $$0) {
/* 127 */     if (equals(EMPTY)) {
/* 128 */       return $$0;
/*     */     }
/* 130 */     ListTag $$1 = new ListTag();
/* 131 */     sorted().forEach($$1 -> $$0.add(StringTag.valueOf(BuiltInRegistries.ITEM.getKey($$1).toString())));
/* 132 */     $$0.put("sherds", (Tag)$$1);
/* 133 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<Item> sorted() {
/* 138 */     return Stream.of(new Item[] { this.back, this.left, this.right, this.front });
/*     */   }
/*     */   
/*     */   public static Decorations load(@Nullable CompoundTag $$0) {
/* 142 */     if ($$0 == null || !$$0.contains("sherds", 9)) {
/* 143 */       return EMPTY;
/*     */     }
/*     */     
/* 146 */     ListTag $$1 = $$0.getList("sherds", 8);
/*     */     
/* 148 */     return new Decorations(
/* 149 */         itemFromTag($$1, 0), 
/* 150 */         itemFromTag($$1, 1), 
/* 151 */         itemFromTag($$1, 2), 
/* 152 */         itemFromTag($$1, 3));
/*     */   }
/*     */ 
/*     */   
/*     */   private static Item itemFromTag(ListTag $$0, int $$1) {
/* 157 */     if ($$1 >= $$0.size()) {
/* 158 */       return Items.BRICK;
/*     */     }
/* 160 */     Tag $$2 = $$0.get($$1);
/* 161 */     return (Item)BuiltInRegistries.ITEM.get(ResourceLocation.tryParse($$2.getAsString()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\DecoratedPotBlockEntity$Decorations.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */