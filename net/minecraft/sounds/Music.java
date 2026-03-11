/*    */ package net.minecraft.sounds;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ 
/*    */ public class Music {
/*    */   static {
/*  8 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)SoundEvent.CODEC.fieldOf("sound").forGetter(()), (App)Codec.INT.fieldOf("min_delay").forGetter(()), (App)Codec.INT.fieldOf("max_delay").forGetter(()), (App)Codec.BOOL.fieldOf("replace_current_music").forGetter(())).apply((Applicative)$$0, Music::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<Music> CODEC;
/*    */   
/*    */   private final Holder<SoundEvent> event;
/*    */   
/*    */   private final int minDelay;
/*    */   private final int maxDelay;
/*    */   private final boolean replaceCurrentMusic;
/*    */   
/*    */   public Music(Holder<SoundEvent> $$0, int $$1, int $$2, boolean $$3) {
/* 21 */     this.event = $$0;
/* 22 */     this.minDelay = $$1;
/* 23 */     this.maxDelay = $$2;
/* 24 */     this.replaceCurrentMusic = $$3;
/*    */   }
/*    */   
/*    */   public Holder<SoundEvent> getEvent() {
/* 28 */     return this.event;
/*    */   }
/*    */   
/*    */   public int getMinDelay() {
/* 32 */     return this.minDelay;
/*    */   }
/*    */   
/*    */   public int getMaxDelay() {
/* 36 */     return this.maxDelay;
/*    */   }
/*    */   
/*    */   public boolean replaceCurrentMusic() {
/* 40 */     return this.replaceCurrentMusic;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\sounds\Music.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */