/*     */ package net.minecraft.core.dispenser;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.Saddleable;
/*     */ import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
/*     */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*     */ import net.minecraft.world.entity.decoration.ArmorStand;
/*     */ import net.minecraft.world.entity.item.PrimedTnt;
/*     */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*     */ import net.minecraft.world.entity.projectile.Arrow;
/*     */ import net.minecraft.world.entity.projectile.FireworkRocketEntity;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
/*     */ import net.minecraft.world.entity.projectile.SmallFireball;
/*     */ import net.minecraft.world.entity.projectile.Snowball;
/*     */ import net.minecraft.world.entity.projectile.SpectralArrow;
/*     */ import net.minecraft.world.entity.projectile.ThrownEgg;
/*     */ import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
/*     */ import net.minecraft.world.entity.projectile.ThrownPotion;
/*     */ import net.minecraft.world.entity.vehicle.Boat;
/*     */ import net.minecraft.world.item.ArmorItem;
/*     */ import net.minecraft.world.item.BoneMealItem;
/*     */ import net.minecraft.world.item.DispensibleContainerItem;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.HoneycombItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.SpawnEggItem;
/*     */ import net.minecraft.world.item.alchemy.PotionUtils;
/*     */ import net.minecraft.world.item.alchemy.Potions;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.BaseFireBlock;
/*     */ import net.minecraft.world.level.block.BeehiveBlock;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.BucketPickup;
/*     */ import net.minecraft.world.level.block.CampfireBlock;
/*     */ import net.minecraft.world.level.block.CandleBlock;
/*     */ import net.minecraft.world.level.block.CandleCakeBlock;
/*     */ import net.minecraft.world.level.block.CarvedPumpkinBlock;
/*     */ import net.minecraft.world.level.block.DispenserBlock;
/*     */ import net.minecraft.world.level.block.RespawnAnchorBlock;
/*     */ import net.minecraft.world.level.block.SkullBlock;
/*     */ import net.minecraft.world.level.block.TntBlock;
/*     */ import net.minecraft.world.level.block.WitherSkullBlock;
/*     */ import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.SkullBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RotationSegment;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public interface DispenseItemBehavior {
/*  81 */   public static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static final DispenseItemBehavior NOOP;
/*     */   
/*     */   static {
/*  86 */     NOOP = (($$0, $$1) -> $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void bootStrap() {
/*  96 */     DispenserBlock.registerBehavior((ItemLike)Items.ARROW, new AbstractProjectileDispenseBehavior()
/*     */         {
/*     */           protected Projectile getProjectile(Level $$0, Position $$1, ItemStack $$2) {
/*  99 */             Arrow $$3 = new Arrow($$0, $$1.x(), $$1.y(), $$1.z(), $$2.copyWithCount(1));
/* 100 */             $$3.pickup = AbstractArrow.Pickup.ALLOWED;
/*     */             
/* 102 */             return (Projectile)$$3;
/*     */           }
/*     */         });
/* 105 */     DispenserBlock.registerBehavior((ItemLike)Items.TIPPED_ARROW, new AbstractProjectileDispenseBehavior()
/*     */         {
/*     */           protected Projectile getProjectile(Level $$0, Position $$1, ItemStack $$2) {
/* 108 */             Arrow $$3 = new Arrow($$0, $$1.x(), $$1.y(), $$1.z(), $$2.copyWithCount(1));
/* 109 */             $$3.setEffectsFromItem($$2);
/* 110 */             $$3.pickup = AbstractArrow.Pickup.ALLOWED;
/*     */             
/* 112 */             return (Projectile)$$3;
/*     */           }
/*     */         });
/* 115 */     DispenserBlock.registerBehavior((ItemLike)Items.SPECTRAL_ARROW, new AbstractProjectileDispenseBehavior()
/*     */         {
/*     */           protected Projectile getProjectile(Level $$0, Position $$1, ItemStack $$2) {
/* 118 */             SpectralArrow spectralArrow = new SpectralArrow($$0, $$1.x(), $$1.y(), $$1.z(), $$2.copyWithCount(1));
/* 119 */             ((AbstractArrow)spectralArrow).pickup = AbstractArrow.Pickup.ALLOWED;
/*     */             
/* 121 */             return (Projectile)spectralArrow;
/*     */           }
/*     */         });
/* 124 */     DispenserBlock.registerBehavior((ItemLike)Items.EGG, new AbstractProjectileDispenseBehavior()
/*     */         {
/*     */           protected Projectile getProjectile(Level $$0, Position $$1, ItemStack $$2) {
/* 127 */             return (Projectile)Util.make(new ThrownEgg($$0, $$1.x(), $$1.y(), $$1.z()), $$1 -> $$1.setItem($$0));
/*     */           }
/*     */         });
/* 130 */     DispenserBlock.registerBehavior((ItemLike)Items.SNOWBALL, new AbstractProjectileDispenseBehavior()
/*     */         {
/*     */           protected Projectile getProjectile(Level $$0, Position $$1, ItemStack $$2) {
/* 133 */             return (Projectile)Util.make(new Snowball($$0, $$1.x(), $$1.y(), $$1.z()), $$1 -> $$1.setItem($$0));
/*     */           }
/*     */         });
/* 136 */     DispenserBlock.registerBehavior((ItemLike)Items.EXPERIENCE_BOTTLE, new AbstractProjectileDispenseBehavior()
/*     */         {
/*     */           protected Projectile getProjectile(Level $$0, Position $$1, ItemStack $$2) {
/* 139 */             return (Projectile)Util.make(new ThrownExperienceBottle($$0, $$1.x(), $$1.y(), $$1.z()), $$1 -> $$1.setItem($$0));
/*     */           }
/*     */ 
/*     */           
/*     */           protected float getUncertainty() {
/* 144 */             return super.getUncertainty() * 0.5F;
/*     */           }
/*     */ 
/*     */           
/*     */           protected float getPower() {
/* 149 */             return super.getPower() * 1.25F;
/*     */           }
/*     */         });
/*     */     
/* 153 */     DispenserBlock.registerBehavior((ItemLike)Items.SPLASH_POTION, new DispenseItemBehavior()
/*     */         {
/*     */           public ItemStack dispense(BlockSource $$0, ItemStack $$1) {
/* 156 */             return (new AbstractProjectileDispenseBehavior()
/*     */               {
/*     */                 protected Projectile getProjectile(Level $$0, Position $$1, ItemStack $$2) {
/* 159 */                   return (Projectile)Util.make(new ThrownPotion($$0, $$1.x(), $$1.y(), $$1.z()), $$1 -> $$1.setItem($$0));
/*     */                 }
/*     */ 
/*     */                 
/*     */                 protected float getUncertainty() {
/* 164 */                   return super.getUncertainty() * 0.5F;
/*     */                 }
/*     */ 
/*     */                 
/*     */                 protected float getPower() {
/* 169 */                   return super.getPower() * 1.25F;
/*     */                 }
/* 171 */               }).dispense($$0, $$1);
/*     */           }
/*     */         });
/*     */     
/* 175 */     DispenserBlock.registerBehavior((ItemLike)Items.LINGERING_POTION, new DispenseItemBehavior()
/*     */         {
/*     */           public ItemStack dispense(BlockSource $$0, ItemStack $$1) {
/* 178 */             return (new AbstractProjectileDispenseBehavior()
/*     */               {
/*     */                 protected Projectile getProjectile(Level $$0, Position $$1, ItemStack $$2) {
/* 181 */                   return (Projectile)Util.make(new ThrownPotion($$0, $$1.x(), $$1.y(), $$1.z()), $$1 -> $$1.setItem($$0));
/*     */                 }
/*     */ 
/*     */                 
/*     */                 protected float getUncertainty() {
/* 186 */                   return super.getUncertainty() * 0.5F;
/*     */                 }
/*     */ 
/*     */                 
/*     */                 protected float getPower() {
/* 191 */                   return super.getPower() * 1.25F;
/*     */                 }
/* 193 */               }).dispense($$0, $$1);
/*     */           }
/*     */         });
/*     */     
/* 197 */     DefaultDispenseItemBehavior $$0 = new DefaultDispenseItemBehavior()
/*     */       {
/*     */         public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 200 */           Direction $$2 = (Direction)$$0.state().getValue((Property)DispenserBlock.FACING);
/*     */           
/* 202 */           EntityType<?> $$3 = ((SpawnEggItem)$$1.getItem()).getType($$1.getTag());
/*     */           try {
/* 204 */             $$3.spawn($$0.level(), $$1, null, $$0.pos().relative($$2), MobSpawnType.DISPENSER, ($$2 != Direction.UP), false);
/* 205 */           } catch (Exception $$4) {
/* 206 */             LOGGER.error("Error while dispensing spawn egg from dispenser at {}", $$0.pos(), $$4);
/* 207 */             return ItemStack.EMPTY;
/*     */           } 
/* 209 */           $$1.shrink(1);
/* 210 */           $$0.level().gameEvent(null, GameEvent.ENTITY_PLACE, $$0.pos());
/* 211 */           return $$1;
/*     */         }
/*     */       };
/*     */     
/* 215 */     for (SpawnEggItem $$1 : SpawnEggItem.eggs()) {
/* 216 */       DispenserBlock.registerBehavior((ItemLike)$$1, $$0);
/*     */     }
/*     */     
/* 219 */     DispenserBlock.registerBehavior((ItemLike)Items.ARMOR_STAND, new DefaultDispenseItemBehavior()
/*     */         {
/*     */           public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 222 */             Direction $$2 = (Direction)$$0.state().getValue((Property)DispenserBlock.FACING);
/* 223 */             BlockPos $$3 = $$0.pos().relative($$2);
/* 224 */             ServerLevel $$4 = $$0.level();
/* 225 */             Consumer<ArmorStand> $$5 = EntityType.appendDefaultStackConfig($$1 -> $$1.setYRot($$0.toYRot()), $$4, $$1, null);
/* 226 */             ArmorStand $$6 = (ArmorStand)EntityType.ARMOR_STAND.spawn($$4, $$1.getTag(), $$5, $$3, MobSpawnType.DISPENSER, false, false);
/* 227 */             if ($$6 != null) {
/* 228 */               $$1.shrink(1);
/*     */             }
/* 230 */             return $$1;
/*     */           }
/*     */         });
/*     */     
/* 234 */     DispenserBlock.registerBehavior((ItemLike)Items.SADDLE, new OptionalDispenseItemBehavior()
/*     */         {
/*     */           public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 237 */             BlockPos $$2 = $$0.pos().relative((Direction)$$0.state().getValue((Property)DispenserBlock.FACING));
/* 238 */             List<LivingEntity> $$3 = $$0.level().getEntitiesOfClass(LivingEntity.class, new AABB($$2), $$0 -> {
/*     */                   if ($$0 instanceof Saddleable) {
/* 240 */                     Saddleable $$1 = (Saddleable)$$0; return (!$$1.isSaddled() && $$1.isSaddleable());
/*     */                   } 
/*     */                   
/*     */                   return false;
/*     */                 });
/* 245 */             if (!$$3.isEmpty()) {
/* 246 */               ((Saddleable)$$3.get(0)).equipSaddle(SoundSource.BLOCKS);
/* 247 */               $$1.shrink(1);
/* 248 */               setSuccess(true);
/* 249 */               return $$1;
/*     */             } 
/*     */             
/* 252 */             return super.execute($$0, $$1);
/*     */           }
/*     */         });
/*     */     
/* 256 */     DefaultDispenseItemBehavior $$2 = new OptionalDispenseItemBehavior()
/*     */       {
/*     */         protected ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 259 */           BlockPos $$2 = $$0.pos().relative((Direction)$$0.state().getValue((Property)DispenserBlock.FACING));
/* 260 */           List<AbstractHorse> $$3 = $$0.level().getEntitiesOfClass(AbstractHorse.class, new AABB($$2), $$0 -> ($$0.isAlive() && $$0.canWearArmor()));
/*     */           
/* 262 */           for (AbstractHorse $$4 : $$3) {
/* 263 */             if ($$4.isArmor($$1) && !$$4.isWearingArmor() && $$4.isTamed()) {
/* 264 */               $$4.getSlot(401).set($$1.split(1));
/* 265 */               setSuccess(true);
/* 266 */               return $$1;
/*     */             } 
/*     */           } 
/*     */           
/* 270 */           return super.execute($$0, $$1);
/*     */         }
/*     */       };
/*     */     
/* 274 */     DispenserBlock.registerBehavior((ItemLike)Items.LEATHER_HORSE_ARMOR, $$2);
/* 275 */     DispenserBlock.registerBehavior((ItemLike)Items.IRON_HORSE_ARMOR, $$2);
/* 276 */     DispenserBlock.registerBehavior((ItemLike)Items.GOLDEN_HORSE_ARMOR, $$2);
/* 277 */     DispenserBlock.registerBehavior((ItemLike)Items.DIAMOND_HORSE_ARMOR, $$2);
/*     */     
/* 279 */     DispenserBlock.registerBehavior((ItemLike)Items.WHITE_CARPET, $$2);
/* 280 */     DispenserBlock.registerBehavior((ItemLike)Items.ORANGE_CARPET, $$2);
/* 281 */     DispenserBlock.registerBehavior((ItemLike)Items.CYAN_CARPET, $$2);
/* 282 */     DispenserBlock.registerBehavior((ItemLike)Items.BLUE_CARPET, $$2);
/* 283 */     DispenserBlock.registerBehavior((ItemLike)Items.BROWN_CARPET, $$2);
/* 284 */     DispenserBlock.registerBehavior((ItemLike)Items.BLACK_CARPET, $$2);
/* 285 */     DispenserBlock.registerBehavior((ItemLike)Items.GRAY_CARPET, $$2);
/* 286 */     DispenserBlock.registerBehavior((ItemLike)Items.GREEN_CARPET, $$2);
/* 287 */     DispenserBlock.registerBehavior((ItemLike)Items.LIGHT_BLUE_CARPET, $$2);
/* 288 */     DispenserBlock.registerBehavior((ItemLike)Items.LIGHT_GRAY_CARPET, $$2);
/* 289 */     DispenserBlock.registerBehavior((ItemLike)Items.LIME_CARPET, $$2);
/* 290 */     DispenserBlock.registerBehavior((ItemLike)Items.MAGENTA_CARPET, $$2);
/* 291 */     DispenserBlock.registerBehavior((ItemLike)Items.PINK_CARPET, $$2);
/* 292 */     DispenserBlock.registerBehavior((ItemLike)Items.PURPLE_CARPET, $$2);
/* 293 */     DispenserBlock.registerBehavior((ItemLike)Items.RED_CARPET, $$2);
/* 294 */     DispenserBlock.registerBehavior((ItemLike)Items.YELLOW_CARPET, $$2);
/*     */     
/* 296 */     DispenserBlock.registerBehavior((ItemLike)Items.CHEST, new OptionalDispenseItemBehavior()
/*     */         {
/*     */           public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 299 */             BlockPos $$2 = $$0.pos().relative((Direction)$$0.state().getValue((Property)DispenserBlock.FACING));
/* 300 */             List<AbstractChestedHorse> $$3 = $$0.level().getEntitiesOfClass(AbstractChestedHorse.class, new AABB($$2), $$0 -> ($$0.isAlive() && !$$0.hasChest()));
/*     */             
/* 302 */             for (AbstractChestedHorse $$4 : $$3) {
/* 303 */               if ($$4.isTamed() && $$4.getSlot(499).set($$1)) {
/* 304 */                 $$1.shrink(1);
/* 305 */                 setSuccess(true);
/* 306 */                 return $$1;
/*     */               } 
/*     */             } 
/*     */             
/* 310 */             return super.execute($$0, $$1);
/*     */           }
/*     */         });
/*     */     
/* 314 */     DispenserBlock.registerBehavior((ItemLike)Items.FIREWORK_ROCKET, new DefaultDispenseItemBehavior()
/*     */         {
/*     */           public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 317 */             Direction $$2 = (Direction)$$0.state().getValue((Property)DispenserBlock.FACING);
/* 318 */             Vec3 $$3 = DispenseItemBehavior.getEntityPokingOutOfBlockPos($$0, EntityType.FIREWORK_ROCKET, $$2);
/*     */             
/* 320 */             FireworkRocketEntity $$4 = new FireworkRocketEntity((Level)$$0.level(), $$1, $$3.x(), $$3.y(), $$3.z(), true);
/* 321 */             $$4.shoot($$2.getStepX(), $$2.getStepY(), $$2.getStepZ(), 0.5F, 1.0F);
/* 322 */             $$0.level().addFreshEntity((Entity)$$4);
/*     */             
/* 324 */             $$1.shrink(1);
/* 325 */             return $$1;
/*     */           }
/*     */ 
/*     */           
/*     */           protected void playSound(BlockSource $$0) {
/* 330 */             $$0.level().levelEvent(1004, $$0.pos(), 0);
/*     */           }
/*     */         });
/*     */     
/* 334 */     DispenserBlock.registerBehavior((ItemLike)Items.FIRE_CHARGE, new DefaultDispenseItemBehavior()
/*     */         {
/*     */           public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 337 */             Direction $$2 = (Direction)$$0.state().getValue((Property)DispenserBlock.FACING);
/*     */             
/* 339 */             Position $$3 = DispenserBlock.getDispensePosition($$0);
/* 340 */             double $$4 = $$3.x() + ($$2.getStepX() * 0.3F);
/* 341 */             double $$5 = $$3.y() + ($$2.getStepY() * 0.3F);
/* 342 */             double $$6 = $$3.z() + ($$2.getStepZ() * 0.3F);
/*     */             
/* 344 */             ServerLevel serverLevel = $$0.level();
/* 345 */             RandomSource $$8 = ((Level)serverLevel).random;
/*     */             
/* 347 */             double $$9 = $$8.triangle($$2.getStepX(), 0.11485000000000001D);
/* 348 */             double $$10 = $$8.triangle($$2.getStepY(), 0.11485000000000001D);
/* 349 */             double $$11 = $$8.triangle($$2.getStepZ(), 0.11485000000000001D);
/*     */             
/* 351 */             SmallFireball $$12 = new SmallFireball((Level)serverLevel, $$4, $$5, $$6, $$9, $$10, $$11);
/* 352 */             serverLevel.addFreshEntity((Entity)Util.make($$12, $$1 -> $$1.setItem($$0)));
/*     */             
/* 354 */             $$1.shrink(1);
/* 355 */             return $$1;
/*     */           }
/*     */ 
/*     */           
/*     */           protected void playSound(BlockSource $$0) {
/* 360 */             $$0.level().levelEvent(1018, $$0.pos(), 0);
/*     */           }
/*     */         });
/*     */     
/* 364 */     DispenserBlock.registerBehavior((ItemLike)Items.OAK_BOAT, new BoatDispenseItemBehavior(Boat.Type.OAK));
/* 365 */     DispenserBlock.registerBehavior((ItemLike)Items.SPRUCE_BOAT, new BoatDispenseItemBehavior(Boat.Type.SPRUCE));
/* 366 */     DispenserBlock.registerBehavior((ItemLike)Items.BIRCH_BOAT, new BoatDispenseItemBehavior(Boat.Type.BIRCH));
/* 367 */     DispenserBlock.registerBehavior((ItemLike)Items.JUNGLE_BOAT, new BoatDispenseItemBehavior(Boat.Type.JUNGLE));
/* 368 */     DispenserBlock.registerBehavior((ItemLike)Items.DARK_OAK_BOAT, new BoatDispenseItemBehavior(Boat.Type.DARK_OAK));
/* 369 */     DispenserBlock.registerBehavior((ItemLike)Items.ACACIA_BOAT, new BoatDispenseItemBehavior(Boat.Type.ACACIA));
/* 370 */     DispenserBlock.registerBehavior((ItemLike)Items.CHERRY_BOAT, new BoatDispenseItemBehavior(Boat.Type.CHERRY));
/* 371 */     DispenserBlock.registerBehavior((ItemLike)Items.MANGROVE_BOAT, new BoatDispenseItemBehavior(Boat.Type.MANGROVE));
/* 372 */     DispenserBlock.registerBehavior((ItemLike)Items.BAMBOO_RAFT, new BoatDispenseItemBehavior(Boat.Type.BAMBOO));
/*     */     
/* 374 */     DispenserBlock.registerBehavior((ItemLike)Items.OAK_CHEST_BOAT, new BoatDispenseItemBehavior(Boat.Type.OAK, true));
/* 375 */     DispenserBlock.registerBehavior((ItemLike)Items.SPRUCE_CHEST_BOAT, new BoatDispenseItemBehavior(Boat.Type.SPRUCE, true));
/* 376 */     DispenserBlock.registerBehavior((ItemLike)Items.BIRCH_CHEST_BOAT, new BoatDispenseItemBehavior(Boat.Type.BIRCH, true));
/* 377 */     DispenserBlock.registerBehavior((ItemLike)Items.JUNGLE_CHEST_BOAT, new BoatDispenseItemBehavior(Boat.Type.JUNGLE, true));
/* 378 */     DispenserBlock.registerBehavior((ItemLike)Items.DARK_OAK_CHEST_BOAT, new BoatDispenseItemBehavior(Boat.Type.DARK_OAK, true));
/* 379 */     DispenserBlock.registerBehavior((ItemLike)Items.ACACIA_CHEST_BOAT, new BoatDispenseItemBehavior(Boat.Type.ACACIA, true));
/* 380 */     DispenserBlock.registerBehavior((ItemLike)Items.CHERRY_CHEST_BOAT, new BoatDispenseItemBehavior(Boat.Type.CHERRY, true));
/* 381 */     DispenserBlock.registerBehavior((ItemLike)Items.MANGROVE_CHEST_BOAT, new BoatDispenseItemBehavior(Boat.Type.MANGROVE, true));
/* 382 */     DispenserBlock.registerBehavior((ItemLike)Items.BAMBOO_CHEST_RAFT, new BoatDispenseItemBehavior(Boat.Type.BAMBOO, true));
/*     */     
/* 384 */     DispenseItemBehavior $$3 = new DefaultDispenseItemBehavior() {
/* 385 */         private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
/*     */ 
/*     */         
/*     */         public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 389 */           DispensibleContainerItem $$2 = (DispensibleContainerItem)$$1.getItem();
/* 390 */           BlockPos $$3 = $$0.pos().relative((Direction)$$0.state().getValue((Property)DispenserBlock.FACING));
/*     */           
/* 392 */           ServerLevel serverLevel = $$0.level();
/* 393 */           if ($$2.emptyContents(null, (Level)serverLevel, $$3, null)) {
/* 394 */             $$2.checkExtraContent(null, (Level)serverLevel, $$1, $$3);
/* 395 */             return new ItemStack((ItemLike)Items.BUCKET);
/*     */           } 
/*     */           
/* 398 */           return this.defaultDispenseItemBehavior.dispense($$0, $$1);
/*     */         }
/*     */       };
/* 401 */     DispenserBlock.registerBehavior((ItemLike)Items.LAVA_BUCKET, $$3);
/* 402 */     DispenserBlock.registerBehavior((ItemLike)Items.WATER_BUCKET, $$3);
/* 403 */     DispenserBlock.registerBehavior((ItemLike)Items.POWDER_SNOW_BUCKET, $$3);
/* 404 */     DispenserBlock.registerBehavior((ItemLike)Items.SALMON_BUCKET, $$3);
/* 405 */     DispenserBlock.registerBehavior((ItemLike)Items.COD_BUCKET, $$3);
/* 406 */     DispenserBlock.registerBehavior((ItemLike)Items.PUFFERFISH_BUCKET, $$3);
/* 407 */     DispenserBlock.registerBehavior((ItemLike)Items.TROPICAL_FISH_BUCKET, $$3);
/* 408 */     DispenserBlock.registerBehavior((ItemLike)Items.AXOLOTL_BUCKET, $$3);
/* 409 */     DispenserBlock.registerBehavior((ItemLike)Items.TADPOLE_BUCKET, $$3);
/*     */     
/* 411 */     DispenserBlock.registerBehavior((ItemLike)Items.BUCKET, new DefaultDispenseItemBehavior() {
/* 412 */           private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
/*     */           
/*     */           public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/*     */             Item $$8;
/* 416 */             ServerLevel serverLevel = $$0.level();
/*     */             
/* 418 */             BlockPos $$3 = $$0.pos().relative((Direction)$$0.state().getValue((Property)DispenserBlock.FACING));
/*     */             
/* 420 */             BlockState $$4 = serverLevel.getBlockState($$3);
/* 421 */             Block $$5 = $$4.getBlock();
/*     */ 
/*     */ 
/*     */             
/* 425 */             if ($$5 instanceof BucketPickup) { BucketPickup $$6 = (BucketPickup)$$5;
/* 426 */               ItemStack $$7 = $$6.pickupBlock(null, (LevelAccessor)serverLevel, $$3, $$4);
/* 427 */               if ($$7.isEmpty()) {
/* 428 */                 return super.execute($$0, $$1);
/*     */               }
/* 430 */               serverLevel.gameEvent(null, GameEvent.FLUID_PICKUP, $$3);
/* 431 */               $$8 = $$7.getItem(); }
/*     */             else
/* 433 */             { return super.execute($$0, $$1); }
/*     */ 
/*     */             
/* 436 */             $$1.shrink(1);
/* 437 */             if ($$1.isEmpty())
/* 438 */               return new ItemStack((ItemLike)$$8); 
/* 439 */             if ($$0.blockEntity().addItem(new ItemStack((ItemLike)$$8)) < 0) {
/* 440 */               this.defaultDispenseItemBehavior.dispense($$0, new ItemStack((ItemLike)$$8));
/*     */             }
/* 442 */             return $$1;
/*     */           }
/*     */         });
/*     */     
/* 446 */     DispenserBlock.registerBehavior((ItemLike)Items.FLINT_AND_STEEL, new OptionalDispenseItemBehavior()
/*     */         {
/*     */           protected ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 449 */             ServerLevel serverLevel = $$0.level();
/*     */             
/* 451 */             setSuccess(true);
/*     */             
/* 453 */             Direction $$3 = (Direction)$$0.state().getValue((Property)DispenserBlock.FACING);
/* 454 */             BlockPos $$4 = $$0.pos().relative($$3);
/* 455 */             BlockState $$5 = serverLevel.getBlockState($$4);
/* 456 */             if (BaseFireBlock.canBePlacedAt((Level)serverLevel, $$4, $$3)) {
/* 457 */               serverLevel.setBlockAndUpdate($$4, BaseFireBlock.getState((BlockGetter)serverLevel, $$4));
/* 458 */               serverLevel.gameEvent(null, GameEvent.BLOCK_PLACE, $$4);
/* 459 */             } else if (CampfireBlock.canLight($$5) || CandleBlock.canLight($$5) || CandleCakeBlock.canLight($$5)) {
/* 460 */               serverLevel.setBlockAndUpdate($$4, (BlockState)$$5.setValue((Property)BlockStateProperties.LIT, Boolean.valueOf(true)));
/* 461 */               serverLevel.gameEvent(null, GameEvent.BLOCK_CHANGE, $$4);
/* 462 */             } else if ($$5.getBlock() instanceof TntBlock) {
/* 463 */               TntBlock.explode((Level)serverLevel, $$4);
/* 464 */               serverLevel.removeBlock($$4, false);
/*     */             } else {
/* 466 */               setSuccess(false);
/*     */             } 
/*     */             
/* 469 */             if (isSuccess() && 
/* 470 */               $$1.hurt(1, ((Level)serverLevel).random, null)) {
/* 471 */               $$1.setCount(0);
/*     */             }
/*     */ 
/*     */             
/* 475 */             return $$1;
/*     */           }
/*     */         });
/*     */     
/* 479 */     DispenserBlock.registerBehavior((ItemLike)Items.BONE_MEAL, new OptionalDispenseItemBehavior()
/*     */         {
/*     */           protected ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 482 */             setSuccess(true);
/* 483 */             ServerLevel serverLevel = $$0.level();
/*     */             
/* 485 */             BlockPos $$3 = $$0.pos().relative((Direction)$$0.state().getValue((Property)DispenserBlock.FACING));
/* 486 */             if (BoneMealItem.growCrop($$1, (Level)serverLevel, $$3) || BoneMealItem.growWaterPlant($$1, (Level)serverLevel, $$3, null)) {
/* 487 */               if (!((Level)serverLevel).isClientSide) {
/* 488 */                 serverLevel.levelEvent(1505, $$3, 0);
/*     */               }
/*     */             } else {
/* 491 */               setSuccess(false);
/*     */             } 
/*     */             
/* 494 */             return $$1;
/*     */           }
/*     */         });
/*     */     
/* 498 */     DispenserBlock.registerBehavior((ItemLike)Blocks.TNT, new DefaultDispenseItemBehavior()
/*     */         {
/*     */           protected ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 501 */             ServerLevel serverLevel = $$0.level();
/* 502 */             BlockPos $$3 = $$0.pos().relative((Direction)$$0.state().getValue((Property)DispenserBlock.FACING));
/*     */             
/* 504 */             PrimedTnt $$4 = new PrimedTnt((Level)serverLevel, $$3.getX() + 0.5D, $$3.getY(), $$3.getZ() + 0.5D, null);
/* 505 */             serverLevel.addFreshEntity((Entity)$$4);
/* 506 */             serverLevel.playSound(null, $$4.getX(), $$4.getY(), $$4.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 507 */             serverLevel.gameEvent(null, GameEvent.ENTITY_PLACE, $$3);
/*     */             
/* 509 */             $$1.shrink(1);
/* 510 */             return $$1;
/*     */           }
/*     */         });
/*     */     
/* 514 */     DispenseItemBehavior $$4 = new OptionalDispenseItemBehavior()
/*     */       {
/*     */         protected ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 517 */           setSuccess(ArmorItem.dispenseArmor($$0, $$1));
/* 518 */           return $$1;
/*     */         }
/*     */       };
/* 521 */     DispenserBlock.registerBehavior((ItemLike)Items.CREEPER_HEAD, $$4);
/* 522 */     DispenserBlock.registerBehavior((ItemLike)Items.ZOMBIE_HEAD, $$4);
/* 523 */     DispenserBlock.registerBehavior((ItemLike)Items.DRAGON_HEAD, $$4);
/* 524 */     DispenserBlock.registerBehavior((ItemLike)Items.SKELETON_SKULL, $$4);
/* 525 */     DispenserBlock.registerBehavior((ItemLike)Items.PIGLIN_HEAD, $$4);
/* 526 */     DispenserBlock.registerBehavior((ItemLike)Items.PLAYER_HEAD, $$4);
/* 527 */     DispenserBlock.registerBehavior((ItemLike)Items.WITHER_SKELETON_SKULL, new OptionalDispenseItemBehavior()
/*     */         {
/*     */           protected ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 530 */             ServerLevel serverLevel = $$0.level();
/* 531 */             Direction $$3 = (Direction)$$0.state().getValue((Property)DispenserBlock.FACING);
/* 532 */             BlockPos $$4 = $$0.pos().relative($$3);
/*     */             
/* 534 */             if (serverLevel.isEmptyBlock($$4) && WitherSkullBlock.canSpawnMob((Level)serverLevel, $$4, $$1)) {
/* 535 */               serverLevel.setBlock($$4, (BlockState)Blocks.WITHER_SKELETON_SKULL.defaultBlockState().setValue((Property)SkullBlock.ROTATION, Integer.valueOf(RotationSegment.convertToSegment($$3))), 3);
/* 536 */               serverLevel.gameEvent(null, GameEvent.BLOCK_PLACE, $$4);
/* 537 */               BlockEntity $$5 = serverLevel.getBlockEntity($$4);
/* 538 */               if ($$5 instanceof SkullBlockEntity) {
/* 539 */                 WitherSkullBlock.checkSpawn((Level)serverLevel, $$4, (SkullBlockEntity)$$5);
/*     */               }
/* 541 */               $$1.shrink(1);
/* 542 */               setSuccess(true);
/*     */             } else {
/* 544 */               setSuccess(ArmorItem.dispenseArmor($$0, $$1));
/*     */             } 
/* 546 */             return $$1;
/*     */           }
/*     */         });
/*     */     
/* 550 */     DispenserBlock.registerBehavior((ItemLike)Blocks.CARVED_PUMPKIN, new OptionalDispenseItemBehavior()
/*     */         {
/*     */           protected ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 553 */             ServerLevel serverLevel = $$0.level();
/* 554 */             BlockPos $$3 = $$0.pos().relative((Direction)$$0.state().getValue((Property)DispenserBlock.FACING));
/* 555 */             CarvedPumpkinBlock $$4 = (CarvedPumpkinBlock)Blocks.CARVED_PUMPKIN;
/*     */             
/* 557 */             if (serverLevel.isEmptyBlock($$3) && $$4.canSpawnGolem((LevelReader)serverLevel, $$3)) {
/* 558 */               if (!((Level)serverLevel).isClientSide) {
/* 559 */                 serverLevel.setBlock($$3, $$4.defaultBlockState(), 3);
/* 560 */                 serverLevel.gameEvent(null, GameEvent.BLOCK_PLACE, $$3);
/*     */               } 
/* 562 */               $$1.shrink(1);
/* 563 */               setSuccess(true);
/*     */             } else {
/* 565 */               setSuccess(ArmorItem.dispenseArmor($$0, $$1));
/*     */             } 
/* 567 */             return $$1;
/*     */           }
/*     */         });
/*     */     
/* 571 */     DispenserBlock.registerBehavior((ItemLike)Blocks.SHULKER_BOX.asItem(), new ShulkerBoxDispenseBehavior());
/* 572 */     for (DyeColor $$5 : DyeColor.values()) {
/* 573 */       DispenserBlock.registerBehavior((ItemLike)ShulkerBoxBlock.getBlockByColor($$5).asItem(), new ShulkerBoxDispenseBehavior());
/*     */     }
/*     */     
/* 576 */     DispenserBlock.registerBehavior((ItemLike)Items.GLASS_BOTTLE.asItem(), new OptionalDispenseItemBehavior() {
/* 577 */           private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
/*     */           
/*     */           private ItemStack takeLiquid(BlockSource $$0, ItemStack $$1, ItemStack $$2) {
/* 580 */             $$1.shrink(1);
/* 581 */             if ($$1.isEmpty()) {
/* 582 */               $$0.level().gameEvent(null, GameEvent.FLUID_PICKUP, $$0.pos());
/* 583 */               return $$2.copy();
/* 584 */             }  if ($$0.blockEntity().addItem($$2.copy()) < 0) {
/* 585 */               this.defaultDispenseItemBehavior.dispense($$0, $$2.copy());
/*     */             }
/* 587 */             return $$1;
/*     */           }
/*     */ 
/*     */           
/*     */           public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 592 */             setSuccess(false);
/* 593 */             ServerLevel $$2 = $$0.level();
/*     */             
/* 595 */             BlockPos $$3 = $$0.pos().relative((Direction)$$0.state().getValue((Property)DispenserBlock.FACING));
/*     */             
/* 597 */             BlockState $$4 = $$2.getBlockState($$3);
/*     */             
/* 599 */             if ($$4.is(BlockTags.BEEHIVES, $$0 -> ($$0.hasProperty((Property)BeehiveBlock.HONEY_LEVEL) && $$0.getBlock() instanceof BeehiveBlock)) && ((Integer)$$4.getValue((Property)BeehiveBlock.HONEY_LEVEL)).intValue() >= 5) {
/* 600 */               ((BeehiveBlock)$$4.getBlock()).releaseBeesAndResetHoneyLevel((Level)$$2, $$4, $$3, null, BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED);
/* 601 */               setSuccess(true);
/* 602 */               return takeLiquid($$0, $$1, new ItemStack((ItemLike)Items.HONEY_BOTTLE));
/* 603 */             }  if ($$2.getFluidState($$3).is(FluidTags.WATER)) {
/* 604 */               setSuccess(true);
/* 605 */               return takeLiquid($$0, $$1, PotionUtils.setPotion(new ItemStack((ItemLike)Items.POTION), Potions.WATER));
/*     */             } 
/* 607 */             return super.execute($$0, $$1);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 612 */     DispenserBlock.registerBehavior((ItemLike)Items.GLOWSTONE, new OptionalDispenseItemBehavior()
/*     */         {
/*     */           public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 615 */             Direction $$2 = (Direction)$$0.state().getValue((Property)DispenserBlock.FACING);
/* 616 */             BlockPos $$3 = $$0.pos().relative($$2);
/* 617 */             ServerLevel serverLevel = $$0.level();
/* 618 */             BlockState $$5 = serverLevel.getBlockState($$3);
/* 619 */             setSuccess(true);
/* 620 */             if ($$5.is(Blocks.RESPAWN_ANCHOR)) {
/* 621 */               if (((Integer)$$5.getValue((Property)RespawnAnchorBlock.CHARGE)).intValue() != 4) {
/* 622 */                 RespawnAnchorBlock.charge(null, (Level)serverLevel, $$3, $$5);
/* 623 */                 $$1.shrink(1);
/*     */               } else {
/* 625 */                 setSuccess(false);
/*     */               } 
/*     */               
/* 628 */               return $$1;
/*     */             } 
/* 630 */             return super.execute($$0, $$1);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 635 */     DispenserBlock.registerBehavior((ItemLike)Items.SHEARS.asItem(), new ShearsDispenseItemBehavior());
/*     */     
/* 637 */     DispenserBlock.registerBehavior((ItemLike)Items.HONEYCOMB, new OptionalDispenseItemBehavior()
/*     */         {
/*     */           public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 640 */             BlockPos $$2 = $$0.pos().relative((Direction)$$0.state().getValue((Property)DispenserBlock.FACING));
/* 641 */             ServerLevel serverLevel = $$0.level();
/* 642 */             BlockState $$4 = serverLevel.getBlockState($$2);
/*     */             
/* 644 */             Optional<BlockState> $$5 = HoneycombItem.getWaxed($$4);
/* 645 */             if ($$5.isPresent()) {
/* 646 */               serverLevel.setBlockAndUpdate($$2, $$5.get());
/* 647 */               serverLevel.levelEvent(3003, $$2, 0);
/* 648 */               $$1.shrink(1);
/* 649 */               setSuccess(true);
/*     */               
/* 651 */               return $$1;
/*     */             } 
/* 653 */             return super.execute($$0, $$1);
/*     */           }
/*     */         });
/*     */     
/* 657 */     DispenserBlock.registerBehavior((ItemLike)Items.POTION, new DefaultDispenseItemBehavior() {
/* 658 */           private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
/*     */ 
/*     */           
/*     */           public ItemStack execute(BlockSource $$0, ItemStack $$1) {
/* 662 */             if (PotionUtils.getPotion($$1) != Potions.WATER) {
/* 663 */               return this.defaultDispenseItemBehavior.dispense($$0, $$1);
/*     */             }
/*     */             
/* 666 */             ServerLevel $$2 = $$0.level();
/* 667 */             BlockPos $$3 = $$0.pos();
/*     */             
/* 669 */             BlockPos $$4 = $$0.pos().relative((Direction)$$0.state().getValue((Property)DispenserBlock.FACING));
/* 670 */             if ($$2.getBlockState($$4).is(BlockTags.CONVERTABLE_TO_MUD)) {
/* 671 */               if (!$$2.isClientSide) {
/* 672 */                 for (int $$5 = 0; $$5 < 5; $$5++) {
/* 673 */                   $$2.sendParticles((ParticleOptions)ParticleTypes.SPLASH, $$3.getX() + $$2.random.nextDouble(), ($$3.getY() + 1), $$3.getZ() + $$2.random.nextDouble(), 1, 0.0D, 0.0D, 0.0D, 1.0D);
/*     */                 }
/*     */               }
/*     */               
/* 677 */               $$2.playSound(null, $$3, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 678 */               $$2.gameEvent(null, GameEvent.FLUID_PLACE, $$3);
/*     */               
/* 680 */               $$2.setBlockAndUpdate($$4, Blocks.MUD.defaultBlockState());
/*     */               
/* 682 */               return new ItemStack((ItemLike)Items.GLASS_BOTTLE);
/*     */             } 
/*     */             
/* 685 */             return this.defaultDispenseItemBehavior.dispense($$0, $$1);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   static Vec3 getEntityPokingOutOfBlockPos(BlockSource $$0, EntityType<?> $$1, Direction $$2) {
/* 691 */     return $$0.center().add($$2
/* 692 */         .getStepX() * (0.5000099999997474D - $$1.getWidth() / 2.0D), $$2
/* 693 */         .getStepY() * (0.5000099999997474D - $$1.getHeight() / 2.0D) - $$1.getHeight() / 2.0D, $$2
/* 694 */         .getStepZ() * (0.5000099999997474D - $$1.getWidth() / 2.0D));
/*     */   }
/*     */   
/*     */   ItemStack dispense(BlockSource paramBlockSource, ItemStack paramItemStack);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\dispenser\DispenseItemBehavior.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */