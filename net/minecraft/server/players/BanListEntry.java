/*    */ package net.minecraft.server.players;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Locale;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public abstract class BanListEntry<T>
/*    */   extends StoredUserEntry<T> {
/* 13 */   public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.ROOT);
/*    */   
/*    */   public static final String EXPIRES_NEVER = "forever";
/*    */   protected final Date created;
/*    */   protected final String source;
/*    */   @Nullable
/*    */   protected final Date expires;
/*    */   protected final String reason;
/*    */   
/*    */   public BanListEntry(@Nullable T $$0, @Nullable Date $$1, @Nullable String $$2, @Nullable Date $$3, @Nullable String $$4) {
/* 23 */     super($$0);
/* 24 */     this.created = ($$1 == null) ? new Date() : $$1;
/* 25 */     this.source = ($$2 == null) ? "(Unknown)" : $$2;
/* 26 */     this.expires = $$3;
/* 27 */     this.reason = ($$4 == null) ? "Banned by an operator." : $$4;
/*    */   }
/*    */   
/*    */   protected BanListEntry(@Nullable T $$0, JsonObject $$1) {
/* 31 */     super($$0);
/*    */     Date $$4, $$7;
/*    */     try {
/* 34 */       Date $$2 = $$1.has("created") ? DATE_FORMAT.parse($$1.get("created").getAsString()) : new Date();
/* 35 */     } catch (ParseException $$3) {
/* 36 */       $$4 = new Date();
/*    */     } 
/* 38 */     this.created = $$4;
/* 39 */     this.source = $$1.has("source") ? $$1.get("source").getAsString() : "(Unknown)";
/*    */     
/*    */     try {
/* 42 */       Date $$5 = $$1.has("expires") ? DATE_FORMAT.parse($$1.get("expires").getAsString()) : null;
/* 43 */     } catch (ParseException $$6) {
/* 44 */       $$7 = null;
/*    */     } 
/* 46 */     this.expires = $$7;
/* 47 */     this.reason = $$1.has("reason") ? $$1.get("reason").getAsString() : "Banned by an operator.";
/*    */   }
/*    */   
/*    */   public Date getCreated() {
/* 51 */     return this.created;
/*    */   }
/*    */   
/*    */   public String getSource() {
/* 55 */     return this.source;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Date getExpires() {
/* 60 */     return this.expires;
/*    */   }
/*    */   
/*    */   public String getReason() {
/* 64 */     return this.reason;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract Component getDisplayName();
/*    */   
/*    */   boolean hasExpired() {
/* 71 */     if (this.expires == null) {
/* 72 */       return false;
/*    */     }
/* 74 */     return this.expires.before(new Date());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void serialize(JsonObject $$0) {
/* 79 */     $$0.addProperty("created", DATE_FORMAT.format(this.created));
/* 80 */     $$0.addProperty("source", this.source);
/* 81 */     $$0.addProperty("expires", (this.expires == null) ? "forever" : DATE_FORMAT.format(this.expires));
/* 82 */     $$0.addProperty("reason", this.reason);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\BanListEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */