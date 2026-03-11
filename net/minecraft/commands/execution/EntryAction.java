package net.minecraft.commands.execution;

@FunctionalInterface
public interface EntryAction<T> {
  void execute(ExecutionContext<T> paramExecutionContext, Frame paramFrame);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\EntryAction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */