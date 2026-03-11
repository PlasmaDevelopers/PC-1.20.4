/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class RealmsScreen
/*    */   extends Screen
/*    */ {
/*    */   protected static final int TITLE_HEIGHT = 17;
/*    */   protected static final int EXPIRATION_NOTIFICATION_DAYS = 7;
/*    */   protected static final long SIZE_LIMIT = 5368709120L;
/*    */   protected static final int COLOR_DARK_GRAY = 5000268;
/*    */   protected static final int COLOR_MEDIUM_GRAY = 7105644;
/*    */   protected static final int COLOR_GREEN = 8388479;
/*    */   protected static final int COLOR_LINK = 3368635;
/*    */   protected static final int COLOR_LINK_HOVER = 7107012;
/*    */   protected static final int COLOR_INFO = 8226750;
/*    */   protected static final int SKIN_FACE_SIZE = 8;
/* 27 */   private final List<RealmsLabel> labels = Lists.newArrayList();
/*    */   
/*    */   public RealmsScreen(Component $$0) {
/* 30 */     super($$0);
/*    */   }
/*    */   
/*    */   protected static int row(int $$0) {
/* 34 */     return 40 + $$0 * 13;
/*    */   }
/*    */   
/*    */   protected RealmsLabel addLabel(RealmsLabel $$0) {
/* 38 */     this.labels.add($$0);
/* 39 */     return (RealmsLabel)addRenderableOnly($$0);
/*    */   }
/*    */   
/*    */   public Component createLabelNarration() {
/* 43 */     return CommonComponents.joinLines((Collection)this.labels.stream().map(RealmsLabel::getText).collect(Collectors.toList()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\realms\RealmsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */