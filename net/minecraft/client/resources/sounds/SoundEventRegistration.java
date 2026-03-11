/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class SoundEventRegistration {
/*    */   private final List<Sound> sounds;
/*    */   private final boolean replace;
/*    */   @Nullable
/*    */   private final String subtitle;
/*    */   
/*    */   public SoundEventRegistration(List<Sound> $$0, boolean $$1, @Nullable String $$2) {
/* 13 */     this.sounds = $$0;
/* 14 */     this.replace = $$1;
/* 15 */     this.subtitle = $$2;
/*    */   }
/*    */   
/*    */   public List<Sound> getSounds() {
/* 19 */     return this.sounds;
/*    */   }
/*    */   
/*    */   public boolean isReplace() {
/* 23 */     return this.replace;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getSubtitle() {
/* 28 */     return this.subtitle;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\SoundEventRegistration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */