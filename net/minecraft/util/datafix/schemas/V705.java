/*     */ package net.minecraft.util.datafix.schemas;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.templates.Hook;
/*     */ import com.mojang.datafixers.types.templates.TypeTemplate;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Map;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.util.datafix.fixes.References;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class V705
/*     */   extends NamespacedSchema
/*     */ {
/*     */   public V705(int $$0, Schema $$1) {
/*  27 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   protected static void registerMob(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/*  31 */     $$0.register($$1, $$2, () -> V100.equipment($$0));
/*     */   }
/*     */   
/*     */   protected static void registerThrowableProjectile(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/*  35 */     $$0.register($$1, $$2, () -> DSL.optionalFields("inTile", References.BLOCK_NAME.in($$0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/*  42 */     Map<String, Supplier<TypeTemplate>> $$1 = Maps.newHashMap();
/*     */     
/*  44 */     $$0.registerSimple($$1, "minecraft:area_effect_cloud");
/*  45 */     registerMob($$0, $$1, "minecraft:armor_stand");
/*  46 */     $$0.register($$1, "minecraft:arrow", $$1 -> DSL.optionalFields("inTile", References.BLOCK_NAME.in($$0)));
/*     */ 
/*     */     
/*  49 */     registerMob($$0, $$1, "minecraft:bat");
/*  50 */     registerMob($$0, $$1, "minecraft:blaze");
/*  51 */     $$0.registerSimple($$1, "minecraft:boat");
/*  52 */     registerMob($$0, $$1, "minecraft:cave_spider");
/*  53 */     $$0.register($$1, "minecraft:chest_minecart", $$1 -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in($$0), "Items", DSL.list(References.ITEM_STACK.in($$0))));
/*     */ 
/*     */ 
/*     */     
/*  57 */     registerMob($$0, $$1, "minecraft:chicken");
/*  58 */     $$0.register($$1, "minecraft:commandblock_minecart", $$1 -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in($$0)));
/*     */ 
/*     */     
/*  61 */     registerMob($$0, $$1, "minecraft:cow");
/*  62 */     registerMob($$0, $$1, "minecraft:creeper");
/*  63 */     $$0.register($$1, "minecraft:donkey", $$1 -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0)), "SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     $$0.registerSimple($$1, "minecraft:dragon_fireball");
/*  69 */     registerThrowableProjectile($$0, $$1, "minecraft:egg");
/*  70 */     registerMob($$0, $$1, "minecraft:elder_guardian");
/*  71 */     $$0.registerSimple($$1, "minecraft:ender_crystal");
/*  72 */     registerMob($$0, $$1, "minecraft:ender_dragon");
/*  73 */     $$0.register($$1, "minecraft:enderman", $$1 -> DSL.optionalFields("carried", References.BLOCK_NAME.in($$0), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */     
/*  77 */     registerMob($$0, $$1, "minecraft:endermite");
/*  78 */     registerThrowableProjectile($$0, $$1, "minecraft:ender_pearl");
/*  79 */     $$0.registerSimple($$1, "minecraft:eye_of_ender_signal");
/*  80 */     $$0.register($$1, "minecraft:falling_block", $$1 -> DSL.optionalFields("Block", References.BLOCK_NAME.in($$0), "TileEntityData", References.BLOCK_ENTITY.in($$0)));
/*     */ 
/*     */ 
/*     */     
/*  84 */     registerThrowableProjectile($$0, $$1, "minecraft:fireball");
/*  85 */     $$0.register($$1, "minecraft:fireworks_rocket", $$1 -> DSL.optionalFields("FireworksItem", References.ITEM_STACK.in($$0)));
/*     */ 
/*     */     
/*  88 */     $$0.register($$1, "minecraft:furnace_minecart", $$1 -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in($$0)));
/*     */ 
/*     */     
/*  91 */     registerMob($$0, $$1, "minecraft:ghast");
/*  92 */     registerMob($$0, $$1, "minecraft:giant");
/*  93 */     registerMob($$0, $$1, "minecraft:guardian");
/*  94 */     $$0.register($$1, "minecraft:hopper_minecart", $$1 -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in($$0), "Items", DSL.list(References.ITEM_STACK.in($$0))));
/*     */ 
/*     */ 
/*     */     
/*  98 */     $$0.register($$1, "minecraft:horse", $$1 -> DSL.optionalFields("ArmorItem", References.ITEM_STACK.in($$0), "SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     registerMob($$0, $$1, "minecraft:husk");
/* 105 */     $$0.register($$1, "minecraft:item", $$1 -> DSL.optionalFields("Item", References.ITEM_STACK.in($$0)));
/*     */ 
/*     */     
/* 108 */     $$0.register($$1, "minecraft:item_frame", $$1 -> DSL.optionalFields("Item", References.ITEM_STACK.in($$0)));
/*     */ 
/*     */     
/* 111 */     $$0.registerSimple($$1, "minecraft:leash_knot");
/* 112 */     registerMob($$0, $$1, "minecraft:magma_cube");
/* 113 */     $$0.register($$1, "minecraft:minecart", $$1 -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in($$0)));
/*     */ 
/*     */     
/* 116 */     registerMob($$0, $$1, "minecraft:mooshroom");
/* 117 */     $$0.register($$1, "minecraft:mule", $$1 -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0)), "SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     registerMob($$0, $$1, "minecraft:ocelot");
/* 123 */     $$0.registerSimple($$1, "minecraft:painting");
/* 124 */     $$0.registerSimple($$1, "minecraft:parrot");
/* 125 */     registerMob($$0, $$1, "minecraft:pig");
/* 126 */     registerMob($$0, $$1, "minecraft:polar_bear");
/* 127 */     $$0.register($$1, "minecraft:potion", $$1 -> DSL.optionalFields("Potion", References.ITEM_STACK.in($$0), "inTile", References.BLOCK_NAME.in($$0)));
/*     */ 
/*     */ 
/*     */     
/* 131 */     registerMob($$0, $$1, "minecraft:rabbit");
/* 132 */     registerMob($$0, $$1, "minecraft:sheep");
/* 133 */     registerMob($$0, $$1, "minecraft:shulker");
/* 134 */     $$0.registerSimple($$1, "minecraft:shulker_bullet");
/* 135 */     registerMob($$0, $$1, "minecraft:silverfish");
/* 136 */     registerMob($$0, $$1, "minecraft:skeleton");
/* 137 */     $$0.register($$1, "minecraft:skeleton_horse", $$1 -> DSL.optionalFields("SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */     
/* 141 */     registerMob($$0, $$1, "minecraft:slime");
/* 142 */     registerThrowableProjectile($$0, $$1, "minecraft:small_fireball");
/* 143 */     registerThrowableProjectile($$0, $$1, "minecraft:snowball");
/* 144 */     registerMob($$0, $$1, "minecraft:snowman");
/* 145 */     $$0.register($$1, "minecraft:spawner_minecart", $$1 -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in($$0), References.UNTAGGED_SPAWNER.in($$0)));
/*     */ 
/*     */ 
/*     */     
/* 149 */     $$0.register($$1, "minecraft:spectral_arrow", $$1 -> DSL.optionalFields("inTile", References.BLOCK_NAME.in($$0)));
/*     */ 
/*     */     
/* 152 */     registerMob($$0, $$1, "minecraft:spider");
/* 153 */     registerMob($$0, $$1, "minecraft:squid");
/* 154 */     registerMob($$0, $$1, "minecraft:stray");
/* 155 */     $$0.registerSimple($$1, "minecraft:tnt");
/* 156 */     $$0.register($$1, "minecraft:tnt_minecart", $$1 -> DSL.optionalFields("DisplayTile", References.BLOCK_NAME.in($$0)));
/*     */ 
/*     */     
/* 159 */     $$0.register($$1, "minecraft:villager", $$1 -> DSL.optionalFields("Inventory", DSL.list(References.ITEM_STACK.in($$0)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", References.ITEM_STACK.in($$0), "buyB", References.ITEM_STACK.in($$0), "sell", References.ITEM_STACK.in($$0)))), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 172 */     registerMob($$0, $$1, "minecraft:villager_golem");
/* 173 */     registerMob($$0, $$1, "minecraft:witch");
/* 174 */     registerMob($$0, $$1, "minecraft:wither");
/* 175 */     registerMob($$0, $$1, "minecraft:wither_skeleton");
/* 176 */     registerThrowableProjectile($$0, $$1, "minecraft:wither_skull");
/* 177 */     registerMob($$0, $$1, "minecraft:wolf");
/* 178 */     registerThrowableProjectile($$0, $$1, "minecraft:xp_bottle");
/* 179 */     $$0.registerSimple($$1, "minecraft:xp_orb");
/* 180 */     registerMob($$0, $$1, "minecraft:zombie");
/* 181 */     $$0.register($$1, "minecraft:zombie_horse", $$1 -> DSL.optionalFields("SaddleItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */     
/* 185 */     registerMob($$0, $$1, "minecraft:zombie_pigman");
/* 186 */     registerMob($$0, $$1, "minecraft:zombie_villager");
/*     */ 
/*     */     
/* 189 */     $$0.registerSimple($$1, "minecraft:evocation_fangs");
/* 190 */     registerMob($$0, $$1, "minecraft:evocation_illager");
/* 191 */     $$0.registerSimple($$1, "minecraft:illusion_illager");
/* 192 */     $$0.register($$1, "minecraft:llama", $$1 -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0)), "SaddleItem", References.ITEM_STACK.in($$0), "DecorItem", References.ITEM_STACK.in($$0), V100.equipment($$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     $$0.registerSimple($$1, "minecraft:llama_spit");
/* 199 */     registerMob($$0, $$1, "minecraft:vex");
/* 200 */     registerMob($$0, $$1, "minecraft:vindication_illager");
/*     */     
/* 202 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
/* 207 */     super.registerTypes($$0, $$1, $$2);
/* 208 */     $$0.registerType(true, References.ENTITY, () -> DSL.taggedChoiceLazy("id", namespacedString(), $$0));
/* 209 */     $$0.registerType(true, References.ITEM_STACK, () -> DSL.hook(DSL.optionalFields("id", References.ITEM_NAME.in($$0), "tag", DSL.optionalFields("EntityTag", References.ENTITY_TREE.in($$0), "BlockEntityTag", References.BLOCK_ENTITY.in($$0), "CanDestroy", DSL.list(References.BLOCK_NAME.in($$0)), "CanPlaceOn", DSL.list(References.BLOCK_NAME.in($$0)), "Items", DSL.list(References.ITEM_STACK.in($$0)))), ADD_NAMES, Hook.HookFunction.IDENTITY));
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
/*     */   
/* 221 */   protected static final Hook.HookFunction ADD_NAMES = new Hook.HookFunction()
/*     */     {
/*     */       public <T> T apply(DynamicOps<T> $$0, T $$1) {
/* 224 */         return V99.addNames(new Dynamic($$0, $$1), V704.ITEM_TO_BLOCKENTITY, "minecraft:armor_stand");
/*     */       }
/*     */     };
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V705.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */