/*    */ package net.minecraft.client.resources.metadata.animation;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class AnimationMetadataSectionSerializer
/*    */   implements MetadataSectionSerializer<AnimationMetadataSection> {
/*    */   public AnimationMetadataSection fromJson(JsonObject $$0) {
/* 17 */     ImmutableList.Builder<AnimationFrame> $$1 = ImmutableList.builder();
/* 18 */     int $$2 = GsonHelper.getAsInt($$0, "frametime", 1);
/* 19 */     if ($$2 != 1) {
/* 20 */       Validate.inclusiveBetween(1L, 2147483647L, $$2, "Invalid default frame time");
/*    */     }
/*    */     
/* 23 */     if ($$0.has("frames")) {
/*    */       try {
/* 25 */         JsonArray $$3 = GsonHelper.getAsJsonArray($$0, "frames");
/*    */         
/* 27 */         for (int $$4 = 0; $$4 < $$3.size(); $$4++) {
/* 28 */           JsonElement $$5 = $$3.get($$4);
/*    */           
/* 30 */           AnimationFrame $$6 = getFrame($$4, $$5);
/* 31 */           if ($$6 != null) {
/* 32 */             $$1.add($$6);
/*    */           }
/*    */         } 
/* 35 */       } catch (ClassCastException $$7) {
/* 36 */         throw new JsonParseException("Invalid animation->frames: expected array, was " + $$0.get("frames"), $$7);
/*    */       } 
/*    */     }
/*    */     
/* 40 */     int $$8 = GsonHelper.getAsInt($$0, "width", -1);
/* 41 */     int $$9 = GsonHelper.getAsInt($$0, "height", -1);
/*    */     
/* 43 */     if ($$8 != -1) {
/* 44 */       Validate.inclusiveBetween(1L, 2147483647L, $$8, "Invalid width");
/*    */     }
/* 46 */     if ($$9 != -1) {
/* 47 */       Validate.inclusiveBetween(1L, 2147483647L, $$9, "Invalid height");
/*    */     }
/*    */     
/* 50 */     boolean $$10 = GsonHelper.getAsBoolean($$0, "interpolate", false);
/*    */     
/* 52 */     return new AnimationMetadataSection((List<AnimationFrame>)$$1.build(), $$8, $$9, $$2, $$10);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private AnimationFrame getFrame(int $$0, JsonElement $$1) {
/* 57 */     if ($$1.isJsonPrimitive())
/* 58 */       return new AnimationFrame(GsonHelper.convertToInt($$1, "frames[" + $$0 + "]")); 
/* 59 */     if ($$1.isJsonObject()) {
/* 60 */       JsonObject $$2 = GsonHelper.convertToJsonObject($$1, "frames[" + $$0 + "]");
/* 61 */       int $$3 = GsonHelper.getAsInt($$2, "time", -1);
/* 62 */       if ($$2.has("time")) {
/* 63 */         Validate.inclusiveBetween(1L, 2147483647L, $$3, "Invalid frame time");
/*    */       }
/* 65 */       int $$4 = GsonHelper.getAsInt($$2, "index");
/* 66 */       Validate.inclusiveBetween(0L, 2147483647L, $$4, "Invalid frame index");
/* 67 */       return new AnimationFrame($$4, $$3);
/*    */     } 
/*    */     
/* 70 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMetadataSectionName() {
/* 76 */     return "animation";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\metadata\animation\AnimationMetadataSectionSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */