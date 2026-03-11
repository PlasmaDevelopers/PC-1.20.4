/*     */ package net.minecraft.world.entity.animal;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LightningBolt;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.Shearable;
/*     */ import net.minecraft.world.entity.VariantHolder;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.ItemUtils;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.SuspiciousStewItem;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.SuspiciousEffectHolder;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class MushroomCow extends Cow implements Shearable, VariantHolder<MushroomCow.MushroomType> {
/*  47 */   private static final EntityDataAccessor<String> DATA_TYPE = SynchedEntityData.defineId(MushroomCow.class, EntityDataSerializers.STRING);
/*     */   
/*     */   private static final int MUTATE_CHANCE = 1024;
/*     */   
/*     */   private static final String TAG_STEW_EFFECTS = "stew_effects";
/*     */   @Nullable
/*     */   private List<SuspiciousEffectHolder.EffectEntry> stewEffects;
/*     */   @Nullable
/*     */   private UUID lastLightningBoltUUID;
/*     */   
/*     */   public MushroomCow(EntityType<? extends MushroomCow> $$0, Level $$1) {
/*  58 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWalkTargetValue(BlockPos $$0, LevelReader $$1) {
/*  63 */     if ($$1.getBlockState($$0.below()).is(Blocks.MYCELIUM)) {
/*  64 */       return 10.0F;
/*     */     }
/*  66 */     return $$1.getPathfindingCostFromLightLevels($$0);
/*     */   }
/*     */   
/*     */   public static boolean checkMushroomSpawnRules(EntityType<MushroomCow> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/*  70 */     return ($$1.getBlockState($$3.below()).is(BlockTags.MOOSHROOMS_SPAWNABLE_ON) && 
/*  71 */       isBrightEnoughToSpawn((BlockAndTintGetter)$$1, $$3));
/*     */   }
/*     */ 
/*     */   
/*     */   public void thunderHit(ServerLevel $$0, LightningBolt $$1) {
/*  76 */     UUID $$2 = $$1.getUUID();
/*  77 */     if (!$$2.equals(this.lastLightningBoltUUID)) {
/*  78 */       setVariant((getVariant() == MushroomType.RED) ? MushroomType.BROWN : MushroomType.RED);
/*  79 */       this.lastLightningBoltUUID = $$2;
/*  80 */       playSound(SoundEvents.MOOSHROOM_CONVERT, 2.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  86 */     super.defineSynchedData();
/*     */     
/*  88 */     this.entityData.define(DATA_TYPE, MushroomType.RED.type);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/*  93 */     ItemStack $$2 = $$0.getItemInHand($$1);
/*  94 */     if ($$2.is(Items.BOWL) && !isBaby()) {
/*     */       ItemStack $$5; SoundEvent $$8;
/*  96 */       boolean $$3 = false;
/*     */       
/*  98 */       if (this.stewEffects != null) {
/*  99 */         $$3 = true;
/* 100 */         ItemStack $$4 = new ItemStack((ItemLike)Items.SUSPICIOUS_STEW);
/* 101 */         SuspiciousStewItem.saveMobEffects($$4, this.stewEffects);
/* 102 */         this.stewEffects = null;
/*     */       } else {
/* 104 */         $$5 = new ItemStack((ItemLike)Items.MUSHROOM_STEW);
/*     */       } 
/*     */       
/* 107 */       ItemStack $$6 = ItemUtils.createFilledResult($$2, $$0, $$5, false);
/* 108 */       $$0.setItemInHand($$1, $$6);
/*     */ 
/*     */       
/* 111 */       if ($$3) {
/* 112 */         SoundEvent $$7 = SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY;
/*     */       } else {
/* 114 */         $$8 = SoundEvents.MOOSHROOM_MILK;
/*     */       } 
/*     */       
/* 117 */       playSound($$8, 1.0F, 1.0F);
/*     */       
/* 119 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/*     */     
/* 122 */     if ($$2.is(Items.SHEARS) && readyForShearing()) {
/* 123 */       shear(SoundSource.PLAYERS);
/* 124 */       gameEvent(GameEvent.SHEAR, (Entity)$$0);
/* 125 */       if (!(level()).isClientSide) {
/* 126 */         $$2.hurtAndBreak(1, (LivingEntity)$$0, $$1 -> $$1.broadcastBreakEvent($$0));
/*     */       }
/* 128 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/*     */ 
/*     */     
/* 132 */     if (getVariant() == MushroomType.BROWN && $$2.is(ItemTags.SMALL_FLOWERS)) {
/* 133 */       if (this.stewEffects != null) {
/* 134 */         for (int $$9 = 0; $$9 < 2; $$9++) {
/* 135 */           level().addParticle((ParticleOptions)ParticleTypes.SMOKE, getX() + this.random.nextDouble() / 2.0D, getY(0.5D), getZ() + this.random.nextDouble() / 2.0D, 0.0D, this.random.nextDouble() / 5.0D, 0.0D);
/*     */         }
/*     */       } else {
/* 138 */         Optional<List<SuspiciousEffectHolder.EffectEntry>> $$10 = getEffectsFromItemStack($$2);
/* 139 */         if ($$10.isEmpty()) {
/* 140 */           return InteractionResult.PASS;
/*     */         }
/*     */         
/* 143 */         if (!($$0.getAbilities()).instabuild) {
/* 144 */           $$2.shrink(1);
/*     */         }
/* 146 */         for (int $$11 = 0; $$11 < 4; $$11++) {
/* 147 */           level().addParticle((ParticleOptions)ParticleTypes.EFFECT, getX() + this.random.nextDouble() / 2.0D, getY(0.5D), getZ() + this.random.nextDouble() / 2.0D, 0.0D, this.random.nextDouble() / 5.0D, 0.0D);
/*     */         }
/* 149 */         this.stewEffects = $$10.get();
/* 150 */         playSound(SoundEvents.MOOSHROOM_EAT, 2.0F, 1.0F);
/*     */       } 
/* 152 */       return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */     } 
/*     */     
/* 155 */     return super.mobInteract($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void shear(SoundSource $$0) {
/* 160 */     level().playSound(null, (Entity)this, SoundEvents.MOOSHROOM_SHEAR, $$0, 1.0F, 1.0F);
/*     */     
/* 162 */     if (!level().isClientSide()) {
/* 163 */       Cow $$1 = (Cow)EntityType.COW.create(level());
/* 164 */       if ($$1 != null) {
/* 165 */         ((ServerLevel)level()).sendParticles((ParticleOptions)ParticleTypes.EXPLOSION, getX(), getY(0.5D), getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */         
/* 167 */         discard();
/*     */         
/* 169 */         $$1.moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
/* 170 */         $$1.setHealth(getHealth());
/* 171 */         $$1.yBodyRot = this.yBodyRot;
/* 172 */         if (hasCustomName()) {
/* 173 */           $$1.setCustomName(getCustomName());
/* 174 */           $$1.setCustomNameVisible(isCustomNameVisible());
/*     */         } 
/* 176 */         if (isPersistenceRequired()) {
/* 177 */           $$1.setPersistenceRequired();
/*     */         }
/* 179 */         $$1.setInvulnerable(isInvulnerable());
/*     */         
/* 181 */         level().addFreshEntity((Entity)$$1);
/* 182 */         for (int $$2 = 0; $$2 < 5; $$2++) {
/* 183 */           level().addFreshEntity((Entity)new ItemEntity(level(), getX(), getY(1.0D), getZ(), new ItemStack((ItemLike)(getVariant()).blockState.getBlock())));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean readyForShearing() {
/* 191 */     return (isAlive() && !isBaby());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 196 */     super.addAdditionalSaveData($$0);
/* 197 */     $$0.putString("Type", getVariant().getSerializedName());
/*     */     
/* 199 */     if (this.stewEffects != null) {
/* 200 */       SuspiciousEffectHolder.EffectEntry.LIST_CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.stewEffects).result().ifPresent($$1 -> $$0.put("stew_effects", $$1));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 206 */     super.readAdditionalSaveData($$0);
/* 207 */     setVariant(MushroomType.byType($$0.getString("Type")));
/*     */     
/* 209 */     if ($$0.contains("stew_effects", 9)) {
/* 210 */       SuspiciousEffectHolder.EffectEntry.LIST_CODEC.parse((DynamicOps)NbtOps.INSTANCE, $$0.get("stew_effects")).result().ifPresent($$0 -> this.stewEffects = $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private Optional<List<SuspiciousEffectHolder.EffectEntry>> getEffectsFromItemStack(ItemStack $$0) {
/* 215 */     SuspiciousEffectHolder $$1 = SuspiciousEffectHolder.tryGet((ItemLike)$$0.getItem());
/* 216 */     if ($$1 != null) {
/* 217 */       return Optional.of($$1.getSuspiciousEffects());
/*     */     }
/* 219 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVariant(MushroomType $$0) {
/* 224 */     this.entityData.set(DATA_TYPE, $$0.type);
/*     */   }
/*     */ 
/*     */   
/*     */   public MushroomType getVariant() {
/* 229 */     return MushroomType.byType((String)this.entityData.get(DATA_TYPE));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public MushroomCow getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 235 */     MushroomCow $$2 = (MushroomCow)EntityType.MOOSHROOM.create((Level)$$0);
/* 236 */     if ($$2 != null) {
/* 237 */       $$2.setVariant(getOffspringType((MushroomCow)$$1));
/*     */     }
/* 239 */     return $$2;
/*     */   }
/*     */   
/*     */   private MushroomType getOffspringType(MushroomCow $$0) {
/* 243 */     MushroomType $$4, $$1 = getVariant();
/* 244 */     MushroomType $$2 = $$0.getVariant();
/*     */ 
/*     */     
/* 247 */     if ($$1 == $$2 && this.random.nextInt(1024) == 0) {
/* 248 */       MushroomType $$3 = ($$1 == MushroomType.BROWN) ? MushroomType.RED : MushroomType.BROWN;
/*     */     } else {
/* 250 */       $$4 = this.random.nextBoolean() ? $$1 : $$2;
/*     */     } 
/* 252 */     return $$4;
/*     */   }
/*     */   
/*     */   public enum MushroomType implements StringRepresentable {
/* 256 */     RED("red", Blocks.RED_MUSHROOM.defaultBlockState()),
/* 257 */     BROWN("brown", Blocks.BROWN_MUSHROOM.defaultBlockState());
/*     */     
/* 259 */     public static final StringRepresentable.EnumCodec<MushroomType> CODEC = StringRepresentable.fromEnum(MushroomType::values); final String type; final BlockState blockState;
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     MushroomType(String $$0, BlockState $$1) {
/* 265 */       this.type = $$0;
/* 266 */       this.blockState = $$1;
/*     */     }
/*     */     
/*     */     public BlockState getBlockState() {
/* 270 */       return this.blockState;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/* 275 */       return this.type;
/*     */     }
/*     */     
/*     */     static MushroomType byType(String $$0) {
/* 279 */       return (MushroomType)CODEC.byName($$0, RED);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\MushroomCow.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */