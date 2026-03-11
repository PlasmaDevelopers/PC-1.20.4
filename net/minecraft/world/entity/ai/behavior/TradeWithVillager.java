/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.npc.Villager;
/*     */ import net.minecraft.world.entity.npc.VillagerProfession;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ 
/*     */ public class TradeWithVillager
/*     */   extends Behavior<Villager> {
/*     */   private static final int INTERACT_DIST_SQR = 5;
/*     */   private static final float SPEED_MODIFIER = 0.5F;
/*  26 */   private Set<Item> trades = (Set<Item>)ImmutableSet.of();
/*     */   
/*     */   public TradeWithVillager() {
/*  29 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.INTERACTION_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryStatus.VALUE_PRESENT));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, Villager $$1) {
/*  37 */     return BehaviorUtils.targetIsValid($$1.getBrain(), MemoryModuleType.INTERACTION_TARGET, EntityType.VILLAGER);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, Villager $$1, long $$2) {
/*  42 */     return checkExtraStartConditions($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, Villager $$1, long $$2) {
/*  47 */     Villager $$3 = $$1.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
/*  48 */     BehaviorUtils.lockGazeAndWalkToEachOther((LivingEntity)$$1, (LivingEntity)$$3, 0.5F);
/*     */     
/*  50 */     this.trades = figureOutWhatIAmWillingToTrade($$1, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, Villager $$1, long $$2) {
/*  55 */     Villager $$3 = $$1.getBrain().getMemory(MemoryModuleType.INTERACTION_TARGET).get();
/*     */     
/*  57 */     if ($$1.distanceToSqr((Entity)$$3) > 5.0D) {
/*     */       return;
/*     */     }
/*     */     
/*  61 */     BehaviorUtils.lockGazeAndWalkToEachOther((LivingEntity)$$1, (LivingEntity)$$3, 0.5F);
/*     */     
/*  63 */     $$1.gossip($$0, $$3, $$2);
/*     */     
/*  65 */     if ($$1.hasExcessFood() && ($$1.getVillagerData().getProfession() == VillagerProfession.FARMER || $$3.wantsMoreFood())) {
/*  66 */       throwHalfStack($$1, Villager.FOOD_POINTS.keySet(), (LivingEntity)$$3);
/*     */     }
/*     */     
/*  69 */     if ($$3.getVillagerData().getProfession() == VillagerProfession.FARMER && $$1.getInventory().countItem(Items.WHEAT) > Items.WHEAT.getMaxStackSize() / 2) {
/*  70 */       throwHalfStack($$1, (Set<Item>)ImmutableSet.of(Items.WHEAT), (LivingEntity)$$3);
/*     */     }
/*     */     
/*  73 */     if (!this.trades.isEmpty() && $$1.getInventory().hasAnyOf(this.trades)) {
/*  74 */       throwHalfStack($$1, this.trades, (LivingEntity)$$3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, Villager $$1, long $$2) {
/*  80 */     $$1.getBrain().eraseMemory(MemoryModuleType.INTERACTION_TARGET);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Set<Item> figureOutWhatIAmWillingToTrade(Villager $$0, Villager $$1) {
/*  86 */     ImmutableSet<Item> $$2 = $$1.getVillagerData().getProfession().requestedItems();
/*  87 */     ImmutableSet<Item> $$3 = $$0.getVillagerData().getProfession().requestedItems();
/*  88 */     return (Set<Item>)$$2.stream().filter($$1 -> !$$0.contains($$1)).collect(Collectors.toSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void throwHalfStack(Villager $$0, Set<Item> $$1, LivingEntity $$2) {
/*  95 */     SimpleContainer $$3 = $$0.getInventory();
/*     */     
/*  97 */     ItemStack $$4 = ItemStack.EMPTY;
/*  98 */     for (int $$5 = 0; $$5 < $$3.getContainerSize(); $$5++) {
/*  99 */       ItemStack $$6 = $$3.getItem($$5);
/* 100 */       if (!$$6.isEmpty()) {
/* 101 */         Item $$7 = $$6.getItem();
/* 102 */         if ($$1.contains($$7)) {
/*     */           int $$9;
/* 104 */           if ($$6.getCount() > $$6.getMaxStackSize() / 2) {
/* 105 */             int $$8 = $$6.getCount() / 2;
/* 106 */           } else if ($$6.getCount() > 24) {
/* 107 */             $$9 = $$6.getCount() - 24;
/*     */           } else {
/*     */             continue;
/*     */           } 
/* 111 */           $$6.shrink($$9);
/* 112 */           $$4 = new ItemStack((ItemLike)$$7, $$9);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       continue;
/*     */     } 
/* 118 */     if (!$$4.isEmpty())
/* 119 */       BehaviorUtils.throwItem((LivingEntity)$$0, $$4, $$2.position()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\TradeWithVillager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */