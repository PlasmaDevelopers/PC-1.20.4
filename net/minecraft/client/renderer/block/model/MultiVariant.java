/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.client.resources.model.BakedModel;
/*    */ import net.minecraft.client.resources.model.Material;
/*    */ import net.minecraft.client.resources.model.ModelBaker;
/*    */ import net.minecraft.client.resources.model.ModelState;
/*    */ import net.minecraft.client.resources.model.UnbakedModel;
/*    */ import net.minecraft.client.resources.model.WeightedBakedModel;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class MultiVariant
/*    */   implements UnbakedModel {
/*    */   private final List<Variant> variants;
/*    */   
/*    */   public MultiVariant(List<Variant> $$0) {
/* 29 */     this.variants = $$0;
/*    */   }
/*    */   
/*    */   public List<Variant> getVariants() {
/* 33 */     return this.variants;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 38 */     if (this == $$0) {
/* 39 */       return true;
/*    */     }
/*    */     
/* 42 */     if ($$0 instanceof MultiVariant) { MultiVariant $$1 = (MultiVariant)$$0;
/* 43 */       return this.variants.equals($$1.variants); }
/*    */ 
/*    */     
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 51 */     return this.variants.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<ResourceLocation> getDependencies() {
/* 56 */     return (Collection<ResourceLocation>)getVariants().stream().map(Variant::getModelLocation).collect(Collectors.toSet());
/*    */   }
/*    */ 
/*    */   
/*    */   public void resolveParents(Function<ResourceLocation, UnbakedModel> $$0) {
/* 61 */     getVariants().stream().map(Variant::getModelLocation).distinct().forEach($$1 -> ((UnbakedModel)$$0.apply($$1)).resolveParents($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BakedModel bake(ModelBaker $$0, Function<Material, TextureAtlasSprite> $$1, ModelState $$2, ResourceLocation $$3) {
/* 67 */     if (getVariants().isEmpty()) {
/* 68 */       return null;
/*    */     }
/*    */     
/* 71 */     WeightedBakedModel.Builder $$4 = new WeightedBakedModel.Builder();
/* 72 */     for (Variant $$5 : getVariants()) {
/* 73 */       BakedModel $$6 = $$0.bake($$5.getModelLocation(), $$5);
/*    */       
/* 75 */       $$4.add($$6, $$5.getWeight());
/*    */     } 
/*    */     
/* 78 */     return $$4.build();
/*    */   }
/*    */   
/*    */   public static class Deserializer
/*    */     implements JsonDeserializer<MultiVariant> {
/*    */     public MultiVariant deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 84 */       List<Variant> $$3 = Lists.newArrayList();
/* 85 */       if ($$0.isJsonArray()) {
/* 86 */         JsonArray $$4 = $$0.getAsJsonArray();
/* 87 */         if ($$4.size() == 0) {
/* 88 */           throw new JsonParseException("Empty variant array");
/*    */         }
/*    */         
/* 91 */         for (JsonElement $$5 : $$4) {
/* 92 */           $$3.add((Variant)$$2.deserialize($$5, Variant.class));
/*    */         }
/*    */       } else {
/* 95 */         $$3.add((Variant)$$2.deserialize($$0, Variant.class));
/*    */       } 
/* 97 */       return new MultiVariant($$3);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\MultiVariant.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */