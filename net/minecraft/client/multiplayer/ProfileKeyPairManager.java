/*    */ package net.minecraft.client.multiplayer;
/*    */ 
/*    */ import com.mojang.authlib.minecraft.UserApiService;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.client.User;
/*    */ import net.minecraft.world.entity.player.ProfileKeyPair;
/*    */ 
/*    */ public interface ProfileKeyPairManager
/*    */ {
/* 12 */   public static final ProfileKeyPairManager EMPTY_KEY_MANAGER = new ProfileKeyPairManager()
/*    */     {
/*    */       public CompletableFuture<Optional<ProfileKeyPair>> prepareKeyPair() {
/* 15 */         return CompletableFuture.completedFuture(Optional.empty());
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean shouldRefreshKeyPair() {
/* 20 */         return false;
/*    */       }
/*    */     };
/*    */   
/*    */   static ProfileKeyPairManager create(UserApiService $$0, User $$1, Path $$2) {
/* 25 */     if ($$1.getType() == User.Type.MSA) {
/* 26 */       return new AccountProfileKeyPairManager($$0, $$1.getProfileId(), $$2);
/*    */     }
/* 28 */     return EMPTY_KEY_MANAGER;
/*    */   }
/*    */   
/*    */   CompletableFuture<Optional<ProfileKeyPair>> prepareKeyPair();
/*    */   
/*    */   boolean shouldRefreshKeyPair();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\ProfileKeyPairManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */