/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.Clearable;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.RecordItem;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.JukeboxBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.ticks.ContainerSingleItem;
/*     */ 
/*     */ public class JukeboxBlockEntity
/*     */   extends BlockEntity implements Clearable, ContainerSingleItem {
/*     */   private static final int SONG_END_PADDING = 20;
/*  31 */   private ItemStack item = ItemStack.EMPTY;
/*     */   private int ticksSinceLastEvent;
/*     */   private long tickCount;
/*     */   private long recordStartedTick;
/*     */   private boolean isPlaying;
/*     */   
/*     */   public JukeboxBlockEntity(BlockPos $$0, BlockState $$1) {
/*  38 */     super(BlockEntityType.JUKEBOX, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/*  43 */     super.load($$0);
/*     */     
/*  45 */     if ($$0.contains("RecordItem", 10)) {
/*  46 */       this.item = ItemStack.of($$0.getCompound("RecordItem"));
/*     */     }
/*     */     
/*  49 */     this.isPlaying = $$0.getBoolean("IsPlaying");
/*  50 */     this.recordStartedTick = $$0.getLong("RecordStartTick");
/*  51 */     this.tickCount = $$0.getLong("TickCount");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*  56 */     super.saveAdditional($$0);
/*     */     
/*  58 */     if (!getTheItem().isEmpty()) {
/*  59 */       $$0.put("RecordItem", (Tag)getTheItem().save(new CompoundTag()));
/*     */     }
/*     */     
/*  62 */     $$0.putBoolean("IsPlaying", this.isPlaying);
/*  63 */     $$0.putLong("RecordStartTick", this.recordStartedTick);
/*  64 */     $$0.putLong("TickCount", this.tickCount);
/*     */   }
/*     */   
/*     */   public boolean isRecordPlaying() {
/*  68 */     return (!getTheItem().isEmpty() && this.isPlaying);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setHasRecordBlockState(@Nullable Entity $$0, boolean $$1) {
/*  73 */     if (this.level.getBlockState(getBlockPos()) == getBlockState()) {
/*  74 */       this.level.setBlock(getBlockPos(), (BlockState)getBlockState().setValue((Property)JukeboxBlock.HAS_RECORD, Boolean.valueOf($$1)), 2);
/*  75 */       this.level.gameEvent(GameEvent.BLOCK_CHANGE, getBlockPos(), GameEvent.Context.of($$0, getBlockState()));
/*     */     } 
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public void startPlaying() {
/*  81 */     this.recordStartedTick = this.tickCount;
/*  82 */     this.isPlaying = true;
/*  83 */     this.level.updateNeighborsAt(getBlockPos(), getBlockState().getBlock());
/*  84 */     this.level.levelEvent(null, 1010, getBlockPos(), Item.getId(getTheItem().getItem()));
/*  85 */     setChanged();
/*     */   }
/*     */   
/*     */   private void stopPlaying() {
/*  89 */     this.isPlaying = false;
/*  90 */     this.level.gameEvent(GameEvent.JUKEBOX_STOP_PLAY, getBlockPos(), GameEvent.Context.of(getBlockState()));
/*  91 */     this.level.updateNeighborsAt(getBlockPos(), getBlockState().getBlock());
/*  92 */     this.level.levelEvent(1011, getBlockPos(), 0);
/*  93 */     setChanged();
/*     */   }
/*     */   
/*     */   private void tick(Level $$0, BlockPos $$1, BlockState $$2) {
/*  97 */     this.ticksSinceLastEvent++;
/*  98 */     if (isRecordPlaying()) { Item item = getTheItem().getItem(); if (item instanceof RecordItem) { RecordItem $$3 = (RecordItem)item;
/*  99 */         if (shouldRecordStopPlaying($$3)) {
/* 100 */           stopPlaying();
/* 101 */         } else if (shouldSendJukeboxPlayingEvent()) {
/* 102 */           this.ticksSinceLastEvent = 0;
/* 103 */           $$0.gameEvent(GameEvent.JUKEBOX_PLAY, $$1, GameEvent.Context.of($$2));
/* 104 */           spawnMusicParticles($$0, $$1);
/*     */         }  }
/*     */        }
/* 107 */      this.tickCount++;
/*     */   }
/*     */   
/*     */   private boolean shouldRecordStopPlaying(RecordItem $$0) {
/* 111 */     return (this.tickCount >= this.recordStartedTick + $$0.getLengthInTicks() + 20L);
/*     */   }
/*     */   
/*     */   private boolean shouldSendJukeboxPlayingEvent() {
/* 115 */     return (this.ticksSinceLastEvent >= 20);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getTheItem() {
/* 120 */     return this.item;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack splitTheItem(int $$0) {
/* 125 */     ItemStack $$1 = this.item;
/* 126 */     this.item = ItemStack.EMPTY;
/*     */     
/* 128 */     if (!$$1.isEmpty()) {
/* 129 */       setHasRecordBlockState((Entity)null, false);
/* 130 */       stopPlaying();
/*     */     } 
/*     */     
/* 133 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTheItem(ItemStack $$0) {
/* 138 */     if ($$0.is(ItemTags.MUSIC_DISCS) && this.level != null) {
/* 139 */       this.item = $$0;
/* 140 */       setHasRecordBlockState((Entity)null, true);
/* 141 */       startPlaying();
/* 142 */     } else if ($$0.isEmpty()) {
/* 143 */       splitTheItem(1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/* 149 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity getContainerBlockEntity() {
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceItem(int $$0, ItemStack $$1) {
/* 159 */     return ($$1.is(ItemTags.MUSIC_DISCS) && getItem($$0).isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeItem(Container $$0, int $$1, ItemStack $$2) {
/* 164 */     return $$0.hasAnyMatching(ItemStack::isEmpty);
/*     */   }
/*     */   
/*     */   private void spawnMusicParticles(Level $$0, BlockPos $$1) {
/* 168 */     if ($$0 instanceof ServerLevel) { ServerLevel $$2 = (ServerLevel)$$0;
/* 169 */       Vec3 $$3 = Vec3.atBottomCenterOf((Vec3i)$$1).add(0.0D, 1.2000000476837158D, 0.0D);
/* 170 */       float $$4 = $$0.getRandom().nextInt(4) / 24.0F;
/* 171 */       $$2.sendParticles((ParticleOptions)ParticleTypes.NOTE, $$3.x(), $$3.y(), $$3.z(), 0, $$4, 0.0D, 0.0D, 1.0D); }
/*     */   
/*     */   }
/*     */   
/*     */   public void popOutRecord() {
/* 176 */     if (this.level == null || this.level.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 180 */     BlockPos $$0 = getBlockPos();
/* 181 */     ItemStack $$1 = getTheItem();
/* 182 */     if ($$1.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 186 */     removeTheItem();
/*     */     
/* 188 */     Vec3 $$2 = Vec3.atLowerCornerWithOffset((Vec3i)$$0, 0.5D, 1.01D, 0.5D).offsetRandom(this.level.random, 0.7F);
/* 189 */     ItemStack $$3 = $$1.copy();
/*     */     
/* 191 */     ItemEntity $$4 = new ItemEntity(this.level, $$2.x(), $$2.y(), $$2.z(), $$3);
/* 192 */     $$4.setDefaultPickUpDelay();
/* 193 */     this.level.addFreshEntity((Entity)$$4);
/*     */   }
/*     */   
/*     */   public static void playRecordTick(Level $$0, BlockPos $$1, BlockState $$2, JukeboxBlockEntity $$3) {
/* 197 */     $$3.tick($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public void setRecordWithoutPlaying(ItemStack $$0) {
/* 202 */     this.item = $$0;
/* 203 */     this.level.updateNeighborsAt(getBlockPos(), getBlockState().getBlock());
/* 204 */     setChanged();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\JukeboxBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */