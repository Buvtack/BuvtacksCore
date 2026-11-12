package com.buvtack.buvtackscore.client.util;

import com.buvtack.buvtackscore.Constants;
import com.buvtack.buvtackscore.client.gui.Alignment;
import com.buvtack.buvtackscore.client.gui.components.DimensionalModWidget;
import com.buvtack.buvtackscore.common.util.ModWorldHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.lwjgl.sdl.*;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.*;

public class ClientModHelper {

    public static MinecraftServer SERVER = null;

    //private static double prevScroll = 0;
    private static double scroll = 0;

    private static Queue<Character> enteredChars = new ArrayDeque<>();
    private static boolean enteredChar = false;

    private static Queue<Integer> pressedKeys = new ArrayDeque<>();
    private static boolean pressedKey;

    private static boolean shiftPressed;
    private static boolean controlPressed;

    private static final boolean[] keyWasDown = new boolean[512];

    public static void handleSDLEvent(SDL_Event event) {
        int type = event.type();

        // 1. Scroll Callback Translation (GLFW: scroll_callback -> SDL: SDL_EVENT_MOUSE_WHEEL)
        if (type == SDLEvents.SDL_EVENT_MOUSE_WHEEL) {
            scroll = event.wheel().y();
        }

        // 2. Char Callback Translation (GLFW: char_callback -> SDL: SDL_EVENT_TEXT_INPUT)
        else if (type == SDLEvents.SDL_EVENT_TEXT_INPUT) {
            Constants.LOG.info("FUCK THIS BULLSHIT I SWEAR");
            String text = event.text().textString();
            if (text != null) {
                for (char c : text.toCharArray()) {
                    enteredChars.offer(c);
                    enteredChar = true;
                }
            }
        }

        // 3. Key Callback Translation (GLFW: key_callback -> SDL: SDL_EVENT_KEY_DOWN / UP)
        else if (type == SDLEvents.SDL_EVENT_KEY_DOWN || type == SDLEvents.SDL_EVENT_KEY_UP) {
            boolean down = (type == SDLEvents.SDL_EVENT_KEY_DOWN);
            int key = event.key().key();

            // Track modifiers (matches your old modifier checks)
            if (key == SDLKeycode.SDLK_LSHIFT || key == SDLKeycode.SDLK_RSHIFT) {
                shiftPressed = down;
            }
            if (key == SDLKeycode.SDLK_LCTRL || key == SDLKeycode.SDLK_RCTRL) {
                controlPressed = down;
            }

            if (down) {
                pressedKeys.offer(key);
                pressedKey = true;
            }
        }
    }

    private static void checkKeyEdge(ByteBuffer keystate, int scancode, int keycode) {
        if (scancode >= keyWasDown.length) return;
        boolean isDown = keystate.get(scancode) != 0;
        if (isDown && !keyWasDown[scancode]) {
            pressedKeys.offer(keycode);
            pressedKey = true;
        }
        keyWasDown[scancode] = isDown;
    }

    public static double scroll() {
        return scroll;
    }

    public static void resetScroll() {
        scroll = 0;
    }

    public static boolean isShiftPressed() {
        return shiftPressed;
    }

    public static boolean isControlPressed() {
        return controlPressed;
    }

    public static Queue<Character> getEnteredChars() {
        return enteredChars;
    }

    public static Queue<Integer> getPressedKeys() {
        return pressedKeys;
    }

    public static boolean enteredChar() {
        return enteredChar;
    }

    public static boolean pressedKey() {
        return pressedKey;
    }

    public static void resetInputChar() {
        enteredChar = false;
        enteredChars.clear();
    }

    public static void resetKeyPressed() {
        pressedKey = false;
        pressedKeys.clear();
    }

    public static boolean isLeftClicking() {
        // Pass null via MemoryStack or explicit null arrays/pointers if permitted,
        // or query it using SDLMouse directly:
        int buttons = SDLMouse.SDL_GetMouseState(null, null);
        // SDL_BUTTON_LMASK is 1 (left mouse button bitmask)
        return (buttons & 1) != 0;
    }

    public static boolean isNotLeftClicking() {
        int buttons = SDLMouse.SDL_GetMouseState(null, null);
        return (buttons & 1) == 0;
    }

    public static Player player() {
        return Minecraft.getInstance().player;
    }

    public static Screen screen() {
        return Minecraft.getInstance().gui.screen();
    }

    public static boolean isValidRegistryName(String name) {
        return name.indexOf(':') > 0 && name.indexOf(':') < name.length() - 1;
    }

    public static boolean isSinglePlayer() {
        return Minecraft.getInstance().getSingleplayerServer() != null;
    }

    public static boolean connected() {
        return server() != null;
    }

    public static boolean isAdmin() {
        return Minecraft.getInstance().player.permissions().hasPermission(Permissions.COMMANDS_ADMIN);
    }

    private static boolean using() {
        return Minecraft.getInstance().options.keyUse.isDown();
    }

    private static boolean isElytraFlyingAndFireworkBoosting() {
        Player player = Minecraft.getInstance().player;
        boolean flying = player.isFallFlying();
        boolean hasElytra = player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA);
        ItemStack offhandItem = player.getOffhandItem();
        return offhandItem.is(Items.FIREWORK_ROCKET) && flying && hasElytra;
    }

    private static boolean isShielding() {
        Player player = Minecraft.getInstance().player;
        ItemStack offhandItem = player.getOffhandItem();
        return offhandItem.is(Items.SHIELD);
    }

    private static boolean isPlacingBlock(HitResult hit) {
        Player player = Minecraft.getInstance().player;
        return isHoldingBlock(InteractionHand.OFF_HAND) && hit.getType() == HitResult.Type.BLOCK;
    }

    private static boolean isHoldingBlock(InteractionHand hand) {
        Player player = Minecraft.getInstance().player;
        return player.getItemInHand(hand).getItem() instanceof BlockItem;
    }

    private static boolean isOffhandUsingProjectile() {
        Player player = Minecraft.getInstance().player;
        ItemStack offhandItem = player.getOffhandItem();
        return offhandItem.is(Items.EGG) ||
                offhandItem.is(Items.SNOWBALL) ||
                offhandItem.is(Items.ENDER_PEARL) ||
                offhandItem.is(Items.ENDER_EYE) ||
                offhandItem.getItem() instanceof SplashPotionItem ||
                offhandItem.getItem() instanceof LingeringPotionItem ||
                offhandItem.getItem() instanceof ExperienceBottleItem;
    }

    private static boolean isOffhandUsingBonemeal(HitResult hit) {
        Player player = Minecraft.getInstance().player;
        return player.getOffhandItem().is(Items.BONE_MEAL) && isTargetGrowable(hit);
    }

    private static boolean isTargetGrowable(HitResult hit) {
        if (!(hit instanceof BlockHitResult blockHit))
            return false;

        Minecraft mc = Minecraft.getInstance();
        BlockState state = mc.level.getBlockState(blockHit.getBlockPos());
        Block block = state.getBlock();

        if (block instanceof BonemealableBlock growable)
            return growable.isValidBonemealTarget(mc.level, blockHit.getBlockPos(), state, BonemealSource.INTERACTION);

        return false;
    }

    private static boolean isOffhandUsingToolOnBlock(HitResult hit) {
        Player player = Minecraft.getInstance().player;
        if (!(hit instanceof BlockHitResult blockHit))
            return false;

        Minecraft mc = Minecraft.getInstance();
        BlockState state = mc.level.getBlockState(blockHit.getBlockPos());
        return isStrippingWood(state) || isPathingWithShovel(state);
    }

    private static boolean isStrippingWood(BlockState block) {
        Player player = Minecraft.getInstance().player;
        ItemStack off = player.getOffhandItem();
        return off.is(ItemTags.AXES) && block.is(BlockTags.LOGS);
    }

    private static boolean isPathingWithShovel(BlockState block) {
        Player player = Minecraft.getInstance().player;
        ItemStack off = player.getOffhandItem();
        return off.is(ItemTags.SHOVELS) && block.is(BlockTags.DIRT);
    }

    private static boolean isOffhandEating() {
        Player player = Minecraft.getInstance().player;
        ItemStack offhandItem = player.getOffhandItem();
        return offhandItem.has(DataComponents.FOOD) || offhandItem.getItem() instanceof PotionItem;
    }

    public static boolean isDrawingBow(InteractionHand hand) {
        Player player = Minecraft.getInstance().player;
        return isHolding(Items.BOW, hand) && player.isUsingItem() && player.getUsedItemHand() == hand;
    }

    public static boolean isEating(InteractionHand hand) {
        Player player = Minecraft.getInstance().player;
        if (!player.isUsingItem())
            return false;

        ItemStack stack = player.getUseItem();
        if (!player.getItemInHand(hand).is(stack.getItem()))
            return false;

        return stack.getUseAnimation() == ItemUseAnimation.EAT || stack.getUseAnimation() == ItemUseAnimation.DRINK;
    }

    public static boolean isHolding(Item item) {
        return isHolding(item, InteractionHand.MAIN_HAND) || isHolding(item, InteractionHand.OFF_HAND);
    }

    public static boolean isHolding(Item item, InteractionHand hand) {
        return player().getItemInHand(hand).is(item);
    }

    public static boolean isHolding(ItemStack stack) {
        return isHolding(stack, InteractionHand.MAIN_HAND) || isHolding(stack, InteractionHand.OFF_HAND);
    }

    public static boolean isHolding(ItemStack stack, InteractionHand hand) {
        ItemStack held = player().getItemInHand(hand);
        return held.is(stack.getItem());
    }

    public static boolean isBlock(ItemStack stack) {
        ItemModelResolver resolver = Minecraft.getInstance().getItemModelResolver();
        ItemStackRenderState renderState = new ItemStackRenderState();
        resolver.updateForTopItem(
                renderState,
                stack,
                ItemDisplayContext.GUI,
                Minecraft.getInstance().level,
                null,
                0
        );

        return stack.getItem() instanceof BlockItem && renderState.usesBlockLight();
    }

    public static boolean isLeftHanded() {
        Player player = Minecraft.getInstance().player;
        return player.getMainArm() == HumanoidArm.LEFT;
    }

    public static boolean isLocalPlayer(Player player) {
        Player localPlayer = Minecraft.getInstance().player;
        return player.getUUID().equals(localPlayer.getUUID());
    }

    public static ClientLevel level() {
        return Minecraft.getInstance().level;
    }

    public static long seed() {
        return level().getServer().overworld().getSeed();
    }

    public static MinecraftServer server() {
        return SERVER;
    }

    public static Font font() {
        return Minecraft.getInstance().font;
    }

    public static double mouseX() {
        Minecraft mc = Minecraft.getInstance();
        return mc.mouseHandler.xpos() * (double) mc.getWindow().getGuiScaledWidth() / mc.getWindow().getScreenWidth();
    }

    public static double mouseY() {
        Minecraft mc = Minecraft.getInstance();
        return mc.mouseHandler.ypos() * (double) mc.getWindow().getGuiScaledHeight() / mc.getWindow().getScreenHeight();
    }

    public static void playSound(SoundEvent sound, float pitch) {
        Minecraft.getInstance().getSoundManager().play(
                SimpleSoundInstance.forUI(sound, pitch)
        );
    }

    public static Map<Alignment, List<DimensionalModWidget>> sortIntoAlignments(List<DimensionalModWidget> widgets) {
        Map<Alignment, List<DimensionalModWidget>> result = new HashMap<>();

        for (Alignment alignment : Alignment.values())
            result.put(alignment, new ArrayList<>());

        widgets.forEach(widget -> {
            result.get(widget.alignment).add(widget);
        });
        return result;
    }

    public static boolean isInteractableBlock(BlockPos pos) {
        return ModWorldHelper.isInteractableBlock(level(), pos);
    }
}
