/*    */ package net.minecraft.client.main;
/*    */ 
/*    */ import com.mojang.authlib.properties.PropertyMap;
/*    */ import java.net.Proxy;
/*    */ import net.minecraft.client.User;
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
/*    */ public class UserData
/*    */ {
/*    */   public final User user;
/*    */   public final PropertyMap userProperties;
/*    */   public final PropertyMap profileProperties;
/*    */   public final Proxy proxy;
/*    */   
/*    */   public UserData(User $$0, PropertyMap $$1, PropertyMap $$2, Proxy $$3) {
/* 52 */     this.user = $$0;
/* 53 */     this.userProperties = $$1;
/* 54 */     this.profileProperties = $$2;
/* 55 */     this.proxy = $$3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\main\GameConfig$UserData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */