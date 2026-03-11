/*     */ package net.minecraft.world.level.block.entity;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.data.worldgen.features.EndFeatures;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.projectile.ThrownEnderpearl;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ import net.minecraft.world.level.levelgen.feature.Feature;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.EndGatewayConfiguration;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class TheEndGatewayBlockEntity extends TheEndPortalBlockEntity {
/*  41 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int SPAWN_TIME = 200;
/*     */   
/*     */   private static final int COOLDOWN_TIME = 40;
/*     */   private static final int ATTENTION_INTERVAL = 2400;
/*     */   private static final int EVENT_COOLDOWN = 1;
/*     */   private static final int GATEWAY_HEIGHT_ABOVE_SURFACE = 10;
/*     */   private long age;
/*     */   private int teleportCooldown;
/*     */   @Nullable
/*     */   private BlockPos exitPortal;
/*     */   private boolean exactTeleport;
/*     */   
/*     */   public TheEndGatewayBlockEntity(BlockPos $$0, BlockState $$1) {
/*  56 */     super(BlockEntityType.END_GATEWAY, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*  61 */     super.saveAdditional($$0);
/*  62 */     $$0.putLong("Age", this.age);
/*  63 */     if (this.exitPortal != null) {
/*  64 */       $$0.put("ExitPortal", (Tag)NbtUtils.writeBlockPos(this.exitPortal));
/*     */     }
/*  66 */     if (this.exactTeleport) {
/*  67 */       $$0.putBoolean("ExactTeleport", true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/*  73 */     super.load($$0);
/*  74 */     this.age = $$0.getLong("Age");
/*  75 */     if ($$0.contains("ExitPortal", 10)) {
/*  76 */       BlockPos $$1 = NbtUtils.readBlockPos($$0.getCompound("ExitPortal"));
/*  77 */       if (Level.isInSpawnableBounds($$1)) {
/*  78 */         this.exitPortal = $$1;
/*     */       }
/*     */     } 
/*  81 */     this.exactTeleport = $$0.getBoolean("ExactTeleport");
/*     */   }
/*     */   
/*     */   public static void beamAnimationTick(Level $$0, BlockPos $$1, BlockState $$2, TheEndGatewayBlockEntity $$3) {
/*  85 */     $$3.age++;
/*     */     
/*  87 */     if ($$3.isCoolingDown()) {
/*  88 */       $$3.teleportCooldown--;
/*     */     }
/*     */   }
/*     */   
/*     */   public static void teleportTick(Level $$0, BlockPos $$1, BlockState $$2, TheEndGatewayBlockEntity $$3) {
/*  93 */     boolean $$4 = $$3.isSpawning();
/*  94 */     boolean $$5 = $$3.isCoolingDown();
/*  95 */     $$3.age++;
/*     */     
/*  97 */     if ($$5) {
/*  98 */       $$3.teleportCooldown--;
/*     */     } else {
/* 100 */       List<Entity> $$6 = $$0.getEntitiesOfClass(Entity.class, new AABB($$1), TheEndGatewayBlockEntity::canEntityTeleport);
/* 101 */       if (!$$6.isEmpty()) {
/* 102 */         teleportEntity($$0, $$1, $$2, $$6.get($$0.random.nextInt($$6.size())), $$3);
/*     */       }
/* 104 */       if ($$3.age % 2400L == 0L) {
/* 105 */         triggerCooldown($$0, $$1, $$2, $$3);
/*     */       }
/*     */     } 
/*     */     
/* 109 */     if ($$4 != $$3.isSpawning() || $$5 != $$3.isCoolingDown()) {
/* 110 */       setChanged($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean canEntityTeleport(Entity $$0) {
/* 115 */     return (EntitySelector.NO_SPECTATORS.test($$0) && !$$0.getRootVehicle().isOnPortalCooldown());
/*     */   }
/*     */   
/*     */   public boolean isSpawning() {
/* 119 */     return (this.age < 200L);
/*     */   }
/*     */   
/*     */   public boolean isCoolingDown() {
/* 123 */     return (this.teleportCooldown > 0);
/*     */   }
/*     */   
/*     */   public float getSpawnPercent(float $$0) {
/* 127 */     return Mth.clamp(((float)this.age + $$0) / 200.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public float getCooldownPercent(float $$0) {
/* 131 */     return 1.0F - Mth.clamp((this.teleportCooldown - $$0) / 40.0F, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/* 136 */     return ClientboundBlockEntityDataPacket.create(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getUpdateTag() {
/* 141 */     return saveWithoutMetadata();
/*     */   }
/*     */   
/*     */   private static void triggerCooldown(Level $$0, BlockPos $$1, BlockState $$2, TheEndGatewayBlockEntity $$3) {
/* 145 */     if (!$$0.isClientSide) {
/* 146 */       $$3.teleportCooldown = 40;
/* 147 */       $$0.blockEvent($$1, $$2.getBlock(), 1, 0);
/* 148 */       setChanged($$0, $$1, $$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean triggerEvent(int $$0, int $$1) {
/* 154 */     if ($$0 == 1) {
/* 155 */       this.teleportCooldown = 40;
/* 156 */       return true;
/*     */     } 
/*     */     
/* 159 */     return super.triggerEvent($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void teleportEntity(Level $$0, BlockPos $$1, BlockState $$2, Entity $$3, TheEndGatewayBlockEntity $$4) {
/* 163 */     if (!($$0 instanceof ServerLevel) || $$4.isCoolingDown()) {
/*     */       return;
/*     */     }
/* 166 */     ServerLevel $$5 = (ServerLevel)$$0;
/* 167 */     $$4.teleportCooldown = 100;
/*     */ 
/*     */     
/* 170 */     if ($$4.exitPortal == null && $$0.dimension() == Level.END) {
/* 171 */       BlockPos $$6 = findOrCreateValidTeleportPos($$5, $$1);
/* 172 */       $$6 = $$6.above(10);
/* 173 */       LOGGER.debug("Creating portal at {}", $$6);
/* 174 */       spawnGatewayPortal($$5, $$6, EndGatewayConfiguration.knownExit($$1, false));
/* 175 */       $$4.exitPortal = $$6;
/*     */     } 
/*     */     
/* 178 */     if ($$4.exitPortal != null) {
/* 179 */       Entity $$11; BlockPos $$7 = $$4.exactTeleport ? $$4.exitPortal : findExitPosition($$0, $$4.exitPortal);
/*     */       
/* 181 */       if ($$3 instanceof ThrownEnderpearl) {
/* 182 */         Entity $$8 = ((ThrownEnderpearl)$$3).getOwner();
/* 183 */         if ($$8 instanceof ServerPlayer) {
/* 184 */           CriteriaTriggers.ENTER_BLOCK.trigger((ServerPlayer)$$8, $$2);
/*     */         }
/* 186 */         if ($$8 != null) {
/* 187 */           Entity $$9 = $$8;
/* 188 */           $$3.discard();
/*     */         } else {
/* 190 */           Entity $$10 = $$3;
/*     */         } 
/*     */       } else {
/* 193 */         $$11 = $$3.getRootVehicle();
/*     */       } 
/* 195 */       $$11.setPortalCooldown();
/* 196 */       $$11.teleportToWithTicket($$7.getX() + 0.5D, $$7.getY(), $$7.getZ() + 0.5D);
/*     */     } 
/*     */     
/* 199 */     triggerCooldown($$0, $$1, $$2, $$4);
/*     */   }
/*     */   
/*     */   private static BlockPos findExitPosition(Level $$0, BlockPos $$1) {
/* 203 */     BlockPos $$2 = findTallestBlock((BlockGetter)$$0, $$1.offset(0, 2, 0), 5, false);
/* 204 */     LOGGER.debug("Best exit position for portal at {} is {}", $$1, $$2);
/* 205 */     return $$2.above();
/*     */   }
/*     */   
/*     */   private static BlockPos findOrCreateValidTeleportPos(ServerLevel $$0, BlockPos $$1) {
/* 209 */     Vec3 $$2 = findExitPortalXZPosTentative($$0, $$1);
/*     */     
/* 211 */     LevelChunk $$3 = getChunk((Level)$$0, $$2);
/*     */     
/* 213 */     BlockPos $$4 = findValidSpawnInChunk($$3);
/*     */     
/* 215 */     if ($$4 == null) {
/* 216 */       BlockPos $$5 = BlockPos.containing($$2.x + 0.5D, 75.0D, $$2.z + 0.5D);
/* 217 */       LOGGER.debug("Failed to find a suitable block to teleport to, spawning an island on {}", $$5);
/* 218 */       $$0.registryAccess().registry(Registries.CONFIGURED_FEATURE)
/* 219 */         .flatMap($$0 -> $$0.getHolder(EndFeatures.END_ISLAND))
/* 220 */         .ifPresent($$2 -> ((ConfiguredFeature)$$2.value()).place((WorldGenLevel)$$0, $$0.getChunkSource().getGenerator(), RandomSource.create($$1.asLong()), $$1));
/* 221 */       $$4 = $$5;
/*     */     } else {
/* 223 */       LOGGER.debug("Found suitable block to teleport to: {}", $$4);
/*     */     } 
/*     */     
/* 226 */     return findTallestBlock((BlockGetter)$$0, $$4, 16, true);
/*     */   }
/*     */   
/*     */   private static Vec3 findExitPortalXZPosTentative(ServerLevel $$0, BlockPos $$1) {
/* 230 */     Vec3 $$2 = (new Vec3($$1.getX(), 0.0D, $$1.getZ())).normalize();
/* 231 */     int $$3 = 1024;
/* 232 */     Vec3 $$4 = $$2.scale(1024.0D);
/*     */     
/* 234 */     int $$5 = 16;
/* 235 */     while (!isChunkEmpty($$0, $$4) && $$5-- > 0) {
/* 236 */       LOGGER.debug("Skipping backwards past nonempty chunk at {}", $$4);
/* 237 */       $$4 = $$4.add($$2.scale(-16.0D));
/*     */     } 
/*     */     
/* 240 */     $$5 = 16;
/* 241 */     while (isChunkEmpty($$0, $$4) && $$5-- > 0) {
/* 242 */       LOGGER.debug("Skipping forward past empty chunk at {}", $$4);
/* 243 */       $$4 = $$4.add($$2.scale(16.0D));
/*     */     } 
/* 245 */     LOGGER.debug("Found chunk at {}", $$4);
/* 246 */     return $$4;
/*     */   }
/*     */   
/*     */   private static boolean isChunkEmpty(ServerLevel $$0, Vec3 $$1) {
/* 250 */     return (getChunk((Level)$$0, $$1).getHighestFilledSectionIndex() == -1);
/*     */   }
/*     */   
/*     */   private static BlockPos findTallestBlock(BlockGetter $$0, BlockPos $$1, int $$2, boolean $$3) {
/* 254 */     BlockPos $$4 = null;
/*     */     
/* 256 */     for (int $$5 = -$$2; $$5 <= $$2; $$5++) {
/* 257 */       for (int $$6 = -$$2; $$6 <= $$2; $$6++) {
/* 258 */         if ($$5 != 0 || $$6 != 0 || $$3)
/*     */         {
/*     */ 
/*     */           
/* 262 */           for (int $$7 = $$0.getMaxBuildHeight() - 1; $$7 > (($$4 == null) ? $$0.getMinBuildHeight() : $$4.getY()); $$7--) {
/* 263 */             BlockPos $$8 = new BlockPos($$1.getX() + $$5, $$7, $$1.getZ() + $$6);
/* 264 */             BlockState $$9 = $$0.getBlockState($$8);
/* 265 */             if ($$9.isCollisionShapeFullBlock($$0, $$8) && ($$3 || !$$9.is(Blocks.BEDROCK))) {
/* 266 */               $$4 = $$8;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 273 */     return ($$4 == null) ? $$1 : $$4;
/*     */   }
/*     */   
/*     */   private static LevelChunk getChunk(Level $$0, Vec3 $$1) {
/* 277 */     return $$0.getChunk(Mth.floor($$1.x / 16.0D), Mth.floor($$1.z / 16.0D));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static BlockPos findValidSpawnInChunk(LevelChunk $$0) {
/* 282 */     ChunkPos $$1 = $$0.getPos();
/* 283 */     BlockPos $$2 = new BlockPos($$1.getMinBlockX(), 30, $$1.getMinBlockZ());
/* 284 */     int $$3 = $$0.getHighestSectionPosition() + 16 - 1;
/* 285 */     BlockPos $$4 = new BlockPos($$1.getMaxBlockX(), $$3, $$1.getMaxBlockZ());
/* 286 */     BlockPos $$5 = null;
/* 287 */     double $$6 = 0.0D;
/*     */ 
/*     */     
/* 290 */     for (BlockPos $$7 : BlockPos.betweenClosed($$2, $$4)) {
/* 291 */       BlockState $$8 = $$0.getBlockState($$7);
/*     */       
/* 293 */       BlockPos $$9 = $$7.above();
/* 294 */       BlockPos $$10 = $$7.above(2);
/* 295 */       if ($$8.is(Blocks.END_STONE) && !$$0.getBlockState($$9).isCollisionShapeFullBlock((BlockGetter)$$0, $$9) && !$$0.getBlockState($$10).isCollisionShapeFullBlock((BlockGetter)$$0, $$10)) {
/* 296 */         double $$11 = $$7.distToCenterSqr(0.0D, 0.0D, 0.0D);
/* 297 */         if ($$5 == null || $$11 < $$6) {
/* 298 */           $$5 = $$7;
/* 299 */           $$6 = $$11;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 304 */     return $$5;
/*     */   }
/*     */   
/*     */   private static void spawnGatewayPortal(ServerLevel $$0, BlockPos $$1, EndGatewayConfiguration $$2) {
/* 308 */     Feature.END_GATEWAY.place((FeatureConfiguration)$$2, (WorldGenLevel)$$0, $$0.getChunkSource().getGenerator(), RandomSource.create(), $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderFace(Direction $$0) {
/* 313 */     return Block.shouldRenderFace(getBlockState(), (BlockGetter)this.level, getBlockPos(), $$0, getBlockPos().relative($$0));
/*     */   }
/*     */   
/*     */   public int getParticleAmount() {
/* 317 */     int $$0 = 0;
/* 318 */     for (Direction $$1 : Direction.values()) {
/* 319 */       $$0 += shouldRenderFace($$1) ? 1 : 0;
/*     */     }
/* 321 */     return $$0;
/*     */   }
/*     */   
/*     */   public void setExitPosition(BlockPos $$0, boolean $$1) {
/* 325 */     this.exactTeleport = $$1;
/* 326 */     this.exitPortal = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\TheEndGatewayBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */