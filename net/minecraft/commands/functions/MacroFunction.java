/*     */ package net.minecraft.commands.functions;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntLists;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.commands.ExecutionCommandSource;
/*     */ import net.minecraft.commands.FunctionInstantiationException;
/*     */ import net.minecraft.commands.execution.UnboundEntryAction;
/*     */ import net.minecraft.nbt.ByteTag;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.DoubleTag;
/*     */ import net.minecraft.nbt.FloatTag;
/*     */ import net.minecraft.nbt.LongTag;
/*     */ import net.minecraft.nbt.ShortTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ public class MacroFunction<T extends ExecutionCommandSource<T>>
/*     */   implements CommandFunction<T> {
/*     */   static {
/*  32 */     DECIMAL_FORMAT = (DecimalFormat)Util.make(new DecimalFormat("#"), $$0 -> {
/*     */           $$0.setMaximumFractionDigits(15);
/*     */           $$0.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));
/*     */         });
/*     */   }
/*     */   private static final DecimalFormat DECIMAL_FORMAT;
/*     */   private static final int MAX_CACHE_ENTRIES = 8;
/*     */   private final List<String> parameters;
/*  40 */   private final Object2ObjectLinkedOpenHashMap<List<String>, InstantiatedFunction<T>> cache = new Object2ObjectLinkedOpenHashMap(8, 0.25F);
/*     */   
/*     */   private final ResourceLocation id;
/*     */   private final List<Entry<T>> entries;
/*     */   
/*     */   public MacroFunction(ResourceLocation $$0, List<Entry<T>> $$1, List<String> $$2) {
/*  46 */     this.id = $$0;
/*  47 */     this.entries = $$1;
/*  48 */     this.parameters = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation id() {
/*  53 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public InstantiatedFunction<T> instantiate(@Nullable CompoundTag $$0, CommandDispatcher<T> $$1, T $$2) throws FunctionInstantiationException {
/*  58 */     if ($$0 == null) {
/*  59 */       throw new FunctionInstantiationException(Component.translatable("commands.function.error.missing_arguments", new Object[] { Component.translationArg(id()) }));
/*     */     }
/*  61 */     List<String> $$3 = new ArrayList<>(this.parameters.size());
/*  62 */     for (String $$4 : this.parameters) {
/*  63 */       Tag $$5 = $$0.get($$4);
/*  64 */       if ($$5 == null) {
/*  65 */         throw new FunctionInstantiationException(Component.translatable("commands.function.error.missing_argument", new Object[] { Component.translationArg(id()), $$4 }));
/*     */       }
/*  67 */       $$3.add(stringify($$5));
/*     */     } 
/*     */     
/*  70 */     InstantiatedFunction<T> $$6 = (InstantiatedFunction<T>)this.cache.getAndMoveToLast($$3);
/*  71 */     if ($$6 != null) {
/*  72 */       return $$6;
/*     */     }
/*  74 */     if (this.cache.size() >= 8) {
/*  75 */       this.cache.removeFirst();
/*     */     }
/*  77 */     InstantiatedFunction<T> $$7 = substituteAndParse(this.parameters, $$3, $$1, $$2);
/*  78 */     this.cache.put($$3, $$7);
/*  79 */     return $$7;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String stringify(Tag $$0) {
/*  84 */     if ($$0 instanceof FloatTag) { FloatTag $$1 = (FloatTag)$$0;
/*  85 */       return DECIMAL_FORMAT.format($$1.getAsFloat()); }
/*  86 */      if ($$0 instanceof DoubleTag) { DoubleTag $$2 = (DoubleTag)$$0;
/*  87 */       return DECIMAL_FORMAT.format($$2.getAsDouble()); }
/*  88 */      if ($$0 instanceof ByteTag) { ByteTag $$3 = (ByteTag)$$0;
/*  89 */       return String.valueOf($$3.getAsByte()); }
/*  90 */      if ($$0 instanceof ShortTag) { ShortTag $$4 = (ShortTag)$$0;
/*  91 */       return String.valueOf($$4.getAsShort()); }
/*  92 */      if ($$0 instanceof LongTag) { LongTag $$5 = (LongTag)$$0;
/*  93 */       return String.valueOf($$5.getAsLong()); }
/*     */     
/*  95 */     return $$0.getAsString();
/*     */   }
/*     */   
/*     */   private static void lookupValues(List<String> $$0, IntList $$1, List<String> $$2) {
/*  99 */     $$2.clear();
/* 100 */     $$1.forEach($$2 -> $$0.add($$1.get($$2)));
/*     */   }
/*     */   
/*     */   private InstantiatedFunction<T> substituteAndParse(List<String> $$0, List<String> $$1, CommandDispatcher<T> $$2, T $$3) throws FunctionInstantiationException {
/* 104 */     List<UnboundEntryAction<T>> $$4 = new ArrayList<>(this.entries.size());
/* 105 */     List<String> $$5 = new ArrayList<>($$1.size());
/*     */     
/* 107 */     for (Entry<T> $$6 : this.entries) {
/* 108 */       lookupValues($$1, $$6.parameters(), $$5);
/* 109 */       $$4.add($$6.instantiate($$5, $$2, $$3, this.id));
/*     */     } 
/* 111 */     return new PlainTextFunction<>(id().withPath($$1 -> $$1 + "/" + $$1), $$4);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class PlainTextEntry<T>
/*     */     implements Entry<T>
/*     */   {
/*     */     private final UnboundEntryAction<T> compiledAction;
/*     */ 
/*     */ 
/*     */     
/*     */     public PlainTextEntry(UnboundEntryAction<T> $$0) {
/* 124 */       this.compiledAction = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntList parameters() {
/* 129 */       return IntLists.emptyList();
/*     */     }
/*     */ 
/*     */     
/*     */     public UnboundEntryAction<T> instantiate(List<String> $$0, CommandDispatcher<T> $$1, T $$2, ResourceLocation $$3) {
/* 134 */       return this.compiledAction;
/*     */     }
/*     */   }
/*     */   
/*     */   static class MacroEntry<T extends ExecutionCommandSource<T>> implements Entry<T> {
/*     */     private final StringTemplate template;
/*     */     private final IntList parameters;
/*     */     
/*     */     public MacroEntry(StringTemplate $$0, IntList $$1) {
/* 143 */       this.template = $$0;
/* 144 */       this.parameters = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public IntList parameters() {
/* 149 */       return this.parameters;
/*     */     }
/*     */ 
/*     */     
/*     */     public UnboundEntryAction<T> instantiate(List<String> $$0, CommandDispatcher<T> $$1, T $$2, ResourceLocation $$3) throws FunctionInstantiationException {
/* 154 */       String $$4 = this.template.substitute($$0);
/*     */       try {
/* 156 */         return CommandFunction.parseCommand($$1, $$2, new StringReader($$4));
/* 157 */       } catch (CommandSyntaxException $$5) {
/* 158 */         throw new FunctionInstantiationException(Component.translatable("commands.function.error.parse", new Object[] { Component.translationArg($$3), $$4, $$5.getMessage() }));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static interface Entry<T> {
/*     */     IntList parameters();
/*     */     
/*     */     UnboundEntryAction<T> instantiate(List<String> param1List, CommandDispatcher<T> param1CommandDispatcher, T param1T, ResourceLocation param1ResourceLocation) throws FunctionInstantiationException;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\functions\MacroFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */