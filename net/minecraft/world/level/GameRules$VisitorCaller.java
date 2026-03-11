package net.minecraft.world.level;

interface VisitorCaller<T extends GameRules.Value<T>> {
  void call(GameRules.GameRuleTypeVisitor paramGameRuleTypeVisitor, GameRules.Key<T> paramKey, GameRules.Type<T> paramType);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\GameRules$VisitorCaller.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */