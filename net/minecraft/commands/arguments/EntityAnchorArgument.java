/*    */ package net.minecraft.commands.arguments;
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
/*    */ import java.util.function.BiFunction;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class EntityAnchorArgument implements ArgumentType<EntityAnchorArgument.Anchor> {
/* 26 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "eyes", "feet" }); private static final DynamicCommandExceptionType ERROR_INVALID; static {
/* 27 */     ERROR_INVALID = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.anchor.invalid", new Object[] { $$0 }));
/*    */   }
/*    */   public static Anchor getAnchor(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 30 */     return (Anchor)$$0.getArgument($$1, Anchor.class);
/*    */   }
/*    */   
/*    */   public static EntityAnchorArgument anchor() {
/* 34 */     return new EntityAnchorArgument();
/*    */   }
/*    */ 
/*    */   
/*    */   public Anchor parse(StringReader $$0) throws CommandSyntaxException {
/* 39 */     int $$1 = $$0.getCursor();
/* 40 */     String $$2 = $$0.readUnquotedString();
/* 41 */     Anchor $$3 = Anchor.getByName($$2);
/* 42 */     if ($$3 == null) {
/* 43 */       $$0.setCursor($$1);
/* 44 */       throw ERROR_INVALID.createWithContext($$0, $$2);
/*    */     } 
/* 46 */     return $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 51 */     return SharedSuggestionProvider.suggest(Anchor.BY_NAME.keySet(), $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 56 */     return EXAMPLES;
/*    */   }
/*    */   public enum Anchor { FEET, EYES; static final Map<String, Anchor> BY_NAME;
/*    */     static {
/* 60 */       FEET = new Anchor("FEET", 0, "feet", ($$0, $$1) -> $$0);
/* 61 */       EYES = new Anchor("EYES", 1, "eyes", ($$0, $$1) -> new Vec3($$0.x, $$0.y + $$1.getEyeHeight(), $$0.z));
/*    */     } private final String name; private final BiFunction<Vec3, Entity, Vec3> transform;
/*    */     static {
/* 64 */       BY_NAME = (Map<String, Anchor>)Util.make(Maps.newHashMap(), $$0 -> {
/*    */             for (Anchor $$1 : values()) {
/*    */               $$0.put($$1.name, $$1);
/*    */             }
/*    */           });
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     Anchor(String $$0, BiFunction<Vec3, Entity, Vec3> $$1) {
/* 74 */       this.name = $$0;
/* 75 */       this.transform = $$1;
/*    */     }
/*    */     
/*    */     @Nullable
/*    */     public static Anchor getByName(String $$0) {
/* 80 */       return BY_NAME.get($$0);
/*    */     }
/*    */     
/*    */     public Vec3 apply(Entity $$0) {
/* 84 */       return this.transform.apply($$0.position(), $$0);
/*    */     }
/*    */     
/*    */     public Vec3 apply(CommandSourceStack $$0) {
/* 88 */       Entity $$1 = $$0.getEntity();
/* 89 */       if ($$1 == null) {
/* 90 */         return $$0.getPosition();
/*    */       }
/* 92 */       return this.transform.apply($$0.getPosition(), $$1);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\EntityAnchorArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */