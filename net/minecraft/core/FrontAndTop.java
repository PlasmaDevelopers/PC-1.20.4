/*    */ package net.minecraft.core;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum FrontAndTop implements StringRepresentable {
/*  9 */   DOWN_EAST("down_east", Direction.DOWN, Direction.EAST),
/* 10 */   DOWN_NORTH("down_north", Direction.DOWN, Direction.NORTH),
/* 11 */   DOWN_SOUTH("down_south", Direction.DOWN, Direction.SOUTH),
/* 12 */   DOWN_WEST("down_west", Direction.DOWN, Direction.WEST),
/*    */   
/* 14 */   UP_EAST("up_east", Direction.UP, Direction.EAST),
/* 15 */   UP_NORTH("up_north", Direction.UP, Direction.NORTH),
/* 16 */   UP_SOUTH("up_south", Direction.UP, Direction.SOUTH),
/* 17 */   UP_WEST("up_west", Direction.UP, Direction.WEST),
/*    */   
/* 19 */   WEST_UP("west_up", Direction.WEST, Direction.UP),
/* 20 */   EAST_UP("east_up", Direction.EAST, Direction.UP),
/* 21 */   NORTH_UP("north_up", Direction.NORTH, Direction.UP),
/* 22 */   SOUTH_UP("south_up", Direction.SOUTH, Direction.UP);
/*    */   
/*    */   static {
/* 25 */     LOOKUP_TOP_FRONT = (Int2ObjectMap<FrontAndTop>)Util.make(new Int2ObjectOpenHashMap((values()).length), $$0 -> {
/*    */           for (FrontAndTop $$1 : values())
/*    */             $$0.put(lookupKey($$1.front, $$1.top), $$1); 
/*    */         });
/*    */   }
/*    */   private static final Int2ObjectMap<FrontAndTop> LOOKUP_TOP_FRONT;
/*    */   private final String name;
/*    */   private final Direction top;
/*    */   private final Direction front;
/*    */   
/*    */   private static int lookupKey(Direction $$0, Direction $$1) {
/* 36 */     return $$1.ordinal() << 3 | $$0.ordinal();
/*    */   }
/*    */   
/*    */   FrontAndTop(String $$0, Direction $$1, Direction $$2) {
/* 40 */     this.name = $$0;
/* 41 */     this.front = $$1;
/* 42 */     this.top = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 47 */     return this.name;
/*    */   }
/*    */   
/*    */   public static FrontAndTop fromFrontAndTop(Direction $$0, Direction $$1) {
/* 51 */     int $$2 = lookupKey($$0, $$1);
/* 52 */     return (FrontAndTop)LOOKUP_TOP_FRONT.get($$2);
/*    */   }
/*    */   
/*    */   public Direction front() {
/* 56 */     return this.front;
/*    */   }
/*    */   
/*    */   public Direction top() {
/* 60 */     return this.top;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\FrontAndTop.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */