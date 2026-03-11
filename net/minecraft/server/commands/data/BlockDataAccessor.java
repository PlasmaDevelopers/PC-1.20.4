/*    */ package net.minecraft.server.commands.data;
/*    */ 
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import java.util.Locale;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.NbtPathArgument;
/*    */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtUtils;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ 
/*    */ public class BlockDataAccessor
/*    */   implements DataAccessor
/*    */ {
/* 27 */   static final SimpleCommandExceptionType ERROR_NOT_A_BLOCK_ENTITY = new SimpleCommandExceptionType((Message)Component.translatable("commands.data.block.invalid"));
/*    */   
/*    */   public static final Function<String, DataCommands.DataProvider> PROVIDER = $$0 -> new DataCommands.DataProvider()
/*    */     {
/*    */       public DataAccessor access(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException {
/* 32 */         BlockPos $$1 = BlockPosArgument.getLoadedBlockPos($$0, argPrefix + "Pos");
/* 33 */         BlockEntity $$2 = ((CommandSourceStack)$$0.getSource()).getLevel().getBlockEntity($$1);
/* 34 */         if ($$2 == null) {
/* 35 */           throw BlockDataAccessor.ERROR_NOT_A_BLOCK_ENTITY.create();
/*    */         }
/* 37 */         return new BlockDataAccessor($$2, $$1);
/*    */       }
/*    */ 
/*    */       
/*    */       public ArgumentBuilder<CommandSourceStack, ?> wrap(ArgumentBuilder<CommandSourceStack, ?> $$0, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> $$1) {
/* 42 */         return $$0.then(Commands.literal("block").then($$1.apply(Commands.argument(argPrefix + "Pos", (ArgumentType)BlockPosArgument.blockPos()))));
/*    */       }
/*    */     };
/*    */   
/*    */   private final BlockEntity entity;
/*    */   private final BlockPos pos;
/*    */   
/*    */   public BlockDataAccessor(BlockEntity $$0, BlockPos $$1) {
/* 50 */     this.entity = $$0;
/* 51 */     this.pos = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setData(CompoundTag $$0) {
/* 56 */     BlockState $$1 = this.entity.getLevel().getBlockState(this.pos);
/* 57 */     this.entity.load($$0);
/* 58 */     this.entity.setChanged();
/* 59 */     this.entity.getLevel().sendBlockUpdated(this.pos, $$1, $$1, 3);
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag getData() {
/* 64 */     return this.entity.saveWithFullMetadata();
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getModifiedSuccess() {
/* 69 */     return (Component)Component.translatable("commands.data.block.modified", new Object[] { Integer.valueOf(this.pos.getX()), Integer.valueOf(this.pos.getY()), Integer.valueOf(this.pos.getZ()) });
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getPrintSuccess(Tag $$0) {
/* 74 */     return (Component)Component.translatable("commands.data.block.query", new Object[] { Integer.valueOf(this.pos.getX()), Integer.valueOf(this.pos.getY()), Integer.valueOf(this.pos.getZ()), NbtUtils.toPrettyComponent($$0) });
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getPrintSuccess(NbtPathArgument.NbtPath $$0, double $$1, int $$2) {
/* 79 */     return (Component)Component.translatable("commands.data.block.get", new Object[] { $$0.asString(), Integer.valueOf(this.pos.getX()), Integer.valueOf(this.pos.getY()), Integer.valueOf(this.pos.getZ()), String.format(Locale.ROOT, "%.2f", new Object[] { Double.valueOf($$1) }), Integer.valueOf($$2) });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\data\BlockDataAccessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */