/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ 
/*     */ 
/*     */ public interface DyeableLeatherItem
/*     */ {
/*     */   public static final String TAG_COLOR = "color";
/*     */   public static final String TAG_DISPLAY = "display";
/*     */   public static final int DEFAULT_LEATHER_COLOR = 10511680;
/*     */   
/*     */   default boolean hasCustomColor(ItemStack $$0) {
/*  14 */     CompoundTag $$1 = $$0.getTagElement("display");
/*  15 */     return ($$1 != null && $$1.contains("color", 99));
/*     */   }
/*     */   
/*     */   default int getColor(ItemStack $$0) {
/*  19 */     CompoundTag $$1 = $$0.getTagElement("display");
/*  20 */     if ($$1 != null && $$1.contains("color", 99)) {
/*  21 */       return $$1.getInt("color");
/*     */     }
/*  23 */     return 10511680;
/*     */   }
/*     */   
/*     */   default void clearColor(ItemStack $$0) {
/*  27 */     CompoundTag $$1 = $$0.getTagElement("display");
/*  28 */     if ($$1 != null && $$1.contains("color")) {
/*  29 */       $$1.remove("color");
/*     */     }
/*     */   }
/*     */   
/*     */   default void setColor(ItemStack $$0, int $$1) {
/*  34 */     $$0.getOrCreateTagElement("display").putInt("color", $$1);
/*     */   }
/*     */   
/*     */   static ItemStack dyeArmor(ItemStack $$0, List<DyeItem> $$1) {
/*  38 */     ItemStack $$2 = ItemStack.EMPTY;
/*  39 */     int[] $$3 = new int[3];
/*  40 */     int $$4 = 0;
/*  41 */     int $$5 = 0;
/*  42 */     DyeableLeatherItem $$6 = null;
/*     */     
/*  44 */     Item $$7 = $$0.getItem();
/*  45 */     if ($$7 instanceof DyeableLeatherItem) {
/*  46 */       $$6 = (DyeableLeatherItem)$$7;
/*     */       
/*  48 */       $$2 = $$0.copyWithCount(1);
/*     */ 
/*     */       
/*  51 */       if ($$6.hasCustomColor($$0)) {
/*  52 */         int $$8 = $$6.getColor($$2);
/*  53 */         float $$9 = ($$8 >> 16 & 0xFF) / 255.0F;
/*  54 */         float $$10 = ($$8 >> 8 & 0xFF) / 255.0F;
/*  55 */         float $$11 = ($$8 & 0xFF) / 255.0F;
/*     */         
/*  57 */         $$4 += (int)(Math.max($$9, Math.max($$10, $$11)) * 255.0F);
/*     */         
/*  59 */         $$3[0] = $$3[0] + (int)($$9 * 255.0F);
/*  60 */         $$3[1] = $$3[1] + (int)($$10 * 255.0F);
/*  61 */         $$3[2] = $$3[2] + (int)($$11 * 255.0F);
/*  62 */         $$5++;
/*     */       } 
/*     */ 
/*     */       
/*  66 */       for (DyeItem $$12 : $$1) {
/*  67 */         float[] $$13 = $$12.getDyeColor().getTextureDiffuseColors();
/*  68 */         int $$14 = (int)($$13[0] * 255.0F);
/*  69 */         int $$15 = (int)($$13[1] * 255.0F);
/*  70 */         int $$16 = (int)($$13[2] * 255.0F);
/*     */         
/*  72 */         $$4 += Math.max($$14, Math.max($$15, $$16));
/*     */         
/*  74 */         $$3[0] = $$3[0] + $$14;
/*  75 */         $$3[1] = $$3[1] + $$15;
/*  76 */         $$3[2] = $$3[2] + $$16;
/*  77 */         $$5++;
/*     */       } 
/*     */     } 
/*     */     
/*  81 */     if ($$6 == null) {
/*  82 */       return ItemStack.EMPTY;
/*     */     }
/*     */     
/*  85 */     int $$17 = $$3[0] / $$5;
/*  86 */     int $$18 = $$3[1] / $$5;
/*  87 */     int $$19 = $$3[2] / $$5;
/*     */     
/*  89 */     float $$20 = $$4 / $$5;
/*  90 */     float $$21 = Math.max($$17, Math.max($$18, $$19));
/*     */     
/*  92 */     $$17 = (int)($$17 * $$20 / $$21);
/*  93 */     $$18 = (int)($$18 * $$20 / $$21);
/*  94 */     $$19 = (int)($$19 * $$20 / $$21);
/*     */     
/*  96 */     int $$22 = $$17;
/*  97 */     $$22 = ($$22 << 8) + $$18;
/*  98 */     $$22 = ($$22 << 8) + $$19;
/*     */     
/* 100 */     $$6.setColor($$2, $$22);
/* 101 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\DyeableLeatherItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */