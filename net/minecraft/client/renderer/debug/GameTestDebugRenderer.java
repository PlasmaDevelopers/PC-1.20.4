/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import java.util.Map;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.core.BlockPos;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GameTestDebugRenderer
/*    */   implements DebugRenderer.SimpleDebugRenderer
/*    */ {
/*    */   private static final float PADDING = 0.02F;
/* 16 */   private final Map<BlockPos, Marker> markers = Maps.newHashMap();
/*    */   
/*    */   private static class Marker {
/*    */     public int color;
/*    */     public String text;
/*    */     public long removeAtTime;
/*    */     
/*    */     public Marker(int $$0, String $$1, long $$2) {
/* 24 */       this.color = $$0;
/* 25 */       this.text = $$1;
/* 26 */       this.removeAtTime = $$2;
/*    */     }
/*    */     
/*    */     public float getR() {
/* 30 */       return (this.color >> 16 & 0xFF) / 255.0F;
/*    */     }
/*    */     
/*    */     public float getG() {
/* 34 */       return (this.color >> 8 & 0xFF) / 255.0F;
/*    */     }
/*    */     
/*    */     public float getB() {
/* 38 */       return (this.color & 0xFF) / 255.0F;
/*    */     }
/*    */     
/*    */     public float getA() {
/* 42 */       return (this.color >> 24 & 0xFF) / 255.0F;
/*    */     }
/*    */   }
/*    */   
/*    */   public void addMarker(BlockPos $$0, int $$1, String $$2, int $$3) {
/* 47 */     this.markers.put($$0, new Marker($$1, $$2, Util.getMillis() + $$3));
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 52 */     this.markers.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 57 */     long $$5 = Util.getMillis();
/* 58 */     this.markers.entrySet().removeIf($$1 -> ($$0 > ((Marker)$$1.getValue()).removeAtTime));
/* 59 */     this.markers.forEach(($$2, $$3) -> renderMarker($$0, $$1, $$2, $$3));
/*    */   }
/*    */   
/*    */   private void renderMarker(PoseStack $$0, MultiBufferSource $$1, BlockPos $$2, Marker $$3) {
/* 63 */     DebugRenderer.renderFilledBox($$0, $$1, $$2, 0.02F, $$3.getR(), $$3.getG(), $$3.getB(), $$3.getA() * 0.75F);
/* 64 */     if (!$$3.text.isEmpty()) {
/* 65 */       double $$4 = $$2.getX() + 0.5D;
/* 66 */       double $$5 = $$2.getY() + 1.2D;
/* 67 */       double $$6 = $$2.getZ() + 0.5D;
/* 68 */       DebugRenderer.renderFloatingText($$0, $$1, $$3.text, $$4, $$5, $$6, -1, 0.01F, true, 0.0F, true);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\GameTestDebugRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */