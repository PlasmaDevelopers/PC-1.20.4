/*    */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*    */ 
/*    */ public class EnderDragonPhase<T extends DragonPhaseInstance>
/*    */ {
/*  9 */   private static EnderDragonPhase<?>[] phases = (EnderDragonPhase<?>[])new EnderDragonPhase[0];
/* 10 */   public static final EnderDragonPhase<DragonHoldingPatternPhase> HOLDING_PATTERN = create(DragonHoldingPatternPhase.class, "HoldingPattern");
/* 11 */   public static final EnderDragonPhase<DragonStrafePlayerPhase> STRAFE_PLAYER = create(DragonStrafePlayerPhase.class, "StrafePlayer");
/* 12 */   public static final EnderDragonPhase<DragonLandingApproachPhase> LANDING_APPROACH = create(DragonLandingApproachPhase.class, "LandingApproach");
/* 13 */   public static final EnderDragonPhase<DragonLandingPhase> LANDING = create(DragonLandingPhase.class, "Landing");
/* 14 */   public static final EnderDragonPhase<DragonTakeoffPhase> TAKEOFF = create(DragonTakeoffPhase.class, "Takeoff");
/* 15 */   public static final EnderDragonPhase<DragonSittingFlamingPhase> SITTING_FLAMING = create(DragonSittingFlamingPhase.class, "SittingFlaming");
/* 16 */   public static final EnderDragonPhase<DragonSittingScanningPhase> SITTING_SCANNING = create(DragonSittingScanningPhase.class, "SittingScanning");
/* 17 */   public static final EnderDragonPhase<DragonSittingAttackingPhase> SITTING_ATTACKING = create(DragonSittingAttackingPhase.class, "SittingAttacking");
/* 18 */   public static final EnderDragonPhase<DragonChargePlayerPhase> CHARGING_PLAYER = create(DragonChargePlayerPhase.class, "ChargingPlayer");
/* 19 */   public static final EnderDragonPhase<DragonDeathPhase> DYING = create(DragonDeathPhase.class, "Dying");
/* 20 */   public static final EnderDragonPhase<DragonHoverPhase> HOVERING = create(DragonHoverPhase.class, "Hover");
/*    */   
/*    */   private final Class<? extends DragonPhaseInstance> instanceClass;
/*    */   private final int id;
/*    */   private final String name;
/*    */   
/*    */   private EnderDragonPhase(int $$0, Class<? extends DragonPhaseInstance> $$1, String $$2) {
/* 27 */     this.id = $$0;
/* 28 */     this.instanceClass = $$1;
/* 29 */     this.name = $$2;
/*    */   }
/*    */   
/*    */   public DragonPhaseInstance createInstance(EnderDragon $$0) {
/*    */     try {
/* 34 */       Constructor<? extends DragonPhaseInstance> $$1 = getConstructor();
/* 35 */       return $$1.newInstance(new Object[] { $$0 });
/* 36 */     } catch (Exception $$2) {
/* 37 */       throw new Error($$2);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected Constructor<? extends DragonPhaseInstance> getConstructor() throws NoSuchMethodException {
/* 42 */     return this.instanceClass.getConstructor(new Class[] { EnderDragon.class });
/*    */   }
/*    */   
/*    */   public int getId() {
/* 46 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 51 */     return this.name + " (#" + this.name + ")";
/*    */   }
/*    */   
/*    */   public static EnderDragonPhase<?> getById(int $$0) {
/* 55 */     if ($$0 < 0 || $$0 >= phases.length) {
/* 56 */       return HOLDING_PATTERN;
/*    */     }
/* 58 */     return phases[$$0];
/*    */   }
/*    */   
/*    */   public static int getCount() {
/* 62 */     return phases.length;
/*    */   }
/*    */   
/*    */   private static <T extends DragonPhaseInstance> EnderDragonPhase<T> create(Class<T> $$0, String $$1) {
/* 66 */     EnderDragonPhase<T> $$2 = new EnderDragonPhase<>(phases.length, $$0, $$1);
/* 67 */     phases = (EnderDragonPhase<?>[])Arrays.<EnderDragonPhase>copyOf((EnderDragonPhase[])phases, phases.length + 1);
/* 68 */     phases[$$2.getId()] = $$2;
/* 69 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\EnderDragonPhase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */