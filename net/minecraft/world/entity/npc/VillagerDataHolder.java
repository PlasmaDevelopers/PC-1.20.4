/*    */ package net.minecraft.world.entity.npc;
/*    */ 
/*    */ import net.minecraft.world.entity.VariantHolder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface VillagerDataHolder
/*    */   extends VariantHolder<VillagerType>
/*    */ {
/*    */   default VillagerType getVariant() {
/* 12 */     return getVillagerData().getType();
/*    */   }
/*    */ 
/*    */   
/*    */   default void setVariant(VillagerType $$0) {
/* 17 */     setVillagerData(getVillagerData().setType($$0));
/*    */   }
/*    */   
/*    */   VillagerData getVillagerData();
/*    */   
/*    */   void setVillagerData(VillagerData paramVillagerData);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\npc\VillagerDataHolder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */