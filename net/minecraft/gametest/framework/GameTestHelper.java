/*     */ package net.minecraft.gametest.framework;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.embedded.EmbeddedChannel;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.IntPredicate;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.LongStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.Connection;
/*     */ import net.minecraft.network.ConnectionProtocol;
/*     */ import net.minecraft.network.protocol.PacketFlow;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.commands.FillBiomeCommand;
/*     */ import net.minecraft.server.level.ClientInformation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.server.network.CommonListenerCookie;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.npc.InventoryCarrier;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.ButtonBlock;
/*     */ import net.minecraft.world.level.block.LeverBlock;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.entity.EntityTypeTest;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class GameTestHelper {
/*     */   public GameTestHelper(GameTestInfo $$0) {
/*  74 */     this.testInfo = $$0;
/*     */   }
/*     */   private final GameTestInfo testInfo;
/*     */   private boolean finalCheckAdded;
/*     */   
/*     */   public ServerLevel getLevel() {
/*  80 */     return this.testInfo.getLevel();
/*     */   }
/*     */   
/*     */   public BlockState getBlockState(BlockPos $$0) {
/*  84 */     return getLevel().getBlockState(absolutePos($$0));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity getBlockEntity(BlockPos $$0) {
/*  89 */     return getLevel().getBlockEntity(absolutePos($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void killAllEntities() {
/*  95 */     killAllEntitiesOfClass(Entity.class);
/*     */   }
/*     */   
/*     */   public void killAllEntitiesOfClass(Class $$0) {
/*  99 */     AABB $$1 = getBounds();
/* 100 */     List<Entity> $$2 = getLevel().getEntitiesOfClass($$0, $$1.inflate(1.0D), $$0 -> !($$0 instanceof Player));
/* 101 */     $$2.forEach(Entity::kill);
/*     */   }
/*     */   
/*     */   public ItemEntity spawnItem(Item $$0, float $$1, float $$2, float $$3) {
/* 105 */     ServerLevel $$4 = getLevel();
/* 106 */     Vec3 $$5 = absoluteVec(new Vec3($$1, $$2, $$3));
/* 107 */     ItemEntity $$6 = new ItemEntity((Level)$$4, $$5.x, $$5.y, $$5.z, new ItemStack((ItemLike)$$0, 1));
/* 108 */     $$6.setDeltaMovement(0.0D, 0.0D, 0.0D);
/* 109 */     $$4.addFreshEntity((Entity)$$6);
/* 110 */     return $$6;
/*     */   }
/*     */   
/*     */   public ItemEntity spawnItem(Item $$0, BlockPos $$1) {
/* 114 */     return spawnItem($$0, $$1.getX(), $$1.getY(), $$1.getZ());
/*     */   }
/*     */   
/*     */   public <E extends Entity> E spawn(EntityType<E> $$0, BlockPos $$1) {
/* 118 */     return spawn($$0, Vec3.atBottomCenterOf((Vec3i)$$1));
/*     */   }
/*     */   
/*     */   public <E extends Entity> E spawn(EntityType<E> $$0, Vec3 $$1) {
/* 122 */     ServerLevel $$2 = getLevel();
/* 123 */     Entity entity = $$0.create((Level)$$2);
/* 124 */     if (entity == null) {
/* 125 */       throw new NullPointerException("Failed to create entity " + $$0.builtInRegistryHolder().key().location());
/*     */     }
/* 127 */     if (entity instanceof Mob) { Mob $$4 = (Mob)entity;
/* 128 */       $$4.setPersistenceRequired(); }
/*     */     
/* 130 */     Vec3 $$5 = absoluteVec($$1);
/* 131 */     entity.moveTo($$5.x, $$5.y, $$5.z, entity.getYRot(), entity.getXRot());
/* 132 */     $$2.addFreshEntity(entity);
/* 133 */     return (E)entity;
/*     */   }
/*     */   
/*     */   public <E extends Entity> E spawn(EntityType<E> $$0, int $$1, int $$2, int $$3) {
/* 137 */     return spawn($$0, new BlockPos($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public <E extends Entity> E spawn(EntityType<E> $$0, float $$1, float $$2, float $$3) {
/* 141 */     return spawn($$0, new Vec3($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public <E extends Mob> E spawnWithNoFreeWill(EntityType<E> $$0, BlockPos $$1) {
/* 145 */     Mob mob = spawn($$0, $$1);
/* 146 */     mob.removeFreeWill();
/* 147 */     return (E)mob;
/*     */   }
/*     */   
/*     */   public <E extends Mob> E spawnWithNoFreeWill(EntityType<E> $$0, int $$1, int $$2, int $$3) {
/* 151 */     return spawnWithNoFreeWill($$0, new BlockPos($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public <E extends Mob> E spawnWithNoFreeWill(EntityType<E> $$0, Vec3 $$1) {
/* 155 */     Mob mob = spawn($$0, $$1);
/* 156 */     mob.removeFreeWill();
/* 157 */     return (E)mob;
/*     */   }
/*     */   
/*     */   public <E extends Mob> E spawnWithNoFreeWill(EntityType<E> $$0, float $$1, float $$2, float $$3) {
/* 161 */     return spawnWithNoFreeWill($$0, new Vec3($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public GameTestSequence walkTo(Mob $$0, BlockPos $$1, float $$2) {
/* 165 */     return startSequence().thenExecuteAfter(2, () -> {
/*     */           Path $$3 = $$0.getNavigation().createPath(absolutePos($$1), 0);
/*     */           $$0.getNavigation().moveTo($$3, $$2);
/*     */         });
/*     */   }
/*     */   
/*     */   public void pressButton(int $$0, int $$1, int $$2) {
/* 172 */     pressButton(new BlockPos($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   public void pressButton(BlockPos $$0) {
/* 176 */     assertBlockState($$0, $$0 -> $$0.is(BlockTags.BUTTONS), () -> "Expected button");
/*     */     
/* 178 */     BlockPos $$1 = absolutePos($$0);
/* 179 */     BlockState $$2 = getLevel().getBlockState($$1);
/*     */     
/* 181 */     ButtonBlock $$3 = (ButtonBlock)$$2.getBlock();
/* 182 */     $$3.press($$2, (Level)getLevel(), $$1);
/*     */   }
/*     */   
/*     */   public void useBlock(BlockPos $$0) {
/* 186 */     useBlock($$0, makeMockPlayer());
/*     */   }
/*     */   
/*     */   public void useBlock(BlockPos $$0, Player $$1) {
/* 190 */     BlockPos $$2 = absolutePos($$0);
/* 191 */     useBlock($$0, $$1, new BlockHitResult(Vec3.atCenterOf((Vec3i)$$2), Direction.NORTH, $$2, true));
/*     */   }
/*     */   
/*     */   public void useBlock(BlockPos $$0, Player $$1, BlockHitResult $$2) {
/* 195 */     BlockPos $$3 = absolutePos($$0);
/* 196 */     BlockState $$4 = getLevel().getBlockState($$3);
/* 197 */     InteractionResult $$5 = $$4.use((Level)getLevel(), $$1, InteractionHand.MAIN_HAND, $$2);
/* 198 */     if (!$$5.consumesAction()) {
/* 199 */       UseOnContext $$6 = new UseOnContext($$1, InteractionHand.MAIN_HAND, $$2);
/* 200 */       $$1.getItemInHand(InteractionHand.MAIN_HAND).useOn($$6);
/*     */     } 
/*     */   }
/*     */   
/*     */   public LivingEntity makeAboutToDrown(LivingEntity $$0) {
/* 205 */     $$0.setAirSupply(0);
/* 206 */     $$0.setHealth(0.25F);
/* 207 */     return $$0;
/*     */   }
/*     */   
/*     */   public Player makeMockSurvivalPlayer() {
/* 211 */     return new Player((Level)getLevel(), BlockPos.ZERO, 0.0F, new GameProfile(UUID.randomUUID(), "test-mock-player"))
/*     */       {
/*     */         public boolean isSpectator() {
/* 214 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isCreative() {
/* 219 */           return false;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public LivingEntity withLowHealth(LivingEntity $$0) {
/* 225 */     $$0.setHealth(0.25F);
/* 226 */     return $$0;
/*     */   }
/*     */   
/*     */   public Player makeMockPlayer() {
/* 230 */     return new Player((Level)getLevel(), BlockPos.ZERO, 0.0F, new GameProfile(UUID.randomUUID(), "test-mock-player"))
/*     */       {
/*     */         public boolean isSpectator() {
/* 233 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isCreative() {
/* 238 */           return true;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isLocalPlayer() {
/* 243 */           return true;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated(forRemoval = true)
/*     */   public ServerPlayer makeMockServerPlayerInLevel() {
/* 254 */     CommonListenerCookie $$0 = CommonListenerCookie.createInitial(new GameProfile(UUID.randomUUID(), "test-mock-player"));
/* 255 */     ServerPlayer $$1 = new ServerPlayer(getLevel().getServer(), getLevel(), $$0.gameProfile(), $$0.clientInformation())
/*     */       {
/*     */         public boolean isSpectator() {
/* 258 */           return false;
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean isCreative() {
/* 263 */           return true;
/*     */         }
/*     */       };
/* 266 */     Connection $$2 = new Connection(PacketFlow.SERVERBOUND);
/*     */ 
/*     */     
/* 269 */     EmbeddedChannel $$3 = new EmbeddedChannel(new ChannelHandler[] { (ChannelHandler)$$2 });
/* 270 */     $$3.attr(Connection.ATTRIBUTE_SERVERBOUND_PROTOCOL).set(ConnectionProtocol.PLAY.codec(PacketFlow.SERVERBOUND));
/* 271 */     getLevel().getServer().getPlayerList().placeNewPlayer($$2, $$1, $$0);
/* 272 */     return $$1;
/*     */   }
/*     */   
/*     */   public void pullLever(int $$0, int $$1, int $$2) {
/* 276 */     pullLever(new BlockPos($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   public void pullLever(BlockPos $$0) {
/* 280 */     assertBlockPresent(Blocks.LEVER, $$0);
/*     */     
/* 282 */     BlockPos $$1 = absolutePos($$0);
/* 283 */     BlockState $$2 = getLevel().getBlockState($$1);
/*     */     
/* 285 */     LeverBlock $$3 = (LeverBlock)$$2.getBlock();
/* 286 */     $$3.pull($$2, (Level)getLevel(), $$1);
/*     */   }
/*     */   
/*     */   public void pulseRedstone(BlockPos $$0, long $$1) {
/* 290 */     setBlock($$0, Blocks.REDSTONE_BLOCK);
/* 291 */     runAfterDelay($$1, () -> setBlock($$0, Blocks.AIR));
/*     */   }
/*     */   
/*     */   public void destroyBlock(BlockPos $$0) {
/* 295 */     getLevel().destroyBlock(absolutePos($$0), false, null);
/*     */   }
/*     */   
/*     */   public void setBlock(int $$0, int $$1, int $$2, Block $$3) {
/* 299 */     setBlock(new BlockPos($$0, $$1, $$2), $$3);
/*     */   }
/*     */   
/*     */   public void setBlock(int $$0, int $$1, int $$2, BlockState $$3) {
/* 303 */     setBlock(new BlockPos($$0, $$1, $$2), $$3);
/*     */   }
/*     */   
/*     */   public void setBlock(BlockPos $$0, Block $$1) {
/* 307 */     setBlock($$0, $$1.defaultBlockState());
/*     */   }
/*     */   
/*     */   public void setBlock(BlockPos $$0, BlockState $$1) {
/* 311 */     getLevel().setBlock(absolutePos($$0), $$1, 3);
/*     */   }
/*     */   
/*     */   public void setNight() {
/* 315 */     setDayTime(13000);
/*     */   }
/*     */   
/*     */   public void setDayTime(int $$0) {
/* 319 */     getLevel().setDayTime($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void assertBlockPresent(Block $$0, int $$1, int $$2, int $$3) {
/* 325 */     assertBlockPresent($$0, new BlockPos($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public void assertBlockPresent(Block $$0, BlockPos $$1) {
/* 329 */     BlockState $$2 = getBlockState($$1);
/* 330 */     assertBlock($$1, $$2 -> $$0.is($$1), "Expected " + $$0.getName().getString() + ", got " + $$2.getBlock().getName().getString());
/*     */   }
/*     */   
/*     */   public void assertBlockNotPresent(Block $$0, int $$1, int $$2, int $$3) {
/* 334 */     assertBlockNotPresent($$0, new BlockPos($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public void assertBlockNotPresent(Block $$0, BlockPos $$1) {
/* 338 */     assertBlock($$1, $$2 -> !getBlockState($$0).is($$1), "Did not expect " + $$0.getName().getString());
/*     */   }
/*     */   
/*     */   public void succeedWhenBlockPresent(Block $$0, int $$1, int $$2, int $$3) {
/* 342 */     succeedWhenBlockPresent($$0, new BlockPos($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public void succeedWhenBlockPresent(Block $$0, BlockPos $$1) {
/* 346 */     succeedWhen(() -> assertBlockPresent($$0, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void assertBlock(BlockPos $$0, Predicate<Block> $$1, String $$2) {
/* 352 */     assertBlock($$0, $$1, () -> $$0);
/*     */   }
/*     */   
/*     */   public void assertBlock(BlockPos $$0, Predicate<Block> $$1, Supplier<String> $$2) {
/* 356 */     assertBlockState($$0, $$1 -> $$0.test($$1.getBlock()), $$2);
/*     */   }
/*     */   
/*     */   public <T extends Comparable<T>> void assertBlockProperty(BlockPos $$0, Property<T> $$1, T $$2) {
/* 360 */     BlockState $$3 = getBlockState($$0);
/* 361 */     boolean $$4 = $$3.hasProperty($$1);
/* 362 */     if (!$$4 || !$$3.getValue($$1).equals($$2)) {
/*     */ 
/*     */       
/* 365 */       String $$5 = $$4 ? ("was " + $$3.getValue($$1)) : ("property " + $$1.getName() + " is missing");
/* 366 */       String $$6 = String.format(Locale.ROOT, "Expected property %s to be %s, %s", new Object[] { $$1.getName(), $$2, $$5 });
/* 367 */       throw new GameTestAssertPosException($$6, absolutePos($$0), $$0, this.testInfo.getTick());
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T extends Comparable<T>> void assertBlockProperty(BlockPos $$0, Property<T> $$1, Predicate<T> $$2, String $$3) {
/* 372 */     assertBlockState($$0, $$2 -> {
/*     */           if (!$$2.hasProperty($$0)) {
/*     */             return false;
/*     */           }
/*     */           Comparable comparable = $$2.getValue($$0);
/*     */           return $$1.test(comparable);
/*     */         }() -> $$0);
/*     */   }
/*     */   
/*     */   public void assertBlockState(BlockPos $$0, Predicate<BlockState> $$1, Supplier<String> $$2) {
/* 382 */     BlockState $$3 = getBlockState($$0);
/* 383 */     if (!$$1.test($$3)) {
/* 384 */       throw new GameTestAssertPosException((String)$$2.get(), absolutePos($$0), $$0, this.testInfo.getTick());
/*     */     }
/*     */   }
/*     */   
/*     */   public void assertRedstoneSignal(BlockPos $$0, Direction $$1, IntPredicate $$2, Supplier<String> $$3) {
/* 389 */     BlockPos $$4 = absolutePos($$0);
/* 390 */     ServerLevel $$5 = getLevel();
/* 391 */     BlockState $$6 = $$5.getBlockState($$4);
/* 392 */     int $$7 = $$6.getSignal((BlockGetter)$$5, $$4, $$1);
/* 393 */     if (!$$2.test($$7)) {
/* 394 */       throw new GameTestAssertPosException((String)$$3.get(), $$4, $$0, this.testInfo.getTick());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void assertEntityPresent(EntityType<?> $$0) {
/* 401 */     List<? extends Entity> $$1 = getLevel().getEntities((EntityTypeTest)$$0, getBounds(), Entity::isAlive);
/* 402 */     if ($$1.isEmpty()) {
/* 403 */       throw new GameTestAssertException("Expected " + $$0.toShortString() + " to exist");
/*     */     }
/*     */   }
/*     */   
/*     */   public void assertEntityPresent(EntityType<?> $$0, int $$1, int $$2, int $$3) {
/* 408 */     assertEntityPresent($$0, new BlockPos($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public void assertEntityPresent(EntityType<?> $$0, BlockPos $$1) {
/* 412 */     BlockPos $$2 = absolutePos($$1);
/* 413 */     List<? extends Entity> $$3 = getLevel().getEntities((EntityTypeTest)$$0, new AABB($$2), Entity::isAlive);
/* 414 */     if ($$3.isEmpty()) {
/* 415 */       throw new GameTestAssertPosException("Expected " + $$0.toShortString(), $$2, $$1, this.testInfo.getTick());
/*     */     }
/*     */   }
/*     */   
/*     */   public void assertEntityPresent(EntityType<?> $$0, Vec3 $$1, Vec3 $$2) {
/* 420 */     List<? extends Entity> $$3 = getLevel().getEntities((EntityTypeTest)$$0, new AABB($$1, $$2), Entity::isAlive);
/* 421 */     if ($$3.isEmpty()) {
/* 422 */       throw new GameTestAssertPosException("Expected " + $$0.toShortString() + " between ", BlockPos.containing($$1), BlockPos.containing($$2), this.testInfo.getTick());
/*     */     }
/*     */   }
/*     */   
/*     */   public void assertEntitiesPresent(EntityType<?> $$0, int $$1) {
/* 427 */     List<? extends Entity> $$2 = getLevel().getEntities((EntityTypeTest)$$0, getBounds(), Entity::isAlive);
/* 428 */     if ($$2.size() != $$1) {
/* 429 */       throw new GameTestAssertException("Expected " + $$1 + " of type " + $$0.toShortString() + " to exist, found " + $$2.size());
/*     */     }
/*     */   }
/*     */   
/*     */   public void assertEntitiesPresent(EntityType<?> $$0, BlockPos $$1, int $$2, double $$3) {
/* 434 */     BlockPos $$4 = absolutePos($$1);
/* 435 */     List<? extends Entity> $$5 = (List)getEntities($$0, $$1, $$3);
/* 436 */     if ($$5.size() != $$2) {
/* 437 */       throw new GameTestAssertPosException("Expected " + $$2 + " entities of type " + $$0.toShortString() + ", actual number of entities found=" + $$5
/* 438 */           .size(), $$4, $$1, this.testInfo.getTick());
/*     */     }
/*     */   }
/*     */   
/*     */   public void assertEntityPresent(EntityType<?> $$0, BlockPos $$1, double $$2) {
/* 443 */     List<? extends Entity> $$3 = (List)getEntities($$0, $$1, $$2);
/* 444 */     if ($$3.isEmpty()) {
/* 445 */       BlockPos $$4 = absolutePos($$1);
/* 446 */       throw new GameTestAssertPosException("Expected " + $$0.toShortString(), $$4, $$1, this.testInfo.getTick());
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T extends Entity> List<T> getEntities(EntityType<T> $$0, BlockPos $$1, double $$2) {
/* 451 */     BlockPos $$3 = absolutePos($$1);
/* 452 */     return getLevel().getEntities((EntityTypeTest)$$0, (new AABB($$3)).inflate($$2), Entity::isAlive);
/*     */   }
/*     */   
/*     */   public void assertEntityInstancePresent(Entity $$0, int $$1, int $$2, int $$3) {
/* 456 */     assertEntityInstancePresent($$0, new BlockPos($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public void assertEntityInstancePresent(Entity $$0, BlockPos $$1) {
/* 460 */     BlockPos $$2 = absolutePos($$1);
/* 461 */     List<? extends Entity> $$3 = getLevel().getEntities((EntityTypeTest)$$0.getType(), new AABB($$2), Entity::isAlive);
/* 462 */     $$3.stream().filter($$1 -> ($$1 == $$0)).findFirst().orElseThrow(() -> new GameTestAssertPosException("Expected " + $$0.getType().toShortString(), $$1, $$2, this.testInfo.getTick()));
/*     */   }
/*     */   
/*     */   public void assertItemEntityCountIs(Item $$0, BlockPos $$1, double $$2, int $$3) {
/* 466 */     BlockPos $$4 = absolutePos($$1);
/* 467 */     List<ItemEntity> $$5 = getLevel().getEntities((EntityTypeTest)EntityType.ITEM, (new AABB($$4)).inflate($$2), Entity::isAlive);
/*     */     
/* 469 */     int $$6 = 0;
/* 470 */     for (ItemEntity $$7 : $$5) {
/* 471 */       ItemStack $$8 = $$7.getItem();
/* 472 */       if ($$8.is($$0)) {
/* 473 */         $$6 += $$8.getCount();
/*     */       }
/*     */     } 
/*     */     
/* 477 */     if ($$6 != $$3) {
/* 478 */       throw new GameTestAssertPosException("Expected " + $$3 + " " + $$0.getDescription().getString() + " items to exist (found " + $$6 + ")", $$4, $$1, this.testInfo.getTick());
/*     */     }
/*     */   }
/*     */   
/*     */   public void assertItemEntityPresent(Item $$0, BlockPos $$1, double $$2) {
/* 483 */     BlockPos $$3 = absolutePos($$1);
/* 484 */     List<? extends Entity> $$4 = getLevel().getEntities((EntityTypeTest)EntityType.ITEM, (new AABB($$3)).inflate($$2), Entity::isAlive);
/* 485 */     for (Entity $$5 : $$4) {
/* 486 */       ItemEntity $$6 = (ItemEntity)$$5;
/* 487 */       if ($$6.getItem().getItem().equals($$0)) {
/*     */         return;
/*     */       }
/*     */     } 
/* 491 */     throw new GameTestAssertPosException("Expected " + $$0.getDescription().getString() + " item", $$3, $$1, this.testInfo.getTick());
/*     */   }
/*     */   
/*     */   public void assertItemEntityNotPresent(Item $$0, BlockPos $$1, double $$2) {
/* 495 */     BlockPos $$3 = absolutePos($$1);
/* 496 */     List<? extends Entity> $$4 = getLevel().getEntities((EntityTypeTest)EntityType.ITEM, (new AABB($$3)).inflate($$2), Entity::isAlive);
/* 497 */     for (Entity $$5 : $$4) {
/* 498 */       ItemEntity $$6 = (ItemEntity)$$5;
/* 499 */       if ($$6.getItem().getItem().equals($$0)) {
/* 500 */         throw new GameTestAssertPosException("Did not expect " + $$0.getDescription().getString() + " item", $$3, $$1, this.testInfo.getTick());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void assertItemEntityPresent(Item $$0) {
/* 506 */     List<? extends Entity> $$1 = getLevel().getEntities((EntityTypeTest)EntityType.ITEM, getBounds(), Entity::isAlive);
/* 507 */     for (Entity $$2 : $$1) {
/* 508 */       ItemEntity $$3 = (ItemEntity)$$2;
/* 509 */       if ($$3.getItem().getItem().equals($$0)) {
/*     */         return;
/*     */       }
/*     */     } 
/* 513 */     throw new GameTestAssertException("Expected " + $$0.getDescription().getString() + " item");
/*     */   }
/*     */   
/*     */   public void assertItemEntityNotPresent(Item $$0) {
/* 517 */     List<? extends Entity> $$1 = getLevel().getEntities((EntityTypeTest)EntityType.ITEM, getBounds(), Entity::isAlive);
/* 518 */     for (Entity $$2 : $$1) {
/* 519 */       ItemEntity $$3 = (ItemEntity)$$2;
/* 520 */       if ($$3.getItem().getItem().equals($$0)) {
/* 521 */         throw new GameTestAssertException("Did not expect " + $$0.getDescription().getString() + " item");
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void assertEntityNotPresent(EntityType<?> $$0) {
/* 527 */     List<? extends Entity> $$1 = getLevel().getEntities((EntityTypeTest)$$0, getBounds(), Entity::isAlive);
/* 528 */     if (!$$1.isEmpty()) {
/* 529 */       throw new GameTestAssertException("Did not expect " + $$0.toShortString() + " to exist");
/*     */     }
/*     */   }
/*     */   
/*     */   public void assertEntityNotPresent(EntityType<?> $$0, int $$1, int $$2, int $$3) {
/* 534 */     assertEntityNotPresent($$0, new BlockPos($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public void assertEntityNotPresent(EntityType<?> $$0, BlockPos $$1) {
/* 538 */     BlockPos $$2 = absolutePos($$1);
/* 539 */     List<? extends Entity> $$3 = getLevel().getEntities((EntityTypeTest)$$0, new AABB($$2), Entity::isAlive);
/* 540 */     if (!$$3.isEmpty()) {
/* 541 */       throw new GameTestAssertPosException("Did not expect " + $$0.toShortString(), $$2, $$1, this.testInfo.getTick());
/*     */     }
/*     */   }
/*     */   
/*     */   public void assertEntityTouching(EntityType<?> $$0, double $$1, double $$2, double $$3) {
/* 546 */     Vec3 $$4 = new Vec3($$1, $$2, $$3);
/* 547 */     Vec3 $$5 = absoluteVec($$4);
/* 548 */     Predicate<? super Entity> $$6 = $$1 -> $$1.getBoundingBox().intersects($$0, $$0);
/* 549 */     List<? extends Entity> $$7 = getLevel().getEntities((EntityTypeTest)$$0, getBounds(), $$6);
/* 550 */     if ($$7.isEmpty()) {
/* 551 */       throw new GameTestAssertException("Expected " + $$0.toShortString() + " to touch " + $$5 + " (relative " + $$4 + ")");
/*     */     }
/*     */   }
/*     */   
/*     */   public void assertEntityNotTouching(EntityType<?> $$0, double $$1, double $$2, double $$3) {
/* 556 */     Vec3 $$4 = new Vec3($$1, $$2, $$3);
/* 557 */     Vec3 $$5 = absoluteVec($$4);
/* 558 */     Predicate<? super Entity> $$6 = $$1 -> !$$1.getBoundingBox().intersects($$0, $$0);
/* 559 */     List<? extends Entity> $$7 = getLevel().getEntities((EntityTypeTest)$$0, getBounds(), $$6);
/* 560 */     if ($$7.isEmpty()) {
/* 561 */       throw new GameTestAssertException("Did not expect " + $$0.toShortString() + " to touch " + $$5 + " (relative " + $$4 + ")");
/*     */     }
/*     */   }
/*     */   
/*     */   public <E extends Entity, T> void assertEntityData(BlockPos $$0, EntityType<E> $$1, Function<? super E, T> $$2, @Nullable T $$3) {
/* 566 */     BlockPos $$4 = absolutePos($$0);
/* 567 */     List<E> $$5 = getLevel().getEntities((EntityTypeTest)$$1, new AABB($$4), Entity::isAlive);
/* 568 */     if ($$5.isEmpty()) {
/* 569 */       throw new GameTestAssertPosException("Expected " + $$1.toShortString(), $$4, $$0, this.testInfo.getTick());
/*     */     }
/*     */     
/* 572 */     for (Entity entity : $$5) {
/* 573 */       T $$7 = $$2.apply((E)entity);
/*     */       
/* 575 */       if ($$7 == null) {
/* 576 */         if ($$3 != null) {
/* 577 */           throw new GameTestAssertException("Expected entity data to be: " + $$3 + ", but was: " + $$7);
/*     */         }
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 583 */       if (!$$7.equals($$3)) {
/* 584 */         throw new GameTestAssertException("Expected entity data to be: " + $$3 + ", but was: " + $$7);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public <E extends LivingEntity> void assertEntityIsHolding(BlockPos $$0, EntityType<E> $$1, Item $$2) {
/* 590 */     BlockPos $$3 = absolutePos($$0);
/*     */     
/* 592 */     List<E> $$4 = getLevel().getEntities((EntityTypeTest)$$1, new AABB($$3), Entity::isAlive);
/* 593 */     if ($$4.isEmpty()) {
/* 594 */       throw new GameTestAssertPosException("Expected entity of type: " + $$1, $$3, $$0, getTick());
/*     */     }
/*     */     
/* 597 */     for (LivingEntity livingEntity : $$4) {
/* 598 */       if (livingEntity.isHolding($$2)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 603 */     throw new GameTestAssertPosException("Entity should be holding: " + $$2, $$3, $$0, getTick());
/*     */   }
/*     */   
/*     */   public <E extends Entity & InventoryCarrier> void assertEntityInventoryContains(BlockPos $$0, EntityType<E> $$1, Item $$2) {
/* 607 */     BlockPos $$3 = absolutePos($$0);
/*     */     
/* 609 */     List<E> $$4 = getLevel().getEntities((EntityTypeTest)$$1, new AABB($$3), $$0 -> ((Entity)$$0).isAlive());
/* 610 */     if ($$4.isEmpty()) {
/* 611 */       throw new GameTestAssertPosException("Expected " + $$1.toShortString() + " to exist", $$3, $$0, getTick());
/*     */     }
/*     */     
/* 614 */     for (Entity entity : $$4) {
/* 615 */       if (((InventoryCarrier)entity).getInventory().hasAnyMatching($$1 -> $$1.is($$0))) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 620 */     throw new GameTestAssertPosException("Entity inventory should contain: " + $$2, $$3, $$0, getTick());
/*     */   }
/*     */   
/*     */   public void assertContainerEmpty(BlockPos $$0) {
/* 624 */     BlockPos $$1 = absolutePos($$0);
/* 625 */     BlockEntity $$2 = getLevel().getBlockEntity($$1);
/* 626 */     if ($$2 instanceof BaseContainerBlockEntity && !((BaseContainerBlockEntity)$$2).isEmpty()) {
/* 627 */       throw new GameTestAssertException("Container should be empty");
/*     */     }
/*     */   }
/*     */   
/*     */   public void assertContainerContains(BlockPos $$0, Item $$1) {
/* 632 */     BlockPos $$2 = absolutePos($$0);
/* 633 */     BlockEntity $$3 = getLevel().getBlockEntity($$2);
/* 634 */     if (!($$3 instanceof BaseContainerBlockEntity)) {
/* 635 */       throw new GameTestAssertException("Expected a container at " + $$0 + ", found " + BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey($$3.getType()));
/*     */     }
/* 637 */     if (((BaseContainerBlockEntity)$$3).countItem($$1) != 1) {
/* 638 */       throw new GameTestAssertException("Container should contain: " + $$1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void assertSameBlockStates(BoundingBox $$0, BlockPos $$1) {
/* 646 */     BlockPos.betweenClosedStream($$0)
/* 647 */       .forEach($$2 -> {
/*     */           BlockPos $$3 = $$0.offset($$2.getX() - $$1.minX(), $$2.getY() - $$1.minY(), $$2.getZ() - $$1.minZ());
/*     */           assertSameBlockState($$2, $$3);
/*     */         });
/*     */   }
/*     */   
/*     */   public void assertSameBlockState(BlockPos $$0, BlockPos $$1) {
/* 654 */     BlockState $$2 = getBlockState($$0);
/* 655 */     BlockState $$3 = getBlockState($$1);
/* 656 */     if ($$2 != $$3) {
/* 657 */       fail("Incorrect state. Expected " + $$3 + ", got " + $$2, $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void assertAtTickTimeContainerContains(long $$0, BlockPos $$1, Item $$2) {
/* 662 */     runAtTickTime($$0, () -> assertContainerContains($$0, $$1));
/*     */   }
/*     */   
/*     */   public void assertAtTickTimeContainerEmpty(long $$0, BlockPos $$1) {
/* 666 */     runAtTickTime($$0, () -> assertContainerEmpty($$0));
/*     */   }
/*     */   
/*     */   public <E extends Entity, T> void succeedWhenEntityData(BlockPos $$0, EntityType<E> $$1, Function<E, T> $$2, T $$3) {
/* 670 */     succeedWhen(() -> assertEntityData($$0, $$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public <E extends Entity> void assertEntityProperty(E $$0, Predicate<E> $$1, String $$2) {
/* 674 */     if (!$$1.test($$0)) {
/* 675 */       throw new GameTestAssertException("Entity " + $$0 + " failed " + $$2 + " test");
/*     */     }
/*     */   }
/*     */   
/*     */   public <E extends Entity, T> void assertEntityProperty(E $$0, Function<E, T> $$1, String $$2, T $$3) {
/* 680 */     T $$4 = $$1.apply($$0);
/* 681 */     if (!$$4.equals($$3)) {
/* 682 */       throw new GameTestAssertException("Entity " + $$0 + " value " + $$2 + "=" + $$4 + " is not equal to expected " + $$3);
/*     */     }
/*     */   }
/*     */   
/*     */   public void assertLivingEntityHasMobEffect(LivingEntity $$0, MobEffect $$1, int $$2) {
/* 687 */     MobEffectInstance $$3 = $$0.getEffect($$1);
/* 688 */     if ($$3 == null || $$3.getAmplifier() != $$2) {
/* 689 */       int $$4 = $$2 + 1;
/* 690 */       throw new GameTestAssertException("Entity " + $$0 + " failed has " + $$1.getDescriptionId() + " x " + $$4 + " test");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void succeedWhenEntityPresent(EntityType<?> $$0, int $$1, int $$2, int $$3) {
/* 695 */     succeedWhenEntityPresent($$0, new BlockPos($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public void succeedWhenEntityPresent(EntityType<?> $$0, BlockPos $$1) {
/* 699 */     succeedWhen(() -> assertEntityPresent($$0, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void succeedWhenEntityNotPresent(EntityType<?> $$0, int $$1, int $$2, int $$3) {
/* 705 */     succeedWhenEntityNotPresent($$0, new BlockPos($$1, $$2, $$3));
/*     */   }
/*     */   
/*     */   public void succeedWhenEntityNotPresent(EntityType<?> $$0, BlockPos $$1) {
/* 709 */     succeedWhen(() -> assertEntityNotPresent($$0, $$1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void succeed() {
/* 717 */     this.testInfo.succeed();
/*     */   }
/*     */   
/*     */   private void ensureSingleFinalCheck() {
/* 721 */     if (this.finalCheckAdded) {
/* 722 */       throw new IllegalStateException("This test already has final clause");
/*     */     }
/* 724 */     this.finalCheckAdded = true;
/*     */   }
/*     */   
/*     */   public void succeedIf(Runnable $$0) {
/* 728 */     ensureSingleFinalCheck();
/* 729 */     this.testInfo.createSequence()
/* 730 */       .thenWaitUntil(0L, $$0)
/* 731 */       .thenSucceed();
/*     */   }
/*     */   
/*     */   public void succeedWhen(Runnable $$0) {
/* 735 */     ensureSingleFinalCheck();
/* 736 */     this.testInfo.createSequence()
/* 737 */       .thenWaitUntil($$0)
/* 738 */       .thenSucceed();
/*     */   }
/*     */   
/*     */   public void succeedOnTickWhen(int $$0, Runnable $$1) {
/* 742 */     ensureSingleFinalCheck();
/* 743 */     this.testInfo.createSequence()
/* 744 */       .thenWaitUntil($$0, $$1)
/* 745 */       .thenSucceed();
/*     */   }
/*     */   
/*     */   public void runAtTickTime(long $$0, Runnable $$1) {
/* 749 */     this.testInfo.setRunAtTickTime($$0, $$1);
/*     */   }
/*     */   
/*     */   public void runAfterDelay(long $$0, Runnable $$1) {
/* 753 */     runAtTickTime(this.testInfo.getTick() + $$0, $$1);
/*     */   }
/*     */   
/*     */   public void randomTick(BlockPos $$0) {
/* 757 */     BlockPos $$1 = absolutePos($$0);
/* 758 */     ServerLevel $$2 = getLevel();
/* 759 */     $$2.getBlockState($$1).randomTick($$2, $$1, $$2.random);
/*     */   }
/*     */   
/*     */   public void tickPrecipitation(BlockPos $$0) {
/* 763 */     BlockPos $$1 = absolutePos($$0);
/* 764 */     ServerLevel $$2 = getLevel();
/* 765 */     $$2.tickPrecipitation($$1);
/*     */   }
/*     */   
/*     */   public void tickPrecipitation() {
/* 769 */     AABB $$0 = getRelativeBounds();
/* 770 */     int $$1 = (int)Math.floor($$0.maxX);
/* 771 */     int $$2 = (int)Math.floor($$0.maxZ);
/* 772 */     int $$3 = (int)Math.floor($$0.maxY);
/* 773 */     for (int $$4 = (int)Math.floor($$0.minX); $$4 < $$1; $$4++) {
/* 774 */       for (int $$5 = (int)Math.floor($$0.minZ); $$5 < $$2; $$5++) {
/* 775 */         tickPrecipitation(new BlockPos($$4, $$3, $$5));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getHeight(Heightmap.Types $$0, int $$1, int $$2) {
/* 781 */     BlockPos $$3 = absolutePos(new BlockPos($$1, 0, $$2));
/* 782 */     return relativePos(getLevel().getHeightmapPos($$0, $$3)).getY();
/*     */   }
/*     */   
/*     */   public void fail(String $$0, BlockPos $$1) {
/* 786 */     throw new GameTestAssertPosException($$0, absolutePos($$1), $$1, getTick());
/*     */   }
/*     */   
/*     */   public void fail(String $$0, Entity $$1) {
/* 790 */     throw new GameTestAssertPosException($$0, $$1.blockPosition(), relativePos($$1.blockPosition()), getTick());
/*     */   }
/*     */   
/*     */   public void fail(String $$0) {
/* 794 */     throw new GameTestAssertException($$0);
/*     */   }
/*     */   
/*     */   public void failIf(Runnable $$0) {
/* 798 */     this.testInfo.createSequence()
/* 799 */       .thenWaitUntil($$0)
/* 800 */       .thenFail(() -> new GameTestAssertException("Fail conditions met"));
/*     */   }
/*     */   
/*     */   public void failIfEver(Runnable $$0) {
/* 804 */     LongStream.range(this.testInfo.getTick(), this.testInfo.getTimeoutTicks())
/* 805 */       .forEach($$1 -> {
/*     */           Objects.requireNonNull($$0);
/*     */           this.testInfo.setRunAtTickTime($$1, $$0::run);
/*     */         }); } public GameTestSequence startSequence() {
/* 809 */     return this.testInfo.createSequence();
/*     */   }
/*     */   
/*     */   public BlockPos absolutePos(BlockPos $$0) {
/* 813 */     BlockPos $$1 = this.testInfo.getStructureBlockPos();
/* 814 */     BlockPos $$2 = $$1.offset((Vec3i)$$0);
/* 815 */     return StructureTemplate.transform($$2, Mirror.NONE, this.testInfo.getRotation(), $$1);
/*     */   }
/*     */   
/*     */   public BlockPos relativePos(BlockPos $$0) {
/* 819 */     BlockPos $$1 = this.testInfo.getStructureBlockPos();
/* 820 */     Rotation $$2 = this.testInfo.getRotation().getRotated(Rotation.CLOCKWISE_180);
/* 821 */     BlockPos $$3 = StructureTemplate.transform($$0, Mirror.NONE, $$2, $$1);
/* 822 */     return $$3.subtract((Vec3i)$$1);
/*     */   }
/*     */   
/*     */   public Vec3 absoluteVec(Vec3 $$0) {
/* 826 */     Vec3 $$1 = Vec3.atLowerCornerOf((Vec3i)this.testInfo.getStructureBlockPos());
/* 827 */     return StructureTemplate.transform($$1.add($$0), Mirror.NONE, this.testInfo.getRotation(), this.testInfo.getStructureBlockPos());
/*     */   }
/*     */   
/*     */   public Vec3 relativeVec(Vec3 $$0) {
/* 831 */     Vec3 $$1 = Vec3.atLowerCornerOf((Vec3i)this.testInfo.getStructureBlockPos());
/* 832 */     return StructureTemplate.transform($$0.subtract($$1), Mirror.NONE, this.testInfo.getRotation(), this.testInfo.getStructureBlockPos());
/*     */   }
/*     */   
/*     */   public void assertTrue(boolean $$0, String $$1) {
/* 836 */     if (!$$0) {
/* 837 */       throw new GameTestAssertException($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void assertFalse(boolean $$0, String $$1) {
/* 842 */     if ($$0) {
/* 843 */       throw new GameTestAssertException($$1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getTick() {
/* 850 */     return this.testInfo.getTick();
/*     */   }
/*     */   
/*     */   public AABB getBounds() {
/* 854 */     return this.testInfo.getStructureBounds();
/*     */   }
/*     */   
/*     */   private AABB getRelativeBounds() {
/* 858 */     AABB $$0 = this.testInfo.getStructureBounds();
/* 859 */     return $$0.move(BlockPos.ZERO.subtract((Vec3i)absolutePos(BlockPos.ZERO)));
/*     */   }
/*     */   
/*     */   public void forEveryBlockInStructure(Consumer<BlockPos> $$0) {
/* 863 */     AABB $$1 = getRelativeBounds();
/* 864 */     BlockPos.MutableBlockPos.betweenClosedStream($$1.move(0.0D, 1.0D, 0.0D)).forEach($$0);
/*     */   }
/*     */   
/*     */   public void onEachTick(Runnable $$0) {
/* 868 */     LongStream.range(this.testInfo.getTick(), this.testInfo.getTimeoutTicks()).forEach($$1 -> {
/*     */           Objects.requireNonNull($$0);
/*     */           this.testInfo.setRunAtTickTime($$1, $$0::run);
/*     */         });
/*     */   }
/*     */   public void placeAt(Player $$0, ItemStack $$1, BlockPos $$2, Direction $$3) {
/* 874 */     BlockPos $$4 = absolutePos($$2.relative($$3));
/* 875 */     BlockHitResult $$5 = new BlockHitResult(Vec3.atCenterOf((Vec3i)$$4), $$3, $$4, false);
/* 876 */     UseOnContext $$6 = new UseOnContext($$0, InteractionHand.MAIN_HAND, $$5);
/* 877 */     $$1.useOn($$6);
/*     */   }
/*     */   
/*     */   public void setBiome(ResourceKey<Biome> $$0) {
/* 881 */     AABB $$1 = getBounds();
/* 882 */     BlockPos $$2 = BlockPos.containing($$1.minX, $$1.minY, $$1.minZ);
/* 883 */     BlockPos $$3 = BlockPos.containing($$1.maxX, $$1.maxY, $$1.maxZ);
/* 884 */     Either<Integer, CommandSyntaxException> $$4 = FillBiomeCommand.fill(getLevel(), $$2, $$3, (Holder)getLevel().registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow($$0));
/* 885 */     if ($$4.right().isPresent())
/* 886 */       fail("Failed to set biome for test"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\GameTestHelper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */