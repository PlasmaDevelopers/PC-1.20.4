package net.minecraft.world.level.storage.loot.providers.score;

import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.scores.ScoreHolder;

public interface ScoreboardNameProvider {
  @Nullable
  ScoreHolder getScoreHolder(LootContext paramLootContext);
  
  LootScoreProviderType getType();
  
  Set<LootContextParam<?>> getReferencedContextParams();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\score\ScoreboardNameProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */