/*     */ package net.minecraft.server.level;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
/*     */ import net.minecraft.server.network.ServerGamePacketListenerImpl;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ServerPlayerGameMode {
/*  32 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   protected ServerLevel level;
/*     */   
/*     */   protected final ServerPlayer player;
/*  37 */   private GameType gameModeForPlayer = GameType.DEFAULT_MODE;
/*     */   
/*     */   @Nullable
/*     */   private GameType previousGameModeForPlayer;
/*     */   private boolean isDestroyingBlock;
/*     */   private int destroyProgressStart;
/*  43 */   private BlockPos destroyPos = BlockPos.ZERO;
/*     */   
/*     */   private int gameTicks;
/*     */   private boolean hasDelayedDestroy;
/*  47 */   private BlockPos delayedDestroyPos = BlockPos.ZERO;
/*     */   private int delayedTickStart;
/*  49 */   private int lastSentState = -1;
/*     */   
/*     */   public ServerPlayerGameMode(ServerPlayer $$0) {
/*  52 */     this.player = $$0;
/*  53 */     this.level = $$0.serverLevel();
/*     */   }
/*     */   
/*     */   public boolean changeGameModeForPlayer(GameType $$0) {
/*  57 */     if ($$0 == this.gameModeForPlayer) {
/*  58 */       return false;
/*     */     }
/*     */     
/*  61 */     setGameModeForPlayer($$0, this.previousGameModeForPlayer);
/*     */     
/*  63 */     this.player.onUpdateAbilities();
/*  64 */     this.player.server.getPlayerList().broadcastAll((Packet)new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.UPDATE_GAME_MODE, this.player));
/*  65 */     this.level.updateSleepingPlayerList();
/*     */     
/*  67 */     return true;
/*     */   }
/*     */   
/*     */   protected void setGameModeForPlayer(GameType $$0, @Nullable GameType $$1) {
/*  71 */     this.previousGameModeForPlayer = $$1;
/*  72 */     this.gameModeForPlayer = $$0;
/*     */     
/*  74 */     $$0.updatePlayerAbilities(this.player.getAbilities());
/*     */   }
/*     */   
/*     */   public GameType getGameModeForPlayer() {
/*  78 */     return this.gameModeForPlayer;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public GameType getPreviousGameModeForPlayer() {
/*  83 */     return this.previousGameModeForPlayer;
/*     */   }
/*     */   
/*     */   public boolean isSurvival() {
/*  87 */     return this.gameModeForPlayer.isSurvival();
/*     */   }
/*     */   
/*     */   public boolean isCreative() {
/*  91 */     return this.gameModeForPlayer.isCreative();
/*     */   }
/*     */   
/*     */   public void tick() {
/*  95 */     this.gameTicks++;
/*     */     
/*  97 */     if (this.hasDelayedDestroy) {
/*  98 */       BlockState $$0 = this.level.getBlockState(this.delayedDestroyPos);
/*  99 */       if ($$0.isAir()) {
/* 100 */         this.hasDelayedDestroy = false;
/*     */       } else {
/* 102 */         float $$1 = incrementDestroyProgress($$0, this.delayedDestroyPos, this.delayedTickStart);
/*     */         
/* 104 */         if ($$1 >= 1.0F) {
/* 105 */           this.hasDelayedDestroy = false;
/* 106 */           destroyBlock(this.delayedDestroyPos);
/*     */         } 
/*     */       } 
/* 109 */     } else if (this.isDestroyingBlock) {
/* 110 */       BlockState $$2 = this.level.getBlockState(this.destroyPos);
/*     */       
/* 112 */       if ($$2.isAir()) {
/* 113 */         this.level.destroyBlockProgress(this.player.getId(), this.destroyPos, -1);
/* 114 */         this.lastSentState = -1;
/* 115 */         this.isDestroyingBlock = false;
/*     */       } else {
/* 117 */         incrementDestroyProgress($$2, this.destroyPos, this.destroyProgressStart);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private float incrementDestroyProgress(BlockState $$0, BlockPos $$1, int $$2) {
/* 123 */     int $$3 = this.gameTicks - $$2;
/* 124 */     float $$4 = $$0.getDestroyProgress(this.player, (BlockGetter)this.player.level(), $$1) * ($$3 + 1);
/* 125 */     int $$5 = (int)($$4 * 10.0F);
/*     */     
/* 127 */     if ($$5 != this.lastSentState) {
/* 128 */       this.level.destroyBlockProgress(this.player.getId(), $$1, $$5);
/* 129 */       this.lastSentState = $$5;
/*     */     } 
/* 131 */     return $$4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void debugLogging(BlockPos $$0, boolean $$1, int $$2, String $$3) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleBlockBreakAction(BlockPos $$0, ServerboundPlayerActionPacket.Action $$1, Direction $$2, int $$3, int $$4) {
/* 142 */     if (this.player.getEyePosition().distanceToSqr(Vec3.atCenterOf((Vec3i)$$0)) > ServerGamePacketListenerImpl.MAX_INTERACTION_DISTANCE) {
/* 143 */       debugLogging($$0, false, $$4, "too far");
/*     */       return;
/*     */     } 
/* 146 */     if ($$0.getY() >= $$3) {
/* 147 */       this.player.connection.send((Packet)new ClientboundBlockUpdatePacket($$0, this.level.getBlockState($$0)));
/* 148 */       debugLogging($$0, false, $$4, "too high");
/*     */       
/*     */       return;
/*     */     } 
/* 152 */     if ($$1 == ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK) {
/* 153 */       if (!this.level.mayInteract(this.player, $$0)) {
/* 154 */         this.player.connection.send((Packet)new ClientboundBlockUpdatePacket($$0, this.level.getBlockState($$0)));
/* 155 */         debugLogging($$0, false, $$4, "may not interact");
/*     */         return;
/*     */       } 
/* 158 */       if (isCreative()) {
/* 159 */         destroyAndAck($$0, $$4, "creative destroy");
/*     */         
/*     */         return;
/*     */       } 
/* 163 */       if (this.player.blockActionRestricted(this.level, $$0, this.gameModeForPlayer)) {
/* 164 */         this.player.connection.send((Packet)new ClientboundBlockUpdatePacket($$0, this.level.getBlockState($$0)));
/* 165 */         debugLogging($$0, false, $$4, "block action restricted");
/*     */         
/*     */         return;
/*     */       } 
/* 169 */       this.destroyProgressStart = this.gameTicks;
/* 170 */       float $$5 = 1.0F;
/* 171 */       BlockState $$6 = this.level.getBlockState($$0);
/* 172 */       if (!$$6.isAir()) {
/* 173 */         $$6.attack(this.level, $$0, this.player);
/* 174 */         $$5 = $$6.getDestroyProgress(this.player, (BlockGetter)this.player.level(), $$0);
/*     */       } 
/*     */       
/* 177 */       if (!$$6.isAir() && $$5 >= 1.0F) {
/* 178 */         destroyAndAck($$0, $$4, "insta mine");
/*     */       } else {
/* 180 */         if (this.isDestroyingBlock) {
/* 181 */           this.player.connection.send((Packet)new ClientboundBlockUpdatePacket(this.destroyPos, this.level.getBlockState(this.destroyPos)));
/* 182 */           debugLogging($$0, false, $$4, "abort destroying since another started (client insta mine, server disagreed)");
/*     */         } 
/* 184 */         this.isDestroyingBlock = true;
/* 185 */         this.destroyPos = $$0.immutable();
/*     */         
/* 187 */         int $$7 = (int)($$5 * 10.0F);
/* 188 */         this.level.destroyBlockProgress(this.player.getId(), $$0, $$7);
/* 189 */         debugLogging($$0, true, $$4, "actual start of destroying");
/* 190 */         this.lastSentState = $$7;
/*     */       } 
/* 192 */     } else if ($$1 == ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK) {
/*     */       
/* 194 */       if ($$0.equals(this.destroyPos)) {
/* 195 */         int $$8 = this.gameTicks - this.destroyProgressStart;
/*     */         
/* 197 */         BlockState $$9 = this.level.getBlockState($$0);
/* 198 */         if (!$$9.isAir()) {
/* 199 */           float $$10 = $$9.getDestroyProgress(this.player, (BlockGetter)this.player.level(), $$0) * ($$8 + 1);
/* 200 */           if ($$10 >= 0.7F) {
/* 201 */             this.isDestroyingBlock = false;
/* 202 */             this.level.destroyBlockProgress(this.player.getId(), $$0, -1);
/* 203 */             destroyAndAck($$0, $$4, "destroyed"); return;
/*     */           } 
/* 205 */           if (!this.hasDelayedDestroy) {
/* 206 */             this.isDestroyingBlock = false;
/* 207 */             this.hasDelayedDestroy = true;
/* 208 */             this.delayedDestroyPos = $$0;
/* 209 */             this.delayedTickStart = this.destroyProgressStart;
/*     */           } 
/*     */         } 
/*     */       } 
/* 213 */       debugLogging($$0, true, $$4, "stopped destroying");
/* 214 */     } else if ($$1 == ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK) {
/* 215 */       this.isDestroyingBlock = false;
/* 216 */       if (!Objects.equals(this.destroyPos, $$0)) {
/* 217 */         LOGGER.warn("Mismatch in destroy block pos: {} {}", this.destroyPos, $$0);
/* 218 */         this.level.destroyBlockProgress(this.player.getId(), this.destroyPos, -1);
/* 219 */         debugLogging($$0, true, $$4, "aborted mismatched destroying");
/*     */       } 
/*     */       
/* 222 */       this.level.destroyBlockProgress(this.player.getId(), $$0, -1);
/* 223 */       debugLogging($$0, true, $$4, "aborted destroying");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void destroyAndAck(BlockPos $$0, int $$1, String $$2) {
/* 228 */     if (destroyBlock($$0)) {
/* 229 */       debugLogging($$0, true, $$1, $$2);
/*     */     } else {
/* 231 */       this.player.connection.send((Packet)new ClientboundBlockUpdatePacket($$0, this.level.getBlockState($$0)));
/* 232 */       debugLogging($$0, false, $$1, $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean destroyBlock(BlockPos $$0) {
/* 241 */     BlockState $$1 = this.level.getBlockState($$0);
/* 242 */     if (!this.player.getMainHandItem().getItem().canAttackBlock($$1, this.level, $$0, this.player)) {
/* 243 */       return false;
/*     */     }
/*     */     
/* 246 */     BlockEntity $$2 = this.level.getBlockEntity($$0);
/* 247 */     Block $$3 = $$1.getBlock();
/*     */ 
/*     */     
/* 250 */     if ($$3 instanceof net.minecraft.world.level.block.GameMasterBlock && !this.player.canUseGameMasterBlocks()) {
/* 251 */       this.level.sendBlockUpdated($$0, $$1, $$1, 3);
/* 252 */       return false;
/*     */     } 
/*     */     
/* 255 */     if (this.player.blockActionRestricted(this.level, $$0, this.gameModeForPlayer)) {
/* 256 */       return false;
/*     */     }
/*     */     
/* 259 */     BlockState $$4 = $$3.playerWillDestroy(this.level, $$0, $$1, this.player);
/*     */ 
/*     */     
/* 262 */     boolean $$5 = this.level.removeBlock($$0, false);
/*     */ 
/*     */ 
/*     */     
/* 266 */     if ($$5) {
/* 267 */       $$3.destroy((LevelAccessor)this.level, $$0, $$4);
/*     */     }
/*     */     
/* 270 */     if (isCreative()) {
/* 271 */       return true;
/*     */     }
/*     */     
/* 274 */     ItemStack $$6 = this.player.getMainHandItem();
/*     */     
/* 276 */     ItemStack $$7 = $$6.copy();
/* 277 */     boolean $$8 = this.player.hasCorrectToolForDrops($$4);
/* 278 */     $$6.mineBlock(this.level, $$4, $$0, this.player);
/* 279 */     if ($$5 && $$8) {
/* 280 */       $$3.playerDestroy(this.level, this.player, $$0, $$4, $$2, $$7);
/*     */     }
/* 282 */     return true;
/*     */   }
/*     */   
/*     */   public InteractionResult useItem(ServerPlayer $$0, Level $$1, ItemStack $$2, InteractionHand $$3) {
/* 286 */     if (this.gameModeForPlayer == GameType.SPECTATOR) {
/* 287 */       return InteractionResult.PASS;
/*     */     }
/* 289 */     if ($$0.getCooldowns().isOnCooldown($$2.getItem())) {
/* 290 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 293 */     int $$4 = $$2.getCount();
/* 294 */     int $$5 = $$2.getDamageValue();
/* 295 */     InteractionResultHolder<ItemStack> $$6 = $$2.use($$1, $$0, $$3);
/*     */     
/* 297 */     ItemStack $$7 = (ItemStack)$$6.getObject();
/* 298 */     if ($$7 == $$2 && $$7.getCount() == $$4 && $$7.getUseDuration() <= 0 && $$7.getDamageValue() == $$5) {
/* 299 */       return $$6.getResult();
/*     */     }
/*     */     
/* 302 */     if ($$6.getResult() == InteractionResult.FAIL && $$7.getUseDuration() > 0 && !$$0.isUsingItem()) {
/* 303 */       return $$6.getResult();
/*     */     }
/*     */ 
/*     */     
/* 307 */     if ($$2 != $$7) {
/* 308 */       $$0.setItemInHand($$3, $$7);
/*     */     }
/*     */     
/* 311 */     if (isCreative() && $$7 != ItemStack.EMPTY) {
/* 312 */       $$7.setCount($$4);
/* 313 */       if ($$7.isDamageableItem() && $$7.getDamageValue() != $$5) {
/* 314 */         $$7.setDamageValue($$5);
/*     */       }
/*     */     } 
/* 317 */     if ($$7.isEmpty()) {
/* 318 */       $$0.setItemInHand($$3, ItemStack.EMPTY);
/*     */     }
/* 320 */     if (!$$0.isUsingItem()) {
/* 321 */       $$0.inventoryMenu.sendAllDataToRemote();
/*     */     }
/* 323 */     return $$6.getResult();
/*     */   }
/*     */   public InteractionResult useItemOn(ServerPlayer $$0, Level $$1, ItemStack $$2, InteractionHand $$3, BlockHitResult $$4) {
/*     */     InteractionResult $$15;
/* 327 */     BlockPos $$5 = $$4.getBlockPos();
/*     */     
/* 329 */     BlockState $$6 = $$1.getBlockState($$5);
/* 330 */     if (!$$6.getBlock().isEnabled($$1.enabledFeatures())) {
/* 331 */       return InteractionResult.FAIL;
/*     */     }
/*     */     
/* 334 */     if (this.gameModeForPlayer == GameType.SPECTATOR) {
/* 335 */       MenuProvider $$7 = $$6.getMenuProvider($$1, $$5);
/* 336 */       if ($$7 != null) {
/* 337 */         $$0.openMenu($$7);
/* 338 */         return InteractionResult.SUCCESS;
/*     */       } 
/* 340 */       return InteractionResult.PASS;
/*     */     } 
/*     */     
/* 343 */     boolean $$8 = (!$$0.getMainHandItem().isEmpty() || !$$0.getOffhandItem().isEmpty());
/* 344 */     boolean $$9 = ($$0.isSecondaryUseActive() && $$8);
/* 345 */     ItemStack $$10 = $$2.copy();
/*     */     
/* 347 */     if (!$$9) {
/* 348 */       InteractionResult $$11 = $$6.use($$1, $$0, $$3, $$4);
/* 349 */       if ($$11.consumesAction()) {
/* 350 */         CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger($$0, $$5, $$10);
/*     */         
/* 352 */         return $$11;
/*     */       } 
/*     */     } 
/*     */     
/* 356 */     if ($$2.isEmpty() || $$0.getCooldowns().isOnCooldown($$2.getItem())) {
/* 357 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 360 */     UseOnContext $$12 = new UseOnContext($$0, $$3, $$4);
/*     */     
/* 362 */     if (isCreative()) {
/*     */       
/* 364 */       int $$13 = $$2.getCount();
/* 365 */       InteractionResult $$14 = $$2.useOn($$12);
/* 366 */       $$2.setCount($$13);
/*     */     } else {
/* 368 */       $$15 = $$2.useOn($$12);
/*     */     } 
/* 370 */     if ($$15.consumesAction()) {
/* 371 */       CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger($$0, $$5, $$10);
/*     */     }
/* 373 */     return $$15;
/*     */   }
/*     */   
/*     */   public void setLevel(ServerLevel $$0) {
/* 377 */     this.level = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ServerPlayerGameMode.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */