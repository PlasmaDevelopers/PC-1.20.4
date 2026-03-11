/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public abstract class BlockEntity {
/*  20 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final BlockEntityType<?> type;
/*     */   
/*     */   @Nullable
/*     */   protected Level level;
/*     */   protected final BlockPos worldPosition;
/*     */   protected boolean remove;
/*     */   private BlockState blockState;
/*     */   
/*     */   public BlockEntity(BlockEntityType<?> $$0, BlockPos $$1, BlockState $$2) {
/*  31 */     this.type = $$0;
/*  32 */     this.worldPosition = $$1.immutable();
/*  33 */     this.blockState = $$2;
/*     */   }
/*     */   
/*     */   public static BlockPos getPosFromTag(CompoundTag $$0) {
/*  37 */     return new BlockPos($$0.getInt("x"), $$0.getInt("y"), $$0.getInt("z"));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Level getLevel() {
/*  42 */     return this.level;
/*     */   }
/*     */   
/*     */   public void setLevel(Level $$0) {
/*  46 */     this.level = $$0;
/*     */   }
/*     */   
/*     */   public boolean hasLevel() {
/*  50 */     return (this.level != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public final CompoundTag saveWithFullMetadata() {
/*  64 */     CompoundTag $$0 = saveWithoutMetadata();
/*  65 */     saveMetadata($$0);
/*  66 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CompoundTag saveWithId() {
/*  74 */     CompoundTag $$0 = saveWithoutMetadata();
/*  75 */     saveId($$0);
/*  76 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CompoundTag saveWithoutMetadata() {
/*  83 */     CompoundTag $$0 = new CompoundTag();
/*  84 */     saveAdditional($$0);
/*  85 */     return $$0;
/*     */   }
/*     */   
/*     */   private void saveId(CompoundTag $$0) {
/*  89 */     ResourceLocation $$1 = BlockEntityType.getKey(getType());
/*  90 */     if ($$1 == null) {
/*  91 */       throw new RuntimeException("" + getClass() + " is missing a mapping! This is a bug!");
/*     */     }
/*  93 */     $$0.putString("id", $$1.toString());
/*     */   }
/*     */   
/*     */   public static void addEntityType(CompoundTag $$0, BlockEntityType<?> $$1) {
/*  97 */     $$0.putString("id", BlockEntityType.getKey($$1).toString());
/*     */   }
/*     */   
/*     */   public void saveToItem(ItemStack $$0) {
/* 101 */     BlockItem.setBlockEntityData($$0, getType(), saveWithoutMetadata());
/*     */   }
/*     */   
/*     */   private void saveMetadata(CompoundTag $$0) {
/* 105 */     saveId($$0);
/* 106 */     $$0.putInt("x", this.worldPosition.getX());
/* 107 */     $$0.putInt("y", this.worldPosition.getY());
/* 108 */     $$0.putInt("z", this.worldPosition.getZ());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static BlockEntity loadStatic(BlockPos $$0, BlockState $$1, CompoundTag $$2) {
/* 113 */     String $$3 = $$2.getString("id");
/*     */     
/* 115 */     ResourceLocation $$4 = ResourceLocation.tryParse($$3);
/* 116 */     if ($$4 == null) {
/* 117 */       LOGGER.error("Block entity has invalid type: {}", $$3);
/* 118 */       return null;
/*     */     } 
/*     */     
/* 121 */     return BuiltInRegistries.BLOCK_ENTITY_TYPE.getOptional($$4)
/* 122 */       .map($$3 -> {
/*     */           try {
/*     */             return $$3.create($$0, $$1);
/* 125 */           } catch (Throwable $$4) {
/*     */             LOGGER.error("Failed to create block entity {}", $$2, $$4);
/*     */             
/*     */             return null;
/*     */           } 
/* 130 */         }).map($$2 -> {
/*     */           try {
/*     */             $$2.load($$0);
/*     */             return $$2;
/* 134 */           } catch (Throwable $$3) {
/*     */             LOGGER.error("Failed to load data for block entity {}", $$1, $$3);
/*     */             
/*     */             return null;
/*     */           } 
/* 139 */         }).orElseGet(() -> {
/*     */           LOGGER.warn("Skipping BlockEntity with id {}", $$0);
/*     */           return null;
/*     */         });
/*     */   }
/*     */   
/*     */   public void setChanged() {
/* 146 */     if (this.level != null) {
/* 147 */       setChanged(this.level, this.worldPosition, this.blockState);
/*     */     }
/*     */   }
/*     */   
/*     */   protected static void setChanged(Level $$0, BlockPos $$1, BlockState $$2) {
/* 152 */     $$0.blockEntityChanged($$1);
/*     */     
/* 154 */     if (!$$2.isAir()) {
/* 155 */       $$0.updateNeighbourForOutputSignal($$1, $$2.getBlock());
/*     */     }
/*     */   }
/*     */   
/*     */   public BlockPos getBlockPos() {
/* 160 */     return this.worldPosition;
/*     */   }
/*     */   
/*     */   public BlockState getBlockState() {
/* 164 */     return this.blockState;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Packet<ClientGamePacketListener> getUpdatePacket() {
/* 169 */     return null;
/*     */   }
/*     */   
/*     */   public CompoundTag getUpdateTag() {
/* 173 */     return new CompoundTag();
/*     */   }
/*     */   
/*     */   public boolean isRemoved() {
/* 177 */     return this.remove;
/*     */   }
/*     */   
/*     */   public void setRemoved() {
/* 181 */     this.remove = true;
/*     */   }
/*     */   
/*     */   public void clearRemoved() {
/* 185 */     this.remove = false;
/*     */   }
/*     */   
/*     */   public boolean triggerEvent(int $$0, int $$1) {
/* 189 */     return false;
/*     */   }
/*     */   
/*     */   public void fillCrashReportCategory(CrashReportCategory $$0) {
/* 193 */     $$0.setDetail("Name", () -> "" + BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(getType()) + " // " + BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(getType()));
/*     */     
/* 195 */     if (this.level == null) {
/*     */       return;
/*     */     }
/*     */     
/* 199 */     CrashReportCategory.populateBlockDetails($$0, (LevelHeightAccessor)this.level, this.worldPosition, getBlockState());
/*     */     
/* 201 */     CrashReportCategory.populateBlockDetails($$0, (LevelHeightAccessor)this.level, this.worldPosition, this.level.getBlockState(this.worldPosition));
/*     */   }
/*     */   
/*     */   public boolean onlyOpCanSetNbt() {
/* 205 */     return false;
/*     */   }
/*     */   
/*     */   public BlockEntityType<?> getType() {
/* 209 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setBlockState(BlockState $$0) {
/* 218 */     this.blockState = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */