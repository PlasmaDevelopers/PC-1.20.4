/*    */ package net.minecraft.client.renderer.texture;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class StitcherException extends RuntimeException {
/*    */   private final Collection<Stitcher.Entry> allSprites;
/*    */   
/*    */   public StitcherException(Stitcher.Entry $$0, Collection<Stitcher.Entry> $$1) {
/* 10 */     super(String.format(Locale.ROOT, "Unable to fit: %s - size: %dx%d - Maybe try a lower resolution resourcepack?", new Object[] { $$0
/*    */ 
/*    */             
/* 13 */             .name(), 
/* 14 */             Integer.valueOf($$0.width()), 
/* 15 */             Integer.valueOf($$0.height()) }));
/*    */ 
/*    */     
/* 18 */     this.allSprites = $$1;
/*    */   }
/*    */   
/*    */   public Collection<Stitcher.Entry> getAllSprites() {
/* 22 */     return this.allSprites;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\StitcherException.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */