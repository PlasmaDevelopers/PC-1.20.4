/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.renderer.item.ItemProperties;
/*     */ import net.minecraft.client.renderer.item.ItemPropertyFunction;
/*     */ import net.minecraft.client.resources.model.BakedModel;
/*     */ import net.minecraft.client.resources.model.BlockModelRotation;
/*     */ import net.minecraft.client.resources.model.ModelBaker;
/*     */ import net.minecraft.client.resources.model.ModelState;
/*     */ import net.minecraft.client.resources.model.UnbakedModel;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class ItemOverrides {
/*  23 */   public static final ItemOverrides EMPTY = new ItemOverrides();
/*     */   
/*     */   public static final float NO_OVERRIDE = -InfinityF;
/*     */   private final BakedOverride[] overrides;
/*     */   private final ResourceLocation[] properties;
/*     */   
/*     */   private ItemOverrides() {
/*  30 */     this.overrides = new BakedOverride[0];
/*  31 */     this.properties = new ResourceLocation[0];
/*     */   }
/*     */   
/*     */   public ItemOverrides(ModelBaker $$0, BlockModel $$1, List<ItemOverride> $$2) {
/*  35 */     this
/*     */ 
/*     */ 
/*     */       
/*  39 */       .properties = (ResourceLocation[])$$2.stream().flatMap(ItemOverride::getPredicates).map(ItemOverride.Predicate::getProperty).distinct().toArray($$0 -> new ResourceLocation[$$0]);
/*     */     
/*  41 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/*  42 */     for (int $$4 = 0; $$4 < this.properties.length; $$4++) {
/*  43 */       object2IntOpenHashMap.put(this.properties[$$4], $$4);
/*     */     }
/*     */     
/*  46 */     List<BakedOverride> $$5 = Lists.newArrayList();
/*  47 */     for (int $$6 = $$2.size() - 1; $$6 >= 0; $$6--) {
/*  48 */       ItemOverride $$7 = $$2.get($$6);
/*  49 */       BakedModel $$8 = bakeModel($$0, $$1, $$7);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  54 */       PropertyMatcher[] $$9 = (PropertyMatcher[])$$7.getPredicates().map($$1 -> { int $$2 = $$0.getInt($$1.getProperty()); return new PropertyMatcher($$2, $$1.getValue()); }).toArray($$0 -> new PropertyMatcher[$$0]);
/*     */       
/*  56 */       $$5.add(new BakedOverride($$9, $$8));
/*     */     } 
/*  58 */     this.overrides = $$5.<BakedOverride>toArray(new BakedOverride[0]);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BakedModel bakeModel(ModelBaker $$0, BlockModel $$1, ItemOverride $$2) {
/*  63 */     UnbakedModel $$3 = $$0.getModel($$2.getModel());
/*  64 */     if (Objects.equals($$3, $$1)) {
/*  65 */       return null;
/*     */     }
/*  67 */     return $$0.bake($$2.getModel(), (ModelState)BlockModelRotation.X0_Y0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BakedModel resolve(BakedModel $$0, ItemStack $$1, @Nullable ClientLevel $$2, @Nullable LivingEntity $$3, int $$4) {
/*  72 */     if (this.overrides.length != 0) {
/*  73 */       Item $$5 = $$1.getItem();
/*  74 */       int $$6 = this.properties.length;
/*  75 */       float[] $$7 = new float[$$6];
/*  76 */       for (int $$8 = 0; $$8 < $$6; $$8++) {
/*  77 */         ResourceLocation $$9 = this.properties[$$8];
/*  78 */         ItemPropertyFunction $$10 = ItemProperties.getProperty($$5, $$9);
/*  79 */         if ($$10 != null) {
/*  80 */           $$7[$$8] = $$10.call($$1, $$2, $$3, $$4);
/*     */         } else {
/*  82 */           $$7[$$8] = Float.NEGATIVE_INFINITY;
/*     */         } 
/*     */       } 
/*     */       
/*  86 */       for (BakedOverride $$11 : this.overrides) {
/*  87 */         if ($$11.test($$7)) {
/*  88 */           BakedModel $$12 = $$11.model;
/*  89 */           if ($$12 == null) {
/*  90 */             return $$0;
/*     */           }
/*  92 */           return $$12;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  97 */     return $$0;
/*     */   }
/*     */   
/*     */   private static class PropertyMatcher {
/*     */     public final int index;
/*     */     public final float value;
/*     */     
/*     */     PropertyMatcher(int $$0, float $$1) {
/* 105 */       this.index = $$0;
/* 106 */       this.value = $$1;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class BakedOverride {
/*     */     private final ItemOverrides.PropertyMatcher[] matchers;
/*     */     @Nullable
/*     */     final BakedModel model;
/*     */     
/*     */     BakedOverride(ItemOverrides.PropertyMatcher[] $$0, @Nullable BakedModel $$1) {
/* 116 */       this.matchers = $$0;
/* 117 */       this.model = $$1;
/*     */     }
/*     */     
/*     */     boolean test(float[] $$0) {
/* 121 */       for (ItemOverrides.PropertyMatcher $$1 : this.matchers) {
/* 122 */         float $$2 = $$0[$$1.index];
/* 123 */         if ($$2 < $$1.value) {
/* 124 */           return false;
/*     */         }
/*     */       } 
/* 127 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\ItemOverrides.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */