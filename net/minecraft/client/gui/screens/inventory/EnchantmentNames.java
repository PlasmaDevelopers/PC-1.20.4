/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ 
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.network.chat.Style;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class EnchantmentNames {
/* 12 */   private static final ResourceLocation ALT_FONT = new ResourceLocation("minecraft", "alt");
/* 13 */   private static final Style ROOT_STYLE = Style.EMPTY.withFont(ALT_FONT);
/*    */   
/* 15 */   private static final EnchantmentNames INSTANCE = new EnchantmentNames();
/*    */   
/* 17 */   private final RandomSource random = RandomSource.create();
/*    */   
/* 19 */   private final String[] words = new String[] { "the", "elder", "scrolls", "klaatu", "berata", "niktu", "xyzzy", "bless", "curse", "light", "darkness", "fire", "air", "earth", "water", "hot", "dry", "cold", "wet", "ignite", "snuff", "embiggen", "twist", "shorten", "stretch", "fiddle", "destroy", "imbue", "galvanize", "enchant", "free", "limited", "range", "of", "towards", "inside", "sphere", "cube", "self", "other", "ball", "mental", "physical", "grow", "shrink", "demon", "elemental", "spirit", "animal", "creature", "beast", "humanoid", "undead", "fresh", "stale", "phnglui", "mglwnafh", "cthulhu", "rlyeh", "wgahnagl", "fhtagn", "baguette" };
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static EnchantmentNames getInstance() {
/* 46 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public FormattedText getRandomName(Font $$0, int $$1) {
/* 50 */     StringBuilder $$2 = new StringBuilder();
/* 51 */     int $$3 = this.random.nextInt(2) + 3;
/*    */     
/* 53 */     for (int $$4 = 0; $$4 < $$3; $$4++) {
/* 54 */       if ($$4 != 0) {
/* 55 */         $$2.append(" ");
/*    */       }
/* 57 */       $$2.append((String)Util.getRandom((Object[])this.words, this.random));
/*    */     } 
/* 59 */     return $$0.getSplitter().headByWidth((FormattedText)Component.literal($$2.toString()).withStyle(ROOT_STYLE), $$1, Style.EMPTY);
/*    */   }
/*    */   
/*    */   public void initSeed(long $$0) {
/* 63 */     this.random.setSeed($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\EnchantmentNames.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */