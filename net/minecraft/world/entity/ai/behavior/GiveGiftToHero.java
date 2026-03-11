/*     */ package net.minecraft.world.entity.ai.behavior;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.npc.Villager;
/*     */ import net.minecraft.world.entity.npc.VillagerProfession;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ 
/*     */ public class GiveGiftToHero
/*     */   extends Behavior<Villager> {
/*     */   private static final int THROW_GIFT_AT_DISTANCE = 5;
/*     */   private static final int MIN_TIME_BETWEEN_GIFTS = 600;
/*     */   private static final int MAX_TIME_BETWEEN_GIFTS = 6600;
/*     */   
/*     */   static {
/*  39 */     GIFTS = (Map<VillagerProfession, ResourceLocation>)Util.make(Maps.newHashMap(), $$0 -> {
/*     */           $$0.put(VillagerProfession.ARMORER, BuiltInLootTables.ARMORER_GIFT);
/*     */           $$0.put(VillagerProfession.BUTCHER, BuiltInLootTables.BUTCHER_GIFT);
/*     */           $$0.put(VillagerProfession.CARTOGRAPHER, BuiltInLootTables.CARTOGRAPHER_GIFT);
/*     */           $$0.put(VillagerProfession.CLERIC, BuiltInLootTables.CLERIC_GIFT);
/*     */           $$0.put(VillagerProfession.FARMER, BuiltInLootTables.FARMER_GIFT);
/*     */           $$0.put(VillagerProfession.FISHERMAN, BuiltInLootTables.FISHERMAN_GIFT);
/*     */           $$0.put(VillagerProfession.FLETCHER, BuiltInLootTables.FLETCHER_GIFT);
/*     */           $$0.put(VillagerProfession.LEATHERWORKER, BuiltInLootTables.LEATHERWORKER_GIFT);
/*     */           $$0.put(VillagerProfession.LIBRARIAN, BuiltInLootTables.LIBRARIAN_GIFT);
/*     */           $$0.put(VillagerProfession.MASON, BuiltInLootTables.MASON_GIFT);
/*     */           $$0.put(VillagerProfession.SHEPHERD, BuiltInLootTables.SHEPHERD_GIFT);
/*     */           $$0.put(VillagerProfession.TOOLSMITH, BuiltInLootTables.TOOLSMITH_GIFT);
/*     */           $$0.put(VillagerProfession.WEAPONSMITH, BuiltInLootTables.WEAPONSMITH_GIFT);
/*     */         });
/*     */   }
/*     */   private static final int TIME_TO_DELAY_FOR_HEAD_TO_FINISH_TURNING = 20; private static final Map<VillagerProfession, ResourceLocation> GIFTS; private static final float SPEED_MODIFIER = 0.5F;
/*  56 */   private int timeUntilNextGift = 600;
/*     */   private boolean giftGivenDuringThisRun;
/*     */   private long timeSinceStart;
/*     */   
/*     */   public GiveGiftToHero(int $$0) {
/*  61 */     super(
/*  62 */         (Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.INTERACTION_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryStatus.VALUE_PRESENT), $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, Villager $$1) {
/*  74 */     if (!isHeroVisible($$1)) {
/*  75 */       return false;
/*     */     }
/*     */     
/*  78 */     if (this.timeUntilNextGift > 0) {
/*  79 */       this.timeUntilNextGift--;
/*  80 */       return false;
/*     */     } 
/*     */     
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, Villager $$1, long $$2) {
/*  88 */     this.giftGivenDuringThisRun = false;
/*  89 */     this.timeSinceStart = $$2;
/*  90 */     Player $$3 = getNearestTargetableHero($$1).get();
/*  91 */     $$1.getBrain().setMemory(MemoryModuleType.INTERACTION_TARGET, $$3);
/*  92 */     BehaviorUtils.lookAtEntity((LivingEntity)$$1, (LivingEntity)$$3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, Villager $$1, long $$2) {
/*  97 */     return (isHeroVisible($$1) && !this.giftGivenDuringThisRun);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, Villager $$1, long $$2) {
/* 102 */     Player $$3 = getNearestTargetableHero($$1).get();
/* 103 */     BehaviorUtils.lookAtEntity((LivingEntity)$$1, (LivingEntity)$$3);
/*     */     
/* 105 */     if (isWithinThrowingDistance($$1, $$3)) {
/* 106 */       if ($$2 - this.timeSinceStart > 20L) {
/* 107 */         throwGift($$1, (LivingEntity)$$3);
/* 108 */         this.giftGivenDuringThisRun = true;
/*     */       } 
/*     */     } else {
/* 111 */       BehaviorUtils.setWalkAndLookTargetMemories((LivingEntity)$$1, (Entity)$$3, 0.5F, 5);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, Villager $$1, long $$2) {
/* 117 */     this.timeUntilNextGift = calculateTimeUntilNextGift($$0);
/* 118 */     $$1.getBrain().eraseMemory(MemoryModuleType.INTERACTION_TARGET);
/* 119 */     $$1.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
/* 120 */     $$1.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
/*     */   }
/*     */   
/*     */   private void throwGift(Villager $$0, LivingEntity $$1) {
/* 124 */     List<ItemStack> $$2 = getItemToThrow($$0);
/* 125 */     for (ItemStack $$3 : $$2) {
/* 126 */       BehaviorUtils.throwItem((LivingEntity)$$0, $$3, $$1.position());
/*     */     }
/*     */   }
/*     */   
/*     */   private List<ItemStack> getItemToThrow(Villager $$0) {
/* 131 */     if ($$0.isBaby()) {
/* 132 */       return (List<ItemStack>)ImmutableList.of(new ItemStack((ItemLike)Items.POPPY));
/*     */     }
/*     */     
/* 135 */     VillagerProfession $$1 = $$0.getVillagerData().getProfession();
/* 136 */     if (GIFTS.containsKey($$1)) {
/* 137 */       LootTable $$2 = $$0.level().getServer().getLootData().getLootTable(GIFTS.get($$1));
/*     */ 
/*     */ 
/*     */       
/* 141 */       LootParams $$3 = (new LootParams.Builder((ServerLevel)$$0.level())).withParameter(LootContextParams.ORIGIN, $$0.position()).withParameter(LootContextParams.THIS_ENTITY, $$0).create(LootContextParamSets.GIFT);
/*     */       
/* 143 */       return (List<ItemStack>)$$2.getRandomItems($$3);
/*     */     } 
/*     */     
/* 146 */     return (List<ItemStack>)ImmutableList.of(new ItemStack((ItemLike)Items.WHEAT_SEEDS));
/*     */   }
/*     */   
/*     */   private boolean isHeroVisible(Villager $$0) {
/* 150 */     return getNearestTargetableHero($$0).isPresent();
/*     */   }
/*     */   
/*     */   private Optional<Player> getNearestTargetableHero(Villager $$0) {
/* 154 */     return $$0.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER)
/* 155 */       .filter(this::isHero);
/*     */   }
/*     */   
/*     */   private boolean isHero(Player $$0) {
/* 159 */     return $$0.hasEffect(MobEffects.HERO_OF_THE_VILLAGE);
/*     */   }
/*     */   
/*     */   private boolean isWithinThrowingDistance(Villager $$0, Player $$1) {
/* 163 */     BlockPos $$2 = $$1.blockPosition();
/* 164 */     BlockPos $$3 = $$0.blockPosition();
/* 165 */     return $$3.closerThan((Vec3i)$$2, 5.0D);
/*     */   }
/*     */   
/*     */   private static int calculateTimeUntilNextGift(ServerLevel $$0) {
/* 169 */     return 600 + $$0.random.nextInt(6001);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\GiveGiftToHero.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */