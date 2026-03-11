/*     */ package net.minecraft.util.datafix.schemas;
/*     */ 
/*     */ import com.mojang.datafixers.DSL;
/*     */ import com.mojang.datafixers.schemas.Schema;
/*     */ import com.mojang.datafixers.types.templates.TypeTemplate;
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
/*     */ public class V100
/*     */   extends Schema
/*     */ {
/*     */   public V100(int $$0, Schema $$1) {
/*  21 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   protected static TypeTemplate equipment(Schema $$0) {
/*  25 */     return DSL.optionalFields("ArmorItems", 
/*  26 */         DSL.list(References.ITEM_STACK.in($$0)), "HandItems", 
/*  27 */         DSL.list(References.ITEM_STACK.in($$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void registerMob(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, String $$2) {
/*  32 */     $$0.register($$1, $$2, () -> equipment($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema $$0) {
/*  37 */     Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities($$0);
/*     */ 
/*     */     
/*  40 */     registerMob($$0, $$1, "ArmorStand");
/*  41 */     registerMob($$0, $$1, "Creeper");
/*  42 */     registerMob($$0, $$1, "Skeleton");
/*  43 */     registerMob($$0, $$1, "Spider");
/*  44 */     registerMob($$0, $$1, "Giant");
/*  45 */     registerMob($$0, $$1, "Zombie");
/*  46 */     registerMob($$0, $$1, "Slime");
/*  47 */     registerMob($$0, $$1, "Ghast");
/*  48 */     registerMob($$0, $$1, "PigZombie");
/*  49 */     $$0.register($$1, "Enderman", $$1 -> DSL.optionalFields("carried", References.BLOCK_NAME.in($$0), equipment($$0)));
/*     */ 
/*     */ 
/*     */     
/*  53 */     registerMob($$0, $$1, "CaveSpider");
/*  54 */     registerMob($$0, $$1, "Silverfish");
/*  55 */     registerMob($$0, $$1, "Blaze");
/*  56 */     registerMob($$0, $$1, "LavaSlime");
/*  57 */     registerMob($$0, $$1, "EnderDragon");
/*  58 */     registerMob($$0, $$1, "WitherBoss");
/*  59 */     registerMob($$0, $$1, "Bat");
/*  60 */     registerMob($$0, $$1, "Witch");
/*  61 */     registerMob($$0, $$1, "Endermite");
/*  62 */     registerMob($$0, $$1, "Guardian");
/*  63 */     registerMob($$0, $$1, "Pig");
/*  64 */     registerMob($$0, $$1, "Sheep");
/*  65 */     registerMob($$0, $$1, "Cow");
/*  66 */     registerMob($$0, $$1, "Chicken");
/*  67 */     registerMob($$0, $$1, "Squid");
/*  68 */     registerMob($$0, $$1, "Wolf");
/*  69 */     registerMob($$0, $$1, "MushroomCow");
/*  70 */     registerMob($$0, $$1, "SnowMan");
/*  71 */     registerMob($$0, $$1, "Ozelot");
/*  72 */     registerMob($$0, $$1, "VillagerGolem");
/*  73 */     $$0.register($$1, "EntityHorse", $$1 -> DSL.optionalFields("Items", DSL.list(References.ITEM_STACK.in($$0)), "ArmorItem", References.ITEM_STACK.in($$0), "SaddleItem", References.ITEM_STACK.in($$0), equipment($$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     registerMob($$0, $$1, "Rabbit");
/*  80 */     $$0.register($$1, "Villager", $$1 -> DSL.optionalFields("Inventory", DSL.list(References.ITEM_STACK.in($$0)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", References.ITEM_STACK.in($$0), "buyB", References.ITEM_STACK.in($$0), "sell", References.ITEM_STACK.in($$0)))), equipment($$0)));
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
/*  93 */     registerMob($$0, $$1, "Shulker");
/*     */ 
/*     */     
/*  96 */     $$0.registerSimple($$1, "AreaEffectCloud");
/*  97 */     $$0.registerSimple($$1, "ShulkerBullet");
/*     */     
/*  99 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerTypes(Schema $$0, Map<String, Supplier<TypeTemplate>> $$1, Map<String, Supplier<TypeTemplate>> $$2) {
/* 104 */     super.registerTypes($$0, $$1, $$2);
/*     */     
/* 106 */     $$0.registerType(false, References.STRUCTURE, () -> DSL.optionalFields("entities", DSL.list(DSL.optionalFields("nbt", References.ENTITY_TREE.in($$0))), "blocks", DSL.list(DSL.optionalFields("nbt", References.BLOCK_ENTITY.in($$0))), "palette", DSL.list(References.BLOCK_STATE.in($$0))));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     $$0.registerType(false, References.BLOCK_STATE, DSL::remainder);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\schemas\V100.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */