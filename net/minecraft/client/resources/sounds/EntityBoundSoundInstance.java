/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class EntityBoundSoundInstance extends AbstractTickableSoundInstance {
/*    */   private final Entity entity;
/*    */   
/*    */   public EntityBoundSoundInstance(SoundEvent $$0, SoundSource $$1, float $$2, float $$3, Entity $$4, long $$5) {
/* 12 */     super($$0, $$1, RandomSource.create($$5));
/* 13 */     this.volume = $$2;
/* 14 */     this.pitch = $$3;
/* 15 */     this.entity = $$4;
/*    */     
/* 17 */     this.x = (float)this.entity.getX();
/* 18 */     this.y = (float)this.entity.getY();
/* 19 */     this.z = (float)this.entity.getZ();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlaySound() {
/* 24 */     return !this.entity.isSilent();
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 29 */     if (this.entity.isRemoved()) {
/* 30 */       stop();
/*    */       
/*    */       return;
/*    */     } 
/* 34 */     this.x = (float)this.entity.getX();
/* 35 */     this.y = (float)this.entity.getY();
/* 36 */     this.z = (float)this.entity.getZ();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\EntityBoundSoundInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */