/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import java.util.Map;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.ExperienceOrb;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.enchantment.Enchantment;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec3;
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
/*     */ class null
/*     */   extends Slot
/*     */ {
/*     */   null(Container $$1, int $$2, int $$3, int $$4) {
/*  64 */     super($$1, $$2, $$3, $$4);
/*     */   }
/*     */   public boolean mayPlace(ItemStack $$0) {
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTake(Player $$0, ItemStack $$1) {
/*  72 */     access.execute(($$0, $$1) -> {
/*     */           if ($$0 instanceof ServerLevel) {
/*     */             ExperienceOrb.award((ServerLevel)$$0, Vec3.atCenterOf((Vec3i)$$1), getExperienceAmount($$0));
/*     */           }
/*     */           
/*     */           $$0.levelEvent(1042, $$1, 0);
/*     */         });
/*  79 */     GrindstoneMenu.this.repairSlots.setItem(0, ItemStack.EMPTY);
/*  80 */     GrindstoneMenu.this.repairSlots.setItem(1, ItemStack.EMPTY);
/*     */   }
/*     */   
/*     */   private int getExperienceAmount(Level $$0) {
/*  84 */     int $$1 = 0;
/*  85 */     $$1 += getExperienceFromItem(GrindstoneMenu.this.repairSlots.getItem(0));
/*  86 */     $$1 += getExperienceFromItem(GrindstoneMenu.this.repairSlots.getItem(1));
/*     */     
/*  88 */     if ($$1 > 0) {
/*  89 */       int $$2 = (int)Math.ceil($$1 / 2.0D);
/*  90 */       return $$2 + $$0.random.nextInt($$2);
/*     */     } 
/*     */     
/*  93 */     return 0;
/*     */   }
/*     */   
/*     */   private int getExperienceFromItem(ItemStack $$0) {
/*  97 */     int $$1 = 0;
/*  98 */     Map<Enchantment, Integer> $$2 = EnchantmentHelper.getEnchantments($$0);
/*  99 */     for (Map.Entry<Enchantment, Integer> $$3 : $$2.entrySet()) {
/* 100 */       Enchantment $$4 = $$3.getKey();
/* 101 */       Integer $$5 = $$3.getValue();
/*     */       
/* 103 */       if (!$$4.isCurse()) {
/* 104 */         $$1 += $$4.getMinCost($$5.intValue());
/*     */       }
/*     */     } 
/*     */     
/* 108 */     return $$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\GrindstoneMenu$4.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */