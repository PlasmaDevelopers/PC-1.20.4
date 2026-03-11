/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Fluid
/*    */ {
/* 80 */   NONE($$0 -> false),
/* 81 */   SOURCE_ONLY(FluidState::isSource), ANY(FluidState::isSource), WATER(FluidState::isSource); static {
/* 82 */     ANY = new Fluid("ANY", 2, $$0 -> !$$0.isEmpty());
/* 83 */     WATER = new Fluid("WATER", 3, $$0 -> $$0.is(FluidTags.WATER));
/*    */   }
/*    */   
/*    */   private final Predicate<FluidState> canPick;
/*    */   
/*    */   Fluid(Predicate<FluidState> $$0) {
/* 89 */     this.canPick = $$0;
/*    */   }
/*    */   
/*    */   public boolean canPick(FluidState $$0) {
/* 93 */     return this.canPick.test($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\ClipContext$Fluid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */