/*     */ package net.minecraft.client.renderer.block.model;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Direction;
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
/*     */ public class Deserializer
/*     */   implements JsonDeserializer<BlockElement>
/*     */ {
/*     */   private static final boolean DEFAULT_SHADE = true;
/*     */   
/*     */   public BlockElement deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/*  72 */     JsonObject $$3 = $$0.getAsJsonObject();
/*  73 */     Vector3f $$4 = getFrom($$3);
/*  74 */     Vector3f $$5 = getTo($$3);
/*  75 */     BlockElementRotation $$6 = getRotation($$3);
/*  76 */     Map<Direction, BlockElementFace> $$7 = getFaces($$2, $$3);
/*  77 */     if ($$3.has("shade") && !GsonHelper.isBooleanValue($$3, "shade")) {
/*  78 */       throw new JsonParseException("Expected shade to be a Boolean");
/*     */     }
/*  80 */     boolean $$8 = GsonHelper.getAsBoolean($$3, "shade", true);
/*     */     
/*  82 */     return new BlockElement($$4, $$5, $$7, $$6, $$8);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockElementRotation getRotation(JsonObject $$0) {
/*  87 */     BlockElementRotation $$1 = null;
/*  88 */     if ($$0.has("rotation")) {
/*  89 */       JsonObject $$2 = GsonHelper.getAsJsonObject($$0, "rotation");
/*  90 */       Vector3f $$3 = getVector3f($$2, "origin");
/*  91 */       $$3.mul(0.0625F);
/*  92 */       Direction.Axis $$4 = getAxis($$2);
/*  93 */       float $$5 = getAngle($$2);
/*  94 */       boolean $$6 = GsonHelper.getAsBoolean($$2, "rescale", false);
/*     */       
/*  96 */       $$1 = new BlockElementRotation($$3, $$4, $$5, $$6);
/*     */     } 
/*  98 */     return $$1;
/*     */   }
/*     */   
/*     */   private float getAngle(JsonObject $$0) {
/* 102 */     float $$1 = GsonHelper.getAsFloat($$0, "angle");
/* 103 */     if ($$1 != 0.0F && Mth.abs($$1) != 22.5F && Mth.abs($$1) != 45.0F) {
/* 104 */       throw new JsonParseException("Invalid rotation " + $$1 + " found, only -45/-22.5/0/22.5/45 allowed");
/*     */     }
/* 106 */     return $$1;
/*     */   }
/*     */   
/*     */   private Direction.Axis getAxis(JsonObject $$0) {
/* 110 */     String $$1 = GsonHelper.getAsString($$0, "axis");
/* 111 */     Direction.Axis $$2 = Direction.Axis.byName($$1.toLowerCase(Locale.ROOT));
/* 112 */     if ($$2 == null) {
/* 113 */       throw new JsonParseException("Invalid rotation axis: " + $$1);
/*     */     }
/* 115 */     return $$2;
/*     */   }
/*     */   
/*     */   private Map<Direction, BlockElementFace> getFaces(JsonDeserializationContext $$0, JsonObject $$1) {
/* 119 */     Map<Direction, BlockElementFace> $$2 = filterNullFromFaces($$0, $$1);
/*     */     
/* 121 */     if ($$2.isEmpty()) {
/* 122 */       throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
/*     */     }
/*     */     
/* 125 */     return $$2;
/*     */   }
/*     */   
/*     */   private Map<Direction, BlockElementFace> filterNullFromFaces(JsonDeserializationContext $$0, JsonObject $$1) {
/* 129 */     Map<Direction, BlockElementFace> $$2 = Maps.newEnumMap(Direction.class);
/* 130 */     JsonObject $$3 = GsonHelper.getAsJsonObject($$1, "faces");
/* 131 */     for (Map.Entry<String, JsonElement> $$4 : (Iterable<Map.Entry<String, JsonElement>>)$$3.entrySet()) {
/* 132 */       Direction $$5 = getFacing($$4.getKey());
/* 133 */       $$2.put($$5, (BlockElementFace)$$0.deserialize($$4.getValue(), BlockElementFace.class));
/*     */     } 
/* 135 */     return $$2;
/*     */   }
/*     */   
/*     */   private Direction getFacing(String $$0) {
/* 139 */     Direction $$1 = Direction.byName($$0);
/* 140 */     if ($$1 == null) {
/* 141 */       throw new JsonParseException("Unknown facing: " + $$0);
/*     */     }
/* 143 */     return $$1;
/*     */   }
/*     */   
/*     */   private Vector3f getTo(JsonObject $$0) {
/* 147 */     Vector3f $$1 = getVector3f($$0, "to");
/* 148 */     if ($$1.x() < -16.0F || $$1.y() < -16.0F || $$1.z() < -16.0F || $$1
/* 149 */       .x() > 32.0F || $$1.y() > 32.0F || $$1.z() > 32.0F)
/*     */     {
/* 151 */       throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + $$1);
/*     */     }
/* 153 */     return $$1;
/*     */   }
/*     */   
/*     */   private Vector3f getFrom(JsonObject $$0) {
/* 157 */     Vector3f $$1 = getVector3f($$0, "from");
/* 158 */     if ($$1.x() < -16.0F || $$1.y() < -16.0F || $$1.z() < -16.0F || $$1
/* 159 */       .x() > 32.0F || $$1.y() > 32.0F || $$1.z() > 32.0F)
/*     */     {
/* 161 */       throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + $$1);
/*     */     }
/* 163 */     return $$1;
/*     */   }
/*     */   
/*     */   private Vector3f getVector3f(JsonObject $$0, String $$1) {
/* 167 */     JsonArray $$2 = GsonHelper.getAsJsonArray($$0, $$1);
/* 168 */     if ($$2.size() != 3) {
/* 169 */       throw new JsonParseException("Expected 3 " + $$1 + " values, found: " + $$2.size());
/*     */     }
/*     */     
/* 172 */     float[] $$3 = new float[3];
/* 173 */     for (int $$4 = 0; $$4 < $$3.length; $$4++) {
/* 174 */       $$3[$$4] = GsonHelper.convertToFloat($$2.get($$4), $$1 + "[" + $$1 + "]");
/*     */     }
/* 176 */     return new Vector3f($$3[0], $$3[1], $$3[2]);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\BlockElement$Deserializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */