/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ 
/*    */ public class InteractGoal
/*    */   extends LookAtPlayerGoal {
/*    */   public InteractGoal(Mob $$0, Class<? extends LivingEntity> $$1, float $$2) {
/* 10 */     super($$0, $$1, $$2);
/* 11 */     setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
/*    */   }
/*    */   
/*    */   public InteractGoal(Mob $$0, Class<? extends LivingEntity> $$1, float $$2, float $$3) {
/* 15 */     super($$0, $$1, $$2, $$3);
/* 16 */     setFlags(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.MOVE));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\InteractGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */