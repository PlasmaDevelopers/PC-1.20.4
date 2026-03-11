/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.util.Mth;
/*     */ import org.joml.Vector3f;
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
/*     */   implements JsonDeserializer<ItemTransform>
/*     */ {
/*  74 */   private static final Vector3f DEFAULT_ROTATION = new Vector3f(0.0F, 0.0F, 0.0F);
/*  75 */   private static final Vector3f DEFAULT_TRANSLATION = new Vector3f(0.0F, 0.0F, 0.0F);
/*  76 */   private static final Vector3f DEFAULT_SCALE = new Vector3f(1.0F, 1.0F, 1.0F);
/*     */   
/*     */   public static final float MAX_TRANSLATION = 5.0F;
/*     */   public static final float MAX_SCALE = 4.0F;
/*     */   
/*     */   public ItemTransform deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/*  82 */     JsonObject $$3 = $$0.getAsJsonObject();
/*     */     
/*  84 */     Vector3f $$4 = getVector3f($$3, "rotation", DEFAULT_ROTATION);
/*     */     
/*  86 */     Vector3f $$5 = getVector3f($$3, "translation", DEFAULT_TRANSLATION);
/*  87 */     $$5.mul(0.0625F);
/*  88 */     $$5.set(
/*  89 */         Mth.clamp($$5.x, -5.0F, 5.0F), 
/*  90 */         Mth.clamp($$5.y, -5.0F, 5.0F), 
/*  91 */         Mth.clamp($$5.z, -5.0F, 5.0F));
/*     */ 
/*     */     
/*  94 */     Vector3f $$6 = getVector3f($$3, "scale", DEFAULT_SCALE);
/*  95 */     $$6.set(
/*  96 */         Mth.clamp($$6.x, -4.0F, 4.0F), 
/*  97 */         Mth.clamp($$6.y, -4.0F, 4.0F), 
/*  98 */         Mth.clamp($$6.z, -4.0F, 4.0F));
/*     */ 
/*     */     
/* 101 */     return new ItemTransform($$4, $$5, $$6);
/*     */   }
/*     */   
/*     */   private Vector3f getVector3f(JsonObject $$0, String $$1, Vector3f $$2) {
/* 105 */     if (!$$0.has($$1)) {
/* 106 */       return $$2;
/*     */     }
/* 108 */     JsonArray $$3 = GsonHelper.getAsJsonArray($$0, $$1);
/* 109 */     if ($$3.size() != 3) {
/* 110 */       throw new JsonParseException("Expected 3 " + $$1 + " values, found: " + $$3.size());
/*     */     }
/*     */     
/* 113 */     float[] $$4 = new float[3];
/* 114 */     for (int $$5 = 0; $$5 < $$4.length; $$5++) {
/* 115 */       $$4[$$5] = GsonHelper.convertToFloat($$3.get($$5), $$1 + "[" + $$1 + "]");
/*     */     }
/* 117 */     return new Vector3f($$4[0], $$4[1], $$4[2]);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\ItemTransform$Deserializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */