/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class ClipBlockStateContext
/*    */ {
/*    */   private final Vec3 from;
/*    */   private final Vec3 to;
/*    */   private final Predicate<BlockState> block;
/*    */   
/*    */   public ClipBlockStateContext(Vec3 $$0, Vec3 $$1, Predicate<BlockState> $$2) {
/* 14 */     this.from = $$0;
/* 15 */     this.to = $$1;
/* 16 */     this.block = $$2;
/*    */   }
/*    */   
/*    */   public Vec3 getTo() {
/* 20 */     return this.to;
/*    */   }
/*    */   
/*    */   public Vec3 getFrom() {
/* 24 */     return this.from;
/*    */   }
/*    */   
/*    */   public Predicate<BlockState> isTargetBlock() {
/* 28 */     return this.block;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\ClipBlockStateContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */