/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class CommonComponents {
/*  7 */   public static final Component EMPTY = Component.empty();
/*    */   
/*  9 */   public static final Component OPTION_ON = Component.translatable("options.on");
/* 10 */   public static final Component OPTION_OFF = Component.translatable("options.off");
/*    */   
/* 12 */   public static final Component GUI_DONE = Component.translatable("gui.done");
/* 13 */   public static final Component GUI_CANCEL = Component.translatable("gui.cancel");
/* 14 */   public static final Component GUI_YES = Component.translatable("gui.yes");
/* 15 */   public static final Component GUI_NO = Component.translatable("gui.no");
/* 16 */   public static final Component GUI_OK = Component.translatable("gui.ok");
/* 17 */   public static final Component GUI_PROCEED = Component.translatable("gui.proceed");
/* 18 */   public static final Component GUI_CONTINUE = Component.translatable("gui.continue");
/* 19 */   public static final Component GUI_BACK = Component.translatable("gui.back");
/* 20 */   public static final Component GUI_TO_TITLE = Component.translatable("gui.toTitle");
/* 21 */   public static final Component GUI_ACKNOWLEDGE = Component.translatable("gui.acknowledge");
/* 22 */   public static final Component GUI_OPEN_IN_BROWSER = Component.translatable("chat.link.open");
/* 23 */   public static final Component GUI_COPY_LINK_TO_CLIPBOARD = Component.translatable("gui.copy_link_to_clipboard");
/* 24 */   public static final Component GUI_DISCONNECT = Component.translatable("menu.disconnect");
/*    */   
/* 26 */   public static final Component CONNECT_FAILED = Component.translatable("connect.failed");
/*    */   
/* 28 */   public static final Component NEW_LINE = Component.literal("\n");
/* 29 */   public static final Component NARRATION_SEPARATOR = Component.literal(". ");
/*    */   
/* 31 */   public static final Component ELLIPSIS = Component.literal("...");
/* 32 */   public static final Component SPACE = space();
/*    */   
/*    */   public static MutableComponent space() {
/* 35 */     return Component.literal(" ");
/*    */   }
/*    */   
/*    */   public static MutableComponent days(long $$0) {
/* 39 */     return Component.translatable("gui.days", new Object[] { Long.valueOf($$0) });
/*    */   }
/*    */   
/*    */   public static MutableComponent hours(long $$0) {
/* 43 */     return Component.translatable("gui.hours", new Object[] { Long.valueOf($$0) });
/*    */   }
/*    */   
/*    */   public static MutableComponent minutes(long $$0) {
/* 47 */     return Component.translatable("gui.minutes", new Object[] { Long.valueOf($$0) });
/*    */   }
/*    */   
/*    */   public static Component optionStatus(boolean $$0) {
/* 51 */     return $$0 ? OPTION_ON : OPTION_OFF;
/*    */   }
/*    */   
/*    */   public static MutableComponent optionStatus(Component $$0, boolean $$1) {
/* 55 */     return Component.translatable($$1 ? "options.on.composed" : "options.off.composed", new Object[] { $$0 });
/*    */   }
/*    */   
/*    */   public static MutableComponent optionNameValue(Component $$0, Component $$1) {
/* 59 */     return Component.translatable("options.generic_value", new Object[] { $$0, $$1 });
/*    */   }
/*    */   
/*    */   public static MutableComponent joinForNarration(Component... $$0) {
/* 63 */     MutableComponent $$1 = Component.empty();
/* 64 */     for (int $$2 = 0; $$2 < $$0.length; $$2++) {
/* 65 */       $$1.append($$0[$$2]);
/* 66 */       if ($$2 != $$0.length - 1) {
/* 67 */         $$1.append(NARRATION_SEPARATOR);
/*    */       }
/*    */     } 
/* 70 */     return $$1;
/*    */   }
/*    */   
/*    */   public static Component joinLines(Component... $$0) {
/* 74 */     return joinLines(Arrays.asList($$0));
/*    */   }
/*    */   
/*    */   public static Component joinLines(Collection<? extends Component> $$0) {
/* 78 */     return ComponentUtils.formatList($$0, NEW_LINE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\CommonComponents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */