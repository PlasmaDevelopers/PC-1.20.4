/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.BoolArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.DynamicLike;
/*     */ import java.util.Comparator;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
/*     */ import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class GameRules {
/*     */   public static final int DEFAULT_RANDOM_TICK_SPEED = 3;
/*     */   
/*     */   public enum Category {
/*  34 */     PLAYER("gamerule.category.player"),
/*  35 */     MOBS("gamerule.category.mobs"),
/*  36 */     SPAWNING("gamerule.category.spawning"),
/*  37 */     DROPS("gamerule.category.drops"),
/*  38 */     UPDATES("gamerule.category.updates"),
/*  39 */     CHAT("gamerule.category.chat"),
/*  40 */     MISC("gamerule.category.misc");
/*     */     
/*     */     private final String descriptionId;
/*     */ 
/*     */     
/*     */     Category(String $$0) {
/*  46 */       this.descriptionId = $$0;
/*     */     }
/*     */     
/*     */     public String getDescriptionId() {
/*  50 */       return this.descriptionId;
/*     */     }
/*     */   }
/*     */   
/*  54 */   static final Logger LOGGER = LogUtils.getLogger(); private static final Map<Key<?>, Type<?>> GAME_RULE_TYPES;
/*     */   static {
/*  56 */     GAME_RULE_TYPES = Maps.newTreeMap(Comparator.comparing($$0 -> $$0.id));
/*     */   }
/*     */   private static <T extends Value<T>> Key<T> register(String $$0, Category $$1, Type<T> $$2) {
/*  59 */     Key<T> $$3 = new Key<>($$0, $$1);
/*  60 */     Type<?> $$4 = GAME_RULE_TYPES.put($$3, $$2);
/*  61 */     if ($$4 != null) {
/*  62 */       throw new IllegalStateException("Duplicate game rule registration for " + $$0);
/*     */     }
/*  64 */     return $$3;
/*     */   }
/*     */   
/*  67 */   public static final Key<BooleanValue> RULE_DOFIRETICK = register("doFireTick", Category.UPDATES, BooleanValue.create(true));
/*  68 */   public static final Key<BooleanValue> RULE_MOBGRIEFING = register("mobGriefing", Category.MOBS, BooleanValue.create(true));
/*  69 */   public static final Key<BooleanValue> RULE_KEEPINVENTORY = register("keepInventory", Category.PLAYER, BooleanValue.create(false));
/*  70 */   public static final Key<BooleanValue> RULE_DOMOBSPAWNING = register("doMobSpawning", Category.SPAWNING, BooleanValue.create(true));
/*  71 */   public static final Key<BooleanValue> RULE_DOMOBLOOT = register("doMobLoot", Category.DROPS, BooleanValue.create(true));
/*  72 */   public static final Key<BooleanValue> RULE_PROJECTILESCANBREAKBLOCKS = register("projectilesCanBreakBlocks", Category.DROPS, BooleanValue.create(true));
/*  73 */   public static final Key<BooleanValue> RULE_DOBLOCKDROPS = register("doTileDrops", Category.DROPS, BooleanValue.create(true));
/*  74 */   public static final Key<BooleanValue> RULE_DOENTITYDROPS = register("doEntityDrops", Category.DROPS, BooleanValue.create(true));
/*  75 */   public static final Key<BooleanValue> RULE_COMMANDBLOCKOUTPUT = register("commandBlockOutput", Category.CHAT, BooleanValue.create(true));
/*  76 */   public static final Key<BooleanValue> RULE_NATURAL_REGENERATION = register("naturalRegeneration", Category.PLAYER, BooleanValue.create(true));
/*  77 */   public static final Key<BooleanValue> RULE_DAYLIGHT = register("doDaylightCycle", Category.UPDATES, BooleanValue.create(true));
/*  78 */   public static final Key<BooleanValue> RULE_LOGADMINCOMMANDS = register("logAdminCommands", Category.CHAT, BooleanValue.create(true));
/*  79 */   public static final Key<BooleanValue> RULE_SHOWDEATHMESSAGES = register("showDeathMessages", Category.CHAT, BooleanValue.create(true));
/*  80 */   public static final Key<IntegerValue> RULE_RANDOMTICKING = register("randomTickSpeed", Category.UPDATES, IntegerValue.create(3)); public static final Key<BooleanValue> RULE_REDUCEDDEBUGINFO;
/*  81 */   public static final Key<BooleanValue> RULE_SENDCOMMANDFEEDBACK = register("sendCommandFeedback", Category.CHAT, BooleanValue.create(true)); static {
/*  82 */     RULE_REDUCEDDEBUGINFO = register("reducedDebugInfo", Category.MISC, BooleanValue.create(false, ($$0, $$1) -> {
/*     */             byte $$2 = $$1.get() ? 22 : 23;
/*     */             for (ServerPlayer $$3 : $$0.getPlayerList().getPlayers())
/*     */               $$3.connection.send((Packet)new ClientboundEntityEventPacket((Entity)$$3, $$2)); 
/*     */           }));
/*     */   }
/*  88 */   public static final Key<BooleanValue> RULE_SPECTATORSGENERATECHUNKS = register("spectatorsGenerateChunks", Category.PLAYER, BooleanValue.create(true));
/*  89 */   public static final Key<IntegerValue> RULE_SPAWN_RADIUS = register("spawnRadius", Category.PLAYER, IntegerValue.create(10));
/*  90 */   public static final Key<BooleanValue> RULE_DISABLE_ELYTRA_MOVEMENT_CHECK = register("disableElytraMovementCheck", Category.PLAYER, BooleanValue.create(false));
/*  91 */   public static final Key<IntegerValue> RULE_MAX_ENTITY_CRAMMING = register("maxEntityCramming", Category.MOBS, IntegerValue.create(24));
/*  92 */   public static final Key<BooleanValue> RULE_WEATHER_CYCLE = register("doWeatherCycle", Category.UPDATES, BooleanValue.create(true)); public static final Key<BooleanValue> RULE_LIMITED_CRAFTING; static {
/*  93 */     RULE_LIMITED_CRAFTING = register("doLimitedCrafting", Category.PLAYER, BooleanValue.create(false, ($$0, $$1) -> {
/*     */             for (ServerPlayer $$2 : $$0.getPlayerList().getPlayers())
/*     */               $$2.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.LIMITED_CRAFTING, $$1.get() ? 1.0F : 0.0F)); 
/*     */           }));
/*     */   }
/*  98 */   public static final Key<IntegerValue> RULE_MAX_COMMAND_CHAIN_LENGTH = register("maxCommandChainLength", Category.MISC, IntegerValue.create(65536));
/*  99 */   public static final Key<IntegerValue> RULE_MAX_COMMAND_FORK_COUNT = register("maxCommandForkCount", Category.MISC, IntegerValue.create(65536));
/* 100 */   public static final Key<IntegerValue> RULE_COMMAND_MODIFICATION_BLOCK_LIMIT = register("commandModificationBlockLimit", Category.MISC, IntegerValue.create(32768));
/* 101 */   public static final Key<BooleanValue> RULE_ANNOUNCE_ADVANCEMENTS = register("announceAdvancements", Category.CHAT, BooleanValue.create(true));
/* 102 */   public static final Key<BooleanValue> RULE_DISABLE_RAIDS = register("disableRaids", Category.MOBS, BooleanValue.create(false));
/* 103 */   public static final Key<BooleanValue> RULE_DOINSOMNIA = register("doInsomnia", Category.SPAWNING, BooleanValue.create(true)); public static final Key<BooleanValue> RULE_DO_IMMEDIATE_RESPAWN; static {
/* 104 */     RULE_DO_IMMEDIATE_RESPAWN = register("doImmediateRespawn", Category.PLAYER, BooleanValue.create(false, ($$0, $$1) -> {
/*     */             for (ServerPlayer $$2 : $$0.getPlayerList().getPlayers())
/*     */               $$2.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.IMMEDIATE_RESPAWN, $$1.get() ? 1.0F : 0.0F)); 
/*     */           }));
/*     */   }
/* 109 */   public static final Key<IntegerValue> RULE_PLAYERS_NETHER_PORTAL_DEFAULT_DELAY = register("playersNetherPortalDefaultDelay", Category.PLAYER, IntegerValue.create(80));
/* 110 */   public static final Key<IntegerValue> RULE_PLAYERS_NETHER_PORTAL_CREATIVE_DELAY = register("playersNetherPortalCreativeDelay", Category.PLAYER, IntegerValue.create(1));
/* 111 */   public static final Key<BooleanValue> RULE_DROWNING_DAMAGE = register("drowningDamage", Category.PLAYER, BooleanValue.create(true));
/* 112 */   public static final Key<BooleanValue> RULE_FALL_DAMAGE = register("fallDamage", Category.PLAYER, BooleanValue.create(true));
/* 113 */   public static final Key<BooleanValue> RULE_FIRE_DAMAGE = register("fireDamage", Category.PLAYER, BooleanValue.create(true));
/* 114 */   public static final Key<BooleanValue> RULE_FREEZE_DAMAGE = register("freezeDamage", Category.PLAYER, BooleanValue.create(true));
/* 115 */   public static final Key<BooleanValue> RULE_DO_PATROL_SPAWNING = register("doPatrolSpawning", Category.SPAWNING, BooleanValue.create(true));
/* 116 */   public static final Key<BooleanValue> RULE_DO_TRADER_SPAWNING = register("doTraderSpawning", Category.SPAWNING, BooleanValue.create(true));
/* 117 */   public static final Key<BooleanValue> RULE_DO_WARDEN_SPAWNING = register("doWardenSpawning", Category.SPAWNING, BooleanValue.create(true));
/* 118 */   public static final Key<BooleanValue> RULE_FORGIVE_DEAD_PLAYERS = register("forgiveDeadPlayers", Category.MOBS, BooleanValue.create(true));
/* 119 */   public static final Key<BooleanValue> RULE_UNIVERSAL_ANGER = register("universalAnger", Category.MOBS, BooleanValue.create(false));
/* 120 */   public static final Key<IntegerValue> RULE_PLAYERS_SLEEPING_PERCENTAGE = register("playersSleepingPercentage", Category.PLAYER, IntegerValue.create(100));
/* 121 */   public static final Key<BooleanValue> RULE_BLOCK_EXPLOSION_DROP_DECAY = register("blockExplosionDropDecay", Category.DROPS, BooleanValue.create(true));
/* 122 */   public static final Key<BooleanValue> RULE_MOB_EXPLOSION_DROP_DECAY = register("mobExplosionDropDecay", Category.DROPS, BooleanValue.create(true));
/* 123 */   public static final Key<BooleanValue> RULE_TNT_EXPLOSION_DROP_DECAY = register("tntExplosionDropDecay", Category.DROPS, BooleanValue.create(false));
/* 124 */   public static final Key<IntegerValue> RULE_SNOW_ACCUMULATION_HEIGHT = register("snowAccumulationHeight", Category.UPDATES, IntegerValue.create(1));
/*     */   
/* 126 */   public static final Key<BooleanValue> RULE_WATER_SOURCE_CONVERSION = register("waterSourceConversion", Category.UPDATES, BooleanValue.create(true));
/* 127 */   public static final Key<BooleanValue> RULE_LAVA_SOURCE_CONVERSION = register("lavaSourceConversion", Category.UPDATES, BooleanValue.create(false));
/* 128 */   public static final Key<BooleanValue> RULE_GLOBAL_SOUND_EVENTS = register("globalSoundEvents", Category.MISC, BooleanValue.create(true));
/* 129 */   public static final Key<BooleanValue> RULE_DO_VINES_SPREAD = register("doVinesSpread", Category.UPDATES, BooleanValue.create(true));
/* 130 */   public static final Key<BooleanValue> RULE_ENDER_PEARLS_VANISH_ON_DEATH = register("enderPearlsVanishOnDeath", Category.PLAYER, BooleanValue.create(true));
/*     */   
/*     */   private final Map<Key<?>, Value<?>> rules;
/*     */   
/*     */   public GameRules(DynamicLike<?> $$0) {
/* 135 */     this();
/* 136 */     loadFromTag($$0);
/*     */   }
/*     */   
/*     */   public GameRules() {
/* 140 */     this.rules = (Map<Key<?>, Value<?>>)GAME_RULE_TYPES.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, $$0 -> ((Type<Value>)$$0.getValue()).createRule()));
/*     */   }
/*     */   
/*     */   private GameRules(Map<Key<?>, Value<?>> $$0) {
/* 144 */     this.rules = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Value<T>> T getRule(Key<T> $$0) {
/* 149 */     return (T)this.rules.get($$0);
/*     */   }
/*     */   
/*     */   public CompoundTag createTag() {
/* 153 */     CompoundTag $$0 = new CompoundTag();
/* 154 */     this.rules.forEach(($$1, $$2) -> $$0.putString($$1.id, $$2.serialize()));
/* 155 */     return $$0;
/*     */   }
/*     */   
/*     */   private void loadFromTag(DynamicLike<?> $$0) {
/* 159 */     this.rules.forEach(($$1, $$2) -> {
/*     */           Objects.requireNonNull($$2);
/*     */           $$0.get($$1.id).asString().result().ifPresent($$2::deserialize);
/*     */         });
/*     */   }
/*     */   public GameRules copy() {
/* 165 */     return new GameRules((Map<Key<?>, Value<?>>)this.rules.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, $$0 -> ((Value<Value>)$$0.getValue()).copy())));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface GameRuleTypeVisitor
/*     */   {
/*     */     default <T extends GameRules.Value<T>> void visit(GameRules.Key<T> $$0, GameRules.Type<T> $$1) {}
/*     */ 
/*     */ 
/*     */     
/*     */     default void visitBoolean(GameRules.Key<GameRules.BooleanValue> $$0, GameRules.Type<GameRules.BooleanValue> $$1) {}
/*     */ 
/*     */     
/*     */     default void visitInteger(GameRules.Key<GameRules.IntegerValue> $$0, GameRules.Type<GameRules.IntegerValue> $$1) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public static void visitGameRuleTypes(GameRuleTypeVisitor $$0) {
/* 184 */     GAME_RULE_TYPES.forEach(($$1, $$2) -> callVisitorCap($$0, $$1, $$2));
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends Value<T>> void callVisitorCap(GameRuleTypeVisitor $$0, Key<?> $$1, Type<?> $$2) {
/* 189 */     Key<T> $$3 = (Key)$$1;
/* 190 */     Type<T> $$4 = (Type)$$2;
/* 191 */     $$0.visit($$3, $$4);
/* 192 */     $$4.callVisitor($$0, $$3);
/*     */   }
/*     */   
/*     */   public void assignFrom(GameRules $$0, @Nullable MinecraftServer $$1) {
/* 196 */     $$0.rules.keySet().forEach($$2 -> assignCap($$2, $$0, $$1));
/*     */   }
/*     */   
/*     */   private <T extends Value<T>> void assignCap(Key<T> $$0, GameRules $$1, @Nullable MinecraftServer $$2) {
/* 200 */     T $$3 = $$1.getRule($$0);
/* 201 */     getRule($$0).setFrom($$3, $$2);
/*     */   }
/*     */   
/*     */   public boolean getBoolean(Key<BooleanValue> $$0) {
/* 205 */     return ((BooleanValue)getRule($$0)).get();
/*     */   }
/*     */   
/*     */   public int getInt(Key<IntegerValue> $$0) {
/* 209 */     return ((IntegerValue)getRule($$0)).get();
/*     */   }
/*     */   
/*     */   public static final class Key<T extends Value<T>> {
/*     */     final String id;
/*     */     private final GameRules.Category category;
/*     */     
/*     */     public Key(String $$0, GameRules.Category $$1) {
/* 217 */       this.id = $$0;
/* 218 */       this.category = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 223 */       return this.id;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/* 228 */       if (this == $$0) {
/* 229 */         return true;
/*     */       }
/* 231 */       return ($$0 instanceof Key && ((Key)$$0).id.equals(this.id));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 236 */       return this.id.hashCode();
/*     */     }
/*     */     
/*     */     public String getId() {
/* 240 */       return this.id;
/*     */     }
/*     */     
/*     */     public String getDescriptionId() {
/* 244 */       return "gamerule." + this.id;
/*     */     }
/*     */     
/*     */     public GameRules.Category getCategory() {
/* 248 */       return this.category;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Type<T extends Value<T>> {
/*     */     private final Supplier<ArgumentType<?>> argument;
/*     */     private final Function<Type<T>, T> constructor;
/*     */     final BiConsumer<MinecraftServer, T> callback;
/*     */     private final GameRules.VisitorCaller<T> visitorCaller;
/*     */     
/*     */     Type(Supplier<ArgumentType<?>> $$0, Function<Type<T>, T> $$1, BiConsumer<MinecraftServer, T> $$2, GameRules.VisitorCaller<T> $$3) {
/* 259 */       this.argument = $$0;
/* 260 */       this.constructor = $$1;
/* 261 */       this.callback = $$2;
/* 262 */       this.visitorCaller = $$3;
/*     */     }
/*     */     
/*     */     public RequiredArgumentBuilder<CommandSourceStack, ?> createArgument(String $$0) {
/* 266 */       return Commands.argument($$0, this.argument.get());
/*     */     }
/*     */     
/*     */     public T createRule() {
/* 270 */       return this.constructor.apply(this);
/*     */     }
/*     */     
/*     */     public void callVisitor(GameRules.GameRuleTypeVisitor $$0, GameRules.Key<T> $$1) {
/* 274 */       this.visitorCaller.call($$0, $$1, this);
/*     */     }
/*     */   }
/*     */   
/*     */   public static abstract class Value<T extends Value<T>> {
/*     */     protected final GameRules.Type<T> type;
/*     */     
/*     */     public Value(GameRules.Type<T> $$0) {
/* 282 */       this.type = $$0;
/*     */     }
/*     */     
/*     */     protected abstract void updateFromArgument(CommandContext<CommandSourceStack> param1CommandContext, String param1String);
/*     */     
/*     */     public void setFromArgument(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 288 */       updateFromArgument($$0, $$1);
/* 289 */       onChanged(((CommandSourceStack)$$0.getSource()).getServer());
/*     */     }
/*     */     
/*     */     protected void onChanged(@Nullable MinecraftServer $$0) {
/* 293 */       if ($$0 != null) {
/* 294 */         this.type.callback.accept($$0, getSelf());
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected abstract void deserialize(String param1String);
/*     */     
/*     */     public abstract String serialize();
/*     */     
/*     */     public String toString() {
/* 304 */       return serialize();
/*     */     }
/*     */     
/*     */     public abstract int getCommandResult();
/*     */     
/*     */     protected abstract T getSelf();
/*     */     
/*     */     protected abstract T copy();
/*     */     
/*     */     public abstract void setFrom(T param1T, @Nullable MinecraftServer param1MinecraftServer); }
/*     */   
/*     */   public static class IntegerValue extends Value<IntegerValue> { private int value;
/*     */     
/*     */     private static GameRules.Type<IntegerValue> create(int $$0, BiConsumer<MinecraftServer, IntegerValue> $$1) {
/* 318 */       return new GameRules.Type<>(IntegerArgumentType::integer, $$1 -> new IntegerValue($$1, $$0), $$1, GameRules.GameRuleTypeVisitor::visitInteger);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static GameRules.Type<IntegerValue> create(int $$0) {
/* 327 */       return create($$0, ($$0, $$1) -> {
/*     */           
/*     */           });
/*     */     }
/*     */     
/*     */     public IntegerValue(GameRules.Type<IntegerValue> $$0, int $$1) {
/* 333 */       super($$0);
/* 334 */       this.value = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void updateFromArgument(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 339 */       this.value = IntegerArgumentType.getInteger($$0, $$1);
/*     */     }
/*     */     
/*     */     public int get() {
/* 343 */       return this.value;
/*     */     }
/*     */     
/*     */     public void set(int $$0, @Nullable MinecraftServer $$1) {
/* 347 */       this.value = $$0;
/* 348 */       onChanged($$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public String serialize() {
/* 353 */       return Integer.toString(this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void deserialize(String $$0) {
/* 358 */       this.value = safeParse($$0);
/*     */     }
/*     */     
/*     */     public boolean tryDeserialize(String $$0) {
/*     */       try {
/* 363 */         this.value = Integer.parseInt($$0);
/* 364 */         return true;
/* 365 */       } catch (NumberFormatException numberFormatException) {
/*     */ 
/*     */         
/* 368 */         return false;
/*     */       } 
/*     */     }
/*     */     private static int safeParse(String $$0) {
/* 372 */       if (!$$0.isEmpty()) {
/*     */         try {
/* 374 */           return Integer.parseInt($$0);
/* 375 */         } catch (NumberFormatException $$1) {
/* 376 */           GameRules.LOGGER.warn("Failed to parse integer {}", $$0);
/*     */         } 
/*     */       }
/* 379 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCommandResult() {
/* 384 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     protected IntegerValue getSelf() {
/* 389 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected IntegerValue copy() {
/* 394 */       return new IntegerValue(this.type, this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setFrom(IntegerValue $$0, @Nullable MinecraftServer $$1) {
/* 399 */       this.value = $$0.value;
/* 400 */       onChanged($$1);
/*     */     } }
/*     */   
/*     */   public static class BooleanValue extends Value<BooleanValue> { private boolean value;
/*     */     
/*     */     static GameRules.Type<BooleanValue> create(boolean $$0, BiConsumer<MinecraftServer, BooleanValue> $$1) {
/* 406 */       return new GameRules.Type<>(BoolArgumentType::bool, $$1 -> new BooleanValue($$1, $$0), $$1, GameRules.GameRuleTypeVisitor::visitBoolean);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static GameRules.Type<BooleanValue> create(boolean $$0) {
/* 415 */       return create($$0, ($$0, $$1) -> {
/*     */           
/*     */           });
/*     */     }
/*     */     
/*     */     public BooleanValue(GameRules.Type<BooleanValue> $$0, boolean $$1) {
/* 421 */       super($$0);
/* 422 */       this.value = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void updateFromArgument(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 427 */       this.value = BoolArgumentType.getBool($$0, $$1);
/*     */     }
/*     */     
/*     */     public boolean get() {
/* 431 */       return this.value;
/*     */     }
/*     */     
/*     */     public void set(boolean $$0, @Nullable MinecraftServer $$1) {
/* 435 */       this.value = $$0;
/* 436 */       onChanged($$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public String serialize() {
/* 441 */       return Boolean.toString(this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void deserialize(String $$0) {
/* 446 */       this.value = Boolean.parseBoolean($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCommandResult() {
/* 451 */       return this.value ? 1 : 0;
/*     */     }
/*     */ 
/*     */     
/*     */     protected BooleanValue getSelf() {
/* 456 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected BooleanValue copy() {
/* 461 */       return new BooleanValue(this.type, this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setFrom(BooleanValue $$0, @Nullable MinecraftServer $$1) {
/* 466 */       this.value = $$0.value;
/* 467 */       onChanged($$1);
/*     */     } }
/*     */ 
/*     */   
/*     */   private static interface VisitorCaller<T extends Value<T>> {
/*     */     void call(GameRules.GameRuleTypeVisitor param1GameRuleTypeVisitor, GameRules.Key<T> param1Key, GameRules.Type<T> param1Type);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\GameRules.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */