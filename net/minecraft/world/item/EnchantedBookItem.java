/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*    */ import net.minecraft.world.item.enchantment.EnchantmentInstance;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class EnchantedBookItem
/*    */   extends Item {
/*    */   public static final String TAG_STORED_ENCHANTMENTS = "StoredEnchantments";
/*    */   
/*    */   public EnchantedBookItem(Item.Properties $$0) {
/* 19 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFoil(ItemStack $$0) {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnchantable(ItemStack $$0) {
/* 29 */     return false;
/*    */   }
/*    */   
/*    */   public static ListTag getEnchantments(ItemStack $$0) {
/* 33 */     CompoundTag $$1 = $$0.getTag();
/* 34 */     if ($$1 != null) {
/* 35 */       return $$1.getList("StoredEnchantments", 10);
/*    */     }
/*    */     
/* 38 */     return new ListTag();
/*    */   }
/*    */ 
/*    */   
/*    */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 43 */     super.appendHoverText($$0, $$1, $$2, $$3);
/* 44 */     ItemStack.appendEnchantmentNames($$2, getEnchantments($$0));
/*    */   }
/*    */   
/*    */   public static void addEnchantment(ItemStack $$0, EnchantmentInstance $$1) {
/* 48 */     ListTag $$2 = getEnchantments($$0);
/* 49 */     boolean $$3 = true;
/*    */     
/* 51 */     ResourceLocation $$4 = EnchantmentHelper.getEnchantmentId($$1.enchantment);
/* 52 */     for (int $$5 = 0; $$5 < $$2.size(); $$5++) {
/* 53 */       CompoundTag $$6 = $$2.getCompound($$5);
/*    */       
/* 55 */       ResourceLocation $$7 = EnchantmentHelper.getEnchantmentId($$6);
/* 56 */       if ($$7 != null && $$7.equals($$4)) {
/* 57 */         if (EnchantmentHelper.getEnchantmentLevel($$6) < $$1.level) {
/* 58 */           EnchantmentHelper.setEnchantmentLevel($$6, $$1.level);
/*    */         }
/*    */         
/* 61 */         $$3 = false;
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 66 */     if ($$3) {
/* 67 */       $$2.add(EnchantmentHelper.storeEnchantment($$4, $$1.level));
/*    */     }
/*    */     
/* 70 */     $$0.getOrCreateTag().put("StoredEnchantments", (Tag)$$2);
/*    */   }
/*    */   
/*    */   public static ItemStack createForEnchantment(EnchantmentInstance $$0) {
/* 74 */     ItemStack $$1 = new ItemStack(Items.ENCHANTED_BOOK);
/* 75 */     addEnchantment($$1, $$0);
/* 76 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\EnchantedBookItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */