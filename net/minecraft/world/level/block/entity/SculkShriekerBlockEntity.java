/*     */ package net.minecraft.world.level.block.entity;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.Objects;
/*     */ import java.util.OptionalInt;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.GameEventTags;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.SpawnUtil;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.monster.warden.Warden;
/*     */ import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.SculkShriekerBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.BlockPositionSource;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.gameevent.GameEventListener;
/*     */ import net.minecraft.world.level.gameevent.PositionSource;
/*     */ import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SculkShriekerBlockEntity extends BlockEntity implements GameEventListener.Holder<VibrationSystem.Listener>, VibrationSystem {
/*  47 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final int WARNING_SOUND_RADIUS = 10;
/*     */   private static final int WARDEN_SPAWN_ATTEMPTS = 20;
/*     */   private static final int WARDEN_SPAWN_RANGE_XZ = 5;
/*     */   private static final int WARDEN_SPAWN_RANGE_Y = 6;
/*     */   private static final int DARKNESS_RADIUS = 40;
/*     */   
/*     */   static {
/*  55 */     SOUND_BY_LEVEL = (Int2ObjectMap<SoundEvent>)Util.make(new Int2ObjectOpenHashMap(), $$0 -> {
/*     */           $$0.put(1, SoundEvents.WARDEN_NEARBY_CLOSE);
/*     */           $$0.put(2, SoundEvents.WARDEN_NEARBY_CLOSER);
/*     */           $$0.put(3, SoundEvents.WARDEN_NEARBY_CLOSEST);
/*     */           $$0.put(4, SoundEvents.WARDEN_LISTENING_ANGRY);
/*     */         });
/*     */   }
/*     */   
/*     */   private static final int SHRIEKING_TICKS = 90;
/*     */   private static final Int2ObjectMap<SoundEvent> SOUND_BY_LEVEL;
/*     */   private int warningLevel;
/*     */   
/*     */   public SculkShriekerBlockEntity(BlockPos $$0, BlockState $$1) {
/*  68 */     super(BlockEntityType.SCULK_SHRIEKER, $$0, $$1);
/*  69 */     this.vibrationUser = new VibrationUser();
/*  70 */     this.vibrationData = new VibrationSystem.Data();
/*  71 */     this.vibrationListener = new VibrationSystem.Listener(this);
/*     */   }
/*     */   private final VibrationSystem.User vibrationUser; private VibrationSystem.Data vibrationData; private final VibrationSystem.Listener vibrationListener;
/*     */   
/*     */   public VibrationSystem.Data getVibrationData() {
/*  76 */     return this.vibrationData;
/*     */   }
/*     */ 
/*     */   
/*     */   public VibrationSystem.User getVibrationUser() {
/*  81 */     return this.vibrationUser;
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/*  86 */     super.load($$0);
/*     */     
/*  88 */     if ($$0.contains("warning_level", 99)) {
/*  89 */       this.warningLevel = $$0.getInt("warning_level");
/*     */     }
/*     */     
/*  92 */     if ($$0.contains("listener", 10)) {
/*     */ 
/*     */       
/*  95 */       Objects.requireNonNull(LOGGER); VibrationSystem.Data.CODEC.parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$0.getCompound("listener"))).resultOrPartial(LOGGER::error)
/*  96 */         .ifPresent($$0 -> this.vibrationData = $$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/* 102 */     super.saveAdditional($$0);
/*     */     
/* 104 */     $$0.putInt("warning_level", this.warningLevel);
/*     */ 
/*     */ 
/*     */     
/* 108 */     Objects.requireNonNull(LOGGER); VibrationSystem.Data.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.vibrationData).resultOrPartial(LOGGER::error)
/* 109 */       .ifPresent($$1 -> $$0.put("listener", $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static ServerPlayer tryGetPlayer(@Nullable Entity $$0) {
/* 115 */     if ($$0 instanceof ServerPlayer) { ServerPlayer $$1 = (ServerPlayer)$$0;
/* 116 */       return $$1; }
/*     */ 
/*     */ 
/*     */     
/* 120 */     if ($$0 != null) { LivingEntity livingEntity = $$0.getControllingPassenger(); if (livingEntity instanceof ServerPlayer) { ServerPlayer $$2 = (ServerPlayer)livingEntity;
/* 121 */         return $$2; }
/*     */        }
/*     */     
/* 124 */     if ($$0 instanceof Projectile) { Projectile $$3 = (Projectile)$$0; Entity entity = $$3.getOwner(); if (entity instanceof ServerPlayer) { ServerPlayer $$4 = (ServerPlayer)entity;
/* 125 */         return $$4; }
/*     */        }
/*     */     
/* 128 */     if ($$0 instanceof ItemEntity) { ItemEntity $$5 = (ItemEntity)$$0; Entity entity = $$5.getOwner(); if (entity instanceof ServerPlayer) { ServerPlayer $$6 = (ServerPlayer)entity;
/* 129 */         return $$6; }
/*     */        }
/*     */     
/* 132 */     return null;
/*     */   }
/*     */   
/*     */   public void tryShriek(ServerLevel $$0, @Nullable ServerPlayer $$1) {
/* 136 */     if ($$1 == null) {
/*     */       return;
/*     */     }
/*     */     
/* 140 */     BlockState $$2 = getBlockState();
/* 141 */     if (((Boolean)$$2.getValue((Property)SculkShriekerBlock.SHRIEKING)).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 145 */     this.warningLevel = 0;
/* 146 */     if (canRespond($$0) && !tryToWarn($$0, $$1)) {
/*     */       return;
/*     */     }
/*     */     
/* 150 */     shriek($$0, (Entity)$$1);
/*     */   }
/*     */   
/*     */   private boolean tryToWarn(ServerLevel $$0, ServerPlayer $$1) {
/* 154 */     OptionalInt $$2 = WardenSpawnTracker.tryWarn($$0, getBlockPos(), $$1);
/* 155 */     $$2.ifPresent($$0 -> this.warningLevel = $$0);
/* 156 */     return $$2.isPresent();
/*     */   }
/*     */   
/*     */   private void shriek(ServerLevel $$0, @Nullable Entity $$1) {
/* 160 */     BlockPos $$2 = getBlockPos();
/* 161 */     BlockState $$3 = getBlockState();
/* 162 */     $$0.setBlock($$2, (BlockState)$$3.setValue((Property)SculkShriekerBlock.SHRIEKING, Boolean.valueOf(true)), 2);
/* 163 */     $$0.scheduleTick($$2, $$3.getBlock(), 90);
/* 164 */     $$0.levelEvent(3007, $$2, 0);
/* 165 */     $$0.gameEvent(GameEvent.SHRIEK, $$2, GameEvent.Context.of($$1));
/*     */   }
/*     */   
/*     */   private boolean canRespond(ServerLevel $$0) {
/* 169 */     return (((Boolean)getBlockState().getValue((Property)SculkShriekerBlock.CAN_SUMMON)).booleanValue() && $$0
/* 170 */       .getDifficulty() != Difficulty.PEACEFUL && $$0
/* 171 */       .getGameRules().getBoolean(GameRules.RULE_DO_WARDEN_SPAWNING));
/*     */   }
/*     */   
/*     */   public void tryRespond(ServerLevel $$0) {
/* 175 */     if (canRespond($$0) && this.warningLevel > 0) {
/* 176 */       if (!trySummonWarden($$0)) {
/* 177 */         playWardenReplySound((Level)$$0);
/*     */       }
/*     */       
/* 180 */       Warden.applyDarknessAround($$0, Vec3.atCenterOf((Vec3i)getBlockPos()), null, 40);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void playWardenReplySound(Level $$0) {
/* 185 */     SoundEvent $$1 = (SoundEvent)SOUND_BY_LEVEL.get(this.warningLevel);
/* 186 */     if ($$1 != null) {
/* 187 */       BlockPos $$2 = getBlockPos();
/* 188 */       int $$3 = $$2.getX() + Mth.randomBetweenInclusive($$0.random, -10, 10);
/* 189 */       int $$4 = $$2.getY() + Mth.randomBetweenInclusive($$0.random, -10, 10);
/* 190 */       int $$5 = $$2.getZ() + Mth.randomBetweenInclusive($$0.random, -10, 10);
/*     */       
/* 192 */       $$0.playSound(null, $$3, $$4, $$5, $$1, SoundSource.HOSTILE, 5.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean trySummonWarden(ServerLevel $$0) {
/* 197 */     if (this.warningLevel < 4) {
/* 198 */       return false;
/*     */     }
/*     */     
/* 201 */     return SpawnUtil.trySpawnMob(EntityType.WARDEN, MobSpawnType.TRIGGERED, $$0, getBlockPos(), 20, 5, 6, SpawnUtil.Strategy.ON_TOP_OF_COLLIDER).isPresent();
/*     */   }
/*     */ 
/*     */   
/*     */   public VibrationSystem.Listener getListener() {
/* 206 */     return this.vibrationListener;
/*     */   }
/*     */ 
/*     */   
/*     */   private class VibrationUser
/*     */     implements VibrationSystem.User
/*     */   {
/*     */     private static final int LISTENER_RADIUS = 8;
/* 214 */     private final PositionSource positionSource = (PositionSource)new BlockPositionSource(SculkShriekerBlockEntity.this.worldPosition);
/*     */ 
/*     */ 
/*     */     
/*     */     public int getListenerRadius() {
/* 219 */       return 8;
/*     */     }
/*     */ 
/*     */     
/*     */     public PositionSource getPositionSource() {
/* 224 */       return this.positionSource;
/*     */     }
/*     */ 
/*     */     
/*     */     public TagKey<GameEvent> getListenableEvents() {
/* 229 */       return GameEventTags.SHRIEKER_CAN_LISTEN;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, GameEvent.Context $$3) {
/* 234 */       return (!((Boolean)SculkShriekerBlockEntity.this.getBlockState().getValue((Property)SculkShriekerBlock.SHRIEKING)).booleanValue() && SculkShriekerBlockEntity.tryGetPlayer($$3.sourceEntity()) != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onReceiveVibration(ServerLevel $$0, BlockPos $$1, GameEvent $$2, @Nullable Entity $$3, @Nullable Entity $$4, float $$5) {
/* 239 */       SculkShriekerBlockEntity.this.tryShriek($$0, SculkShriekerBlockEntity.tryGetPlayer(($$4 != null) ? $$4 : $$3));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onDataChanged() {
/* 244 */       SculkShriekerBlockEntity.this.setChanged();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean requiresAdjacentChunksToBeTicking() {
/* 249 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\SculkShriekerBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */