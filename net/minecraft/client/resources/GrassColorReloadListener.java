/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
/*    */ import net.minecraft.util.profiling.ProfilerFiller;
/*    */ import net.minecraft.world.level.GrassColor;
/*    */ 
/*    */ public class GrassColorReloadListener
/*    */   extends SimplePreparableReloadListener<int[]> {
/* 12 */   private static final ResourceLocation LOCATION = new ResourceLocation("textures/colormap/grass.png");
/*    */ 
/*    */   
/*    */   protected int[] prepare(ResourceManager $$0, ProfilerFiller $$1) {
/*    */     try {
/* 17 */       return LegacyStuffWrapper.getPixels($$0, LOCATION);
/* 18 */     } catch (IOException $$2) {
/* 19 */       throw new IllegalStateException("Failed to load grass color texture", $$2);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void apply(int[] $$0, ResourceManager $$1, ProfilerFiller $$2) {
/* 25 */     GrassColor.init($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\GrassColorReloadListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */