/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.commands.arguments.ResourceArgument;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.enchantment.Enchantment;
/*    */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*    */ 
/*    */ public class EnchantCommand {
/*    */   private static final DynamicCommandExceptionType ERROR_NOT_LIVING_ENTITY;
/*    */   private static final DynamicCommandExceptionType ERROR_NO_ITEM;
/*    */   
/*    */   static {
/* 32 */     ERROR_NOT_LIVING_ENTITY = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.enchant.failed.entity", new Object[] { $$0 }));
/* 33 */     ERROR_NO_ITEM = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.enchant.failed.itemless", new Object[] { $$0 }));
/* 34 */     ERROR_INCOMPATIBLE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.enchant.failed.incompatible", new Object[] { $$0 }));
/* 35 */     ERROR_LEVEL_TOO_HIGH = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.enchant.failed.level", new Object[] { $$0, $$1 }));
/* 36 */   } private static final DynamicCommandExceptionType ERROR_INCOMPATIBLE; private static final Dynamic2CommandExceptionType ERROR_LEVEL_TOO_HIGH; private static final SimpleCommandExceptionType ERROR_NOTHING_HAPPENED = new SimpleCommandExceptionType((Message)Component.translatable("commands.enchant.failed"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/* 39 */     $$0.register(
/* 40 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("enchant")
/* 41 */         .requires($$0 -> $$0.hasPermission(2)))
/* 42 */         .then(
/* 43 */           Commands.argument("targets", (ArgumentType)EntityArgument.entities())
/* 44 */           .then((
/* 45 */             (RequiredArgumentBuilder)Commands.argument("enchantment", (ArgumentType)ResourceArgument.resource($$1, Registries.ENCHANTMENT))
/* 46 */             .executes($$0 -> enchant((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), (Holder<Enchantment>)ResourceArgument.getEnchantment($$0, "enchantment"), 1)))
/* 47 */             .then(
/* 48 */               Commands.argument("level", (ArgumentType)IntegerArgumentType.integer(0))
/* 49 */               .executes($$0 -> enchant((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), (Holder<Enchantment>)ResourceArgument.getEnchantment($$0, "enchantment"), IntegerArgumentType.getInteger($$0, "level")))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int enchant(CommandSourceStack $$0, Collection<? extends Entity> $$1, Holder<Enchantment> $$2, int $$3) throws CommandSyntaxException {
/* 57 */     Enchantment $$4 = (Enchantment)$$2.value();
/* 58 */     if ($$3 > $$4.getMaxLevel()) {
/* 59 */       throw ERROR_LEVEL_TOO_HIGH.create(Integer.valueOf($$3), Integer.valueOf($$4.getMaxLevel()));
/*    */     }
/*    */     
/* 62 */     int $$5 = 0;
/*    */     
/* 64 */     for (Entity $$6 : $$1) {
/* 65 */       if ($$6 instanceof LivingEntity) { LivingEntity $$7 = (LivingEntity)$$6;
/* 66 */         ItemStack $$8 = $$7.getMainHandItem();
/* 67 */         if (!$$8.isEmpty()) {
/* 68 */           if ($$4.canEnchant($$8) && EnchantmentHelper.isEnchantmentCompatible(EnchantmentHelper.getEnchantments($$8).keySet(), $$4)) {
/* 69 */             $$8.enchant($$4, $$3);
/* 70 */             $$5++; continue;
/* 71 */           }  if ($$1.size() == 1)
/* 72 */             throw ERROR_INCOMPATIBLE.create($$8.getItem().getName($$8).getString());  continue;
/*    */         } 
/* 74 */         if ($$1.size() == 1)
/* 75 */           throw ERROR_NO_ITEM.create($$7.getName().getString());  continue; }
/*    */       
/* 77 */       if ($$1.size() == 1) {
/* 78 */         throw ERROR_NOT_LIVING_ENTITY.create($$6.getName().getString());
/*    */       }
/*    */     } 
/*    */     
/* 82 */     if ($$5 == 0)
/* 83 */       throw ERROR_NOTHING_HAPPENED.create(); 
/* 84 */     if ($$1.size() == 1) {
/* 85 */       $$0.sendSuccess(() -> Component.translatable("commands.enchant.success.single", new Object[] { $$0.getFullname($$1), ((Entity)$$2.iterator().next()).getDisplayName() }), true);
/*    */     } else {
/* 87 */       $$0.sendSuccess(() -> Component.translatable("commands.enchant.success.multiple", new Object[] { $$0.getFullname($$1), Integer.valueOf($$2.size()) }), true);
/*    */     } 
/*    */     
/* 90 */     return $$5;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\EnchantCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */