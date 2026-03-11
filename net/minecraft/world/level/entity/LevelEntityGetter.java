package net.minecraft.world.level.entity;

import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.world.phys.AABB;

public interface LevelEntityGetter<T extends EntityAccess> {
  @Nullable
  T get(int paramInt);
  
  @Nullable
  T get(UUID paramUUID);
  
  Iterable<T> getAll();
  
  <U extends T> void get(EntityTypeTest<T, U> paramEntityTypeTest, AbortableIterationConsumer<U> paramAbortableIterationConsumer);
  
  void get(AABB paramAABB, Consumer<T> paramConsumer);
  
  <U extends T> void get(EntityTypeTest<T, U> paramEntityTypeTest, AABB paramAABB, AbortableIterationConsumer<U> paramAbortableIterationConsumer);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\LevelEntityGetter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */