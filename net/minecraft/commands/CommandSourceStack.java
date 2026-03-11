/*     */ package net.minecraft.commands;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.BinaryOperator;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.commands.arguments.EntityAnchorArgument;
/*     */ import net.minecraft.commands.execution.TraceCallbacks;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.chat.ChatType;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.OutgoingChatMessage;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.TaskChainer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.dimension.DimensionType;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class CommandSourceStack implements ExecutionCommandSource<CommandSourceStack>, SharedSuggestionProvider {
/*  48 */   public static final SimpleCommandExceptionType ERROR_NOT_PLAYER = new SimpleCommandExceptionType((Message)Component.translatable("permissions.requires.player"));
/*  49 */   public static final SimpleCommandExceptionType ERROR_NOT_ENTITY = new SimpleCommandExceptionType((Message)Component.translatable("permissions.requires.entity"));
/*     */   
/*     */   private final CommandSource source;
/*     */   private final Vec3 worldPosition;
/*     */   private final ServerLevel level;
/*     */   private final int permissionLevel;
/*     */   private final String textName;
/*     */   private final Component displayName;
/*     */   private final MinecraftServer server;
/*     */   private final boolean silent;
/*     */   @Nullable
/*     */   private final Entity entity;
/*     */   private final CommandResultCallback resultCallback;
/*     */   private final EntityAnchorArgument.Anchor anchor;
/*     */   private final Vec2 rotation;
/*     */   private final CommandSigningContext signingContext;
/*     */   private final TaskChainer chatMessageChainer;
/*     */   
/*     */   public CommandSourceStack(CommandSource $$0, Vec3 $$1, Vec2 $$2, ServerLevel $$3, int $$4, String $$5, Component $$6, MinecraftServer $$7, @Nullable Entity $$8) {
/*  68 */     this($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, false, CommandResultCallback.EMPTY, EntityAnchorArgument.Anchor.FEET, CommandSigningContext.ANONYMOUS, TaskChainer.immediate((Executor)$$7));
/*     */   }
/*     */   
/*     */   protected CommandSourceStack(CommandSource $$0, Vec3 $$1, Vec2 $$2, ServerLevel $$3, int $$4, String $$5, Component $$6, MinecraftServer $$7, @Nullable Entity $$8, boolean $$9, CommandResultCallback $$10, EntityAnchorArgument.Anchor $$11, CommandSigningContext $$12, TaskChainer $$13) {
/*  72 */     this.source = $$0;
/*  73 */     this.worldPosition = $$1;
/*  74 */     this.level = $$3;
/*  75 */     this.silent = $$9;
/*  76 */     this.entity = $$8;
/*  77 */     this.permissionLevel = $$4;
/*  78 */     this.textName = $$5;
/*  79 */     this.displayName = $$6;
/*  80 */     this.server = $$7;
/*  81 */     this.resultCallback = $$10;
/*  82 */     this.anchor = $$11;
/*  83 */     this.rotation = $$2;
/*  84 */     this.signingContext = $$12;
/*  85 */     this.chatMessageChainer = $$13;
/*     */   }
/*     */   
/*     */   public CommandSourceStack withSource(CommandSource $$0) {
/*  89 */     if (this.source == $$0) {
/*  90 */       return this;
/*     */     }
/*  92 */     return new CommandSourceStack($$0, this.worldPosition, this.rotation, this.level, this.permissionLevel, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
/*     */   }
/*     */   
/*     */   public CommandSourceStack withEntity(Entity $$0) {
/*  96 */     if (this.entity == $$0) {
/*  97 */       return this;
/*     */     }
/*  99 */     return new CommandSourceStack(this.source, this.worldPosition, this.rotation, this.level, this.permissionLevel, $$0.getName().getString(), $$0.getDisplayName(), this.server, $$0, this.silent, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
/*     */   }
/*     */   
/*     */   public CommandSourceStack withPosition(Vec3 $$0) {
/* 103 */     if (this.worldPosition.equals($$0)) {
/* 104 */       return this;
/*     */     }
/* 106 */     return new CommandSourceStack(this.source, $$0, this.rotation, this.level, this.permissionLevel, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
/*     */   }
/*     */   
/*     */   public CommandSourceStack withRotation(Vec2 $$0) {
/* 110 */     if (this.rotation.equals($$0)) {
/* 111 */       return this;
/*     */     }
/* 113 */     return new CommandSourceStack(this.source, this.worldPosition, $$0, this.level, this.permissionLevel, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandSourceStack withCallback(CommandResultCallback $$0) {
/* 118 */     if (Objects.equals(this.resultCallback, $$0)) {
/* 119 */       return this;
/*     */     }
/* 121 */     return new CommandSourceStack(this.source, this.worldPosition, this.rotation, this.level, this.permissionLevel, this.textName, this.displayName, this.server, this.entity, this.silent, $$0, this.anchor, this.signingContext, this.chatMessageChainer);
/*     */   }
/*     */   
/*     */   public CommandSourceStack withCallback(CommandResultCallback $$0, BinaryOperator<CommandResultCallback> $$1) {
/* 125 */     CommandResultCallback $$2 = $$1.apply(this.resultCallback, $$0);
/* 126 */     return withCallback($$2);
/*     */   }
/*     */   
/*     */   public CommandSourceStack withSuppressedOutput() {
/* 130 */     if (this.silent || this.source.alwaysAccepts()) {
/* 131 */       return this;
/*     */     }
/* 133 */     return new CommandSourceStack(this.source, this.worldPosition, this.rotation, this.level, this.permissionLevel, this.textName, this.displayName, this.server, this.entity, true, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
/*     */   }
/*     */   
/*     */   public CommandSourceStack withPermission(int $$0) {
/* 137 */     if ($$0 == this.permissionLevel) {
/* 138 */       return this;
/*     */     }
/* 140 */     return new CommandSourceStack(this.source, this.worldPosition, this.rotation, this.level, $$0, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
/*     */   }
/*     */   
/*     */   public CommandSourceStack withMaximumPermission(int $$0) {
/* 144 */     if ($$0 <= this.permissionLevel) {
/* 145 */       return this;
/*     */     }
/* 147 */     return new CommandSourceStack(this.source, this.worldPosition, this.rotation, this.level, $$0, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
/*     */   }
/*     */   
/*     */   public CommandSourceStack withAnchor(EntityAnchorArgument.Anchor $$0) {
/* 151 */     if ($$0 == this.anchor) {
/* 152 */       return this;
/*     */     }
/* 154 */     return new CommandSourceStack(this.source, this.worldPosition, this.rotation, this.level, this.permissionLevel, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, $$0, this.signingContext, this.chatMessageChainer);
/*     */   }
/*     */   
/*     */   public CommandSourceStack withLevel(ServerLevel $$0) {
/* 158 */     if ($$0 == this.level) {
/* 159 */       return this;
/*     */     }
/* 161 */     double $$1 = DimensionType.getTeleportationScale(this.level.dimensionType(), $$0.dimensionType());
/* 162 */     Vec3 $$2 = new Vec3(this.worldPosition.x * $$1, this.worldPosition.y, this.worldPosition.z * $$1);
/* 163 */     return new CommandSourceStack(this.source, $$2, this.rotation, $$0, this.permissionLevel, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, this.anchor, this.signingContext, this.chatMessageChainer);
/*     */   }
/*     */   
/*     */   public CommandSourceStack facing(Entity $$0, EntityAnchorArgument.Anchor $$1) {
/* 167 */     return facing($$1.apply($$0));
/*     */   }
/*     */   
/*     */   public CommandSourceStack facing(Vec3 $$0) {
/* 171 */     Vec3 $$1 = this.anchor.apply(this);
/* 172 */     double $$2 = $$0.x - $$1.x;
/* 173 */     double $$3 = $$0.y - $$1.y;
/* 174 */     double $$4 = $$0.z - $$1.z;
/* 175 */     double $$5 = Math.sqrt($$2 * $$2 + $$4 * $$4);
/*     */     
/* 177 */     float $$6 = Mth.wrapDegrees((float)-(Mth.atan2($$3, $$5) * 57.2957763671875D));
/* 178 */     float $$7 = Mth.wrapDegrees((float)(Mth.atan2($$4, $$2) * 57.2957763671875D) - 90.0F);
/* 179 */     return withRotation(new Vec2($$6, $$7));
/*     */   }
/*     */   
/*     */   public CommandSourceStack withSigningContext(CommandSigningContext $$0, TaskChainer $$1) {
/* 183 */     if ($$0 == this.signingContext && $$1 == this.chatMessageChainer) {
/* 184 */       return this;
/*     */     }
/* 186 */     return new CommandSourceStack(this.source, this.worldPosition, this.rotation, this.level, this.permissionLevel, this.textName, this.displayName, this.server, this.entity, this.silent, this.resultCallback, this.anchor, $$0, $$1);
/*     */   }
/*     */   
/*     */   public Component getDisplayName() {
/* 190 */     return this.displayName;
/*     */   }
/*     */   
/*     */   public String getTextName() {
/* 194 */     return this.textName;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPermission(int $$0) {
/* 199 */     return (this.permissionLevel >= $$0);
/*     */   }
/*     */   
/*     */   public Vec3 getPosition() {
/* 203 */     return this.worldPosition;
/*     */   }
/*     */   
/*     */   public ServerLevel getLevel() {
/* 207 */     return this.level;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entity getEntity() {
/* 212 */     return this.entity;
/*     */   }
/*     */   
/*     */   public Entity getEntityOrException() throws CommandSyntaxException {
/* 216 */     if (this.entity == null) {
/* 217 */       throw ERROR_NOT_ENTITY.create();
/*     */     }
/* 219 */     return this.entity;
/*     */   }
/*     */   
/*     */   public ServerPlayer getPlayerOrException() throws CommandSyntaxException {
/* 223 */     Entity entity = this.entity; if (entity instanceof ServerPlayer) { ServerPlayer $$0 = (ServerPlayer)entity;
/* 224 */       return $$0; }
/*     */     
/* 226 */     throw ERROR_NOT_PLAYER.create();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ServerPlayer getPlayer() {
/* 231 */     Entity entity = this.entity; ServerPlayer $$0 = (ServerPlayer)entity; return (entity instanceof ServerPlayer) ? $$0 : null;
/*     */   }
/*     */   
/*     */   public boolean isPlayer() {
/* 235 */     return this.entity instanceof ServerPlayer;
/*     */   }
/*     */   
/*     */   public Vec2 getRotation() {
/* 239 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public MinecraftServer getServer() {
/* 243 */     return this.server;
/*     */   }
/*     */   
/*     */   public EntityAnchorArgument.Anchor getAnchor() {
/* 247 */     return this.anchor;
/*     */   }
/*     */   
/*     */   public CommandSigningContext getSigningContext() {
/* 251 */     return this.signingContext;
/*     */   }
/*     */   
/*     */   public TaskChainer getChatMessageChainer() {
/* 255 */     return this.chatMessageChainer;
/*     */   }
/*     */   
/*     */   public boolean shouldFilterMessageTo(ServerPlayer $$0) {
/* 259 */     ServerPlayer $$1 = getPlayer();
/* 260 */     if ($$0 == $$1) {
/* 261 */       return false;
/*     */     }
/* 263 */     return (($$1 != null && $$1.isTextFilteringEnabled()) || $$0.isTextFilteringEnabled());
/*     */   }
/*     */   
/*     */   public void sendChatMessage(OutgoingChatMessage $$0, boolean $$1, ChatType.Bound $$2) {
/* 267 */     if (this.silent) {
/*     */       return;
/*     */     }
/*     */     
/* 271 */     ServerPlayer $$3 = getPlayer();
/* 272 */     if ($$3 != null) {
/* 273 */       $$3.sendChatMessage($$0, $$1, $$2);
/*     */     } else {
/* 275 */       this.source.sendSystemMessage($$2.decorate($$0.content()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendSystemMessage(Component $$0) {
/* 280 */     if (this.silent) {
/*     */       return;
/*     */     }
/*     */     
/* 284 */     ServerPlayer $$1 = getPlayer();
/* 285 */     if ($$1 != null) {
/* 286 */       $$1.sendSystemMessage($$0);
/*     */     } else {
/* 288 */       this.source.sendSystemMessage($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendSuccess(Supplier<Component> $$0, boolean $$1) {
/* 298 */     boolean $$2 = (this.source.acceptsSuccess() && !this.silent);
/* 299 */     boolean $$3 = ($$1 && this.source.shouldInformAdmins() && !this.silent);
/* 300 */     if (!$$2 && !$$3) {
/*     */       return;
/*     */     }
/*     */     
/* 304 */     Component $$4 = $$0.get();
/* 305 */     if ($$2) {
/* 306 */       this.source.sendSystemMessage($$4);
/*     */     }
/* 308 */     if ($$3) {
/* 309 */       broadcastToAdmins($$4);
/*     */     }
/*     */   }
/*     */   
/*     */   private void broadcastToAdmins(Component $$0) {
/* 314 */     MutableComponent mutableComponent = Component.translatable("chat.type.admin", new Object[] { getDisplayName(), $$0 }).withStyle(new ChatFormatting[] { ChatFormatting.GRAY, ChatFormatting.ITALIC });
/*     */     
/* 316 */     if (this.server.getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK)) {
/* 317 */       for (ServerPlayer $$2 : this.server.getPlayerList().getPlayers()) {
/* 318 */         if ($$2 != this.source && this.server.getPlayerList().isOp($$2.getGameProfile())) {
/* 319 */           $$2.sendSystemMessage((Component)mutableComponent);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 324 */     if (this.source != this.server && this.server.getGameRules().getBoolean(GameRules.RULE_LOGADMINCOMMANDS)) {
/* 325 */       this.server.sendSystemMessage((Component)mutableComponent);
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendFailure(Component $$0) {
/* 330 */     if (this.source.acceptsFailure() && !this.silent) {
/* 331 */       this.source.sendSystemMessage((Component)Component.empty().append($$0).withStyle(ChatFormatting.RED));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandResultCallback callback() {
/* 337 */     return this.resultCallback;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getOnlinePlayerNames() {
/* 342 */     return Lists.newArrayList((Object[])this.server.getPlayerNames());
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getAllTeams() {
/* 347 */     return this.server.getScoreboard().getTeamNames();
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<ResourceLocation> getAvailableSounds() {
/* 352 */     return BuiltInRegistries.SOUND_EVENT.stream().map(SoundEvent::getLocation);
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<ResourceLocation> getRecipeNames() {
/* 357 */     return this.server.getRecipeManager().getRecipeIds();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<Suggestions> customSuggestion(CommandContext<?> $$0) {
/* 362 */     return Suggestions.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<Suggestions> suggestRegistryElements(ResourceKey<? extends Registry<?>> $$0, SharedSuggestionProvider.ElementSuggestionType $$1, SuggestionsBuilder $$2, CommandContext<?> $$3) {
/* 367 */     return registryAccess().registry($$0).map($$2 -> {
/*     */           suggestRegistryElements($$2, $$0, $$1);
/*     */           return $$1.buildFuture();
/* 370 */         }).orElseGet(Suggestions::empty);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<ResourceKey<Level>> levels() {
/* 375 */     return this.server.levelKeys();
/*     */   }
/*     */ 
/*     */   
/*     */   public RegistryAccess registryAccess() {
/* 380 */     return (RegistryAccess)this.server.registryAccess();
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureFlagSet enabledFeatures() {
/* 385 */     return this.level.enabledFeatures();
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandDispatcher<CommandSourceStack> dispatcher() {
/* 390 */     return getServer().getFunctions().getDispatcher();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleError(CommandExceptionType $$0, Message $$1, boolean $$2, @Nullable TraceCallbacks $$3) {
/* 395 */     if ($$3 != null) {
/* 396 */       $$3.onError($$1.getString());
/*     */     }
/* 398 */     if (!$$2) {
/* 399 */       sendFailure(ComponentUtils.fromMessage($$1));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSilent() {
/* 405 */     return this.silent;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\CommandSourceStack.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */