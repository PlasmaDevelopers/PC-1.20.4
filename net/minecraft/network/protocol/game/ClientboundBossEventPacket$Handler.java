package net.minecraft.network.protocol.game;

import java.util.UUID;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;

public interface Handler {
  default void add(UUID $$0, Component $$1, float $$2, BossEvent.BossBarColor $$3, BossEvent.BossBarOverlay $$4, boolean $$5, boolean $$6, boolean $$7) {}
  
  default void remove(UUID $$0) {}
  
  default void updateProgress(UUID $$0, float $$1) {}
  
  default void updateName(UUID $$0, Component $$1) {}
  
  default void updateStyle(UUID $$0, BossEvent.BossBarColor $$1, BossEvent.BossBarOverlay $$2) {}
  
  default void updateProperties(UUID $$0, boolean $$1, boolean $$2, boolean $$3) {}
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundBossEventPacket$Handler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */