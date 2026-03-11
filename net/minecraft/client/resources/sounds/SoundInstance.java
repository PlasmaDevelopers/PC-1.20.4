/*    */ package net.minecraft.client.resources.sounds;public interface SoundInstance { ResourceLocation getLocation(); @Nullable
/*    */   WeighedSoundEvents resolve(SoundManager paramSoundManager); Sound getSound();
/*    */   SoundSource getSource();
/*    */   boolean isLooping();
/*    */   boolean isRelative();
/*    */   int getDelay();
/*    */   float getVolume();
/*    */   float getPitch();
/*    */   double getX();
/*    */   double getY();
/*    */   double getZ();
/*    */   Attenuation getAttenuation();
/* 13 */   public enum Attenuation { NONE,
/* 14 */     LINEAR; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default boolean canStartSilent() {
/* 48 */     return false;
/*    */   }
/*    */   
/*    */   default boolean canPlaySound() {
/* 52 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static RandomSource createUnseededRandom() {
/* 60 */     return RandomSource.create();
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\SoundInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */