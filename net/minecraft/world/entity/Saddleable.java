/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ 
/*    */ public interface Saddleable
/*    */ {
/*    */   boolean isSaddleable();
/*    */   
/*    */   void equipSaddle(@Nullable SoundSource paramSoundSource);
/*    */   
/*    */   default SoundEvent getSaddleSoundEvent() {
/* 15 */     return SoundEvents.HORSE_SADDLE;
/*    */   }
/*    */   
/*    */   boolean isSaddled();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\Saddleable.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */