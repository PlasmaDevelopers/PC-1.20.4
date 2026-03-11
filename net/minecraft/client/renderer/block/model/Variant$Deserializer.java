/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import net.minecraft.client.resources.model.BlockModelRotation;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.GsonHelper;
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
/*     */ public class Deserializer
/*     */   implements JsonDeserializer<Variant>
/*     */ {
/*     */   @VisibleForTesting
/*     */   static final boolean DEFAULT_UVLOCK = false;
/*     */   @VisibleForTesting
/*     */   static final int DEFAULT_WEIGHT = 1;
/*     */   @VisibleForTesting
/*     */   static final int DEFAULT_X_ROTATION = 0;
/*     */   @VisibleForTesting
/*     */   static final int DEFAULT_Y_ROTATION = 0;
/*     */   
/*     */   public Variant deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/*  91 */     JsonObject $$3 = $$0.getAsJsonObject();
/*     */     
/*  93 */     ResourceLocation $$4 = getModel($$3);
/*  94 */     BlockModelRotation $$5 = getBlockRotation($$3);
/*  95 */     boolean $$6 = getUvLock($$3);
/*  96 */     int $$7 = getWeight($$3);
/*     */     
/*  98 */     return new Variant($$4, $$5.getRotation(), $$6, $$7);
/*     */   }
/*     */   
/*     */   private boolean getUvLock(JsonObject $$0) {
/* 102 */     return GsonHelper.getAsBoolean($$0, "uvlock", false);
/*     */   }
/*     */   
/*     */   protected BlockModelRotation getBlockRotation(JsonObject $$0) {
/* 106 */     int $$1 = GsonHelper.getAsInt($$0, "x", 0);
/* 107 */     int $$2 = GsonHelper.getAsInt($$0, "y", 0);
/*     */     
/* 109 */     BlockModelRotation $$3 = BlockModelRotation.by($$1, $$2);
/* 110 */     if ($$3 == null) {
/* 111 */       throw new JsonParseException("Invalid BlockModelRotation x: " + $$1 + ", y: " + $$2);
/*     */     }
/* 113 */     return $$3;
/*     */   }
/*     */   
/*     */   protected ResourceLocation getModel(JsonObject $$0) {
/* 117 */     return new ResourceLocation(GsonHelper.getAsString($$0, "model"));
/*     */   }
/*     */   
/*     */   protected int getWeight(JsonObject $$0) {
/* 121 */     int $$1 = GsonHelper.getAsInt($$0, "weight", 1);
/*     */     
/* 123 */     if ($$1 < 1) {
/* 124 */       throw new JsonParseException("Invalid weight " + $$1 + " found, expected integer >= 1");
/*     */     }
/* 126 */     return $$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\Variant$Deserializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */