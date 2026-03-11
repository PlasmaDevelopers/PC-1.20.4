/*     */ package net.minecraft.server;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.internal.Streams;
/*     */ import com.google.gson.stream.JsonReader;
/*     */ import com.mojang.datafixers.DataFixer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.AdvancementHolder;
/*     */ import net.minecraft.advancements.AdvancementNode;
/*     */ import net.minecraft.advancements.AdvancementProgress;
/*     */ import net.minecraft.advancements.Criterion;
/*     */ import net.minecraft.advancements.CriterionProgress;
/*     */ import net.minecraft.advancements.CriterionTrigger;
/*     */ import net.minecraft.advancements.CriterionTriggerInstance;
/*     */ import net.minecraft.advancements.DisplayInfo;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.advancements.AdvancementVisibilityEvaluator;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.server.players.PlayerList;
/*     */ import net.minecraft.util.datafix.DataFixTypes;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class PlayerAdvancements {
/*  49 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  50 */   private static final Gson GSON = (new GsonBuilder())
/*  51 */     .setPrettyPrinting()
/*  52 */     .create();
/*     */   private final PlayerList playerList;
/*     */   private final Path playerSavePath;
/*     */   private AdvancementTree tree;
/*  56 */   private final Map<AdvancementHolder, AdvancementProgress> progress = new LinkedHashMap<>();
/*  57 */   private final Set<AdvancementHolder> visible = new HashSet<>();
/*  58 */   private final Set<AdvancementHolder> progressChanged = new HashSet<>();
/*  59 */   private final Set<AdvancementNode> rootsToUpdate = new HashSet<>();
/*     */   private ServerPlayer player;
/*     */   @Nullable
/*     */   private AdvancementHolder lastSelectedTab;
/*     */   private boolean isFirstPacket = true;
/*     */   private final Codec<Data> codec;
/*     */   
/*     */   public PlayerAdvancements(DataFixer $$0, PlayerList $$1, ServerAdvancementManager $$2, Path $$3, ServerPlayer $$4) {
/*  67 */     this.playerList = $$1;
/*  68 */     this.playerSavePath = $$3;
/*  69 */     this.player = $$4;
/*  70 */     this.tree = $$2.tree();
/*     */     
/*  72 */     int $$5 = 1343;
/*  73 */     this.codec = DataFixTypes.ADVANCEMENTS.wrapCodec(Data.CODEC, $$0, 1343);
/*  74 */     load($$2);
/*     */   }
/*     */   
/*     */   public void setPlayer(ServerPlayer $$0) {
/*  78 */     this.player = $$0;
/*     */   }
/*     */   
/*     */   public void stopListening() {
/*  82 */     for (CriterionTrigger<?> $$0 : (Iterable<CriterionTrigger<?>>)BuiltInRegistries.TRIGGER_TYPES) {
/*  83 */       $$0.removePlayerListeners(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void reload(ServerAdvancementManager $$0) {
/*  88 */     stopListening();
/*  89 */     this.progress.clear();
/*  90 */     this.visible.clear();
/*  91 */     this.rootsToUpdate.clear();
/*  92 */     this.progressChanged.clear();
/*  93 */     this.isFirstPacket = true;
/*  94 */     this.lastSelectedTab = null;
/*  95 */     this.tree = $$0.tree();
/*  96 */     load($$0);
/*     */   }
/*     */   
/*     */   private void registerListeners(ServerAdvancementManager $$0) {
/* 100 */     for (AdvancementHolder $$1 : $$0.getAllAdvancements()) {
/* 101 */       registerListeners($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkForAutomaticTriggers(ServerAdvancementManager $$0) {
/* 106 */     for (AdvancementHolder $$1 : $$0.getAllAdvancements()) {
/* 107 */       Advancement $$2 = $$1.value();
/* 108 */       if ($$2.criteria().isEmpty()) {
/* 109 */         award($$1, "");
/* 110 */         $$2.rewards().grant(this.player);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void load(ServerAdvancementManager $$0) {
/* 116 */     if (Files.isRegularFile(this.playerSavePath, new java.nio.file.LinkOption[0])) {
/* 117 */       try { JsonReader $$1 = new JsonReader(Files.newBufferedReader(this.playerSavePath, StandardCharsets.UTF_8)); 
/* 118 */         try { $$1.setLenient(false);
/* 119 */           JsonElement $$2 = Streams.parse($$1);
/* 120 */           Data $$3 = (Data)Util.getOrThrow(this.codec.parse((DynamicOps)JsonOps.INSTANCE, $$2), JsonParseException::new);
/* 121 */           applyFrom($$0, $$3);
/* 122 */           $$1.close(); } catch (Throwable throwable) { try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (JsonParseException $$4)
/* 123 */       { LOGGER.error("Couldn't parse player advancements in {}", this.playerSavePath, $$4); }
/* 124 */       catch (IOException $$5)
/* 125 */       { LOGGER.error("Couldn't access player advancements in {}", this.playerSavePath, $$5); }
/*     */     
/*     */     }
/*     */     
/* 129 */     checkForAutomaticTriggers($$0);
/* 130 */     registerListeners($$0);
/*     */   }
/*     */   
/*     */   public void save() {
/* 134 */     JsonElement $$0 = (JsonElement)Util.getOrThrow(this.codec.encodeStart((DynamicOps)JsonOps.INSTANCE, asData()), IllegalStateException::new);
/*     */     
/* 136 */     try { FileUtil.createDirectoriesSafe(this.playerSavePath.getParent());
/* 137 */       Writer $$1 = Files.newBufferedWriter(this.playerSavePath, StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]); 
/* 138 */       try { GSON.toJson($$0, $$1);
/* 139 */         if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null)
/* 140 */           try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$2)
/* 141 */     { LOGGER.error("Couldn't save player advancements to {}", this.playerSavePath, $$2); }
/*     */   
/*     */   }
/*     */   
/*     */   private void applyFrom(ServerAdvancementManager $$0, Data $$1) {
/* 146 */     $$1.forEach(($$1, $$2) -> {
/*     */           AdvancementHolder $$3 = $$0.get($$1);
/*     */           if ($$3 == null) {
/*     */             LOGGER.warn("Ignored advancement '{}' in progress file {} - it doesn't exist anymore?", $$1, this.playerSavePath);
/*     */             return;
/*     */           } 
/*     */           startProgress($$3, $$2);
/*     */           this.progressChanged.add($$3);
/*     */           markForVisibilityUpdate($$3);
/*     */         });
/*     */   }
/*     */   
/*     */   private Data asData() {
/* 159 */     Map<ResourceLocation, AdvancementProgress> $$0 = new LinkedHashMap<>();
/* 160 */     this.progress.forEach(($$1, $$2) -> {
/*     */           if ($$2.hasProgress()) {
/*     */             $$0.put($$1.id(), $$2);
/*     */           }
/*     */         });
/* 165 */     return new Data($$0);
/*     */   }
/*     */   
/*     */   public boolean award(AdvancementHolder $$0, String $$1) {
/* 169 */     boolean $$2 = false;
/*     */     
/* 171 */     AdvancementProgress $$3 = getOrStartProgress($$0);
/* 172 */     boolean $$4 = $$3.isDone();
/*     */     
/* 174 */     if ($$3.grantProgress($$1)) {
/* 175 */       unregisterListeners($$0);
/* 176 */       this.progressChanged.add($$0);
/* 177 */       $$2 = true;
/*     */       
/* 179 */       if (!$$4 && $$3.isDone()) {
/* 180 */         $$0.value().rewards().grant(this.player);
/* 181 */         $$0.value().display().ifPresent($$1 -> {
/*     */               if ($$1.shouldAnnounceChat() && this.player.level().getGameRules().getBoolean(GameRules.RULE_ANNOUNCE_ADVANCEMENTS)) {
/*     */                 this.playerList.broadcastSystemMessage((Component)$$1.getType().createAnnouncement($$0, this.player), false);
/*     */               }
/*     */             });
/*     */       } 
/*     */     } 
/*     */     
/* 189 */     if (!$$4 && $$3.isDone()) {
/* 190 */       markForVisibilityUpdate($$0);
/*     */     }
/*     */     
/* 193 */     return $$2;
/*     */   }
/*     */   
/*     */   public boolean revoke(AdvancementHolder $$0, String $$1) {
/* 197 */     boolean $$2 = false;
/*     */     
/* 199 */     AdvancementProgress $$3 = getOrStartProgress($$0);
/* 200 */     boolean $$4 = $$3.isDone();
/* 201 */     if ($$3.revokeProgress($$1)) {
/* 202 */       registerListeners($$0);
/* 203 */       this.progressChanged.add($$0);
/* 204 */       $$2 = true;
/*     */     } 
/*     */     
/* 207 */     if ($$4 && !$$3.isDone()) {
/* 208 */       markForVisibilityUpdate($$0);
/*     */     }
/*     */     
/* 211 */     return $$2;
/*     */   }
/*     */   
/*     */   private void markForVisibilityUpdate(AdvancementHolder $$0) {
/* 215 */     AdvancementNode $$1 = this.tree.get($$0);
/* 216 */     if ($$1 != null) {
/* 217 */       this.rootsToUpdate.add($$1.root());
/*     */     }
/*     */   }
/*     */   
/*     */   private void registerListeners(AdvancementHolder $$0) {
/* 222 */     AdvancementProgress $$1 = getOrStartProgress($$0);
/* 223 */     if ($$1.isDone()) {
/*     */       return;
/*     */     }
/* 226 */     for (Map.Entry<String, Criterion<?>> $$2 : (Iterable<Map.Entry<String, Criterion<?>>>)$$0.value().criteria().entrySet()) {
/* 227 */       CriterionProgress $$3 = $$1.getCriterion($$2.getKey());
/* 228 */       if ($$3 == null || $$3.isDone()) {
/*     */         continue;
/*     */       }
/* 231 */       registerListener($$0, $$2.getKey(), (Criterion<CriterionTriggerInstance>)$$2.getValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T extends CriterionTriggerInstance> void registerListener(AdvancementHolder $$0, String $$1, Criterion<T> $$2) {
/* 236 */     $$2.trigger().addPlayerListener(this, new CriterionTrigger.Listener($$2.triggerInstance(), $$0, $$1));
/*     */   }
/*     */   
/*     */   private void unregisterListeners(AdvancementHolder $$0) {
/* 240 */     AdvancementProgress $$1 = getOrStartProgress($$0);
/* 241 */     for (Map.Entry<String, Criterion<?>> $$2 : (Iterable<Map.Entry<String, Criterion<?>>>)$$0.value().criteria().entrySet()) {
/* 242 */       CriterionProgress $$3 = $$1.getCriterion($$2.getKey());
/* 243 */       if ($$3 == null || (!$$3.isDone() && !$$1.isDone())) {
/*     */         continue;
/*     */       }
/* 246 */       removeListener($$0, $$2.getKey(), (Criterion<CriterionTriggerInstance>)$$2.getValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   private <T extends CriterionTriggerInstance> void removeListener(AdvancementHolder $$0, String $$1, Criterion<T> $$2) {
/* 251 */     $$2.trigger().removePlayerListener(this, new CriterionTrigger.Listener($$2.triggerInstance(), $$0, $$1));
/*     */   }
/*     */   
/*     */   public void flushDirty(ServerPlayer $$0) {
/* 255 */     if (this.isFirstPacket || !this.rootsToUpdate.isEmpty() || !this.progressChanged.isEmpty()) {
/* 256 */       Map<ResourceLocation, AdvancementProgress> $$1 = new HashMap<>();
/* 257 */       Set<AdvancementHolder> $$2 = new HashSet<>();
/* 258 */       Set<ResourceLocation> $$3 = new HashSet<>();
/*     */       
/* 260 */       for (AdvancementNode $$4 : this.rootsToUpdate) {
/* 261 */         updateTreeVisibility($$4, $$2, $$3);
/*     */       }
/* 263 */       this.rootsToUpdate.clear();
/*     */       
/* 265 */       for (AdvancementHolder $$5 : this.progressChanged) {
/* 266 */         if (this.visible.contains($$5)) {
/* 267 */           $$1.put($$5.id(), this.progress.get($$5));
/*     */         }
/*     */       } 
/* 270 */       this.progressChanged.clear();
/*     */       
/* 272 */       if (!$$1.isEmpty() || !$$2.isEmpty() || !$$3.isEmpty()) {
/* 273 */         $$0.connection.send((Packet)new ClientboundUpdateAdvancementsPacket(this.isFirstPacket, $$2, $$3, $$1));
/*     */       }
/*     */     } 
/* 276 */     this.isFirstPacket = false;
/*     */   }
/*     */   
/*     */   public void setSelectedTab(@Nullable AdvancementHolder $$0) {
/* 280 */     AdvancementHolder $$1 = this.lastSelectedTab;
/* 281 */     if ($$0 != null && $$0.value().isRoot() && $$0.value().display().isPresent()) {
/* 282 */       this.lastSelectedTab = $$0;
/*     */     } else {
/* 284 */       this.lastSelectedTab = null;
/*     */     } 
/* 286 */     if ($$1 != this.lastSelectedTab) {
/* 287 */       this.player.connection.send((Packet)new ClientboundSelectAdvancementsTabPacket((this.lastSelectedTab == null) ? null : this.lastSelectedTab.id()));
/*     */     }
/*     */   }
/*     */   
/*     */   public AdvancementProgress getOrStartProgress(AdvancementHolder $$0) {
/* 292 */     AdvancementProgress $$1 = this.progress.get($$0);
/* 293 */     if ($$1 == null) {
/* 294 */       $$1 = new AdvancementProgress();
/* 295 */       startProgress($$0, $$1);
/*     */     } 
/* 297 */     return $$1;
/*     */   }
/*     */   
/*     */   private void startProgress(AdvancementHolder $$0, AdvancementProgress $$1) {
/* 301 */     $$1.update($$0.value().requirements());
/* 302 */     this.progress.put($$0, $$1);
/*     */   }
/*     */   
/*     */   private void updateTreeVisibility(AdvancementNode $$0, Set<AdvancementHolder> $$1, Set<ResourceLocation> $$2) {
/* 306 */     AdvancementVisibilityEvaluator.evaluateVisibility($$0, $$0 -> getOrStartProgress($$0.holder()).isDone(), ($$2, $$3) -> {
/*     */           AdvancementHolder $$4 = $$2.holder();
/*     */           if ($$3) {
/*     */             if (this.visible.add($$4)) {
/*     */               $$0.add($$4);
/*     */               if (this.progress.containsKey($$4))
/*     */                 this.progressChanged.add($$4); 
/*     */             } 
/*     */           } else if (this.visible.remove($$4)) {
/*     */             $$1.add($$4.id());
/*     */           } 
/*     */         });
/*     */   }
/*     */   private static final class Data extends Record {
/*     */     private final Map<ResourceLocation, AdvancementProgress> map;
/*     */     public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/PlayerAdvancements$Data;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #327	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/PlayerAdvancements$Data;
/*     */     }
/*     */     public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/PlayerAdvancements$Data;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #327	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/PlayerAdvancements$Data;
/*     */     }
/* 327 */     Data(Map<ResourceLocation, AdvancementProgress> $$0) { this.map = $$0; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/PlayerAdvancements$Data;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #327	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/PlayerAdvancements$Data;
/* 327 */       //   0	8	1	$$0	Ljava/lang/Object; } public Map<ResourceLocation, AdvancementProgress> map() { return this.map; }
/* 328 */      public static final Codec<Data> CODEC = Codec.unboundedMap(ResourceLocation.CODEC, AdvancementProgress.CODEC).xmap(Data::new, Data::map);
/*     */     
/*     */     public void forEach(BiConsumer<ResourceLocation, AdvancementProgress> $$0) {
/* 331 */       this.map.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach($$1 -> $$0.accept((ResourceLocation)$$1.getKey(), (AdvancementProgress)$$1.getValue()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\PlayerAdvancements.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */