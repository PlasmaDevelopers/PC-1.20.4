/*     */ package net.minecraft.world.item.trading;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.function.IntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class MerchantOffers
/*     */   extends ArrayList<MerchantOffer> {
/*     */   public MerchantOffers() {}
/*     */   
/*     */   private MerchantOffers(int $$0) {
/*  17 */     super($$0);
/*     */   }
/*     */   
/*     */   public MerchantOffers(CompoundTag $$0) {
/*  21 */     ListTag $$1 = $$0.getList("Recipes", 10);
/*     */     
/*  23 */     for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/*  24 */       add(new MerchantOffer($$1.getCompound($$2)));
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MerchantOffer getRecipeFor(ItemStack $$0, ItemStack $$1, int $$2) {
/*  30 */     if ($$2 > 0 && $$2 < size()) {
/*     */       
/*  32 */       MerchantOffer $$3 = get($$2);
/*  33 */       if ($$3.satisfiedBy($$0, $$1)) {
/*  34 */         return $$3;
/*     */       }
/*  36 */       return null;
/*     */     } 
/*     */     
/*  39 */     for (int $$4 = 0; $$4 < size(); $$4++) {
/*  40 */       MerchantOffer $$5 = get($$4);
/*  41 */       if ($$5.satisfiedBy($$0, $$1)) {
/*  42 */         return $$5;
/*     */       }
/*     */     } 
/*  45 */     return null;
/*     */   }
/*     */   
/*     */   public void writeToStream(FriendlyByteBuf $$0) {
/*  49 */     $$0.writeCollection(this, ($$0, $$1) -> {
/*     */           $$0.writeItem($$1.getBaseCostA());
/*     */           $$0.writeItem($$1.getResult());
/*     */           $$0.writeItem($$1.getCostB());
/*     */           $$0.writeBoolean($$1.isOutOfStock());
/*     */           $$0.writeInt($$1.getUses());
/*     */           $$0.writeInt($$1.getMaxUses());
/*     */           $$0.writeInt($$1.getXp());
/*     */           $$0.writeInt($$1.getSpecialPriceDiff());
/*     */           $$0.writeFloat($$1.getPriceMultiplier());
/*     */           $$0.writeInt($$1.getDemand());
/*     */         });
/*     */   }
/*     */   
/*     */   public static MerchantOffers createFromStream(FriendlyByteBuf $$0) {
/*  64 */     return (MerchantOffers)$$0.readCollection(MerchantOffers::new, $$0 -> {
/*     */           ItemStack $$1 = $$0.readItem();
/*     */           ItemStack $$2 = $$0.readItem();
/*     */           ItemStack $$3 = $$0.readItem();
/*     */           boolean $$4 = $$0.readBoolean();
/*     */           int $$5 = $$0.readInt();
/*     */           int $$6 = $$0.readInt();
/*     */           int $$7 = $$0.readInt();
/*     */           int $$8 = $$0.readInt();
/*     */           float $$9 = $$0.readFloat();
/*     */           int $$10 = $$0.readInt();
/*     */           MerchantOffer $$11 = new MerchantOffer($$1, $$3, $$2, $$5, $$6, $$7, $$9, $$10);
/*     */           if ($$4) {
/*     */             $$11.setToOutOfStock();
/*     */           }
/*     */           $$11.setSpecialPriceDiff($$8);
/*     */           return $$11;
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag createTag() {
/*  86 */     CompoundTag $$0 = new CompoundTag();
/*     */     
/*  88 */     ListTag $$1 = new ListTag();
/*  89 */     for (int $$2 = 0; $$2 < size(); $$2++) {
/*  90 */       MerchantOffer $$3 = get($$2);
/*  91 */       $$1.add($$3.createTag());
/*     */     } 
/*  93 */     $$0.put("Recipes", (Tag)$$1);
/*  94 */     return $$0;
/*     */   }
/*     */   
/*     */   public MerchantOffers copy() {
/*  98 */     MerchantOffers $$0 = new MerchantOffers(size());
/*  99 */     for (MerchantOffer $$1 : this) {
/* 100 */       $$0.add($$1.copy());
/*     */     }
/* 102 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\trading\MerchantOffers.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */