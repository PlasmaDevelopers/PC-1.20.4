/*    */ package net.minecraft.client.gui.screens.reporting;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.client.multiplayer.chat.ChatLog;
/*    */ import net.minecraft.client.multiplayer.chat.LoggedChatEvent;
/*    */ import net.minecraft.client.multiplayer.chat.LoggedChatMessage;
/*    */ import net.minecraft.client.multiplayer.chat.report.ChatReportContextBuilder;
/*    */ import net.minecraft.client.multiplayer.chat.report.ReportingContext;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.PlayerChatMessage;
/*    */ import net.minecraft.network.chat.SignedMessageLink;
/*    */ 
/*    */ public class ChatSelectionLogFiller
/*    */ {
/*    */   private final ChatLog log;
/*    */   private final ChatReportContextBuilder contextBuilder;
/*    */   private final Predicate<LoggedChatMessage.Player> canReport;
/*    */   @Nullable
/* 21 */   private SignedMessageLink previousLink = null;
/*    */   
/*    */   private int eventId;
/*    */   
/*    */   private int missedCount;
/*    */   @Nullable
/*    */   private PlayerChatMessage lastMessage;
/*    */   
/*    */   public ChatSelectionLogFiller(ReportingContext $$0, Predicate<LoggedChatMessage.Player> $$1) {
/* 30 */     this.log = $$0.chatLog();
/* 31 */     this.contextBuilder = new ChatReportContextBuilder($$0.sender().reportLimits().leadingContextMessageCount());
/* 32 */     this.canReport = $$1;
/* 33 */     this.eventId = this.log.end();
/*    */   }
/*    */   
/*    */   public void fillNextPage(int $$0, Output $$1) {
/* 37 */     int $$2 = 0;
/* 38 */     while ($$2 < $$0) {
/* 39 */       LoggedChatEvent $$3 = this.log.lookup(this.eventId);
/* 40 */       if ($$3 == null) {
/*    */         break;
/*    */       }
/* 43 */       int $$4 = this.eventId;
/* 44 */       this.eventId--;
/*    */       
/* 46 */       if ($$3 instanceof LoggedChatMessage.Player) { LoggedChatMessage.Player $$5 = (LoggedChatMessage.Player)$$3;
/* 47 */         if ($$5.message().equals(this.lastMessage)) {
/*    */           continue;
/*    */         }
/*    */         
/* 51 */         if (acceptMessage($$1, $$5)) {
/* 52 */           if (this.missedCount > 0) {
/* 53 */             $$1.acceptDivider((Component)Component.translatable("gui.chatSelection.fold", new Object[] { Integer.valueOf(this.missedCount) }));
/* 54 */             this.missedCount = 0;
/*    */           } 
/* 56 */           $$1.acceptMessage($$4, $$5);
/* 57 */           $$2++;
/*    */         } else {
/* 59 */           this.missedCount++;
/*    */         } 
/* 61 */         this.lastMessage = $$5.message(); }
/*    */     
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean acceptMessage(Output $$0, LoggedChatMessage.Player $$1) {
/* 67 */     PlayerChatMessage $$2 = $$1.message();
/* 68 */     boolean $$3 = this.contextBuilder.acceptContext($$2);
/* 69 */     if (this.canReport.test($$1)) {
/* 70 */       this.contextBuilder.trackContext($$2);
/* 71 */       if (this.previousLink != null && !this.previousLink.isDescendantOf($$2.link())) {
/* 72 */         $$0.acceptDivider((Component)Component.translatable("gui.chatSelection.join", new Object[] { $$1.profile().getName() }).withStyle(ChatFormatting.YELLOW));
/*    */       }
/* 74 */       this.previousLink = $$2.link();
/* 75 */       return true;
/*    */     } 
/* 77 */     return $$3;
/*    */   }
/*    */   
/*    */   public static interface Output {
/*    */     void acceptMessage(int param1Int, LoggedChatMessage.Player param1Player);
/*    */     
/*    */     void acceptDivider(Component param1Component);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\reporting\ChatSelectionLogFiller.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */