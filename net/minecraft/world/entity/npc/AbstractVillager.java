/*     */ package net.minecraft.world.entity.npc;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SlotAccess;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.trading.Merchant;
/*     */ import net.minecraft.world.item.trading.MerchantOffer;
/*     */ import net.minecraft.world.item.trading.MerchantOffers;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.pathfinder.BlockPathTypes;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public abstract class AbstractVillager
/*     */   extends AgeableMob implements InventoryCarrier, Npc, Merchant {
/*  42 */   private static final EntityDataAccessor<Integer> DATA_UNHAPPY_COUNTER = SynchedEntityData.defineId(AbstractVillager.class, EntityDataSerializers.INT);
/*     */   
/*     */   public static final int VILLAGER_SLOT_OFFSET = 300;
/*     */   
/*     */   private static final int VILLAGER_INVENTORY_SIZE = 8;
/*     */   
/*     */   @Nullable
/*     */   private Player tradingPlayer;
/*     */   @Nullable
/*     */   protected MerchantOffers offers;
/*  52 */   private final SimpleContainer inventory = new SimpleContainer(8);
/*     */   
/*     */   public AbstractVillager(EntityType<? extends AbstractVillager> $$0, Level $$1) {
/*  55 */     super($$0, $$1);
/*  56 */     setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
/*  57 */     setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
/*     */   }
/*     */   
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*     */     AgeableMob.AgeableMobGroupData ageableMobGroupData;
/*  62 */     if ($$3 == null) {
/*  63 */       ageableMobGroupData = new AgeableMob.AgeableMobGroupData(false);
/*     */     }
/*     */     
/*  66 */     return super.finalizeSpawn($$0, $$1, $$2, (SpawnGroupData)ageableMobGroupData, $$4);
/*     */   }
/*     */   
/*     */   public int getUnhappyCounter() {
/*  70 */     return ((Integer)this.entityData.get(DATA_UNHAPPY_COUNTER)).intValue();
/*     */   }
/*     */   
/*     */   public void setUnhappyCounter(int $$0) {
/*  74 */     this.entityData.set(DATA_UNHAPPY_COUNTER, Integer.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getVillagerXp() {
/*  79 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/*  84 */     if (isBaby()) {
/*  85 */       return 0.81F;
/*     */     }
/*  87 */     return 1.62F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  92 */     super.defineSynchedData();
/*  93 */     this.entityData.define(DATA_UNHAPPY_COUNTER, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTradingPlayer(@Nullable Player $$0) {
/*  98 */     this.tradingPlayer = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Player getTradingPlayer() {
/* 104 */     return this.tradingPlayer;
/*     */   }
/*     */   
/*     */   public boolean isTrading() {
/* 108 */     return (this.tradingPlayer != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public MerchantOffers getOffers() {
/* 113 */     if (this.offers == null) {
/* 114 */       this.offers = new MerchantOffers();
/* 115 */       updateTrades();
/*     */     } 
/* 117 */     return this.offers;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void overrideOffers(@Nullable MerchantOffers $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void overrideXp(int $$0) {}
/*     */ 
/*     */   
/*     */   public void notifyTrade(MerchantOffer $$0) {
/* 130 */     $$0.increaseUses();
/* 131 */     this.ambientSoundTime = -getAmbientSoundInterval();
/*     */     
/* 133 */     rewardTradeXp($$0);
/*     */     
/* 135 */     if (this.tradingPlayer instanceof ServerPlayer) {
/* 136 */       CriteriaTriggers.TRADE.trigger((ServerPlayer)this.tradingPlayer, this, $$0.getResult());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void rewardTradeXp(MerchantOffer paramMerchantOffer);
/*     */   
/*     */   public boolean showProgressBar() {
/* 144 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyTradeUpdated(ItemStack $$0) {
/* 149 */     if (!(level()).isClientSide && this.ambientSoundTime > -getAmbientSoundInterval() + 20) {
/* 150 */       this.ambientSoundTime = -getAmbientSoundInterval();
/* 151 */       playSound(getTradeUpdatedSound(!$$0.isEmpty()), getSoundVolume(), getVoicePitch());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getNotifyTradeSound() {
/* 157 */     return SoundEvents.VILLAGER_YES;
/*     */   }
/*     */   
/*     */   protected SoundEvent getTradeUpdatedSound(boolean $$0) {
/* 161 */     return $$0 ? SoundEvents.VILLAGER_YES : SoundEvents.VILLAGER_NO;
/*     */   }
/*     */   
/*     */   public void playCelebrateSound() {
/* 165 */     playSound(SoundEvents.VILLAGER_CELEBRATE, getSoundVolume(), getVoicePitch());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 170 */     super.addAdditionalSaveData($$0);
/*     */     
/* 172 */     MerchantOffers $$1 = getOffers();
/* 173 */     if (!$$1.isEmpty()) {
/* 174 */       $$0.put("Offers", (Tag)$$1.createTag());
/*     */     }
/* 176 */     writeInventoryToTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 181 */     super.readAdditionalSaveData($$0);
/*     */ 
/*     */     
/* 184 */     if ($$0.contains("Offers", 10)) {
/* 185 */       this.offers = new MerchantOffers($$0.getCompound("Offers"));
/*     */     }
/* 187 */     readInventoryFromTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity changeDimension(ServerLevel $$0) {
/* 193 */     stopTrading();
/* 194 */     return super.changeDimension($$0);
/*     */   }
/*     */   
/*     */   protected void stopTrading() {
/* 198 */     setTradingPlayer((Player)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void die(DamageSource $$0) {
/* 203 */     super.die($$0);
/* 204 */     stopTrading();
/*     */   }
/*     */   
/*     */   protected void addParticlesAroundSelf(ParticleOptions $$0) {
/* 208 */     for (int $$1 = 0; $$1 < 5; $$1++) {
/* 209 */       double $$2 = this.random.nextGaussian() * 0.02D;
/* 210 */       double $$3 = this.random.nextGaussian() * 0.02D;
/* 211 */       double $$4 = this.random.nextGaussian() * 0.02D;
/* 212 */       level().addParticle($$0, getRandomX(1.0D), getRandomY() + 1.0D, getRandomZ(1.0D), $$2, $$3, $$4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeLeashed(Player $$0) {
/* 218 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public SimpleContainer getInventory() {
/* 223 */     return this.inventory;
/*     */   }
/*     */ 
/*     */   
/*     */   public SlotAccess getSlot(int $$0) {
/* 228 */     int $$1 = $$0 - 300;
/* 229 */     if ($$1 >= 0 && $$1 < this.inventory.getContainerSize()) {
/* 230 */       return SlotAccess.forContainer((Container)this.inventory, $$1);
/*     */     }
/* 232 */     return super.getSlot($$0);
/*     */   }
/*     */   
/*     */   protected abstract void updateTrades();
/*     */   
/*     */   protected void addOffersFromItemListings(MerchantOffers $$0, VillagerTrades.ItemListing[] $$1, int $$2) {
/* 238 */     ArrayList<VillagerTrades.ItemListing> $$3 = Lists.newArrayList((Object[])$$1);
/* 239 */     int $$4 = 0;
/* 240 */     while ($$4 < $$2 && !$$3.isEmpty()) {
/* 241 */       MerchantOffer $$5 = ((VillagerTrades.ItemListing)$$3.remove(this.random.nextInt($$3.size()))).getOffer((Entity)this, this.random);
/* 242 */       if ($$5 != null) {
/* 243 */         $$0.add($$5);
/* 244 */         $$4++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getRopeHoldPosition(float $$0) {
/* 251 */     float $$1 = Mth.lerp($$0, this.yBodyRotO, this.yBodyRot) * 0.017453292F;
/* 252 */     Vec3 $$2 = new Vec3(0.0D, getBoundingBox().getYsize() - 1.0D, 0.2D);
/* 253 */     return getPosition($$0).add($$2.yRot(-$$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClientSide() {
/* 258 */     return (level()).isClientSide;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\npc\AbstractVillager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */