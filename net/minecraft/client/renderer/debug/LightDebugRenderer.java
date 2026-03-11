/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.multiplayer.ClientLevel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.level.LightLayer;
/*    */ 
/*    */ public class LightDebugRenderer
/*    */   implements DebugRenderer.SimpleDebugRenderer {
/*    */   private final Minecraft minecraft;
/*    */   private static final int MAX_RENDER_DIST = 10;
/*    */   
/*    */   public LightDebugRenderer(Minecraft $$0) {
/* 19 */     this.minecraft = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 24 */     ClientLevel clientLevel = this.minecraft.level;
/*    */     
/* 26 */     BlockPos $$6 = BlockPos.containing($$2, $$3, $$4);
/*    */     
/* 28 */     LongOpenHashSet longOpenHashSet = new LongOpenHashSet();
/*    */     
/* 30 */     for (BlockPos $$8 : BlockPos.betweenClosed($$6.offset(-10, -10, -10), $$6.offset(10, 10, 10))) {
/* 31 */       int $$9 = clientLevel.getBrightness(LightLayer.SKY, $$8);
/*    */       
/* 33 */       float $$10 = (15 - $$9) / 15.0F * 0.5F + 0.16F;
/* 34 */       int $$11 = Mth.hsvToRgb($$10, 0.9F, 0.9F);
/*    */       
/* 36 */       long $$12 = SectionPos.blockToSection($$8.asLong());
/* 37 */       if (longOpenHashSet.add($$12)) {
/* 38 */         DebugRenderer.renderFloatingText($$0, $$1, clientLevel.getChunkSource().getLightEngine().getDebugData(LightLayer.SKY, SectionPos.of($$12)), SectionPos.sectionToBlockCoord(SectionPos.x($$12), 8), SectionPos.sectionToBlockCoord(SectionPos.y($$12), 8), SectionPos.sectionToBlockCoord(SectionPos.z($$12), 8), 16711680, 0.3F);
/*    */       }
/* 40 */       if ($$9 != 15)
/* 41 */         DebugRenderer.renderFloatingText($$0, $$1, String.valueOf($$9), $$8.getX() + 0.5D, $$8.getY() + 0.25D, $$8.getZ() + 0.5D, $$11); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\LightDebugRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */