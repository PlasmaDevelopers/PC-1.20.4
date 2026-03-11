/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ 
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.saveddata.maps.MapDecoration;
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
/*     */ public class Builder
/*     */   extends LootItemConditionalFunction.Builder<ExplorationMapFunction.Builder>
/*     */ {
/*  92 */   private TagKey<Structure> destination = ExplorationMapFunction.DEFAULT_DESTINATION;
/*  93 */   private MapDecoration.Type mapDecoration = ExplorationMapFunction.DEFAULT_DECORATION;
/*  94 */   private byte zoom = 2;
/*  95 */   private int searchRadius = 50;
/*     */   
/*     */   private boolean skipKnownStructures = true;
/*     */   
/*     */   protected Builder getThis() {
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setDestination(TagKey<Structure> $$0) {
/* 104 */     this.destination = $$0;
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setMapDecoration(MapDecoration.Type $$0) {
/* 109 */     this.mapDecoration = $$0;
/* 110 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setZoom(byte $$0) {
/* 114 */     this.zoom = $$0;
/* 115 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setSearchRadius(int $$0) {
/* 119 */     this.searchRadius = $$0;
/* 120 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setSkipKnownStructures(boolean $$0) {
/* 124 */     this.skipKnownStructures = $$0;
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public LootItemFunction build() {
/* 130 */     return new ExplorationMapFunction(getConditions(), this.destination, this.mapDecoration, this.zoom, this.searchRadius, this.skipKnownStructures);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\ExplorationMapFunction$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */