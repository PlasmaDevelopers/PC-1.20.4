/*     */ package net.minecraft.client.multiplayer.chat.report;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.minecraft.report.AbuseReport;
/*     */ import com.mojang.authlib.minecraft.report.AbuseReportLimits;
/*     */ import com.mojang.authlib.minecraft.report.ReportChatMessage;
/*     */ import com.mojang.authlib.minecraft.report.ReportEvidence;
/*     */ import com.mojang.authlib.minecraft.report.ReportedEntity;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.ints.IntSet;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.time.Instant;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Optionull;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.gui.screens.reporting.ChatReportScreen;
/*     */ import net.minecraft.client.multiplayer.chat.LoggedChatMessage;
/*     */ import net.minecraft.network.chat.MessageSignature;
/*     */ import net.minecraft.network.chat.SignedMessageBody;
/*     */ import net.minecraft.network.chat.SignedMessageLink;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class ChatReport extends Report {
/*  30 */   final IntSet reportedMessages = (IntSet)new IntOpenHashSet();
/*     */   
/*     */   ChatReport(UUID $$0, Instant $$1, UUID $$2) {
/*  33 */     super($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public void toggleReported(int $$0, AbuseReportLimits $$1) {
/*  37 */     if (this.reportedMessages.contains($$0)) {
/*  38 */       this.reportedMessages.remove($$0);
/*  39 */     } else if (this.reportedMessages.size() < $$1.maxReportedMessageCount()) {
/*  40 */       this.reportedMessages.add($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChatReport copy() {
/*  46 */     ChatReport $$0 = new ChatReport(this.reportId, this.createdAt, this.reportedProfileId);
/*  47 */     $$0.reportedMessages.addAll((IntCollection)this.reportedMessages);
/*  48 */     $$0.comments = this.comments;
/*  49 */     $$0.reason = this.reason;
/*  50 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Screen createScreen(Screen $$0, ReportingContext $$1) {
/*  55 */     return (Screen)new ChatReportScreen($$0, $$1, this);
/*     */   }
/*     */   
/*     */   public static class Builder extends Report.Builder<ChatReport> {
/*     */     public Builder(ChatReport $$0, AbuseReportLimits $$1) {
/*  60 */       super($$0, $$1);
/*     */     }
/*     */     
/*     */     public Builder(UUID $$0, AbuseReportLimits $$1) {
/*  64 */       super(new ChatReport(UUID.randomUUID(), Instant.now(), $$0), $$1);
/*     */     }
/*     */     
/*     */     public IntSet reportedMessages() {
/*  68 */       return this.report.reportedMessages;
/*     */     }
/*     */     
/*     */     public void toggleReported(int $$0) {
/*  72 */       this.report.toggleReported($$0, this.limits);
/*     */     }
/*     */     
/*     */     public boolean isReported(int $$0) {
/*  76 */       return this.report.reportedMessages.contains($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasContent() {
/*  81 */       return (StringUtils.isNotEmpty(comments()) || !reportedMessages().isEmpty() || reason() != null);
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Report.CannotBuildReason checkBuildable() {
/*  87 */       if (this.report.reportedMessages.isEmpty()) {
/*  88 */         return Report.CannotBuildReason.NO_REPORTED_MESSAGES;
/*     */       }
/*  90 */       if (this.report.reportedMessages.size() > this.limits.maxReportedMessageCount()) {
/*  91 */         return Report.CannotBuildReason.TOO_MANY_MESSAGES;
/*     */       }
/*  93 */       if (this.report.reason == null) {
/*  94 */         return Report.CannotBuildReason.NO_REASON;
/*     */       }
/*  96 */       if (this.report.comments.length() > this.limits.maxOpinionCommentsLength()) {
/*  97 */         return Report.CannotBuildReason.COMMENT_TOO_LONG;
/*     */       }
/*  99 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Either<Report.Result, Report.CannotBuildReason> build(ReportingContext $$0) {
/* 104 */       Report.CannotBuildReason $$1 = checkBuildable();
/* 105 */       if ($$1 != null) {
/* 106 */         return Either.right($$1);
/*     */       }
/*     */       
/* 109 */       String $$2 = ((ReportReason)Objects.<ReportReason>requireNonNull(this.report.reason)).backendName();
/*     */       
/* 111 */       ReportEvidence $$3 = buildEvidence($$0);
/*     */       
/* 113 */       ReportedEntity $$4 = new ReportedEntity(this.report.reportedProfileId);
/*     */       
/* 115 */       AbuseReport $$5 = AbuseReport.chat(this.report.comments, $$2, $$3, $$4, this.report.createdAt);
/* 116 */       return Either.left(new Report.Result(this.report.reportId, ReportType.CHAT, $$5));
/*     */     }
/*     */     
/*     */     private ReportEvidence buildEvidence(ReportingContext $$0) {
/* 120 */       List<ReportChatMessage> $$1 = new ArrayList<>();
/*     */       
/* 122 */       ChatReportContextBuilder $$2 = new ChatReportContextBuilder(this.limits.leadingContextMessageCount());
/* 123 */       $$2.collectAllContext($$0.chatLog(), (IntCollection)this.report.reportedMessages, ($$1, $$2) -> $$0.add(buildReportedChatMessage($$2, isReported($$1))));
/*     */ 
/*     */ 
/*     */       
/* 127 */       return new ReportEvidence(Lists.reverse($$1));
/*     */     }
/*     */     
/*     */     private ReportChatMessage buildReportedChatMessage(LoggedChatMessage.Player $$0, boolean $$1) {
/* 131 */       SignedMessageLink $$2 = $$0.message().link();
/* 132 */       SignedMessageBody $$3 = $$0.message().signedBody();
/*     */ 
/*     */ 
/*     */       
/* 136 */       List<ByteBuffer> $$4 = $$3.lastSeen().entries().stream().map(MessageSignature::asByteBuffer).toList();
/* 137 */       ByteBuffer $$5 = (ByteBuffer)Optionull.map($$0.message().signature(), MessageSignature::asByteBuffer);
/*     */       
/* 139 */       return new ReportChatMessage($$2.index(), $$2.sender(), $$2.sessionId(), $$3.timeStamp(), $$3.salt(), $$4, $$3.content(), $$5, $$1);
/*     */     }
/*     */     
/*     */     public Builder copy() {
/* 143 */       return new Builder(this.report.copy(), this.limits);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\ChatReport.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */