/*     */ package net.minecraft.client.renderer.block.model.multipart;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.block.model.BlockModelDefinition;
/*     */ import net.minecraft.client.renderer.block.model.MultiVariant;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.model.BakedModel;
/*     */ import net.minecraft.client.resources.model.Material;
/*     */ import net.minecraft.client.resources.model.ModelBaker;
/*     */ import net.minecraft.client.resources.model.ModelState;
/*     */ import net.minecraft.client.resources.model.MultiPartBakedModel;
/*     */ import net.minecraft.client.resources.model.UnbakedModel;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ 
/*     */ public class MultiPart implements UnbakedModel {
/*     */   private final StateDefinition<Block, BlockState> definition;
/*     */   private final List<Selector> selectors;
/*     */   
/*     */   public MultiPart(StateDefinition<Block, BlockState> $$0, List<Selector> $$1) {
/*  38 */     this.definition = $$0;
/*  39 */     this.selectors = $$1;
/*     */   }
/*     */   
/*     */   public List<Selector> getSelectors() {
/*  43 */     return this.selectors;
/*     */   }
/*     */   
/*     */   public Set<MultiVariant> getMultiVariants() {
/*  47 */     Set<MultiVariant> $$0 = Sets.newHashSet();
/*     */     
/*  49 */     for (Selector $$1 : this.selectors) {
/*  50 */       $$0.add($$1.getVariant());
/*     */     }
/*     */     
/*  53 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  58 */     if (this == $$0) {
/*  59 */       return true;
/*     */     }
/*  61 */     if ($$0 instanceof MultiPart) { MultiPart $$1 = (MultiPart)$$0;
/*  62 */       return (Objects.equals(this.definition, $$1.definition) && Objects.equals(this.selectors, $$1.selectors)); }
/*     */     
/*  64 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  69 */     return Objects.hash(new Object[] { this.definition, this.selectors });
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ResourceLocation> getDependencies() {
/*  74 */     return (Collection<ResourceLocation>)getSelectors().stream().flatMap($$0 -> $$0.getVariant().getDependencies().stream()).collect(Collectors.toSet());
/*     */   }
/*     */ 
/*     */   
/*     */   public void resolveParents(Function<ResourceLocation, UnbakedModel> $$0) {
/*  79 */     getSelectors().forEach($$1 -> $$1.getVariant().resolveParents($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BakedModel bake(ModelBaker $$0, Function<Material, TextureAtlasSprite> $$1, ModelState $$2, ResourceLocation $$3) {
/*  85 */     MultiPartBakedModel.Builder $$4 = new MultiPartBakedModel.Builder();
/*     */     
/*  87 */     for (Selector $$5 : getSelectors()) {
/*  88 */       BakedModel $$6 = $$5.getVariant().bake($$0, $$1, $$2, $$3);
/*  89 */       if ($$6 != null) {
/*  90 */         $$4.add($$5.getPredicate(this.definition), $$6);
/*     */       }
/*     */     } 
/*     */     
/*  94 */     return $$4.build();
/*     */   }
/*     */   
/*     */   public static class Deserializer implements JsonDeserializer<MultiPart> {
/*     */     private final BlockModelDefinition.Context context;
/*     */     
/*     */     public Deserializer(BlockModelDefinition.Context $$0) {
/* 101 */       this.context = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public MultiPart deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 106 */       return new MultiPart(this.context.getDefinition(), getSelectors($$2, $$0.getAsJsonArray()));
/*     */     }
/*     */     
/*     */     private List<Selector> getSelectors(JsonDeserializationContext $$0, JsonArray $$1) {
/* 110 */       List<Selector> $$2 = Lists.newArrayList();
/*     */       
/* 112 */       for (JsonElement $$3 : $$1) {
/* 113 */         $$2.add((Selector)$$0.deserialize($$3, Selector.class));
/*     */       }
/*     */       
/* 116 */       return $$2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\multipart\MultiPart.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */