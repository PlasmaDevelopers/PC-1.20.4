/*    */ package net.minecraft.client;
/*    */ public final class GuiMessageTag extends Record {
/*    */   private final int indicatorColor;
/*    */   @Nullable
/*    */   private final Icon icon;
/*    */   @Nullable
/*    */   private final Component text;
/*    */   @Nullable
/*    */   private final String logTag;
/*    */   
/* 11 */   public GuiMessageTag(int $$0, @Nullable Icon $$1, @Nullable Component $$2, @Nullable String $$3) { this.indicatorColor = $$0; this.icon = $$1; this.text = $$2; this.logTag = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/GuiMessageTag;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/client/GuiMessageTag; } public int indicatorColor() { return this.indicatorColor; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/GuiMessageTag;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/GuiMessageTag; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/GuiMessageTag;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/GuiMessageTag;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } @Nullable public Icon icon() { return this.icon; } @Nullable public Component text() { return this.text; } @Nullable public String logTag() { return this.logTag; }
/* 12 */    private static final Component SYSTEM_TEXT = (Component)Component.translatable("chat.tag.system");
/* 13 */   private static final Component SYSTEM_TEXT_SINGLE_PLAYER = (Component)Component.translatable("chat.tag.system_single_player");
/* 14 */   private static final Component CHAT_NOT_SECURE_TEXT = (Component)Component.translatable("chat.tag.not_secure");
/* 15 */   private static final Component CHAT_MODIFIED_TEXT = (Component)Component.translatable("chat.tag.modified");
/* 16 */   private static final Component CHAT_ERROR_TEXT = (Component)Component.translatable("chat.tag.error");
/*    */   
/*    */   private static final int CHAT_NOT_SECURE_INDICATOR_COLOR = 13684944;
/*    */   
/*    */   private static final int CHAT_MODIFIED_INDICATOR_COLOR = 6316128;
/* 21 */   private static final GuiMessageTag SYSTEM = new GuiMessageTag(13684944, null, SYSTEM_TEXT, "System");
/* 22 */   private static final GuiMessageTag SYSTEM_SINGLE_PLAYER = new GuiMessageTag(13684944, null, SYSTEM_TEXT_SINGLE_PLAYER, "System");
/* 23 */   private static final GuiMessageTag CHAT_NOT_SECURE = new GuiMessageTag(13684944, null, CHAT_NOT_SECURE_TEXT, "Not Secure");
/* 24 */   private static final GuiMessageTag CHAT_ERROR = new GuiMessageTag(16733525, null, CHAT_ERROR_TEXT, "Chat Error");
/*    */   
/*    */   public static GuiMessageTag system() {
/* 27 */     return SYSTEM;
/*    */   }
/*    */   
/*    */   public static GuiMessageTag systemSinglePlayer() {
/* 31 */     return SYSTEM_SINGLE_PLAYER;
/*    */   }
/*    */   
/*    */   public static GuiMessageTag chatNotSecure() {
/* 35 */     return CHAT_NOT_SECURE;
/*    */   }
/*    */   
/*    */   public static GuiMessageTag chatModified(String $$0) {
/* 39 */     MutableComponent mutableComponent1 = Component.literal($$0).withStyle(ChatFormatting.GRAY);
/* 40 */     MutableComponent mutableComponent2 = Component.empty().append(CHAT_MODIFIED_TEXT).append(CommonComponents.NEW_LINE).append((Component)mutableComponent1);
/* 41 */     return new GuiMessageTag(6316128, Icon.CHAT_MODIFIED, (Component)mutableComponent2, "Modified");
/*    */   }
/*    */   
/*    */   public static GuiMessageTag chatError() {
/* 45 */     return CHAT_ERROR;
/*    */   }
/*    */   
/*    */   public enum Icon {
/* 49 */     CHAT_MODIFIED((String)new ResourceLocation("icon/chat_modified"), 9, 9);
/*    */     
/*    */     public final int height;
/*    */     
/*    */     public final int width;
/*    */     public final ResourceLocation sprite;
/*    */     
/*    */     Icon(ResourceLocation $$0, int $$1, int $$2) {
/* 57 */       this.sprite = $$0;
/* 58 */       this.width = $$1;
/* 59 */       this.height = $$2;
/*    */     }
/*    */     
/*    */     public void draw(GuiGraphics $$0, int $$1, int $$2) {
/* 63 */       $$0.blitSprite(this.sprite, $$1, $$2, this.width, this.height);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\GuiMessageTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */