/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.ReputationEventHandler;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.village.ReputationEventType;
/*     */ import net.minecraft.world.entity.npc.Villager;
/*     */ import net.minecraft.world.entity.npc.VillagerData;
/*     */ import net.minecraft.world.entity.npc.VillagerDataHolder;
/*     */ import net.minecraft.world.entity.npc.VillagerProfession;
/*     */ import net.minecraft.world.entity.npc.VillagerType;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.item.trading.MerchantOffers;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import org.joml.Vector3f;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ZombieVillager
/*     */   extends Zombie implements VillagerDataHolder {
/*  57 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  59 */   private static final EntityDataAccessor<Boolean> DATA_CONVERTING_ID = SynchedEntityData.defineId(ZombieVillager.class, EntityDataSerializers.BOOLEAN);
/*  60 */   private static final EntityDataAccessor<VillagerData> DATA_VILLAGER_DATA = SynchedEntityData.defineId(ZombieVillager.class, EntityDataSerializers.VILLAGER_DATA);
/*     */   
/*     */   private static final int VILLAGER_CONVERSION_WAIT_MIN = 3600;
/*     */   
/*     */   private static final int VILLAGER_CONVERSION_WAIT_MAX = 6000;
/*     */   
/*     */   private static final int MAX_SPECIAL_BLOCKS_COUNT = 14;
/*     */   private static final int SPECIAL_BLOCK_RADIUS = 4;
/*     */   private int villagerConversionTime;
/*     */   @Nullable
/*     */   private UUID conversionStarter;
/*     */   @Nullable
/*     */   private Tag gossips;
/*     */   @Nullable
/*     */   private CompoundTag tradeOffers;
/*     */   private int villagerXp;
/*     */   
/*     */   public ZombieVillager(EntityType<? extends ZombieVillager> $$0, Level $$1) {
/*  78 */     super((EntityType)$$0, $$1);
/*     */     
/*  80 */     BuiltInRegistries.VILLAGER_PROFESSION.getRandom(this.random).ifPresent($$0 -> setVillagerData(getVillagerData().setProfession((VillagerProfession)$$0.value())));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  85 */     super.defineSynchedData();
/*     */     
/*  87 */     this.entityData.define(DATA_CONVERTING_ID, Boolean.valueOf(false));
/*  88 */     this.entityData.define(DATA_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  93 */     super.addAdditionalSaveData($$0);
/*     */ 
/*     */     
/*  96 */     Objects.requireNonNull(LOGGER); VillagerData.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, getVillagerData()).resultOrPartial(LOGGER::error)
/*  97 */       .ifPresent($$1 -> $$0.put("VillagerData", $$1));
/*     */     
/*  99 */     if (this.tradeOffers != null) {
/* 100 */       $$0.put("Offers", (Tag)this.tradeOffers);
/*     */     }
/*     */     
/* 103 */     if (this.gossips != null) {
/* 104 */       $$0.put("Gossips", this.gossips);
/*     */     }
/*     */     
/* 107 */     $$0.putInt("ConversionTime", isConverting() ? this.villagerConversionTime : -1);
/*     */     
/* 109 */     if (this.conversionStarter != null) {
/* 110 */       $$0.putUUID("ConversionPlayer", this.conversionStarter);
/*     */     }
/*     */     
/* 113 */     $$0.putInt("Xp", this.villagerXp);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 118 */     super.readAdditionalSaveData($$0);
/*     */     
/* 120 */     if ($$0.contains("VillagerData", 10)) {
/* 121 */       DataResult<VillagerData> $$1 = VillagerData.CODEC.parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$0.get("VillagerData")));
/* 122 */       Objects.requireNonNull(LOGGER); $$1.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
/*     */     } 
/*     */     
/* 125 */     if ($$0.contains("Offers", 10)) {
/* 126 */       this.tradeOffers = $$0.getCompound("Offers");
/*     */     }
/*     */     
/* 129 */     if ($$0.contains("Gossips", 9)) {
/* 130 */       this.gossips = (Tag)$$0.getList("Gossips", 10);
/*     */     }
/*     */     
/* 133 */     if ($$0.contains("ConversionTime", 99) && $$0.getInt("ConversionTime") > -1) {
/* 134 */       startConverting($$0.hasUUID("ConversionPlayer") ? $$0.getUUID("ConversionPlayer") : null, $$0.getInt("ConversionTime"));
/*     */     }
/*     */     
/* 137 */     if ($$0.contains("Xp", 3)) {
/* 138 */       this.villagerXp = $$0.getInt("Xp");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 144 */     if (!(level()).isClientSide && isAlive() && isConverting()) {
/* 145 */       int $$0 = getConversionProgress();
/*     */       
/* 147 */       this.villagerConversionTime -= $$0;
/*     */       
/* 149 */       if (this.villagerConversionTime <= 0) {
/* 150 */         finishConversion((ServerLevel)level());
/*     */       }
/*     */     } 
/*     */     
/* 154 */     super.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 159 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 160 */     if ($$2.is(Items.GOLDEN_APPLE)) {
/* 161 */       if (hasEffect(MobEffects.WEAKNESS)) {
/* 162 */         if (!($$0.getAbilities()).instabuild) {
/* 163 */           $$2.shrink(1);
/*     */         }
/*     */         
/* 166 */         if (!(level()).isClientSide) {
/* 167 */           startConverting($$0.getUUID(), this.random.nextInt(2401) + 3600);
/*     */         }
/*     */ 
/*     */         
/* 171 */         return InteractionResult.SUCCESS;
/*     */       } 
/* 173 */       return InteractionResult.CONSUME;
/*     */     } 
/*     */     
/* 176 */     return super.mobInteract($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean convertsInWater() {
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeWhenFarAway(double $$0) {
/* 186 */     return (!isConverting() && this.villagerXp == 0);
/*     */   }
/*     */   
/*     */   public boolean isConverting() {
/* 190 */     return ((Boolean)getEntityData().get(DATA_CONVERTING_ID)).booleanValue();
/*     */   }
/*     */   
/*     */   private void startConverting(@Nullable UUID $$0, int $$1) {
/* 194 */     this.conversionStarter = $$0;
/* 195 */     this.villagerConversionTime = $$1;
/* 196 */     getEntityData().set(DATA_CONVERTING_ID, Boolean.valueOf(true));
/*     */     
/* 198 */     removeEffect(MobEffects.WEAKNESS);
/* 199 */     addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, $$1, Math.min(level().getDifficulty().getId() - 1, 0)));
/*     */     
/* 201 */     level().broadcastEntityEvent((Entity)this, (byte)16);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 206 */     if ($$0 == 16) {
/* 207 */       if (!isSilent()) {
/* 208 */         level().playLocalSound(getX(), getEyeY(), getZ(), SoundEvents.ZOMBIE_VILLAGER_CURE, getSoundSource(), 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F, false);
/*     */       }
/*     */       return;
/*     */     } 
/* 212 */     super.handleEntityEvent($$0);
/*     */   }
/*     */   
/*     */   private void finishConversion(ServerLevel $$0) {
/* 216 */     Villager $$1 = (Villager)convertTo(EntityType.VILLAGER, false);
/*     */     
/* 218 */     for (EquipmentSlot $$2 : EquipmentSlot.values()) {
/* 219 */       ItemStack $$3 = getItemBySlot($$2);
/* 220 */       if (!$$3.isEmpty())
/*     */       {
/* 222 */         if (EnchantmentHelper.hasBindingCurse($$3)) {
/* 223 */           $$1.getSlot($$2.getIndex() + 300).set($$3);
/*     */         }
/*     */         else {
/*     */           
/* 227 */           double $$4 = getEquipmentDropChance($$2);
/* 228 */           if ($$4 > 1.0D)
/* 229 */             spawnAtLocation($$3); 
/*     */         } 
/*     */       }
/*     */     } 
/* 233 */     $$1.setVillagerData(getVillagerData());
/* 234 */     if (this.gossips != null) {
/* 235 */       $$1.setGossips(this.gossips);
/*     */     }
/* 237 */     if (this.tradeOffers != null) {
/* 238 */       $$1.setOffers(new MerchantOffers(this.tradeOffers));
/*     */     }
/* 240 */     $$1.setVillagerXp(this.villagerXp);
/* 241 */     $$1.finalizeSpawn((ServerLevelAccessor)$$0, $$0.getCurrentDifficultyAt($$1.blockPosition()), MobSpawnType.CONVERSION, null, null);
/* 242 */     $$1.refreshBrain($$0);
/*     */     
/* 244 */     if (this.conversionStarter != null) {
/* 245 */       Player $$5 = $$0.getPlayerByUUID(this.conversionStarter);
/* 246 */       if ($$5 instanceof ServerPlayer) {
/* 247 */         CriteriaTriggers.CURED_ZOMBIE_VILLAGER.trigger((ServerPlayer)$$5, this, $$1);
/* 248 */         $$0.onReputationEvent(ReputationEventType.ZOMBIE_VILLAGER_CURED, (Entity)$$5, (ReputationEventHandler)$$1);
/*     */       } 
/*     */     } 
/*     */     
/* 252 */     $$1.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
/* 253 */     if (!isSilent()) {
/* 254 */       $$0.levelEvent(null, 1027, blockPosition(), 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private int getConversionProgress() {
/* 259 */     int $$0 = 1;
/*     */     
/* 261 */     if (this.random.nextFloat() < 0.01F) {
/* 262 */       int $$1 = 0;
/*     */       
/* 264 */       BlockPos.MutableBlockPos $$2 = new BlockPos.MutableBlockPos();
/*     */       
/* 266 */       for (int $$3 = (int)getX() - 4; $$3 < (int)getX() + 4 && $$1 < 14; $$3++) {
/* 267 */         for (int $$4 = (int)getY() - 4; $$4 < (int)getY() + 4 && $$1 < 14; $$4++) {
/* 268 */           for (int $$5 = (int)getZ() - 4; $$5 < (int)getZ() + 4 && $$1 < 14; $$5++) {
/* 269 */             BlockState $$6 = level().getBlockState((BlockPos)$$2.set($$3, $$4, $$5));
/* 270 */             if ($$6.is(Blocks.IRON_BARS) || $$6.getBlock() instanceof net.minecraft.world.level.block.BedBlock) {
/* 271 */               if (this.random.nextFloat() < 0.3F) {
/* 272 */                 $$0++;
/*     */               }
/* 274 */               $$1++;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 280 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getVoicePitch() {
/* 285 */     if (isBaby()) {
/* 286 */       return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 2.0F;
/*     */     }
/* 288 */     return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getAmbientSound() {
/* 293 */     return SoundEvents.ZOMBIE_VILLAGER_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getHurtSound(DamageSource $$0) {
/* 298 */     return SoundEvents.ZOMBIE_VILLAGER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getDeathSound() {
/* 303 */     return SoundEvents.ZOMBIE_VILLAGER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getStepSound() {
/* 308 */     return SoundEvents.ZOMBIE_VILLAGER_STEP;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack getSkull() {
/* 313 */     return ItemStack.EMPTY;
/*     */   }
/*     */   
/*     */   public void setTradeOffers(CompoundTag $$0) {
/* 317 */     this.tradeOffers = $$0;
/*     */   }
/*     */   
/*     */   public void setGossips(Tag $$0) {
/* 321 */     this.gossips = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 327 */     setVillagerData(getVillagerData().setType(VillagerType.byBiome($$0.getBiome(blockPosition()))));
/*     */     
/* 329 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVillagerData(VillagerData $$0) {
/* 334 */     VillagerData $$1 = getVillagerData();
/* 335 */     if ($$1.getProfession() != $$0.getProfession()) {
/* 336 */       this.tradeOffers = null;
/*     */     }
/*     */     
/* 339 */     this.entityData.set(DATA_VILLAGER_DATA, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public VillagerData getVillagerData() {
/* 344 */     return (VillagerData)this.entityData.get(DATA_VILLAGER_DATA);
/*     */   }
/*     */   
/*     */   public int getVillagerXp() {
/* 348 */     return this.villagerXp;
/*     */   }
/*     */   
/*     */   public void setVillagerXp(int $$0) {
/* 352 */     this.villagerXp = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 357 */     return new Vector3f(0.0F, $$1.height + 0.175F * $$2, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\ZombieVillager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */