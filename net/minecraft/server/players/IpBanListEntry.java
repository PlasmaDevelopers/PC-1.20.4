/*    */ package net.minecraft.server.players;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.Date;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class IpBanListEntry
/*    */   extends BanListEntry<String> {
/*    */   public IpBanListEntry(String $$0) {
/* 11 */     this($$0, null, null, null, null);
/*    */   }
/*    */   
/*    */   public IpBanListEntry(String $$0, @Nullable Date $$1, @Nullable String $$2, @Nullable Date $$3, @Nullable String $$4) {
/* 15 */     super($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getDisplayName() {
/* 20 */     return (Component)Component.literal(String.valueOf(getUser()));
/*    */   }
/*    */   
/*    */   public IpBanListEntry(JsonObject $$0) {
/* 24 */     super(createIpInfo($$0), $$0);
/*    */   }
/*    */   
/*    */   private static String createIpInfo(JsonObject $$0) {
/* 28 */     return $$0.has("ip") ? $$0.get("ip").getAsString() : null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void serialize(JsonObject $$0) {
/* 33 */     if (getUser() == null) {
/*    */       return;
/*    */     }
/* 36 */     $$0.addProperty("ip", getUser());
/* 37 */     super.serialize($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\players\IpBanListEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */