/*     */ package net.minecraft.world.item.trading;
/*     */ 
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class MerchantOffer
/*     */ {
/*     */   private final ItemStack baseCostA;
/*     */   private final ItemStack costB;
/*     */   private final ItemStack result;
/*     */   private int uses;
/*     */   private final int maxUses;
/*     */   private boolean rewardExp = true;
/*     */   private int specialPriceDiff;
/*     */   private int demand;
/*     */   private float priceMultiplier;
/*  20 */   private int xp = 1;
/*     */   
/*     */   public MerchantOffer(CompoundTag $$0) {
/*  23 */     this.baseCostA = ItemStack.of($$0.getCompound("buy"));
/*  24 */     this.costB = ItemStack.of($$0.getCompound("buyB"));
/*     */     
/*  26 */     this.result = ItemStack.of($$0.getCompound("sell"));
/*     */     
/*  28 */     this.uses = $$0.getInt("uses");
/*  29 */     if ($$0.contains("maxUses", 99)) {
/*  30 */       this.maxUses = $$0.getInt("maxUses");
/*     */     } else {
/*  32 */       this.maxUses = 4;
/*     */     } 
/*     */     
/*  35 */     if ($$0.contains("rewardExp", 1)) {
/*  36 */       this.rewardExp = $$0.getBoolean("rewardExp");
/*     */     }
/*     */     
/*  39 */     if ($$0.contains("xp", 3)) {
/*  40 */       this.xp = $$0.getInt("xp");
/*     */     }
/*     */     
/*  43 */     if ($$0.contains("priceMultiplier", 5)) {
/*  44 */       this.priceMultiplier = $$0.getFloat("priceMultiplier");
/*     */     }
/*     */     
/*  47 */     this.specialPriceDiff = $$0.getInt("specialPrice");
/*  48 */     this.demand = $$0.getInt("demand");
/*     */   }
/*     */   
/*     */   public MerchantOffer(ItemStack $$0, ItemStack $$1, int $$2, int $$3, float $$4) {
/*  52 */     this($$0, ItemStack.EMPTY, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public MerchantOffer(ItemStack $$0, ItemStack $$1, ItemStack $$2, int $$3, int $$4, float $$5) {
/*  56 */     this($$0, $$1, $$2, 0, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public MerchantOffer(ItemStack $$0, ItemStack $$1, ItemStack $$2, int $$3, int $$4, int $$5, float $$6) {
/*  60 */     this($$0, $$1, $$2, $$3, $$4, $$5, $$6, 0);
/*     */   }
/*     */   
/*     */   public MerchantOffer(ItemStack $$0, ItemStack $$1, ItemStack $$2, int $$3, int $$4, int $$5, float $$6, int $$7) {
/*  64 */     this.baseCostA = $$0;
/*  65 */     this.costB = $$1;
/*  66 */     this.result = $$2;
/*  67 */     this.uses = $$3;
/*  68 */     this.maxUses = $$4;
/*  69 */     this.xp = $$5;
/*  70 */     this.priceMultiplier = $$6;
/*  71 */     this.demand = $$7;
/*     */   }
/*     */   
/*     */   private MerchantOffer(MerchantOffer $$0) {
/*  75 */     this.baseCostA = $$0.baseCostA.copy();
/*  76 */     this.costB = $$0.costB.copy();
/*  77 */     this.result = $$0.result.copy();
/*  78 */     this.uses = $$0.uses;
/*  79 */     this.maxUses = $$0.maxUses;
/*  80 */     this.rewardExp = $$0.rewardExp;
/*  81 */     this.specialPriceDiff = $$0.specialPriceDiff;
/*  82 */     this.demand = $$0.demand;
/*  83 */     this.priceMultiplier = $$0.priceMultiplier;
/*  84 */     this.xp = $$0.xp;
/*     */   }
/*     */   
/*     */   public ItemStack getBaseCostA() {
/*  88 */     return this.baseCostA;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCostA() {
/*  93 */     if (this.baseCostA.isEmpty()) {
/*  94 */       return ItemStack.EMPTY;
/*     */     }
/*     */     
/*  97 */     int $$0 = this.baseCostA.getCount();
/*     */ 
/*     */     
/* 100 */     int $$1 = Math.max(0, Mth.floor(($$0 * this.demand) * this.priceMultiplier));
/*     */     
/* 102 */     return this.baseCostA.copyWithCount(Mth.clamp($$0 + $$1 + this.specialPriceDiff, 1, this.baseCostA.getItem().getMaxStackSize()));
/*     */   }
/*     */   
/*     */   public ItemStack getCostB() {
/* 106 */     return this.costB;
/*     */   }
/*     */   
/*     */   public ItemStack getResult() {
/* 110 */     return this.result;
/*     */   }
/*     */   
/*     */   public void updateDemand() {
/* 114 */     this.demand = this.demand + this.uses - this.maxUses - this.uses;
/*     */   }
/*     */   
/*     */   public ItemStack assemble() {
/* 118 */     return this.result.copy();
/*     */   }
/*     */   
/*     */   public int getUses() {
/* 122 */     return this.uses;
/*     */   }
/*     */   
/*     */   public void resetUses() {
/* 126 */     this.uses = 0;
/*     */   }
/*     */   
/*     */   public int getMaxUses() {
/* 130 */     return this.maxUses;
/*     */   }
/*     */   
/*     */   public void increaseUses() {
/* 134 */     this.uses++;
/*     */   }
/*     */   
/*     */   public int getDemand() {
/* 138 */     return this.demand;
/*     */   }
/*     */   
/*     */   public void addToSpecialPriceDiff(int $$0) {
/* 142 */     this.specialPriceDiff += $$0;
/*     */   }
/*     */   
/*     */   public void resetSpecialPriceDiff() {
/* 146 */     this.specialPriceDiff = 0;
/*     */   }
/*     */   
/*     */   public int getSpecialPriceDiff() {
/* 150 */     return this.specialPriceDiff;
/*     */   }
/*     */   
/*     */   public void setSpecialPriceDiff(int $$0) {
/* 154 */     this.specialPriceDiff = $$0;
/*     */   }
/*     */   
/*     */   public float getPriceMultiplier() {
/* 158 */     return this.priceMultiplier;
/*     */   }
/*     */   
/*     */   public int getXp() {
/* 162 */     return this.xp;
/*     */   }
/*     */   
/*     */   public boolean isOutOfStock() {
/* 166 */     return (this.uses >= this.maxUses);
/*     */   }
/*     */   
/*     */   public void setToOutOfStock() {
/* 170 */     this.uses = this.maxUses;
/*     */   }
/*     */   
/*     */   public boolean needsRestock() {
/* 174 */     return (this.uses > 0);
/*     */   }
/*     */   
/*     */   public boolean shouldRewardExp() {
/* 178 */     return this.rewardExp;
/*     */   }
/*     */   
/*     */   public CompoundTag createTag() {
/* 182 */     CompoundTag $$0 = new CompoundTag();
/* 183 */     $$0.put("buy", (Tag)this.baseCostA.save(new CompoundTag()));
/* 184 */     $$0.put("sell", (Tag)this.result.save(new CompoundTag()));
/* 185 */     $$0.put("buyB", (Tag)this.costB.save(new CompoundTag()));
/* 186 */     $$0.putInt("uses", this.uses);
/* 187 */     $$0.putInt("maxUses", this.maxUses);
/* 188 */     $$0.putBoolean("rewardExp", this.rewardExp);
/* 189 */     $$0.putInt("xp", this.xp);
/* 190 */     $$0.putFloat("priceMultiplier", this.priceMultiplier);
/* 191 */     $$0.putInt("specialPrice", this.specialPriceDiff);
/* 192 */     $$0.putInt("demand", this.demand);
/* 193 */     return $$0;
/*     */   }
/*     */   
/*     */   public boolean satisfiedBy(ItemStack $$0, ItemStack $$1) {
/* 197 */     return (isRequiredItem($$0, getCostA()) && $$0.getCount() >= getCostA().getCount() && 
/* 198 */       isRequiredItem($$1, this.costB) && $$1.getCount() >= this.costB.getCount());
/*     */   }
/*     */   
/*     */   private boolean isRequiredItem(ItemStack $$0, ItemStack $$1) {
/* 202 */     if ($$1.isEmpty() && $$0.isEmpty()) {
/* 203 */       return true;
/*     */     }
/*     */     
/* 206 */     ItemStack $$2 = $$0.copy();
/* 207 */     if ($$2.getItem().canBeDepleted()) {
/* 208 */       $$2.setDamageValue($$2.getDamageValue());
/*     */     }
/* 210 */     return (ItemStack.isSameItem($$2, $$1) && (!$$1.hasTag() || ($$2.hasTag() && NbtUtils.compareNbt((Tag)$$1.getTag(), (Tag)$$2.getTag(), false))));
/*     */   }
/*     */   
/*     */   public boolean take(ItemStack $$0, ItemStack $$1) {
/* 214 */     if (!satisfiedBy($$0, $$1)) {
/* 215 */       return false;
/*     */     }
/*     */     
/* 218 */     $$0.shrink(getCostA().getCount());
/* 219 */     if (!getCostB().isEmpty()) {
/* 220 */       $$1.shrink(getCostB().getCount());
/*     */     }
/* 222 */     return true;
/*     */   }
/*     */   
/*     */   public MerchantOffer copy() {
/* 226 */     return new MerchantOffer(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\trading\MerchantOffer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */