/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function6;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.StructureTags;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.MapItem;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.saveddata.maps.MapDecoration;
/*     */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class ExplorationMapFunction extends LootItemConditionalFunction {
/*  29 */   public static final TagKey<Structure> DEFAULT_DESTINATION = StructureTags.ON_TREASURE_MAPS;
/*  30 */   public static final MapDecoration.Type DEFAULT_DECORATION = MapDecoration.Type.MANSION; public static final byte DEFAULT_ZOOM = 2; public static final int DEFAULT_SEARCH_RADIUS = 50;
/*     */   public static final boolean DEFAULT_SKIP_EXISTING = true;
/*     */   public static final Codec<ExplorationMapFunction> CODEC;
/*     */   
/*     */   static {
/*  35 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and($$0.group((App)ExtraCodecs.strictOptionalField(TagKey.codec(Registries.STRUCTURE), "destination", DEFAULT_DESTINATION).forGetter(()), (App)MapDecoration.Type.CODEC.optionalFieldOf("decoration", DEFAULT_DECORATION).forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BYTE, "zoom", Byte.valueOf((byte)2)).forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.INT, "search_radius", Integer.valueOf(50)).forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "skip_existing_chunks", Boolean.valueOf(true)).forGetter(()))).apply((Applicative)$$0, ExplorationMapFunction::new));
/*     */   }
/*     */ 
/*     */   
/*     */   private final TagKey<Structure> destination;
/*     */   
/*     */   private final MapDecoration.Type mapDecoration;
/*     */   
/*     */   private final byte zoom;
/*     */   
/*     */   private final int searchRadius;
/*     */   
/*     */   private final boolean skipKnownStructures;
/*     */ 
/*     */   
/*     */   ExplorationMapFunction(List<LootItemCondition> $$0, TagKey<Structure> $$1, MapDecoration.Type $$2, byte $$3, int $$4, boolean $$5) {
/*  51 */     super($$0);
/*  52 */     this.destination = $$1;
/*  53 */     this.mapDecoration = $$2;
/*  54 */     this.zoom = $$3;
/*  55 */     this.searchRadius = $$4;
/*  56 */     this.skipKnownStructures = $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   public LootItemFunctionType getType() {
/*  61 */     return LootItemFunctions.EXPLORATION_MAP;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<LootContextParam<?>> getReferencedContextParams() {
/*  66 */     return (Set<LootContextParam<?>>)ImmutableSet.of(LootContextParams.ORIGIN);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/*  71 */     if (!$$0.is(Items.MAP)) {
/*  72 */       return $$0;
/*     */     }
/*     */     
/*  75 */     Vec3 $$2 = (Vec3)$$1.getParamOrNull(LootContextParams.ORIGIN);
/*  76 */     if ($$2 != null) {
/*  77 */       ServerLevel $$3 = $$1.getLevel();
/*     */       
/*  79 */       BlockPos $$4 = $$3.findNearestMapStructure(this.destination, BlockPos.containing((Position)$$2), this.searchRadius, this.skipKnownStructures);
/*  80 */       if ($$4 != null) {
/*  81 */         ItemStack $$5 = MapItem.create((Level)$$3, $$4.getX(), $$4.getZ(), this.zoom, true, true);
/*  82 */         MapItem.renderBiomePreviewMap($$3, $$5);
/*  83 */         MapItemSavedData.addTargetDecoration($$5, $$4, "+", this.mapDecoration);
/*  84 */         return $$5;
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     return $$0;
/*     */   }
/*     */   
/*     */   public static class Builder extends LootItemConditionalFunction.Builder<Builder> {
/*  92 */     private TagKey<Structure> destination = ExplorationMapFunction.DEFAULT_DESTINATION;
/*  93 */     private MapDecoration.Type mapDecoration = ExplorationMapFunction.DEFAULT_DECORATION;
/*  94 */     private byte zoom = 2;
/*  95 */     private int searchRadius = 50;
/*     */     
/*     */     private boolean skipKnownStructures = true;
/*     */     
/*     */     protected Builder getThis() {
/* 100 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setDestination(TagKey<Structure> $$0) {
/* 104 */       this.destination = $$0;
/* 105 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setMapDecoration(MapDecoration.Type $$0) {
/* 109 */       this.mapDecoration = $$0;
/* 110 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setZoom(byte $$0) {
/* 114 */       this.zoom = $$0;
/* 115 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setSearchRadius(int $$0) {
/* 119 */       this.searchRadius = $$0;
/* 120 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setSkipKnownStructures(boolean $$0) {
/* 124 */       this.skipKnownStructures = $$0;
/* 125 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LootItemFunction build() {
/* 130 */       return new ExplorationMapFunction(getConditions(), this.destination, this.mapDecoration, this.zoom, this.searchRadius, this.skipKnownStructures);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder makeExplorationMap() {
/* 135 */     return new Builder();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\ExplorationMapFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */