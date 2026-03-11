package net.minecraft.world.level;

public interface GameRuleTypeVisitor {
  default <T extends GameRules.Value<T>> void visit(GameRules.Key<T> $$0, GameRules.Type<T> $$1) {}
  
  default void visitBoolean(GameRules.Key<GameRules.BooleanValue> $$0, GameRules.Type<GameRules.BooleanValue> $$1) {}
  
  default void visitInteger(GameRules.Key<GameRules.IntegerValue> $$0, GameRules.Type<GameRules.IntegerValue> $$1) {}
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\GameRules$GameRuleTypeVisitor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */