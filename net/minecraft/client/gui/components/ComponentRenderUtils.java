/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.client.ComponentCollector;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.locale.Language;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.network.chat.Style;
/*    */ import net.minecraft.util.FormattedCharSequence;
/*    */ 
/*    */ public class ComponentRenderUtils
/*    */ {
/* 17 */   private static final FormattedCharSequence INDENT = FormattedCharSequence.codepoint(32, Style.EMPTY);
/*    */   
/*    */   private static String stripColor(String $$0) {
/* 20 */     return ((Boolean)(Minecraft.getInstance()).options.chatColors().get()).booleanValue() ? $$0 : ChatFormatting.stripFormatting($$0);
/*    */   }
/*    */   
/*    */   public static List<FormattedCharSequence> wrapComponents(FormattedText $$0, int $$1, Font $$2) {
/* 24 */     ComponentCollector $$3 = new ComponentCollector();
/* 25 */     $$0.visit(($$1, $$2) -> { $$0.append(FormattedText.of(stripColor($$2), $$1)); return Optional.empty(); }Style.EMPTY);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 30 */     List<FormattedCharSequence> $$4 = Lists.newArrayList();
/*    */     
/* 32 */     $$2.getSplitter().splitLines($$3.getResultOrEmpty(), $$1, Style.EMPTY, ($$1, $$2) -> {
/*    */           FormattedCharSequence $$3 = Language.getInstance().getVisualOrder($$1);
/*    */           
/*    */           $$0.add($$2.booleanValue() ? FormattedCharSequence.composite(INDENT, $$3) : $$3);
/*    */         });
/*    */     
/* 38 */     if ($$4.isEmpty())
/*    */     {
/* 40 */       return Lists.newArrayList((Object[])new FormattedCharSequence[] { FormattedCharSequence.EMPTY });
/*    */     }
/* 42 */     return $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\ComponentRenderUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */