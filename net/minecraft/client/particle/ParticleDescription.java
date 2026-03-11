/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.Streams;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ 
/*    */ public class ParticleDescription
/*    */ {
/*    */   private ParticleDescription(List<ResourceLocation> $$0) {
/* 16 */     this.textures = $$0;
/*    */   }
/*    */   private final List<ResourceLocation> textures;
/*    */   public List<ResourceLocation> getTextures() {
/* 20 */     return this.textures;
/*    */   }
/*    */   
/*    */   public static ParticleDescription fromJson(JsonObject $$0) {
/* 24 */     JsonArray $$1 = GsonHelper.getAsJsonArray($$0, "textures", null);
/* 25 */     if ($$1 == null) {
/* 26 */       return new ParticleDescription(List.of());
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 32 */     List<ResourceLocation> $$2 = (List<ResourceLocation>)Streams.stream((Iterable)$$1).map($$0 -> GsonHelper.convertToString($$0, "texture")).map(ResourceLocation::new).collect(ImmutableList.toImmutableList());
/*    */     
/* 34 */     return new ParticleDescription($$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\ParticleDescription.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */