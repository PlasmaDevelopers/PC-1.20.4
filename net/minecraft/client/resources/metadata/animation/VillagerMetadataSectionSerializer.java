/*    */ package net.minecraft.client.resources.metadata.animation;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ import net.minecraft.util.GsonHelper;
/*    */ 
/*    */ public class VillagerMetadataSectionSerializer
/*    */   implements MetadataSectionSerializer<VillagerMetaDataSection> {
/*    */   public VillagerMetaDataSection fromJson(JsonObject $$0) {
/* 10 */     return new VillagerMetaDataSection(VillagerMetaDataSection.Hat.getByName(GsonHelper.getAsString($$0, "hat", "none")));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMetadataSectionName() {
/* 15 */     return "villager";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\metadata\animation\VillagerMetadataSectionSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */