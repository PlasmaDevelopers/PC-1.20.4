/*    */ package net.minecraft.client.renderer.block.model.multipart;
/*    */ 
/*    */ import com.google.common.collect.Streams;
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ 
/*    */ public class OrCondition
/*    */   implements Condition
/*    */ {
/*    */   public static final String TOKEN = "OR";
/*    */   private final Iterable<? extends Condition> conditions;
/*    */   
/*    */   public OrCondition(Iterable<? extends Condition> $$0) {
/* 18 */     this.conditions = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Predicate<BlockState> getPredicate(StateDefinition<Block, BlockState> $$0) {
/* 23 */     List<Predicate<BlockState>> $$1 = (List<Predicate<BlockState>>)Streams.stream(this.conditions).map($$1 -> $$1.getPredicate($$0)).collect(Collectors.toList());
/*    */     
/* 25 */     return $$1 -> $$0.stream().anyMatch(());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\multipart\OrCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */