/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ 
/*    */ public class BlockFaceUV
/*    */ {
/*    */   public float[] uvs;
/*    */   public final int rotation;
/*    */   
/*    */   public BlockFaceUV(@Nullable float[] $$0, int $$1) {
/* 19 */     this.uvs = $$0;
/* 20 */     this.rotation = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getU(int $$0) {
/* 25 */     if (this.uvs == null) {
/* 26 */       throw new NullPointerException("uvs");
/*    */     }
/* 28 */     int $$1 = getShiftedIndex($$0);
/* 29 */     return this.uvs[($$1 == 0 || $$1 == 1) ? 0 : 2];
/*    */   }
/*    */ 
/*    */   
/*    */   public float getV(int $$0) {
/* 34 */     if (this.uvs == null) {
/* 35 */       throw new NullPointerException("uvs");
/*    */     }
/* 37 */     int $$1 = getShiftedIndex($$0);
/* 38 */     return this.uvs[($$1 == 0 || $$1 == 3) ? 1 : 3];
/*    */   }
/*    */   
/*    */   private int getShiftedIndex(int $$0) {
/* 42 */     return ($$0 + this.rotation / 90) % 4;
/*    */   }
/*    */   
/*    */   public int getReverseIndex(int $$0) {
/* 46 */     return ($$0 + 4 - this.rotation / 90) % 4;
/*    */   }
/*    */   
/*    */   public void setMissingUv(float[] $$0) {
/* 50 */     if (this.uvs == null)
/* 51 */       this.uvs = $$0; 
/*    */   }
/*    */   
/*    */   protected static class Deserializer
/*    */     implements JsonDeserializer<BlockFaceUV>
/*    */   {
/*    */     private static final int DEFAULT_ROTATION = 0;
/*    */     
/*    */     public BlockFaceUV deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 60 */       JsonObject $$3 = $$0.getAsJsonObject();
/*    */       
/* 62 */       float[] $$4 = getUVs($$3);
/* 63 */       int $$5 = getRotation($$3);
/*    */       
/* 65 */       return new BlockFaceUV($$4, $$5);
/*    */     }
/*    */     
/*    */     protected int getRotation(JsonObject $$0) {
/* 69 */       int $$1 = GsonHelper.getAsInt($$0, "rotation", 0);
/*    */       
/* 71 */       if ($$1 < 0 || $$1 % 90 != 0 || $$1 / 90 > 3) {
/* 72 */         throw new JsonParseException("Invalid rotation " + $$1 + " found, only 0/90/180/270 allowed");
/*    */       }
/*    */       
/* 75 */       return $$1;
/*    */     }
/*    */     
/*    */     @Nullable
/*    */     private float[] getUVs(JsonObject $$0) {
/* 80 */       if (!$$0.has("uv")) {
/* 81 */         return null;
/*    */       }
/*    */       
/* 84 */       JsonArray $$1 = GsonHelper.getAsJsonArray($$0, "uv");
/* 85 */       if ($$1.size() != 4) {
/* 86 */         throw new JsonParseException("Expected 4 uv values, found: " + $$1.size());
/*    */       }
/*    */       
/* 89 */       float[] $$2 = new float[4];
/* 90 */       for (int $$3 = 0; $$3 < $$2.length; $$3++) {
/* 91 */         $$2[$$3] = GsonHelper.convertToFloat($$1.get($$3), "uv[" + $$3 + "]");
/*    */       }
/*    */       
/* 94 */       return $$2;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\BlockFaceUV.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */