/*    */ package net.minecraft.world.level.block.state.predicate;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class BlockPredicate
/*    */   implements Predicate<BlockState> {
/*    */   private final Block block;
/*    */   
/*    */   public BlockPredicate(Block $$0) {
/* 13 */     this.block = $$0;
/*    */   }
/*    */   
/*    */   public static BlockPredicate forBlock(Block $$0) {
/* 17 */     return new BlockPredicate($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(@Nullable BlockState $$0) {
/* 22 */     return ($$0 != null && $$0.is(this.block));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\predicate\BlockPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */