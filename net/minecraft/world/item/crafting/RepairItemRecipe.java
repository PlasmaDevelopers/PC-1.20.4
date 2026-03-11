/*     */ package net.minecraft.world.item.crafting;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.inventory.CraftingContainer;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.enchantment.Enchantment;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class RepairItemRecipe extends CustomRecipe {
/*     */   public RepairItemRecipe(CraftingBookCategory $$0) {
/*  19 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matches(CraftingContainer $$0, Level $$1) {
/*  24 */     List<ItemStack> $$2 = Lists.newArrayList();
/*     */     
/*  26 */     for (int $$3 = 0; $$3 < $$0.getContainerSize(); $$3++) {
/*  27 */       ItemStack $$4 = $$0.getItem($$3);
/*     */       
/*  29 */       if (!$$4.isEmpty()) {
/*  30 */         $$2.add($$4);
/*     */         
/*  32 */         if ($$2.size() > 1) {
/*  33 */           ItemStack $$5 = $$2.get(0);
/*  34 */           if (!$$4.is($$5.getItem()) || $$5.getCount() != 1 || $$4.getCount() != 1 || !$$5.getItem().canBeDepleted()) {
/*  35 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  41 */     return ($$2.size() == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack assemble(CraftingContainer $$0, RegistryAccess $$1) {
/*  46 */     List<ItemStack> $$2 = Lists.newArrayList();
/*     */     
/*  48 */     for (int $$3 = 0; $$3 < $$0.getContainerSize(); $$3++) {
/*  49 */       ItemStack $$4 = $$0.getItem($$3);
/*     */       
/*  51 */       if (!$$4.isEmpty()) {
/*  52 */         $$2.add($$4);
/*     */         
/*  54 */         if ($$2.size() > 1) {
/*  55 */           ItemStack $$5 = $$2.get(0);
/*  56 */           if (!$$4.is($$5.getItem()) || $$5.getCount() != 1 || $$4.getCount() != 1 || !$$5.getItem().canBeDepleted()) {
/*  57 */             return ItemStack.EMPTY;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  63 */     if ($$2.size() == 2) {
/*  64 */       ItemStack $$6 = $$2.get(0);
/*  65 */       ItemStack $$7 = $$2.get(1);
/*     */       
/*  67 */       if ($$6.is($$7.getItem()) && $$6.getCount() == 1 && $$7.getCount() == 1 && $$6.getItem().canBeDepleted()) {
/*  68 */         Item $$8 = $$6.getItem();
/*  69 */         int $$9 = $$8.getMaxDamage() - $$6.getDamageValue();
/*  70 */         int $$10 = $$8.getMaxDamage() - $$7.getDamageValue();
/*  71 */         int $$11 = $$9 + $$10 + $$8.getMaxDamage() * 5 / 100;
/*  72 */         int $$12 = $$8.getMaxDamage() - $$11;
/*  73 */         if ($$12 < 0) {
/*  74 */           $$12 = 0;
/*     */         }
/*     */         
/*  77 */         ItemStack $$13 = new ItemStack((ItemLike)$$6.getItem());
/*  78 */         $$13.setDamageValue($$12);
/*     */         
/*  80 */         Map<Enchantment, Integer> $$14 = Maps.newHashMap();
/*  81 */         Map<Enchantment, Integer> $$15 = EnchantmentHelper.getEnchantments($$6);
/*  82 */         Map<Enchantment, Integer> $$16 = EnchantmentHelper.getEnchantments($$7);
/*  83 */         BuiltInRegistries.ENCHANTMENT.stream().filter(Enchantment::isCurse).forEach($$3 -> {
/*     */               int $$4 = Math.max(((Integer)$$0.getOrDefault($$3, Integer.valueOf(0))).intValue(), ((Integer)$$1.getOrDefault($$3, Integer.valueOf(0))).intValue());
/*     */               
/*     */               if ($$4 > 0) {
/*     */                 $$2.put($$3, Integer.valueOf($$4));
/*     */               }
/*     */             });
/*  90 */         if (!$$14.isEmpty()) {
/*  91 */           EnchantmentHelper.setEnchantments($$14, $$13);
/*     */         }
/*     */         
/*  94 */         return $$13;
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCraftInDimensions(int $$0, int $$1) {
/* 103 */     return ($$0 * $$1 >= 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeSerializer<?> getSerializer() {
/* 108 */     return RecipeSerializer.REPAIR_ITEM;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\crafting\RepairItemRecipe.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */