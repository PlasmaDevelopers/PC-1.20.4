/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class ItemCombinerMenuSlotDefinition
/*    */ {
/*    */   private final List<SlotDefinition> slots;
/*    */   private final SlotDefinition resultSlot;
/*    */   
/*    */   ItemCombinerMenuSlotDefinition(List<SlotDefinition> $$0, SlotDefinition $$1) {
/* 16 */     if ($$0.isEmpty() || $$1.equals(SlotDefinition.EMPTY)) {
/* 17 */       throw new IllegalArgumentException("Need to define both inputSlots and resultSlot");
/*    */     }
/* 19 */     this.slots = $$0;
/* 20 */     this.resultSlot = $$1;
/*    */   }
/*    */   
/*    */   public static Builder create() {
/* 24 */     return new Builder();
/*    */   }
/*    */   
/*    */   public boolean hasSlot(int $$0) {
/* 28 */     return (this.slots.size() >= $$0);
/*    */   }
/*    */   
/*    */   public SlotDefinition getSlot(int $$0) {
/* 32 */     return this.slots.get($$0);
/*    */   }
/*    */   
/*    */   public SlotDefinition getResultSlot() {
/* 36 */     return this.resultSlot;
/*    */   }
/*    */   
/*    */   public List<SlotDefinition> getSlots() {
/* 40 */     return this.slots;
/*    */   }
/*    */   
/*    */   public int getNumOfInputSlots() {
/* 44 */     return this.slots.size();
/*    */   }
/*    */   
/*    */   public int getResultSlotIndex() {
/* 48 */     return getNumOfInputSlots();
/*    */   }
/*    */   
/*    */   public List<Integer> getInputSlotIndexes() {
/* 52 */     return (List<Integer>)this.slots.stream()
/* 53 */       .map(SlotDefinition::slotIndex)
/* 54 */       .collect(Collectors.toList());
/*    */   }
/*    */   
/*    */   public static class Builder {
/* 58 */     private final List<ItemCombinerMenuSlotDefinition.SlotDefinition> slots = new ArrayList<>();
/* 59 */     private ItemCombinerMenuSlotDefinition.SlotDefinition resultSlot = ItemCombinerMenuSlotDefinition.SlotDefinition.EMPTY;
/*    */     
/*    */     public Builder withSlot(int $$0, int $$1, int $$2, Predicate<ItemStack> $$3) {
/* 62 */       this.slots.add(new ItemCombinerMenuSlotDefinition.SlotDefinition($$0, $$1, $$2, $$3));
/* 63 */       return this;
/*    */     }
/*    */     
/*    */     public Builder withResultSlot(int $$0, int $$1, int $$2) {
/* 67 */       this.resultSlot = new ItemCombinerMenuSlotDefinition.SlotDefinition($$0, $$1, $$2, $$0 -> false);
/* 68 */       return this;
/*    */     }
/*    */     
/*    */     public ItemCombinerMenuSlotDefinition build() {
/* 72 */       return new ItemCombinerMenuSlotDefinition(this.slots, this.resultSlot);
/*    */     } }
/*    */   public static final class SlotDefinition extends Record { private final int slotIndex; private final int x; private final int y; private final Predicate<ItemStack> mayPlace;
/*    */     
/* 76 */     public SlotDefinition(int $$0, int $$1, int $$2, Predicate<ItemStack> $$3) { this.slotIndex = $$0; this.x = $$1; this.y = $$2; this.mayPlace = $$3; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$SlotDefinition;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #76	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 76 */       //   0	7	0	this	Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$SlotDefinition; } public int slotIndex() { return this.slotIndex; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$SlotDefinition;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #76	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$SlotDefinition; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$SlotDefinition;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #76	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/inventory/ItemCombinerMenuSlotDefinition$SlotDefinition;
/* 76 */       //   0	8	1	$$0	Ljava/lang/Object; } public int x() { return this.x; } public int y() { return this.y; } public Predicate<ItemStack> mayPlace() { return this.mayPlace; }
/* 77 */      static final SlotDefinition EMPTY = new SlotDefinition(0, 0, 0, $$0 -> true); }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\ItemCombinerMenuSlotDefinition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */