/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ 
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.commands.arguments.NbtPathArgument;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.providers.nbt.NbtProvider;
/*     */ 
/*     */ public class CopyNbtFunction extends LootItemConditionalFunction {
/*     */   public static final Codec<CopyNbtFunction> CODEC;
/*     */   private final NbtProvider source;
/*     */   private final List<CopyOperation> operations;
/*     */   
/*     */   private static final class Path extends Record { private final String string;
/*     */     private final NbtPathArgument.NbtPath path;
/*     */     public static final Codec<Path> CODEC;
/*     */     
/*  28 */     private Path(String $$0, NbtPathArgument.NbtPath $$1) { this.string = $$0; this.path = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$Path;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #28	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  28 */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$Path; } public String string() { return this.string; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$Path;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #28	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$Path; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$Path;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #28	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$Path;
/*  28 */       //   0	8	1	$$0	Ljava/lang/Object; } public NbtPathArgument.NbtPath path() { return this.path; } static {
/*  29 */       CODEC = Codec.STRING.comapFlatMap($$0 -> {
/*     */             
/*     */             try {
/*     */               return DataResult.success(of($$0));
/*  33 */             } catch (CommandSyntaxException $$1) {
/*     */               return DataResult.error(());
/*     */             } 
/*     */           }Path::string);
/*     */     }
/*     */ 
/*     */     
/*     */     public static Path of(String $$0) throws CommandSyntaxException {
/*  41 */       NbtPathArgument.NbtPath $$1 = (new NbtPathArgument()).parse(new StringReader($$0));
/*  42 */       return new Path($$0, $$1);
/*     */     } }
/*     */   private static final class CopyOperation extends Record { private final CopyNbtFunction.Path sourcePath; private final CopyNbtFunction.Path targetPath; private final CopyNbtFunction.MergeStrategy op; public static final Codec<CopyOperation> CODEC;
/*     */     
/*  46 */     CopyOperation(CopyNbtFunction.Path $$0, CopyNbtFunction.Path $$1, CopyNbtFunction.MergeStrategy $$2) { this.sourcePath = $$0; this.targetPath = $$1; this.op = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$CopyOperation;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #46	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$CopyOperation; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$CopyOperation;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #46	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$CopyOperation; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$CopyOperation;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #46	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/functions/CopyNbtFunction$CopyOperation;
/*  46 */       //   0	8	1	$$0	Ljava/lang/Object; } public CopyNbtFunction.Path sourcePath() { return this.sourcePath; } public CopyNbtFunction.Path targetPath() { return this.targetPath; } public CopyNbtFunction.MergeStrategy op() { return this.op; } static {
/*  47 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)CopyNbtFunction.Path.CODEC.fieldOf("source").forGetter(CopyOperation::sourcePath), (App)CopyNbtFunction.Path.CODEC.fieldOf("target").forGetter(CopyOperation::targetPath), (App)CopyNbtFunction.MergeStrategy.CODEC.fieldOf("op").forGetter(CopyOperation::op)).apply((Applicative)$$0, CopyOperation::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void apply(Supplier<Tag> $$0, Tag $$1) {
/*     */       try {
/*  55 */         List<Tag> $$2 = this.sourcePath.path().get($$1);
/*  56 */         if (!$$2.isEmpty()) {
/*  57 */           this.op.merge($$0.get(), this.targetPath.path(), $$2);
/*     */         }
/*  59 */       } catch (CommandSyntaxException commandSyntaxException) {}
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  65 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and($$0.group((App)NbtProviders.CODEC.fieldOf("source").forGetter(()), (App)CopyOperation.CODEC.listOf().fieldOf("ops").forGetter(()))).apply((Applicative)$$0, CopyNbtFunction::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CopyNbtFunction(List<LootItemCondition> $$0, NbtProvider $$1, List<CopyOperation> $$2) {
/*  74 */     super($$0);
/*  75 */     this.source = $$1;
/*  76 */     this.operations = List.copyOf($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public LootItemFunctionType getType() {
/*  81 */     return LootItemFunctions.COPY_NBT;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<LootContextParam<?>> getReferencedContextParams() {
/*  86 */     return this.source.getReferencedContextParams();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/*  91 */     Tag $$2 = this.source.get($$1);
/*  92 */     if ($$2 != null)
/*  93 */       this.operations.forEach($$2 -> {
/*     */             Objects.requireNonNull($$0); $$2.apply($$0::getOrCreateTag, $$1);
/*     */           }); 
/*  96 */     return $$0;
/*     */   }
/*     */   
/*     */   public static class Builder extends LootItemConditionalFunction.Builder<Builder> {
/*     */     private final NbtProvider source;
/* 101 */     private final List<CopyNbtFunction.CopyOperation> ops = Lists.newArrayList();
/*     */     
/*     */     Builder(NbtProvider $$0) {
/* 104 */       this.source = $$0;
/*     */     }
/*     */     
/*     */     public Builder copy(String $$0, String $$1, CopyNbtFunction.MergeStrategy $$2) {
/*     */       try {
/* 109 */         this.ops.add(new CopyNbtFunction.CopyOperation(CopyNbtFunction.Path.of($$0), CopyNbtFunction.Path.of($$1), $$2));
/* 110 */       } catch (CommandSyntaxException $$3) {
/* 111 */         throw new IllegalArgumentException($$3);
/*     */       } 
/* 113 */       return this;
/*     */     }
/*     */     
/*     */     public Builder copy(String $$0, String $$1) {
/* 117 */       return copy($$0, $$1, CopyNbtFunction.MergeStrategy.REPLACE);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Builder getThis() {
/* 122 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LootItemFunction build() {
/* 127 */       return new CopyNbtFunction(getConditions(), this.source, this.ops);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder copyData(NbtProvider $$0) {
/* 132 */     return new Builder($$0);
/*     */   }
/*     */   
/*     */   public static Builder copyData(LootContext.EntityTarget $$0) {
/* 136 */     return new Builder(ContextNbtProvider.forContextEntity($$0));
/*     */   }
/*     */   
/*     */   public enum MergeStrategy implements StringRepresentable {
/* 140 */     REPLACE("replace")
/*     */     {
/*     */       public void merge(Tag $$0, NbtPathArgument.NbtPath $$1, List<Tag> $$2) throws CommandSyntaxException {
/* 143 */         $$1.set($$0, (Tag)Iterables.getLast($$2));
/*     */       }
/*     */     },
/* 146 */     APPEND("append")
/*     */     {
/*     */       public void merge(Tag $$0, NbtPathArgument.NbtPath $$1, List<Tag> $$2) throws CommandSyntaxException {
/* 149 */         List<Tag> $$3 = $$1.getOrCreate($$0, ListTag::new);
/* 150 */         $$3.forEach($$1 -> {
/*     */               
/*     */               if ($$1 instanceof ListTag) {
/*     */                 $$0.forEach(());
/*     */               }
/*     */             });
/*     */       }
/*     */     },
/* 158 */     MERGE("merge")
/*     */     {
/*     */       public void merge(Tag $$0, NbtPathArgument.NbtPath $$1, List<Tag> $$2) throws CommandSyntaxException {
/* 161 */         List<Tag> $$3 = $$1.getOrCreate($$0, CompoundTag::new);
/* 162 */         $$3.forEach($$1 -> {
/*     */               if ($$1 instanceof CompoundTag) {
/*     */                 $$0.forEach(());
/*     */               }
/*     */             });
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 175 */     public static final Codec<MergeStrategy> CODEC = (Codec<MergeStrategy>)StringRepresentable.fromEnum(MergeStrategy::values); private final String name;
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     MergeStrategy(String $$0) {
/* 182 */       this.name = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/* 187 */       return this.name;
/*     */     }
/*     */     
/*     */     public abstract void merge(Tag param1Tag, NbtPathArgument.NbtPath param1NbtPath, List<Tag> param1List) throws CommandSyntaxException;
/*     */   }
/*     */   
/*     */   enum null {
/*     */     public void merge(Tag $$0, NbtPathArgument.NbtPath $$1, List<Tag> $$2) throws CommandSyntaxException {
/*     */       $$1.set($$0, (Tag)Iterables.getLast($$2));
/*     */     }
/*     */   }
/*     */   
/*     */   enum null {
/*     */     public void merge(Tag $$0, NbtPathArgument.NbtPath $$1, List<Tag> $$2) throws CommandSyntaxException {
/*     */       List<Tag> $$3 = $$1.getOrCreate($$0, ListTag::new);
/*     */       $$3.forEach($$1 -> {
/*     */             if ($$1 instanceof ListTag)
/*     */               $$0.forEach(()); 
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   enum null {
/*     */     public void merge(Tag $$0, NbtPathArgument.NbtPath $$1, List<Tag> $$2) throws CommandSyntaxException {
/*     */       List<Tag> $$3 = $$1.getOrCreate($$0, CompoundTag::new);
/*     */       $$3.forEach($$1 -> {
/*     */             if ($$1 instanceof CompoundTag)
/*     */               $$0.forEach(()); 
/*     */           });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\CopyNbtFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */