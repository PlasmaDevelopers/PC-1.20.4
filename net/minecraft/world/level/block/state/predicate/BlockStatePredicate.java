/*    */ package net.minecraft.world.level.block.state.predicate;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class BlockStatePredicate
/*    */   implements Predicate<BlockState>
/*    */ {
/*    */   public static final Predicate<BlockState> ANY = $$0 -> true;
/*    */   private final StateDefinition<Block, BlockState> definition;
/* 17 */   private final Map<Property<?>, Predicate<Object>> properties = Maps.newHashMap();
/*    */   
/*    */   private BlockStatePredicate(StateDefinition<Block, BlockState> $$0) {
/* 20 */     this.definition = $$0;
/*    */   }
/*    */   
/*    */   public static BlockStatePredicate forBlock(Block $$0) {
/* 24 */     return new BlockStatePredicate($$0.getStateDefinition());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(@Nullable BlockState $$0) {
/* 29 */     if ($$0 == null || !$$0.getBlock().equals(this.definition.getOwner())) {
/* 30 */       return false;
/*    */     }
/*    */     
/* 33 */     if (this.properties.isEmpty()) {
/* 34 */       return true;
/*    */     }
/*    */     
/* 37 */     for (Map.Entry<Property<?>, Predicate<Object>> $$1 : this.properties.entrySet()) {
/* 38 */       if (!applies($$0, (Property<Comparable>)$$1.getKey(), $$1.getValue())) {
/* 39 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 43 */     return true;
/*    */   }
/*    */   
/*    */   protected <T extends Comparable<T>> boolean applies(BlockState $$0, Property<T> $$1, Predicate<Object> $$2) {
/* 47 */     Comparable comparable = $$0.getValue($$1);
/* 48 */     return $$2.test(comparable);
/*    */   }
/*    */   
/*    */   public <V extends Comparable<V>> BlockStatePredicate where(Property<V> $$0, Predicate<Object> $$1) {
/* 52 */     if (!this.definition.getProperties().contains($$0)) {
/* 53 */       throw new IllegalArgumentException("" + this.definition + " cannot support property " + this.definition);
/*    */     }
/* 55 */     this.properties.put($$0, $$1);
/* 56 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\predicate\BlockStatePredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */