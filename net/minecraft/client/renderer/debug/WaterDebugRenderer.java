/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ public class WaterDebugRenderer
/*    */   implements DebugRenderer.SimpleDebugRenderer {
/*    */   public WaterDebugRenderer(Minecraft $$0) {
/* 16 */     this.minecraft = $$0;
/*    */   }
/*    */   private final Minecraft minecraft;
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 21 */     BlockPos $$5 = this.minecraft.player.blockPosition();
/* 22 */     Level level = this.minecraft.player.level();
/*    */     
/* 24 */     for (BlockPos $$7 : BlockPos.betweenClosed($$5.offset(-10, -10, -10), $$5.offset(10, 10, 10))) {
/* 25 */       FluidState $$8 = level.getFluidState($$7);
/* 26 */       if ($$8.is(FluidTags.WATER)) {
/* 27 */         double $$9 = ($$7.getY() + $$8.getHeight((BlockGetter)level, $$7));
/*    */         
/* 29 */         DebugRenderer.renderFilledBox($$0, $$1, (new AABB(($$7
/* 30 */               .getX() + 0.01F), ($$7
/* 31 */               .getY() + 0.01F), ($$7
/* 32 */               .getZ() + 0.01F), ($$7
/* 33 */               .getX() + 0.99F), $$9, ($$7
/*    */               
/* 35 */               .getZ() + 0.99F)))
/* 36 */             .move(-$$2, -$$3, -$$4), 0.0F, 1.0F, 0.0F, 0.15F);
/*    */       } 
/*    */     } 
/*    */     
/* 40 */     for (BlockPos $$10 : BlockPos.betweenClosed($$5.offset(-10, -10, -10), $$5.offset(10, 10, 10))) {
/* 41 */       FluidState $$11 = level.getFluidState($$10);
/* 42 */       if ($$11.is(FluidTags.WATER))
/* 43 */         DebugRenderer.renderFloatingText($$0, $$1, 
/*    */ 
/*    */             
/* 46 */             String.valueOf($$11.getAmount()), $$10
/* 47 */             .getX() + 0.5D, ($$10
/* 48 */             .getY() + $$11.getHeight((BlockGetter)level, $$10)), $$10
/* 49 */             .getZ() + 0.5D, -16777216); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\WaterDebugRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */