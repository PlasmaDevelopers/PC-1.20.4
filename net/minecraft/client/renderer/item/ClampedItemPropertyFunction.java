/*    */ package net.minecraft.client.renderer.item;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public interface ClampedItemPropertyFunction
/*    */   extends ItemPropertyFunction
/*    */ {
/*    */   @Deprecated
/*    */   default float call(ItemStack $$0, @Nullable ClientLevel $$1, @Nullable LivingEntity $$2, int $$3) {
/* 14 */     return Mth.clamp(unclampedCall($$0, $$1, $$2, $$3), 0.0F, 1.0F);
/*    */   }
/*    */   
/*    */   float unclampedCall(ItemStack paramItemStack, @Nullable ClientLevel paramClientLevel, @Nullable LivingEntity paramLivingEntity, int paramInt);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\item\ClampedItemPropertyFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */