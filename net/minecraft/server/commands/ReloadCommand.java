/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.packs.repository.PackRepository;
/*    */ import net.minecraft.world.level.storage.WorldData;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class ReloadCommand {
/* 19 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public static void reloadPacks(Collection<String> $$0, CommandSourceStack $$1) {
/* 22 */     $$1.getServer().reloadResources($$0).exceptionally($$1 -> {
/*    */           LOGGER.warn("Failed to execute reload", $$1);
/*    */           $$0.sendFailure((Component)Component.translatable("commands.reload.failure"));
/*    */           return null;
/*    */         });
/*    */   }
/*    */   
/*    */   private static Collection<String> discoverNewPacks(PackRepository $$0, WorldData $$1, Collection<String> $$2) {
/* 30 */     $$0.reload();
/* 31 */     Collection<String> $$3 = Lists.newArrayList($$2);
/* 32 */     Collection<String> $$4 = $$1.getDataConfiguration().dataPacks().getDisabled();
/*    */     
/* 34 */     for (String $$5 : $$0.getAvailableIds()) {
/* 35 */       if (!$$4.contains($$5) && !$$3.contains($$5)) {
/* 36 */         $$3.add($$5);
/*    */       }
/*    */     } 
/* 39 */     return $$3;
/*    */   }
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 43 */     $$0.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("reload")
/* 44 */         .requires($$0 -> $$0.hasPermission(2)))
/* 45 */         .executes($$0 -> {
/*    */             CommandSourceStack $$1 = (CommandSourceStack)$$0.getSource();
/*    */             MinecraftServer $$2 = $$1.getServer();
/*    */             PackRepository $$3 = $$2.getPackRepository();
/*    */             WorldData $$4 = $$2.getWorldData();
/*    */             Collection<String> $$5 = $$3.getSelectedIds();
/*    */             Collection<String> $$6 = discoverNewPacks($$3, $$4, $$5);
/*    */             $$1.sendSuccess((), true);
/*    */             reloadPacks($$6, $$1);
/*    */             return 0;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ReloadCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */