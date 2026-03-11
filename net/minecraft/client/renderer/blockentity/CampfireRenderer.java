/*    */ package net.minecraft.client.renderer.blockentity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.world.item.ItemDisplayContext;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.CampfireBlock;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.CampfireBlockEntity;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class CampfireRenderer implements BlockEntityRenderer<CampfireBlockEntity> {
/*    */   private static final float SIZE = 0.375F;
/*    */   
/*    */   public CampfireRenderer(BlockEntityRendererProvider.Context $$0) {
/* 20 */     this.itemRenderer = $$0.getItemRenderer();
/*    */   }
/*    */   private final ItemRenderer itemRenderer;
/*    */   
/*    */   public void render(CampfireBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/* 25 */     Direction $$6 = (Direction)$$0.getBlockState().getValue((Property)CampfireBlock.FACING);
/*    */     
/* 27 */     NonNullList<ItemStack> $$7 = $$0.getItems();
/* 28 */     int $$8 = (int)$$0.getBlockPos().asLong();
/* 29 */     for (int $$9 = 0; $$9 < $$7.size(); $$9++) {
/* 30 */       ItemStack $$10 = (ItemStack)$$7.get($$9);
/* 31 */       if ($$10 != ItemStack.EMPTY) {
/*    */ 
/*    */         
/* 34 */         $$2.pushPose();
/* 35 */         $$2.translate(0.5F, 0.44921875F, 0.5F);
/*    */         
/* 37 */         Direction $$11 = Direction.from2DDataValue(($$9 + $$6.get2DDataValue()) % 4);
/* 38 */         float $$12 = -$$11.toYRot();
/* 39 */         $$2.mulPose(Axis.YP.rotationDegrees($$12));
/* 40 */         $$2.mulPose(Axis.XP.rotationDegrees(90.0F));
/* 41 */         $$2.translate(-0.3125F, -0.3125F, 0.0F);
/* 42 */         $$2.scale(0.375F, 0.375F, 0.375F);
/*    */         
/* 44 */         this.itemRenderer.renderStatic($$10, ItemDisplayContext.FIXED, $$4, $$5, $$2, $$3, $$0.getLevel(), $$8 + $$9);
/* 45 */         $$2.popPose();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\CampfireRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */