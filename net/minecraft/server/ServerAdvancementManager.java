/*    */ package net.minecraft.server;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Multimap;
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.JsonOps;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.advancements.Advancement;
/*    */ import net.minecraft.advancements.AdvancementHolder;
/*    */ import net.minecraft.advancements.AdvancementNode;
/*    */ import net.minecraft.advancements.AdvancementTree;
/*    */ import net.minecraft.advancements.TreeNodePosition;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
/*    */ import net.minecraft.util.ProblemReporter;
/*    */ import net.minecraft.util.profiling.ProfilerFiller;
/*    */ import net.minecraft.world.level.storage.loot.LootDataManager;
/*    */ import net.minecraft.world.level.storage.loot.LootDataResolver;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class ServerAdvancementManager extends SimpleJsonResourceReloadListener {
/* 32 */   private static final Logger LOGGER = LogUtils.getLogger();
/* 33 */   private static final Gson GSON = (new GsonBuilder()).create();
/*    */   
/* 35 */   private Map<ResourceLocation, AdvancementHolder> advancements = Map.of();
/* 36 */   private AdvancementTree tree = new AdvancementTree();
/*    */   private final LootDataManager lootData;
/*    */   
/*    */   public ServerAdvancementManager(LootDataManager $$0) {
/* 40 */     super(GSON, "advancements");
/* 41 */     this.lootData = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void apply(Map<ResourceLocation, JsonElement> $$0, ResourceManager $$1, ProfilerFiller $$2) {
/* 46 */     ImmutableMap.Builder<ResourceLocation, AdvancementHolder> $$3 = ImmutableMap.builder();
/* 47 */     $$0.forEach(($$1, $$2) -> {
/*    */           try {
/*    */             Advancement $$3 = (Advancement)Util.getOrThrow(Advancement.CODEC.parse((DynamicOps)JsonOps.INSTANCE, $$2), com.google.gson.JsonParseException::new);
/*    */             validate($$1, $$3);
/*    */             $$0.put($$1, new AdvancementHolder($$1, $$3));
/* 52 */           } catch (Exception $$4) {
/*    */             LOGGER.error("Parsing error loading custom advancement {}: {}", $$1, $$4.getMessage());
/*    */           } 
/*    */         });
/*    */     
/* 57 */     this.advancements = (Map<ResourceLocation, AdvancementHolder>)$$3.buildOrThrow();
/*    */     
/* 59 */     AdvancementTree $$4 = new AdvancementTree();
/* 60 */     $$4.addAll(this.advancements.values());
/*    */     
/* 62 */     for (AdvancementNode $$5 : $$4.roots()) {
/* 63 */       if ($$5.holder().value().display().isPresent()) {
/* 64 */         TreeNodePosition.run($$5);
/*    */       }
/*    */     } 
/*    */     
/* 68 */     this.tree = $$4;
/*    */   }
/*    */   
/*    */   private void validate(ResourceLocation $$0, Advancement $$1) {
/* 72 */     ProblemReporter.Collector $$2 = new ProblemReporter.Collector();
/* 73 */     $$1.validate((ProblemReporter)$$2, (LootDataResolver)this.lootData);
/*    */     
/* 75 */     Multimap<String, String> $$3 = $$2.get();
/* 76 */     if (!$$3.isEmpty()) {
/*    */ 
/*    */       
/* 79 */       String $$4 = $$3.asMap().entrySet().stream().map($$0 -> "  at " + (String)$$0.getKey() + ": " + String.join("; ", (Iterable<? extends CharSequence>)$$0.getValue())).collect(Collectors.joining("\n"));
/* 80 */       LOGGER.warn("Found validation problems in advancement {}: \n{}", $$0, $$4);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public AdvancementHolder get(ResourceLocation $$0) {
/* 86 */     return this.advancements.get($$0);
/*    */   }
/*    */   
/*    */   public AdvancementTree tree() {
/* 90 */     return this.tree;
/*    */   }
/*    */   
/*    */   public Collection<AdvancementHolder> getAllAdvancements() {
/* 94 */     return this.advancements.values();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\ServerAdvancementManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */