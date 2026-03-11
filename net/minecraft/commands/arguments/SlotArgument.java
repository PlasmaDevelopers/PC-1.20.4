/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import com.mojang.brigadier.suggestion.Suggestions;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ 
/*    */ 
/*    */ public class SlotArgument
/*    */   implements ArgumentType<Integer>
/*    */ {
/* 27 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "container.5", "12", "weapon" }); static {
/* 28 */     ERROR_UNKNOWN_SLOT = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("slot.unknown", new Object[] { $$0 }));
/* 29 */     SLOTS = (Map<String, Integer>)Util.make(Maps.newHashMap(), $$0 -> {
/*    */           for (int $$1 = 0; $$1 < 54; $$1++) {
/*    */             $$0.put("container." + $$1, Integer.valueOf($$1));
/*    */           }
/*    */           for (int $$2 = 0; $$2 < 9; $$2++) {
/*    */             $$0.put("hotbar." + $$2, Integer.valueOf($$2));
/*    */           }
/*    */           for (int $$3 = 0; $$3 < 27; $$3++)
/*    */             $$0.put("inventory." + $$3, Integer.valueOf(9 + $$3)); 
/*    */           for (int $$4 = 0; $$4 < 27; $$4++)
/*    */             $$0.put("enderchest." + $$4, Integer.valueOf(200 + $$4)); 
/*    */           for (int $$5 = 0; $$5 < 8; $$5++)
/*    */             $$0.put("villager." + $$5, Integer.valueOf(300 + $$5)); 
/*    */           for (int $$6 = 0; $$6 < 15; $$6++)
/*    */             $$0.put("horse." + $$6, Integer.valueOf(500 + $$6)); 
/*    */           $$0.put("weapon", Integer.valueOf(EquipmentSlot.MAINHAND.getIndex(98)));
/*    */           $$0.put("weapon.mainhand", Integer.valueOf(EquipmentSlot.MAINHAND.getIndex(98)));
/*    */           $$0.put("weapon.offhand", Integer.valueOf(EquipmentSlot.OFFHAND.getIndex(98)));
/*    */           $$0.put("armor.head", Integer.valueOf(EquipmentSlot.HEAD.getIndex(100)));
/*    */           $$0.put("armor.chest", Integer.valueOf(EquipmentSlot.CHEST.getIndex(100)));
/*    */           $$0.put("armor.legs", Integer.valueOf(EquipmentSlot.LEGS.getIndex(100)));
/*    */           $$0.put("armor.feet", Integer.valueOf(EquipmentSlot.FEET.getIndex(100)));
/*    */           $$0.put("horse.saddle", Integer.valueOf(400));
/*    */           $$0.put("horse.armor", Integer.valueOf(401));
/*    */           $$0.put("horse.chest", Integer.valueOf(499));
/*    */         });
/*    */   }
/*    */   
/*    */   private static final DynamicCommandExceptionType ERROR_UNKNOWN_SLOT;
/*    */   private static final Map<String, Integer> SLOTS;
/*    */   
/*    */   public static SlotArgument slot() {
/* 61 */     return new SlotArgument();
/*    */   }
/*    */   
/*    */   public static int getSlot(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 65 */     return ((Integer)$$0.getArgument($$1, Integer.class)).intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public Integer parse(StringReader $$0) throws CommandSyntaxException {
/* 70 */     String $$1 = $$0.readUnquotedString();
/* 71 */     if (!SLOTS.containsKey($$1)) {
/* 72 */       throw ERROR_UNKNOWN_SLOT.create($$1);
/*    */     }
/* 74 */     return SLOTS.get($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 79 */     return SharedSuggestionProvider.suggest(SLOTS.keySet(), $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 84 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\SlotArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */