/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.ClientRecipeBook;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
/*     */ import net.minecraft.client.multiplayer.prediction.PredictiveAction;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*     */ import net.minecraft.client.resources.sounds.SoundInstance;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ServerGamePacketListener;
/*     */ import net.minecraft.network.protocol.game.ServerboundContainerButtonClickPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundContainerSlotStateChangedPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundInteractPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundPickItemPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundPlaceRecipePacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundSetCreativeModeSlotPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.StatsCounter;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.ClickType;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.SoundType;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public class MultiPlayerGameMode
/*     */ {
/*  64 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final Minecraft minecraft;
/*     */   
/*     */   private final ClientPacketListener connection;
/*  69 */   private BlockPos destroyBlockPos = new BlockPos(-1, -1, -1);
/*  70 */   private ItemStack destroyingItem = ItemStack.EMPTY;
/*     */   private float destroyProgress;
/*     */   private float destroyTicks;
/*     */   private int destroyDelay;
/*     */   private boolean isDestroying;
/*  75 */   private GameType localPlayerMode = GameType.DEFAULT_MODE; @Nullable
/*     */   private GameType previousLocalPlayerMode;
/*     */   private int carriedIndex;
/*     */   
/*     */   public MultiPlayerGameMode(Minecraft $$0, ClientPacketListener $$1) {
/*  80 */     this.minecraft = $$0;
/*  81 */     this.connection = $$1;
/*     */   }
/*     */   
/*     */   public void adjustPlayer(Player $$0) {
/*  85 */     this.localPlayerMode.updatePlayerAbilities($$0.getAbilities());
/*     */   }
/*     */   
/*     */   public void setLocalMode(GameType $$0, @Nullable GameType $$1) {
/*  89 */     this.localPlayerMode = $$0;
/*  90 */     this.previousLocalPlayerMode = $$1;
/*  91 */     this.localPlayerMode.updatePlayerAbilities(this.minecraft.player.getAbilities());
/*     */   }
/*     */   
/*     */   public void setLocalMode(GameType $$0) {
/*  95 */     if ($$0 != this.localPlayerMode) {
/*  96 */       this.previousLocalPlayerMode = this.localPlayerMode;
/*     */     }
/*  98 */     this.localPlayerMode = $$0;
/*  99 */     this.localPlayerMode.updatePlayerAbilities(this.minecraft.player.getAbilities());
/*     */   }
/*     */   
/*     */   public boolean canHurtPlayer() {
/* 103 */     return this.localPlayerMode.isSurvival();
/*     */   }
/*     */   
/*     */   public boolean destroyBlock(BlockPos $$0) {
/* 107 */     if (this.minecraft.player.blockActionRestricted(this.minecraft.level, $$0, this.localPlayerMode)) {
/* 108 */       return false;
/*     */     }
/*     */     
/* 111 */     Level $$1 = this.minecraft.level;
/* 112 */     BlockState $$2 = $$1.getBlockState($$0);
/* 113 */     if (!this.minecraft.player.getMainHandItem().getItem().canAttackBlock($$2, $$1, $$0, (Player)this.minecraft.player)) {
/* 114 */       return false;
/*     */     }
/*     */     
/* 117 */     Block $$3 = $$2.getBlock();
/*     */     
/* 119 */     if ($$3 instanceof net.minecraft.world.level.block.GameMasterBlock && !this.minecraft.player.canUseGameMasterBlocks()) {
/* 120 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 124 */     if ($$2.isAir()) {
/* 125 */       return false;
/*     */     }
/*     */     
/* 128 */     $$3.playerWillDestroy($$1, $$0, $$2, (Player)this.minecraft.player);
/* 129 */     FluidState $$4 = $$1.getFluidState($$0);
/*     */     
/* 131 */     boolean $$5 = $$1.setBlock($$0, $$4.createLegacyBlock(), 11);
/* 132 */     if ($$5) {
/* 133 */       $$3.destroy((LevelAccessor)$$1, $$0, $$2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     return $$5;
/*     */   }
/*     */   
/*     */   public boolean startDestroyBlock(BlockPos $$0, Direction $$1) {
/* 143 */     if (this.minecraft.player.blockActionRestricted(this.minecraft.level, $$0, this.localPlayerMode)) {
/* 144 */       return false;
/*     */     }
/*     */     
/* 147 */     if (!this.minecraft.level.getWorldBorder().isWithinBounds($$0)) {
/* 148 */       return false;
/*     */     }
/*     */     
/* 151 */     if (this.localPlayerMode.isCreative()) {
/* 152 */       BlockState $$2 = this.minecraft.level.getBlockState($$0);
/* 153 */       this.minecraft.getTutorial().onDestroyBlock(this.minecraft.level, $$0, $$2, 1.0F);
/*     */ 
/*     */ 
/*     */       
/* 157 */       startPrediction(this.minecraft.level, $$2 -> {
/*     */             destroyBlock($$0);
/*     */             
/*     */             return (Packet)new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, $$0, $$1, $$2);
/*     */           });
/* 162 */       this.destroyDelay = 5;
/* 163 */     } else if (!this.isDestroying || !sameDestroyTarget($$0)) {
/* 164 */       if (this.isDestroying)
/*     */       {
/*     */ 
/*     */         
/* 168 */         this.connection.send((Packet<?>)new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK, this.destroyBlockPos, $$1));
/*     */       }
/* 170 */       BlockState $$3 = this.minecraft.level.getBlockState($$0);
/* 171 */       this.minecraft.getTutorial().onDestroyBlock(this.minecraft.level, $$0, $$3, 0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 176 */       startPrediction(this.minecraft.level, $$3 -> {
/*     */             boolean $$4 = !$$0.isAir();
/*     */             if ($$4 && this.destroyProgress == 0.0F) {
/*     */               $$0.attack(this.minecraft.level, $$1, (Player)this.minecraft.player);
/*     */             }
/*     */             if ($$4 && $$0.getDestroyProgress((Player)this.minecraft.player, (BlockGetter)this.minecraft.player.level(), $$1) >= 1.0F) {
/*     */               destroyBlock($$1);
/*     */             } else {
/*     */               this.isDestroying = true;
/*     */               this.destroyBlockPos = $$1;
/*     */               this.destroyingItem = this.minecraft.player.getMainHandItem();
/*     */               this.destroyProgress = 0.0F;
/*     */               this.destroyTicks = 0.0F;
/*     */               this.minecraft.level.destroyBlockProgress(this.minecraft.player.getId(), this.destroyBlockPos, getDestroyStage());
/*     */             } 
/*     */             return (Packet)new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, $$1, $$2, $$3);
/*     */           });
/*     */     } 
/* 194 */     return true;
/*     */   }
/*     */   
/*     */   public void stopDestroyBlock() {
/* 198 */     if (this.isDestroying) {
/* 199 */       BlockState $$0 = this.minecraft.level.getBlockState(this.destroyBlockPos);
/* 200 */       this.minecraft.getTutorial().onDestroyBlock(this.minecraft.level, this.destroyBlockPos, $$0, -1.0F);
/*     */ 
/*     */ 
/*     */       
/* 204 */       this.connection.send((Packet<?>)new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK, this.destroyBlockPos, Direction.DOWN));
/* 205 */       this.isDestroying = false;
/* 206 */       this.destroyProgress = 0.0F;
/* 207 */       this.minecraft.level.destroyBlockProgress(this.minecraft.player.getId(), this.destroyBlockPos, -1);
/* 208 */       this.minecraft.player.resetAttackStrengthTicker();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean continueDestroyBlock(BlockPos $$0, Direction $$1) {
/* 213 */     ensureHasSentCarriedItem();
/*     */     
/* 215 */     if (this.destroyDelay > 0) {
/* 216 */       this.destroyDelay--;
/* 217 */       return true;
/*     */     } 
/*     */     
/* 220 */     if (this.localPlayerMode.isCreative() && this.minecraft.level.getWorldBorder().isWithinBounds($$0)) {
/* 221 */       this.destroyDelay = 5;
/* 222 */       BlockState $$2 = this.minecraft.level.getBlockState($$0);
/* 223 */       this.minecraft.getTutorial().onDestroyBlock(this.minecraft.level, $$0, $$2, 1.0F);
/*     */ 
/*     */ 
/*     */       
/* 227 */       startPrediction(this.minecraft.level, $$2 -> {
/*     */             destroyBlock($$0);
/*     */             return (Packet)new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK, $$0, $$1, $$2);
/*     */           });
/* 231 */       return true;
/*     */     } 
/*     */     
/* 234 */     if (sameDestroyTarget($$0)) {
/* 235 */       BlockState $$3 = this.minecraft.level.getBlockState($$0);
/*     */       
/* 237 */       if ($$3.isAir()) {
/* 238 */         this.isDestroying = false;
/* 239 */         return false;
/*     */       } 
/*     */       
/* 242 */       this.destroyProgress += $$3.getDestroyProgress((Player)this.minecraft.player, (BlockGetter)this.minecraft.player.level(), $$0);
/*     */       
/* 244 */       if (this.destroyTicks % 4.0F == 0.0F) {
/* 245 */         SoundType $$4 = $$3.getSoundType();
/* 246 */         this.minecraft.getSoundManager().play((SoundInstance)new SimpleSoundInstance($$4
/* 247 */               .getHitSound(), SoundSource.BLOCKS, ($$4.getVolume() + 1.0F) / 8.0F, $$4.getPitch() * 0.5F, SoundInstance.createUnseededRandom(), $$0));
/*     */       } 
/*     */ 
/*     */       
/* 251 */       this.destroyTicks++;
/*     */       
/* 253 */       this.minecraft.getTutorial().onDestroyBlock(this.minecraft.level, $$0, $$3, Mth.clamp(this.destroyProgress, 0.0F, 1.0F));
/* 254 */       if (this.destroyProgress >= 1.0F) {
/* 255 */         this.isDestroying = false;
/*     */ 
/*     */ 
/*     */         
/* 259 */         startPrediction(this.minecraft.level, $$2 -> {
/*     */               destroyBlock($$0);
/*     */               
/*     */               return (Packet)new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK, $$0, $$1, $$2);
/*     */             });
/* 264 */         this.destroyProgress = 0.0F;
/* 265 */         this.destroyTicks = 0.0F;
/* 266 */         this.destroyDelay = 5;
/*     */       } 
/*     */       
/* 269 */       this.minecraft.level.destroyBlockProgress(this.minecraft.player.getId(), this.destroyBlockPos, getDestroyStage());
/*     */     } else {
/* 271 */       return startDestroyBlock($$0, $$1);
/*     */     } 
/* 273 */     return true;
/*     */   }
/*     */   
/*     */   private void startPrediction(ClientLevel $$0, PredictiveAction $$1) {
/* 277 */     BlockStatePredictionHandler $$2 = $$0.getBlockStatePredictionHandler().startPredicting(); 
/* 278 */     try { int $$3 = $$2.currentSequence();
/* 279 */       Packet<ServerGamePacketListener> $$4 = $$1.predict($$3);
/* 280 */       this.connection.send($$4);
/* 281 */       if ($$2 != null) $$2.close();  } catch (Throwable throwable) { if ($$2 != null)
/*     */         try { $$2.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 285 */      } public float getPickRange() { return Player.getPickRange(this.localPlayerMode.isCreative()); }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 289 */     ensureHasSentCarriedItem();
/*     */     
/* 291 */     if (this.connection.getConnection().isConnected()) {
/* 292 */       this.connection.getConnection().tick();
/*     */     } else {
/* 294 */       this.connection.getConnection().handleDisconnection();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean sameDestroyTarget(BlockPos $$0) {
/* 301 */     ItemStack $$1 = this.minecraft.player.getMainHandItem();
/* 302 */     return ($$0.equals(this.destroyBlockPos) && ItemStack.isSameItemSameTags($$1, this.destroyingItem));
/*     */   }
/*     */   
/*     */   private void ensureHasSentCarriedItem() {
/* 306 */     int $$0 = (this.minecraft.player.getInventory()).selected;
/* 307 */     if ($$0 != this.carriedIndex) {
/* 308 */       this.carriedIndex = $$0;
/* 309 */       this.connection.send((Packet<?>)new ServerboundSetCarriedItemPacket(this.carriedIndex));
/*     */     } 
/*     */   }
/*     */   
/*     */   public InteractionResult useItemOn(LocalPlayer $$0, InteractionHand $$1, BlockHitResult $$2) {
/* 314 */     ensureHasSentCarriedItem();
/*     */     
/* 316 */     if (!this.minecraft.level.getWorldBorder().isWithinBounds($$2.getBlockPos())) {
/* 317 */       return InteractionResult.FAIL;
/*     */     }
/*     */     
/* 320 */     MutableObject<InteractionResult> $$3 = new MutableObject();
/* 321 */     startPrediction(this.minecraft.level, $$4 -> {
/*     */           $$0.setValue(performUseItemOn($$1, $$2, $$3));
/*     */           
/*     */           return (Packet)new ServerboundUseItemOnPacket($$2, $$3, $$4);
/*     */         });
/* 326 */     return (InteractionResult)$$3.getValue();
/*     */   }
/*     */   private InteractionResult performUseItemOn(LocalPlayer $$0, InteractionHand $$1, BlockHitResult $$2) {
/*     */     InteractionResult $$12;
/* 330 */     BlockPos $$3 = $$2.getBlockPos();
/* 331 */     ItemStack $$4 = $$0.getItemInHand($$1);
/* 332 */     if (this.localPlayerMode == GameType.SPECTATOR) {
/* 333 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */ 
/*     */     
/* 337 */     boolean $$5 = (!$$0.getMainHandItem().isEmpty() || !$$0.getOffhandItem().isEmpty());
/* 338 */     boolean $$6 = ($$0.isSecondaryUseActive() && $$5);
/*     */     
/* 340 */     if (!$$6) {
/* 341 */       BlockState $$7 = this.minecraft.level.getBlockState($$3);
/* 342 */       if (!this.connection.isFeatureEnabled($$7.getBlock().requiredFeatures())) {
/* 343 */         return InteractionResult.FAIL;
/*     */       }
/* 345 */       InteractionResult $$8 = $$7.use(this.minecraft.level, (Player)$$0, $$1, $$2);
/* 346 */       if ($$8.consumesAction()) {
/* 347 */         return $$8;
/*     */       }
/*     */     } 
/*     */     
/* 351 */     if ($$4.isEmpty() || $$0.getCooldowns().isOnCooldown($$4.getItem())) {
/* 352 */       return InteractionResult.PASS;
/*     */     }
/*     */ 
/*     */     
/* 356 */     UseOnContext $$9 = new UseOnContext((Player)$$0, $$1, $$2);
/*     */     
/* 358 */     if (this.localPlayerMode.isCreative()) {
/*     */       
/* 360 */       int $$10 = $$4.getCount();
/* 361 */       InteractionResult $$11 = $$4.useOn($$9);
/* 362 */       $$4.setCount($$10);
/*     */     } else {
/* 364 */       $$12 = $$4.useOn($$9);
/*     */     } 
/* 366 */     return $$12;
/*     */   }
/*     */   
/*     */   public InteractionResult useItem(Player $$0, InteractionHand $$1) {
/* 370 */     if (this.localPlayerMode == GameType.SPECTATOR) {
/* 371 */       return InteractionResult.PASS;
/*     */     }
/* 373 */     ensureHasSentCarriedItem();
/*     */     
/* 375 */     this.connection.send((Packet<?>)new ServerboundMovePlayerPacket.PosRot($$0.getX(), $$0.getY(), $$0.getZ(), $$0.getYRot(), $$0.getXRot(), $$0.onGround()));
/* 376 */     MutableObject<InteractionResult> $$2 = new MutableObject();
/* 377 */     startPrediction(this.minecraft.level, $$3 -> {
/*     */           ServerboundUseItemPacket $$4 = new ServerboundUseItemPacket($$0, $$3);
/*     */           
/*     */           ItemStack $$5 = $$1.getItemInHand($$0);
/*     */           
/*     */           if ($$1.getCooldowns().isOnCooldown($$5.getItem())) {
/*     */             $$2.setValue(InteractionResult.PASS);
/*     */             
/*     */             return (Packet)$$4;
/*     */           } 
/*     */           InteractionResultHolder<ItemStack> $$6 = $$5.use(this.minecraft.level, $$1, $$0);
/*     */           ItemStack $$7 = (ItemStack)$$6.getObject();
/*     */           if ($$7 != $$5) {
/*     */             $$1.setItemInHand($$0, $$7);
/*     */           }
/*     */           $$2.setValue($$6.getResult());
/*     */           return (Packet)$$4;
/*     */         });
/* 395 */     return (InteractionResult)$$2.getValue();
/*     */   }
/*     */   
/*     */   public LocalPlayer createPlayer(ClientLevel $$0, StatsCounter $$1, ClientRecipeBook $$2) {
/* 399 */     return createPlayer($$0, $$1, $$2, false, false);
/*     */   }
/*     */   
/*     */   public LocalPlayer createPlayer(ClientLevel $$0, StatsCounter $$1, ClientRecipeBook $$2, boolean $$3, boolean $$4) {
/* 403 */     return new LocalPlayer(this.minecraft, $$0, this.connection, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public void attack(Player $$0, Entity $$1) {
/* 407 */     ensureHasSentCarriedItem();
/* 408 */     this.connection.send((Packet<?>)ServerboundInteractPacket.createAttackPacket($$1, $$0.isShiftKeyDown()));
/* 409 */     if (this.localPlayerMode != GameType.SPECTATOR) {
/* 410 */       $$0.attack($$1);
/* 411 */       $$0.resetAttackStrengthTicker();
/*     */     } 
/*     */   }
/*     */   
/*     */   public InteractionResult interact(Player $$0, Entity $$1, InteractionHand $$2) {
/* 416 */     ensureHasSentCarriedItem();
/* 417 */     this.connection.send((Packet<?>)ServerboundInteractPacket.createInteractionPacket($$1, $$0.isShiftKeyDown(), $$2));
/* 418 */     if (this.localPlayerMode == GameType.SPECTATOR) {
/* 419 */       return InteractionResult.PASS;
/*     */     }
/* 421 */     return $$0.interactOn($$1, $$2);
/*     */   }
/*     */   
/*     */   public InteractionResult interactAt(Player $$0, Entity $$1, EntityHitResult $$2, InteractionHand $$3) {
/* 425 */     ensureHasSentCarriedItem();
/* 426 */     Vec3 $$4 = $$2.getLocation().subtract($$1.getX(), $$1.getY(), $$1.getZ());
/* 427 */     this.connection.send((Packet<?>)ServerboundInteractPacket.createInteractionPacket($$1, $$0.isShiftKeyDown(), $$3, $$4));
/* 428 */     if (this.localPlayerMode == GameType.SPECTATOR) {
/* 429 */       return InteractionResult.PASS;
/*     */     }
/* 431 */     return $$1.interactAt($$0, $$4, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleInventoryMouseClick(int $$0, int $$1, int $$2, ClickType $$3, Player $$4) {
/* 436 */     AbstractContainerMenu $$5 = $$4.containerMenu;
/* 437 */     if ($$0 != $$5.containerId) {
/* 438 */       LOGGER.warn("Ignoring click in mismatching container. Click in {}, player has {}.", Integer.valueOf($$0), Integer.valueOf($$5.containerId));
/*     */       return;
/*     */     } 
/* 441 */     NonNullList<Slot> $$6 = $$5.slots;
/* 442 */     int $$7 = $$6.size();
/* 443 */     List<ItemStack> $$8 = Lists.newArrayListWithCapacity($$7);
/* 444 */     for (Slot $$9 : $$6) {
/* 445 */       $$8.add($$9.getItem().copy());
/*     */     }
/*     */     
/* 448 */     $$5.clicked($$1, $$2, $$3, $$4);
/*     */     
/* 450 */     Int2ObjectOpenHashMap int2ObjectOpenHashMap = new Int2ObjectOpenHashMap();
/* 451 */     for (int $$11 = 0; $$11 < $$7; $$11++) {
/* 452 */       ItemStack $$12 = $$8.get($$11);
/* 453 */       ItemStack $$13 = ((Slot)$$6.get($$11)).getItem();
/* 454 */       if (!ItemStack.matches($$12, $$13)) {
/* 455 */         int2ObjectOpenHashMap.put($$11, $$13.copy());
/*     */       }
/*     */     } 
/*     */     
/* 459 */     this.connection.send((Packet<?>)new ServerboundContainerClickPacket($$0, $$5.getStateId(), $$1, $$2, $$3, $$5.getCarried().copy(), (Int2ObjectMap)int2ObjectOpenHashMap));
/*     */   }
/*     */   
/*     */   public void handlePlaceRecipe(int $$0, RecipeHolder<?> $$1, boolean $$2) {
/* 463 */     this.connection.send((Packet<?>)new ServerboundPlaceRecipePacket($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   public void handleInventoryButtonClick(int $$0, int $$1) {
/* 467 */     this.connection.send((Packet<?>)new ServerboundContainerButtonClickPacket($$0, $$1));
/*     */   }
/*     */   
/*     */   public void handleCreativeModeItemAdd(ItemStack $$0, int $$1) {
/* 471 */     if (this.localPlayerMode.isCreative() && this.connection.isFeatureEnabled($$0.getItem().requiredFeatures())) {
/* 472 */       this.connection.send((Packet<?>)new ServerboundSetCreativeModeSlotPacket($$1, $$0));
/*     */     }
/*     */   }
/*     */   
/*     */   public void handleCreativeModeItemDrop(ItemStack $$0) {
/* 477 */     if (this.localPlayerMode.isCreative() && !$$0.isEmpty() && this.connection.isFeatureEnabled($$0.getItem().requiredFeatures())) {
/* 478 */       this.connection.send((Packet<?>)new ServerboundSetCreativeModeSlotPacket(-1, $$0));
/*     */     }
/*     */   }
/*     */   
/*     */   public void releaseUsingItem(Player $$0) {
/* 483 */     ensureHasSentCarriedItem();
/* 484 */     this.connection.send((Packet<?>)new ServerboundPlayerActionPacket(ServerboundPlayerActionPacket.Action.RELEASE_USE_ITEM, BlockPos.ZERO, Direction.DOWN));
/* 485 */     $$0.releaseUsingItem();
/*     */   }
/*     */   
/*     */   public boolean hasExperience() {
/* 489 */     return this.localPlayerMode.isSurvival();
/*     */   }
/*     */   
/*     */   public boolean hasMissTime() {
/* 493 */     return !this.localPlayerMode.isCreative();
/*     */   }
/*     */   
/*     */   public boolean hasInfiniteItems() {
/* 497 */     return this.localPlayerMode.isCreative();
/*     */   }
/*     */   
/*     */   public boolean hasFarPickRange() {
/* 501 */     return this.localPlayerMode.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isServerControlledInventory() {
/* 510 */     return (this.minecraft.player.isPassenger() && this.minecraft.player.getVehicle() instanceof net.minecraft.world.entity.HasCustomInventoryScreen);
/*     */   }
/*     */   
/*     */   public boolean isAlwaysFlying() {
/* 514 */     return (this.localPlayerMode == GameType.SPECTATOR);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public GameType getPreviousPlayerMode() {
/* 519 */     return this.previousLocalPlayerMode;
/*     */   }
/*     */   
/*     */   public GameType getPlayerMode() {
/* 523 */     return this.localPlayerMode;
/*     */   }
/*     */   
/*     */   public boolean isDestroying() {
/* 527 */     return this.isDestroying;
/*     */   }
/*     */   
/*     */   public int getDestroyStage() {
/* 531 */     return (this.destroyProgress > 0.0F) ? (int)(this.destroyProgress * 10.0F) : -1;
/*     */   }
/*     */   
/*     */   public void handlePickItem(int $$0) {
/* 535 */     this.connection.send((Packet<?>)new ServerboundPickItemPacket($$0));
/*     */   }
/*     */   
/*     */   public void handleSlotStateChanged(int $$0, int $$1, boolean $$2) {
/* 539 */     this.connection.send((Packet<?>)new ServerboundContainerSlotStateChangedPacket($$0, $$1, $$2));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\MultiPlayerGameMode.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */