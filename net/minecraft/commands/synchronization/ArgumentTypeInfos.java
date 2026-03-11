/*     */ package net.minecraft.commands.synchronization;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.BoolArgumentType;
/*     */ import com.mojang.brigadier.arguments.DoubleArgumentType;
/*     */ import com.mojang.brigadier.arguments.FloatArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.arguments.LongArgumentType;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.commands.arguments.AngleArgument;
/*     */ import net.minecraft.commands.arguments.ColorArgument;
/*     */ import net.minecraft.commands.arguments.ComponentArgument;
/*     */ import net.minecraft.commands.arguments.CompoundTagArgument;
/*     */ import net.minecraft.commands.arguments.DimensionArgument;
/*     */ import net.minecraft.commands.arguments.EntityAnchorArgument;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.commands.arguments.GameModeArgument;
/*     */ import net.minecraft.commands.arguments.GameProfileArgument;
/*     */ import net.minecraft.commands.arguments.HeightmapTypeArgument;
/*     */ import net.minecraft.commands.arguments.MessageArgument;
/*     */ import net.minecraft.commands.arguments.NbtPathArgument;
/*     */ import net.minecraft.commands.arguments.NbtTagArgument;
/*     */ import net.minecraft.commands.arguments.ObjectiveArgument;
/*     */ import net.minecraft.commands.arguments.ObjectiveCriteriaArgument;
/*     */ import net.minecraft.commands.arguments.OperationArgument;
/*     */ import net.minecraft.commands.arguments.ParticleArgument;
/*     */ import net.minecraft.commands.arguments.RangeArgument;
/*     */ import net.minecraft.commands.arguments.ResourceArgument;
/*     */ import net.minecraft.commands.arguments.ResourceKeyArgument;
/*     */ import net.minecraft.commands.arguments.ResourceLocationArgument;
/*     */ import net.minecraft.commands.arguments.ResourceOrTagArgument;
/*     */ import net.minecraft.commands.arguments.ResourceOrTagKeyArgument;
/*     */ import net.minecraft.commands.arguments.ScoreHolderArgument;
/*     */ import net.minecraft.commands.arguments.ScoreboardSlotArgument;
/*     */ import net.minecraft.commands.arguments.SlotArgument;
/*     */ import net.minecraft.commands.arguments.StyleArgument;
/*     */ import net.minecraft.commands.arguments.TeamArgument;
/*     */ import net.minecraft.commands.arguments.TemplateMirrorArgument;
/*     */ import net.minecraft.commands.arguments.TemplateRotationArgument;
/*     */ import net.minecraft.commands.arguments.TimeArgument;
/*     */ import net.minecraft.commands.arguments.UuidArgument;
/*     */ import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
/*     */ import net.minecraft.commands.arguments.blocks.BlockStateArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.ColumnPosArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.RotationArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.SwizzleArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.Vec2Argument;
/*     */ import net.minecraft.commands.arguments.coordinates.Vec3Argument;
/*     */ import net.minecraft.commands.arguments.item.FunctionArgument;
/*     */ import net.minecraft.commands.arguments.item.ItemArgument;
/*     */ import net.minecraft.commands.arguments.item.ItemPredicateArgument;
/*     */ import net.minecraft.commands.synchronization.brigadier.DoubleArgumentInfo;
/*     */ import net.minecraft.commands.synchronization.brigadier.FloatArgumentInfo;
/*     */ import net.minecraft.commands.synchronization.brigadier.IntegerArgumentInfo;
/*     */ import net.minecraft.commands.synchronization.brigadier.LongArgumentInfo;
/*     */ import net.minecraft.commands.synchronization.brigadier.StringArgumentSerializer;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.gametest.framework.TestClassNameArgument;
/*     */ import net.minecraft.gametest.framework.TestFunctionArgument;
/*     */ 
/*     */ public class ArgumentTypeInfos
/*     */ {
/*  68 */   private static final Map<Class<?>, ArgumentTypeInfo<?, ?>> BY_CLASS = Maps.newHashMap();
/*     */   
/*     */   private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> ArgumentTypeInfo<A, T> register(Registry<ArgumentTypeInfo<?, ?>> $$0, String $$1, Class<? extends A> $$2, ArgumentTypeInfo<A, T> $$3) {
/*  71 */     BY_CLASS.put($$2, $$3);
/*  72 */     return (ArgumentTypeInfo<A, T>)Registry.register($$0, $$1, $$3);
/*     */   }
/*     */   
/*     */   public static ArgumentTypeInfo<?, ?> bootstrap(Registry<ArgumentTypeInfo<?, ?>> $$0) {
/*  76 */     register($$0, "brigadier:bool", BoolArgumentType.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(BoolArgumentType::bool));
/*  77 */     register($$0, "brigadier:float", FloatArgumentType.class, (ArgumentTypeInfo<FloatArgumentType, ArgumentTypeInfo.Template<FloatArgumentType>>)new FloatArgumentInfo());
/*  78 */     register($$0, "brigadier:double", DoubleArgumentType.class, (ArgumentTypeInfo<DoubleArgumentType, ArgumentTypeInfo.Template<DoubleArgumentType>>)new DoubleArgumentInfo());
/*  79 */     register($$0, "brigadier:integer", IntegerArgumentType.class, (ArgumentTypeInfo<IntegerArgumentType, ArgumentTypeInfo.Template<IntegerArgumentType>>)new IntegerArgumentInfo());
/*  80 */     register($$0, "brigadier:long", LongArgumentType.class, (ArgumentTypeInfo<LongArgumentType, ArgumentTypeInfo.Template<LongArgumentType>>)new LongArgumentInfo());
/*  81 */     register($$0, "brigadier:string", StringArgumentType.class, (ArgumentTypeInfo<StringArgumentType, ArgumentTypeInfo.Template<StringArgumentType>>)new StringArgumentSerializer());
/*     */     
/*  83 */     register($$0, "entity", EntityArgument.class, (ArgumentTypeInfo<EntityArgument, ArgumentTypeInfo.Template<EntityArgument>>)new EntityArgument.Info());
/*  84 */     register($$0, "game_profile", GameProfileArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(GameProfileArgument::gameProfile));
/*  85 */     register($$0, "block_pos", BlockPosArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(BlockPosArgument::blockPos));
/*  86 */     register($$0, "column_pos", ColumnPosArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(ColumnPosArgument::columnPos));
/*  87 */     register($$0, "vec3", Vec3Argument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(Vec3Argument::vec3));
/*  88 */     register($$0, "vec2", Vec2Argument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(Vec2Argument::vec2));
/*  89 */     register($$0, "block_state", BlockStateArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextAware(BlockStateArgument::block));
/*  90 */     register($$0, "block_predicate", BlockPredicateArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextAware(BlockPredicateArgument::blockPredicate));
/*  91 */     register($$0, "item_stack", ItemArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextAware(ItemArgument::item));
/*  92 */     register($$0, "item_predicate", ItemPredicateArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextAware(ItemPredicateArgument::itemPredicate));
/*  93 */     register($$0, "color", ColorArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(ColorArgument::color));
/*  94 */     register($$0, "component", ComponentArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(ComponentArgument::textComponent));
/*  95 */     register($$0, "style", StyleArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(StyleArgument::style));
/*  96 */     register($$0, "message", MessageArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(MessageArgument::message));
/*  97 */     register($$0, "nbt_compound_tag", CompoundTagArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(CompoundTagArgument::compoundTag));
/*  98 */     register($$0, "nbt_tag", NbtTagArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(NbtTagArgument::nbtTag));
/*  99 */     register($$0, "nbt_path", NbtPathArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(NbtPathArgument::nbtPath));
/* 100 */     register($$0, "objective", ObjectiveArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(ObjectiveArgument::objective));
/* 101 */     register($$0, "objective_criteria", ObjectiveCriteriaArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(ObjectiveCriteriaArgument::criteria));
/* 102 */     register($$0, "operation", OperationArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(OperationArgument::operation));
/* 103 */     register($$0, "particle", ParticleArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextAware(ParticleArgument::particle));
/* 104 */     register($$0, "angle", AngleArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(AngleArgument::angle));
/* 105 */     register($$0, "rotation", RotationArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(RotationArgument::rotation));
/* 106 */     register($$0, "scoreboard_slot", ScoreboardSlotArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(ScoreboardSlotArgument::displaySlot));
/* 107 */     register($$0, "score_holder", ScoreHolderArgument.class, (ArgumentTypeInfo<ScoreHolderArgument, ArgumentTypeInfo.Template<ScoreHolderArgument>>)new ScoreHolderArgument.Info());
/* 108 */     register($$0, "swizzle", SwizzleArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(SwizzleArgument::swizzle));
/* 109 */     register($$0, "team", TeamArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(TeamArgument::team));
/* 110 */     register($$0, "item_slot", SlotArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(SlotArgument::slot));
/* 111 */     register($$0, "resource_location", ResourceLocationArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(ResourceLocationArgument::id));
/* 112 */     register($$0, "function", FunctionArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(FunctionArgument::functions));
/* 113 */     register($$0, "entity_anchor", EntityAnchorArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(EntityAnchorArgument::anchor));
/* 114 */     register($$0, "int_range", RangeArgument.Ints.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(RangeArgument::intRange));
/* 115 */     register($$0, "float_range", RangeArgument.Floats.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(RangeArgument::floatRange));
/* 116 */     register($$0, "dimension", DimensionArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(DimensionArgument::dimension));
/* 117 */     register($$0, "gamemode", GameModeArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(GameModeArgument::gameMode));
/* 118 */     register($$0, "time", TimeArgument.class, (ArgumentTypeInfo<TimeArgument, ArgumentTypeInfo.Template<TimeArgument>>)new TimeArgument.Info());
/* 119 */     register($$0, "resource_or_tag", (Class)fixClassType(ResourceOrTagArgument.class), (ArgumentTypeInfo<ArgumentType<?>, ArgumentTypeInfo.Template<ArgumentType<?>>>)new ResourceOrTagArgument.Info());
/* 120 */     register($$0, "resource_or_tag_key", (Class)fixClassType(ResourceOrTagKeyArgument.class), (ArgumentTypeInfo<ArgumentType<?>, ArgumentTypeInfo.Template<ArgumentType<?>>>)new ResourceOrTagKeyArgument.Info());
/* 121 */     register($$0, "resource", (Class)fixClassType(ResourceArgument.class), (ArgumentTypeInfo<ArgumentType<?>, ArgumentTypeInfo.Template<ArgumentType<?>>>)new ResourceArgument.Info());
/* 122 */     register($$0, "resource_key", (Class)fixClassType(ResourceKeyArgument.class), (ArgumentTypeInfo<ArgumentType<?>, ArgumentTypeInfo.Template<ArgumentType<?>>>)new ResourceKeyArgument.Info());
/* 123 */     register($$0, "template_mirror", TemplateMirrorArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(TemplateMirrorArgument::templateMirror));
/* 124 */     register($$0, "template_rotation", TemplateRotationArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(TemplateRotationArgument::templateRotation));
/* 125 */     register($$0, "heightmap", HeightmapTypeArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(HeightmapTypeArgument::heightmap));
/*     */     
/* 127 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/* 128 */       register($$0, "test_argument", TestFunctionArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(TestFunctionArgument::testFunctionArgument));
/* 129 */       register($$0, "test_class", TestClassNameArgument.class, (ArgumentTypeInfo)SingletonArgumentInfo.contextFree(TestClassNameArgument::testClassName));
/*     */     } 
/* 131 */     return register($$0, "uuid", UuidArgument.class, SingletonArgumentInfo.contextFree(UuidArgument::uuid));
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T extends ArgumentType<?>> Class<T> fixClassType(Class<? super T> $$0) {
/* 136 */     return (Class)$$0;
/*     */   }
/*     */   
/*     */   public static boolean isClassRecognized(Class<?> $$0) {
/* 140 */     return BY_CLASS.containsKey($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <A extends ArgumentType<?>> ArgumentTypeInfo<A, ?> byClass(A $$0) {
/* 145 */     ArgumentTypeInfo<?, ?> $$1 = BY_CLASS.get($$0.getClass());
/* 146 */     if ($$1 == null) {
/* 147 */       throw new IllegalArgumentException(String.format(Locale.ROOT, "Unrecognized argument type %s (%s)", new Object[] { $$0, $$0.getClass() }));
/*     */     }
/* 149 */     return (ArgumentTypeInfo)$$1;
/*     */   }
/*     */   
/*     */   public static <A extends ArgumentType<?>> ArgumentTypeInfo.Template<A> unpack(A $$0) {
/* 153 */     return byClass($$0).unpack($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\ArgumentTypeInfos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */