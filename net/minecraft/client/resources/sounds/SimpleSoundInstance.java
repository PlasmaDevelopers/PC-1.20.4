/*    */ package net.minecraft.client.resources.sounds;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class SimpleSoundInstance extends AbstractSoundInstance {
/*    */   public SimpleSoundInstance(SoundEvent $$0, SoundSource $$1, float $$2, float $$3, RandomSource $$4, BlockPos $$5) {
/* 13 */     this($$0, $$1, $$2, $$3, $$4, $$5.getX() + 0.5D, $$5.getY() + 0.5D, $$5.getZ() + 0.5D);
/*    */   }
/*    */   
/*    */   public static SimpleSoundInstance forUI(SoundEvent $$0, float $$1) {
/* 17 */     return forUI($$0, $$1, 0.25F);
/*    */   }
/*    */   
/*    */   public static SimpleSoundInstance forUI(Holder<SoundEvent> $$0, float $$1) {
/* 21 */     return forUI((SoundEvent)$$0.value(), $$1);
/*    */   }
/*    */   
/*    */   public static SimpleSoundInstance forUI(SoundEvent $$0, float $$1, float $$2) {
/* 25 */     return new SimpleSoundInstance($$0.getLocation(), SoundSource.MASTER, $$2, $$1, SoundInstance.createUnseededRandom(), false, 0, SoundInstance.Attenuation.NONE, 0.0D, 0.0D, 0.0D, true);
/*    */   }
/*    */   
/*    */   public static SimpleSoundInstance forMusic(SoundEvent $$0) {
/* 29 */     return new SimpleSoundInstance($$0.getLocation(), SoundSource.MUSIC, 1.0F, 1.0F, SoundInstance.createUnseededRandom(), false, 0, SoundInstance.Attenuation.NONE, 0.0D, 0.0D, 0.0D, true);
/*    */   }
/*    */   
/*    */   public static SimpleSoundInstance forRecord(SoundEvent $$0, Vec3 $$1) {
/* 33 */     return new SimpleSoundInstance($$0, SoundSource.RECORDS, 4.0F, 1.0F, SoundInstance.createUnseededRandom(), false, 0, SoundInstance.Attenuation.LINEAR, $$1.x, $$1.y, $$1.z);
/*    */   }
/*    */   
/*    */   public static SimpleSoundInstance forLocalAmbience(SoundEvent $$0, float $$1, float $$2) {
/* 37 */     return new SimpleSoundInstance($$0.getLocation(), SoundSource.AMBIENT, $$2, $$1, SoundInstance.createUnseededRandom(), false, 0, SoundInstance.Attenuation.NONE, 0.0D, 0.0D, 0.0D, true);
/*    */   }
/*    */   
/*    */   public static SimpleSoundInstance forAmbientAddition(SoundEvent $$0) {
/* 41 */     return forLocalAmbience($$0, 1.0F, 1.0F);
/*    */   }
/*    */   
/*    */   public static SimpleSoundInstance forAmbientMood(SoundEvent $$0, RandomSource $$1, double $$2, double $$3, double $$4) {
/* 45 */     return new SimpleSoundInstance($$0, SoundSource.AMBIENT, 1.0F, 1.0F, $$1, false, 0, SoundInstance.Attenuation.LINEAR, $$2, $$3, $$4);
/*    */   }
/*    */   
/*    */   public SimpleSoundInstance(SoundEvent $$0, SoundSource $$1, float $$2, float $$3, RandomSource $$4, double $$5, double $$6, double $$7) {
/* 49 */     this($$0, $$1, $$2, $$3, $$4, false, 0, SoundInstance.Attenuation.LINEAR, $$5, $$6, $$7);
/*    */   }
/*    */   
/*    */   private SimpleSoundInstance(SoundEvent $$0, SoundSource $$1, float $$2, float $$3, RandomSource $$4, boolean $$5, int $$6, SoundInstance.Attenuation $$7, double $$8, double $$9, double $$10) {
/* 53 */     this($$0.getLocation(), $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, false);
/*    */   }
/*    */   
/*    */   public SimpleSoundInstance(ResourceLocation $$0, SoundSource $$1, float $$2, float $$3, RandomSource $$4, boolean $$5, int $$6, SoundInstance.Attenuation $$7, double $$8, double $$9, double $$10, boolean $$11) {
/* 57 */     super($$0, $$1, $$4);
/* 58 */     this.volume = $$2;
/* 59 */     this.pitch = $$3;
/* 60 */     this.x = $$8;
/* 61 */     this.y = $$9;
/* 62 */     this.z = $$10;
/* 63 */     this.looping = $$5;
/* 64 */     this.delay = $$6;
/* 65 */     this.attenuation = $$7;
/* 66 */     this.relative = $$11;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\SimpleSoundInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */