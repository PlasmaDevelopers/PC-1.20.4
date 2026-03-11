/*    */ package net.minecraft.server.commands.data;
/*    */ 
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import java.util.Locale;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.advancements.critereon.NbtPredicate;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.commands.arguments.NbtPathArgument;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtUtils;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ 
/*    */ public class EntityDataAccessor
/*    */   implements DataAccessor
/*    */ {
/* 27 */   private static final SimpleCommandExceptionType ERROR_NO_PLAYERS = new SimpleCommandExceptionType((Message)Component.translatable("commands.data.entity.invalid"));
/*    */   
/*    */   public static final Function<String, DataCommands.DataProvider> PROVIDER = $$0 -> new DataCommands.DataProvider()
/*    */     {
/*    */       public DataAccessor access(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException {
/* 32 */         return new EntityDataAccessor(EntityArgument.getEntity($$0, arg));
/*    */       }
/*    */ 
/*    */       
/*    */       public ArgumentBuilder<CommandSourceStack, ?> wrap(ArgumentBuilder<CommandSourceStack, ?> $$0, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> $$1) {
/* 37 */         return $$0.then(Commands.literal("entity").then($$1.apply(Commands.argument(arg, (ArgumentType)EntityArgument.entity()))));
/*    */       }
/*    */     };
/*    */   
/*    */   private final Entity entity;
/*    */   
/*    */   public EntityDataAccessor(Entity $$0) {
/* 44 */     this.entity = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setData(CompoundTag $$0) throws CommandSyntaxException {
/* 49 */     if (this.entity instanceof net.minecraft.world.entity.player.Player) {
/* 50 */       throw ERROR_NO_PLAYERS.create();
/*    */     }
/* 52 */     UUID $$1 = this.entity.getUUID();
/* 53 */     this.entity.load($$0);
/* 54 */     this.entity.setUUID($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag getData() {
/* 59 */     return NbtPredicate.getEntityTagToCompare(this.entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getModifiedSuccess() {
/* 64 */     return (Component)Component.translatable("commands.data.entity.modified", new Object[] { this.entity.getDisplayName() });
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getPrintSuccess(Tag $$0) {
/* 69 */     return (Component)Component.translatable("commands.data.entity.query", new Object[] { this.entity.getDisplayName(), NbtUtils.toPrettyComponent($$0) });
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getPrintSuccess(NbtPathArgument.NbtPath $$0, double $$1, int $$2) {
/* 74 */     return (Component)Component.translatable("commands.data.entity.get", new Object[] { $$0.asString(), this.entity.getDisplayName(), String.format(Locale.ROOT, "%.2f", new Object[] { Double.valueOf($$1) }), Integer.valueOf($$2) });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\data\EntityDataAccessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */