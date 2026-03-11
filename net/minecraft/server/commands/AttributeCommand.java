/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.DoubleArgumentType;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.commands.arguments.ResourceArgument;
/*     */ import net.minecraft.commands.arguments.UuidArgument;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeInstance;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeMap;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ 
/*     */ public class AttributeCommand {
/*     */   private static final DynamicCommandExceptionType ERROR_NOT_LIVING_ENTITY;
/*     */   private static final Dynamic2CommandExceptionType ERROR_NO_SUCH_ATTRIBUTE;
/*     */   
/*     */   static {
/*  37 */     ERROR_NOT_LIVING_ENTITY = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.attribute.failed.entity", new Object[] { $$0 }));
/*  38 */     ERROR_NO_SUCH_ATTRIBUTE = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.attribute.failed.no_attribute", new Object[] { $$0, $$1 }));
/*  39 */     ERROR_NO_SUCH_MODIFIER = new Dynamic3CommandExceptionType(($$0, $$1, $$2) -> Component.translatableEscape("commands.attribute.failed.no_modifier", new Object[] { $$1, $$0, $$2 }));
/*  40 */     ERROR_MODIFIER_ALREADY_PRESENT = new Dynamic3CommandExceptionType(($$0, $$1, $$2) -> Component.translatableEscape("commands.attribute.failed.modifier_already_present", new Object[] { $$2, $$1, $$0 }));
/*     */   } private static final Dynamic3CommandExceptionType ERROR_NO_SUCH_MODIFIER; private static final Dynamic3CommandExceptionType ERROR_MODIFIER_ALREADY_PRESENT;
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/*  43 */     $$0.register(
/*  44 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("attribute")
/*  45 */         .requires($$0 -> $$0.hasPermission(2)))
/*  46 */         .then(
/*  47 */           Commands.argument("target", (ArgumentType)EntityArgument.entity())
/*  48 */           .then((
/*  49 */             (RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("attribute", (ArgumentType)ResourceArgument.resource($$1, Registries.ATTRIBUTE))
/*  50 */             .then((
/*  51 */               (LiteralArgumentBuilder)Commands.literal("get")
/*  52 */               .executes($$0 -> getAttributeValue((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), (Holder<Attribute>)ResourceArgument.getAttribute($$0, "attribute"), 1.0D)))
/*  53 */               .then(
/*  54 */                 Commands.argument("scale", (ArgumentType)DoubleArgumentType.doubleArg())
/*  55 */                 .executes($$0 -> getAttributeValue((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), (Holder<Attribute>)ResourceArgument.getAttribute($$0, "attribute"), DoubleArgumentType.getDouble($$0, "scale"))))))
/*     */ 
/*     */             
/*  58 */             .then((
/*  59 */               (LiteralArgumentBuilder)Commands.literal("base")
/*  60 */               .then(
/*  61 */                 Commands.literal("set")
/*  62 */                 .then(
/*  63 */                   Commands.argument("value", (ArgumentType)DoubleArgumentType.doubleArg())
/*  64 */                   .executes($$0 -> setAttributeBase((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), (Holder<Attribute>)ResourceArgument.getAttribute($$0, "attribute"), DoubleArgumentType.getDouble($$0, "value"))))))
/*     */ 
/*     */               
/*  67 */               .then((
/*  68 */                 (LiteralArgumentBuilder)Commands.literal("get")
/*  69 */                 .executes($$0 -> getAttributeBase((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), (Holder<Attribute>)ResourceArgument.getAttribute($$0, "attribute"), 1.0D)))
/*  70 */                 .then(
/*  71 */                   Commands.argument("scale", (ArgumentType)DoubleArgumentType.doubleArg())
/*  72 */                   .executes($$0 -> getAttributeBase((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), (Holder<Attribute>)ResourceArgument.getAttribute($$0, "attribute"), DoubleArgumentType.getDouble($$0, "scale")))))))
/*     */ 
/*     */ 
/*     */             
/*  76 */             .then((
/*  77 */               (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("modifier")
/*  78 */               .then(
/*  79 */                 Commands.literal("add")
/*  80 */                 .then(
/*  81 */                   Commands.argument("uuid", (ArgumentType)UuidArgument.uuid())
/*  82 */                   .then(
/*  83 */                     Commands.argument("name", (ArgumentType)StringArgumentType.string())
/*  84 */                     .then((
/*  85 */                       (RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("value", (ArgumentType)DoubleArgumentType.doubleArg())
/*  86 */                       .then(
/*  87 */                         Commands.literal("add")
/*  88 */                         .executes($$0 -> addModifier((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), (Holder<Attribute>)ResourceArgument.getAttribute($$0, "attribute"), UuidArgument.getUuid($$0, "uuid"), StringArgumentType.getString($$0, "name"), DoubleArgumentType.getDouble($$0, "value"), AttributeModifier.Operation.ADDITION))))
/*     */                       
/*  90 */                       .then(
/*  91 */                         Commands.literal("multiply")
/*  92 */                         .executes($$0 -> addModifier((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), (Holder<Attribute>)ResourceArgument.getAttribute($$0, "attribute"), UuidArgument.getUuid($$0, "uuid"), StringArgumentType.getString($$0, "name"), DoubleArgumentType.getDouble($$0, "value"), AttributeModifier.Operation.MULTIPLY_TOTAL))))
/*     */                       
/*  94 */                       .then(
/*  95 */                         Commands.literal("multiply_base")
/*  96 */                         .executes($$0 -> addModifier((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), (Holder<Attribute>)ResourceArgument.getAttribute($$0, "attribute"), UuidArgument.getUuid($$0, "uuid"), StringArgumentType.getString($$0, "name"), DoubleArgumentType.getDouble($$0, "value"), AttributeModifier.Operation.MULTIPLY_BASE))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 102 */               .then(
/* 103 */                 Commands.literal("remove")
/* 104 */                 .then(Commands.argument("uuid", (ArgumentType)UuidArgument.uuid())
/* 105 */                   .executes($$0 -> removeModifier((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), (Holder<Attribute>)ResourceArgument.getAttribute($$0, "attribute"), UuidArgument.getUuid($$0, "uuid"))))))
/*     */ 
/*     */               
/* 108 */               .then(
/* 109 */                 Commands.literal("value")
/* 110 */                 .then(
/* 111 */                   Commands.literal("get")
/* 112 */                   .then((
/* 113 */                     (RequiredArgumentBuilder)Commands.argument("uuid", (ArgumentType)UuidArgument.uuid())
/* 114 */                     .executes($$0 -> getAttributeModifier((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), (Holder<Attribute>)ResourceArgument.getAttribute($$0, "attribute"), UuidArgument.getUuid($$0, "uuid"), 1.0D)))
/* 115 */                     .then(
/* 116 */                       Commands.argument("scale", (ArgumentType)DoubleArgumentType.doubleArg())
/* 117 */                       .executes($$0 -> getAttributeModifier((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), (Holder<Attribute>)ResourceArgument.getAttribute($$0, "attribute"), UuidArgument.getUuid($$0, "uuid"), DoubleArgumentType.getDouble($$0, "scale")))))))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AttributeInstance getAttributeInstance(Entity $$0, Holder<Attribute> $$1) throws CommandSyntaxException {
/* 129 */     AttributeInstance $$2 = getLivingEntity($$0).getAttributes().getInstance($$1);
/* 130 */     if ($$2 == null) {
/* 131 */       throw ERROR_NO_SUCH_ATTRIBUTE.create($$0.getName(), getAttributeDescription($$1));
/*     */     }
/* 133 */     return $$2;
/*     */   }
/*     */   
/*     */   private static LivingEntity getLivingEntity(Entity $$0) throws CommandSyntaxException {
/* 137 */     if (!($$0 instanceof LivingEntity)) {
/* 138 */       throw ERROR_NOT_LIVING_ENTITY.create($$0.getName());
/*     */     }
/* 140 */     return (LivingEntity)$$0;
/*     */   }
/*     */   
/*     */   private static LivingEntity getEntityWithAttribute(Entity $$0, Holder<Attribute> $$1) throws CommandSyntaxException {
/* 144 */     LivingEntity $$2 = getLivingEntity($$0);
/* 145 */     if (!$$2.getAttributes().hasAttribute($$1)) {
/* 146 */       throw ERROR_NO_SUCH_ATTRIBUTE.create($$0.getName(), getAttributeDescription($$1));
/*     */     }
/* 148 */     return $$2;
/*     */   }
/*     */   
/*     */   private static int getAttributeValue(CommandSourceStack $$0, Entity $$1, Holder<Attribute> $$2, double $$3) throws CommandSyntaxException {
/* 152 */     LivingEntity $$4 = getEntityWithAttribute($$1, $$2);
/* 153 */     double $$5 = $$4.getAttributeValue($$2);
/* 154 */     $$0.sendSuccess(() -> Component.translatable("commands.attribute.value.get.success", new Object[] { getAttributeDescription($$0), $$1.getName(), Double.valueOf($$2) }), false);
/* 155 */     return (int)($$5 * $$3);
/*     */   }
/*     */   
/*     */   private static int getAttributeBase(CommandSourceStack $$0, Entity $$1, Holder<Attribute> $$2, double $$3) throws CommandSyntaxException {
/* 159 */     LivingEntity $$4 = getEntityWithAttribute($$1, $$2);
/* 160 */     double $$5 = $$4.getAttributeBaseValue($$2);
/* 161 */     $$0.sendSuccess(() -> Component.translatable("commands.attribute.base_value.get.success", new Object[] { getAttributeDescription($$0), $$1.getName(), Double.valueOf($$2) }), false);
/* 162 */     return (int)($$5 * $$3);
/*     */   }
/*     */   
/*     */   private static int getAttributeModifier(CommandSourceStack $$0, Entity $$1, Holder<Attribute> $$2, UUID $$3, double $$4) throws CommandSyntaxException {
/* 166 */     LivingEntity $$5 = getEntityWithAttribute($$1, $$2);
/*     */     
/* 168 */     AttributeMap $$6 = $$5.getAttributes();
/*     */     
/* 170 */     if (!$$6.hasModifier($$2, $$3)) {
/* 171 */       throw ERROR_NO_SUCH_MODIFIER.create($$1.getName(), getAttributeDescription($$2), $$3);
/*     */     }
/*     */     
/* 174 */     double $$7 = $$6.getModifierValue($$2, $$3);
/* 175 */     $$0.sendSuccess(() -> Component.translatable("commands.attribute.modifier.value.get.success", new Object[] { Component.translationArg($$0), getAttributeDescription($$1), $$2.getName(), Double.valueOf($$3) }), false);
/* 176 */     return (int)($$7 * $$4);
/*     */   }
/*     */   
/*     */   private static int setAttributeBase(CommandSourceStack $$0, Entity $$1, Holder<Attribute> $$2, double $$3) throws CommandSyntaxException {
/* 180 */     getAttributeInstance($$1, $$2).setBaseValue($$3);
/* 181 */     $$0.sendSuccess(() -> Component.translatable("commands.attribute.base_value.set.success", new Object[] { getAttributeDescription($$0), $$1.getName(), Double.valueOf($$2) }), false);
/* 182 */     return 1;
/*     */   }
/*     */   
/*     */   private static int addModifier(CommandSourceStack $$0, Entity $$1, Holder<Attribute> $$2, UUID $$3, String $$4, double $$5, AttributeModifier.Operation $$6) throws CommandSyntaxException {
/* 186 */     AttributeInstance $$7 = getAttributeInstance($$1, $$2);
/* 187 */     AttributeModifier $$8 = new AttributeModifier($$3, $$4, $$5, $$6);
/* 188 */     if ($$7.hasModifier($$8)) {
/* 189 */       throw ERROR_MODIFIER_ALREADY_PRESENT.create($$1.getName(), getAttributeDescription($$2), $$3);
/*     */     }
/* 191 */     $$7.addPermanentModifier($$8);
/* 192 */     $$0.sendSuccess(() -> Component.translatable("commands.attribute.modifier.add.success", new Object[] { Component.translationArg($$0), getAttributeDescription($$1), $$2.getName() }), false);
/* 193 */     return 1;
/*     */   }
/*     */   
/*     */   private static int removeModifier(CommandSourceStack $$0, Entity $$1, Holder<Attribute> $$2, UUID $$3) throws CommandSyntaxException {
/* 197 */     AttributeInstance $$4 = getAttributeInstance($$1, $$2);
/* 198 */     if ($$4.removePermanentModifier($$3)) {
/* 199 */       $$0.sendSuccess(() -> Component.translatable("commands.attribute.modifier.remove.success", new Object[] { Component.translationArg($$0), getAttributeDescription($$1), $$2.getName() }), false);
/* 200 */       return 1;
/*     */     } 
/* 202 */     throw ERROR_NO_SUCH_MODIFIER.create($$1.getName(), getAttributeDescription($$2), $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Component getAttributeDescription(Holder<Attribute> $$0) {
/* 207 */     return (Component)Component.translatable(((Attribute)$$0.value()).getDescriptionId());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\AttributeCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */