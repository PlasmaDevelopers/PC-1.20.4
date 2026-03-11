package net.minecraft.client.gui.components.tabs;

import java.util.function.Consumer;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;

public interface Tab {
  Component getTabTitle();
  
  void visitChildren(Consumer<AbstractWidget> paramConsumer);
  
  void doLayout(ScreenRectangle paramScreenRectangle);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\tabs\Tab.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */