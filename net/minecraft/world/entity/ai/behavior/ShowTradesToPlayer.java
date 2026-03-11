/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.Brain;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.npc.Villager;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.trading.MerchantOffer;
/*     */ 
/*     */ 
/*     */ public class ShowTradesToPlayer
/*     */   extends Behavior<Villager>
/*     */ {
/*     */   private static final int MAX_LOOK_TIME = 900;
/*     */   private static final int STARTING_LOOK_TIME = 40;
/*     */   @Nullable
/*     */   private ItemStack playerItemStack;
/*  28 */   private final List<ItemStack> displayItems = Lists.newArrayList();
/*     */   private int cycleCounter;
/*     */   private int displayIndex;
/*     */   private int lookTime;
/*     */   
/*     */   public ShowTradesToPlayer(int $$0, int $$1) {
/*  34 */     super(
/*  35 */         (Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.INTERACTION_TARGET, MemoryStatus.VALUE_PRESENT), $$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkExtraStartConditions(ServerLevel $$0, Villager $$1) {
/*  45 */     Brain<?> $$2 = $$1.getBrain();
/*  46 */     if ($$2.getMemory(MemoryModuleType.INTERACTION_TARGET).isEmpty()) {
/*  47 */       return false;
/*     */     }
/*     */     
/*  50 */     LivingEntity $$3 = $$2.getMemory(MemoryModuleType.INTERACTION_TARGET).get();
/*  51 */     return ($$3.getType() == EntityType.PLAYER && $$1
/*  52 */       .isAlive() && $$3
/*  53 */       .isAlive() && 
/*  54 */       !$$1.isBaby() && $$1
/*  55 */       .distanceToSqr((Entity)$$3) <= 17.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canStillUse(ServerLevel $$0, Villager $$1, long $$2) {
/*  60 */     return (checkExtraStartConditions($$0, $$1) && this.lookTime > 0 && $$1
/*     */       
/*  62 */       .getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).isPresent());
/*     */   }
/*     */ 
/*     */   
/*     */   public void start(ServerLevel $$0, Villager $$1, long $$2) {
/*  67 */     super.start($$0, $$1, $$2);
/*  68 */     lookAtTarget($$1);
/*     */     
/*  70 */     this.cycleCounter = 0;
/*  71 */     this.displayIndex = 0;
/*  72 */     this.lookTime = 40;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(ServerLevel $$0, Villager $$1, long $$2) {
/*  77 */     LivingEntity $$3 = lookAtTarget($$1);
/*     */     
/*  79 */     findItemsToDisplay($$3, $$1);
/*  80 */     if (!this.displayItems.isEmpty()) {
/*  81 */       displayCyclingItems($$1);
/*     */     } else {
/*  83 */       clearHeldItem($$1);
/*  84 */       this.lookTime = Math.min(this.lookTime, 40);
/*     */     } 
/*     */     
/*  87 */     this.lookTime--;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop(ServerLevel $$0, Villager $$1, long $$2) {
/*  92 */     super.stop($$0, $$1, $$2);
/*  93 */     $$1.getBrain().eraseMemory(MemoryModuleType.INTERACTION_TARGET);
/*     */     
/*  95 */     clearHeldItem($$1);
/*  96 */     this.playerItemStack = null;
/*     */   }
/*     */   
/*     */   private void findItemsToDisplay(LivingEntity $$0, Villager $$1) {
/* 100 */     boolean $$2 = false;
/* 101 */     ItemStack $$3 = $$0.getMainHandItem();
/* 102 */     if (this.playerItemStack == null || !ItemStack.isSameItem(this.playerItemStack, $$3)) {
/* 103 */       this.playerItemStack = $$3;
/* 104 */       $$2 = true;
/* 105 */       this.displayItems.clear();
/*     */     } 
/*     */     
/* 108 */     if ($$2 && !this.playerItemStack.isEmpty()) {
/* 109 */       updateDisplayItems($$1);
/* 110 */       if (!this.displayItems.isEmpty()) {
/* 111 */         this.lookTime = 900;
/* 112 */         displayFirstItem($$1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void displayFirstItem(Villager $$0) {
/* 118 */     displayAsHeldItem($$0, this.displayItems.get(0));
/*     */   }
/*     */   
/*     */   private void updateDisplayItems(Villager $$0) {
/* 122 */     for (MerchantOffer $$1 : $$0.getOffers()) {
/* 123 */       if (!$$1.isOutOfStock() && playerItemStackMatchesCostOfOffer($$1)) {
/* 124 */         this.displayItems.add($$1.assemble());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean playerItemStackMatchesCostOfOffer(MerchantOffer $$0) {
/* 130 */     return (ItemStack.isSameItem(this.playerItemStack, $$0.getCostA()) || ItemStack.isSameItem(this.playerItemStack, $$0.getCostB()));
/*     */   }
/*     */   
/*     */   private static void clearHeldItem(Villager $$0) {
/* 134 */     $$0.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
/* 135 */     $$0.setDropChance(EquipmentSlot.MAINHAND, 0.085F);
/*     */   }
/*     */   
/*     */   private static void displayAsHeldItem(Villager $$0, ItemStack $$1) {
/* 139 */     $$0.setItemSlot(EquipmentSlot.MAINHAND, $$1);
/* 140 */     $$0.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
/*     */   }
/*     */   
/*     */   private LivingEntity lookAtTarget(Villager $$0) {
/* 144 */     Brain<?> $$1 = $$0.getBrain();
/*     */     
/* 146 */     LivingEntity $$2 = $$1.getMemory(MemoryModuleType.INTERACTION_TARGET).get();
/*     */     
/* 148 */     $$1.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker((Entity)$$2, true));
/* 149 */     return $$2;
/*     */   }
/*     */   
/*     */   private void displayCyclingItems(Villager $$0) {
/* 153 */     if (this.displayItems.size() >= 2 && ++this.cycleCounter >= 40) {
/* 154 */       this.displayIndex++;
/* 155 */       this.cycleCounter = 0;
/* 156 */       if (this.displayIndex > this.displayItems.size() - 1) {
/* 157 */         this.displayIndex = 0;
/*     */       }
/* 159 */       displayAsHeldItem($$0, this.displayItems.get(this.displayIndex));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\ShowTradesToPlayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */