/*    */ package net.minecraft.client.renderer.block.model.multipart;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ 
/*    */ @FunctionalInterface
/*    */ public interface Condition {
/*    */   public static final Condition TRUE = $$0 -> ();
/*    */   public static final Condition FALSE = $$0 -> ();
/*    */   
/*    */   Predicate<BlockState> getPredicate(StateDefinition<Block, BlockState> paramStateDefinition);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\multipart\Condition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */