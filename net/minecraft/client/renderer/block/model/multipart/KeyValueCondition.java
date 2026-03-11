/*    */ package net.minecraft.client.renderer.block.model.multipart;
/*    */ 
/*    */ import com.google.common.base.MoreObjects;
/*    */ import com.google.common.base.Splitter;
/*    */ import java.util.List;
/*    */ import java.util.Locale;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class KeyValueCondition
/*    */   implements Condition {
/* 17 */   private static final Splitter PIPE_SPLITTER = Splitter.on('|').omitEmptyStrings();
/*    */   
/*    */   private final String key;
/*    */   private final String value;
/*    */   
/*    */   public KeyValueCondition(String $$0, String $$1) {
/* 23 */     this.key = $$0;
/* 24 */     this.value = $$1;
/*    */   }
/*    */   
/*    */   public Predicate<BlockState> getPredicate(StateDefinition<Block, BlockState> $$0) {
/*    */     Predicate<BlockState> $$7;
/* 29 */     Property<?> $$1 = $$0.getProperty(this.key);
/* 30 */     if ($$1 == null) {
/* 31 */       throw new RuntimeException(String.format(Locale.ROOT, "Unknown property '%s' on '%s'", new Object[] { this.key, $$0.getOwner() }));
/*    */     }
/*    */     
/* 34 */     String $$2 = this.value;
/* 35 */     boolean $$3 = (!$$2.isEmpty() && $$2.charAt(0) == '!');
/* 36 */     if ($$3) {
/* 37 */       $$2 = $$2.substring(1);
/*    */     }
/*    */     
/* 40 */     List<String> $$4 = PIPE_SPLITTER.splitToList($$2);
/* 41 */     if ($$4.isEmpty()) {
/* 42 */       throw new RuntimeException(String.format(Locale.ROOT, "Empty value '%s' for property '%s' on '%s'", new Object[] { this.value, this.key, $$0.getOwner() }));
/*    */     }
/*    */ 
/*    */     
/* 46 */     if ($$4.size() == 1) {
/* 47 */       Predicate<BlockState> $$5 = getBlockStatePredicate($$0, $$1, $$2);
/*    */     } else {
/* 49 */       List<Predicate<BlockState>> $$6 = (List<Predicate<BlockState>>)$$4.stream().map($$2 -> getBlockStatePredicate($$0, $$1, $$2)).collect(Collectors.toList());
/* 50 */       $$7 = ($$1 -> $$0.stream().anyMatch(()));
/*    */     } 
/*    */     
/* 53 */     return $$3 ? $$7.negate() : $$7;
/*    */   }
/*    */   
/*    */   private Predicate<BlockState> getBlockStatePredicate(StateDefinition<Block, BlockState> $$0, Property<?> $$1, String $$2) {
/* 57 */     Optional<?> $$3 = $$1.getValue($$2);
/* 58 */     if ($$3.isEmpty()) {
/* 59 */       throw new RuntimeException(String.format(Locale.ROOT, "Unknown value '%s' for property '%s' on '%s' in '%s'", new Object[] { $$2, this.key, $$0.getOwner(), this.value }));
/*    */     }
/*    */     
/* 62 */     return $$2 -> $$2.getValue($$0).equals($$1.get());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 67 */     return MoreObjects.toStringHelper(this)
/* 68 */       .add("key", this.key)
/* 69 */       .add("value", this.value)
/* 70 */       .toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\multipart\KeyValueCondition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */