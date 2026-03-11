/*     */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.AreaEffectCloud;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class DragonSittingFlamingPhase extends AbstractDragonSittingPhase {
/*     */   private static final int FLAME_DURATION = 200;
/*     */   private static final int SITTING_FLAME_ATTACKS_COUNT = 4;
/*     */   private static final int WARMUP_TIME = 10;
/*     */   private int flameTicks;
/*     */   private int flameCount;
/*     */   @Nullable
/*     */   private AreaEffectCloud flame;
/*     */   
/*     */   public DragonSittingFlamingPhase(EnderDragon $$0) {
/*  26 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void doClientTick() {
/*  31 */     this.flameTicks++;
/*     */     
/*  33 */     if (this.flameTicks % 2 == 0 && this.flameTicks < 10) {
/*  34 */       Vec3 $$0 = this.dragon.getHeadLookVector(1.0F).normalize();
/*  35 */       $$0.yRot(-0.7853982F);
/*  36 */       double $$1 = this.dragon.head.getX();
/*  37 */       double $$2 = this.dragon.head.getY(0.5D);
/*  38 */       double $$3 = this.dragon.head.getZ();
/*  39 */       for (int $$4 = 0; $$4 < 8; $$4++) {
/*  40 */         double $$5 = $$1 + this.dragon.getRandom().nextGaussian() / 2.0D;
/*  41 */         double $$6 = $$2 + this.dragon.getRandom().nextGaussian() / 2.0D;
/*  42 */         double $$7 = $$3 + this.dragon.getRandom().nextGaussian() / 2.0D;
/*  43 */         for (int $$8 = 0; $$8 < 6; $$8++) {
/*  44 */           this.dragon.level().addParticle((ParticleOptions)ParticleTypes.DRAGON_BREATH, $$5, $$6, $$7, -$$0.x * 0.07999999821186066D * $$8, -$$0.y * 0.6000000238418579D, -$$0.z * 0.07999999821186066D * $$8);
/*     */         }
/*  46 */         $$0.yRot(0.19634955F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void doServerTick() {
/*  53 */     this.flameTicks++;
/*     */     
/*  55 */     if (this.flameTicks >= 200) {
/*  56 */       if (this.flameCount >= 4) {
/*  57 */         this.dragon.getPhaseManager().setPhase(EnderDragonPhase.TAKEOFF);
/*     */       } else {
/*  59 */         this.dragon.getPhaseManager().setPhase(EnderDragonPhase.SITTING_SCANNING);
/*     */       } 
/*  61 */     } else if (this.flameTicks == 10) {
/*  62 */       Vec3 $$0 = (new Vec3(this.dragon.head.getX() - this.dragon.getX(), 0.0D, this.dragon.head.getZ() - this.dragon.getZ())).normalize();
/*  63 */       float $$1 = 5.0F;
/*  64 */       double $$2 = this.dragon.head.getX() + $$0.x * 5.0D / 2.0D;
/*  65 */       double $$3 = this.dragon.head.getZ() + $$0.z * 5.0D / 2.0D;
/*  66 */       double $$4 = this.dragon.head.getY(0.5D);
/*  67 */       double $$5 = $$4;
/*     */       
/*  69 */       BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos($$2, $$5, $$3);
/*  70 */       while (this.dragon.level().isEmptyBlock((BlockPos)$$6)) {
/*  71 */         $$5--;
/*  72 */         if ($$5 < 0.0D) {
/*  73 */           $$5 = $$4;
/*     */           break;
/*     */         } 
/*  76 */         $$6.set($$2, $$5, $$3);
/*     */       } 
/*  78 */       $$5 = (Mth.floor($$5) + 1);
/*  79 */       this.flame = new AreaEffectCloud(this.dragon.level(), $$2, $$5, $$3);
/*  80 */       this.flame.setOwner((LivingEntity)this.dragon);
/*  81 */       this.flame.setRadius(5.0F);
/*  82 */       this.flame.setDuration(200);
/*  83 */       this.flame.setParticle((ParticleOptions)ParticleTypes.DRAGON_BREATH);
/*  84 */       this.flame.addEffect(new MobEffectInstance(MobEffects.HARM));
/*  85 */       this.dragon.level().addFreshEntity((Entity)this.flame);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void begin() {
/*  91 */     this.flameTicks = 0;
/*  92 */     this.flameCount++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/*  97 */     if (this.flame != null) {
/*  98 */       this.flame.discard();
/*  99 */       this.flame = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnderDragonPhase<DragonSittingFlamingPhase> getPhase() {
/* 105 */     return EnderDragonPhase.SITTING_FLAMING;
/*     */   }
/*     */   
/*     */   public void resetFlameCount() {
/* 109 */     this.flameCount = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\DragonSittingFlamingPhase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */