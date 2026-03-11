/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.arguments.selector.EntitySelector;
/*    */ import net.minecraft.server.level.ServerPlayer;
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
/*    */ public class SelectorResult
/*    */   implements GameProfileArgument.Result
/*    */ {
/*    */   private final EntitySelector selector;
/*    */   
/*    */   public SelectorResult(EntitySelector $$0) {
/* 69 */     this.selector = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<GameProfile> getNames(CommandSourceStack $$0) throws CommandSyntaxException {
/* 74 */     List<ServerPlayer> $$1 = this.selector.findPlayers($$0);
/* 75 */     if ($$1.isEmpty()) {
/* 76 */       throw EntityArgument.NO_PLAYERS_FOUND.create();
/*    */     }
/* 78 */     List<GameProfile> $$2 = Lists.newArrayList();
/* 79 */     for (ServerPlayer $$3 : $$1) {
/* 80 */       $$2.add($$3.getGameProfile());
/*    */     }
/* 82 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\GameProfileArgument$SelectorResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */