/*     */ package net.minecraft.world.entity.npc;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.ExperienceOrb;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.InteractGoal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtTradingPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
/*     */ import net.minecraft.world.entity.ai.goal.PanicGoal;
/*     */ import net.minecraft.world.entity.ai.goal.TradeWithPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.UseItemGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.entity.monster.Evoker;
/*     */ import net.minecraft.world.entity.monster.Illusioner;
/*     */ import net.minecraft.world.entity.monster.Pillager;
/*     */ import net.minecraft.world.entity.monster.Vex;
/*     */ import net.minecraft.world.entity.monster.Vindicator;
/*     */ import net.minecraft.world.entity.monster.Zoglin;
/*     */ import net.minecraft.world.entity.monster.Zombie;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.PotionUtils;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.item.trading.MerchantOffer;
/*     */ import net.minecraft.world.item.trading.MerchantOffers;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ public class WanderingTrader extends AbstractVillager {
/*     */   private static final int NUMBER_OF_TRADE_OFFERS = 5;
/*     */   
/*     */   public WanderingTrader(EntityType<? extends WanderingTrader> $$0, Level $$1) {
/*  59 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */   @Nullable
/*     */   private BlockPos wanderTarget; private int despawnDelay;
/*     */   protected void registerGoals() {
/*  64 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/*  65 */     this.goalSelector.addGoal(0, (Goal)new UseItemGoal((Mob)this, PotionUtils.setPotion(new ItemStack((ItemLike)Items.POTION), Potions.INVISIBILITY), SoundEvents.WANDERING_TRADER_DISAPPEARED, $$0 -> (level().isNight() && !$$0.isInvisible())));
/*  66 */     this.goalSelector.addGoal(0, (Goal)new UseItemGoal((Mob)this, new ItemStack((ItemLike)Items.MILK_BUCKET), SoundEvents.WANDERING_TRADER_REAPPEARED, $$0 -> (level().isDay() && $$0.isInvisible())));
/*  67 */     this.goalSelector.addGoal(1, (Goal)new TradeWithPlayerGoal(this));
/*  68 */     this.goalSelector.addGoal(1, (Goal)new AvoidEntityGoal((PathfinderMob)this, Zombie.class, 8.0F, 0.5D, 0.5D));
/*  69 */     this.goalSelector.addGoal(1, (Goal)new AvoidEntityGoal((PathfinderMob)this, Evoker.class, 12.0F, 0.5D, 0.5D));
/*  70 */     this.goalSelector.addGoal(1, (Goal)new AvoidEntityGoal((PathfinderMob)this, Vindicator.class, 8.0F, 0.5D, 0.5D));
/*  71 */     this.goalSelector.addGoal(1, (Goal)new AvoidEntityGoal((PathfinderMob)this, Vex.class, 8.0F, 0.5D, 0.5D));
/*  72 */     this.goalSelector.addGoal(1, (Goal)new AvoidEntityGoal((PathfinderMob)this, Pillager.class, 15.0F, 0.5D, 0.5D));
/*  73 */     this.goalSelector.addGoal(1, (Goal)new AvoidEntityGoal((PathfinderMob)this, Illusioner.class, 12.0F, 0.5D, 0.5D));
/*  74 */     this.goalSelector.addGoal(1, (Goal)new AvoidEntityGoal((PathfinderMob)this, Zoglin.class, 10.0F, 0.5D, 0.5D));
/*  75 */     this.goalSelector.addGoal(1, (Goal)new PanicGoal((PathfinderMob)this, 0.5D));
/*  76 */     this.goalSelector.addGoal(1, (Goal)new LookAtTradingPlayerGoal(this));
/*  77 */     this.goalSelector.addGoal(2, new WanderToPositionGoal(this, 2.0D, 0.35D));
/*  78 */     this.goalSelector.addGoal(4, (Goal)new MoveTowardsRestrictionGoal((PathfinderMob)this, 0.35D));
/*  79 */     this.goalSelector.addGoal(8, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 0.35D));
/*  80 */     this.goalSelector.addGoal(9, (Goal)new InteractGoal((Mob)this, Player.class, 3.0F, 1.0F));
/*  81 */     this.goalSelector.addGoal(10, (Goal)new LookAtPlayerGoal((Mob)this, Mob.class, 8.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AgeableMob getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean showProgressBar() {
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/*  98 */     ItemStack $$2 = $$0.getItemInHand($$1);
/*  99 */     if (!$$2.is(Items.VILLAGER_SPAWN_EGG) && isAlive() && !isTrading() && !isBaby()) {
/*     */       
/* 101 */       if ($$1 == InteractionHand.MAIN_HAND) {
/* 102 */         $$0.awardStat(Stats.TALKED_TO_VILLAGER);
/*     */       }
/*     */       
/* 105 */       if (getOffers().isEmpty()) {
/* 106 */         return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */       }
/*     */       
/* 109 */       if (!(level()).isClientSide) {
/*     */         
/* 111 */         setTradingPlayer($$0);
/* 112 */         openTradingScreen($$0, getDisplayName(), 1);
/*     */       } 
/* 114 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/* 116 */     return super.mobInteract($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateTrades() {
/* 121 */     if (level().enabledFeatures().contains(FeatureFlags.TRADE_REBALANCE)) {
/* 122 */       experimentalUpdateTrades();
/*     */       
/*     */       return;
/*     */     } 
/* 126 */     VillagerTrades.ItemListing[] $$0 = (VillagerTrades.ItemListing[])VillagerTrades.WANDERING_TRADER_TRADES.get(1);
/* 127 */     VillagerTrades.ItemListing[] $$1 = (VillagerTrades.ItemListing[])VillagerTrades.WANDERING_TRADER_TRADES.get(2);
/*     */     
/* 129 */     if ($$0 == null || $$1 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 133 */     MerchantOffers $$2 = getOffers();
/* 134 */     addOffersFromItemListings($$2, $$0, 5);
/*     */     
/* 136 */     int $$3 = this.random.nextInt($$1.length);
/* 137 */     VillagerTrades.ItemListing $$4 = $$1[$$3];
/* 138 */     MerchantOffer $$5 = $$4.getOffer((Entity)this, this.random);
/* 139 */     if ($$5 != null) {
/* 140 */       $$2.add($$5);
/*     */     }
/*     */   }
/*     */   
/*     */   private void experimentalUpdateTrades() {
/* 145 */     MerchantOffers $$0 = getOffers();
/* 146 */     for (Pair<VillagerTrades.ItemListing[], Integer> $$1 : VillagerTrades.EXPERIMENTAL_WANDERING_TRADER_TRADES) {
/* 147 */       VillagerTrades.ItemListing[] $$2 = (VillagerTrades.ItemListing[])$$1.getLeft();
/* 148 */       addOffersFromItemListings($$0, $$2, ((Integer)$$1.getRight()).intValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 154 */     super.addAdditionalSaveData($$0);
/* 155 */     $$0.putInt("DespawnDelay", this.despawnDelay);
/*     */     
/* 157 */     if (this.wanderTarget != null) {
/* 158 */       $$0.put("WanderTarget", (Tag)NbtUtils.writeBlockPos(this.wanderTarget));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 164 */     super.readAdditionalSaveData($$0);
/* 165 */     if ($$0.contains("DespawnDelay", 99)) {
/* 166 */       this.despawnDelay = $$0.getInt("DespawnDelay");
/*     */     }
/* 168 */     if ($$0.contains("WanderTarget")) {
/* 169 */       this.wanderTarget = NbtUtils.readBlockPos($$0.getCompound("WanderTarget"));
/*     */     }
/*     */     
/* 172 */     setAge(Math.max(0, getAge()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeWhenFarAway(double $$0) {
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void rewardTradeXp(MerchantOffer $$0) {
/* 182 */     if ($$0.shouldRewardExp()) {
/* 183 */       int $$1 = 3 + this.random.nextInt(4);
/* 184 */       level().addFreshEntity((Entity)new ExperienceOrb(level(), getX(), getY() + 0.5D, getZ(), $$1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 190 */     if (isTrading()) {
/* 191 */       return SoundEvents.WANDERING_TRADER_TRADE;
/*     */     }
/* 193 */     return SoundEvents.WANDERING_TRADER_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 198 */     return SoundEvents.WANDERING_TRADER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 203 */     return SoundEvents.WANDERING_TRADER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDrinkingSound(ItemStack $$0) {
/* 208 */     if ($$0.is(Items.MILK_BUCKET)) {
/* 209 */       return SoundEvents.WANDERING_TRADER_DRINK_MILK;
/*     */     }
/* 211 */     return SoundEvents.WANDERING_TRADER_DRINK_POTION;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getTradeUpdatedSound(boolean $$0) {
/* 217 */     return $$0 ? SoundEvents.WANDERING_TRADER_YES : SoundEvents.WANDERING_TRADER_NO;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getNotifyTradeSound() {
/* 222 */     return SoundEvents.WANDERING_TRADER_YES;
/*     */   }
/*     */   
/*     */   public void setDespawnDelay(int $$0) {
/* 226 */     this.despawnDelay = $$0;
/*     */   }
/*     */   
/*     */   public int getDespawnDelay() {
/* 230 */     return this.despawnDelay;
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 235 */     super.aiStep();
/*     */     
/* 237 */     if (!(level()).isClientSide) {
/* 238 */       maybeDespawn();
/*     */     }
/*     */   }
/*     */   
/*     */   private void maybeDespawn() {
/* 243 */     if (this.despawnDelay > 0 && !isTrading() && --this.despawnDelay == 0) {
/* 244 */       discard();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setWanderTarget(@Nullable BlockPos $$0) {
/* 249 */     this.wanderTarget = $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   BlockPos getWanderTarget() {
/* 254 */     return this.wanderTarget;
/*     */   }
/*     */   
/*     */   private class WanderToPositionGoal extends Goal {
/*     */     final WanderingTrader trader;
/*     */     final double stopDistance;
/*     */     final double speedModifier;
/*     */     
/*     */     WanderToPositionGoal(WanderingTrader $$0, double $$1, double $$2) {
/* 263 */       this.trader = $$0;
/* 264 */       this.stopDistance = $$1;
/* 265 */       this.speedModifier = $$2;
/* 266 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 271 */       this.trader.setWanderTarget((BlockPos)null);
/* 272 */       WanderingTrader.this.navigation.stop();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 277 */       BlockPos $$0 = this.trader.getWanderTarget();
/* 278 */       return ($$0 != null && isTooFarAway($$0, this.stopDistance));
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 283 */       BlockPos $$0 = this.trader.getWanderTarget();
/* 284 */       if ($$0 != null && WanderingTrader.this.navigation.isDone()) {
/* 285 */         if (isTooFarAway($$0, 10.0D)) {
/*     */           
/* 287 */           Vec3 $$1 = (new Vec3($$0.getX() - this.trader.getX(), $$0.getY() - this.trader.getY(), $$0.getZ() - this.trader.getZ())).normalize();
/* 288 */           Vec3 $$2 = $$1.scale(10.0D).add(this.trader.getX(), this.trader.getY(), this.trader.getZ());
/* 289 */           WanderingTrader.this.navigation.moveTo($$2.x, $$2.y, $$2.z, this.speedModifier);
/*     */         } else {
/* 291 */           WanderingTrader.this.navigation.moveTo($$0.getX(), $$0.getY(), $$0.getZ(), this.speedModifier);
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     private boolean isTooFarAway(BlockPos $$0, double $$1) {
/* 297 */       return !$$0.closerToCenterThan((Position)this.trader.position(), $$1);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\npc\WanderingTrader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */