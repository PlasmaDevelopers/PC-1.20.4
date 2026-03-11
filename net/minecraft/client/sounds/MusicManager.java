/*    */ package net.minecraft.client.sounds;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*    */ import net.minecraft.client.resources.sounds.SoundInstance;
/*    */ import net.minecraft.sounds.Music;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ 
/*    */ public class MusicManager
/*    */ {
/*    */   private static final int STARTING_DELAY = 100;
/* 16 */   private final RandomSource random = RandomSource.create();
/*    */   private final Minecraft minecraft;
/*    */   @Nullable
/*    */   private SoundInstance currentMusic;
/* 20 */   private int nextSongDelay = 100;
/*    */   
/*    */   public MusicManager(Minecraft $$0) {
/* 23 */     this.minecraft = $$0;
/*    */   }
/*    */   
/*    */   public void tick() {
/* 27 */     Music $$0 = this.minecraft.getSituationalMusic();
/*    */     
/* 29 */     if (this.currentMusic != null) {
/* 30 */       if (!((SoundEvent)$$0.getEvent().value()).getLocation().equals(this.currentMusic.getLocation()) && $$0.replaceCurrentMusic()) {
/* 31 */         this.minecraft.getSoundManager().stop(this.currentMusic);
/* 32 */         this.nextSongDelay = Mth.nextInt(this.random, 0, $$0.getMinDelay() / 2);
/*    */       } 
/*    */       
/* 35 */       if (!this.minecraft.getSoundManager().isActive(this.currentMusic)) {
/* 36 */         this.currentMusic = null;
/* 37 */         this.nextSongDelay = Math.min(this.nextSongDelay, Mth.nextInt(this.random, $$0.getMinDelay(), $$0.getMaxDelay()));
/*    */       } 
/*    */     } 
/*    */     
/* 41 */     this.nextSongDelay = Math.min(this.nextSongDelay, $$0.getMaxDelay());
/*    */     
/* 43 */     if (this.currentMusic == null && this.nextSongDelay-- <= 0) {
/* 44 */       startPlaying($$0);
/*    */     }
/*    */   }
/*    */   
/*    */   public void startPlaying(Music $$0) {
/* 49 */     this.currentMusic = (SoundInstance)SimpleSoundInstance.forMusic((SoundEvent)$$0.getEvent().value());
/* 50 */     if (this.currentMusic.getSound() != SoundManager.EMPTY_SOUND) {
/* 51 */       this.minecraft.getSoundManager().play(this.currentMusic);
/*    */     }
/* 53 */     this.nextSongDelay = Integer.MAX_VALUE;
/*    */   }
/*    */   
/*    */   public void stopPlaying(Music $$0) {
/* 57 */     if (isPlayingMusic($$0)) {
/* 58 */       stopPlaying();
/*    */     }
/*    */   }
/*    */   
/*    */   public void stopPlaying() {
/* 63 */     if (this.currentMusic != null) {
/* 64 */       this.minecraft.getSoundManager().stop(this.currentMusic);
/* 65 */       this.currentMusic = null;
/*    */     } 
/* 67 */     this.nextSongDelay += 100;
/*    */   }
/*    */   
/*    */   public boolean isPlayingMusic(Music $$0) {
/* 71 */     if (this.currentMusic == null) {
/* 72 */       return false;
/*    */     }
/*    */     
/* 75 */     return ((SoundEvent)$$0.getEvent().value()).getLocation().equals(this.currentMusic.getLocation());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\MusicManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */