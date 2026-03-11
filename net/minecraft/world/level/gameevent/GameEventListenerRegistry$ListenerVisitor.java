package net.minecraft.world.level.gameevent;

import net.minecraft.world.phys.Vec3;

@FunctionalInterface
public interface ListenerVisitor {
  void visit(GameEventListener paramGameEventListener, Vec3 paramVec3);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\GameEventListenerRegistry$ListenerVisitor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */