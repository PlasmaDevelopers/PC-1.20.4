/*    */ package net.minecraft.world.inventory;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.world.item.ItemStack;
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
/*    */ public class Builder
/*    */ {
/* 58 */   private final List<ItemCombinerMenuSlotDefinition.SlotDefinition> slots = new ArrayList<>();
/* 59 */   private ItemCombinerMenuSlotDefinition.SlotDefinition resultSlot = ItemCombinerMenuSlotDefinition.SlotDefinition.EMPTY;
/*    */   
/*    */   public Builder withSlot(int $$0, int $$1, int $$2, Predicate<ItemStack> $$3) {
/* 62 */     this.slots.add(new ItemCombinerMenuSlotDefinition.SlotDefinition($$0, $$1, $$2, $$3));
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public Builder withResultSlot(int $$0, int $$1, int $$2) {
/* 67 */     this.resultSlot = new ItemCombinerMenuSlotDefinition.SlotDefinition($$0, $$1, $$2, $$0 -> false);
/* 68 */     return this;
/*    */   }
/*    */   
/*    */   public ItemCombinerMenuSlotDefinition build() {
/* 72 */     return new ItemCombinerMenuSlotDefinition(this.slots, this.resultSlot);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\ItemCombinerMenuSlotDefinition$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */