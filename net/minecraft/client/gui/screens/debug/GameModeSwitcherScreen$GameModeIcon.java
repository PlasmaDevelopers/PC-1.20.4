/*    */ package net.minecraft.client.gui.screens.debug;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.GameType;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ enum GameModeIcon
/*    */ {
/* 28 */   CREATIVE((Component)Component.translatable("gameMode.creative"), "gamemode creative", new ItemStack((ItemLike)Blocks.GRASS_BLOCK)),
/* 29 */   SURVIVAL((Component)Component.translatable("gameMode.survival"), "gamemode survival", new ItemStack((ItemLike)Items.IRON_SWORD)),
/* 30 */   ADVENTURE((Component)Component.translatable("gameMode.adventure"), "gamemode adventure", new ItemStack((ItemLike)Items.MAP)),
/* 31 */   SPECTATOR((Component)Component.translatable("gameMode.spectator"), "gamemode spectator", new ItemStack((ItemLike)Items.ENDER_EYE)); protected static final GameModeIcon[] VALUES; private static final int ICON_AREA = 16; protected static final int ICON_TOP_LEFT = 5; final Component name; final String command; final ItemStack renderStack;
/*    */   static {
/* 33 */     VALUES = values();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   GameModeIcon(Component $$0, String $$1, ItemStack $$2) {
/* 42 */     this.name = $$0;
/* 43 */     this.command = $$1;
/* 44 */     this.renderStack = $$2;
/*    */   }
/*    */   
/*    */   void drawIcon(GuiGraphics $$0, int $$1, int $$2) {
/* 48 */     $$0.renderItem(this.renderStack, $$1, $$2);
/*    */   }
/*    */   
/*    */   Component getName() {
/* 52 */     return this.name;
/*    */   }
/*    */   
/*    */   String getCommand() {
/* 56 */     return this.command;
/*    */   }
/*    */   
/*    */   GameModeIcon getNext() {
/* 60 */     switch (GameModeSwitcherScreen.null.$SwitchMap$net$minecraft$client$gui$screens$debug$GameModeSwitcherScreen$GameModeIcon[ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: case 3: case 4: break; }  return 
/*    */ 
/*    */ 
/*    */       
/* 64 */       CREATIVE;
/*    */   }
/*    */ 
/*    */   
/*    */   static GameModeIcon getFromGameType(GameType $$0) {
/* 69 */     switch (GameModeSwitcherScreen.null.$SwitchMap$net$minecraft$world$level$GameType[$$0.ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: case 3: case 4: break; }  return 
/*    */ 
/*    */ 
/*    */       
/* 73 */       ADVENTURE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\debug\GameModeSwitcherScreen$GameModeIcon.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */