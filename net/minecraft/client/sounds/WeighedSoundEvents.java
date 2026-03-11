/*    */ package net.minecraft.client.sounds;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.resources.sounds.Sound;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WeighedSoundEvents
/*    */   implements Weighted<Sound>
/*    */ {
/* 16 */   private final List<Weighted<Sound>> list = Lists.newArrayList();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private final Component subtitle;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WeighedSoundEvents(ResourceLocation $$0, @Nullable String $$1) {
/* 28 */     this.subtitle = ($$1 == null) ? null : (Component)Component.translatable($$1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getWeight() {
/* 34 */     int $$0 = 0;
/* 35 */     for (Weighted<Sound> $$1 : this.list) {
/* 36 */       $$0 += $$1.getWeight();
/*    */     }
/* 38 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Sound getSound(RandomSource $$0) {
/* 43 */     int $$1 = getWeight();
/*    */     
/* 45 */     if (this.list.isEmpty() || $$1 == 0) {
/* 46 */       return SoundManager.EMPTY_SOUND;
/*    */     }
/*    */     
/* 49 */     int $$2 = $$0.nextInt($$1);
/* 50 */     for (Weighted<Sound> $$3 : this.list) {
/* 51 */       $$2 -= $$3.getWeight();
/*    */       
/* 53 */       if ($$2 < 0) {
/* 54 */         return $$3.getSound($$0);
/*    */       }
/*    */     } 
/*    */     
/* 58 */     return SoundManager.EMPTY_SOUND;
/*    */   }
/*    */   
/*    */   public void addSound(Weighted<Sound> $$0) {
/* 62 */     this.list.add($$0);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Component getSubtitle() {
/* 67 */     return this.subtitle;
/*    */   }
/*    */ 
/*    */   
/*    */   public void preloadIfRequired(SoundEngine $$0) {
/* 72 */     for (Weighted<Sound> $$1 : this.list)
/* 73 */       $$1.preloadIfRequired($$0); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\WeighedSoundEvents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */