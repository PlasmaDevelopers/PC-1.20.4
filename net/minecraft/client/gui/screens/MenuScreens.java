/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.screens.inventory.MenuAccess;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.inventory.MenuType;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MenuScreens
/*     */ {
/*  38 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static <T extends net.minecraft.world.inventory.AbstractContainerMenu> void create(@Nullable MenuType<T> $$0, Minecraft $$1, int $$2, Component $$3) {
/*  41 */     if ($$0 == null) {
/*  42 */       LOGGER.warn("Trying to open invalid screen with name: {}", $$3.getString());
/*     */       
/*     */       return;
/*     */     } 
/*  46 */     ScreenConstructor<T, ?> $$4 = getConstructor($$0);
/*  47 */     if ($$4 == null) {
/*     */       
/*  49 */       LOGGER.warn("Failed to create screen for menu type: {}", BuiltInRegistries.MENU.getKey($$0));
/*     */       return;
/*     */     } 
/*  52 */     $$4.fromPacket($$3, $$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static <T extends net.minecraft.world.inventory.AbstractContainerMenu> ScreenConstructor<T, ?> getConstructor(MenuType<T> $$0) {
/*  58 */     return (ScreenConstructor<T, ?>)SCREENS.get($$0);
/*     */   }
/*     */   
/*     */   private static interface ScreenConstructor<T extends net.minecraft.world.inventory.AbstractContainerMenu, U extends Screen & MenuAccess<T>> {
/*     */     default void fromPacket(Component $$0, MenuType<T> $$1, Minecraft $$2, int $$3) {
/*  63 */       U $$4 = create((T)$$1.create($$3, $$2.player.getInventory()), $$2.player.getInventory(), $$0);
/*     */       
/*  65 */       $$2.player.containerMenu = ((MenuAccess)$$4).getMenu();
/*  66 */       $$2.setScreen((Screen)$$4);
/*     */     }
/*     */     
/*     */     U create(T param1T, Inventory param1Inventory, Component param1Component);
/*     */   }
/*     */   
/*  72 */   private static final Map<MenuType<?>, ScreenConstructor<?, ?>> SCREENS = Maps.newHashMap();
/*     */   
/*     */   private static <M extends net.minecraft.world.inventory.AbstractContainerMenu, U extends Screen & MenuAccess<M>> void register(MenuType<? extends M> $$0, ScreenConstructor<M, U> $$1) {
/*  75 */     ScreenConstructor<?, ?> $$2 = SCREENS.put($$0, $$1);
/*  76 */     if ($$2 != null) {
/*  77 */       throw new IllegalStateException("Duplicate registration for " + BuiltInRegistries.MENU.getKey($$0));
/*     */     }
/*     */   }
/*     */   
/*     */   static {
/*  82 */     register(MenuType.GENERIC_9x1, net.minecraft.client.gui.screens.inventory.ContainerScreen::new);
/*  83 */     register(MenuType.GENERIC_9x2, net.minecraft.client.gui.screens.inventory.ContainerScreen::new);
/*  84 */     register(MenuType.GENERIC_9x3, net.minecraft.client.gui.screens.inventory.ContainerScreen::new);
/*  85 */     register(MenuType.GENERIC_9x4, net.minecraft.client.gui.screens.inventory.ContainerScreen::new);
/*  86 */     register(MenuType.GENERIC_9x5, net.minecraft.client.gui.screens.inventory.ContainerScreen::new);
/*  87 */     register(MenuType.GENERIC_9x6, net.minecraft.client.gui.screens.inventory.ContainerScreen::new);
/*     */     
/*  89 */     register(MenuType.GENERIC_3x3, net.minecraft.client.gui.screens.inventory.DispenserScreen::new);
/*  90 */     register(MenuType.CRAFTER_3x3, net.minecraft.client.gui.screens.inventory.CrafterScreen::new);
/*     */     
/*  92 */     register(MenuType.ANVIL, net.minecraft.client.gui.screens.inventory.AnvilScreen::new);
/*  93 */     register(MenuType.BEACON, net.minecraft.client.gui.screens.inventory.BeaconScreen::new);
/*  94 */     register(MenuType.BLAST_FURNACE, net.minecraft.client.gui.screens.inventory.BlastFurnaceScreen::new);
/*  95 */     register(MenuType.BREWING_STAND, net.minecraft.client.gui.screens.inventory.BrewingStandScreen::new);
/*  96 */     register(MenuType.CRAFTING, net.minecraft.client.gui.screens.inventory.CraftingScreen::new);
/*  97 */     register(MenuType.ENCHANTMENT, net.minecraft.client.gui.screens.inventory.EnchantmentScreen::new);
/*  98 */     register(MenuType.FURNACE, net.minecraft.client.gui.screens.inventory.FurnaceScreen::new);
/*  99 */     register(MenuType.GRINDSTONE, net.minecraft.client.gui.screens.inventory.GrindstoneScreen::new);
/* 100 */     register(MenuType.HOPPER, net.minecraft.client.gui.screens.inventory.HopperScreen::new);
/* 101 */     register(MenuType.LECTERN, net.minecraft.client.gui.screens.inventory.LecternScreen::new);
/* 102 */     register(MenuType.LOOM, net.minecraft.client.gui.screens.inventory.LoomScreen::new);
/* 103 */     register(MenuType.MERCHANT, net.minecraft.client.gui.screens.inventory.MerchantScreen::new);
/* 104 */     register(MenuType.SHULKER_BOX, net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen::new);
/* 105 */     register(MenuType.SMITHING, net.minecraft.client.gui.screens.inventory.SmithingScreen::new);
/* 106 */     register(MenuType.SMOKER, net.minecraft.client.gui.screens.inventory.SmokerScreen::new);
/* 107 */     register(MenuType.CARTOGRAPHY_TABLE, net.minecraft.client.gui.screens.inventory.CartographyTableScreen::new);
/* 108 */     register(MenuType.STONECUTTER, net.minecraft.client.gui.screens.inventory.StonecutterScreen::new);
/*     */   }
/*     */   
/*     */   public static boolean selfTest() {
/* 112 */     boolean $$0 = false;
/* 113 */     for (MenuType<?> $$1 : (Iterable<MenuType<?>>)BuiltInRegistries.MENU) {
/* 114 */       if (!SCREENS.containsKey($$1)) {
/* 115 */         LOGGER.debug("Menu {} has no matching screen", BuiltInRegistries.MENU.getKey($$1));
/* 116 */         $$0 = true;
/*     */       } 
/*     */     } 
/* 119 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\MenuScreens.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */