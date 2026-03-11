package net.minecraft.client.gui.screens.multiplayer;

import net.minecraft.client.gui.components.ObjectSelectionList;

public abstract class Entry extends ObjectSelectionList.Entry<ServerSelectionList.Entry> implements AutoCloseable {
  public void close() {}
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\multiplayer\ServerSelectionList$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */