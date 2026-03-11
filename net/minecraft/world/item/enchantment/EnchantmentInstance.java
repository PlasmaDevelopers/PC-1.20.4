/*    */ package net.minecraft.world.item.enchantment;
/*    */ 
/*    */ import net.minecraft.util.random.WeightedEntry;
/*    */ 
/*    */ public class EnchantmentInstance extends WeightedEntry.IntrusiveBase {
/*    */   public final Enchantment enchantment;
/*    */   public final int level;
/*    */   
/*    */   public EnchantmentInstance(Enchantment $$0, int $$1) {
/* 10 */     super($$0.getRarity().getWeight());
/* 11 */     this.enchantment = $$0;
/* 12 */     this.level = $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\enchantment\EnchantmentInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */