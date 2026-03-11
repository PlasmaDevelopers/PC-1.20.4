/*     */ package net.minecraft.world.level.storage.loot.parameters;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ public class LootContextParamSets {
/*  13 */   private static final BiMap<ResourceLocation, LootContextParamSet> REGISTRY = (BiMap<ResourceLocation, LootContextParamSet>)HashBiMap.create();
/*     */   
/*     */   public static final Codec<LootContextParamSet> CODEC;
/*     */ 
/*     */   
/*     */   static {
/*  19 */     Objects.requireNonNull(REGISTRY.inverse()); CODEC = ResourceLocation.CODEC.comapFlatMap($$0 -> (DataResult)Optional.<LootContextParamSet>ofNullable((LootContextParamSet)REGISTRY.get($$0)).map(DataResult::success).orElseGet(()), REGISTRY.inverse()::get);
/*     */   }
/*     */   public static final LootContextParamSet CHEST; public static final LootContextParamSet COMMAND; public static final LootContextParamSet SELECTOR;
/*  22 */   public static final LootContextParamSet EMPTY = register("empty", $$0 -> {
/*     */       
/*  24 */       }); public static final LootContextParamSet FISHING; public static final LootContextParamSet ENTITY; public static final LootContextParamSet ARCHAEOLOGY; static { CHEST = register("chest", $$0 -> $$0.required(LootContextParams.ORIGIN).optional(LootContextParams.THIS_ENTITY));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  29 */     COMMAND = register("command", $$0 -> $$0.required(LootContextParams.ORIGIN).optional(LootContextParams.THIS_ENTITY));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  34 */     SELECTOR = register("selector", $$0 -> $$0.required(LootContextParams.ORIGIN).required(LootContextParams.THIS_ENTITY));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  39 */     FISHING = register("fishing", $$0 -> $$0.required(LootContextParams.ORIGIN).required(LootContextParams.TOOL).optional(LootContextParams.THIS_ENTITY));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  45 */     ENTITY = register("entity", $$0 -> $$0.required(LootContextParams.THIS_ENTITY).required(LootContextParams.ORIGIN).required(LootContextParams.DAMAGE_SOURCE).optional(LootContextParams.KILLER_ENTITY).optional(LootContextParams.DIRECT_KILLER_ENTITY).optional(LootContextParams.LAST_DAMAGE_PLAYER));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     ARCHAEOLOGY = register("archaeology", $$0 -> $$0.required(LootContextParams.ORIGIN).optional(LootContextParams.THIS_ENTITY));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  59 */     GIFT = register("gift", $$0 -> $$0.required(LootContextParams.ORIGIN).required(LootContextParams.THIS_ENTITY));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     PIGLIN_BARTER = register("barter", $$0 -> $$0.required(LootContextParams.THIS_ENTITY));
/*     */ 
/*     */ 
/*     */     
/*  68 */     ADVANCEMENT_REWARD = register("advancement_reward", $$0 -> $$0.required(LootContextParams.THIS_ENTITY).required(LootContextParams.ORIGIN));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     ADVANCEMENT_ENTITY = register("advancement_entity", $$0 -> $$0.required(LootContextParams.THIS_ENTITY).required(LootContextParams.ORIGIN));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     ADVANCEMENT_LOCATION = register("advancement_location", $$0 -> $$0.required(LootContextParams.THIS_ENTITY).required(LootContextParams.ORIGIN).required(LootContextParams.TOOL).required(LootContextParams.BLOCK_STATE));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     ALL_PARAMS = register("generic", $$0 -> $$0.required(LootContextParams.THIS_ENTITY).required(LootContextParams.LAST_DAMAGE_PLAYER).required(LootContextParams.DAMAGE_SOURCE).required(LootContextParams.KILLER_ENTITY).required(LootContextParams.DIRECT_KILLER_ENTITY).required(LootContextParams.ORIGIN).required(LootContextParams.BLOCK_STATE).required(LootContextParams.BLOCK_ENTITY).required(LootContextParams.TOOL).required(LootContextParams.EXPLOSION_RADIUS));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     BLOCK = register("block", $$0 -> $$0.required(LootContextParams.BLOCK_STATE).required(LootContextParams.ORIGIN).required(LootContextParams.TOOL).optional(LootContextParams.THIS_ENTITY).optional(LootContextParams.BLOCK_ENTITY).optional(LootContextParams.EXPLOSION_RADIUS)); }
/*     */   
/*     */   public static final LootContextParamSet GIFT; public static final LootContextParamSet PIGLIN_BARTER;
/*     */   public static final LootContextParamSet ADVANCEMENT_REWARD;
/*     */   public static final LootContextParamSet ADVANCEMENT_ENTITY;
/*     */   public static final LootContextParamSet ADVANCEMENT_LOCATION;
/*     */   public static final LootContextParamSet ALL_PARAMS;
/*     */   public static final LootContextParamSet BLOCK;
/*     */   
/*     */   private static LootContextParamSet register(String $$0, Consumer<LootContextParamSet.Builder> $$1) {
/* 108 */     LootContextParamSet.Builder $$2 = new LootContextParamSet.Builder();
/* 109 */     $$1.accept($$2);
/* 110 */     LootContextParamSet $$3 = $$2.build();
/* 111 */     ResourceLocation $$4 = new ResourceLocation($$0);
/* 112 */     LootContextParamSet $$5 = (LootContextParamSet)REGISTRY.put($$4, $$3);
/* 113 */     if ($$5 != null) {
/* 114 */       throw new IllegalStateException("Loot table parameter set " + $$4 + " is already registered");
/*     */     }
/* 116 */     return $$3;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\parameters\LootContextParamSets.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */