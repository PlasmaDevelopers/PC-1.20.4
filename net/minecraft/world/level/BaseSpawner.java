/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.random.SimpleWeightedRandomList;
/*     */ import net.minecraft.util.random.WeightedEntry;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.SpawnPlacements;
/*     */ import net.minecraft.world.level.entity.EntityTypeTest;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public abstract class BaseSpawner {
/*     */   public static final String SPAWN_DATA_TAG = "SpawnData";
/*  35 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int EVENT_SPAWN = 1;
/*     */   
/*  39 */   private int spawnDelay = 20;
/*  40 */   private SimpleWeightedRandomList<SpawnData> spawnPotentials = SimpleWeightedRandomList.empty();
/*     */   @Nullable
/*     */   private SpawnData nextSpawnData;
/*     */   private double spin;
/*     */   private double oSpin;
/*  45 */   private int minSpawnDelay = 200;
/*  46 */   private int maxSpawnDelay = 800;
/*  47 */   private int spawnCount = 4;
/*     */   @Nullable
/*     */   private Entity displayEntity;
/*  50 */   private int maxNearbyEntities = 6;
/*  51 */   private int requiredPlayerRange = 16;
/*  52 */   private int spawnRange = 4;
/*     */   
/*     */   public void setEntityId(EntityType<?> $$0, @Nullable Level $$1, RandomSource $$2, BlockPos $$3) {
/*  55 */     getOrCreateNextSpawnData($$1, $$2, $$3).getEntityToSpawn().putString("id", BuiltInRegistries.ENTITY_TYPE.getKey($$0).toString());
/*     */   }
/*     */   
/*     */   private boolean isNearPlayer(Level $$0, BlockPos $$1) {
/*  59 */     return $$0.hasNearbyAlivePlayer($$1.getX() + 0.5D, $$1.getY() + 0.5D, $$1.getZ() + 0.5D, this.requiredPlayerRange);
/*     */   }
/*     */   
/*     */   public void clientTick(Level $$0, BlockPos $$1) {
/*  63 */     if (!isNearPlayer($$0, $$1)) {
/*  64 */       this.oSpin = this.spin;
/*  65 */     } else if (this.displayEntity != null) {
/*  66 */       RandomSource $$2 = $$0.getRandom();
/*  67 */       double $$3 = $$1.getX() + $$2.nextDouble();
/*  68 */       double $$4 = $$1.getY() + $$2.nextDouble();
/*  69 */       double $$5 = $$1.getZ() + $$2.nextDouble();
/*  70 */       $$0.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$3, $$4, $$5, 0.0D, 0.0D, 0.0D);
/*  71 */       $$0.addParticle((ParticleOptions)ParticleTypes.FLAME, $$3, $$4, $$5, 0.0D, 0.0D, 0.0D);
/*     */       
/*  73 */       if (this.spawnDelay > 0) {
/*  74 */         this.spawnDelay--;
/*     */       }
/*  76 */       this.oSpin = this.spin;
/*  77 */       this.spin = (this.spin + (1000.0F / (this.spawnDelay + 200.0F))) % 360.0D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void serverTick(ServerLevel $$0, BlockPos $$1) {
/*  82 */     if (!isNearPlayer((Level)$$0, $$1)) {
/*     */       return;
/*     */     }
/*     */     
/*  86 */     if (this.spawnDelay == -1) {
/*  87 */       delay((Level)$$0, $$1);
/*     */     }
/*     */     
/*  90 */     if (this.spawnDelay > 0) {
/*  91 */       this.spawnDelay--;
/*     */       
/*     */       return;
/*     */     } 
/*  95 */     boolean $$2 = false;
/*     */     
/*  97 */     RandomSource $$3 = $$0.getRandom();
/*  98 */     SpawnData $$4 = getOrCreateNextSpawnData((Level)$$0, $$3, $$1);
/*  99 */     for (int $$5 = 0; $$5 < this.spawnCount; $$5++) {
/* 100 */       CompoundTag $$6 = $$4.getEntityToSpawn();
/* 101 */       Optional<EntityType<?>> $$7 = EntityType.by($$6);
/* 102 */       if ($$7.isEmpty()) {
/* 103 */         delay((Level)$$0, $$1);
/*     */         
/*     */         return;
/*     */       } 
/* 107 */       ListTag $$8 = $$6.getList("Pos", 6);
/*     */       
/* 109 */       int $$9 = $$8.size();
/* 110 */       double $$10 = ($$9 >= 1) ? $$8.getDouble(0) : ($$1.getX() + ($$3.nextDouble() - $$3.nextDouble()) * this.spawnRange + 0.5D);
/* 111 */       double $$11 = ($$9 >= 2) ? $$8.getDouble(1) : ($$1.getY() + $$3.nextInt(3) - 1);
/* 112 */       double $$12 = ($$9 >= 3) ? $$8.getDouble(2) : ($$1.getZ() + ($$3.nextDouble() - $$3.nextDouble()) * this.spawnRange + 0.5D);
/*     */       
/* 114 */       if (!$$0.noCollision(((EntityType)$$7.get()).getAABB($$10, $$11, $$12))) {
/*     */         continue;
/*     */       }
/*     */       
/* 118 */       BlockPos $$13 = BlockPos.containing($$10, $$11, $$12);
/* 119 */       if ($$4.getCustomSpawnRules().isPresent()) {
/* 120 */         if (!((EntityType)$$7.get()).getCategory().isFriendly() && $$0.getDifficulty() == Difficulty.PEACEFUL) {
/*     */           continue;
/*     */         }
/*     */         
/* 124 */         SpawnData.CustomSpawnRules $$14 = $$4.getCustomSpawnRules().get();
/* 125 */         if (!$$14.blockLightLimit().isValueInRange(Integer.valueOf($$0.getBrightness(LightLayer.BLOCK, $$13))) || 
/* 126 */           !$$14.skyLightLimit().isValueInRange(Integer.valueOf($$0.getBrightness(LightLayer.SKY, $$13)))) {
/*     */           continue;
/*     */         }
/*     */       }
/* 130 */       else if (!SpawnPlacements.checkSpawnRules($$7.get(), (ServerLevelAccessor)$$0, MobSpawnType.SPAWNER, $$13, $$0.getRandom())) {
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 135 */       Entity $$15 = EntityType.loadEntityRecursive($$6, (Level)$$0, $$3 -> {
/*     */             $$3.moveTo($$0, $$1, $$2, $$3.getYRot(), $$3.getXRot());
/*     */             return $$3;
/*     */           });
/* 139 */       if ($$15 == null) {
/* 140 */         delay((Level)$$0, $$1);
/*     */         
/*     */         return;
/*     */       } 
/* 144 */       int $$16 = $$0.getEntities(EntityTypeTest.forExactClass($$15.getClass()), (new AABB($$1.getX(), $$1.getY(), $$1.getZ(), ($$1.getX() + 1), ($$1.getY() + 1), ($$1.getZ() + 1))).inflate(this.spawnRange), EntitySelector.NO_SPECTATORS).size();
/* 145 */       if ($$16 >= this.maxNearbyEntities) {
/* 146 */         delay((Level)$$0, $$1);
/*     */         
/*     */         return;
/*     */       } 
/* 150 */       $$15.moveTo($$15.getX(), $$15.getY(), $$15.getZ(), $$3.nextFloat() * 360.0F, 0.0F);
/* 151 */       if ($$15 instanceof Mob) { Mob $$17 = (Mob)$$15;
/* 152 */         if ($$4.getCustomSpawnRules().isEmpty() && !$$17.checkSpawnRules((LevelAccessor)$$0, MobSpawnType.SPAWNER)) {
/*     */           continue;
/*     */         }
/* 155 */         if (!$$17.checkSpawnObstruction((LevelReader)$$0)) {
/*     */           continue;
/*     */         }
/* 158 */         if ($$4.getEntityToSpawn().size() == 1 && $$4.getEntityToSpawn().contains("id", 8)) {
/* 159 */           ((Mob)$$15).finalizeSpawn((ServerLevelAccessor)$$0, $$0.getCurrentDifficultyAt($$15.blockPosition()), MobSpawnType.SPAWNER, null, null);
/*     */         } }
/*     */ 
/*     */       
/* 163 */       if (!$$0.tryAddFreshEntityWithPassengers($$15)) {
/* 164 */         delay((Level)$$0, $$1);
/*     */         
/*     */         return;
/*     */       } 
/* 168 */       $$0.levelEvent(2004, $$1, 0);
/* 169 */       $$0.gameEvent($$15, GameEvent.ENTITY_PLACE, $$13);
/* 170 */       if ($$15 instanceof Mob) {
/* 171 */         ((Mob)$$15).spawnAnim();
/*     */       }
/* 173 */       $$2 = true;
/*     */       continue;
/*     */     } 
/* 176 */     if ($$2) {
/* 177 */       delay((Level)$$0, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void delay(Level $$0, BlockPos $$1) {
/* 182 */     RandomSource $$2 = $$0.random;
/* 183 */     if (this.maxSpawnDelay <= this.minSpawnDelay) {
/* 184 */       this.spawnDelay = this.minSpawnDelay;
/*     */     } else {
/* 186 */       this.spawnDelay = this.minSpawnDelay + $$2.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
/*     */     } 
/*     */     
/* 189 */     this.spawnPotentials.getRandom($$2).ifPresent($$2 -> setNextSpawnData($$0, $$1, (SpawnData)$$2.getData()));
/*     */     
/* 191 */     broadcastEvent($$0, $$1, 1);
/*     */   }
/*     */   
/*     */   public void load(@Nullable Level $$0, BlockPos $$1, CompoundTag $$2) {
/* 195 */     this.spawnDelay = $$2.getShort("Delay");
/*     */     
/* 197 */     boolean $$3 = $$2.contains("SpawnData", 10);
/* 198 */     if ($$3) {
/* 199 */       SpawnData $$4 = SpawnData.CODEC.parse((DynamicOps)NbtOps.INSTANCE, $$2.getCompound("SpawnData")).resultOrPartial($$0 -> LOGGER.warn("Invalid SpawnData: {}", $$0)).orElseGet(SpawnData::new);
/* 200 */       setNextSpawnData($$0, $$1, $$4);
/*     */     } 
/* 202 */     boolean $$5 = $$2.contains("SpawnPotentials", 9);
/* 203 */     if ($$5) {
/* 204 */       ListTag $$6 = $$2.getList("SpawnPotentials", 10);
/* 205 */       this.spawnPotentials = SpawnData.LIST_CODEC.parse((DynamicOps)NbtOps.INSTANCE, $$6).resultOrPartial($$0 -> LOGGER.warn("Invalid SpawnPotentials list: {}", $$0)).orElseGet(SimpleWeightedRandomList::empty);
/*     */     } else {
/* 207 */       this.spawnPotentials = SimpleWeightedRandomList.single((this.nextSpawnData != null) ? this.nextSpawnData : new SpawnData());
/*     */     } 
/*     */     
/* 210 */     if ($$2.contains("MinSpawnDelay", 99)) {
/* 211 */       this.minSpawnDelay = $$2.getShort("MinSpawnDelay");
/* 212 */       this.maxSpawnDelay = $$2.getShort("MaxSpawnDelay");
/* 213 */       this.spawnCount = $$2.getShort("SpawnCount");
/*     */     } 
/*     */     
/* 216 */     if ($$2.contains("MaxNearbyEntities", 99)) {
/* 217 */       this.maxNearbyEntities = $$2.getShort("MaxNearbyEntities");
/* 218 */       this.requiredPlayerRange = $$2.getShort("RequiredPlayerRange");
/*     */     } 
/*     */     
/* 221 */     if ($$2.contains("SpawnRange", 99)) {
/* 222 */       this.spawnRange = $$2.getShort("SpawnRange");
/*     */     }
/*     */     
/* 225 */     this.displayEntity = null;
/*     */   }
/*     */   
/*     */   public CompoundTag save(CompoundTag $$0) {
/* 229 */     $$0.putShort("Delay", (short)this.spawnDelay);
/* 230 */     $$0.putShort("MinSpawnDelay", (short)this.minSpawnDelay);
/* 231 */     $$0.putShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
/* 232 */     $$0.putShort("SpawnCount", (short)this.spawnCount);
/* 233 */     $$0.putShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
/* 234 */     $$0.putShort("RequiredPlayerRange", (short)this.requiredPlayerRange);
/* 235 */     $$0.putShort("SpawnRange", (short)this.spawnRange);
/* 236 */     if (this.nextSpawnData != null) {
/* 237 */       $$0.put("SpawnData", (Tag)SpawnData.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.nextSpawnData).result().orElseThrow(() -> new IllegalStateException("Invalid SpawnData")));
/*     */     }
/* 239 */     $$0.put("SpawnPotentials", SpawnData.LIST_CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.spawnPotentials).result().orElseThrow());
/*     */     
/* 241 */     return $$0;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entity getOrCreateDisplayEntity(Level $$0, BlockPos $$1) {
/* 246 */     if (this.displayEntity == null) {
/* 247 */       CompoundTag $$2 = getOrCreateNextSpawnData($$0, $$0.getRandom(), $$1).getEntityToSpawn();
/* 248 */       if (!$$2.contains("id", 8)) {
/* 249 */         return null;
/*     */       }
/* 251 */       this.displayEntity = EntityType.loadEntityRecursive($$2, $$0, Function.identity());
/* 252 */       if ($$2.size() != 1 || this.displayEntity instanceof Mob);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     return this.displayEntity;
/*     */   }
/*     */   
/*     */   public boolean onEventTriggered(Level $$0, int $$1) {
/* 262 */     if ($$1 == 1) {
/* 263 */       if ($$0.isClientSide) {
/* 264 */         this.spawnDelay = this.minSpawnDelay;
/*     */       }
/* 266 */       return true;
/*     */     } 
/* 268 */     return false;
/*     */   }
/*     */   
/*     */   protected void setNextSpawnData(@Nullable Level $$0, BlockPos $$1, SpawnData $$2) {
/* 272 */     this.nextSpawnData = $$2;
/*     */   }
/*     */   
/*     */   private SpawnData getOrCreateNextSpawnData(@Nullable Level $$0, RandomSource $$1, BlockPos $$2) {
/* 276 */     if (this.nextSpawnData != null) {
/* 277 */       return this.nextSpawnData;
/*     */     }
/* 279 */     setNextSpawnData($$0, $$2, this.spawnPotentials.getRandom($$1).map(WeightedEntry.Wrapper::getData).orElseGet(SpawnData::new));
/* 280 */     return this.nextSpawnData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSpin() {
/* 286 */     return this.spin;
/*     */   }
/*     */   
/*     */   public double getoSpin() {
/* 290 */     return this.oSpin;
/*     */   }
/*     */   
/*     */   public abstract void broadcastEvent(Level paramLevel, BlockPos paramBlockPos, int paramInt);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\BaseSpawner.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */