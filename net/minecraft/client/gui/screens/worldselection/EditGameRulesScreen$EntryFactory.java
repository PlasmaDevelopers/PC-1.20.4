package net.minecraft.client.gui.screens.worldselection;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

@FunctionalInterface
interface EntryFactory<T extends net.minecraft.world.level.GameRules.Value<T>> {
  EditGameRulesScreen.RuleEntry create(Component paramComponent, List<FormattedCharSequence> paramList, String paramString, T paramT);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\EditGameRulesScreen$EntryFactory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */