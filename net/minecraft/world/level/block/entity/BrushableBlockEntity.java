/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.BrushableBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class BrushableBlockEntity extends BlockEntity {
/*  37 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final String LOOT_TABLE_TAG = "LootTable";
/*     */   private static final String LOOT_TABLE_SEED_TAG = "LootTableSeed";
/*     */   private static final String HIT_DIRECTION_TAG = "hit_direction";
/*     */   private static final String ITEM_TAG = "item";
/*     */   private static final int BRUSH_COOLDOWN_TICKS = 10;
/*     */   private static final int BRUSH_RESET_TICKS = 40;
/*     */   private static final int REQUIRED_BRUSHES_TO_BREAK = 10;
/*     */   private int brushCount;
/*     */   private long brushCountResetsAtTick;
/*     */   private long coolDownEndsAtTick;
/*  49 */   private ItemStack item = ItemStack.EMPTY;
/*     */   @Nullable
/*     */   private Direction hitDirection;
/*     */   @Nullable
/*     */   private ResourceLocation lootTable;
/*     */   private long lootTableSeed;
/*     */   
/*     */   public BrushableBlockEntity(BlockPos $$0, BlockState $$1) {
/*  57 */     super(BlockEntityType.BRUSHABLE_BLOCK, $$0, $$1);
/*     */   }
/*     */   
/*     */   public boolean brush(long $$0, Player $$1, Direction $$2) {
/*  61 */     if (this.hitDirection == null) {
/*  62 */       this.hitDirection = $$2;
/*     */     }
/*  64 */     this.brushCountResetsAtTick = $$0 + 40L;
/*     */     
/*  66 */     if ($$0 < this.coolDownEndsAtTick || !(this.level instanceof ServerLevel)) {
/*  67 */       return false;
/*     */     }
/*  69 */     this.coolDownEndsAtTick = $$0 + 10L;
/*     */     
/*  71 */     unpackLootTable($$1);
/*     */     
/*  73 */     int $$3 = getCompletionState();
/*     */     
/*  75 */     if (++this.brushCount >= 10) {
/*  76 */       brushingCompleted($$1);
/*  77 */       return true;
/*     */     } 
/*     */     
/*  80 */     this.level.scheduleTick(getBlockPos(), getBlockState().getBlock(), 2);
/*     */     
/*  82 */     int $$4 = getCompletionState();
/*  83 */     if ($$3 != $$4) {
/*  84 */       BlockState $$5 = getBlockState();
/*  85 */       BlockState $$6 = (BlockState)$$5.setValue((Property)BlockStateProperties.DUSTED, Integer.valueOf($$4));
/*  86 */       this.level.setBlock(getBlockPos(), $$6, 3);
/*     */     } 
/*     */     
/*  89 */     return false;
/*     */   }
/*     */   
/*     */   public void unpackLootTable(Player $$0) {
/*  93 */     if (this.lootTable == null || this.level == null || this.level.isClientSide() || this.level.getServer() == null) {
/*     */       return;
/*     */     }
/*     */     
/*  97 */     LootTable $$1 = this.level.getServer().getLootData().getLootTable(this.lootTable);
/*  98 */     if ($$0 instanceof ServerPlayer) { ServerPlayer $$2 = (ServerPlayer)$$0;
/*  99 */       CriteriaTriggers.GENERATE_LOOT.trigger($$2, this.lootTable); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 106 */     LootParams $$3 = (new LootParams.Builder((ServerLevel)this.level)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf((Vec3i)this.worldPosition)).withLuck($$0.getLuck()).withParameter(LootContextParams.THIS_ENTITY, $$0).create(LootContextParamSets.CHEST);
/*     */     
/* 108 */     ObjectArrayList<ItemStack> $$4 = $$1.getRandomItems($$3, this.lootTableSeed);
/*     */     
/* 110 */     switch ($$4.size()) { case 0: 
/*     */       case 1:
/*     */       
/*     */       default:
/* 114 */         LOGGER.warn("Expected max 1 loot from loot table " + this.lootTable + " got " + $$4.size()); break; }
/* 115 */      this.item = (ItemStack)$$4.get(0);
/*     */ 
/*     */ 
/*     */     
/* 119 */     this.lootTable = null;
/* 120 */     setChanged();
/*     */   }
/*     */   private void brushingCompleted(Player $$0) {
/*     */     Block $$5;
/* 124 */     if (this.level == null || this.level.getServer() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 128 */     dropContent($$0);
/* 129 */     BlockState $$1 = getBlockState();
/* 130 */     this.level.levelEvent(3008, getBlockPos(), Block.getId($$1));
/* 131 */     Block $$2 = getBlockState().getBlock();
/*     */ 
/*     */     
/* 134 */     if ($$2 instanceof BrushableBlock) { BrushableBlock $$3 = (BrushableBlock)$$2;
/* 135 */       Block $$4 = $$3.getTurnsInto(); }
/*     */     else
/* 137 */     { $$5 = Blocks.AIR; }
/*     */ 
/*     */     
/* 140 */     this.level.setBlock(this.worldPosition, $$5.defaultBlockState(), 3);
/*     */   }
/*     */   
/*     */   private void dropContent(Player $$0) {
/* 144 */     if (this.level == null || this.level.getServer() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 148 */     unpackLootTable($$0);
/*     */     
/* 150 */     if (!this.item.isEmpty()) {
/* 151 */       double $$1 = EntityType.ITEM.getWidth();
/* 152 */       double $$2 = 1.0D - $$1;
/* 153 */       double $$3 = $$1 / 2.0D;
/*     */       
/* 155 */       Direction $$4 = Objects.<Direction>requireNonNullElse(this.hitDirection, Direction.UP);
/* 156 */       BlockPos $$5 = this.worldPosition.relative($$4, 1);
/*     */       
/* 158 */       double $$6 = $$5.getX() + 0.5D * $$2 + $$3;
/* 159 */       double $$7 = $$5.getY() + 0.5D + (EntityType.ITEM.getHeight() / 2.0F);
/* 160 */       double $$8 = $$5.getZ() + 0.5D * $$2 + $$3;
/*     */       
/* 162 */       ItemEntity $$9 = new ItemEntity(this.level, $$6, $$7, $$8, this.item.split(this.level.random.nextInt(21) + 10));
/* 163 */       $$9.setDeltaMovement(Vec3.ZERO);
/* 164 */       this.level.addFreshEntity((Entity)$$9);
/*     */       
/* 166 */       this.item = ItemStack.EMPTY;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void checkReset() {
/* 171 */     if (this.level == null) {
/*     */       return;
/*     */     }
/*     */     
/* 175 */     if (this.brushCount != 0 && this.level.getGameTime() >= this.brushCountResetsAtTick) {
/* 176 */       int $$0 = getCompletionState();
/* 177 */       this.brushCount = Math.max(0, this.brushCount - 2);
/* 178 */       int $$1 = getCompletionState();
/*     */       
/* 180 */       if ($$0 != $$1) {
/* 181 */         this.level.setBlock(getBlockPos(), (BlockState)getBlockState().setValue((Property)BlockStateProperties.DUSTED, Integer.valueOf($$1)), 3);
/*     */       }
/* 183 */       int $$2 = 4;
/* 184 */       this.brushCountResetsAtTick = this.level.getGameTime() + 4L;
/*     */     } 
/*     */     
/* 187 */     if (this.brushCount == 0) {
/* 188 */       this.hitDirection = null;
/* 189 */       this.brushCountResetsAtTick = 0L;
/* 190 */       this.coolDownEndsAtTick = 0L;
/*     */     } else {
/* 192 */       this.level.scheduleTick(getBlockPos(), getBlockState().getBlock(), 2);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean tryLoadLootTable(CompoundTag $$0) {
/* 197 */     if ($$0.contains("LootTable", 8)) {
/* 198 */       this.lootTable = new ResourceLocation($$0.getString("LootTable"));
/* 199 */       this.lootTableSeed = $$0.getLong("LootTableSeed");
/* 200 */       return true;
/*     */     } 
/* 202 */     return false;
/*     */   }
/*     */   
/*     */   private boolean trySaveLootTable(CompoundTag $$0) {
/* 206 */     if (this.lootTable == null) {
/* 207 */       return false;
/*     */     }
/*     */     
/* 210 */     $$0.putString("LootTable", this.lootTable.toString());
/* 211 */     if (this.lootTableSeed != 0L) {
/* 212 */       $$0.putLong("LootTableSeed", this.lootTableSeed);
/*     */     }
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getUpdateTag() {
/* 219 */     CompoundTag $$0 = super.getUpdateTag();
/* 220 */     if (this.hitDirection != null) {
/* 221 */       $$0.putInt("hit_direction", this.hitDirection.ordinal());
/*     */     }
/* 223 */     $$0.put("item", (Tag)this.item.save(new CompoundTag()));
/* 224 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/* 229 */     return ClientboundBlockEntityDataPacket.create(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 234 */     if (!tryLoadLootTable($$0) && $$0.contains("item")) {
/* 235 */       this.item = ItemStack.of($$0.getCompound("item"));
/*     */     }
/*     */     
/* 238 */     if ($$0.contains("hit_direction")) {
/* 239 */       this.hitDirection = Direction.values()[$$0.getInt("hit_direction")];
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/* 245 */     if (!trySaveLootTable($$0)) {
/* 246 */       $$0.put("item", (Tag)this.item.save(new CompoundTag()));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setLootTable(ResourceLocation $$0, long $$1) {
/* 251 */     this.lootTable = $$0;
/* 252 */     this.lootTableSeed = $$1;
/*     */   }
/*     */   
/*     */   private int getCompletionState() {
/* 256 */     if (this.brushCount == 0) {
/* 257 */       return 0;
/*     */     }
/* 259 */     if (this.brushCount < 3) {
/* 260 */       return 1;
/*     */     }
/* 262 */     if (this.brushCount < 6) {
/* 263 */       return 2;
/*     */     }
/* 265 */     return 3;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Direction getHitDirection() {
/* 270 */     return this.hitDirection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItem() {
/* 279 */     return this.item;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BrushableBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */