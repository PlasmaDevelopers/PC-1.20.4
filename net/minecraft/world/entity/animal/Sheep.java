/*     */ package net.minecraft.world.entity.animal;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.Shearable;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*     */ import net.minecraft.world.entity.ai.goal.EatBlockGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FollowParentGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
/*     */ import net.minecraft.world.entity.ai.goal.TemptGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.CraftingContainer;
/*     */ import net.minecraft.world.inventory.MenuType;
/*     */ import net.minecraft.world.inventory.TransientCraftingContainer;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.DyeItem;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.CraftingRecipe;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Sheep extends Animal implements Shearable {
/*     */   private static final int EAT_ANIMATION_TICKS = 40;
/*  71 */   private static final EntityDataAccessor<Byte> DATA_WOOL_ID = SynchedEntityData.defineId(Sheep.class, EntityDataSerializers.BYTE); private static final Map<DyeColor, ItemLike> ITEM_BY_DYE; private static final Map<DyeColor, float[]> COLORARRAY_BY_COLOR; private int eatAnimationTick; private EatBlockGoal eatBlockGoal;
/*     */   static {
/*  73 */     ITEM_BY_DYE = (Map<DyeColor, ItemLike>)Util.make(Maps.newEnumMap(DyeColor.class), $$0 -> {
/*     */           $$0.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
/*     */           
/*     */           $$0.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
/*     */           $$0.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
/*     */           $$0.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
/*     */           $$0.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
/*     */           $$0.put(DyeColor.LIME, Blocks.LIME_WOOL);
/*     */           $$0.put(DyeColor.PINK, Blocks.PINK_WOOL);
/*     */           $$0.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
/*     */           $$0.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
/*     */           $$0.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
/*     */           $$0.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
/*     */           $$0.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
/*     */           $$0.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
/*     */           $$0.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
/*     */           $$0.put(DyeColor.RED, Blocks.RED_WOOL);
/*     */           $$0.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
/*     */         });
/*  92 */     COLORARRAY_BY_COLOR = Maps.newEnumMap((Map)Arrays.<DyeColor>stream(DyeColor.values()).collect(Collectors.toMap($$0 -> $$0, Sheep::createSheepColor)));
/*     */   }
/*     */   private static float[] createSheepColor(DyeColor $$0) {
/*  95 */     if ($$0 == DyeColor.WHITE) {
/*  96 */       return new float[] { 0.9019608F, 0.9019608F, 0.9019608F };
/*     */     }
/*  98 */     float[] $$1 = $$0.getTextureDiffuseColors();
/*     */ 
/*     */     
/* 101 */     float $$2 = 0.75F;
/*     */     
/* 103 */     return new float[] { $$1[0] * 0.75F, $$1[1] * 0.75F, $$1[2] * 0.75F };
/*     */   }
/*     */   
/*     */   public static float[] getColorArray(DyeColor $$0) {
/* 107 */     return COLORARRAY_BY_COLOR.get($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sheep(EntityType<? extends Sheep> $$0, Level $$1) {
/* 114 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 119 */     this.eatBlockGoal = new EatBlockGoal((Mob)this);
/* 120 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/* 121 */     this.goalSelector.addGoal(1, (Goal)new PanicGoal((PathfinderMob)this, 1.25D));
/* 122 */     this.goalSelector.addGoal(2, (Goal)new BreedGoal(this, 1.0D));
/* 123 */     this.goalSelector.addGoal(3, (Goal)new TemptGoal((PathfinderMob)this, 1.1D, Ingredient.of(new ItemLike[] { (ItemLike)Items.WHEAT }, ), false));
/* 124 */     this.goalSelector.addGoal(4, (Goal)new FollowParentGoal(this, 1.1D));
/* 125 */     this.goalSelector.addGoal(5, (Goal)this.eatBlockGoal);
/* 126 */     this.goalSelector.addGoal(6, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 1.0D));
/* 127 */     this.goalSelector.addGoal(7, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 6.0F));
/* 128 */     this.goalSelector.addGoal(8, (Goal)new RandomLookAroundGoal((Mob)this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/* 133 */     this.eatAnimationTick = this.eatBlockGoal.getEatAnimationTick();
/* 134 */     super.customServerAiStep();
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 139 */     if ((level()).isClientSide) {
/* 140 */       this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
/*     */     }
/* 142 */     super.aiStep();
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 146 */     return Mob.createMobAttributes()
/* 147 */       .add(Attributes.MAX_HEALTH, 8.0D)
/* 148 */       .add(Attributes.MOVEMENT_SPEED, 0.23000000417232513D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 153 */     super.defineSynchedData();
/*     */ 
/*     */     
/* 156 */     this.entityData.define(DATA_WOOL_ID, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getDefaultLootTable() {
/* 161 */     if (isSheared()) {
/* 162 */       return getType().getDefaultLootTable();
/*     */     }
/* 164 */     switch (getColor()) { default: throw new IncompatibleClassChangeError();case WHITE: case ORANGE: case MAGENTA: case LIGHT_BLUE: case YELLOW: case LIME: case PINK: case GRAY: case LIGHT_GRAY: case CYAN: case PURPLE: case BLUE: case BROWN: case GREEN: case RED: case BLACK: break; }  return 
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
/* 180 */       BuiltInLootTables.SHEEP_BLACK;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 186 */     if ($$0 == 10) {
/* 187 */       this.eatAnimationTick = 40;
/*     */     } else {
/* 189 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getHeadEatPositionScale(float $$0) {
/* 194 */     if (this.eatAnimationTick <= 0) {
/* 195 */       return 0.0F;
/*     */     }
/* 197 */     if (this.eatAnimationTick >= 4 && this.eatAnimationTick <= 36) {
/* 198 */       return 1.0F;
/*     */     }
/* 200 */     if (this.eatAnimationTick < 4) {
/* 201 */       return (this.eatAnimationTick - $$0) / 4.0F;
/*     */     }
/* 203 */     return -((this.eatAnimationTick - 40) - $$0) / 4.0F;
/*     */   }
/*     */   
/*     */   public float getHeadEatAngleScale(float $$0) {
/* 207 */     if (this.eatAnimationTick > 4 && this.eatAnimationTick <= 36) {
/* 208 */       float $$1 = ((this.eatAnimationTick - 4) - $$0) / 32.0F;
/* 209 */       return 0.62831855F + 0.21991149F * Mth.sin($$1 * 28.7F);
/*     */     } 
/* 211 */     if (this.eatAnimationTick > 0) {
/* 212 */       return 0.62831855F;
/*     */     }
/* 214 */     return getXRot() * 0.017453292F;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult mobInteract(Player $$0, InteractionHand $$1) {
/* 219 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 220 */     if ($$2.is(Items.SHEARS)) {
/* 221 */       if (!(level()).isClientSide && readyForShearing()) {
/* 222 */         shear(SoundSource.PLAYERS);
/* 223 */         gameEvent(GameEvent.SHEAR, (Entity)$$0);
/* 224 */         $$2.hurtAndBreak(1, (LivingEntity)$$0, $$1 -> $$1.broadcastBreakEvent($$0));
/* 225 */         return InteractionResult.SUCCESS;
/*     */       } 
/* 227 */       return InteractionResult.CONSUME;
/*     */     } 
/*     */     
/* 230 */     return super.mobInteract($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void shear(SoundSource $$0) {
/* 235 */     level().playSound(null, (Entity)this, SoundEvents.SHEEP_SHEAR, $$0, 1.0F, 1.0F);
/*     */     
/* 237 */     setSheared(true);
/* 238 */     int $$1 = 1 + this.random.nextInt(3);
/* 239 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/* 240 */       ItemEntity $$3 = spawnAtLocation(ITEM_BY_DYE.get(getColor()), 1);
/* 241 */       if ($$3 != null) {
/* 242 */         $$3.setDeltaMovement($$3.getDeltaMovement().add(((this.random
/* 243 */               .nextFloat() - this.random.nextFloat()) * 0.1F), (this.random
/* 244 */               .nextFloat() * 0.05F), ((this.random
/* 245 */               .nextFloat() - this.random.nextFloat()) * 0.1F)));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean readyForShearing() {
/* 253 */     return (isAlive() && !isSheared() && !isBaby());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 258 */     super.addAdditionalSaveData($$0);
/* 259 */     $$0.putBoolean("Sheared", isSheared());
/* 260 */     $$0.putByte("Color", (byte)getColor().getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 265 */     super.readAdditionalSaveData($$0);
/* 266 */     setSheared($$0.getBoolean("Sheared"));
/* 267 */     setColor(DyeColor.byId($$0.getByte("Color")));
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 272 */     return SoundEvents.SHEEP_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 277 */     return SoundEvents.SHEEP_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 282 */     return SoundEvents.SHEEP_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/* 287 */     playSound(SoundEvents.SHEEP_STEP, 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   public DyeColor getColor() {
/* 291 */     return DyeColor.byId(((Byte)this.entityData.get(DATA_WOOL_ID)).byteValue() & 0xF);
/*     */   }
/*     */   
/*     */   public void setColor(DyeColor $$0) {
/* 295 */     byte $$1 = ((Byte)this.entityData.get(DATA_WOOL_ID)).byteValue();
/* 296 */     this.entityData.set(DATA_WOOL_ID, Byte.valueOf((byte)($$1 & 0xF0 | $$0.getId() & 0xF)));
/*     */   }
/*     */   
/*     */   public boolean isSheared() {
/* 300 */     return ((((Byte)this.entityData.get(DATA_WOOL_ID)).byteValue() & 0x10) != 0);
/*     */   }
/*     */   
/*     */   public void setSheared(boolean $$0) {
/* 304 */     byte $$1 = ((Byte)this.entityData.get(DATA_WOOL_ID)).byteValue();
/* 305 */     if ($$0) {
/* 306 */       this.entityData.set(DATA_WOOL_ID, Byte.valueOf((byte)($$1 | 0x10)));
/*     */     } else {
/* 308 */       this.entityData.set(DATA_WOOL_ID, Byte.valueOf((byte)($$1 & 0xFFFFFFEF)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static DyeColor getRandomSheepColor(RandomSource $$0) {
/* 313 */     int $$1 = $$0.nextInt(100);
/* 314 */     if ($$1 < 5) {
/* 315 */       return DyeColor.BLACK;
/*     */     }
/* 317 */     if ($$1 < 10) {
/* 318 */       return DyeColor.GRAY;
/*     */     }
/* 320 */     if ($$1 < 15) {
/* 321 */       return DyeColor.LIGHT_GRAY;
/*     */     }
/* 323 */     if ($$1 < 18) {
/* 324 */       return DyeColor.BROWN;
/*     */     }
/* 326 */     if ($$0.nextInt(500) == 0) {
/* 327 */       return DyeColor.PINK;
/*     */     }
/* 329 */     return DyeColor.WHITE;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Sheep getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/* 335 */     Sheep $$2 = (Sheep)EntityType.SHEEP.create((Level)$$0);
/*     */     
/* 337 */     if ($$2 != null) {
/* 338 */       $$2.setColor(getOffspringColor(this, (Sheep)$$1));
/*     */     }
/*     */     
/* 341 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void ate() {
/* 346 */     super.ate();
/* 347 */     setSheared(false);
/* 348 */     if (isBaby())
/*     */     {
/* 350 */       ageUp(60);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 357 */     setColor(getRandomSheepColor($$0.getRandom()));
/* 358 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   private DyeColor getOffspringColor(Animal $$0, Animal $$1) {
/* 362 */     DyeColor $$2 = ((Sheep)$$0).getColor();
/* 363 */     DyeColor $$3 = ((Sheep)$$1).getColor();
/*     */     
/* 365 */     CraftingContainer $$4 = makeContainer($$2, $$3);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 370 */     Objects.requireNonNull(DyeItem.class);
/* 371 */     Objects.requireNonNull(DyeItem.class); return level().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, (Container)$$4, level()).map($$1 -> ((CraftingRecipe)$$1.value()).assemble((Container)$$0, level().registryAccess())).map(ItemStack::getItem).filter(DyeItem.class::isInstance).map(DyeItem.class::cast)
/* 372 */       .map(DyeItem::getDyeColor)
/* 373 */       .orElseGet(() -> (level()).random.nextBoolean() ? $$0 : $$1);
/*     */   }
/*     */   
/*     */   private static CraftingContainer makeContainer(DyeColor $$0, DyeColor $$1) {
/* 377 */     TransientCraftingContainer transientCraftingContainer = new TransientCraftingContainer(new AbstractContainerMenu(null, -1)
/*     */         {
/*     */           public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 380 */             return ItemStack.EMPTY;
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean stillValid(Player $$0) {
/* 385 */             return false;
/*     */           }
/*     */         }2, 1);
/* 388 */     transientCraftingContainer.setItem(0, new ItemStack((ItemLike)DyeItem.byColor($$0)));
/* 389 */     transientCraftingContainer.setItem(1, new ItemStack((ItemLike)DyeItem.byColor($$1)));
/* 390 */     return (CraftingContainer)transientCraftingContainer;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 395 */     return 0.95F * $$1.height;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 400 */     return new Vector3f(0.0F, $$1.height - 0.0625F * $$2, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Sheep.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */