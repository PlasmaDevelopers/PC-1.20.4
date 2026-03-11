/*    */ package net.minecraft.network.chat.contents;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.arguments.coordinates.Coordinates;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ 
/*    */ public final class BlockDataSource extends Record implements DataSource {
/*    */   private final String posPattern;
/*    */   
/* 19 */   public BlockDataSource(String $$0, @Nullable Coordinates $$1) { this.posPattern = $$0; this.compiledPos = $$1; } @Nullable private final Coordinates compiledPos; public static final MapCodec<BlockDataSource> SUB_CODEC; public String posPattern() { return this.posPattern; } @Nullable public Coordinates compiledPos() { return this.compiledPos; } static {
/* 20 */     SUB_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.STRING.fieldOf("block").forGetter(BlockDataSource::posPattern)).apply((Applicative)$$0, BlockDataSource::new));
/*    */   }
/*    */ 
/*    */   
/* 24 */   public static final DataSource.Type<BlockDataSource> TYPE = new DataSource.Type<>(SUB_CODEC, "block");
/*    */   
/*    */   public BlockDataSource(String $$0) {
/* 27 */     this($$0, compilePos($$0));
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private static Coordinates compilePos(String $$0) {
/*    */     try {
/* 33 */       return BlockPosArgument.blockPos().parse(new StringReader($$0));
/* 34 */     } catch (CommandSyntaxException $$1) {
/* 35 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<CompoundTag> getData(CommandSourceStack $$0) {
/* 41 */     if (this.compiledPos != null) {
/* 42 */       ServerLevel $$1 = $$0.getLevel();
/* 43 */       BlockPos $$2 = this.compiledPos.getBlockPos($$0);
/* 44 */       if ($$1.isLoaded($$2)) {
/* 45 */         BlockEntity $$3 = $$1.getBlockEntity($$2);
/*    */         
/* 47 */         if ($$3 != null) {
/* 48 */           return Stream.of($$3.saveWithFullMetadata());
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 53 */     return Stream.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public DataSource.Type<?> type() {
/* 58 */     return TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 63 */     return "block=" + this.posPattern;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: if_acmpne -> 7
/*    */     //   5: iconst_1
/*    */     //   6: ireturn
/*    */     //   7: aload_1
/*    */     //   8: instanceof net/minecraft/network/chat/contents/BlockDataSource
/*    */     //   11: ifeq -> 37
/*    */     //   14: aload_1
/*    */     //   15: checkcast net/minecraft/network/chat/contents/BlockDataSource
/*    */     //   18: astore_2
/*    */     //   19: aload_0
/*    */     //   20: getfield posPattern : Ljava/lang/String;
/*    */     //   23: aload_2
/*    */     //   24: getfield posPattern : Ljava/lang/String;
/*    */     //   27: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   30: ifeq -> 37
/*    */     //   33: iconst_1
/*    */     //   34: goto -> 38
/*    */     //   37: iconst_0
/*    */     //   38: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #68	-> 0
/*    */     //   #69	-> 5
/*    */     //   #72	-> 7
/*    */     //   #71	-> 14
/*    */     //   #72	-> 27
/*    */     //   #71	-> 38
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	39	0	this	Lnet/minecraft/network/chat/contents/BlockDataSource;
/*    */     //   0	39	1	$$0	Ljava/lang/Object;
/*    */     //   19	18	2	$$1	Lnet/minecraft/network/chat/contents/BlockDataSource;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 77 */     return this.posPattern.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\contents\BlockDataSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */