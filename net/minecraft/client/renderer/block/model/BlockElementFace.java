/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import java.lang.reflect.Type;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ 
/*    */ 
/*    */ public class BlockElementFace
/*    */ {
/*    */   public static final int NO_TINT = -1;
/*    */   public final Direction cullForDirection;
/*    */   public final int tintIndex;
/*    */   public final String texture;
/*    */   public final BlockFaceUV uv;
/*    */   
/*    */   public BlockElementFace(@Nullable Direction $$0, int $$1, String $$2, BlockFaceUV $$3) {
/* 23 */     this.cullForDirection = $$0;
/* 24 */     this.tintIndex = $$1;
/* 25 */     this.texture = $$2;
/* 26 */     this.uv = $$3;
/*    */   }
/*    */   
/*    */   protected static class Deserializer
/*    */     implements JsonDeserializer<BlockElementFace> {
/*    */     private static final int DEFAULT_TINT_INDEX = -1;
/*    */     
/*    */     public BlockElementFace deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
/* 34 */       JsonObject $$3 = $$0.getAsJsonObject();
/*    */       
/* 36 */       Direction $$4 = getCullFacing($$3);
/* 37 */       int $$5 = getTintIndex($$3);
/* 38 */       String $$6 = getTexture($$3);
/* 39 */       BlockFaceUV $$7 = (BlockFaceUV)$$2.deserialize((JsonElement)$$3, BlockFaceUV.class);
/*    */       
/* 41 */       return new BlockElementFace($$4, $$5, $$6, $$7);
/*    */     }
/*    */     
/*    */     protected int getTintIndex(JsonObject $$0) {
/* 45 */       return GsonHelper.getAsInt($$0, "tintindex", -1);
/*    */     }
/*    */     
/*    */     private String getTexture(JsonObject $$0) {
/* 49 */       return GsonHelper.getAsString($$0, "texture");
/*    */     }
/*    */     
/*    */     @Nullable
/*    */     private Direction getCullFacing(JsonObject $$0) {
/* 54 */       String $$1 = GsonHelper.getAsString($$0, "cullface", "");
/* 55 */       return Direction.byName($$1);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\BlockElementFace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */