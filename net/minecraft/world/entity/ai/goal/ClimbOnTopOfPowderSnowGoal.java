/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.tags.EntityTypeTags;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.Shapes;
/*    */ 
/*    */ public class ClimbOnTopOfPowderSnowGoal extends Goal {
/*    */   private final Mob mob;
/*    */   private final Level level;
/*    */   
/*    */   public ClimbOnTopOfPowderSnowGoal(Mob $$0, Level $$1) {
/* 18 */     this.mob = $$0;
/* 19 */     this.level = $$1;
/* 20 */     setFlags(EnumSet.of(Goal.Flag.JUMP));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 25 */     boolean $$0 = (this.mob.wasInPowderSnow || this.mob.isInPowderSnow);
/* 26 */     if (!$$0 || !this.mob.getType().is(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS)) {
/* 27 */       return false;
/*    */     }
/* 29 */     BlockPos $$1 = this.mob.blockPosition().above();
/* 30 */     BlockState $$2 = this.level.getBlockState($$1);
/* 31 */     return ($$2.is(Blocks.POWDER_SNOW) || $$2.getCollisionShape((BlockGetter)this.level, $$1) == Shapes.empty());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean requiresUpdateEveryTick() {
/* 36 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 41 */     this.mob.getJumpControl().jump();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\ClimbOnTopOfPowderSnowGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */