/*    */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class EnderDragonPhaseManager
/*    */ {
/* 10 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final EnderDragon dragon;
/* 13 */   private final DragonPhaseInstance[] phases = new DragonPhaseInstance[EnderDragonPhase.getCount()];
/*    */   @Nullable
/*    */   private DragonPhaseInstance currentPhase;
/*    */   
/*    */   public EnderDragonPhaseManager(EnderDragon $$0) {
/* 18 */     this.dragon = $$0;
/*    */     
/* 20 */     setPhase(EnderDragonPhase.HOVERING);
/*    */   }
/*    */   
/*    */   public void setPhase(EnderDragonPhase<?> $$0) {
/* 24 */     if (this.currentPhase != null && $$0 == this.currentPhase.getPhase()) {
/*    */       return;
/*    */     }
/*    */     
/* 28 */     if (this.currentPhase != null) {
/* 29 */       this.currentPhase.end();
/*    */     }
/*    */     
/* 32 */     this.currentPhase = getPhase($$0);
/* 33 */     if (!(this.dragon.level()).isClientSide) {
/* 34 */       this.dragon.getEntityData().set(EnderDragon.DATA_PHASE, Integer.valueOf($$0.getId()));
/*    */     }
/* 36 */     LOGGER.debug("Dragon is now in phase {} on the {}", $$0, (this.dragon.level()).isClientSide ? "client" : "server");
/*    */     
/* 38 */     this.currentPhase.begin();
/*    */   }
/*    */   
/*    */   public DragonPhaseInstance getCurrentPhase() {
/* 42 */     return this.currentPhase;
/*    */   }
/*    */ 
/*    */   
/*    */   public <T extends DragonPhaseInstance> T getPhase(EnderDragonPhase<T> $$0) {
/* 47 */     int $$1 = $$0.getId();
/* 48 */     if (this.phases[$$1] == null) {
/* 49 */       this.phases[$$1] = $$0.createInstance(this.dragon);
/*    */     }
/* 51 */     return (T)this.phases[$$1];
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\EnderDragonPhaseManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */