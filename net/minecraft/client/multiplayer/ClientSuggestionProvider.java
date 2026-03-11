/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundCustomChatCompletionsPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundCommandSuggestionPacket;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class ClientSuggestionProvider implements SharedSuggestionProvider {
/*     */   private final ClientPacketListener connection;
/*     */   private final Minecraft minecraft;
/*  38 */   private int pendingSuggestionsId = -1;
/*     */   @Nullable
/*     */   private CompletableFuture<Suggestions> pendingSuggestionsFuture;
/*  41 */   private final Set<String> customCompletionSuggestions = new HashSet<>();
/*     */   
/*     */   public ClientSuggestionProvider(ClientPacketListener $$0, Minecraft $$1) {
/*  44 */     this.connection = $$0;
/*  45 */     this.minecraft = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getOnlinePlayerNames() {
/*  50 */     List<String> $$0 = Lists.newArrayList();
/*     */     
/*  52 */     for (PlayerInfo $$1 : this.connection.getOnlinePlayers()) {
/*  53 */       $$0.add($$1.getProfile().getName());
/*     */     }
/*     */     
/*  56 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getCustomTabSugggestions() {
/*  61 */     if (this.customCompletionSuggestions.isEmpty()) {
/*  62 */       return getOnlinePlayerNames();
/*     */     }
/*  64 */     Set<String> $$0 = new HashSet<>(getOnlinePlayerNames());
/*  65 */     $$0.addAll(this.customCompletionSuggestions);
/*  66 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getSelectedEntities() {
/*  71 */     if (this.minecraft.hitResult != null && this.minecraft.hitResult.getType() == HitResult.Type.ENTITY) {
/*  72 */       return Collections.singleton(((EntityHitResult)this.minecraft.hitResult).getEntity().getStringUUID());
/*     */     }
/*  74 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getAllTeams() {
/*  79 */     return this.connection.getLevel().getScoreboard().getTeamNames();
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<ResourceLocation> getAvailableSounds() {
/*  84 */     return this.minecraft.getSoundManager().getAvailableSounds().stream();
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<ResourceLocation> getRecipeNames() {
/*  89 */     return this.connection.getRecipeManager().getRecipeIds();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPermission(int $$0) {
/*  94 */     LocalPlayer $$1 = this.minecraft.player;
/*  95 */     return ($$1 != null) ? $$1.hasPermissions($$0) : (($$0 == 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<Suggestions> suggestRegistryElements(ResourceKey<? extends Registry<?>> $$0, SharedSuggestionProvider.ElementSuggestionType $$1, SuggestionsBuilder $$2, CommandContext<?> $$3) {
/* 100 */     return registryAccess().registry($$0).map($$2 -> {
/*     */           suggestRegistryElements($$2, $$0, $$1);
/*     */           return $$1.buildFuture();
/* 103 */         }).orElseGet(() -> customSuggestion($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<Suggestions> customSuggestion(CommandContext<?> $$0) {
/* 108 */     if (this.pendingSuggestionsFuture != null) {
/* 109 */       this.pendingSuggestionsFuture.cancel(false);
/*     */     }
/* 111 */     this.pendingSuggestionsFuture = new CompletableFuture<>();
/* 112 */     int $$1 = ++this.pendingSuggestionsId;
/* 113 */     this.connection.send((Packet<?>)new ServerboundCommandSuggestionPacket($$1, $$0.getInput()));
/* 114 */     return this.pendingSuggestionsFuture;
/*     */   }
/*     */   
/*     */   private static String prettyPrint(double $$0) {
/* 118 */     return String.format(Locale.ROOT, "%.2f", new Object[] { Double.valueOf($$0) });
/*     */   }
/*     */   
/*     */   private static String prettyPrint(int $$0) {
/* 122 */     return Integer.toString($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<SharedSuggestionProvider.TextCoordinates> getRelevantCoordinates() {
/* 127 */     HitResult $$0 = this.minecraft.hitResult;
/* 128 */     if ($$0 == null || $$0.getType() != HitResult.Type.BLOCK) {
/* 129 */       return super.getRelevantCoordinates();
/*     */     }
/*     */     
/* 132 */     BlockPos $$1 = ((BlockHitResult)$$0).getBlockPos();
/* 133 */     return Collections.singleton(new SharedSuggestionProvider.TextCoordinates(prettyPrint($$1.getX()), prettyPrint($$1.getY()), prettyPrint($$1.getZ())));
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<SharedSuggestionProvider.TextCoordinates> getAbsoluteCoordinates() {
/* 138 */     HitResult $$0 = this.minecraft.hitResult;
/* 139 */     if ($$0 == null || $$0.getType() != HitResult.Type.BLOCK) {
/* 140 */       return super.getAbsoluteCoordinates();
/*     */     }
/*     */     
/* 143 */     Vec3 $$1 = $$0.getLocation();
/* 144 */     return Collections.singleton(new SharedSuggestionProvider.TextCoordinates(prettyPrint($$1.x), prettyPrint($$1.y), prettyPrint($$1.z)));
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<ResourceKey<Level>> levels() {
/* 149 */     return this.connection.levels();
/*     */   }
/*     */ 
/*     */   
/*     */   public RegistryAccess registryAccess() {
/* 154 */     return (RegistryAccess)this.connection.registryAccess();
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureFlagSet enabledFeatures() {
/* 159 */     return this.connection.enabledFeatures();
/*     */   }
/*     */   
/*     */   public void completeCustomSuggestions(int $$0, Suggestions $$1) {
/* 163 */     if ($$0 == this.pendingSuggestionsId) {
/* 164 */       this.pendingSuggestionsFuture.complete($$1);
/* 165 */       this.pendingSuggestionsFuture = null;
/* 166 */       this.pendingSuggestionsId = -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void modifyCustomCompletions(ClientboundCustomChatCompletionsPacket.Action $$0, List<String> $$1) {
/* 171 */     switch ($$0) { case ADD:
/* 172 */         this.customCompletionSuggestions.addAll($$1); break;
/* 173 */       case REMOVE: Objects.requireNonNull(this.customCompletionSuggestions); $$1.forEach(this.customCompletionSuggestions::remove); break;
/*     */       case SET:
/* 175 */         this.customCompletionSuggestions.clear();
/* 176 */         this.customCompletionSuggestions.addAll($$1);
/*     */         break; }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ClientSuggestionProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */