/*    */ package net.minecraft.client.resources.metadata.texture;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ 
/*    */ public class TextureMetadataSectionSerializer
/*    */   implements MetadataSectionSerializer<TextureMetadataSection> {
/*    */   public TextureMetadataSection fromJson(JsonObject $$0) {
/* 10 */     boolean $$1 = GsonHelper.getAsBoolean($$0, "blur", false);
/* 11 */     boolean $$2 = GsonHelper.getAsBoolean($$0, "clamp", false);
/*    */     
/* 13 */     return new TextureMetadataSection($$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMetadataSectionName() {
/* 18 */     return "texture";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\metadata\texture\TextureMetadataSectionSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */