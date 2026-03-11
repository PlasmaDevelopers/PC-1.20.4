/*    */ package net.minecraft.network.chat.contents;
/*    */ 
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.arguments.selector.EntitySelector;
/*    */ import net.minecraft.commands.arguments.selector.EntitySelectorParser;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public final class EntityDataSource extends Record implements DataSource {
/*    */   private final String selectorPattern;
/*    */   
/* 19 */   public EntityDataSource(String $$0, @Nullable EntitySelector $$1) { this.selectorPattern = $$0; this.compiledSelector = $$1; } @Nullable private final EntitySelector compiledSelector; public static final MapCodec<EntityDataSource> SUB_CODEC; public String selectorPattern() { return this.selectorPattern; } @Nullable public EntitySelector compiledSelector() { return this.compiledSelector; } static {
/* 20 */     SUB_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.STRING.fieldOf("entity").forGetter(EntityDataSource::selectorPattern)).apply((Applicative)$$0, EntityDataSource::new));
/*    */   }
/*    */ 
/*    */   
/* 24 */   public static final DataSource.Type<EntityDataSource> TYPE = new DataSource.Type<>(SUB_CODEC, "entity");
/*    */   
/*    */   public EntityDataSource(String $$0) {
/* 27 */     this($$0, compileSelector($$0));
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private static EntitySelector compileSelector(String $$0) {
/*    */     try {
/* 33 */       EntitySelectorParser $$1 = new EntitySelectorParser(new StringReader($$0));
/* 34 */       return $$1.parse();
/* 35 */     } catch (CommandSyntaxException $$2) {
/* 36 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<CompoundTag> getData(CommandSourceStack $$0) throws CommandSyntaxException {
/* 42 */     if (this.compiledSelector != null) {
/* 43 */       List<? extends Entity> $$1 = this.compiledSelector.findEntities($$0);
/* 44 */       return $$1.stream().map(NbtPredicate::getEntityTagToCompare);
/*    */     } 
/*    */     
/* 47 */     return Stream.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public DataSource.Type<?> type() {
/* 52 */     return TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return "entity=" + this.selectorPattern;
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
/*    */     //   8: instanceof net/minecraft/network/chat/contents/EntityDataSource
/*    */     //   11: ifeq -> 37
/*    */     //   14: aload_1
/*    */     //   15: checkcast net/minecraft/network/chat/contents/EntityDataSource
/*    */     //   18: astore_2
/*    */     //   19: aload_0
/*    */     //   20: getfield selectorPattern : Ljava/lang/String;
/*    */     //   23: aload_2
/*    */     //   24: getfield selectorPattern : Ljava/lang/String;
/*    */     //   27: invokevirtual equals : (Ljava/lang/Object;)Z
/*    */     //   30: ifeq -> 37
/*    */     //   33: iconst_1
/*    */     //   34: goto -> 38
/*    */     //   37: iconst_0
/*    */     //   38: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #62	-> 0
/*    */     //   #63	-> 5
/*    */     //   #66	-> 7
/*    */     //   #65	-> 14
/*    */     //   #66	-> 27
/*    */     //   #65	-> 38
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	39	0	this	Lnet/minecraft/network/chat/contents/EntityDataSource;
/*    */     //   0	39	1	$$0	Ljava/lang/Object;
/*    */     //   19	18	2	$$1	Lnet/minecraft/network/chat/contents/EntityDataSource;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 71 */     return this.selectorPattern.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\contents\EntityDataSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */