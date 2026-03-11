/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.EntityTypeTags;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.animal.Bee;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.BeehiveBlock;
/*     */ import net.minecraft.world.level.block.CampfireBlock;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ 
/*     */ 
/*     */ public class BeehiveBlockEntity
/*     */   extends BlockEntity
/*     */ {
/*     */   public static final String TAG_FLOWER_POS = "FlowerPos";
/*     */   public static final String MIN_OCCUPATION_TICKS = "MinOccupationTicks";
/*     */   public static final String ENTITY_DATA = "EntityData";
/*     */   public static final String TICKS_IN_HIVE = "TicksInHive";
/*     */   public static final String HAS_NECTAR = "HasNectar";
/*     */   public static final String BEES = "Bees";
/*  44 */   private static final List<String> IGNORED_BEE_TAGS = Arrays.asList(new String[] { "Air", "ArmorDropChances", "ArmorItems", "Brain", "CanPickUpLoot", "DeathTime", "FallDistance", "FallFlying", "Fire", "HandDropChances", "HandItems", "HurtByTimestamp", "HurtTime", "LeftHanded", "Motion", "NoGravity", "OnGround", "PortalCooldown", "Pos", "Rotation", "CannotEnterHiveTicks", "TicksSincePollination", "CropsGrownSincePollination", "HivePos", "Passengers", "Leash", "UUID" });
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int MAX_OCCUPANTS = 3;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MIN_TICKS_BEFORE_REENTERING_HIVE = 400;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MIN_OCCUPATION_TICKS_NECTAR = 2400;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int MIN_OCCUPATION_TICKS_NECTARLESS = 600;
/*     */ 
/*     */ 
/*     */   
/*  64 */   private final List<BeeData> stored = Lists.newArrayList();
/*     */   @Nullable
/*     */   private BlockPos savedFlowerPos;
/*     */   
/*     */   public enum BeeReleaseStatus
/*     */   {
/*  70 */     HONEY_DELIVERED,
/*  71 */     BEE_RELEASED,
/*  72 */     EMERGENCY;
/*     */   }
/*     */   
/*     */   public BeehiveBlockEntity(BlockPos $$0, BlockState $$1) {
/*  76 */     super(BlockEntityType.BEEHIVE, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged() {
/*  81 */     if (isFireNearby())
/*     */     {
/*  83 */       emptyAllLivingFromHive((Player)null, this.level.getBlockState(getBlockPos()), BeeReleaseStatus.EMERGENCY);
/*     */     }
/*  85 */     super.setChanged();
/*     */   }
/*     */   
/*     */   public boolean isFireNearby() {
/*  89 */     if (this.level == null) {
/*  90 */       return false;
/*     */     }
/*     */     
/*  93 */     for (BlockPos $$0 : BlockPos.betweenClosed(this.worldPosition.offset(-1, -1, -1), this.worldPosition.offset(1, 1, 1))) {
/*  94 */       if (this.level.getBlockState($$0).getBlock() instanceof net.minecraft.world.level.block.FireBlock) {
/*  95 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  99 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 103 */     return this.stored.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean isFull() {
/* 107 */     return (this.stored.size() == 3);
/*     */   }
/*     */   
/*     */   public void emptyAllLivingFromHive(@Nullable Player $$0, BlockState $$1, BeeReleaseStatus $$2) {
/* 111 */     List<Entity> $$3 = releaseAllOccupants($$1, $$2);
/*     */     
/* 113 */     if ($$0 != null) {
/* 114 */       for (Entity $$4 : $$3) {
/* 115 */         if ($$4 instanceof Bee) { Bee $$5 = (Bee)$$4;
/* 116 */           if ($$0.position().distanceToSqr($$4.position()) <= 16.0D) {
/* 117 */             if (!isSedated()) {
/* 118 */               $$5.setTarget((LivingEntity)$$0); continue;
/*     */             } 
/* 120 */             $$5.setStayOutOfHiveCountdown(400);
/*     */           }  }
/*     */       
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private List<Entity> releaseAllOccupants(BlockState $$0, BeeReleaseStatus $$1) {
/* 129 */     List<Entity> $$2 = Lists.newArrayList();
/* 130 */     this.stored.removeIf($$3 -> releaseOccupant(this.level, this.worldPosition, $$0, $$3, $$1, $$2, this.savedFlowerPos));
/* 131 */     if (!$$2.isEmpty()) {
/* 132 */       super.setChanged();
/*     */     }
/* 134 */     return $$2;
/*     */   }
/*     */   
/*     */   public void addOccupant(Entity $$0, boolean $$1) {
/* 138 */     addOccupantWithPresetTicks($$0, $$1, 0);
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public int getOccupantCount() {
/* 143 */     return this.stored.size();
/*     */   }
/*     */   
/*     */   public static int getHoneyLevel(BlockState $$0) {
/* 147 */     return ((Integer)$$0.getValue((Property)BeehiveBlock.HONEY_LEVEL)).intValue();
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   public boolean isSedated() {
/* 152 */     return CampfireBlock.isSmokeyPos(this.level, getBlockPos());
/*     */   }
/*     */   
/*     */   public void addOccupantWithPresetTicks(Entity $$0, boolean $$1, int $$2) {
/* 156 */     if (this.stored.size() >= 3) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 161 */     $$0.stopRiding();
/* 162 */     $$0.ejectPassengers();
/*     */     
/* 164 */     CompoundTag $$3 = new CompoundTag();
/* 165 */     $$0.save($$3);
/* 166 */     storeBee($$3, $$2, $$1);
/*     */     
/* 168 */     if (this.level != null) {
/* 169 */       if ($$0 instanceof Bee) { Bee $$4 = (Bee)$$0;
/*     */         
/* 171 */         if ($$4.hasSavedFlowerPos() && (!hasSavedFlowerPos() || this.level.random.nextBoolean())) {
/* 172 */           this.savedFlowerPos = $$4.getSavedFlowerPos();
/*     */         } }
/*     */ 
/*     */       
/* 176 */       BlockPos $$5 = getBlockPos();
/* 177 */       this.level.playSound(null, $$5.getX(), $$5.getY(), $$5.getZ(), SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 178 */       this.level.gameEvent(GameEvent.BLOCK_CHANGE, $$5, GameEvent.Context.of($$0, getBlockState()));
/*     */     } 
/*     */ 
/*     */     
/* 182 */     $$0.discard();
/* 183 */     super.setChanged();
/*     */   }
/*     */   
/*     */   public void storeBee(CompoundTag $$0, int $$1, boolean $$2) {
/* 187 */     this.stored.add(new BeeData($$0, $$1, $$2 ? 2400 : 600));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean releaseOccupant(Level $$0, BlockPos $$1, BlockState $$2, BeeData $$3, @Nullable List<Entity> $$4, BeeReleaseStatus $$5, @Nullable BlockPos $$6) {
/* 194 */     if (($$0.isNight() || $$0.isRaining()) && $$5 != BeeReleaseStatus.EMERGENCY) {
/* 195 */       return false;
/*     */     }
/*     */     
/* 198 */     CompoundTag $$7 = $$3.entityData.copy();
/*     */     
/* 200 */     removeIgnoredBeeTags($$7);
/*     */     
/* 202 */     $$7.put("HivePos", (Tag)NbtUtils.writeBlockPos($$1));
/* 203 */     $$7.putBoolean("NoGravity", true);
/*     */     
/* 205 */     Direction $$8 = (Direction)$$2.getValue((Property)BeehiveBlock.FACING);
/* 206 */     BlockPos $$9 = $$1.relative($$8);
/* 207 */     boolean $$10 = !$$0.getBlockState($$9).getCollisionShape((BlockGetter)$$0, $$9).isEmpty();
/*     */     
/* 209 */     if ($$10 && $$5 != BeeReleaseStatus.EMERGENCY) {
/* 210 */       return false;
/*     */     }
/*     */     
/* 213 */     Entity $$11 = EntityType.loadEntityRecursive($$7, $$0, $$0 -> $$0);
/*     */     
/* 215 */     if ($$11 != null) {
/*     */       
/* 217 */       if (!$$11.getType().is(EntityTypeTags.BEEHIVE_INHABITORS)) {
/* 218 */         return false;
/*     */       }
/*     */       
/* 221 */       if ($$11 instanceof Bee) { Bee $$12 = (Bee)$$11;
/*     */ 
/*     */         
/* 224 */         if ($$6 != null && !$$12.hasSavedFlowerPos() && $$0.random.nextFloat() < 0.9F) {
/* 225 */           $$12.setSavedFlowerPos($$6);
/*     */         }
/*     */         
/* 228 */         if ($$5 == BeeReleaseStatus.HONEY_DELIVERED) {
/* 229 */           $$12.dropOffNectar();
/*     */           
/* 231 */           if ($$2.is(BlockTags.BEEHIVES, $$0 -> $$0.hasProperty((Property)BeehiveBlock.HONEY_LEVEL))) {
/* 232 */             int $$13 = getHoneyLevel($$2);
/* 233 */             if ($$13 < 5) {
/* 234 */               int $$14 = ($$0.random.nextInt(100) == 0) ? 2 : 1;
/* 235 */               if ($$13 + $$14 > 5) {
/* 236 */                 $$14--;
/*     */               }
/* 238 */               $$0.setBlockAndUpdate($$1, (BlockState)$$2.setValue((Property)BeehiveBlock.HONEY_LEVEL, Integer.valueOf($$13 + $$14)));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 243 */         setBeeReleaseData($$3.ticksInHive, $$12);
/*     */         
/* 245 */         if ($$4 != null) {
/* 246 */           $$4.add($$12);
/*     */         }
/*     */         
/* 249 */         float $$15 = $$11.getBbWidth();
/* 250 */         double $$16 = $$10 ? 0.0D : (0.55D + ($$15 / 2.0F));
/* 251 */         double $$17 = $$1.getX() + 0.5D + $$16 * $$8.getStepX();
/* 252 */         double $$18 = $$1.getY() + 0.5D - ($$11.getBbHeight() / 2.0F);
/* 253 */         double $$19 = $$1.getZ() + 0.5D + $$16 * $$8.getStepZ();
/* 254 */         $$11.moveTo($$17, $$18, $$19, $$11.getYRot(), $$11.getXRot()); }
/*     */ 
/*     */       
/* 257 */       $$0.playSound(null, $$1, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 258 */       $$0.gameEvent(GameEvent.BLOCK_CHANGE, $$1, GameEvent.Context.of($$11, $$0.getBlockState($$1)));
/*     */       
/* 260 */       return $$0.addFreshEntity($$11);
/*     */     } 
/*     */     
/* 263 */     return false;
/*     */   }
/*     */   
/*     */   static void removeIgnoredBeeTags(CompoundTag $$0) {
/* 267 */     for (String $$1 : IGNORED_BEE_TAGS) {
/* 268 */       $$0.remove($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void setBeeReleaseData(int $$0, Bee $$1) {
/* 273 */     int $$2 = $$1.getAge();
/* 274 */     if ($$2 < 0) {
/* 275 */       $$1.setAge(Math.min(0, $$2 + $$0));
/* 276 */     } else if ($$2 > 0) {
/* 277 */       $$1.setAge(Math.max(0, $$2 - $$0));
/*     */     } 
/* 279 */     $$1.setInLoveTime(Math.max(0, $$1.getInLoveTime() - $$0));
/*     */   }
/*     */   
/*     */   private boolean hasSavedFlowerPos() {
/* 283 */     return (this.savedFlowerPos != null);
/*     */   }
/*     */   
/*     */   private static void tickOccupants(Level $$0, BlockPos $$1, BlockState $$2, List<BeeData> $$3, @Nullable BlockPos $$4) {
/* 287 */     boolean $$5 = false;
/* 288 */     Iterator<BeeData> $$6 = $$3.iterator();
/* 289 */     while ($$6.hasNext()) {
/* 290 */       BeeData $$7 = $$6.next();
/* 291 */       if ($$7.ticksInHive > $$7.minOccupationTicks) {
/*     */         
/* 293 */         BeeReleaseStatus $$8 = $$7.entityData.getBoolean("HasNectar") ? BeeReleaseStatus.HONEY_DELIVERED : BeeReleaseStatus.BEE_RELEASED;
/* 294 */         if (releaseOccupant($$0, $$1, $$2, $$7, (List<Entity>)null, $$8, $$4)) {
/* 295 */           $$5 = true;
/* 296 */           $$6.remove();
/*     */         } 
/*     */       } 
/* 299 */       $$7.ticksInHive++;
/*     */     } 
/* 301 */     if ($$5) {
/* 302 */       setChanged($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void serverTick(Level $$0, BlockPos $$1, BlockState $$2, BeehiveBlockEntity $$3) {
/* 307 */     tickOccupants($$0, $$1, $$2, $$3.stored, $$3.savedFlowerPos);
/*     */     
/* 309 */     if (!$$3.stored.isEmpty() && $$0.getRandom().nextDouble() < 0.005D) {
/* 310 */       double $$4 = $$1.getX() + 0.5D;
/* 311 */       double $$5 = $$1.getY();
/* 312 */       double $$6 = $$1.getZ() + 0.5D;
/* 313 */       $$0.playSound(null, $$4, $$5, $$6, SoundEvents.BEEHIVE_WORK, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */     } 
/*     */     
/* 316 */     DebugPackets.sendHiveInfo($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 321 */     super.load($$0);
/*     */     
/* 323 */     this.stored.clear();
/*     */     
/* 325 */     ListTag $$1 = $$0.getList("Bees", 10);
/* 326 */     for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/* 327 */       CompoundTag $$3 = $$1.getCompound($$2);
/* 328 */       BeeData $$4 = new BeeData($$3.getCompound("EntityData").copy(), $$3.getInt("TicksInHive"), $$3.getInt("MinOccupationTicks"));
/* 329 */       this.stored.add($$4);
/*     */     } 
/*     */     
/* 332 */     this.savedFlowerPos = null;
/* 333 */     if ($$0.contains("FlowerPos")) {
/* 334 */       this.savedFlowerPos = NbtUtils.readBlockPos($$0.getCompound("FlowerPos"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/* 340 */     super.saveAdditional($$0);
/*     */     
/* 342 */     $$0.put("Bees", (Tag)writeBees());
/* 343 */     if (hasSavedFlowerPos()) {
/* 344 */       $$0.put("FlowerPos", (Tag)NbtUtils.writeBlockPos(this.savedFlowerPos));
/*     */     }
/*     */   }
/*     */   
/*     */   public ListTag writeBees() {
/* 349 */     ListTag $$0 = new ListTag();
/* 350 */     for (BeeData $$1 : this.stored) {
/* 351 */       CompoundTag $$2 = $$1.entityData.copy();
/* 352 */       $$2.remove("UUID");
/* 353 */       CompoundTag $$3 = new CompoundTag();
/* 354 */       $$3.put("EntityData", (Tag)$$2);
/* 355 */       $$3.putInt("TicksInHive", $$1.ticksInHive);
/* 356 */       $$3.putInt("MinOccupationTicks", $$1.minOccupationTicks);
/* 357 */       $$0.add($$3);
/*     */     } 
/* 359 */     return $$0;
/*     */   }
/*     */   
/*     */   private static class BeeData {
/*     */     final CompoundTag entityData;
/*     */     int ticksInHive;
/*     */     final int minOccupationTicks;
/*     */     
/*     */     BeeData(CompoundTag $$0, int $$1, int $$2) {
/* 368 */       BeehiveBlockEntity.removeIgnoredBeeTags($$0);
/* 369 */       this.entityData = $$0;
/* 370 */       this.ticksInHive = $$1;
/* 371 */       this.minOccupationTicks = $$2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BeehiveBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */