package net.minecraft.commands.execution.tasks;

import net.minecraft.commands.execution.CommandQueueEntry;
import net.minecraft.commands.execution.Frame;

@FunctionalInterface
public interface TaskProvider<T, P> {
  CommandQueueEntry<T> create(Frame paramFrame, P paramP);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\execution\tasks\ContinuationTask$TaskProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */