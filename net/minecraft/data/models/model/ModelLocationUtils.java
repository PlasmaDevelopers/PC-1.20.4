/*    */ package net.minecraft.data.models.model;
/*    */ 
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public class ModelLocationUtils
/*    */ {
/*    */   @Deprecated
/*    */   public static ResourceLocation decorateBlockModelLocation(String $$0) {
/* 12 */     return new ResourceLocation("minecraft", "block/" + $$0);
/*    */   }
/*    */   
/*    */   public static ResourceLocation decorateItemModelLocation(String $$0) {
/* 16 */     return new ResourceLocation("minecraft", "item/" + $$0);
/*    */   }
/*    */   
/*    */   public static ResourceLocation getModelLocation(Block $$0, String $$1) {
/* 20 */     ResourceLocation $$2 = BuiltInRegistries.BLOCK.getKey($$0);
/* 21 */     return $$2.withPath($$1 -> "block/" + $$1 + $$0);
/*    */   }
/*    */   
/*    */   public static ResourceLocation getModelLocation(Block $$0) {
/* 25 */     ResourceLocation $$1 = BuiltInRegistries.BLOCK.getKey($$0);
/* 26 */     return $$1.withPrefix("block/");
/*    */   }
/*    */   
/*    */   public static ResourceLocation getModelLocation(Item $$0) {
/* 30 */     ResourceLocation $$1 = BuiltInRegistries.ITEM.getKey($$0);
/* 31 */     return $$1.withPrefix("item/");
/*    */   }
/*    */   
/*    */   public static ResourceLocation getModelLocation(Item $$0, String $$1) {
/* 35 */     ResourceLocation $$2 = BuiltInRegistries.ITEM.getKey($$0);
/* 36 */     return $$2.withPath($$1 -> "item/" + $$1 + $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\model\ModelLocationUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */